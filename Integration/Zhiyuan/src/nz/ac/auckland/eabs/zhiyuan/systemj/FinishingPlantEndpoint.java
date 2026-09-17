package nz.ac.auckland.eabs.eric.systemj;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.PlantResult;
import nz.ac.auckland.eabs.eric.plants.*;
import java.util.LinkedHashMap;
import java.util.Map;

/** Plant-owned timed-step model. No tracker, coordinator or controller reference. */
public final class FinishingPlantEndpoint {
    private final String machine;
    private final FinishingPlant model;
    private FinishingJob job;
    private PlantResult planned;
    private int phase, polls, cycles;
    private boolean active, safe = true, fault;
    private String mode = "NORMAL";

    public FinishingPlantEndpoint(String machine) {
        this.machine = machine;
        FinishingWire.operation(machine);
        if ("LidLoaderController".equals(machine)) { model = new LidLoaderPlant(); }
        else if ("CapperController".equals(machine)) { model = new CapperPlant(); }
        else if ("LabelerController".equals(machine)) { model = new LabelerPlant(); }
        else { model = new UnloaderPlant(); }
    }

    /** Called after the plant CD's timed STEP/ABORT delay, never by the controller. */
    public synchronized String accept(String command) {
        try {
            String[] f = FinishingWire.fields(command);
            if ("BEGIN".equals(f[0])) {
                FinishingJob next = FinishingWire.start(command, machine);
                if (active || !safe || fault) { return response(next, "DENIED", "PLANT_NOT_RESET"); }
                job = next; phase = 0; polls = 0; cycles++;
                mode = job.getOperationData().containsKey("simMode") ? job.getOperationData().get("simMode") : "NORMAL";
                // Reuse the existing device-specific preconditions and final evidence schema.
                // These predicted terminal values are NOT exposed until timed phases finish.
                planned = model.execute(job);
                if (!planned.isSuccessful()) { fault = true; return response(job, "FAULT", "PRECONDITION"); }
                active = true;
                return response(job, "PREPARED", "");
            }
            if (f.length < 4 || !machine.equals(f[3])) { throw new IllegalArgumentException("INVALID_COMMAND"); }
            if ("ABORT".equals(f[0]) && !active && safe && !FinishingWire.matches(f, job) && f.length == 4) {
                FinishingJob idleAbort = new FinishingJob(FinishingWire.id(f[1]), FinishingWire.id(f[2]), machine,
                        FinishingWire.operation(machine), null);
                job = idleAbort; fault = true; phase = 0; polls = 0;
                return response(job, "ABORTED", "NO_CYCLE_STARTED");
            }
            if (!FinishingWire.matches(f, job)) { return response(job, "DENIED", "WRONG_CORRELATION"); }
            if ("STEP".equals(f[0]) && f.length == 5) {
                if (!active || fault || Integer.parseInt(f[4]) != phase + 1) {
                    return response(job, "DENIED", "INVALID_PHASE");
                }
                polls++; safe = false;
                if ("FAULT".equals(mode) && polls == 2) {
                    fault = true;
                    return response(job, "FAULT", "SIMULATED_JAM");
                }
                if (!"STALL".equals(mode)) { phase++; }
                int duration = Integer.parseInt(planned.getSensorEvidence().get("simulatedCycleTicks"));
                if (phase >= duration) {
                    active = false; safe = true;
                    return response(job, "COMPLETE", "");
                }
                String report = response(job, "PROGRESS", "");
                if ("BAD_CORRELATION".equals(mode) && polls == 1) {
                    report = report.replace("|" + job.getWorkpieceId() + "|", "|STALE-WORKPIECE|");
                }
                return report;
            }
            if (f.length != 4) { throw new IllegalArgumentException("INVALID_COMMAND"); }
            if ("ABORT".equals(f[0])) {
                // Abstract safe-return/de-energisation sequence; the CD supplies its delay.
                // A stopped partial workpiece is NOT a completed/reusable product.
                active = false; safe = true; fault = true;
                return response(job, "ABORTED", "SAFE_RETURN_CONFIRMED");
            }
            if ("CLEAR".equals(f[0]) && safe && !active) {
                fault = false; model.clearFault();
                return response(job, "CLEARED", "SIMULATED_REPAIR_CONFIRMED");
            }
            if ("RESET".equals(f[0]) && safe && !active && !fault) {
                return response(job, "RESET", "");
            }
            return response(job, "DENIED", "PLANT_NOT_CLEAR");
        } catch (IllegalArgumentException malformed) {
            return response(job, "DENIED", "MALFORMED_COMMAND");
        }
    }

    private String response(FinishingJob correlated, String status, String reason) {
        Map<String, String> data = new LinkedHashMap<String, String>();
        data.put("phase", Integer.toString(phase)); data.put("polls", Integer.toString(polls));
        data.put("safe", Boolean.toString(safe)); data.put("active", Boolean.toString(active));
        data.put("cycleCount", Integer.toString(cycles));
        if (!reason.isEmpty()) { data.put("reason", reason); }
        if ("COMPLETE".equals(status)) {
            data.putAll(planned.getSensorEvidence());
            if ("MISSING_EVIDENCE".equals(mode)) { data.remove(FinishingWire.requiredEvidence(machine)[0]); }
        }
        String ids = correlated == null ? "UNKNOWN|UNKNOWN|" + machine : FinishingWire.correlation(correlated);
        return "EVIDENCE|" + ids + "|" + status + FinishingWire.data(data);
    }

    public synchronized int getCycles() { return cycles; }
    public synchronized boolean isSafe() { return safe && !active; }
}
