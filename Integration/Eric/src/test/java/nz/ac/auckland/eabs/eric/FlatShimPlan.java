package nz.ac.auckland.eabs.eric;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import nz.ac.auckland.eabs.eric.systemj.FinishingWire;

/** One flat-side plan reused by Java tests and real SystemJ test-only peers. */
public final class FlatShimPlan {
    private static final AtomicInteger FINISHED = new AtomicInteger();
    private final List<String[]> steps = new ArrayList<String[]>();
    private final String station, machine, op;
    private int cursor, checks;

    public FlatShimPlan(String station) {
        this.station = station;
        machine = "LID".equals(station) ? "LidLoaderController" : "CAPPER".equals(station)
                ? "CapperController" : "LABELLER".equals(station) ? "LabelerController" : "UnloaderController";
        op = FinishingWire.operation(machine).name();
        report("READY|" + station);
        success("J-1", "WP-1", true);
        success("J-2", "WP-2", false);
        start("J-1", "WP-1", "");
        report("FAULT|J-1|WP-1|" + station + "|START_REJECTED_DUPLICATE_JOB"); ready();
        job("START|J-WRONG|WP-WRONG|OTHER|" + op);
        report("FAULT|J-WRONG|WP-WRONG|" + station + "|START_REJECTED_WRONG_STATION"); ready();
        job("START|J-OP|WP-OP|" + station + "|WRONG_OPERATION");
        report("FAULT|J-OP|WP-OP|" + station + "|START_REJECTED_WRONG_OPERATION"); ready();
        job("START"); report("FAULT|UNKNOWN|UNKNOWN|" + station + "|START_REJECTED_INVALID_START"); ready();
        if ("LABELLER".equals(station)) {
            job("START|J-LABEL|WP-LABEL|LABELLER|APPLY_LABEL|WP:BAD:BATCH");
            report("FAULT|J-LABEL|WP-LABEL|LABELLER|START_REJECTED_LABEL_REQUIRES_TWO_COLON_FREE_IDS"); ready();
        }
        fault("J-JAM", "WP-JAM", "|simMode=FAULT", "PLANT_SIMULATED_JAM");
        fault("J-TIME", "WP-TIME", "|simMode=STALL|timeoutTicks=3", "TIMEOUT");
        fault("J-EVIDENCE", "WP-EVIDENCE", "|simMode=BAD_CORRELATION", "BAD_PLANT_EVIDENCE");
        fault("J-MISSING", "WP-MISSING", "|simMode=MISSING_EVIDENCE", "BAD_PLANT_EVIDENCE");
        success("J-3", "WP-3", false);
    }
    private void success(String job, String wp, boolean badAck) {
        start(job, wp, ""); report("BUSY|" + ids(job, wp)); report("DONE|" + ids(job, wp) + "|OK");
        if (badAck) { ack("ACK|OLD-JOB|" + wp); ack("ACK|" + job + "|OTHER-BOTTLE"); }
        ack("ACK|" + job + "|" + wp); ready();
    }
    private void fault(String job, String wp, String extra, String reason) {
        start(job, wp, extra); report("BUSY|" + ids(job, wp));
        report("FAULT|" + ids(job, wp) + "|" + reason);
        // No external ACK or RESET: the shim repairs the simulated plant and waits for real READY.
        ready();
    }
    private void start(String job, String wp, String extra) {
        job("START|" + ids(job, wp) + "|" + op
                + ("LABELLER".equals(station) ? "|" + wp + ":BATCH-1" : "") + extra);
    }
    private String ids(String job, String wp) { return job + "|" + wp + "|" + station; }
    private void job(String value) { steps.add(new String[]{"JOB", value}); }
    private void ack(String value) { steps.add(new String[]{"ACK", value}); }
    private void report(String value) { steps.add(new String[]{"REPORT", value}); }
    private void ready() { report("READY|" + station); }
    public String action() { return cursor == steps.size() ? "FINISHED" : steps.get(cursor)[0]; }
    public String value() { return steps.get(cursor)[1]; }
    public void sent() { cursor++; }
    public void check(String actual) {
        require("REPORT".equals(action()), "Unsolicited report at " + action());
        String expected = value();
        if (expected.startsWith("DONE|")) {
            require(actual.startsWith(expected + "|"), "Expected " + expected + ", got " + actual);
            require(actual.contains("|safe=true"), "Missing safe completion");
            for (String key : FinishingWire.requiredEvidence(machine)) {
                require(actual.contains("|" + key + "=true"), "Missing " + key);
            }
            if ("LABELLER".equals(station)) {
                String wp = expected.split("\\|")[2];
                require(actual.contains("|printedPayload=" + wp + ":BATCH-1"), "Label not restored to flat grammar");
                require(!actual.contains("%7C"), "Outer evidence must be plain");
            }
        } else { require(expected.equals(actual), "Expected " + expected + ", got " + actual); }
        cursor++; checks++;
    }
    public int checks() { return checks; }
    public void completeSystemJ() {
        require("FINISHED".equals(action()), "Incomplete plan");
        System.out.println("ERIC FLAT SHIM " + station + " PASSED: " + checks + " checked channel responses");
        if (FINISHED.incrementAndGet() == 4) {
            System.out.println("ERIC SYSTEMJ FLAT SHIM ACCEPTANCE PASSED");
            System.exit(0); // Only this test harness owns process termination.
        }
    }
    private static void require(boolean condition, String message) { if (!condition) { throw new AssertionError(message); } }
}
