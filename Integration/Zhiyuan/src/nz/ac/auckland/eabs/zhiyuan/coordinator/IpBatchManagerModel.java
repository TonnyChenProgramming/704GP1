package nz.ac.auckland.eabs.zhiyuan.coordinator;

import com.g7.ip.BatchManager;
import com.g7.ip.Dao;
import com.g7.ip.Db;
import com.g7.ip.POS;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Real, database-backed batch source for CoordinatorCD.activateBatchIn/batchDrainedOut --
 * the IP's Stage-1 persistence layer (com.g7.ip.*), wired directly (synchronous JDBC calls,
 * per the IP report Section 10 first stage) instead of BatchManagerModel's raw console
 * strings. A customer purchase order is validated and written to SQLite by POS; com.g7.ip.
 * BatchManager consolidates all pending orders for one product (possibly from several
 * customers) into a single batch; this class resolves that batch's recipe into concrete
 * doses and sends the SAME ACTIVATE protocol frame BatchManagerCD already sends, so
 * CoordinatorCD itself needs no changes.
 *
 * The ACTIVATE frame still carries one orderId label, because Eric's Tracker requires one --
 * but that label is display-only. Real per-bottle order attribution is entirely
 * Dao#nextUnfulfilledOrderForBatch's job on the IP side and does not depend on it, so
 * cross-order merging (several orders inside one batch) is unaffected by the Coordinator/
 * Tracker only ever seeing a single representative orderId.
 */
public final class IpBatchManagerModel {
    private final Dao dao;
    private final POS pos;
    private final BatchManager batchManager;

    private String pendingFrame = null;
    private String awaitingResultFor = null; // null = no batch currently in flight
    private volatile boolean halted = false;

    public IpBatchManagerModel() {
        Dao daoRef = null;
        POS posRef = null;
        BatchManager batchManagerRef = null;
        try {
            String dbPath = System.getProperty("ip.database", "build/ip-" + UUID.randomUUID() + ".db");
            Connection conn = Db.open(dbPath, "sql/schema.sql");
            daoRef = new Dao(conn);
            posRef = new POS(daoRef);
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

        SharedConsole.ensureStarted();
        Thread reader = new Thread(new Runnable() {
            public void run() { readLoop(); }
        }, "ip-batch-manager-console");
        reader.setDaemon(true);
        reader.start();
    }

    /** Recipes are a pre-existing catalog in the real design (POS validates against them,
     * never creates them) -- but a fresh database starts with none, which would reject every
     * order. Seeds two convenience recipes for interactive testing. Safe to call on every
     * run because the default ip.database path is a fresh, uniquely-named file each time; if
     * -Dip.database points at a persistent file reused across runs, this will add duplicate
     * rows each run (findRecipeIdForProduct always picks the newest, so this is harmless to
     * behaviour, just untidy). */
    private void seedDefaultRecipes(Dao dao) throws SQLException {
        int recipeX = dao.insertRecipe("PRODUCT_X", 0.60, 0.40, "500ml");
        int recipeY = dao.insertRecipe("PRODUCT_Y", 0.50, 0.50, "500ml");
        System.out.println("[IpBatchManager] Seeded default recipes for testing: "
                + "recipe_id=" + recipeX + " (PRODUCT_X, 60/40, 500ml), "
                + "recipe_id=" + recipeY + " (PRODUCT_Y, 50/50, 500ml)");
    }

    private void readLoop() {
        while (!halted) {
            System.out.println("[IpBatchManager] Enter next purchase order (server validates; bad values come back Rejected):");
            String customerPo = readField("  customer_po [A-Za-z0-9_.-]{1,60}: ");
            String customerId = readField("  customer_id: ");
            String productId = readField("  product_id: ");
            String quantityText = readField("  quantity [>0]: ");
            String bottleSpec = readField("  bottle_spec: ");
            String recipeIdText = readField("  recipe_id [int, must already exist in Recipes]: ");
            if (customerPo == null || customerId == null || productId == null || quantityText == null
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

    private synchronized void submitOrder(String customerPo, String customerId, String productId,
            String quantityText, String bottleSpec, String recipeIdText) {
        if (halted) { return; }
        int quantity;
        int recipeId;
        try {
            quantity = Integer.parseInt(quantityText);
            recipeId = Integer.parseInt(recipeIdText);
        } catch (NumberFormatException invalid) {
            System.out.println("[IpBatchManager] Rejected(quantity and recipe_id must be integers)");
            return;
        }
        try {
            POS.SubmitResult result = pos.submitOrder(customerPo, customerId, productId, quantity, bottleSpec, recipeId);
            System.out.println("[IpBatchManager] " + result);
            if (result.accepted && awaitingResultFor == null) {
                batchManager.start();
            }
        } catch (SQLException failure) {
            System.out.println("[IpBatchManager] Database error while submitting order: " + failure.getMessage());
        }
    }

    /** com.g7.ip.BatchManager.CoordinatorLink callback -- resolves the batch's recipe into
     * concrete doses and stashes the ACTIVATE frame for nextActivate() to pick up. Always
     * called synchronously from within a submitOrder()/resultReceived() call on the console
     * thread or the SystemJ tick thread, never concurrently (this instance is the only
     * caller of batchManager.start()/onBatchDrained()). */
    private synchronized void onActivateBatch(Dao dao, int batchId, int recipeId, String productId, int totalQuantity) {
        try {
            Dao.RecipeInfo recipe = dao.getRecipe(recipeId);
            if (recipe == null) {
                System.err.println("[IpBatchManager] Batch " + batchId + " references unknown recipe " + recipeId + " -- cannot activate.");
                return;
            }
            int doseA = (int) Math.round(recipe.liquidA * 100);
            int doseB = (int) Math.round(recipe.liquidB * 100);
            Integer orderId = dao.firstOrderIdForBatch(batchId);
            String orderLabel = orderId == null ? ("BATCH-" + batchId) : String.valueOf(orderId);
            pendingFrame = "ACTIVATE|" + batchId + "|" + recipeId + "|" + productId + "|"
                    + totalQuantity + "|" + doseA + "|" + doseB + "|" + orderLabel;
            awaitingResultFor = String.valueOf(batchId);
        } catch (SQLException failure) {
            System.err.println("[IpBatchManager] Could not resolve recipe for batch " + batchId + ": " + failure.getMessage());
        }
    }

    /** Non-blocking: called once per SystemJ tick, same contract as BatchManagerModel. */
    public synchronized String nextActivate() {
        if (halted || pendingFrame == null) { return ""; }
        String frame = pendingFrame;
        pendingFrame = null;
        return frame;
    }

    public synchronized void resultReceived(String result) {
        awaitingResultFor = null;
        if (result.startsWith("DRAINED|")) {
            System.out.println("[IpBatchManager] " + result + " -- checking for more pending demand.");
            String[] f = result.split("\\|", -1);
            try {
                batchManager.onBatchDrained(f.length > 1 ? Integer.parseInt(f[1]) : -1);
            } catch (RuntimeException | SQLException failure) {
                System.err.println("[IpBatchManager] Could not process BatchDrained: " + failure.getMessage());
            }
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
