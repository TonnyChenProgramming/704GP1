package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Coordinator's contract with the Workpiece Tracker (Coordinator_Spec.md
 * Section 4; GP report Table 4): createTwin, setStatus, confirmLocation,
 * confirmOperation, queryTwin.
 *
 * This interface is deliberately boundary-compliant with
 * Coordinator_SystemI_Spec.md Section 1.2: no order_id, customer_id,
 * product_id, recipe_id or history list ever crosses it, and every method
 * is evidence-based -- called only once a Controller's confirmation is in
 * hand, never merely because a command was sent.
 *
 * Eric's currently-delivered WorkpieceTracker.createTwin(workpieceId,
 * orderId, batchId, productId, requiredRecipe, requiredOperations) requires
 * orderId/productId/requiredRecipe and the twin keeps full history lists
 * internally -- both conflict with the boundary rule above. Zhiyuan has
 * asked Eric to update to a six-field-only shape and this interface is
 * written assuming that lands (see Tracker_Change_Request_for_Eric.md).
 * Nothing else in this package depends on Eric's WorkpieceTracker class
 * directly; wiring a concrete Eric-backed implementation once he updates is
 * a single adapter class implementing this interface.
 */
public interface TrackerPort {

    /** Called once, when the Bottle Loader confirms admission of a new bottle. */
    void createTwin(String workpieceId, String batchId, List<String> requiredOperations);

    /** Evidence-based status change -- e.g. FAULT, ABORTED. Never called merely because a command was sent. */
    void setStatus(String workpieceId, String status);

    /** Called only after position/photo-eye (or table-alignment) evidence confirms the bottle's new location. */
    void confirmLocation(String workpieceId, String location);

    /** Called only after a Controller's DONE report confirms an operation actually completed. */
    void confirmOperation(String workpieceId, String operation, Map<String, String> result);

    Optional<TwinView> queryTwin(String workpieceId);
}
