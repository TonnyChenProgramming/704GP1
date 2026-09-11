package nz.ac.auckland.eabs.eric.controllers;

import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.plants.UnloaderPlant;

/** Confirms collection before a workpiece may be retired from the live line. */
public final class UnloaderController extends AbstractFinishingController {
    public static final String MACHINE_ID = "UnloaderController";

    public UnloaderController(UnloaderPlant plant) {
        super(MACHINE_ID, Operation.UNLOAD, plant);
    }
}
