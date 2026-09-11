package nz.ac.auckland.eabs.eric.controllers;

import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.plants.CapperPlant;

/** Local capper controller; owns clamp/gripper interlocks only. */
public final class CapperController extends AbstractFinishingController {
    public static final String MACHINE_ID = "CapperController";

    public CapperController(CapperPlant plant) {
        super(MACHINE_ID, Operation.CAP_BOTTLE, plant);
    }
}
