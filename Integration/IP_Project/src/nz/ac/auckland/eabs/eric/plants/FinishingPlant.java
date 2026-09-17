package nz.ac.auckland.eabs.eric.plants;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.model.PlantResult;

/** Boundary between a controller and its separate simulated plant model. */
public interface FinishingPlant {
    String getPlantId();

    Operation getSupportedOperation();

    PlantResult execute(FinishingJob job);

    void failNextCycle(String reason);

    void clearFault();

    boolean isFaultCleared();
}
