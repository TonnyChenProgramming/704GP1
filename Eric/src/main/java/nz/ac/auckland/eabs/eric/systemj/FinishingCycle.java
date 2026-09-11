package nz.ac.auckland.eabs.eric.systemj;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Per-controller state, codec and evidence checks; SystemJ owns communication/timing. */
public final class FinishingCycle {
    private final String machine;
    private final Set<String> acceptedJobs = new HashSet<String>();
    private final Map<String, String> controlRequests = new LinkedHashMap<String, String>();
    private FinishingJob job;
    private String state = "READY", reply, failure = "", pending = "";
    private boolean permit, stop, begun, safeConfirmed;
    private int phase, polls, limit;
    private Map<String, String> lastEvidence = new LinkedHashMap<String, String>();

    public FinishingCycle(String machine) {
        FinishingWire.operation(machine);
        this.machine = machine;
        reply = "READY|" + machine;
    }

    /** A separate SystemJ reaction keeps this input responsive during a plant cycle. */
    public synchronized String control(String wire) {
        String requestId = "UNKNOWN";
        try {
            String[] f = FinishingWire.fields(wire);
            if (f.length < 2) { throw new IllegalArgumentException(); }
            requestId = FinishingWire.id(f[1]);
            String previous = controlRequests.get(requestId);
            if (previous != null) {
                return (previous.equals(wire) ? "CONTROL_DUPLICATE|" : "CONTROL_REJECTED|") + requestId + "|" + machine;
            }
            if ("PERMIT".equals(f[0]) && f.length == 3 && ("true".equals(f[2]) || "false".equals(f[2]))) {
                permit = Boolean.parseBoolean(f[2]);
                if (!permit) { stop = true; failIfBusy("SAFETY_LOST"); }
            } else if ("STOP".equals(f[0]) && f.length == 2) {
                stop = true; failIfBusy("SAFE_STOP");
            } else if ("CLEAR_STOP".equals(f[0]) && f.length == 2 && permit) {
                stop = false; // Never clears the active job's fault or restarts it.
            } else { throw new IllegalArgumentException(); }
            controlRequests.put(requestId, wire);
            return "CONTROL_ACK|" + requestId + "|" + machine;
        } catch (IllegalArgumentException invalid) {
            return "CONTROL_REJECTED|" + requestId + "|" + machine;
        }
    }

    public synchronized boolean accept(String wire) {
        if (!"READY".equals(state)) { throw new IllegalStateException("Controller is latched"); }
        String jobId = "UNKNOWN", workpieceId = "UNKNOWN";
        try {
            String[] raw = FinishingWire.fields(wire);
            if (raw.length >= 3) { jobId = FinishingWire.id(raw[1]); workpieceId = FinishingWire.id(raw[2]); }
            if (raw.length == 0 || !"START".equals(raw[0])) { throw new IllegalArgumentException("INVALID_START"); }
            FinishingJob next = FinishingWire.start(wire, machine);
            if (acceptedJobs.contains(next.getJobId())) { throw new IllegalArgumentException("DUPLICATE_JOB"); }
            if (!permit || stop) { throw new IllegalArgumentException("SAFETY_NOT_PERMITTED"); }
            job = next;
            acceptedJobs.add(job.getJobId());
            limit = Integer.parseInt(job.getOperationData().containsKey("timeoutTicks") ? job.getOperationData().get("timeoutTicks") : "20");
            phase = 0; polls = 0; begun = false; safeConfirmed = false;
            failure = ""; pending = ""; lastEvidence.clear(); state = "BUSY";
            reply = "BUSY|" + FinishingWire.correlation(job);
            return true;
        } catch (IllegalArgumentException invalid) {
            Map<String, String> data = new LinkedHashMap<String, String>();
            data.put("reason", invalid instanceof NumberFormatException ? "INVALID_NUMBER" : invalid.getMessage());
            reply = "REJECTED|" + jobId + "|" + workpieceId + "|" + machine + FinishingWire.data(data);
            return false;
        }
    }

    public synchronized String nextPlantCommand() {
        if (!"BUSY".equals(state)) { throw new IllegalStateException("No active cycle"); }
        if (!permit || stop) { failIfBusy("SAFETY_NOT_PERMITTED"); }
        if (failure.isEmpty() && begun && polls >= limit) { failIfBusy("TIMEOUT"); }
        if (!failure.isEmpty()) { pending = "ABORT"; return "ABORT|" + FinishingWire.correlation(job); }
        if (!begun) {
            pending = "BEGIN";
            return "BEGIN|" + FinishingWire.correlation(job) + "|" + job.getOperation().name() + FinishingWire.data(job.getOperationData());
        }
        pending = "STEP"; polls++;
        return "STEP|" + FinishingWire.correlation(job) + "|" + (phase + 1);
    }

    public synchronized void observe(String wire) {
        if (!"BUSY".equals(state)) { throw new IllegalStateException("No outstanding plant cycle"); }
        try {
            String[] f = FinishingWire.fields(wire);
            if (f.length < 6 || !"EVIDENCE".equals(f[0]) || !FinishingWire.matches(f, job)) {
                throw new IllegalArgumentException("BAD_EVIDENCE_CORRELATION");
            }
            Map<String, String> data = FinishingWire.data(f, 5);
            int observedPhase = Integer.parseInt(data.get("phase"));
            boolean safe = "true".equals(data.get("safe")) && "false".equals(data.get("active"));
            if ("ABORT".equals(pending)) {
                if (!"ABORTED".equals(f[4]) || !safe) { throw new IllegalArgumentException("STOP_UNCONFIRMED"); }
                safeConfirmed = true; lastEvidence = data;
                terminal("FAULT");
                return;
            }
            if ("FAULT".equals(f[4]) || "DENIED".equals(f[4])) {
                failIfBusy("PLANT_" + data.get("reason"));
                return; // The next step is ABORT, never a fabricated successful result.
            }
            if ("BEGIN".equals(pending)) {
                if (!"PREPARED".equals(f[4]) || observedPhase != 0) { throw new IllegalArgumentException("INVALID_PREPARE"); }
                begun = true;
                return;
            }
            if (!"STEP".equals(pending) || observedPhase < phase || observedPhase > phase + 1) {
                throw new IllegalArgumentException("INVALID_PHASE_EVIDENCE");
            }
            if ("PROGRESS".equals(f[4])) { phase = observedPhase; return; }
            if (!"COMPLETE".equals(f[4]) || observedPhase != phase + 1 || !safe) {
                throw new IllegalArgumentException("INVALID_COMPLETION");
            }
            for (String key : FinishingWire.requiredEvidence(machine)) {
                if (!"true".equals(data.get(key))) { throw new IllegalArgumentException("MISSING_COMPLETION_EVIDENCE"); }
            }
            if ("LabelerController".equals(machine) && !job.requireData("labelPayload").equals(data.get("printedPayload"))) {
                throw new IllegalArgumentException("LABEL_PAYLOAD_MISMATCH");
            }
            if (Integer.parseInt(data.get("simulatedCycleTicks")) != observedPhase) {
                throw new IllegalArgumentException("INVALID_CYCLE_TIMING");
            }
            // Safety may have changed while the controller was waiting for the plant reply.
            if (!permit || stop) { failIfBusy("SAFETY_NOT_PERMITTED"); }
            if (!failure.isEmpty()) { return; }
            safeConfirmed = true; phase = observedPhase; lastEvidence = data;
            terminal("DONE");
        } catch (RuntimeException invalid) {
            failIfBusy("BAD_PLANT_EVIDENCE");
            if ("ABORT".equals(pending)) {
                safeConfirmed = false; lastEvidence.clear();
                terminal("FAULT"); // No reset is allowed without a confirmed safe plant.
            }
        }
    }

    /** Returns a plant CLEAR/RESET command, or empty for an immediate ACK response. */
    public synchronized String acknowledge(String wire) {
        try {
            String[] f = FinishingWire.fields(wire);
            if (!latched() || f.length != 3 || !job.getJobId().equals(f[1]) || !job.getWorkpieceId().equals(f[2])) {
                throw new IllegalArgumentException("WRONG_ACK_CORRELATION");
            }
            if ("ACK".equals(f[0]) && "DONE".equals(state)) {
                state = "READY"; job = null; reply = "READY|" + machine; return "";
            }
            if ("FAULT".equals(state) && ("CLEAR_FAULT".equals(f[0]) || "RESET".equals(f[0]))) {
                if (!permit || stop || !safeConfirmed) { throw new IllegalArgumentException("UNSAFE_RESET"); }
                pending = "CLEAR_FAULT".equals(f[0]) ? "CLEAR" : "RESET";
                return pending + "|" + FinishingWire.correlation(job);
            }
            throw new IllegalArgumentException("INVALID_ACK_STATE");
        } catch (IllegalArgumentException invalid) {
            reply = acknowledgementRejected(invalid.getMessage()); return "";
        }
    }

    public synchronized void finishAcknowledgement(String wire) {
        try {
            String[] f = FinishingWire.fields(wire);
            if (f.length < 6 || !"EVIDENCE".equals(f[0]) || !FinishingWire.matches(f, job)) { throw new IllegalArgumentException(); }
            Map<String, String> data = FinishingWire.data(f, 5);
            if (!permit || stop || !"true".equals(data.get("safe")) || !"false".equals(data.get("active"))) {
                throw new IllegalArgumentException();
            }
            if ("CLEAR".equals(pending) && "CLEARED".equals(f[4])) {
                reply = "CLEARED|" + FinishingWire.correlation(job); return;
            }
            if ("RESET".equals(pending) && "RESET".equals(f[4])) {
                state = "READY"; job = null; reply = "READY|" + machine; return;
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException invalid) { reply = acknowledgementRejected("PLANT_RESET_NOT_CONFIRMED"); }
    }

    private String acknowledgementRejected(String reason) {
        String ids = job == null ? "UNKNOWN|UNKNOWN|" + machine : FinishingWire.correlation(job);
        return "ACK_REJECTED|" + ids + "|reason=" + reason;
    }
    private void failIfBusy(String reason) {
        if ("BUSY".equals(state) && failure.isEmpty()) { failure = reason; }
    }
    private void terminal(String outcome) {
        state = outcome;
        Map<String, String> data = new LinkedHashMap<String, String>(lastEvidence);
        data.put("safe", Boolean.toString(safeConfirmed));
        if (!failure.isEmpty()) { data.put("reason", failure); }
        reply = outcome + "|" + FinishingWire.correlation(job) + FinishingWire.data(data);
    }
    public synchronized boolean latched() { return "DONE".equals(state) || "FAULT".equals(state); }
    public synchronized String reply() { return reply; }
    public synchronized String state() { return state; }
}
