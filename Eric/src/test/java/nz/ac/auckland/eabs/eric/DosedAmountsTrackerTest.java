package nz.ac.auckland.eabs.eric;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.Location;
import nz.ac.auckland.eabs.eric.model.MachineReport;
import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.model.WorkpieceStatus;
import nz.ac.auckland.eabs.eric.persistence.WorkpieceRepository;
import nz.ac.auckland.eabs.eric.tracking.WorkpieceSnapshot;
import nz.ac.auckland.eabs.eric.tracking.WorkpieceTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Tests the optional confirmed-dosing field without changing GP persistence. */
public final class DosedAmountsTrackerTest {
    private DosedAmountsTrackerTest() { }

    public static void runAll() throws IOException {
        testConfirmedAmountsSurviveFinishingAndSnapshots();
        testMissingAmountsAndNonFillerResults();
        testFaultAndStaleReports();
        System.out.println("ERIC CONFIRMED DOSING TESTS PASSED");
    }

    private static void testConfirmedAmountsSurviveFinishingAndSnapshots()
            throws IOException {
        WorkpieceTracker tracker = tracker("BOTTLE-1", Operation.FILL_TWO_LIQUIDS,
                Operation.PLACE_LID, Operation.CAP_BOTTLE,
                Operation.APPLY_LABEL, Operation.UNLOAD);
        WorkpieceSnapshot before = snapshot(tracker, "BOTTLE-1");
        tracker.createTwin("BOTTLE-2", "ORDER", "BATCH", "PRODUCT", "20/80",
                Collections.singletonList(Operation.FILL_TWO_LIQUIDS));
        Map<String, String> amounts = amounts("100.1250", "400.5000");
        FinishingJob fill = job("FILL-1", "BOTTLE-1", "FillerController",
                Operation.FILL_TWO_LIQUIDS, amounts);
        tracker.dispatch(fill);
        require(snapshot(tracker, "BOTTLE-1").getActualDosedAmounts() == null,
                "Dispatch data must not become confirmed dosing");
        tracker.acceptReport(MachineReport.busy(fill));
        require(snapshot(tracker, "BOTTLE-1").getActualDosedAmounts() == null,
                "BUSY must not confirm dosing");
        tracker.acceptReport(MachineReport.done(fill, "filled", amounts));
        WorkpieceSnapshot confirmed = snapshot(tracker, "BOTTLE-1");
        require(amounts.equals(confirmed.getActualDosedAmounts()),
                "DONE must retain both raw values without rounding");
        require(confirmed.getStatus() == WorkpieceStatus.WAITING
                        && confirmed.getNextOperation() == Operation.PLACE_LID,
                "Existing progress semantics must remain unchanged");
        require(before.getActualDosedAmounts() == null
                        && snapshot(tracker, "BOTTLE-2").getActualDosedAmounts() == null,
                "Other bottles and earlier snapshots must not acquire measurements");
        amounts.put("liquidA", "999");
        require("100.1250".equals(confirmed.getActualDosedAmounts().get("liquidA")),
                "Caller-owned evidence must not mutate the twin");
        expectFailure(() -> confirmed.getActualDosedAmounts().put("liquidA", "0"));

        int sequence = 0;
        for (Operation operation : Arrays.asList(Operation.PLACE_LID,
                Operation.CAP_BOTTLE, Operation.APPLY_LABEL, Operation.UNLOAD)) {
            FinishingJob finishing = job("FINISH-" + sequence++, "BOTTLE-1",
                    "FinishingController", operation, Collections.emptyMap());
            tracker.dispatch(finishing);
            tracker.acceptReport(MachineReport.done(finishing, "finished",
                    amounts("1", "2")));
            require(confirmed.getActualDosedAmounts().equals(
                            snapshot(tracker, "BOTTLE-1").getActualDosedAmounts()),
                    "Non-filler DONE must neither clear nor overwrite dosing");
        }
        tracker.confirmLocation("BOTTLE-1", Location.OUTPUT_COLLECTION, 0,
                "collection sensor");
        WorkpieceSnapshot completed = tracker.completeAndArchive("BOTTLE-1", "LABEL");
        require(confirmed.getActualDosedAmounts().equals(completed.getActualDosedAmounts()),
                "Final snapshot handed to assembler/repository must retain dosing");
    }

    private static void testMissingAmountsAndNonFillerResults() {
        List<Map<String, String>> missing = Arrays.asList(
                Collections.<String, String>emptyMap(),
                Collections.singletonMap("liquidA", "10"),
                Collections.singletonMap("liquidB", "20"),
                amounts(" ", "20"), amounts("10", null));
        for (Map<String, String> evidence : missing) {
            WorkpieceTracker tracker = tracker("B", Operation.FILL_TWO_LIQUIDS);
            FinishingJob fill = job("J", "B", "FillerController",
                    Operation.FILL_TWO_LIQUIDS, Collections.emptyMap());
            tracker.dispatch(fill);
            tracker.acceptReport(MachineReport.done(fill, "legacy completion", evidence));
            require(snapshot(tracker, "B").getActualDosedAmounts() == null,
                    "Missing/partial evidence must remain absent");
            require(snapshot(tracker, "B").getCompletedOperations()
                            .contains(Operation.FILL_TWO_LIQUIDS),
                    "Optional evidence must not block existing DONE processing");
        }
        WorkpieceTracker tracker = tracker("B", Operation.CAP_BOTTLE);
        FinishingJob cap = job("J", "B", "CapperController",
                Operation.CAP_BOTTLE, Collections.emptyMap());
        tracker.dispatch(cap);
        tracker.acceptReport(MachineReport.done(cap, "capped", amounts("1", "2")));
        require(snapshot(tracker, "B").getActualDosedAmounts() == null,
                "Non-filler result must not introduce measurements");
    }

    private static void testFaultAndStaleReports() {
        WorkpieceTracker tracker = tracker("B", Operation.FILL_TWO_LIQUIDS);
        FinishingJob first = job("OLD", "B", "FillerController",
                Operation.FILL_TWO_LIQUIDS, amounts("1", "2"));
        tracker.dispatch(first);
        tracker.acceptReport(MachineReport.fault(first, "stopped"));
        require(snapshot(tracker, "B").getActualDosedAmounts() == null,
                "FAULT must not confirm command amounts");
        tracker.clearFaultForRetry("B", "safe retry authorised");
        FinishingJob retry = job("NEW", "B", "FillerController",
                Operation.FILL_TWO_LIQUIDS, Collections.emptyMap());
        tracker.dispatch(retry);
        expectFailure(() -> tracker.acceptReport(
                MachineReport.done(first, "stale", amounts("9", "9"))));
        FinishingJob wrongMachine = job("NEW", "B", "WrongController",
                Operation.FILL_TWO_LIQUIDS, Collections.emptyMap());
        expectFailure(() -> tracker.acceptReport(
                MachineReport.done(wrongMachine, "wrong station", amounts("9", "9"))));
        require(snapshot(tracker, "B").getActualDosedAmounts() == null,
                "Rejected completions must not mutate dosing");
        tracker.acceptReport(MachineReport.done(retry, "filled", amounts("0", "100")));
        expectFailure(() -> tracker.acceptReport(
                MachineReport.done(retry, "duplicate", amounts("9", "9"))));
        require(amounts("0", "100").equals(snapshot(tracker, "B").getActualDosedAmounts()),
                "A confirmed zero must be retained; duplicate DONE must not overwrite it");
    }

    private static WorkpieceTracker tracker(String id, Operation... route) {
        WorkpieceTracker tracker = new WorkpieceTracker(new MemoryRepository());
        tracker.createTwin(id, "ORDER", "BATCH", "PRODUCT", "20/80", Arrays.asList(route));
        return tracker;
    }

    private static WorkpieceSnapshot snapshot(WorkpieceTracker tracker, String id) {
        return tracker.findActive(id).get();
    }

    private static FinishingJob job(String id, String bottle, String machine,
            Operation operation, Map<String, String> data) {
        return new FinishingJob(id, bottle, machine, operation, data);
    }

    private static Map<String, String> amounts(String a, String b) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        result.put("liquidA", a);
        result.put("liquidB", b);
        return result;
    }

    private static void require(boolean condition, String message) {
        if (!condition) { throw new AssertionError(message); }
    }

    private static void expectFailure(Runnable action) {
        try { action.run(); }
        catch (RuntimeException expected) { return; }
        throw new AssertionError("Expected rejection");
    }

    private static final class MemoryRepository implements WorkpieceRepository {
        private final Map<String, WorkpieceSnapshot> records = new LinkedHashMap<>();
        public void save(WorkpieceSnapshot snapshot) {
            records.put(snapshot.getWorkpieceId(), snapshot);
        }
        public Optional<WorkpieceSnapshot> findById(String id) {
            return Optional.ofNullable(records.get(id));
        }
        public List<WorkpieceSnapshot> findAll() {
            return new ArrayList<>(records.values());
        }
    }
}
