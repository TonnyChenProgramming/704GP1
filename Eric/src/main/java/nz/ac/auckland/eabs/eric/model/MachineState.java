package nz.ac.auckland.eabs.eric.model;

/** Runtime state of a controller, kept separate from bottle state. */
public enum MachineState {
    OFFLINE,
    READY,
    BUSY,
    DONE,
    FAULT
}
