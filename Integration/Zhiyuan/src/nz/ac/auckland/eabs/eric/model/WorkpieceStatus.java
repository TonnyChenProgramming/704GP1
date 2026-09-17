package nz.ac.auckland.eabs.eric.model;

/** Lifecycle of one bottle instance. */
public enum WorkpieceStatus {
    CREATED,
    WAITING,
    IN_PROCESS,
    FAULTED,
    COMPLETED,
    REJECTED,
    ABORTED
}
