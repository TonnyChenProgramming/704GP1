package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Real, interactive batch source for CoordinatorCD.activateBatchIn/batchDrainedOut,
 * replacing the scripted CoordinatorBatchHarnessCD for actual use. Mirrors
 * IntegratedCoordinator's shape exactly: one plain Java object holding all
 * real state/logic behind a signal, with SystemJ reactions only relaying
 * channel <-> method calls -- never do blocking console I/O inside a SystemJ
 * reaction itself, so a background thread owns the console here.
 */
public final class BatchManagerModel {
    private final BlockingQueue<String> pending = new ArrayBlockingQueue<String>(16);
    private String awaitingResultFor = null; // null = no batch currently in flight
    private volatile boolean halted = false;

    public BatchManagerModel() {
        SharedConsole.ensureStarted();
        Thread reader = new Thread(new Runnable() {
            public void run() { readLoop(); }
        }, "batch-manager-console");
        reader.setDaemon(true);
        reader.start();
    }

    /** Lines arrive from SharedConsole, which also feeds SafetyMonitorModel from the
     * same physical console -- hazard/clear/reset are intercepted there and never reach
     * this queue, so this loop's shape is unchanged from owning its own Scanner. */
    private void readLoop() {
        while (!halted) {
            System.out.println("[BatchManager] Enter next order (server validates; bad values come back REJECTED):");
            String batchId = readField("  batchId [A-Za-z0-9_.-]{1,60}: ");
            String recipeId = readField("  recipeId [A-Za-z0-9_.-]{1,60}: ");
            String productId = readField("  productId [A-Za-z0-9_.-]{1,60}: ");
            String quantity = readField("  quantity [1-10000]: ");
            String doseA = readField("  doseA [int >= 0]: ");
            String doseB = readField("  doseB [int >= 0, doseA+doseB in 1-100]: ");
            String orderId = readField("  orderId [A-Za-z0-9_.-]{1,60}: ");
            if (batchId == null || recipeId == null || productId == null || quantity == null
                    || doseA == null || doseB == null || orderId == null) {
                System.out.println("[BatchManager] Input closed; no more orders will be submitted.");
                return;
            }
            String frame = "ACTIVATE|" + batchId + "|" + recipeId + "|" + productId + "|"
                    + quantity + "|" + doseA + "|" + doseB + "|" + orderId;
            try {
                pending.put(frame);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("[BatchManager] System is in FAULT/HOLD -- no further batches can be submitted this session.");
    }

    /** Returns the typed line, or null on end-of-input (never loops on EOF). */
    private String readField(String label) {
        System.out.print(label);
        System.out.flush();
        try {
            String line = SharedConsole.orderLines().take();
            return SharedConsole.EOF_MARKER.equals(line) ? null : line.trim();
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /** Non-blocking: called once per SystemJ tick. Never sends a second batch
     * while one is still in flight, and never sends anything once FAULT has
     * latched -- mirrors "do not activate another batch until its terminal
     * result is received" and "coordinator never automatically advances
     * following FAULT" from COORDINATOR_INTEGRATION.md. */
    public synchronized String nextActivate() {
        if (halted || awaitingResultFor != null) return "";
        String frame = pending.poll();
        if (frame == null) return "";
        String[] f = frame.split("\\|", -1);
        awaitingResultFor = f.length > 1 ? f[1] : "UNKNOWN";
        return frame;
    }

    public synchronized void resultReceived(String result) {
        awaitingResultFor = null;
        if (result.startsWith("DRAINED|")) {
            System.out.println("[BatchManager] " + result + " -- ready for the next order.");
        } else if (result.startsWith("REJECTED|")) {
            System.out.println("[BatchManager] " + result + " -- correct the fields and try again.");
        } else if (result.startsWith("FAULT|")) {
            halted = true;
            System.out.println("[BatchManager] " + result + " -- coordinator is HOLDING. No further batches will be sent this session.");
        } else if (result.startsWith("RECOVERED|")) {
            // Sent once by IntegratedCoordinator.reset() (the safety-specific recovery path --
            // never for a machine fault, which stays permanently HOLDING). Without this branch
            // halted, once latched true on an earlier FAULT|, could never clear again even
            // after a real safety reset, and every later order would be rejected forever.
            halted = false;
            System.out.println("[BatchManager] " + result + " -- coordinator recovered from safety HOLD, resuming order acceptance.");
        } else {
            System.out.println("[BatchManager] Unexpected result: " + result);
        }
    }

    /** Nothing time-based yet; kept only so BatchManagerCD's shape matches
     * IntegratedCoordinator's proven tick()-driven pattern exactly. */
    public synchronized void tick() {
    }
}
