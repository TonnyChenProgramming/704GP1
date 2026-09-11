package nz.ac.auckland.eabs.eric.tracking;

import nz.ac.auckland.eabs.eric.model.Location;
import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.model.WorkpieceStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
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
    private final Map<String, String> actualDosedAmounts;

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
        this(workpieceId, orderId, batchId, productId, requiredRecipe, location,
                rotaryPosition, currentOperation, nextOperation, status,
                requiredOperations, completedOperations, actualStationsUsed,
                operationTimestamps, faultHistory, eventHistory, finalOutcome,
                labelPayload, null);
    }

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
            String labelPayload,
            Map<String, String> actualDosedAmounts) {
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
        this.actualDosedAmounts = actualDosedAmounts == null ? null
                : Collections.unmodifiableMap(
                        new LinkedHashMap<String, String>(actualDosedAmounts));
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

    /**
     * Confirmed filler evidence (liquidA and liquidB), or null if unavailable.
     * Values retain the incoming strings and units; no conversion or deviation
     * judgement is performed. The returned map is immutable.
     */
    public Map<String, String> getActualDosedAmounts() {
        return actualDosedAmounts;
    }

    public boolean isTerminal() {
        return status == WorkpieceStatus.COMPLETED
                || status == WorkpieceStatus.REJECTED
                || status == WorkpieceStatus.ABORTED;
    }
}
