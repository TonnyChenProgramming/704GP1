package nz.ac.auckland.eabs.eric.plants;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.Operation;

import java.util.Map;

/** Simulates removal from the output path and collection confirmation. */
public final class UnloaderPlant extends AbstractSimulatedPlant {
    public UnloaderPlant() {
        super("UnloaderPlant", Operation.UNLOAD, 2);
    }

    @Override
    protected Map<String, String> perform(FinishingJob job) {
        requireTrue(job, "bottlePresent");
        requireTrue(job, "collectionAvailable");
        return evidence(
                "bottleCollected", "true",
                "outputPathClear", "true");
    }
}
