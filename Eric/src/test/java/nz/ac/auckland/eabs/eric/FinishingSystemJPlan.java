package nz.ac.auckland.eabs.eric;

import nz.ac.auckland.eabs.eric.systemj.FinishingWire;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/** Channel-driving acceptance plan, used only by the four SystemJ harness CDs. */
public final class FinishingSystemJPlan {
    private static final AtomicInteger FINISHED = new AtomicInteger();
    private final List<String[]> steps = new ArrayList<String[]>();
    private final String machine, name;
    private int cursor, checks, controlSequence;

    public FinishingSystemJPlan(String machine, String name) {
        this.machine = machine; this.name = name;
        expect("READY|" + machine);
        // Startup is fail-closed, even though the command receiver is ready.
        start("NO-PERMIT", "WP-0", ""); reject("NO-PERMIT", "WP-0", "SAFETY_NOT_PERMITTED");
        control("PERMIT", "true");
        success("J-101", "BOTTLE-ALPHA", true);
        success("J-205", "BOTTLE-BETA", false);
        start("J-101", "BOTTLE-ALPHA", ""); reject("J-101", "BOTTLE-ALPHA", "DUPLICATE_JOB");
        action("JOB", "START|WRONG-M|WP-M|OtherController|" + FinishingWire.operation(machine).name());
        reject("WRONG-M", "WP-M", "WRONG_MACHINE");
        action("JOB", "START|WRONG-OP|WP-OP|" + machine + "|FILL_TWO_LIQUIDS");
        reject("WRONG-OP", "WP-OP", "WRONG_OPERATION");
        action("JOB", "START|MALFORMED|WP-MAL|" + machine + "|" + FinishingWire.operation(machine).name() + "|bottlePresent=true|bottlePresent=false");
        reject("MALFORMED", "WP-MAL", "DUPLICATE_OR_INVALID_KEY");
        start("BAD-TIME", "WP-TIME", "|timeoutTicks=NaN"); reject("BAD-TIME", "WP-TIME", "INVALID_NUMBER");
        fault("J-PRE", "WP-PRE", "|bottlePresent=false", "PLANT_PRECONDITION", false);
        fault("J-JAM", "WP-JAM", "|simMode=FAULT", "PLANT_SIMULATED_JAM", false);
        fault("J-TIME", "WP-TIMEOUT", "|simMode=STALL|timeoutTicks=3", "TIMEOUT", false);
        fault("J-STALE", "WP-STALE", "|simMode=BAD_CORRELATION", "BAD_PLANT_EVIDENCE", false);
        fault("J-SENSOR", "WP-SENSOR", "|simMode=MISSING_EVIDENCE", "BAD_PLANT_EVIDENCE", false);
        fault("J-STOP", "WP-STOP", "|simMode=STALL|timeoutTicks=1000", "SAFE_STOP", true);
        // Lose safety while a plant is stalled. Restoring permission alone cannot retry.
        start("J-HAZARD", "WP-HAZARD", "|simMode=STALL|timeoutTicks=1000"); busy("J-HAZARD", "WP-HAZARD");
        action("WAIT", ""); control("PERMIT", "false"); terminal("FAULT", "J-HAZARD", "WP-HAZARD", "SAFETY_LOST");
        ack("RESET", "J-HAZARD", "WP-HAZARD"); expect("ACK_REJECTED|" + ids("J-HAZARD", "WP-HAZARD"));
        control("PERMIT", "true"); control("CLEAR_STOP", ""); repair("J-HAZARD", "WP-HAZARD");
        success("J-FINAL", "BOTTLE-GAMMA", false);
    }

    private void success(String job, String wp, boolean wrongAck) {
        start(job, wp, ""); busy(job, wp); terminal("DONE", job, wp, "");
        if (wrongAck) {
            ack("ACK", "OLD-JOB", wp); expect("ACK_REJECTED|" + ids(job, wp));
            ack("ACK", job, "OTHER-BOTTLE"); expect("ACK_REJECTED|" + ids(job, wp));
        }
        ack("ACK", job, wp); expect("READY|" + machine);
    }
    private void fault(String job, String wp, String extra, String reason, boolean stopped) {
        start(job, wp, extra); busy(job, wp);
        if (stopped) { action("WAIT", ""); control("STOP", ""); }
        terminal("FAULT", job, wp, reason);
        ack("ACK", job, wp); expect("ACK_REJECTED|" + ids(job, wp));
        ack("RESET", job, wp); expect("ACK_REJECTED|" + ids(job, wp));
        if (stopped) { control("CLEAR_STOP", ""); }
        repair(job, wp);
    }
    private void repair(String job, String wp) {
        ack("CLEAR_FAULT", job, wp); expect("CLEARED|" + ids(job, wp));
        ack("RESET", job, wp); expect("READY|" + machine);
    }
    private void reject(String job, String wp, String reason) {
        action("REPORT", "REJECTED|" + ids(job, wp), reason); expect("READY|" + machine);
    }
    private void terminal(String kind, String job, String wp, String reason) {
        action("REPORT", kind + "|" + ids(job, wp), reason);
    }
    private void busy(String job, String wp) { expect("BUSY|" + ids(job, wp)); }
    private void ack(String kind, String job, String wp) { action("ACK", kind + "|" + job + "|" + wp); }
    private void control(String kind, String value) {
        action("CONTROL", kind + "|C" + (++controlSequence) + (value.isEmpty() ? "" : "|" + value));
    }
    private String ids(String job, String wp) { return job + "|" + wp + "|" + machine; }
    private void start(String job, String wp, String extra) {
        action("JOB", job(machine, job, wp, extra));
    }
    public static String job(String machine, String job, String wp, String extra) {
        String defaults = "|bottlePresent=true|filled=true|lidAvailable=true|lidPresent=true|manufacturingComplete=true|collectionAvailable=true|labelPayload=Bottle%7C" + wp + "%3DA%2BB";
        if (extra.contains("bottlePresent=false")) { defaults = defaults.replace("bottlePresent=true", "bottlePresent=false"); extra = extra.replace("|bottlePresent=false", ""); }
        return "START|" + job + "|" + wp + "|" + machine + "|" + FinishingWire.operation(machine).name() + defaults + extra;
    }
    private void expect(String report) { action("REPORT", report); }
    private void action(String type, String value) { action(type, value, ""); }
    private void action(String type, String value, String reason) { steps.add(new String[]{type, value, reason}); }
    public String action() { return cursor == steps.size() ? "FINISHED" : steps.get(cursor)[0]; }
    public String value() { return steps.get(cursor)[1]; }
    public void sent() { cursor++; }
    public void check(String report) {
        String[] step = steps.get(cursor);
        String expected = step[1];
        if ("CONTROL".equals(step[0])) { expected = "CONTROL_ACK|" + step[1].split("\\|")[1] + "|" + machine; }
        require(report.equals(expected) || report.startsWith(expected + "|"), "Expected " + expected + ", got " + report);
        if (report.startsWith("DONE|") || report.startsWith("FAULT|") || report.startsWith("REJECTED|")) {
            Map<String, String> data = FinishingWire.data(FinishingWire.fields(report), 4);
            if (!step[2].isEmpty()) { require(step[2].equals(data.get("reason")), "Wrong fault/rejection reason: " + report); }
            if (!report.startsWith("REJECTED|")) { require("true".equals(data.get("safe")), "Unconfirmed terminal safe state"); }
            if (report.startsWith("DONE|")) {
                for (String key : FinishingWire.requiredEvidence(machine)) { require("true".equals(data.get(key)), "Missing " + key); }
            }
            if ("SAFE_STOP".equals(step[2]) || "SAFETY_LOST".equals(step[2])) {
                require(Integer.parseInt(data.get("polls")) > 0, "Stop test must interrupt an active plant cycle");
            }
        }
        checks++; cursor++;
    }
    public void complete() {
        require(cursor == steps.size(), "Incomplete plan");
        System.out.println("ERIC SYSTEMJ " + name + " CONTRACT PASSED: " + checks + " checked channel responses");
        if (FINISHED.incrementAndGet() == 4) {
            System.out.println("ERIC SYSTEMJ DYNAMIC FINISHING ACCEPTANCE PASSED");
            System.exit(0); // Test harness only, never imported by production controllers/plants.
        }
    }
    private static void require(boolean condition, String message) { if (!condition) { throw new AssertionError(message); } }
}
