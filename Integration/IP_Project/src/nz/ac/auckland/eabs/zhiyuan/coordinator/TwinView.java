package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.Collections;
import java.util.Map;

/**
 * Read-only view of a Tracker twin, carrying exactly the six confirmed
 * fields Coordinator_SystemI_Spec.md Section 1.2 allows the Workpiece
 * Tracker to expose: workpiece_id, batch_id, location, current_operation,
 * status, and the pending filler-only actualDosedAmounts. Nothing else --
 * in particular, no order_id, product_id, recipe_id, or history list.
 */
public final class TwinView {
    private final String workpieceId;
    private final String batchId;
    private final String location;
    private final String currentOperation;
    private final String status;
    private final Map<String, String> actualDosedAmounts;

    public TwinView(String workpieceId, String batchId, String location,
                     String currentOperation, String status,
                     Map<String, String> actualDosedAmounts) {
        this.workpieceId = workpieceId;
        this.batchId = batchId;
        this.location = location;
        this.currentOperation = currentOperation;
        this.status = status;
        this.actualDosedAmounts = actualDosedAmounts == null
                ? null : Collections.unmodifiableMap(actualDosedAmounts);
    }

    public String getWorkpieceId() { return workpieceId; }
    public String getBatchId() { return batchId; }
    public String getLocation() { return location; }
    public String getCurrentOperation() { return currentOperation; }
    public String getStatus() { return status; }

    /** Non-null only for a bottle whose current/last confirmed operation was the filler (Section 5, pending). */
    public Map<String, String> getActualDosedAmounts() { return actualDosedAmounts; }
}
