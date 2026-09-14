package com.g7.ip;

/**
 * A single confirmed field change reported by the GP's Workpiece Tracker.
 *
 * This mirrors exactly the five fields the GP report (Section 5, Table 3)
 * commits to: workpieceId, batchId, location, currentOperation, status.
 * The Assembler never sees anything richer than this — no order/product/
 * recipe, no history. In the real system these arrive as SystemJ signal
 * values; here they are plain Java objects fed from a stub stream, which
 * is exactly the "unit-tested against a stub stream of Tracker field
 * updates" arrangement described in Section 10 of the IP report.
 */
public class TrackerFieldChange {
    public final String workpieceId;
    public final int batchId;
    public final String location;        // e.g. "loader", "filler", "capper", "unloader"
    public final String currentOperation; // e.g. "FILLING", "FILLED"
    public final String status;           // "BUSY" | "DONE" | "FAULT" | "ABORTED"

    public TrackerFieldChange(String workpieceId, int batchId, String location,
                               String currentOperation, String status) {
        this.workpieceId = workpieceId;
        this.batchId = batchId;
        this.location = location;
        this.currentOperation = currentOperation;
        this.status = status;
    }

    /** Only DONE / FAULT / ABORTED are "confirmed transitions" worth persisting (Section 4). */
    public boolean isConfirmedTransition() {
        return "DONE".equals(status) || "FAULT".equals(status) || "ABORTED".equals(status);
    }
}
