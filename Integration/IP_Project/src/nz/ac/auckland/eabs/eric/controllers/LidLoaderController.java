package nz.ac.auckland.eabs.eric.controllers;

import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.plants.LidLoaderPlant;

/** Local lid-loader controller; global sequencing remains in the coordinator. */
public final class LidLoaderController extends AbstractFinishingController {
    public static final String MACHINE_ID = "LidLoaderController";

    public LidLoaderController(LidLoaderPlant plant) {
        super(MACHINE_ID, Operation.PLACE_LID, plant);
    }
}
