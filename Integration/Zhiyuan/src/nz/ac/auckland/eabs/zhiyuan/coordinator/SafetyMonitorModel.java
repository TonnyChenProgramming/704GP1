package nz.ac.auckland.eabs.zhiyuan.coordinator;

/**
 * Stand-in for the human-presence / environmental sensor inputs required by brief 4.1.2,
 * until real hardware is wired directly. Mirrors BatchManagerModel's shape: one plain Java
 * object behind a signal, no blocking I/O inside a SystemJ reaction.
 *
 * Two independent ways to drive it, either or both active at once:
 *  - Console commands ('hazard'/'clear'/'reset') via SharedConsole (shared with
 *    BatchManagerModel when both are present, e.g. coordinator_real.xml) -- kept as a manual
 *    override/fallback for every existing profile, unchanged from the original design.
 *  - An autonomous sensor (HazardSensorSimulator), opt-in via -Dsafety.autoSensor=true, which
 *    triggers and clears a hazard entirely on its own timer, with no operator input at all --
 *    this is what a real human-presence sensor does, and what a professor watching a demo
 *    expects "simulate the sensor" to mean, as opposed to a person typing the word "hazard".
 * Both paths funnel through the same triggerHazard()/triggerClear()/triggerReset() methods,
 * so hazardRequested()/restoredRequested()/resetRequested() (and therefore
 * safety_monitor.sysj, which only ever polls those three methods) do not need to know or
 * care which path fired.
 */
public final class SafetyMonitorModel {
    private volatile boolean hazardPending = false;
    private volatile boolean restoredPending = false;
    private volatile boolean resetPending = false;
    private volatile boolean unsafe = false;

    public SafetyMonitorModel() {
        SharedConsole.attachSafety(this);
        printHelp();
        HazardSensorSimulator.startIfEnabled(this);
    }

    /** Called by SharedConsole for every console line before it is offered to any
     * order-entry queue. Returns true if this was a safety keyword (consumed). */
    boolean tryConsole(String line) {
        if (line.isEmpty()) { return false; }
        if (line.equalsIgnoreCase("hazard")) { triggerHazard(); return true; }
        if (line.equalsIgnoreCase("clear")) { triggerClear(); return true; }
        if (line.equalsIgnoreCase("reset")) { triggerReset(); return true; }
        return false;
    }

    /** Simulates the sensor detecting a hazard (human presence / out-of-range reading).
     * Safe to call from any thread -- HazardSensorSimulator calls this from its own timer
     * thread, tryConsole() calls it from the console reader thread. */
    public synchronized void triggerHazard() {
        if (unsafe) {
            System.out.println("[SafetyMonitor] Already unsafe.");
        } else {
            unsafe = true;
            hazardPending = true;
            System.out.println("[SafetyMonitor] Hazard detected (human presence / out-of-range reading). safetyPermitLost will be emitted.");
        }
    }

    /** Simulates the sensor reporting conditions back in range. Does not by itself resume
     * production -- see IntegratedCoordinator.permitRestored()/reset(). */
    public synchronized void triggerClear() {
        if (!unsafe) {
            System.out.println("[SafetyMonitor] Already safe -- nothing to clear.");
        } else {
            unsafe = false;
            restoredPending = true;
            System.out.println("[SafetyMonitor] Condition cleared (sensors back in range). safetyPermitRestored will be emitted. 'reset' still required to resume.");
        }
    }

    /** Operator acknowledgement -- deliberately never triggered by HazardSensorSimulator.
     * Per the brief, resuming production after a hazard is a human decision, not something a
     * sensor decides on its own; only the console (or, later, a GUI button) calls this. */
    public synchronized void triggerReset() {
        if (unsafe) {
            System.out.println("[SafetyMonitor] Cannot reset while still unsafe -- clear the hazard first.");
        } else {
            resetPending = true;
            System.out.println("[SafetyMonitor] Operator reset requested -- coordinator will reconcile and resume if the fault was safety-related.");
        }
    }

    private void printHelp() {
        System.out.println("[SafetyMonitor] Commands: 'hazard' (simulate human presence / out-of-range sensor), "
                + "'clear' (simulate condition back to normal), 'reset' (operator acknowledgement to resume). "
                + (Boolean.getBoolean("safety.autoSensor")
                        ? "Autonomous sensor simulation is ALSO active (-Dsafety.autoSensor=true)."
                        : "Autonomous sensor simulation is off (-Dsafety.autoSensor=true to enable)."));
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

    /** Current sensor reading, for a GUI status indicator -- true from triggerHazard() until
     * the matching triggerClear(). Read-only; does not consume anything (unlike the
     * *Requested() methods above, which are one-shot edge triggers for the .sysj poll). */
    public synchronized boolean isUnsafe() {
        return unsafe;
    }
}
