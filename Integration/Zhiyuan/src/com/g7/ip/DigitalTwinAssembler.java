package com.g7.ip;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Digital Twin Assembler (IP report, Section 4).
 *
 * Responsibilities, exactly as scoped in the report:
 *  - observe changes to the Tracker's five confirmed fields (never anything else);
 *  - mint a stable bottle_id at admission, distinct from the GP's run-local workpieceId;
 *  - attach order/product/recipe linkage ONCE, at admission, via the batch context
 *    the Batch Manager already holds;
 *  - append one BottleEvents row per confirmed DONE/FAULT/ABORTED transition —
 *    never on a BUSY/START-style intermediate state.
 *
 * Stage-1 (current) implementation: calls Dao directly and synchronously,
 * exactly as planned in Section 10 ("a first, synchronous version will call
 * the JDBC layer directly ... to validate the schema and the assembled
 * twin"). Stage 2 will replace the direct dao.* calls below with
 * dbRequest/dbResponseAck signal emission to an asynchronous DB Worker,
 * without changing any of the admission/eventing logic itself.
 */
public class DigitalTwinAssembler {

    private final Dao dao;

    /** workpieceId -> bottle_id, valid for the lifetime of one bottle on the line. */
    private final Map<String, String> bottleIdByWorkpiece = new HashMap<>();
    /** workpieceId -> order_id it was attributed to at admission. */
    private final Map<String, Integer> orderIdByWorkpiece = new HashMap<>();
    /** batchId -> {productId, recipeId}, cached from Batches at admission time. */
    private final Map<Integer, int[]> recipeIdByBatch = new HashMap<>();
    private final Map<Integer, String> productIdByBatch = new HashMap<>();

    private int bottleCounter = 0;

    /** The station a bottle must reach with status=DONE to count as "complete" for order tracking. */
    private final String finalStation;

    public DigitalTwinAssembler(Dao dao) {
        this(dao, "unloader");
    }

    public DigitalTwinAssembler(Dao dao, String finalStation) {
        this.dao = dao;
        this.finalStation = finalStation;
    }

    /**
     * Called once when the Bottle Loader admits a new bottle (workpieceId assigned
     * by the GP Tracker for the first time). Mints bottle_id, attributes the bottle
     * to the next unfulfilled order in this batch, and caches the batch's recipe.
     */
    public String admitBottle(String workpieceId, int batchId, int recipeId, String productId) throws SQLException {
        bottleCounter++;
        String bottleId = "B-" + batchId + "-" + String.format("%04d", bottleCounter);
        bottleIdByWorkpiece.put(workpieceId, bottleId);
        recipeIdByBatch.put(batchId, new int[]{recipeId});
        productIdByBatch.put(batchId, productId);

        Integer orderId = dao.nextUnfulfilledOrderForBatch(batchId);
        if (orderId != null) {
            orderIdByWorkpiece.put(workpieceId, orderId);
        }
        return bottleId;
    }

    /**
     * Called for every field change the stub stream (or, in Stage 2, the live GP)
     * reports. Only confirmed transitions are persisted; BUSY/intermediate states
     * are observed but intentionally dropped (Section 4: "reflects what actually
     * happened, not merely what was requested").
     */
    public void onFieldChange(TrackerFieldChange change) throws SQLException {
        if (!change.isConfirmedTransition()) {
            return; // e.g. BUSY — nothing to persist yet
        }
        String bottleId = bottleIdByWorkpiece.get(change.workpieceId);
        if (bottleId == null) {
            throw new IllegalStateException("Field change for unadmitted workpieceId=" + change.workpieceId
                    + " — admitBottle() must be called first, mirroring GP admission order.");
        }
        Integer orderId = orderIdByWorkpiece.get(change.workpieceId);
        int[] recipe = recipeIdByBatch.get(change.batchId);
        String productId = productIdByBatch.get(change.batchId);

        dao.insertBottleEvent(bottleId, change.workpieceId, orderId, change.batchId,
                productId, recipe == null ? null : recipe[0], change.location, change.status);

        if ("FAULT".equals(change.status)) {
            dao.insertFault(bottleId, change.batchId, change.location, "device_fault",
                    "FAULT reported at " + change.location + " for " + change.workpieceId);
        }

        // Order-completion bookkeeping happens ONCE per bottle, when it reaches the
        // final station with a DONE status — not once per station along the way.
        if ("DONE".equals(change.status) && finalStation.equals(change.location) && orderId != null) {
            dao.incrementOrderBatchCompleted(orderId, change.batchId);
        }
    }

    /** Exposed for the Batch Manager's power-loss recovery check (Section 7). */
    public String bottleIdFor(String workpieceId) {
        return bottleIdByWorkpiece.get(workpieceId);
    }
}
