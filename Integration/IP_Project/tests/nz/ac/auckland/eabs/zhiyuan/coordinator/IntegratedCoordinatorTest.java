package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.*;
import java.nio.file.Paths;
import nz.ac.auckland.eabs.eric.model.*;
import nz.ac.auckland.eabs.eric.tracking.*;
import nz.ac.auckland.eabs.eric.persistence.*;
import nz.ac.auckland.eabs.eric.systemj.FinishingWire;

/** Dependency-free contract tests, located under the Eclipse package directory. */
public final class IntegratedCoordinatorTest {
    public static void finishRun() { System.exit(0); }
    private static int checks;
    private static final class Memory implements WorkpieceRepository {
        final Map<String, WorkpieceSnapshot> values = new LinkedHashMap<String, WorkpieceSnapshot>();
        public void save(WorkpieceSnapshot s) { values.put(s.getWorkpieceId(), s); }
        public Optional<WorkpieceSnapshot> findById(String id) { return Optional.ofNullable(values.get(id)); }
        public List<WorkpieceSnapshot> findAll() { return new ArrayList<WorkpieceSnapshot>(values.values()); }
    }
    private static void check(boolean ok, String why) { checks++; if (!ok) throw new AssertionError(why); }
    private static IntegratedCoordinator model(Memory repo) {
        IntegratedCoordinator c = new IntegratedCoordinator(new WorkpieceTracker(repo), 10000);
        for (String station : IntegratedCoordinator.STATIONS) c.ready(station, "READY|" + station);
        return c;
    }
    private static String body(String start) {
        String[] f = start.split("\\|", -1);
        return f[1] + "|" + f[2] + "|" + f[3];
    }
    private static String evidence(String start) {
        String[] f = start.split("\\|", -1);
        if (f[3].equals("LOADER")) return "bottlePlaced=true";
        if (f[3].equals("CONVEYOR")) return "bottleMoved=true";
        if (f[3].equals("FILLER")) { check(f.length == 7, "two explicit filler targets"); return "liquidA=" + f[5] + "|liquidB=" + f[6]; }
        String result = "safe=true";
        String machine = f[3].equals("LID") ? "LidLoaderController" : f[3].equals("CAPPER") ? "CapperController" : f[3].equals("LABELLER") ? "LabelerController" : "UnloaderController";
        for (String key : FinishingWire.requiredEvidence(machine)) result += "|" + key + "=true";
        if (f[3].equals("LABELLER")) { check(f.length == 6 && f[5].startsWith(f[2]+":"), "workpiece:batch label"); result += "|printedPayload=" + f[5]; }
        return result;
    }
    private static int run(IntegratedCoordinator c, String batch, int count) {
        c.activate("ACTIVATE|" + batch + "|RECIPE|PRODUCT|" + count + "|20|80|ORDER");
        int concurrency = 0;
        Set<String> operations = new HashSet<String>();
        for (int turn=0; turn<1000 && c.response().isEmpty(); turn++) {
            c.tick();
            Map<String,String> jobs = new LinkedHashMap<String,String>();
            for (String station : IntegratedCoordinator.STATIONS) {
                String job = c.take(station); if (!job.isEmpty()) jobs.put(station, job);
            }
            concurrency = Math.max(concurrency, jobs.size());
            for (Map.Entry<String,String> entry : jobs.entrySet()) {
                String station = entry.getKey(), start = entry.getValue();
                String[] f = start.split("\\|", -1);
                check(operations.add(f[2] + ":" + f[4]), "operation not dispatched twice");
                check(c.report(station, "BUSY|" + body(start)).isEmpty(), "BUSY must not ACK");
                String ack = c.report(station, "DONE|" + body(start) + "|OK|" + evidence(start));
                check(ack.equals("ACK|" + f[1] + "|" + f[2]), "correlated ACK");
                check(c.take(station).isEmpty(), "no job before post-ACK READY");
                c.ready(station, "READY|" + station);
            }
            if (c.requestRotation()) {
                List<WorkpieceSnapshot> before = c.snapshots();
                c.tick();
                for (WorkpieceSnapshot s : before) {
                    for (WorkpieceSnapshot after : c.snapshots()) if (s.getWorkpieceId().equals(after.getWorkpieceId()))
                        check(s.getLocation() == after.getLocation(), "no movement before alignment");
                }
                c.aligned();
            }
        }
        check(c.response().equals("DRAINED|" + batch), "batch drains, not fault/deadlock: " + c.response());
        check(c.completed() == count && c.snapshots().isEmpty(), "all bottles archived before drained");
        check(operations.size() == count*8, "exactly eight operations per bottle");
        c.responseSent();
        return concurrency;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            List<WorkpieceSnapshot> records = new RecoverableFileWorkpieceRepository(Paths.get(args[0])).findAll();
            check(records.size() == 10, "ten persisted bottles");
            int b1=0, b2=0;
            for (WorkpieceSnapshot s : records) {
                check(s.getStatus() == WorkpieceStatus.COMPLETED, "completed archive");
                check(s.getCompletedOperations().size() == 8, "all operations persisted");
                check(s.getActualStationsUsed().size() == 8, "actual station sequence persisted");
                check(s.getActualDosedAmounts() == null, "commanded doses must not masquerade as measurements");
                check(s.getLabelPayload().equals(s.getWorkpieceId()+":"+s.getBatchId()), "label identity");
                check(s.getOrderId().equals("ORDER-1"), "order association");
                if (s.getBatchId().equals("B1")) { b1++; check(s.getRequiredRecipe().contains("20/80"), "first recipe"); }
                else if (s.getBatchId().equals("B2")) { b2++; check(s.getRequiredRecipe().contains("35/65"), "second recipe"); }
                else throw new AssertionError("unexpected batch");
            }
            check(b1 == 8 && b2 == 2, "correct batch quantities");
            System.out.println("COORDINATOR ARCHIVE CHECK PASSED ("+checks+" checks)"); return;
        }
        Memory repo = new Memory(); IntegratedCoordinator c = model(repo);
        for (String invalid : new String[]{"ACTIVATE|X|R|P|1", "ACTIVATE|X|R|P|0|20|80|O", "ACTIVATE|X|R|P|1|120|380|O", "ACTIVATE|X|R|P|1|-1|50|O", "ACTIVATE|X|R|P|1|0|0|O"}) {
            c.activate(invalid); check(c.response().startsWith("REJECTED|"), "reject malformed/unsafe recipe"); c.responseSent();
        }
        check(run(c, "FIRST", 12) >= 3, "multiple stations dispatched concurrently");
        run(c, "SECOND", 1);
        check(repo.values.size() == 13, "distinct batch IDs and archived bottles");
        c.activate("ACTIVATE|FIRST|R|P|1|20|80|O"); check(c.response().startsWith("REJECTED|"), "reject batch replay");

        IntegratedCoordinator bad = model(new Memory());
        bad.activate("ACTIVATE|BAD|R|P|1|20|80|O"); bad.tick(); String job = bad.take("LOADER");
        bad.report("LOADER", "BUSY|wrong|wrong|LOADER");
        check(bad.fault().equals("MISMATCHED_REPORT_LOADER"), "reject wrong correlation");
        check(bad.snapshots().get(0).getCompletedOperations().isEmpty(), "wrong result cannot complete twin");
        check(!bad.requestRotation(), "fault blocks rotary");

        IntegratedCoordinator fault = model(new Memory());
        fault.activate("ACTIVATE|FAULT|R|P|1|20|80|O"); fault.tick(); job = fault.take("LOADER");
        check(fault.report("LOADER", "FAULT|"+body(job)+"|NO_BOTTLE").isEmpty(), "no ACK on FAULT");
        check(fault.snapshots().get(0).getStatus() == WorkpieceStatus.FAULTED, "fault stored on twin");
        fault.tick(); check(fault.take("LOADER").isEmpty(), "no automatic retry");
        check(fault.response().startsWith("FAULT|FAULT|"), "batch fault returned");

        IntegratedCoordinator premature = model(new Memory());
        premature.activate("ACTIVATE|EARLY|R|P|1|20|80|O"); premature.tick(); job=premature.take("LOADER");
        premature.report("LOADER", "DONE|"+body(job)+"|OK|bottlePlaced=true");
        check(premature.fault().equals("INVALID_RESULT_LOADER"), "DONE before BUSY rejected");

        IntegratedCoordinator safety = model(new Memory());
        safety.activate("ACTIVATE|SAFE|R|P|1|20|80|O"); safety.tick(); safety.hazard(); safety.tick();
        check(safety.phase().equals("HOLD") && safety.snapshots().size() == 1, "hazard preserves occupancy/identity");
        check(safety.take("LOADER").isEmpty(), "no new dispatch under hazard");
        check(!safety.requestRotation(), "hazard blocks rotation");

        IntegratedCoordinator timeout = new IntegratedCoordinator(new WorkpieceTracker(new Memory()), 1);
        Thread.sleep(5); timeout.tick(); check(timeout.fault().startsWith("STARTUP_READY_TIMEOUT"), "missing READY has deadline");
        IntegratedCoordinator missing = model(new Memory());
        missing.activate("ACTIVATE|MISSING|R|P|1|20|80|O"); missing.tick(); job=missing.take("LOADER");
        missing.report("LOADER", "BUSY|"+body(job));
        missing.report("LOADER", "DONE|"+body(job)+"|OK|bottlePlaced=false");
        check(missing.fault().equals("MISSING_COMPLETION_EVIDENCE_LOADER"), "DONE is not enough without confirmed evidence");
        IntegratedCoordinator lateReady = model(new Memory());
        lateReady.activate("ACTIVATE|ACK|R|P|1|20|80|O"); lateReady.tick(); job=lateReady.take("LOADER");
        lateReady.report("LOADER", "BUSY|"+body(job)); lateReady.report("LOADER", "DONE|"+body(job)+"|OK|bottlePlaced=true");
        for (int i=0;i<10;i++) lateReady.tick();
        check(lateReady.phase().equals("WAIT_OPERATIONS") && lateReady.take("CONVEYOR").isEmpty(), "cycle waits for post-ACK READY");
        lateReady.ready("LOADER", "READY|LOADER"); lateReady.tick(); lateReady.tick();
        check(!lateReady.take("CONVEYOR").isEmpty(), "matching READY releases next phase");
        System.out.println("COORDINATOR MODEL TESTS PASSED (" + checks + " checks)");
    }
}
