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
     * be compared against it (IP report Section 7, recipe-deviation detection). */
    private static final List<String> EXPECTED_STATIONS = Arrays.asList(
            "loader", "conveyor_in", "filler", "lid", "capper", "conveyor_out", "labeller", "unloader");

    private final Dao dao;
    private final POS pos;
    private final BatchManager batchManager;
    private final DigitalTwinAssembler assembler;
    private final DeviationDetector detector;
    private final DbWorker dbWorker = new DbWorker();

    private String pendingFrame = null;
    private String awaitingResultFor = null; // null = no batch currently in flight
    private volatile boolean halted = false;

    public IpBatchManagerModel() {
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
        Thread reader = new Thread(new Runnable() {
            public void run() { readLoop(); }
        }, "ip-batch-manager-console");
        reader.setDaemon(true);
        reader.start();

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
                        List<String> incomplete = dao.queryIncompleteBottlesInBatch(batchId, "unloader");
                        for (String bottleId : incomplete) {
                            dao.markBottleAborted(bottleId);
                        }
                        dao.markBatchFault(batchId);
                        System.out.println("[IpBatchManager] Recovered batch " + batchId + " left RUNNING by a previous, "
                                + "abruptly-ended process: " + incomplete.size() + " incomplete bottle(s) marked ABORTED, "
                                + "batch closed as FAULT.");
                    }
                } catch (SQLException failure) {
                    System.err.println("[IpBatchManager] Database error during startup recovery: " + failure.getMessage());
                }
            }
        });
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

    private void readLoop() {
        while (!halted) {
            System.out.println("[IpBatchManager] Enter next purchase order, or 'go' to activate now (server validates; bad values come back Rejected):");
            String customerPo = readField("  customer_po [A-Za-z0-9_.-]{1,60}, or 'go': ");
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
            String recipeIdText = readField("  recipe_id [int, must already exist in Recipes]: ");
            if (customerId == null || productId == null || quantityText == null
                    || bottleSpec == null || recipeIdText == null) {
                System.out.println("[IpBatchManager] Input closed; no more orders will be submitted.");
                return;
            }
            submitOrder(customerPo, customerId, productId, quantityText, bottleSpec, recipeIdText);
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
            String quantityText, final String bottleSpec, String recipeIdText) {
        if (halted) { return; }
        final int quantity;
        final int recipeId;
        try {
            quantity = Integer.parseInt(quantityText);
            recipeId = Integer.parseInt(recipeIdText);
        } catch (NumberFormatException invalid) {
            System.out.println("[IpBatchManager] Rejected(quantity and recipe_id must be integers)");
            return;
        }
        dbWorker.submit(new Runnable() {
            public void run() {
                try {
                    POS.SubmitResult result = pos.submitOrder(customerPo, customerId, productId, quantity, bottleSpec, recipeId);
                    System.out.println("[IpBatchManager] " + result
                            + (result.accepted ? " -- stored as PENDING; type 'go' once you're done entering orders for this batch." : ""));
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
        } else {
            System.out.println("[IpBatchManager] Unexpected result: " + result);
        }
    }

    /** Nothing time-based yet; kept only so IpBatchManagerCD's shape matches
     * BatchManagerCD/IntegratedCoordinator's proven tick()-driven pattern exactly. */
    public synchronized void tick() {
    }
}
