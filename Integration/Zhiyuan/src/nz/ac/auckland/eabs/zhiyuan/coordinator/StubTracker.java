package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory stand-in for the Tracker (Section 6/7's assumed boundary-compliant
 * shape -- see {@link TrackerPort}'s Javadoc), recording every call so
 * acceptance tests can assert evidence-based behaviour: a field only ever
 * changes after the matching call, never merely because a command was sent.
 */
public final class StubTracker implements TrackerPort {

    private final Map<String, TwinView> twins = new LinkedHashMap<String, TwinView>();
    private final List<String> calls = new ArrayList<String>();

    @Override
    public void createTwin(String workpieceId, String batchId, List<String> requiredOperations) {
        calls.add("createTwin(" + workpieceId + "," + batchId + ")");
        twins.put(workpieceId, new TwinView(workpieceId, batchId, null, null, "ADMITTED", null));
    }

    @Override
    public void setStatus(String workpieceId, String status) {
        calls.add("setStatus(" + workpieceId + "," + status + ")");
        TwinView old = require(workpieceId);
        twins.put(workpieceId, new TwinView(workpieceId, old.getBatchId(), old.getLocation(),
                old.getCurrentOperation(), status, old.getActualDosedAmounts()));
    }

    @Override
    public void confirmLocation(String workpieceId, String location) {
        calls.add("confirmLocation(" + workpieceId + "," + location + ")");
        TwinView old = require(workpieceId);
        twins.put(workpieceId, new TwinView(workpieceId, old.getBatchId(), location,
                old.getCurrentOperation(), old.getStatus(), old.getActualDosedAmounts()));
    }

    @Override
    public void confirmOperation(String workpieceId, String operation, Map<String, String> result) {
        calls.add("confirmOperation(" + workpieceId + "," + operation + ")");
        TwinView old = require(workpieceId);
        Map<String, String> dosed = "FILLER".equals(operation) ? result : old.getActualDosedAmounts();
        twins.put(workpieceId, new TwinView(workpieceId, old.getBatchId(), old.getLocation(),
                operation, old.getStatus(), dosed));
    }

    @Override
    public Optional<TwinView> queryTwin(String workpieceId) {
        return Optional.ofNullable(twins.get(workpieceId));
    }

    public List<String> getCalls() {
        return calls;
    }

    private TwinView require(String workpieceId) {
        TwinView twin = twins.get(workpieceId);
        if (twin == null) {
            throw new IllegalStateException("No twin for " + workpieceId + " -- createTwin() must be called first");
        }
        return twin;
    }
}
