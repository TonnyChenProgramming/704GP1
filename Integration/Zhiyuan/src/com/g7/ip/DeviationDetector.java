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
     */
    public Result checkStationSequence(String bottleId, List<String> expectedStations) throws SQLException {
        List<String> actual = dao.queryStationSequence(bottleId);

        for (String entry : actual) {
            if (entry.endsWith(":FAULT")) {
                return new Result(true, "fault recorded at " + entry.split(":")[0]);
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
