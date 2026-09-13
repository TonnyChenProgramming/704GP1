package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.Scanner;

/**
 * Interactive stand-in for the human-presence / environmental sensor inputs required by
 * brief 4.1.2, until the operator GUI wires ACKNOWLEDGE_HAZARD/SAFE_RESET directly. Mirrors
 * BatchManagerModel's shape: one plain Java object behind a signal, background console
 * thread only, no blocking I/O inside a SystemJ reaction.
 */
public final class SafetyMonitorModel {
    private volatile boolean hazardPending = false;
    private volatile boolean restoredPending = false;
    private volatile boolean resetPending = false;
    private volatile boolean unsafe = false;

    public SafetyMonitorModel() {
        Thread reader = new Thread(new Runnable() {
            public void run() { readLoop(); }
        }, "safety-monitor-console");
        reader.setDaemon(true);
        reader.start();
    }

    private void readLoop() {
        Scanner in = new Scanner(System.in);
        printHelp();
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (line.isEmpty()) { continue; }
            if (line.equalsIgnoreCase("hazard")) {
                if (unsafe) {
                    System.out.println("[SafetyMonitor] Already unsafe.");
                } else {
                    unsafe = true;
                    hazardPending = true;
                    System.out.println("[SafetyMonitor] Hazard injected (human presence / out-of-range reading). safetyPermitLost will be emitted.");
                }
            } else if (line.equalsIgnoreCase("clear")) {
                if (!unsafe) {
                    System.out.println("[SafetyMonitor] Already safe -- nothing to clear.");
                } else {
                    unsafe = false;
                    restoredPending = true;
                    System.out.println("[SafetyMonitor] Condition cleared (sensors back in range). safetyPermitRestored will be emitted. Type 'reset' once ready to resume.");
                }
            } else if (line.equalsIgnoreCase("reset")) {
                if (unsafe) {
                    System.out.println("[SafetyMonitor] Cannot reset while still unsafe -- 'clear' the hazard first.");
                } else {
                    resetPending = true;
                    System.out.println("[SafetyMonitor] Operator reset requested -- coordinator will reconcile and resume if the fault was safety-related.");
                }
            } else {
                printHelp();
            }
        }
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
