package nz.ac.auckland.eabs.eric.tracking;

import nz.ac.auckland.eabs.eric.model.Location;
import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.model.WorkpieceStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/** Immutable view used by persistence, POS progress and visualization. */
public final class WorkpieceSnapshot {
    private final String workpieceId;
    private final String orderId;
    private final String batchId;
    private final String productId;
    private final String requiredRecipe;
    private final Location location;
    private final int rotaryPosition;
    private final Operation currentOperation;
    private final Operation nextOperation;
    private final WorkpieceStatus status;
    private final List<Operation> requiredOperations;
    private final List<Operation> completedOperations;
    private final List<String> actualStationsUsed;
    private final Map<Operation, String> operationTimestamps;
    private final List<String> faultHistory;
    private final List<String> eventHistory;
    private final String finalOutcome;
    private final String labelPayload;

    public WorkpieceSnapshot(
            String workpieceId,
            String orderId,
            String batchId,
            String productId,
            String requiredRecipe,
            Location location,
            int rotaryPosition,
            Operation currentOperation,
            Operation nextOperation,
            WorkpieceStatus status,
            List<Operation> requiredOperations,
            List<Operation> completedOperations,
            List<String> actualStationsUsed,
            Map<Operation, String> operationTimestamps,
            List<String> faultHistory,
            List<String> eventHistory,
            String finalOutcome,
            String labelPayload) {
        this.workpieceId = workpieceId;
        this.orderId = orderId;
        this.batchId = batchId;
        this.productId = productId;
        this.requiredRecipe = requiredRecipe;
        this.location = location;
        this.rotaryPosition = rotaryPosition;
        this.currentOperation = currentOperation;
        this.nextOperation = nextOperation;
        this.status = status;
        this.requiredOperations = immutableCopy(requiredOperations);
        this.completedOperations = immutableCopy(completedOperations);
        this.actualStationsUsed = immutableCopy(actualStationsUsed);
        Map<Operation, String> timestampCopy =
                new EnumMap<Operation, String>(Operation.class);
        timestampCopy.putAll(operationTimestamps);
        this.operationTimestamps = Collections.unmodifiableMap(timestampCopy);
        this.faultHistory = immutableCopy(faultHistory);
        this.eventHistory = immutableCopy(eventHistory);
        this.finalOutcome = finalOutcome == null ? "" : finalOutcome;
        this.labelPayload = labelPayload == null ? "" : labelPayload;
    }

    private static <T> List<T> immutableCopy(List<T> values) {
        return Collections.unmodifiableList(new ArrayList<T>(values));
    }

    public String getWorkpieceId() { return workpieceId; }
    public String getOrderId() { return orderId; }
    public String getBatchId() { return batchId; }
    public String getProductId() { return productId; }
    public String getRequiredRecipe() { return requiredRecipe; }
    public Location getLocation() { return location; }
    public int getRotaryPosition() { return rotaryPosition; }
    public Operation getCurrentOperation() { return currentOperation; }
    public Operation getNextOperation() { return nextOperation; }
    public WorkpieceStatus getStatus() { return status; }
    public List<Operation> getRequiredOperations() { return requiredOperations; }
    public List<Operation> getCompletedOperations() { return completedOperations; }
    public List<String> getActualStationsUsed() { return actualStationsUsed; }
    public Map<Operation, String> getOperationTimestamps() {
        return operationTimestamps;
    }
    public List<String> getFaultHistory() { return faultHistory; }
    public List<String> getEventHistory() { return eventHistory; }
    public String getFinalOutcome() { return finalOutcome; }
    public String getLabelPayload() { return labelPayload; }

    public boolean isTerminal() {
        return status == WorkpieceStatus.COMPLETED
                || status == WorkpieceStatus.REJECTED
                || status == WorkpieceStatus.ABORTED;
    }
}
