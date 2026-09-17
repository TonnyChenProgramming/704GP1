package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.*;

/** Batch-lifecycle state split from IntegratedCoordinator. SystemJ owns all inter-CD I/O. */
public final class BatchCoordinatorModel {

    private final Set<String> usedBatches = new HashSet<String>();

    private String batch = "", product = "", recipe = "", order = "";
    private String fault = "", response = "";
    private int quantity, admitted, completed, doseA, doseB;
    private String phase = "WAIT_ORDER";

    /** Explicit recipe amounts and order identity, no fabricated defaults. */
    public synchronized void activate(String frame) {

        if (!phase.equals("WAIT_ORDER") || !fault.isEmpty()) { fail("ACTIVATE_WHILE_NOT_IDLE"); return; }

        String[] f = frame.split("\\|", -1);

        try {

            if (f.length != 8 || !f[0].equals("ACTIVATE")) throw new IllegalArgumentException();

            for (int i : new int[]{1, 2, 3, 7}) if (!f[i].matches("[A-Za-z0-9_.-]{1,60}")) throw new IllegalArgumentException();

            int q = Integer.parseInt(f[4]), a = Integer.parseInt(f[5]), b = Integer.parseInt(f[6]);

            if (q < 1 || q > 10000 || a < 0 || b < 0 || a > 100 || b > 100 || a+b < 1 || a+b > 100 || usedBatches.contains(f[1])) throw new IllegalArgumentException();

            batch = f[1]; recipe = f[2]; product = f[3]; order = f[7];

            quantity = q; doseA = a; doseB = b; admitted = 0; completed = 0;

            usedBatches.add(batch); phase = "ACTIVE";

            System.out.println("COORDINATOR ACTIVATE " + batch + " quantity=" + quantity + " doses=" + a + "/" + b);

        } catch (IllegalArgumentException invalid) {

            response = "REJECTED|" + (f.length > 1 && f[1].matches("[A-Za-z0-9_.-]{1,60}") ? f[1] : "UNKNOWN") + "|INVALID_BATCH_OR_RECIPE";

        }

    }

    /** Validated batch frame for the ProductionCoordinatorCD. Empty unless a batch is active. */
    public synchronized String productionFrame() {

        if (!phase.equals("ACTIVE") || !fault.isEmpty()) return "";

        return "ACTIVATE|" + batch + "|" + recipe + "|" + product + "|" + quantity + "|" + doseA + "|" + doseB + "|" + order;

    }

    /** Event from ProductionCoordinatorCD after a loader DONE has been validated. */
    public synchronized void admitted(String workpieceId) {

        if (!phase.equals("ACTIVE") || !fault.isEmpty()) return;

        admitted++;

    }

    /** Event from ProductionCoordinatorCD after an unloader DONE has been validated. */
    public synchronized void completed(String workpieceId) {

        if (!phase.equals("ACTIVE") || !fault.isEmpty()) return;

        completed++;

    }

    /** Event from ProductionCoordinatorCD once its physical line is empty. */
    public synchronized void drained() {

        if (!phase.equals("ACTIVE") || !fault.isEmpty()) return;

        if (completed != quantity) { fail("DRAINED_BEFORE_BATCH_COMPLETE"); return; }

        response = "DRAINED|" + batch; phase = "WAIT_ORDER";

        System.out.println("COORDINATOR DRAINED " + batch);

    }

    /** Fault propagated from ProductionCoordinatorCD or SafetyCoordinatorCD. */
    public synchronized void hold(String reason) { fail(reason); }

    /** Handles the pipe-delimited event protocol from ProductionCoordinatorCD. */
    public synchronized void productionEvent(String event) {

        String[] parts = event.split("\\|", 3);

        if (parts.length >= 2 && parts[0].equals("ADMITTED")) {

            admitted(parts[1]);

        } else if (parts.length >= 2 && parts[0].equals("COMPLETED")) {

            completed(parts[1]);

        } else if (parts.length >= 2 && parts[0].equals("DRAINED")) {

            drained();

        } else if (parts.length >= 3 && parts[0].equals("FAULT")) {

            hold(parts[2]);

        }

    }

    /** Safety recovery starts a fresh order after ProductionCoordinatorCD reconciles the physical line. */
    public synchronized void reset() {

        if (fault.isEmpty()) return;

        fault = ""; response = ""; phase = "WAIT_ORDER";

        batch = ""; product = ""; recipe = ""; order = "";
        quantity = 0; admitted = 0; completed = 0; doseA = 0; doseB = 0;

        System.out.println("COORDINATOR BATCH RESET -- ready for next order.");

    }

    public synchronized boolean awaitingOrder() { return phase.equals("WAIT_ORDER") && fault.isEmpty() && response.isEmpty(); }

    public synchronized boolean active() { return phase.equals("ACTIVE") && fault.isEmpty(); }

    public synchronized String response() { return response; }

    public synchronized void responseSent() { response = ""; }

    public synchronized String phase() { return phase; }

    public synchronized int admitted() { return admitted; }

    public synchronized int completed() { return completed; }

    public synchronized int quantity() { return quantity; }

    public synchronized String batch() { return batch; }

    public synchronized String fault() { return fault; }

    private void fail(String reason) {

        if (!fault.isEmpty()) return;

        fault = reason; phase = "HOLD"; response = "FAULT|" + (batch.isEmpty() ? "NONE" : batch) + "|" + reason;

        System.err.println("COORDINATOR HOLD " + reason);

    }

}
