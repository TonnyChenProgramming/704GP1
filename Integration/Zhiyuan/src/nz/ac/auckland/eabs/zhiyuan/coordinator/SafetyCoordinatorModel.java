package nz.ac.auckland.eabs.zhiyuan.coordinator;

/** Safety-permit state split from IntegratedCoordinator. SystemJ owns all inter-CD I/O. */
public final class SafetyCoordinatorModel {

    private boolean unsafe;
    private boolean operatorAcknowledged;

    /** Safety monitor reports loss of permit. */
    public synchronized void hazard() {

        unsafe = true;
        operatorAcknowledged = false;

    }

    /** Sensor condition cleared. Does not by itself permit production to resume. */
    public synchronized void permitRestored() {

        unsafe = false;

    }

    /** Operator acknowledgement required after the physical hazard has cleared. */
    public synchronized void acknowledge() {

        operatorAcknowledged = true;

    }

    /** True only when no safety hold is active. */
    public synchronized boolean permit() {

        return !unsafe;

    }

    /** Production may reset only after hazard clear plus operator acknowledgement. */
    public synchronized boolean canReset() {

        return !unsafe && operatorAcknowledged;

    }

    /** Complete one recovery cycle after ProductionCoordinatorModel and BatchCoordinatorModel reset. */
    public synchronized void resetComplete() {

        if (!canReset()) return;

        operatorAcknowledged = false;

    }

    public synchronized boolean unsafe() { return unsafe; }

    public synchronized boolean operatorAcknowledged() { return operatorAcknowledged; }

}
