package nz.ac.auckland.eabs.eric.controllers;

import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.plants.LabelerPlant;

/** Labels only bottles whose completed manufacturing result is provided. */
public final class LabelerController extends AbstractFinishingController {
    public static final String MACHINE_ID = "LabelerController";

    public LabelerController(LabelerPlant plant) {
        super(MACHINE_ID, Operation.APPLY_LABEL, plant);
    }
}
