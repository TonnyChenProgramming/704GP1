package nz.ac.auckland.eabs.eric.systemj;

import java.util.LinkedHashMap;
import java.util.Map;

/** Tony's flat simulation profile. SystemJ, not this model, performs channel IO.
 * Fixed preconditions and automatic simulated repair are NOT live safety/occupancy.
 */
public final class FlatFinishingShim {
    private final String station, machine;
    private String state = "BOOT_READY", job = "UNKNOWN", wp = "UNKNOWN";
    private String richStart = "", outerReport = "", label = "";
    private boolean rejected, repairable;
    private int ignoredAcks;

    public FlatFinishingShim(String station) {
        if (!Boolean.getBoolean("eric.finishing.flatSimulation")) {
            throw new IllegalStateException("Flat shim requires explicit -Deric.finishing.flatSimulation=true; not a live safety adapter");
        }
        this.station = station;
        if ("LID".equals(station)) { machine = "LidLoaderController"; }
        else if ("CAPPER".equals(station)) { machine = "CapperController"; }
        else if ("LABELLER".equals(station)) { machine = "LabelerController"; }
        else if ("UNLOADER".equals(station)) { machine = "UnloaderController"; }
        else { throw new IllegalArgumentException("UNKNOWN_STATION"); }
        System.out.println("FLAT SHIM SIMULATION: " + station
                + " assumes preconditions, grants boot permit, and auto-repairs safe simulated faults; no bottle retry");
    }

    public String action() {
        if ("BOOT_READY".equals(state) || "FIRST_REPORT".equals(state)
                || "TERMINAL".equals(state) || "CLEARED".equals(state) || "READY".equals(state)) { return "REPORT_IN"; }
        if ("BOOT_SEND".equals(state)) { return "CONTROL_OUT"; }
        if ("BOOT_REPLY".equals(state)) { return "CONTROL_IN"; }
        if ("PUBLISH_READY".equals(state) || "PUBLISH_BUSY".equals(state)
                || "PUBLISH_DONE".equals(state) || "PUBLISH_FAULT".equals(state)) { return "REPORT_OUT"; }
        if ("START".equals(state)) { return "COMMAND_IN"; }
        if ("JOB".equals(state)) { return "JOB_OUT"; }
        if ("WAIT_ACK".equals(state)) { return "ACK_IN"; }
        if ("ACK".equals(state) || "CLEAR".equals(state) || "RESET".equals(state)) { return "ACK_OUT"; }
        if ("HOLD".equals(state)) { return "HOLD"; }
        throw new IllegalStateException(state);
    }

    public String value() {
        if ("BOOT_SEND".equals(state)) { return "PERMIT|shim-boot-" + station + "|true"; }
        if ("PUBLISH_READY".equals(state)) { return "READY|" + station; }
        if ("JOB".equals(state)) { return richStart; }
        if ("ACK".equals(state)) { return "ACK|" + job + "|" + wp; }
        if ("CLEAR".equals(state)) { return "CLEAR_FAULT|" + job + "|" + wp; }
        if ("RESET".equals(state)) { return "RESET|" + job + "|" + wp; }
        if ("REPORT_OUT".equals(action())) { return outerReport; }
        return "";
    }

    public void sent() {
        if ("BOOT_SEND".equals(state)) { state = "BOOT_REPLY"; }
        else if ("PUBLISH_READY".equals(state)) { state = "START"; }
        else if ("JOB".equals(state)) { state = "FIRST_REPORT"; }
        else if ("PUBLISH_BUSY".equals(state)) { state = "TERMINAL"; }
        else if ("PUBLISH_DONE".equals(state)) { state = "WAIT_ACK"; }
        else if ("ACK".equals(state) || "RESET".equals(state)) { state = "READY"; }
        else if ("CLEAR".equals(state)) { state = "CLEARED"; }
        else if ("PUBLISH_FAULT".equals(state)) {
            // A rejected START has no latched controller fault to clear.
            state = rejected ? "READY" : (repairable ? "CLEAR" : "HOLD");
        } else { throw new IllegalStateException("Unexpected send: " + state); }
    }

    public void received(String wire) {
        if ("BOOT_READY".equals(state)) { expect(wire, "READY|" + machine); state = "BOOT_SEND"; return; }
        if ("BOOT_REPLY".equals(state)) {
            expect(wire, "CONTROL_ACK|shim-boot-" + station + "|" + machine);
            state = "PUBLISH_READY"; return;
        }
        if ("READY".equals(state)) { expect(wire, "READY|" + machine); state = "PUBLISH_READY"; return; }
        if ("CLEARED".equals(state)) {
            expect(wire, "CLEARED|" + richIds()); state = "RESET"; return;
        }
        if ("START".equals(state)) {
            try { prepare(wire); state = "JOB"; }
            catch (IllegalArgumentException invalid) {
                // No command reached the controller; it is already waiting on jobIn.
                outerReport = "FAULT|" + flatIds() + "|START_REJECTED_" + safeReason(invalid.getMessage());
                state = "LOCAL_REJECTION";
            }
            return;
        }
        if ("WAIT_ACK".equals(state)) {
            if (("ACK|" + job + "|" + wp).equals(wire)) { state = "ACK"; }
            else {
                ignoredAcks++;
                System.err.println("Flat shim " + station + " ignored unmatched ACK; successful job remains latched");
            }
            return;
        }
        if (!"FIRST_REPORT".equals(state) && !"TERMINAL".equals(state)) { throw new IllegalStateException(state); }
        String[] f = FinishingWire.fields(wire);
        if (f.length < 4 || !job.equals(f[1]) || !wp.equals(f[2]) || !machine.equals(f[3])) {
            throw new IllegalStateException("Controller report correlation mismatch: " + station);
        }
        if ("FIRST_REPORT".equals(state) && "BUSY".equals(f[0]) && f.length == 4) {
            outerReport = "BUSY|" + flatIds(); state = "PUBLISH_BUSY"; return;
        }
        if ("FIRST_REPORT".equals(state) && "REJECTED".equals(f[0])) {
            Map<String, String> data = FinishingWire.data(f, 4);
            rejected = true; repairable = false;
            outerReport = "FAULT|" + flatIds() + "|START_REJECTED_" + safeReason(data.get("reason"));
            state = "PUBLISH_FAULT"; return;
        }
        if (!"TERMINAL".equals(state)) { throw new IllegalStateException("Expected BUSY or REJECTED"); }
        Map<String, String> data = FinishingWire.data(f, 4);
        if ("DONE".equals(f[0])) {
            if (!"true".equals(data.get("safe"))) { throw new IllegalStateException("Unsafe DONE"); }
            for (String key : FinishingWire.requiredEvidence(machine)) {
                if (!"true".equals(data.get(key))) { throw new IllegalStateException("Incomplete DONE evidence"); }
            }
            if ("LABELLER".equals(station) && !label.equals(data.get("printedPayload"))) {
                throw new IllegalStateException("Label evidence mismatch");
            }
            StringBuilder flat = new StringBuilder("DONE|" + flatIds() + "|OK");
            for (Map.Entry<String, String> item : data.entrySet()) {
                String value = item.getValue();
                if ("printedPayload".equals(item.getKey())) { value = value.replace('|', ':'); }
                if (value.indexOf('|') >= 0 || value.indexOf('\r') >= 0 || value.indexOf('\n') >= 0) {
                    throw new IllegalStateException("Evidence cannot be represented losslessly on flat wire");
                }
                flat.append('|').append(item.getKey()).append('=').append(value);
            }
            outerReport = flat.toString(); state = "PUBLISH_DONE"; return;
        }
        if ("FAULT".equals(f[0])) {
            rejected = false; repairable = "true".equals(data.get("safe"));
            outerReport = "FAULT|" + flatIds() + "|" + safeReason(data.get("reason"));
            state = "PUBLISH_FAULT"; return;
        }
        throw new IllegalStateException("Expected terminal report");
    }

    private void prepare(String wire) {
        job = "UNKNOWN"; wp = "UNKNOWN"; label = ""; rejected = false; repairable = false;
        String[] f = FinishingWire.fields(wire);
        if (f.length > 1) { job = FinishingWire.id(f[1]); }
        if (f.length > 2) { wp = FinishingWire.id(f[2]); }
        if (f.length < 5 || !"START".equals(f[0])) { throw new IllegalArgumentException("INVALID_START"); }
        if (!station.equals(f[3])) { throw new IllegalArgumentException("WRONG_STATION"); }
        if (!FinishingWire.operation(machine).name().equals(f[4])) { throw new IllegalArgumentException("WRONG_OPERATION"); }
        Map<String, String> data = new LinkedHashMap<String, String>();
        data.put("bottlePresent", "true");
        if ("LID".equals(station)) { data.put("filled", "true"); data.put("lidAvailable", "true"); }
        if ("CAPPER".equals(station)) { data.put("lidPresent", "true"); }
        if ("UNLOADER".equals(station)) { data.put("collectionAvailable", "true"); }
        int extra = 5;
        if ("LABELLER".equals(station)) {
            // Tony's restricted label grammar: two colon-free ID components only.
            if (f.length < 6 || !f[5].matches("[A-Za-z0-9_.-]{1,100}:[A-Za-z0-9_.-]{1,100}")) {
                throw new IllegalArgumentException("LABEL_REQUIRES_TWO_COLON_FREE_IDS");
            }
            label = f[5].replace(':', '|');
            data.put("manufacturingComplete", "true"); data.put("labelPayload", label); extra = 6;
        }
        // Explicit test-only extension for real plant fault injection. No default random failures.
        for (int i = extra; i < f.length; i++) {
            if (!Boolean.getBoolean("eric.finishing.testMode")) { throw new IllegalArgumentException("EXTRA_DATA_NOT_SUPPORTED"); }
            int split = f[i].indexOf('=');
            if (split < 1) { throw new IllegalArgumentException("INVALID_TEST_DATA"); }
            String key = f[i].substring(0, split);
            if (!("simMode".equals(key) || "timeoutTicks".equals(key)) || data.containsKey(key)) {
                throw new IllegalArgumentException("INVALID_TEST_DATA");
            }
            data.put(key, f[i].substring(split + 1));
        }
        richStart = "START|" + richIds() + "|" + f[4] + FinishingWire.data(data);
        FinishingWire.start(richStart, machine);
    }

    // Local rejection does not consume a READY from a controller that never received START.
    public String nextAction() { return "LOCAL_REJECTION".equals(state) ? "REPORT_OUT" : action(); }
    public String nextValue() { return "LOCAL_REJECTION".equals(state) ? outerReport : value(); }
    public void afterSend() { if ("LOCAL_REJECTION".equals(state)) { state = "PUBLISH_READY"; } else { sent(); } }
    public int ignoredAcknowledgements() { return ignoredAcks; }
    private String richIds() { return job + "|" + wp + "|" + machine; }
    private String flatIds() { return job + "|" + wp + "|" + station; }
    private static void expect(String actual, String expected) {
        if (!expected.equals(actual)) { throw new IllegalStateException("Expected " + expected + ", received " + actual); }
    }
    private static String safeReason(String reason) {
        return reason == null ? "UNSPECIFIED" : reason.replace('|', '/').replace('\r', ' ').replace('\n', ' ');
    }
}
