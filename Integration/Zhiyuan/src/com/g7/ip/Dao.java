package com.g7.ip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Synchronous DAO for the six-table schema (Appendix Tables 1-6 of the
 * IP Conceptual Design Report).
 *
 * Per the staged development plan (Section 10), THIS is the "first,
 * synchronous version" — it is called directly from Assembler/BatchManager
 * logic for now. It exists behind a narrow interface (this class) so that
 * Stage 2 can swap it for the asynchronous DB Worker (dbRequest /
 * dbResponseAck signal pair) without changing any calling code above it.
 */
public class Dao {

    private final Connection conn;

    public Dao(Connection conn) {
        this.conn = conn;
    }

    // ---------- Recipes (Table 1) ----------

    public int insertRecipe(String productId, double liquidA, double liquidB, String bottleType) throws SQLException {
        String sql = "INSERT INTO Recipes(product_id, liquid_a_proportion, liquid_b_proportion, bottle_type) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ps.setDouble(2, liquidA);
            ps.setDouble(3, liquidB);
            ps.setString(4, bottleType);
            ps.executeUpdate();
            return lastId(ps);
        }
    }

    /** Looks for a recipe already on file with the same composition, so operator-entered "new"
     * recipes reuse an identical existing row instead of piling up duplicates (product/doses/
     * bottle type differing only in typed formatting still counts as the same recipe: doses are
     * compared to the nearest whole percent, bottle type case/whitespace-insensitively). Returns
     * null if nothing matches, in which case the caller should insertRecipe(...) a new row. */
    public Integer findMatchingRecipe(String productId, double liquidA, double liquidB, String bottleType) throws SQLException {
        String sql = "SELECT recipe_id FROM Recipes WHERE product_id=? "
                + "AND ROUND(liquid_a_proportion*100)=ROUND(?*100) "
                + "AND ROUND(liquid_b_proportion*100)=ROUND(?*100) "
                + "AND LOWER(TRIM(bottle_type))=LOWER(TRIM(?)) "
                + "ORDER BY recipe_id LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ps.setDouble(2, liquidA);
            ps.setDouble(3, liquidB);
            ps.setString(4, bottleType);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        }
    }

    /** Recipe fields needed by POS validation, Batch Manager admission (POS_BatchManager_Spec.md),
     * and GP dose resolution: liquidA/liquidB are the raw 0-1 proportions from the schema, used by
     * the GP-side Coordinator adapter to compute the integer doseA/doseB the ACTIVATE frame carries. */
    public static class RecipeInfo {
        public final int recipeId;
        public final String productId;
        public final String bottleType;
        public final double liquidA;
        public final double liquidB;
        public RecipeInfo(int recipeId, String productId, String bottleType, double liquidA, double liquidB) {
            this.recipeId = recipeId;
            this.productId = productId;
            this.bottleType = bottleType;
            this.liquidA = liquidA;
            this.liquidB = liquidB;
        }
    }

    /** Used by POS to validate a submitted order's recipe/bottle_spec against what's on file,
     * and by the GP-side Coordinator adapter to resolve a recipe_id into concrete doses. */
    public RecipeInfo getRecipe(int recipeId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT recipe_id, product_id, bottle_type, liquid_a_proportion, liquid_b_proportion FROM Recipes WHERE recipe_id=?")) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new RecipeInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5));
            }
        }
    }

    /**
     * The recipe the Batch Manager should attach to a new batch for this product
     * (POS_BatchManager_Spec.md Section 4: {@code recipe_id = lookupRecipeFor(target_product)}).
     * If more than one recipe is on file for a product, the most recently added
     * one wins — Stage 1 does not yet support recipe versioning/selection.
     */
    public Integer findRecipeIdForProduct(String productId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT recipe_id FROM Recipes WHERE product_id=? ORDER BY recipe_id DESC LIMIT 1")) {
            ps.setString(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        }
    }

    /** One row of the recipe catalog, for printing a pick-list at order entry. */
    public static class RecipeSummary {
        public final int recipeId;
        public final String productId;
        public final double liquidA;
        public final double liquidB;
        public final String bottleType;
        public RecipeSummary(int recipeId, String productId, double liquidA, double liquidB, String bottleType) {
            this.recipeId = recipeId;
            this.productId = productId;
            this.liquidA = liquidA;
            this.liquidB = liquidB;
            this.bottleType = bottleType;
        }
    }

    /** The full recipe catalog, oldest first -- lets a caller show "1, 2, 3, 4..." before
     * asking which one an order uses, rather than the customer having to already know an id. */
    public List<RecipeSummary> listRecipes() throws SQLException {
        List<RecipeSummary> out = new ArrayList<>();
        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(
                     "SELECT recipe_id, product_id, liquid_a_proportion, liquid_b_proportion, bottle_type "
                             + "FROM Recipes ORDER BY recipe_id")) {
            while (rs.next()) {
                out.add(new RecipeSummary(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getString(5)));
            }
        }
        return out;
    }

    // ---------- Batches (Table 2) ----------

    public int insertBatch(int recipeId, String productId) throws SQLException {
        String sql = "INSERT INTO Batches(recipe_id, product_id, status, start_timestamp) VALUES (?,?,'RUNNING', CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            ps.setString(2, productId);
            ps.executeUpdate();
            return lastId(ps);
        }
    }

    public void completeBatch(int batchId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE Batches SET status='COMPLETED', completion_timestamp=CURRENT_TIMESTAMP WHERE batch_id=?")) {
            ps.setInt(1, batchId);
            ps.executeUpdate();
        }
    }

    /** Batches left RUNNING -- used at startup to find work orphaned by an abrupt interruption
     * (Section 7, power-loss recovery). Any batch found RUNNING here was necessarily left by a
     * PREVIOUS process: a fresh process has not activated anything of its own yet when this is
     * called, and completeBatch()/markBatchFault() always resolve RUNNING before any later
     * batch reuses this status for real in-process work. */
    public List<Integer> queryRunningBatchIds() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery("SELECT batch_id FROM Batches WHERE status='RUNNING'")) {
            while (rs.next()) ids.add(rs.getInt(1));
        }
        return ids;
    }

    /** Closes out a batch that did not complete normally (Section 7): its incomplete bottles
     * have already been discarded via markBottleAborted before this is called. */
    public void markBatchFault(int batchId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE Batches SET status='FAULT', completion_timestamp=CURRENT_TIMESTAMP WHERE batch_id=?")) {
            ps.setInt(1, batchId);
            ps.executeUpdate();
        }
    }

    // ---------- Orders (Table 3) ----------

    /** All customer_po references currently on file. Needed to seed a client-side PO
     * generator (PosGuiFrame.nextPoReference) with a collision-free starting point: a fixed,
     * reused database file (-Dip.database=build/...db) persists customer_po values across
     * JVM restarts, but an in-memory sequence counter does not, so re-deriving the highest
     * value already used is the only way to avoid re-issuing one that violates the UNIQUE
     * constraint on customer_po. */
    public List<String> listCustomerPos() throws SQLException {
        List<String> out = new ArrayList<>();
        try (Statement s = conn.createStatement(); ResultSet rs = s.executeQuery("SELECT customer_po FROM Orders")) {
            while (rs.next()) { out.add(rs.getString(1)); }
        }
        return out;
    }

    public int insertOrder(String customerPo, String customerId, String productId, int quantity) throws SQLException {
        String sql = "INSERT INTO Orders(customer_po, customer_id, product_id, quantity, status) VALUES (?,?,?,?,'PENDING')";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customerPo);
            ps.setString(2, customerId);
            ps.setString(3, productId);
            ps.setInt(4, quantity);
            ps.executeUpdate();
            return lastId(ps);
        }
    }

    /** One order's current state for the POS "Track Order" lookup: which batch (if any)
     * it was admitted into, that batch's status, and how many of this order's own bottles
     * have completed. batchId/batchStatus are null while the order is still PENDING. */
    public static class OrderStatus {
        public final int orderId;
        public final String customerPo;
        public final String customerId;
        public final String productId;
        public final int quantity;
        public final String status;
        public final Integer batchId;
        public final String batchStatus;
        public final int completedInBatch;
        public OrderStatus(int orderId, String customerPo, String customerId, String productId, int quantity,
                String status, Integer batchId, String batchStatus, int completedInBatch) {
            this.orderId = orderId;
            this.customerPo = customerPo;
            this.customerId = customerId;
            this.productId = productId;
            this.quantity = quantity;
            this.status = status;
            this.batchId = batchId;
            this.batchStatus = batchStatus;
            this.completedInBatch = completedInBatch;
        }
    }

    /** BatchManager admits an order into exactly one batch today, so the LEFT JOIN below
     * returns at most one row per customer_po; returns null if no order has that PO at all. */
    public OrderStatus findOrderStatus(String customerPo) throws SQLException {
        String sql = "SELECT o.order_id, o.customer_po, o.customer_id, o.product_id, o.quantity, o.status, "
                + "ob.batch_id, b.status, ob.completed_quantity "
                + "FROM Orders o "
                + "LEFT JOIN OrderBatches ob ON ob.order_id = o.order_id "
                + "LEFT JOIN Batches b ON b.batch_id = ob.batch_id "
                + "WHERE o.customer_po = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customerPo);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) { return null; }
                Object batchIdObj = rs.getObject(7);
                return new OrderStatus(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getString(6), batchIdObj == null ? null : ((Number) batchIdObj).intValue(),
                        rs.getString(8), rs.getInt(9));
            }
        }
    }

    /** Result row for the cross-order scheduling query (Section 7 of the IP report). */
    public static class PendingDemand {
        public final String productId;
        public final int totalQuantity;
        public PendingDemand(String productId, int totalQuantity) {
            this.productId = productId;
            this.totalQuantity = totalQuantity;
        }
    }

    /**
     * Groups all PENDING orders by product and sums their quantity.
     * This is the query the Batch Manager runs before dispatching the
     * next batch, per Section 7 ("Cross-order scheduling").
     */
    public List<PendingDemand> queryPendingDemandByProduct() throws SQLException {
        String sql = "SELECT product_id, SUM(quantity) AS qty FROM Orders WHERE status='PENDING' GROUP BY product_id ORDER BY qty DESC";
        List<PendingDemand> out = new ArrayList<>();
        try (Statement s = conn.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                out.add(new PendingDemand(rs.getString("product_id"), rs.getInt("qty")));
            }
        }
        return out;
    }

    public List<Integer> queryPendingOrderIdsForProduct(String productId) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT order_id FROM Orders WHERE status='PENDING' AND product_id=?")) {
            ps.setString(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) ids.add(rs.getInt("order_id"));
            }
        }
        return ids;
    }

    public void markOrderAdmitted(int orderId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE Orders SET status='ADMITTED' WHERE order_id=?")) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        }
    }

    /** The oldest order contributing to this batch, used ONLY as a display/label value for
     * GP-side components (e.g. the Tracker's orderId field) that expect exactly one order per
     * batch. Real per-bottle order attribution never uses this -- that is entirely
     * {@link #nextUnfulfilledOrderForBatch(int)}'s job, which is unaffected by cross-order
     * merging. Returns null only if the batch has no OrderBatches rows yet (should not happen
     * once BatchManager has admitted at least one order into it). */
    public Integer firstOrderIdForBatch(int batchId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT MIN(order_id) FROM OrderBatches WHERE batch_id=?")) {
            ps.setInt(1, batchId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                int value = rs.getInt(1);
                return rs.wasNull() ? null : value;
            }
        }
    }

    public int getOrderQuantity(int orderId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT quantity FROM Orders WHERE order_id=?")) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    // ---------- OrderBatches (Table 4) ----------

    public void insertOrderBatch(int orderId, int batchId, int allocatedQuantity) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO OrderBatches(order_id, batch_id, allocated_quantity) VALUES (?,?,?)")) {
            ps.setInt(1, orderId);
            ps.setInt(2, batchId);
            ps.setInt(3, allocatedQuantity);
            ps.executeUpdate();
        }
    }

    /** Increments the completed_quantity for the order's share of this batch (Section 5/7). */
    public void incrementOrderBatchCompleted(int orderId, int batchId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE OrderBatches SET completed_quantity = completed_quantity + 1 WHERE order_id=? AND batch_id=?")) {
            ps.setInt(1, orderId);
            ps.setInt(2, batchId);
            ps.executeUpdate();
        }
    }

    /**
     * Which order should the NEXT admitted bottle in this batch be attributed to?
     * Picks the first order still short of its allocated_quantity (FIFO by order_id)
     * and reserves the slot immediately by incrementing admitted_quantity in the
     * same call — this is how order_id gets attached to a bottle "at admission"
     * (Section 5/4).
     *
     * Checks admitted_quantity (in-flight, reserved-but-not-yet-complete), not
     * completed_quantity: with several bottles admitted concurrently for the same
     * order (as the live GP's rotary table does), checking completed_quantity alone
     * would under-count in-flight bottles and over-attribute later bottles to an
     * order that's already fully spoken for. See POS_BatchManager_Spec.md Section 4
     * ("Known fix required") for the incident this was written to prevent.
     */
    public Integer nextUnfulfilledOrderForBatch(int batchId) throws SQLException {
        Integer orderId;
        String selectSql = "SELECT order_id FROM OrderBatches WHERE batch_id=? AND admitted_quantity < allocated_quantity ORDER BY order_id LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setInt(1, batchId);
            try (ResultSet rs = ps.executeQuery()) {
                orderId = rs.next() ? rs.getInt(1) : null;
            }
        }
        if (orderId != null) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE OrderBatches SET admitted_quantity = admitted_quantity + 1 WHERE order_id=? AND batch_id=?")) {
                ps.setInt(1, orderId);
                ps.setInt(2, batchId);
                ps.executeUpdate();
            }
        }
        return orderId;
    }

    // ---------- BottleEvents (Table 5) ----------

    public void insertBottleEvent(String bottleId, String workpieceId, Integer orderId, int batchId,
                                   String productId, Integer recipeId, String location, String status) throws SQLException {
        String sql = "INSERT INTO BottleEvents(bottle_id, workpiece_id, order_id, batch_id, product_id, recipe_id, location, status, event_timestamp) "
                   + "VALUES (?,?,?,?,?,?,?,?, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bottleId);
            ps.setString(2, workpieceId);
            if (orderId != null) ps.setInt(3, orderId); else ps.setNull(3, Types.INTEGER);
            ps.setInt(4, batchId);
            ps.setString(5, productId);
            if (recipeId != null) ps.setInt(6, recipeId); else ps.setNull(6, Types.INTEGER);
            ps.setString(7, location);
            ps.setString(8, status);
            ps.executeUpdate();
        }
    }

    /** Full station-by-station history for one bottle, in order — used by deviation detection and the GUI panel. */
    public List<String> queryStationSequence(String bottleId) throws SQLException {
        List<String> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT location, status FROM BottleEvents WHERE bottle_id=? ORDER BY event_id")) {
            ps.setString(1, bottleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(rs.getString("location") + ":" + rs.getString("status"));
            }
        }
        return out;
    }

    /** Has this bottle recorded a terminal DONE event at the given final station (used by recovery check, Section 7). */
    public boolean hasTerminalDone(String bottleId, String finalStation) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM BottleEvents WHERE bottle_id=? AND location=? AND status='DONE'")) {
            ps.setString(1, bottleId);
            ps.setString(2, finalStation);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** All bottle_ids that have at least one row for the given batch but no terminal DONE at finalStation. */
    public List<String> queryIncompleteBottlesInBatch(int batchId, String finalStation) throws SQLException {
        String sql = "SELECT DISTINCT bottle_id FROM BottleEvents WHERE batch_id=? "
                   + "AND bottle_id NOT IN (SELECT bottle_id FROM BottleEvents WHERE batch_id=? AND location=? AND status='DONE')";
        List<String> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, batchId);
            ps.setInt(2, batchId);
            ps.setString(3, finalStation);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(rs.getString(1));
            }
        }
        return out;
    }

    /** Appends one more row recording this bottle's final disposition as ABORTED (Section 7,
     * power-loss recovery). BottleEvents is append-only: a bottle interrupted mid-route has
     * every existing row legitimately marked DONE (it genuinely completed those stations, just
     * not the final one), so an UPDATE that excludes DONE rows -- the original approach here --
     * touches nothing and silently fails to record the abort at all. Reads the bottle's own
     * most recent row for its context (workpiece/order/batch/product/recipe/location) rather
     * than requiring the caller to supply it again. A no-op if bottleId has no rows yet. */
    public void markBottleAborted(String bottleId) throws SQLException {
        String workpieceId; Integer orderId; int batchId; String productId; Integer recipeId; String location;
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT workpiece_id, order_id, batch_id, product_id, recipe_id, location "
                        + "FROM BottleEvents WHERE bottle_id=? ORDER BY event_id DESC LIMIT 1")) {
            ps.setString(1, bottleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return;
                workpieceId = rs.getString(1);
                int orderIdRaw = rs.getInt(2); orderId = rs.wasNull() ? null : orderIdRaw;
                batchId = rs.getInt(3);
                productId = rs.getString(4);
                int recipeIdRaw = rs.getInt(5); recipeId = rs.wasNull() ? null : recipeIdRaw;
                location = rs.getString(6);
            }
        }
        insertBottleEvent(bottleId, workpieceId, orderId, batchId, productId, recipeId, location, "ABORTED");
    }

    // ---------- Faults (Table 6) ----------

    public void insertFault(String bottleId, int batchId, String deviceName, String faultType, String reason) throws SQLException {
        String sql = "INSERT INTO Faults(bottle_id, batch_id, device_name, fault_type, fault_reason, fault_timestamp, status) "
                   + "VALUES (?,?,?,?,?, CURRENT_TIMESTAMP, 'OPEN')";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bottleId);
            ps.setInt(2, batchId);
            ps.setString(3, deviceName);
            ps.setString(4, faultType);
            ps.setString(5, reason);
            ps.executeUpdate();
        }
    }

    public void resolveFault(int faultId, String resolution) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE Faults SET status='RESOLVED', resolution=?, resolved_timestamp=CURRENT_TIMESTAMP WHERE fault_id=?")) {
            ps.setString(1, resolution);
            ps.setInt(2, faultId);
            ps.executeUpdate();
        }
    }

    // ---------- helpers ----------

    /**
     * The xerial SQLite JDBC driver does not implement getGeneratedKeys()
     * on PreparedStatement, so the last inserted rowid is fetched explicitly
     * instead (same connection, so this is safe/serialised in Stage 1).
     */
    private int lastId(Statement ignored) throws SQLException {
        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery("SELECT last_insert_rowid()")) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
