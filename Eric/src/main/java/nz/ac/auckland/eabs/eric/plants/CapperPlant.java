package nz.ac.auckland.eabs.eric.plants;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.Operation;

import java.util.Map;

/** Simulates clamping, cap fixing, gripper return and completion sensors. */
public final class CapperPlant extends AbstractSimulatedPlant {
    public CapperPlant() {
        super("CapperPlant", Operation.CAP_BOTTLE, 4);
    }

    @Override
    protected Map<String, String> perform(FinishingJob job) {
        requireTrue(job, "bottlePresent");
        requireTrue(job, "lidPresent");
        return evidence(
                "capSecured", "true",
                "clampHome", "true",
                "gripperHome", "true",
                "cycleComplete", "true");
    }
}
