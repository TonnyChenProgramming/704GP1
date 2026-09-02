package nz.ac.auckland.eabs.eric.controllers;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.MachineReport;
import nz.ac.auckland.eabs.eric.model.MachineState;
import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.model.PlantResult;
import nz.ac.auckland.eabs.eric.plants.FinishingPlant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Reusable local-controller lifecycle for Eric's four finishing machines.
 *
 * DONE and FAULT are latched until the coordinator explicitly acknowledges or
 * resets them, so a one-tick completion cannot be lost.
 */
public abstract class AbstractFinishingController {
    private final String machineId;
    private final Operation supportedOperation;
    private final FinishingPlant plant;

    private MachineState state = MachineState.READY;
    private FinishingJob currentJob;

    protected AbstractFinishingController(
            String machineId,
            Operation supportedOperation,
            FinishingPlant plant) {
        this.machineId = requireText(machineId, "machineId");
        this.supportedOperation =
                Objects.requireNonNull(supportedOperation, "supportedOperation");
        this.plant = Objects.requireNonNull(plant, "plant");
        if (plant.getSupportedOperation() != supportedOperation) {
            throw new IllegalArgumentException(
                    "Controller and plant operation do not match");
        }
    }

    /**
     * Executes one deterministic simulated cycle.
     *
     * The returned list always preserves BUSY before terminal DONE/FAULT for an
     * accepted job. The controller stays terminal until acknowledgement/reset.
     */
    public synchronized List<MachineReport> execute(
            FinishingJob job,
            boolean safetyPermit) {
        Objects.requireNonNull(job, "job");
        if (state != MachineState.READY) {
            throw new IllegalStateException(
                    machineId + " cannot accept work while " + state);
        }
        currentJob = job;
        String invalidReason = validateJob(job, safetyPermit);
        if (invalidReason != null) {
            state = MachineState.FAULT;
            return Collections.singletonList(
                    MachineReport.fault(job, invalidReason));
        }

        List<MachineReport> reports = new ArrayList<MachineReport>();
        state = MachineState.BUSY;
        reports.add(MachineReport.busy(job));

        PlantResult result = plant.execute(job);
        if (result.isSuccessful()) {
            String timeoutReason = timeoutReason(job, result);
            if (timeoutReason == null) {
                state = MachineState.DONE;
                reports.add(MachineReport.done(
                        job,
                        result.getDetail(),
                        result.getSensorEvidence()));
            } else {
                state = MachineState.FAULT;
                reports.add(MachineReport.fault(job, timeoutReason));
            }
        } else {
            state = MachineState.FAULT;
            reports.add(MachineReport.fault(job, result.getDetail()));
        }
        return Collections.unmodifiableList(reports);
    }

    public synchronized void acknowledgeDone(
            String jobId,
            String workpieceId) {
        if (state != MachineState.DONE || currentJob == null) {
            throw new IllegalStateException(
                    machineId + " has no DONE result to acknowledge");
        }
        requireCorrelation(jobId, workpieceId);
        currentJob = null;
        state = MachineState.READY;
    }

    public synchronized void resetFault(boolean safetyPermit) {
        if (state != MachineState.FAULT) {
            throw new IllegalStateException(machineId + " is not faulted");
        }
        if (!safetyPermit) {
            throw new IllegalStateException(
                    "Reset requires the global safety permit");
        }
        if (!plant.isFaultCleared()) {
            throw new IllegalStateException(
                    "Reset requires the plant fault cause to be cleared");
        }
        currentJob = null;
        state = MachineState.READY;
    }

    public synchronized void safeStop() {
        if (state == MachineState.BUSY) {
            throw new IllegalStateException(
                    "A BUSY controller must first reach its defined safe point");
        }
        currentJob = null;
        state = MachineState.OFFLINE;
    }

    public synchronized void setOnline() {
        if (state != MachineState.OFFLINE) {
            throw new IllegalStateException(
                    machineId + " is not offline");
        }
        if (!plant.isFaultCleared()) {
            throw new IllegalStateException(
                    "Cannot enable controller with a plant fault");
        }
        state = MachineState.READY;
    }

    public synchronized MachineReport readinessReport() {
        if (state != MachineState.READY) {
            throw new IllegalStateException(
                    machineId + " is not ready");
        }
        return MachineReport.ready(machineId);
    }

    private String validateJob(
            FinishingJob job,
            boolean safetyPermit) {
        if (!machineId.equals(job.getMachineId())) {
            return "Job addressed to " + job.getMachineId()
                    + " instead of " + machineId;
        }
        if (job.getOperation() != supportedOperation) {
            return "Unsupported operation " + job.getOperation();
        }
        if (!safetyPermit) {
            return "Global safety permit is absent";
        }
        String timeoutTicks = job.getOperationData().get("timeoutTicks");
        if (timeoutTicks != null) {
            try {
                if (Integer.parseInt(timeoutTicks) <= 0) {
                    return "timeoutTicks must be positive";
                }
            } catch (NumberFormatException invalidNumber) {
                return "timeoutTicks must be an integer";
            }
        }
        return null;
    }

    private String timeoutReason(
            FinishingJob job,
            PlantResult result) {
        String configured = job.getOperationData().get("timeoutTicks");
        if (configured == null) {
            return null;
        }
        String observed = result.getSensorEvidence().get("simulatedCycleTicks");
        if (observed == null) {
            return "Plant result omitted simulatedCycleTicks evidence";
        }
        try {
            int observedTicks = Integer.parseInt(observed);
            int allowedTicks = Integer.parseInt(configured);
            if (observedTicks > allowedTicks) {
                return "Plant timeout: cycle took " + observedTicks
                        + " ticks; limit was " + allowedTicks;
            }
            return null;
        } catch (NumberFormatException invalidEvidence) {
            return "Plant returned invalid simulatedCycleTicks evidence";
        }
    }

    private void requireCorrelation(
            String jobId,
            String workpieceId) {
        if (!currentJob.getJobId().equals(jobId)
                || !currentJob.getWorkpieceId().equals(workpieceId)) {
            throw new IllegalArgumentException(
                    "Acknowledgement does not match the latched result");
        }
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }

    public String getMachineId() { return machineId; }
    public Operation getSupportedOperation() { return supportedOperation; }
    public FinishingPlant getPlant() { return plant; }
    public synchronized MachineState getState() { return state; }
    public synchronized FinishingJob getCurrentJob() { return currentJob; }
}
