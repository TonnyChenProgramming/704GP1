package nz.ac.auckland.eabs.eric.plants;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.Operation;

import java.util.Map;

/** Simulates lid pick, arm movement, placement and arm retraction sensors. */
public final class LidLoaderPlant extends AbstractSimulatedPlant {
    public LidLoaderPlant() {
        super("LidLoaderPlant", Operation.PLACE_LID, 3);
    }

    @Override
    protected Map<String, String> perform(FinishingJob job) {
        requireTrue(job, "bottlePresent");
        requireTrue(job, "filled");
        requireTrue(job, "lidAvailable");
        return evidence(
                "lidPresent", "true",
                "placementComplete", "true",
                "armHome", "true");
    }
}
