package nz.ac.auckland.eabs.eric.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Sensor evidence produced by a separate simulated plant model. */
public final class PlantResult {
    private final boolean successful;
    private final String detail;
    private final Map<String, String> sensorEvidence;

    private PlantResult(
            boolean successful,
            String detail,
            Map<String, String> sensorEvidence) {
        this.successful = successful;
        this.detail = detail == null ? "" : detail;
        this.sensorEvidence = Collections.unmodifiableMap(
                sensorEvidence == null
                        ? new LinkedHashMap<String, String>()
                        : new LinkedHashMap<String, String>(sensorEvidence));
    }

    public static PlantResult success(
            String detail,
            Map<String, String> sensorEvidence) {
        return new PlantResult(true, detail, sensorEvidence);
    }

    public static PlantResult fault(String reason) {
        return new PlantResult(false, reason, null);
    }

    public boolean isSuccessful() { return successful; }
    public String getDetail() { return detail; }
    public Map<String, String> getSensorEvidence() { return sensorEvidence; }
}
