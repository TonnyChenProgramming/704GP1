package nz.ac.auckland.eabs.eric.plants;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.Operation;

import java.util.Map;

/** Simulates stop, print, apply, confirm and release behaviour. */
public final class LabelerPlant extends AbstractSimulatedPlant {
    public LabelerPlant() {
        super("LabelerPlant", Operation.APPLY_LABEL, 3);
    }

    @Override
    protected Map<String, String> perform(FinishingJob job) {
        requireTrue(job, "bottlePresent");
        requireTrue(job, "manufacturingComplete");
        String labelPayload = job.requireData("labelPayload");
        return evidence(
                "bottleStopped", "true",
                "labelApplied", "true",
                "printedPayload", labelPayload,
                "bottleReleased", "true");
    }
}
