package nz.ac.auckland.eabs.eric.plants;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.model.PlantResult;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Common deterministic timing/fault behaviour for the separate plant CDs.
 *
 * The Java model is testable without SystemJ. A SystemJ plant CD can delegate
 * its reaction body to the same operation-specific checks.
 */
public abstract class AbstractSimulatedPlant implements FinishingPlant {
    private final String plantId;
    private final Operation supportedOperation;
    private final int nominalCycleTicks;
    private String latchedFault = "";
    private String failNextReason = "";

    protected AbstractSimulatedPlant(
            String plantId,
            Operation supportedOperation,
            int nominalCycleTicks) {
        this.plantId = requireText(plantId, "plantId");
        this.supportedOperation =
                Objects.requireNonNull(supportedOperation, "supportedOperation");
        if (nominalCycleTicks <= 0) {
            throw new IllegalArgumentException(
                    "nominalCycleTicks must be positive");
        }
        this.nominalCycleTicks = nominalCycleTicks;
    }

    @Override
    public final synchronized PlantResult execute(FinishingJob job) {
        Objects.requireNonNull(job, "job");
        if (job.getOperation() != supportedOperation) {
            return latchFault("Unsupported operation " + job.getOperation());
        }
        if (!latchedFault.isEmpty()) {
            return PlantResult.fault(latchedFault);
        }
        if (!failNextReason.isEmpty()) {
            String reason = failNextReason;
            failNextReason = "";
            return latchFault(reason);
        }
        try {
            Map<String, String> evidence = perform(job);
            Map<String, String> timedEvidence =
                    new LinkedHashMap<String, String>(evidence);
            timedEvidence.put(
                    "simulatedCycleTicks",
                    Integer.toString(nominalCycleTicks));
            return PlantResult.success(
                    supportedOperation + " sensor sequence confirmed",
                    timedEvidence);
        } catch (IllegalArgumentException invalidPrecondition) {
            return latchFault(invalidPrecondition.getMessage());
        }
    }

    protected abstract Map<String, String> perform(FinishingJob job);

    protected static void requireTrue(FinishingJob job, String field) {
        if (!job.booleanData(field)) {
            throw new IllegalArgumentException(
                    "Plant precondition is false: " + field);
        }
    }

    protected static Map<String, String> evidence(String... pairs) {
        if (pairs.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "Evidence requires key/value pairs");
        }
        Map<String, String> values = new LinkedHashMap<String, String>();
        for (int index = 0; index < pairs.length; index += 2) {
            values.put(pairs[index], pairs[index + 1]);
        }
        return values;
    }

    private PlantResult latchFault(String reason) {
        latchedFault = requireText(reason, "reason");
        return PlantResult.fault(latchedFault);
    }

    @Override
    public synchronized void failNextCycle(String reason) {
        failNextReason = requireText(reason, "reason");
    }

    @Override
    public synchronized void clearFault() {
        latchedFault = "";
        failNextReason = "";
    }

    @Override
    public synchronized boolean isFaultCleared() {
        return latchedFault.isEmpty() && failNextReason.isEmpty();
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }

    @Override
    public String getPlantId() { return plantId; }

    @Override
    public Operation getSupportedOperation() { return supportedOperation; }
}
