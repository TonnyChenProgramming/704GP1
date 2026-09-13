package com.g7.ip;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Batch and Recipe Manager (POS_BatchManager_Spec.md, Section 2/4).
 *
 * On BatchDrained(prev_batch_id) or at startup, consolidates ALL pending
 * orders for one product into a single batch and emits exactly one
 * ActivateBatch to the Coordinator. The Coordinator never sees order_id or
 * customer_id (Section 3 boundary rule) — only batch_id/recipe_id/
 * product_id/total_quantity.
 *
 * The Coordinator doesn't exist yet in this codebase, so it's represented
 * here only by the {@link CoordinatorLink} signal-contract interface this
 * spec defines — exactly what Section 1 means by "both only need to know
 * the signal contract ... not the Coordinator's internal state machine."
 */
public class BatchManager {

    /** Batch Manager -> Coordinator: ActivateBatch(batch_id, recipe_id, product_id, total_quantity) (Section 3). */
    public interface CoordinatorLink {
        void activateBatch(int batchId, int recipeId, String productId, int totalQuantity);
    }

    private final Dao dao;
    private final CoordinatorLink coordinator;

    public BatchManager(Dao dao, CoordinatorLink coordinator) {
        this.dao = dao;
        this.coordinator = coordinator;
    }

    /** Call once at startup, before any BatchDrained signal has been received. */
    public void start() throws SQLException {
        tryActivateNextBatch();
    }

    /** Coordinator -> Batch Manager: BatchDrained(batch_id) (Section 3). drainedBatchId is not otherwise used. */
    public void onBatchDrained(int drainedBatchId) throws SQLException {
        tryActivateNextBatch();
    }

    /**
     * The admission algorithm from Section 4:
     * pick the product with the most pending demand (tie-break: oldest
     * pending order first), consolidate all its pending orders into one
     * batch, and emit exactly one ActivateBatch. No-op if nothing is pending
     * — the caller (POS, on the next SubmitOrder) is responsible for
     * prompting a retry.
     */
    private void tryActivateNextBatch() throws SQLException {
        List<Dao.PendingDemand> demand = dao.queryPendingDemandByProduct();
        if (demand.isEmpty()) {
            return;
        }

        String targetProduct = pickTargetProduct(demand);
        List<Integer> orderIds = dao.queryPendingOrderIdsForProduct(targetProduct);
        Integer recipeId = dao.findRecipeIdForProduct(targetProduct);
        if (recipeId == null) {
            // Can't happen if POS is the only path into Orders, since POS requires a
            // matching Recipes row before it will accept an order for a product.
            throw new IllegalStateException("No recipe on file for product " + targetProduct);
        }

        int batchId = dao.insertBatch(recipeId, targetProduct);
        int totalQuantity = 0;
        for (int orderId : orderIds) {
            int qty = dao.getOrderQuantity(orderId);
            dao.insertOrderBatch(orderId, batchId, qty);
            dao.markOrderAdmitted(orderId);
            totalQuantity += qty;
        }

        coordinator.activateBatch(batchId, recipeId, targetProduct, totalQuantity);
    }

    /**
     * demand is already ORDER BY qty DESC (see Dao.queryPendingDemandByProduct),
     * so ties for the max quantity form a contiguous prefix. Among those tied
     * products, the one whose oldest pending order has the lowest order_id wins
     * (order_id is assigned in submission order, so this is "oldest order first"
     * without a second created_at column/query).
     */
    private String pickTargetProduct(List<Dao.PendingDemand> demand) throws SQLException {
        int maxQty = demand.get(0).totalQuantity;
        String best = null;
        Integer bestOldestOrderId = null;
        for (Dao.PendingDemand d : demand) {
            if (d.totalQuantity != maxQty) break;
            int oldest = Collections.min(dao.queryPendingOrderIdsForProduct(d.productId));
            if (bestOldestOrderId == null || oldest < bestOldestOrderId) {
                bestOldestOrderId = oldest;
                best = d.productId;
            }
        }
        return best;
    }
}
