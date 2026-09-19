package nz.ac.auckland.eabs.zhiyuan.coordinator;

import com.g7.ip.BatchManager;
import com.g7.ip.Dao;
import com.g7.ip.Db;
import com.g7.ip.DeviationDetector;
import com.g7.ip.DigitalTwinAssembler;
import com.g7.ip.POS;
import com.g7.ip.TrackerFieldChange;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Real, database-backed batch source for CoordinatorCD.activateBatchIn/batchDrainedOut --
 * the IP's persistence layer (com.g7.ip.*). Stage 2 (IP report Section 6/10): every call
 * that touches Dao/POS/BatchManager -- and therefore the JDBC Connection -- runs on the
 * dedicated DbWorker thread, never on the console reader thread or the SystemJ tick thread
 * that invokes resultReceived(). Submitting an order, triggering a batch check, and
 * processing BatchDrained all just enqueue a Runnable and return immediately; the
 * assembly/admission logic itself (Stage 1, already validated) is unchanged.
 *
 * The ACTIVATE frame still carries one orderId label, because Eric's Tracker requires one --
 * but that label is display-only. Real per-bottle order attribution is entirely
 * Dao#nextUnfulfilledOrderForBatch's job on the IP side and does not depend on it, so
 * cross-order merging (several orders inside one batch) is unaffected by the Coordinator/
 * Tracker only ever seeing a single representative orderId.
 */
public final class IpBatchManagerModel {
    /** The real production route, in station order -- matches IntegratedCoordinator's own
     * ROUTE/twinLocationFor labels exactly, so a bottle's persisted BottleEvents sequence can
     * be compared against it (IP report Section 7, recipe-deviation detection). Public so the
     * factory GUI's bottle-traceability view (com.g7.ip.gui.FactoryGuiFrame) can render every
     * expected stop -- including ones a bottle hasn't reached yet -- without duplicating this
     * list. */
    public static final List<String> EXPECTED_STATIONS = Arrays.asList(
            "loader", "conveyor_in", "filler", "lid", "capper", "conveyor_out", "labeller", "unloader");

    private final Dao dao;
    private final POS pos;
    private final BatchManager batchManager;
    private final DigitalTwinAssembler assembler;
    private final DeviationDetector detector;
    private final DbWorker dbWorker = new DbWorker();
    /** GP demo mode (brief 4.2: "order should be submitted ... processed and production
     * launched"): each accepted order activates its own batch immediately, no 'go'/cross-order
     * merge step. Off by default -Dip.autoActivate=true keeps the IP's own two-step demo
     * (submit several orders, then 'go' to merge same-product ones) completely unaffected. */
    private final boolean autoActivate;

    private String pendingFrame = null;
    private String awaitingResultFor = null; // null = no batch currently in flight
    /** Stops NEW PRODUCTION: set on a coordinator FAULT| (and if the database never opened),
     * cleared by RECOVERED|. Gates order submission and batch activation only. It deliberately
     * does NOT gate the GUI's read-only queries -- see databaseUnavailable(). */
    private volatile boolean halted = false;

    /** Gate for the GUI's read-only queries (and fault resolution, which records what an
     * operator did but never affects production). A coordinator HOLD stops production, not
     * inspection: the fault that caused a hold is exactly what the operator needs to look at.
     * Previously these queries were gated on halted, so during any hold Track Order reported
     * an existing order as NOT FOUND and Faults History refreshed itself empty -- and since a
     * device fault's hold is permanent for the session, that fault could never be viewed in
     * the session in which it happened. Only a missing database makes a query impossible. */
    private boolean databaseUnavailable() {
        return dao == null;
    }

    public IpBatchManagerModel() {
        this.autoActivate = Boolean.getBoolean("ip.autoActivate");
        Dao daoRef = null;
        POS posRef = null;
        BatchManager batchManagerRef = null;
        DigitalTwinAssembler assemblerRef = null;
        DeviationDetector detectorRef = null;
        try {
            String dbPath = System.getProperty("ip.database", "build/ip-" + UUID.randomUUID() + ".db");
            Connection conn = Db.open(dbPath, "sql/schema.sql");
            daoRef = new Dao(conn);
            posRef = new POS(daoRef);
            assemblerRef = new DigitalTwinAssembler(daoRef, "unloader");
            detectorRef = new DeviationDetector(daoRef);
            seedDefaultRecipes(daoRef);
            printRecipeCatalog(daoRef);
            final Dao daoForLink = daoRef;
            batchManagerRef = new BatchManager(daoRef, new BatchManager.CoordinatorLink() {
                public void activateBatch(int batchId, int recipeId, String productId, int totalQuantity) {
                    onActivateBatch(daoForLink, batchId, recipeId, productId, totalQuantity);
                }
            });
            System.out.println("[IpBatchManager] Database ready: " + dbPath);
        } catch (SQLException failure) {
            System.err.println("[IpBatchManager] Could not open database: " + failure.getMessage());
            halted = true;
        }
        this.dao = daoRef;
        this.pos = posRef;
        this.batchManager = batchManagerRef;
        this.assembler = assemblerRef;
        this.detector = detectorRef;

        SharedConsole.ensureStarted();
        if (Boolean.getBoolean("ip.gui")) {
            // One Swing GUI for everything IP/POS-side (Eric's own EabsDashboardPanel is a
            // separate, untouched window) replaces the console order-entry loop -- SharedConsole
            // still starts, since SafetyMonitorModel's hazard/clear/reset commands still read
            // from it when that CD is present. Constructed in-process, same pattern as Eric's
            // dashboard: the frame holds a direct reference to this already-running model and
            // calls straight into its methods, one Dao/POS/BatchManager instance, one database,
            // shared by every tab.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() { new com.g7.ip.gui.IpGuiFrame(IpBatchManagerModel.this).setVisible(true); }
            });
        } else {
            Thread reader = new Thread(new Runnable() {
                public void run() { readLoop(); }
            }, "ip-batch-manager-console");
            reader.setDaemon(true);
            reader.start();
        }

        // Drains TwinEventBus -- the real per-bottle events IntegratedCoordinator publishes
        // as it runs -- and feeds them to the Digital Twin Assembler, entirely on the DB
        // worker thread (IP report Section 4: assemble the twin from the GP's real signals,
        // never on a SystemJ-facing thread).
        Thread twinConsumer = new Thread(new Runnable() {
            public void run() { twinConsumerLoop(); }
        }, "ip-twin-consumer");
        twinConsumer.setDaemon(true);
        twinConsumer.start();

        // Power-loss / abrupt-interruption recovery (IP report Section 7) -- must run before
        // triggerBatchIfIdle() below, so a stale RUNNING batch from a killed previous process
        // is reconciled before anything decides whether to activate a new one.
        recoverIncompleteBatchesIfAny();

        // "On startup" per POS_BatchManager_Spec.md Section 4 -- picks up any orders left
        // PENDING from a previous run if -Dip.database points at a reused persistent file.
        // A no-op on the (default) fresh database. Enqueued like every other trigger, so it
        // runs on the DB worker thread rather than blocking the constructor's own caller
        // (the SystemJ reaction that does emit stateModel(new IpBatchManagerModel())).
        triggerBatchIfIdle();
    }

    /** Power-loss / abrupt-interruption recovery (IP report Section 7): "the system treats
     * any bottle without a completed terminal event in BottleEvents as faulty upon restart,
     * discards it, and resumes from the next bottle in the active batch." Any batch found
     * RUNNING here was left by a PREVIOUS process -- this runs once, at construction, before
     * this run has activated anything of its own, so RUNNING cannot yet mean "in flight in
     * this process." Runs entirely on the DB worker thread like every other database access. */
    private void recoverIncompleteBatchesIfAny() {
        if (halted) { return; }
        dbWorker.submit(new Runnable() {
            public void run() {
                try {
                    for (int batchId : dao.queryRunningBatchIds()) {
                        int abortedCount = closeBatchAsFault(batchId);
                        System.out.println("[IpBatchManager] Recovered batch " + batchId + " left RUNNING by a previous, "
                                + "abruptly-ended process: " + abortedCount + " incomplete bottle(s) marked ABORTED, "
                                + "batch closed as FAULT.");
                    }
                } catch (SQLException failure) {
                    System.err.println("[IpBatchManager] Database error during startup recovery: " + failure.getMessage());
                }
            }
        });
    }

    /** Marks every bottle in this batch with no terminal DONE at the unloader as ABORTED and
     * closes the batch itself as FAULT. Shared by the startup power-loss recovery check above
     * and live in-session FAULT handling below -- the same reconciliation, just triggered by
     * "a previous process died" vs. "this process's own coordinator just went on HOLD". Without
     * the live case, a batch a FAULT interrupts mid-session stayed status='RUNNING' in the
     * database until the next process restart, which is what made Track Order get stuck
     * showing "ADMITTED - IN PRODUCTION" forever for any order in that batch: its batchStatus
     * never became FAULT, so applyOrderTrackResult() never had a reason to stop polling it.
     * Must already be running on the DB worker thread; returns the number of bottles aborted. */
    private int closeBatchAsFault(int batchId) throws SQLException {
        List<String> incomplete = dao.queryIncompleteBottlesInBatch(batchId, "unloader");
        for (String bottleId : incomplete) {
            dao.markBottleAborted(bottleId);
        }
        dao.markBatchFault(batchId);
        return incomplete.size();
    }

    /** Recipes are a pre-existing catalog in the real design (POS validates against them,
     * never creates them) -- but a fresh database starts with none, which would reject every
     * order. Seeds two convenience recipes for interactive testing. Runs synchronously in the
     * constructor, before any other thread can see this object -- unlike every other database
     * access below, there is no concurrency risk to hand off to the DB worker for. Safe to
     * call on every run because the default ip.database path is a fresh, uniquely-named file
     * each time; if -Dip.database points at a persistent file reused across runs, this will
     * add duplicate rows each run (findRecipeIdForProduct always picks the newest, so this is
     * harmless to behaviour, just untidy). */
    private void seedDefaultRecipes(Dao dao) throws SQLException {
        int recipeX = dao.insertRecipe("PRODUCT_X", 0.60, 0.40, "500ml");
        int recipeY = dao.insertRecipe("PRODUCT_Y", 0.50, 0.50, "500ml");
        System.out.println("[IpBatchManager] Seeded default recipes for testing: "
                + "recipe_id=" + recipeX + " (PRODUCT_X, 60/40, 500ml), "
                + "recipe_id=" + recipeY + " (PRODUCT_Y, 50/50, 500ml)");
    }

    /** Prints the full recipe catalog so an order can pick "1, 2, 3, 4..." by id instead of
     * having to already know one. Runs synchronously at construction time only (same
     * justification as seedDefaultRecipes() above); printRecipeCatalog(dao) is called again,
     * asynchronously on the DB worker, every time submitOrder() defines a new custom recipe,
     * so the printed catalog never goes stale for the rest of the session. */
    private void printRecipeCatalog(Dao dao) throws SQLException {
        System.out.println("[IpBatchManager] Recipes on file:");
        for (Dao.RecipeSummary recipe : dao.listRecipes()) {
            System.out.println("  " + recipe.recipeId + ": " + recipe.productId + " "
                    + Math.round(recipe.liquidA * 100) + "%/" + Math.round(recipe.liquidB * 100)
                    + "% (" + recipe.bottleType + ")");
        }
    }

    private void readLoop() {
        while (!halted) {
            System.out.println(autoActivate
                    ? "[IpBatchManager] Enter next purchase order -- it activates its own batch immediately (server validates; bad values come back Rejected):"
                    : "[IpBatchManager] Enter next purchase order, or 'go' to activate now (server validates; bad values come back Rejected):");
            String customerPo = readField(autoActivate ? "  customer_po [A-Za-z0-9_.-]{1,60}: " : "  customer_po [A-Za-z0-9_.-]{1,60}, or 'go': ");
            if (customerPo == null) {
                System.out.println("[IpBatchManager] Input closed; no more orders will be submitted.");
                return;
            }
            if (customerPo.equalsIgnoreCase("go")) {
                triggerBatchIfIdle();
                continue;
            }
            String customerId = readField("  customer_id: ");
            String productId = readField("  product_id: ");
            String quantityText = readField("  quantity [>0]: ");
            String bottleSpec = readField("  bottle_spec: ");
            String recipeIdText = readField("  recipe_id [pick a number from the catalog above, or 'new' to define one]: ");
            if (customerId == null || productId == null || quantityText == null
                    || bottleSpec == null || recipeIdText == null) {
                System.out.println("[IpBatchManager] Input closed; no more orders will be submitted.");
                return;
            }
            String doseAText = null, doseBText = null;
            if (recipeIdText.equalsIgnoreCase("new")) {
                doseAText = readField("  new recipe doseA [% 0-100]: ");
                doseBText = readField("  new recipe doseB [% 0-100, doseA+doseB in 1-100]: ");
                if (doseAText == null || doseBText == null) {
                    System.out.println("[IpBatchManager] Input closed; no more orders will be submitted.");
                    return;
                }
            }
            submitOrder(customerPo, customerId, productId, quantityText, bottleSpec, recipeIdText, doseAText, doseBText);
        }
        System.out.println("[IpBatchManager] System is in FAULT/HOLD -- no further orders can be submitted this session.");
    }

    private String readField(String label) {
        System.out.print(label);
        System.out.flush();
        try {
            String line = SharedConsole.orderLines().take();
            return SharedConsole.EOF_MARKER.equals(line) ? null : line.trim();
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /** Validates the two integer fields inline (cheap, no database access) so a typo gets an
     * immediate console response; everything that touches the database is handed to the DB
     * worker and this method returns without blocking on it. */
    private void submitOrder(final String customerPo, final String customerId, final String productId,
            String quantityText, final String bottleSpec, String recipeIdText, String doseAText, String doseBText) {
        if (halted) { return; }
        final int quantity;
        final boolean newRecipe = recipeIdText.equalsIgnoreCase("new");
        final int recipeId;   // meaningful only when !newRecipe
        final int doseA, doseB; // meaningful only when newRecipe
        try {
            quantity = Integer.parseInt(quantityText);
            if (newRecipe) {
                recipeId = 0;
                doseA = Integer.parseInt(doseAText);
                doseB = Integer.parseInt(doseBText);
                if (doseA < 0 || doseB < 0 || doseA + doseB < 1 || doseA + doseB > 100) {
                    System.out.println("[IpBatchManager] Rejected(doseA/doseB must each be >= 0 and sum to 1-100)");
                    return;
                }
            } else {
                recipeId = Integer.parseInt(recipeIdText);
                doseA = 0; doseB = 0;
            }
        } catch (NumberFormatException invalid) {
            System.out.println("[IpBatchManager] Rejected(quantity/recipe_id/doseA/doseB must be integers)");
            return;
        }
        dbWorker.submit(new Runnable() {
            public void run() {
                try {
                    int resolvedRecipeId = recipeId;
                    if (newRecipe) {
                        try {
                            Integer existing = dao.findMatchingRecipe(productId, doseA / 100.0, doseB / 100.0, bottleSpec);
                            if (existing != null) {
                                resolvedRecipeId = existing;
                                System.out.println("[IpBatchManager] Reusing existing recipe_id=" + resolvedRecipeId + " (" + productId
                                        + " " + doseA + "%/" + doseB + "%, " + bottleSpec + ") -- identical recipe already on file.");
                            } else {
                                resolvedRecipeId = dao.insertRecipe(productId, doseA / 100.0, doseB / 100.0, bottleSpec);
                                System.out.println("[IpBatchManager] Created recipe_id=" + resolvedRecipeId + " (" + productId
                                        + " " + doseA + "%/" + doseB + "%, " + bottleSpec + ") -- traceable in Recipes from now on.");
                            }
                        } catch (SQLException failure) {
                            System.out.println("[IpBatchManager] Rejected(could not resolve recipe: " + failure.getMessage() + ")");
                            return;
                        }
                        try {
                            printRecipeCatalog(dao);
                        } catch (SQLException ignored) {
                            // Catalog re-print is a convenience only; the order submission below still proceeds.
                        }
                    }
                    POS.SubmitResult result = pos.submitOrder(customerPo, customerId, productId, quantity, bottleSpec, resolvedRecipeId);
                    System.out.println("[IpBatchManager] " + result
                            + (result.accepted
                                    ? (autoActivate ? " -- activating immediately." : " -- stored as PENDING; type 'go' once you're done entering orders for this batch.")
                                    : ""));
                    if (result.accepted && autoActivate) {
                        triggerBatchIfIdle();
                    }
                } catch (SQLException failure) {
                    System.out.println("[IpBatchManager] Database error while submitting order: " + failure.getMessage());
                }
            }
        });
    }

    /** Explicit trigger (console 'go', or once at startup) -- deliberately NOT called from
     * submitOrder() itself, so several PENDING orders for the same product can accumulate
     * and be merged into one batch (POS_BatchManager_Spec.md Section 4/7 cross-order
     * scheduling) instead of the first order always being activated alone before a second
     * one is even entered. Enqueued on the DB worker like every other database-touching
     * operation; the in-flight check happens there too, so it is read consistently with
     * whatever the worker itself last set it to. */
    private void triggerBatchIfIdle() {
        if (halted) { return; }
        dbWorker.submit(new Runnable() {
            public void run() {
                boolean inFlight;
                synchronized (IpBatchManagerModel.this) {
                    inFlight = halted || awaitingResultFor != null;
                }
                if (inFlight) {
                    System.out.println("[IpBatchManager] A batch is already in flight -- pending orders will be picked up once it drains.");
                    return;
                }
                try {
                    batchManager.start();
                } catch (SQLException failure) {
                    System.out.println("[IpBatchManager] Database error while checking pending demand: " + failure.getMessage());
                }
            }
        });
    }

    /** Reports one submitted cart line's outcome. Always invoked on the DB worker thread --
     * a caller touching Swing components (PosGuiFrame) must hop to the EDT itself, the same
     * contract as Eric's own VisualizationBridge.StateListener. */
    public interface CartLineResult {
        void onComplete(boolean accepted, boolean newFormulation, String message);
    }

    /** Reports one PO lookup's outcome (null = no such order). Same DB-worker-thread contract
     * as CartLineResult. */
    public interface OrderLookupResult {
        void onComplete(Dao.OrderStatus status);
    }

    /** POS GUI entry point for the "Place Order" tab (one call per cart line). Unlike
     * submitOrder()'s console path, the caller never picks a product_id -- it is derived
     * deterministically from the exact (bottleSpec, doseA, doseB) combination via
     * deriveProductId(), so two lines with the identical capacity+mix always resolve to the
     * same product_id. That also keeps BatchManager's existing "group PENDING orders by
     * product_id" query correct now that capacity/ratio are freely combinable: grouping by
     * product_id is grouping by exact formulation. Validates the cheap fields inline (same
     * reasoning as submitOrder() above) before handing the database work to the DB worker. */
    public void submitCartLine(final String customerPo, final String customerId, final String bottleSpec,
            final int doseA, final int doseB, final int quantity, final CartLineResult onResult) {
        if (halted) {
            onResult.onComplete(false, false, "Rejected(system is in FAULT/HOLD)");
            return;
        }
        if (doseA < 0 || doseB < 0 || doseA + doseB < 1 || doseA + doseB > 100) {
            onResult.onComplete(false, false, "Rejected(doseA/doseB must each be >= 0 and sum to 1-100)");
            return;
        }
        if (quantity <= 0) {
            onResult.onComplete(false, false, "Rejected(quantity must be > 0)");
            return;
        }
        final String productId = deriveProductId(bottleSpec, doseA, doseB);
        dbWorker.submit(new Runnable() {
            public void run() {
                try {
                    Integer existing = dao.findMatchingRecipe(productId, doseA / 100.0, doseB / 100.0, bottleSpec);
                    boolean isNew = existing == null;
                    int recipeId = isNew ? dao.insertRecipe(productId, doseA / 100.0, doseB / 100.0, bottleSpec) : existing;
                    POS.SubmitResult result = pos.submitOrder(customerPo, customerId, productId, quantity, bottleSpec, recipeId);
                    if (result.accepted && autoActivate) {
                        triggerBatchIfIdle();
                    }
                    onResult.onComplete(result.accepted, isNew, result.toString());
                } catch (SQLException failure) {
                    onResult.onComplete(false, false, "Rejected(database error: " + failure.getMessage() + ")");
                }
            }
        });
    }

    /** POS GUI entry point for the "Track Order" tab. */
    public void lookupOrder(final String customerPo, final OrderLookupResult onResult) {
        if (databaseUnavailable()) {
            onResult.onComplete(null);
            return;
        }
        dbWorker.submit(new Runnable() {
            public void run() {
                try {
                    onResult.onComplete(dao.findOrderStatus(customerPo));
                } catch (SQLException failure) {
                    onResult.onComplete(null);
                }
            }
        });
    }

    /** Reports the highest PO sequence number already on file for the given prefix. */
    public interface PoSequenceResult {
        void onComplete(int highestSequence);
    }

    /** GUI startup helper: PosGuiFrame generates PO references client-side as
     * prefix + zero-padded sequence, but a fixed, reused -Dip.database=...db file persists
     * across JVM restarts while an in-memory AtomicInteger does not -- so on its own, a fresh
     * counter starting at 1 will eventually re-issue a customer_po the database already has,
     * which fails the UNIQUE constraint on submit. Scanning existing customer_po values for
     * this prefix and returning the highest suffix found (0 if none) lets the caller seed its
     * counter to continue from there instead of restarting at 1 every launch. */
    public void highestPoSequence(final String prefix, final PoSequenceResult onResult) {
        if (databaseUnavailable()) {
            onResult.onComplete(0);
            return;
        }
        dbWorker.submit(new Runnable() {
            public void run() {
                int highest = 0;
                try {
                    for (String po : dao.listCustomerPos()) {
                        if (po == null || !po.startsWith(prefix)) { continue; }
                        try {
                            int n = Integer.parseInt(po.substring(prefix.length()));
                            if (n > highest) { highest = n; }
                        } catch (NumberFormatException ignored) {
                            // A customer_po under this prefix that isn't one of ours -- skip it.
                        }
                    }
                } catch (SQLException ignored) {
                    // Falls through with whatever was found so far (0 on total failure); the
                    // caller's counter still starts somewhere sane rather than blocking forever.
                }
                onResult.onComplete(highest);
            }
        });
    }

    /** Reports one bottle's full history (null if unknown) plus a deviation check against
     * EXPECTED_STATIONS -- the same check handleTwinEvent() already runs automatically the
     * moment a bottle reaches the unloader, re-run here on demand for the traceability GUI. */
    public interface BottleHistoryResult {
        void onComplete(Dao.BottleHistory history, DeviationDetector.Result deviation);
    }

    /** Factory GUI entry point (bottle traceability tab). */
    public void lookupBottleHistory(final String bottleId, final BottleHistoryResult onResult) {
        if (databaseUnavailable()) {
            onResult.onComplete(null, null);
            return;
        }
        dbWorker.submit(new Runnable() {
            public void run() {
                try {
                    Dao.BottleHistory history = dao.findBottleHistory(bottleId);
                    DeviationDetector.Result deviation = history == null
                            ? null : detector.checkStationSequence(bottleId, EXPECTED_STATIONS);
                    onResult.onComplete(history, deviation);
                } catch (SQLException failure) {
                    onResult.onComplete(null, null);
                }
            }
        });
    }

    /** Reports the most recently active bottle ids, newest first. */
    public interface RecentBottlesResult {
        void onComplete(java.util.List<String> bottleIds);
    }

    /** Factory GUI entry point: populates the "recent bottles" picker. */
    public void recentBottleIds(final int limit, final RecentBottlesResult onResult) {
        if (databaseUnavailable()) {
            onResult.onComplete(java.util.Collections.<String>emptyList());
            return;
        }
        dbWorker.submit(new Runnable() {
            public void run() {
                try {
                    onResult.onComplete(dao.recentBottleIds(limit));
                } catch (SQLException failure) {
                    onResult.onComplete(java.util.Collections.<String>emptyList());
                }
            }
        });
    }

    /** Reports every fault on file, most recent first. */
    public interface FaultsResult {
        void onComplete(java.util.List<Dao.FaultRow> faults);
    }

    /** GUI entry point (Faults History tab). */
    public void listFaults(final FaultsResult onResult) {
        if (databaseUnavailable()) {
            onResult.onComplete(java.util.Collections.<Dao.FaultRow>emptyList());
            return;
        }
        dbWorker.submit(new Runnable() {
            public void run() {
                try {
                    onResult.onComplete(dao.listFaults());
                } catch (SQLException failure) {
                    onResult.onComplete(java.util.Collections.<Dao.FaultRow>emptyList());
                }
            }
        });
    }

    /** Reports whether a fault was successfully marked resolved. */
    public interface ResolveFaultResult {
        void onComplete(boolean success, String message);
    }

    /** GUI entry point (Faults History tab, "Resolve" action). */
    public void resolveFault(final int faultId, final String resolution, final ResolveFaultResult onResult) {
        if (databaseUnavailable()) {
            onResult.onComplete(false, "Database unavailable");
            return;
        }
        dbWorker.submit(new Runnable() {
            public void run() {
                try {
                    dao.resolveFault(faultId, resolution);
                    onResult.onComplete(true, "Resolved");
                } catch (SQLException failure) {
                    onResult.onComplete(false, failure.getMessage());
                }
            }
        });
    }

    /** GUI status indicator: true while no batch can be sent (a fault -- machine or safety --
     * is holding the coordinator, per the last DRAINED/REJECTED/FAULT/RECOVERED result seen).
     * Pure in-memory read, no database access. */
    public boolean isHalted() {
        return halted;
    }

    /** GUI entry point for a "Reset" button: forwards to whichever SafetyMonitorModel is
     * attached to SharedConsole, exactly as typing 'reset' at the console already does (see
     * SafetyMonitorModel.triggerReset() for why this is deliberately never automatic).
     * Returns false without doing anything if no SafetyMonitorCD is present in this profile --
     * every XML wiring that has one constructs it well before this could ever be reachable
     * from a GUI click, so null here means "not wired in", not "not ready yet". */
    public boolean triggerSafetyReset() {
        SafetyMonitorModel safety = SharedConsole.safety();
        if (safety == null) { return false; }
        safety.triggerReset();
        return true;
    }

    /** GUI entry point for a "Clear Hazard" button: forwards to whichever SafetyMonitorModel
     * is attached to SharedConsole, exactly as typing 'clear' at the console already does.
     * Manual, not automatic -- lets an operator confirm the condition is back to normal
     * without waiting for HazardSensorSimulator's own timer, without changing what the sensor
     * itself reports. Same "not wired in" contract as triggerSafetyReset(). */
    public boolean triggerSafetyClear() {
        SafetyMonitorModel safety = SharedConsole.safety();
        if (safety == null) { return false; }
        safety.triggerClear();
        return true;
    }

    /** GUI status indicator: true while the attached SafetyMonitorModel currently reports an
     * active hazard. False (not unsafe) if no SafetyMonitorCD is present in this profile. */
    public boolean isSafetyHazardActive() {
        SafetyMonitorModel safety = SharedConsole.safety();
        return safety != null && safety.isUnsafe();
    }

    /** product_id is customer-invisible in the GUI flow (brief 4.2 defines a product BY its
     * bottle size and liquid specification, not the other way round) -- this derives a stable
     * id from exactly those two things, so resubmitting the identical capacity+mix always
     * lands on the identical product_id/recipe instead of minting a duplicate. */
    private static String deriveProductId(String bottleSpec, int doseA, int doseB) {
        String capacity = bottleSpec.trim().toUpperCase(java.util.Locale.ROOT).replaceAll("[^A-Z0-9]", "");
        return "FORM-" + capacity + "-" + doseA + "-" + doseB;
    }

    /** Drains TwinEventBus one event at a time and hands each to the DB worker -- this loop
     * itself never touches the database, only IntegratedCoordinator's already-fast, in-memory
     * TwinEventBus.take(). Runs for the lifetime of the process; there is nothing to stop it
     * for, matching every other background loop in this class. */
    private void twinConsumerLoop() {
        while (true) {
            final TwinEventBus.Event event;
            try {
                event = TwinEventBus.take();
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                return;
            }
            if (halted) { continue; }
            dbWorker.submit(new Runnable() {
                public void run() { handleTwinEvent(event); }
            });
        }
    }

    /** Runs on the DB worker thread. Admission must be recorded before any transition for the
     * same workpieceId is processed -- true here because IntegratedCoordinator only ever
     * publishes a workpiece's admission event, then later transitions, in that order, and
     * TwinEventBus is a single FIFO queue drained by one consumer. */
    private void handleTwinEvent(TwinEventBus.Event event) {
        try {
            if (event.admission) {
                String bottleId = assembler.admitBottle(event.workpieceId, event.batchId, event.recipeId, event.productId);
                System.out.println("[DigitalTwin] Admitted " + event.workpieceId + " as " + bottleId + " (batch " + event.batchId + ")");
                return;
            }
            assembler.onFieldChange(new TrackerFieldChange(event.workpieceId, event.batchId, event.location, event.location, event.status));
            if ("unloader".equals(event.location) && "DONE".equals(event.status)) {
                String bottleId = assembler.bottleIdFor(event.workpieceId);
                DeviationDetector.Result result = detector.checkStationSequence(bottleId, EXPECTED_STATIONS);
                System.out.println("[DigitalTwin] " + bottleId + " (" + event.workpieceId + "): deviated=" + result.deviated + " -> " + result.reason);
            }
        } catch (SQLException failure) {
            System.err.println("[DigitalTwin] Database error handling event for " + event.workpieceId + ": " + failure.getMessage());
        } catch (IllegalStateException notAdmitted) {
            System.err.println("[DigitalTwin] " + notAdmitted.getMessage());
        }
    }

    /** com.g7.ip.BatchManager.CoordinatorLink callback -- resolves the batch's recipe into
     * concrete doses and stashes the ACTIVATE frame for nextActivate() to pick up. Called
     * synchronously from within batchManager.start()/onBatchDrained(), which (since Stage 2)
     * only ever run as a Runnable submitted to the DB worker -- so this always executes on
     * the DB worker thread too, never on the console thread or the SystemJ tick thread.
     * The JDBC calls run with no lock held at all, so nextActivate()/resultReceived() on the
     * SystemJ tick thread can never be made to wait on a database round trip -- only the
     * final two-field handoff is synchronized, and that is pure in-memory assignment. */
    private void onActivateBatch(Dao dao, int batchId, int recipeId, String productId, int totalQuantity) {
        Dao.RecipeInfo recipe;
        Integer orderId;
        try {
            recipe = dao.getRecipe(recipeId);
            if (recipe == null) {
                System.err.println("[IpBatchManager] Batch " + batchId + " references unknown recipe " + recipeId + " -- cannot activate.");
                return;
            }
            orderId = dao.firstOrderIdForBatch(batchId);
        } catch (SQLException failure) {
            System.err.println("[IpBatchManager] Could not resolve recipe for batch " + batchId + ": " + failure.getMessage());
            return;
        }
        int doseA = (int) Math.round(recipe.liquidA * 100);
        int doseB = (int) Math.round(recipe.liquidB * 100);
        String orderLabel = orderId == null ? ("BATCH-" + batchId) : String.valueOf(orderId);
        String frame = "ACTIVATE|" + batchId + "|" + recipeId + "|" + productId + "|"
                + totalQuantity + "|" + doseA + "|" + doseB + "|" + orderLabel;
        synchronized (this) {
            pendingFrame = frame;
            awaitingResultFor = String.valueOf(batchId);
        }
    }

    /** Non-blocking: called once per SystemJ tick, same contract as BatchManagerModel. Pure
     * in-memory field access -- never touches the database, so this needs no DB worker
     * involvement regardless of Stage 1 or Stage 2. */
    public synchronized String nextActivate() {
        if (halted || pendingFrame == null) { return ""; }
        String frame = pendingFrame;
        pendingFrame = null;
        return frame;
    }

    /** Called directly from the SystemJ tick thread (ip_batch_manager.sysj's
     * batchResultIn reaction). Stage 2: the only database-touching branch (DRAINED) is
     * handed to the DB worker and this method returns immediately either way, so the
     * SystemJ reaction that calls it is never blocked on a JDBC round trip. */
    public synchronized void resultReceived(String result) {
        awaitingResultFor = null;
        if (result.startsWith("DRAINED|")) {
            System.out.println("[IpBatchManager] " + result + " -- checking for more pending demand.");
            String[] f = result.split("\\|", -1);
            int parsedBatchId = -1;
            try {
                parsedBatchId = f.length > 1 ? Integer.parseInt(f[1]) : -1;
            } catch (NumberFormatException ignored) {
                // Falls through with -1; skips completeBatch() below rather than closing the wrong row.
            }
            final int drainedBatchId = parsedBatchId;
            dbWorker.submit(new Runnable() {
                public void run() {
                    try {
                        if (drainedBatchId >= 0) {
                            dao.completeBatch(drainedBatchId);
                        }
                        batchManager.onBatchDrained(drainedBatchId);
                    } catch (SQLException failure) {
                        System.err.println("[IpBatchManager] Could not process BatchDrained: " + failure.getMessage());
                    }
                }
            });
        } else if (result.startsWith("REJECTED|")) {
            System.out.println("[IpBatchManager] " + result + " -- unexpected: POS should already have validated this batch.");
        } else if (result.startsWith("FAULT|")) {
            halted = true;
            System.out.println("[IpBatchManager] " + result + " -- coordinator is HOLDING. No further batches will be sent this session.");
            String[] f = result.split("\\|", -1);
            if (f.length > 1 && !"NONE".equals(f[1])) {
                try {
                    final int faultedBatchId = Integer.parseInt(f[1]);
                    dbWorker.submit(new Runnable() {
                        public void run() {
                            try {
                                int abortedCount = closeBatchAsFault(faultedBatchId);
                                System.out.println("[IpBatchManager] Batch " + faultedBatchId + " closed as FAULT: "
                                        + abortedCount + " incomplete bottle(s) marked ABORTED.");
                            } catch (SQLException failure) {
                                System.err.println("[IpBatchManager] Could not close faulted batch " + faultedBatchId
                                        + ": " + failure.getMessage());
                            }
                        }
                    });
                } catch (NumberFormatException ignored) {
                    // Non-numeric batch id (the console-typed GP-only profile's own faults) --
                    // nothing to close in this database.
                }
            }
        } else if (result.startsWith("RECOVERED|")) {
            // Sent once by IntegratedCoordinator.reset() (the safety-specific recovery path --
            // never for a machine fault, which stays permanently HOLDING). halted was latched
            // true on the earlier FAULT| and, before this, had no way to ever clear again --
            // any orders placed while halted are still sitting PENDING, so check for them now
            // instead of waiting for the operator to resubmit something.
            halted = false;
            System.out.println("[IpBatchManager] " + result + " -- coordinator recovered from safety HOLD, resuming order acceptance.");
            triggerBatchIfIdle();
        } else {
            System.out.println("[IpBatchManager] Unexpected result: " + result);
        }
    }

    /** Nothing time-based yet; kept only so IpBatchManagerCD's shape matches
     * BatchManagerCD/IntegratedCoordinator's proven tick()-driven pattern exactly. */
    public synchronized void tick() {
    }
}
