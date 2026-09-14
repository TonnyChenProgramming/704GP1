package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.*;
import java.nio.file.Paths;
import nz.ac.auckland.eabs.eric.model.*;
import nz.ac.auckland.eabs.eric.tracking.*;
import nz.ac.auckland.eabs.eric.persistence.*;
import nz.ac.auckland.eabs.eric.gui.*;
import nz.ac.auckland.eabs.eric.systemj.FinishingWire;

/** Per-CoordinatorCD state; never a static cross-CD registry. SystemJ owns all I/O. */
public final class IntegratedCoordinator {
    public static final String[] STATIONS = {"LOADER", "CONVEYOR", "FILLER", "LID", "CAPPER", "LABELLER", "UNLOADER"};
    private static final List<Operation> ROUTE = Collections.unmodifiableList(Arrays.asList(
        Operation.LOAD_BOTTLE, Operation.MOVE_TO_ROTARY, Operation.FILL_TWO_LIQUIDS,
        Operation.PLACE_LID, Operation.CAP_BOTTLE, Operation.MOVE_TO_LABELLER,
        Operation.APPLY_LABEL, Operation.UNLOAD));
    private static final Location[] POSITIONS = {Location.ROTARY_P1, Location.ROTARY_P2_FILLER,
        Location.ROTARY_P3_LID, Location.ROTARY_P4_CAPPER, Location.ROTARY_P5_EXIT, Location.ROTARY_P6};
    private final Map<String, Station> machines = new LinkedHashMap<String, Station>();
    private final Set<String> usedBatches = new HashSet<String>();
    private final String[] table = new String[6];
    private final WorkpieceTracker tracker;
    private final VisualizationBridge view;
    private String batch = "", product = "", recipe = "", order = "";
    private String input = null, label = null, output = null;
    private String fault = "", response = "";
    private int quantity, admitted, completed, doseA, doseB, jobSequence, rotations;
    private String phase = "WAIT_ORDER";
    private long deadline, startupDeadline;
    private final long timeoutNanos;
    private boolean rotating, rotationOffered, labelDone, unsafe;
    private long lastPublish;

    private static final class Station {
        boolean ready, taken, busy;
        FinishingJob job;
        long deadline;
    }

    public IntegratedCoordinator() {
        this(new WorkpieceTracker(new RecoverableFileWorkpieceRepository(Paths.get(
            System.getProperty("coordinator.archive", "build/coordinator-" + UUID.randomUUID() + ".properties")))), 15000);
    }

    public IntegratedCoordinator(WorkpieceTracker tracker, long timeoutMillis) {
        this.tracker = tracker;
        timeoutNanos = timeoutMillis * 1000000L;
        startupDeadline = System.nanoTime() + timeoutNanos;
        for (String station : STATIONS) machines.put(station, new Station());
        view = new VisualizationBridge(new OperatorCommandSink() {
            public void submit(OperatorCommand command) { throw new IllegalStateException("Read-only integration monitor; POS controls are not wired"); }
        });
        if (Boolean.getBoolean("coordinator.gui")) javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() {
            javax.swing.JFrame frame = new javax.swing.JFrame("GP integrated SystemJ line - read-only");
            frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new EabsDashboardPanel(view, EabsDashboardPanel.Mode.READ_ONLY));
            frame.setSize(1250, 850); frame.setVisible(true);
        }});
    }

    /** Explicit recipe amounts and order identity, no fabricated defaults. */
    public synchronized void activate(String frame) {
        if (!phase.equals("WAIT_ORDER") || !fault.isEmpty()) { fail("ACTIVATE_WHILE_NOT_IDLE"); return; }
        String[] f = frame.split("\\|", -1);
        try {
            if (f.length != 8 || !f[0].equals("ACTIVATE")) throw new IllegalArgumentException();
            for (int i : new int[]{1, 2, 3, 7}) if (!f[i].matches("[A-Za-z0-9_.-]{1,60}")) throw new IllegalArgumentException();
            int q = Integer.parseInt(f[4]), a = Integer.parseInt(f[5]), b = Integer.parseInt(f[6]);
            if (q < 1 || q > 10000 || a < 0 || b < 0 || a > 100 || b > 100 || a+b < 1 || a+b > 100 || usedBatches.contains(f[1])) throw new IllegalArgumentException();
            batch = f[1]; recipe = f[2]; product = f[3]; order = f[7];
            quantity = q; doseA = a; doseB = b; admitted = 0; completed = 0; rotations = 0;
            usedBatches.add(batch); phase = "DISPATCH";
            System.out.println("COORDINATOR ACTIVATE " + batch + " quantity=" + quantity + " doses=" + a + "/" + b);
        } catch (IllegalArgumentException invalid) {
            response = "REJECTED|" + (f.length > 1 && f[1].matches("[A-Za-z0-9_.-]{1,60}") ? f[1] : "UNKNOWN") + "|INVALID_BATCH_OR_RECIPE";
        }
    }

    public synchronized boolean awaitingOrder() { return phase.equals("WAIT_ORDER") && fault.isEmpty() && response.isEmpty(); }
    public synchronized String response() { return response; }
    public synchronized void responseSent() { response = ""; }
    public synchronized String phase() { return phase; }
    public synchronized int completed() { return completed; }
    public synchronized int rotations() { return rotations; }
    public synchronized String fault() { return fault; }
    public synchronized List<WorkpieceSnapshot> snapshots() { return tracker.activeSnapshots(); }

    public synchronized void ready(String name, String frame) {
        Station s = machines.get(name);
        if (!frame.equals("READY|" + name) || s.job != null) { fail("INVALID_READY_" + name); return; }
        s.ready = true; s.busy = false; s.deadline = 0;
    }

    public synchronized String take(String name) {
        Station s = machines.get(name);
        if (!fault.isEmpty() || s.job == null || s.taken) return "";
        s.taken = true;
        FinishingJob j = s.job;
        String start = "START|" + j.getJobId() + "|" + j.getWorkpieceId() + "|" + name + "|" + j.getOperation();
        if (name.equals("FILLER")) start += "|" + doseA + "|" + doseB;
        if (name.equals("LABELLER")) start += "|" + j.getWorkpieceId() + ":" + batch;
        if (name.equals("LID") && Boolean.getBoolean("eric.finishing.testMode") && Boolean.getBoolean("coordinator.testLidFault")) start += "|simMode=FAULT";
        return start;
    }

    /** Returns ACK only for a correlated, validated DONE. FAULT never gets ACK. */
    public synchronized String report(String name, String frame) {
        Station s = machines.get(name);
        String[] f = frame.split("\\|", -1);
        FinishingJob j = s.job;
        if (j == null || !s.taken || f.length < 4 || !f[1].equals(j.getJobId())
                || !f[2].equals(j.getWorkpieceId()) || !f[3].equals(name)) {
            fail("MISMATCHED_REPORT_" + name); return "";
        }
        if (f[0].equals("BUSY") && f.length == 4 && !s.busy) {
            tracker.acceptReport(MachineReport.busy(j)); s.busy = true; return "";
        }
        if (f[0].equals("FAULT") && f.length >= 5) {
            tracker.acceptReport(MachineReport.fault(j, f[4]));
            publishTwinTransition(j.getWorkpieceId(), name.toLowerCase(), "FAULT");
            fail(name + "_" + f[4]); return "";
        }
        if (!f[0].equals("DONE") || f.length < 6 || !f[4].equals("OK") || !s.busy) {
            fail("INVALID_RESULT_" + name); return "";
        }
        Map<String, String> evidence = new LinkedHashMap<String, String>();
        for (int i=5; i<f.length; i++) {
            int split = f[i].indexOf('=');
            if (split <= 0 || split == f[i].length()-1 || evidence.put(f[i].substring(0,split), f[i].substring(split+1)) != null) {
                fail("INVALID_EVIDENCE_" + name); return "";
            }
        }
        if (name.equals("LOADER") && !"true".equals(evidence.get("bottlePlaced"))
        	    || name.equals("CONVEYOR") && !"true".equals(evidence.get("bottleMoved"))
        	    || name.equals("FILLER") && (evidence.get("liquidA") == null
        	        || evidence.get("liquidB") == null)) {
        	    fail("MISSING_COMPLETION_EVIDENCE_" + name); return "";
        	}
        String richMachine = name.equals("LID") ? "LidLoaderController" : name.equals("CAPPER") ? "CapperController"
            : name.equals("LABELLER") ? "LabelerController" : name.equals("UNLOADER") ? "UnloaderController" : "";
        if (!richMachine.isEmpty()) {
            if (!"true".equals(evidence.get("safe"))) { fail("UNSAFE_DONE_" + name); return ""; }
            for (String key : FinishingWire.requiredEvidence(richMachine)) {
                if (!"true".equals(evidence.get(key))) { fail("MISSING_COMPLETION_EVIDENCE_" + name); return ""; }
            }
            if (name.equals("LABELLER") && !(j.getWorkpieceId()+":"+batch).equals(evidence.get("printedPayload"))) {
                fail("LABEL_MISMATCH"); return "";
            }
        }
	     // Filler returns simulated plant-side actual amounts.
	     // Keep commanded and actual values separate.
        if (name.equals("FILLER")) {
            evidence.put("commandedLiquidA", Integer.toString(doseA));
            evidence.put("commandedLiquidB", Integer.toString(doseB));
        }
        tracker.acceptReport(MachineReport.done(j, "OK", evidence));
        String wp = j.getWorkpieceId();
        if (name.equals("LOADER")) {
            input = wp; admitted++; tracker.confirmLocation(wp, Location.INPUT_CONVEYOR, 0, frame);
            publishTwinAdmission(wp, product);
        } else if (j.getOperation() == Operation.MOVE_TO_ROTARY) {
            table[0] = wp; input = null; tracker.confirmLocation(wp, POSITIONS[0], 1, frame);
        } else if (j.getOperation() == Operation.MOVE_TO_LABELLER) {
            table[4] = null; label = wp; tracker.confirmLocation(wp, Location.LABELLER, 0, frame);
        } else if (name.equals("LABELLER")) {
            labelDone = true;
        } else if (name.equals("UNLOADER")) {
            tracker.confirmLocation(wp, Location.OUTPUT_COLLECTION, 0, frame);
            try { tracker.completeAndArchive(wp, wp + ":" + batch); }
            catch (java.io.IOException failure) { fail("ARCHIVE_FAILED"); return ""; }
            output = null; completed++;
            System.out.println("COORDINATOR COMPLETED " + wp + " " + completed + "/" + quantity);
        }
        publishTwinTransition(wp, twinLocationFor(name, j.getOperation()), "DONE");
        s.job = null; s.busy = false; // READY is still required after ACK.
        s.deadline = System.nanoTime() + timeoutNanos;
        return "ACK|" + j.getJobId() + "|" + wp;
    }

    private void queue(String name, String wp, Operation operation) {
        Station s = machines.get(name);
        if (!s.ready || s.job != null) throw new IllegalStateException("Station unavailable " + name);
        FinishingJob job = new FinishingJob("J-" + (++jobSequence), wp, name, operation, null);
        tracker.dispatch(job); s.job = job; s.ready = false; s.taken = false; s.busy = false;
        s.deadline = System.nanoTime() + timeoutNanos;
    }

    /** Tick does not block. Seven SystemJ I/O reactions service the pending jobs. */
    public synchronized void tick() {
        long now = System.nanoTime();
        for (Map.Entry<String, Station> e : machines.entrySet()) {
            Station s = e.getValue();
            if (s.deadline != 0 && now > s.deadline) fail("TIMEOUT_" + e.getKey());
            if (!s.ready && s.job == null && s.deadline == 0 && now > startupDeadline) fail("STARTUP_READY_TIMEOUT_" + e.getKey());
        }
        if (rotating && now > deadline) fail("TABLE_ALIGNMENT_TIMEOUT");
        if (!fault.isEmpty()) { publish(now); return; }
        if (phase.equals("DISPATCH") && allReady()) {
            if (completed == quantity) {
                if (input != null || label != null || output != null || tableOccupied()) throw new IllegalStateException("Cannot drain occupied line");
                response = "DRAINED|" + batch; phase = "WAIT_ORDER";
                System.out.println("COORDINATOR DRAINED " + batch + " rotations=" + rotations);
            } else {
                if (output != null) queue("UNLOADER", output, Operation.UNLOAD);
                if (label != null && !labelDone) queue("LABELLER", label, Operation.APPLY_LABEL);
                if (table[1] != null) queue("FILLER", table[1], Operation.FILL_TWO_LIQUIDS);
                if (table[2] != null) queue("LID", table[2], Operation.PLACE_LID);
                if (table[3] != null) queue("CAPPER", table[3], Operation.CAP_BOTTLE);
                if (admitted < quantity && input == null && table[0] == null) {
                    String wp = "WP-" + batch + "-" + (admitted+1);
                    tracker.createTwin(wp, order, batch, product, recipe + ":" + doseA + "/" + doseB + " simulation-units", ROUTE);
                    queue("LOADER", wp, Operation.LOAD_BOTTLE);
                }
                phase = "WAIT_OPERATIONS";
            }
        } else if (phase.equals("WAIT_OPERATIONS") && allReady()) {
            // Free downstream capacity BEFORE checking whether P5 may leave.
            if (labelDone && output == null) { output = label; label = null; labelDone = false; }
            if (table[4] != null && label == null) queue("CONVEYOR", table[4], Operation.MOVE_TO_LABELLER);
            phase = "WAIT_OUTPUT_TRANSFER";
        } else if (phase.equals("WAIT_OUTPUT_TRANSFER") && allReady()) {
            if (input != null && table[0] == null) queue("CONVEYOR", input, Operation.MOVE_TO_ROTARY);
            phase = "WAIT_INPUT_TRANSFER";
        } else if (phase.equals("WAIT_INPUT_TRANSFER") && allReady()) {
            if (table[4] != null || table[5] != null) { fail("EXIT_NOT_CLEAR_FOR_INDEX"); }
            else if (tableOccupied()) {
                rotating = true; rotationOffered = false; deadline = now + timeoutNanos; phase = "WAIT_ALIGNMENT";
            } else { phase = "DISPATCH"; }
        }
        publish(now);
    }

    public synchronized boolean requestRotation() {
        if (!rotating || rotationOffered || !fault.isEmpty()) return false;
        rotationOffered = true; return true;
    }

    public synchronized void aligned() {
        if (!rotating || !rotationOffered || !fault.isEmpty()) { fail("UNEXPECTED_ALIGNMENT"); return; }
        // One physical 60-degree index moves ALL six positions atomically.
        String p6 = table[5];
        for (int p=5; p>0; p--) table[p] = table[p-1];
        table[0] = p6;
        for (int p=0; p<6; p++) if (table[p] != null) tracker.confirmLocation(table[p], POSITIONS[p], p+1, "tableAligned index=" + (rotations+1));
        rotations++; rotating = false; phase = "DISPATCH";
    }

    public synchronized void hazard() { unsafe = true; fail("SAFETY_PERMIT_LOST_MANUAL_RECOVERY_REQUIRED"); }

    /** Sensor condition cleared. Does not by itself resume production: reset() still requires it. */
    public synchronized void permitRestored() { unsafe = false; }

    /** Operator acknowledgement. Only the safety hold is recoverable, and only once the hazard is cleared.
     * Reconciles the line by archiving every open workpiece as ABORTED before resuming from WAIT_ORDER,
     * per "Open or incomplete bottles are marked ABORTED and removed before resumption" in the brief. */
    public synchronized void reset() {
        if (unsafe || !fault.equals("SAFETY_PERMIT_LOST_MANUAL_RECOVERY_REQUIRED")) return;
        try {
            if (input != null) { tracker.abortAndArchive(input, "SAFETY_STOP"); publishTwinTransition(input, "input", "ABORTED"); input = null; }
            for (int p = 0; p < 6; p++) {
                if (table[p] != null) { tracker.abortAndArchive(table[p], "SAFETY_STOP"); publishTwinTransition(table[p], "table" + p, "ABORTED"); table[p] = null; }
            }
            if (label != null) { tracker.abortAndArchive(label, "SAFETY_STOP"); publishTwinTransition(label, "label", "ABORTED"); label = null; }
            if (output != null) { tracker.abortAndArchive(output, "SAFETY_STOP"); publishTwinTransition(output, "output", "ABORTED"); output = null; }
        } catch (java.io.IOException failure) {
            System.err.println("COORDINATOR RESET_FAILED archive error, remaining on hold: " + failure.getMessage());
            return;
        }
        for (Station s : machines.values()) { s.job = null; s.taken = false; s.busy = false; s.deadline = 0; }
        rotating = false; rotationOffered = false; labelDone = false;
        fault = ""; response = ""; phase = "WAIT_ORDER";
        System.out.println("COORDINATOR SAFETY RESET -- line reconciled, ready for next order.");
    }

    private void fail(String reason) {
        if (!fault.isEmpty()) return;
        fault = reason; phase = "HOLD"; response = "FAULT|" + (batch.isEmpty() ? "NONE" : batch) + "|" + reason;
        System.err.println("COORDINATOR HOLD " + reason);
        // Never erase occupancy, fabricate completion, retry a bottle, or auto-reset.
    }
    private boolean allReady() { for (Station s : machines.values()) if (!s.ready || s.job != null) return false; return true; }
    private boolean tableOccupied() { for (String wp : table) if (wp != null) return true; return false; }

    /** Feeds the IP's real-time digital twin (IP report Section 4). Self-gating: becomes a
     * genuine no-op whenever batch/recipe are not the numeric database ids IpBatchManagerCD
     * supplies (e.g. the scripted harness's "B1"/"F1", or the interactive console's own typed
     * batch ids), so coordinator.xml/coordinator_real.xml stay completely unaffected. Never
     * allowed to throw: a problem here must never destabilise the coordinator's own control
     * flow, and TwinEventBus itself never blocks (a non-blocking, non-throwing queue offer). */
    private void publishTwinAdmission(String workpieceId, String productId) {
        try {
            TwinEventBus.publishAdmission(workpieceId, Integer.parseInt(batch), Integer.parseInt(recipe), productId);
        } catch (RuntimeException ignored) {
            // Non-numeric batch/recipe (scripted harness or console-typed ids) -- no digital twin to feed.
        }
    }

    private void publishTwinTransition(String workpieceId, String location, String status) {
        try {
            TwinEventBus.publishTransition(workpieceId, Integer.parseInt(batch), location, status);
        } catch (RuntimeException ignored) {
        }
    }

    /** Station-name to digital-twin location label. Conveyor moves a bottle twice (into the
     * rotary table, then out to the labeller), so it needs its operation to disambiguate;
     * every other station occupies exactly one label. */
    private static String twinLocationFor(String name, Operation operation) {
        if (name.equals("CONVEYOR")) {
            return operation == Operation.MOVE_TO_ROTARY ? "conveyor_in" : "conveyor_out";
        }
        return name.toLowerCase();
    }

    private void publish(long now) {
        if (now - lastPublish < 100000000L) return;
        lastPublish = now;
        Map<String, MachineState> states = new LinkedHashMap<String, MachineState>();
        for (Map.Entry<String, Station> e : machines.entrySet()) states.put(e.getKey(), e.getValue().ready ? MachineState.READY : e.getValue().job != null ? MachineState.BUSY : MachineState.OFFLINE);
        Map<Integer, String> occupancy = new LinkedHashMap<Integer, String>();
        for (int p=0; p<6; p++) if (table[p] != null) occupancy.put(p+1, table[p]);
        view.publish(new DashboardState(order, product, batch, recipe, quantity, completed, 0,
            tracker.activeSnapshots().size(), Math.max(0, quantity-completed), !unsafe,
            unsafe ? fault : "Safety permit active", phase + (fault.isEmpty() ? "" : ": " + fault), states, occupancy, tracker.activeSnapshots()));
    }
}
