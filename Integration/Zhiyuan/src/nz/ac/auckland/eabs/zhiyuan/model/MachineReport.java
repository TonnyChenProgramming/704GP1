package nz.ac.auckland.eabs.eric.model;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** Correlated status/result emitted by a local finishing controller. */
public final class MachineReport {
    private final String machineId;
    private final MachineState state;
    private final String jobId;
    private final String workpieceId;
    private final String detail;
    private final Map<String, String> evidence;
    private final String reportedAt;

    private MachineReport(
            String machineId,
            MachineState state,
            String jobId,
            String workpieceId,
            String detail,
            Map<String, String> evidence) {
        this.machineId = requireText(machineId, "machineId");
        this.state = Objects.requireNonNull(state, "state");
        this.jobId = jobId == null ? "" : jobId;
        this.workpieceId = workpieceId == null ? "" : workpieceId;
        this.detail = detail == null ? "" : detail;
        this.evidence = Collections.unmodifiableMap(
                evidence == null
                        ? new LinkedHashMap<String, String>()
                        : new LinkedHashMap<String, String>(evidence));
        this.reportedAt = Instant.now().toString();
        if (requiresCorrelation(state)
                && (this.jobId.isEmpty() || this.workpieceId.isEmpty())) {
            throw new IllegalArgumentException(
                    state + " reports require jobId and workpieceId");
        }
    }

    public static MachineReport ready(String machineId) {
        return new MachineReport(
                machineId, MachineState.READY, "", "", "", null);
    }

    public static MachineReport busy(FinishingJob job) {
        return fromJob(job, MachineState.BUSY, "job accepted", null);
    }

    public static MachineReport done(
            FinishingJob job,
            String detail,
            Map<String, String> evidence) {
        return fromJob(job, MachineState.DONE, detail, evidence);
    }

    public static MachineReport fault(FinishingJob job, String reason) {
        return fromJob(job, MachineState.FAULT, reason, null);
    }

    private static MachineReport fromJob(
            FinishingJob job,
            MachineState state,
            String detail,
            Map<String, String> evidence) {
        Objects.requireNonNull(job, "job");
        return new MachineReport(
                job.getMachineId(),
                state,
                job.getJobId(),
                job.getWorkpieceId(),
                detail,
                evidence);
    }

    private static boolean requiresCorrelation(MachineState state) {
        return state == MachineState.BUSY
                || state == MachineState.DONE
                || state == MachineState.FAULT;
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }

    public String getMachineId() { return machineId; }
    public MachineState getState() { return state; }
    public String getJobId() { return jobId; }
    public String getWorkpieceId() { return workpieceId; }
    public String getDetail() { return detail; }
    public Map<String, String> getEvidence() { return evidence; }
    public String getReportedAt() { return reportedAt; }
}
