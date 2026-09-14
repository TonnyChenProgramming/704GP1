package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Bridges IntegratedCoordinator's real, per-bottle confirmed transitions -- the same
 * evidence it already passes to Eric's WorkpieceTracker -- to the IP's
 * DigitalTwinAssembler, without CoordinatorCD ever holding a Java reference to
 * IpBatchManagerCD's model (SystemJ CDs never share Java objects across a signal
 * boundary) and without ever blocking the SystemJ tick thread that calls
 * IntegratedCoordinator.report()/reset() on a database operation -- that part is
 * IpBatchManagerModel's DbWorker's job, entirely on the consuming side.
 *
 * publish* methods only ever perform a non-blocking, non-throwing queue offer:
 * silently dropping an event if the queue is ever completely full is preferable to
 * ever risking the coordinator's own control flow. Events are consumed by whichever
 * IpBatchManagerModel is running in this process (at most one at a time, per the
 * project's one-simulation-per-process rule).
 */
final class TwinEventBus {
    static final class Event {
        final boolean admission;
        final String workpieceId;
        final int batchId;
        final int recipeId;     // meaningful only when admission is true
        final String productId; // meaningful only when admission is true
        final String location;  // meaningful only when admission is false
        final String status;    // meaningful only when admission is false; DONE|FAULT|ABORTED

        private Event(boolean admission, String workpieceId, int batchId, int recipeId,
                String productId, String location, String status) {
            this.admission = admission;
            this.workpieceId = workpieceId;
            this.batchId = batchId;
            this.recipeId = recipeId;
            this.productId = productId;
            this.location = location;
            this.status = status;
        }
    }

    private static final BlockingQueue<Event> events = new ArrayBlockingQueue<Event>(10000);

    static void publishAdmission(String workpieceId, int batchId, int recipeId, String productId) {
        events.offer(new Event(true, workpieceId, batchId, recipeId, productId, null, null));
    }

    static void publishTransition(String workpieceId, int batchId, String location, String status) {
        events.offer(new Event(false, workpieceId, batchId, 0, null, location, status));
    }

    /** Blocking: for the one dedicated consumer thread that drains this bus. */
    static Event take() throws InterruptedException {
        return events.take();
    }
}
