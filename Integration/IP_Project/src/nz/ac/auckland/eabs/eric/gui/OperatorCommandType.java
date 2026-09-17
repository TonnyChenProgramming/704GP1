package nz.ac.auckland.eabs.eric.gui;

/** Permitted GUI commands; none is a direct plant actuator command. */
public enum OperatorCommandType {
    SUBMIT_ORDER,
    START,
    PAUSE_ADMISSION,
    SAFE_STOP,
    ACKNOWLEDGE_HAZARD,
    SAFE_RESET,
    SET_SIMULATION_SPEED,
    INJECT_SIMULATED_FAULT
}
