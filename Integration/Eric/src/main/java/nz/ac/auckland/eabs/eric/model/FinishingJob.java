package nz.ac.auckland.eabs.eric.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** Correlated operation request sent by the Production Coordinator. */
public final class FinishingJob {
    private final String jobId;
    private final String workpieceId;
    private final String machineId;
    private final Operation operation;
    private final Map<String, String> operationData;

    public FinishingJob(
            String jobId,
            String workpieceId,
            String machineId,
            Operation operation,
            Map<String, String> operationData) {
        this.jobId = requireText(jobId, "jobId");
        this.workpieceId = requireText(workpieceId, "workpieceId");
        this.machineId = requireText(machineId, "machineId");
        this.operation = Objects.requireNonNull(operation, "operation");
        Map<String, String> copy = operationData == null
                ? new LinkedHashMap<String, String>()
                : new LinkedHashMap<String, String>(operationData);
        this.operationData = Collections.unmodifiableMap(copy);
    }

    public String requireData(String key) {
        String value = operationData.get(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Operation " + operation + " requires data field " + key);
        }
        return value;
    }

    public boolean booleanData(String key) {
        return Boolean.parseBoolean(requireData(key));
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }

    public String getJobId() { return jobId; }
    public String getWorkpieceId() { return workpieceId; }
    public String getMachineId() { return machineId; }
    public Operation getOperation() { return operation; }
    public Map<String, String> getOperationData() { return operationData; }
}
