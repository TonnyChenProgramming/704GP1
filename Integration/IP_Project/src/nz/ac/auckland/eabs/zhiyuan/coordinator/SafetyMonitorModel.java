package nz.ac.auckland.eabs.zhiyuan.coordinator;

/**
 * Interactive stand-in for the human-presence / environmental sensor inputs required by
 * brief 4.1.2, until the operator GUI wires ACKNOWLEDGE_HAZARD/SAFE_RESET directly. Mirrors
 * BatchManagerModel's shape: one plain Java object behind a signal, no blocking I/O inside
 * a SystemJ reaction. Console lines arrive via SharedConsole (shared with BatchManagerModel
 * when both are present, e.g. coordinator_real.xml) rather than its own Scanner.
 */
public final class SafetyMonitorModel {
    private volatile boolean hazardPending = false;
    private volatile boolean restoredPending = false;
    private volatile boolean resetPending = false;
    private volatile boolean unsafe = false;

    public SafetyMonitorModel() {
        SharedConsole.attachSafety(this);
        printHelp();
    }

    /** Called by SharedConsole for every console line before it is offered to any
     * order-entry queue. Returns true if this was a safety keyword (consumed). */
    boolean tryConsole(String line) {
        if (line.isEmpty()) { return false; }
        if (line.equalsIgnoreCase("hazard")) {
            if (unsafe) {
                System.out.println("[SafetyMonitor] Already unsafe.");
            } else {
                unsafe = true;
                hazardPending = true;
                System.out.println("[SafetyMonitor] Hazard injected (human presence / out-of-range reading). safetyPermitLost will be emitted.");
            }
            return true;
        }
        if (line.equalsIgnoreCase("clear")) {
            if (!unsafe) {
                System.out.println("[SafetyMonitor] Already safe -- nothing to clear.");
            } else {
                unsafe = false;
                restoredPending = true;
                System.out.println("[SafetyMonitor] Condition cleared (sensors back in range). safetyPermitRestored will be emitted. Type 'reset' once ready to resume.");
            }
            return true;
        }
        if (line.equalsIgnoreCase("reset")) {
            if (unsafe) {
                System.out.println("[SafetyMonitor] Cannot reset while still unsafe -- 'clear' the hazard first.");
            } else {
                resetPending = true;
                System.out.println("[SafetyMonitor] Operator reset requested -- coordinator will reconcile and resume if the fault was safety-related.");
            }
            return true;
        }
        return false;
    }

    private void printHelp() {
        System.out.println("[SafetyMonitor] Commands: 'hazard' (simulate human presence / out-of-range sensor), "
                + "'clear' (simulate condition back to normal), 'reset' (operator acknowledgement to resume).");
    }

    public synchronized boolean hazardRequested() {
        if (!hazardPending) { return false; }
        hazardPending = false;
        return true;
    }

    public synchronized boolean restoredRequested() {
        if (!restoredPending) { return false; }
        restoredPending = false;
        return true;
    }

    public synchronized boolean resetRequested() {
        if (!resetPending) { return false; }
        resetPending = false;
        return true;
    }
}
