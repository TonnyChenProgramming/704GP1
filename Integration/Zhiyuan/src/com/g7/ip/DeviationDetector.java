package com.g7.ip;

import java.sql.SQLException;
import java.util.List;

/**
 * Recipe-deviation detection (IP report, Section 7 / objective 4).
 *
 * "Detect and flag recipe deviation: compare each bottle's assembled
 * history against its assigned recipe and expected station sequence,
 * and mark any bottle whose actual liquid proportions, stations, or
 * fault history depart from what was specified" — this is the
 * detection-stage implementation only (Section 4.1.3), no automatic
 * rerouting.
 */
public class DeviationDetector {

    private final Dao dao;

    public DeviationDetector(Dao dao) {
        this.dao = dao;
    }

    public static class Result {
        public final boolean deviated;
        public final String reason;
        Result(boolean deviated, String reason) { this.deviated = deviated; this.reason = reason; }
    }

    /**
     * Compares the bottle's recorded station:status sequence against the
     * expected sequence for the core ABS flow. Any missing, extra, or
     * out-of-order station, or any FAULT entry, counts as a deviation.
     *
     * A bottle that was ABORTED before completion is deliberately NOT a
     * deviation: "deviated" is a process-quality verdict (this bottle was
     * built, but not the way the recipe specifies), whereas ABORTED is a
     * lifecycle outcome (the run was interrupted from outside -- a safety
     * halt or an abrupt stop -- before the bottle could finish). Every
     * station such a bottle did reach may have executed perfectly.
     *
     * Without this branch an aborted bottle is always reported as deviated,
     * because its history is necessarily short AND its abort row carries a
     * physical-position label (input, table0..table5, label, output -- see
     * IntegratedCoordinator.reset) drawn from a different vocabulary than
     * the route stations, so the positional comparison below can never
     * match. The FAULT scan deliberately runs FIRST: a bottle that genuinely
     * faulted and was only later swept up by a safety reset should still be
     * reported by its fault, which is the more informative signal.
     */
    public Result checkStationSequence(String bottleId, List<String> expectedStations) throws SQLException {
        List<String> actual = dao.queryStationSequence(bottleId);

        for (String entry : actual) {
            if (entry.endsWith(":FAULT")) {
                return new Result(true, "fault recorded at " + entry.split(":")[0]);
            }
        }

        for (String entry : actual) {
            if (entry.endsWith(":ABORTED")) {
                return new Result(false, "aborted at " + entry.split(":")[0]
                        + " before completion -- no recipe verdict applies");
            }
        }

        int i = 0;
        for (String expectedStation : expectedStations) {
            String expectedEntry = expectedStation + ":DONE";
            if (i >= actual.size() || !actual.get(i).equals(expectedEntry)) {
                return new Result(true, "expected " + expectedEntry + " at position " + i
                        + " but recorded history was " + actual);
            }
            i++;
        }
        if (i != actual.size()) {
            return new Result(true, "bottle recorded more stations than the recipe expects: " + actual);
        }
        return new Result(false, "matches expected sequence " + expectedStations);
    }
}
