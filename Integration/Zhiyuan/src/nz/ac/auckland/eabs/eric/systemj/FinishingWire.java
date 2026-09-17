package nz.ac.auckland.eabs.eric.systemj;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.Operation;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/** Strict local wire codec. SystemJ sees only String/boolean/int methods. */
public final class FinishingWire {
    private FinishingWire() { }

    public static Operation operation(String machine) {
        if ("LidLoaderController".equals(machine)) { return Operation.PLACE_LID; }
        if ("CapperController".equals(machine)) { return Operation.CAP_BOTTLE; }
        if ("LabelerController".equals(machine)) { return Operation.APPLY_LABEL; }
        if ("UnloaderController".equals(machine)) { return Operation.UNLOAD; }
        throw new IllegalArgumentException("UNKNOWN_MACHINE");
    }

    public static String[] fields(String wire) {
        if (wire == null || wire.length() > 16384) { throw new IllegalArgumentException("INVALID_FRAME"); }
        return wire.split("\\|", -1);
    }

    public static String id(String value) {
        if (value == null || !value.matches("[A-Za-z0-9_.:-]{1,100}")) {
            throw new IllegalArgumentException("INVALID_ID");
        }
        return value;
    }

    public static FinishingJob start(String wire, String machine) {
        String[] f = fields(wire);
        if (f.length < 5 || !("START".equals(f[0]) || "BEGIN".equals(f[0]))) {
            throw new IllegalArgumentException("INVALID_START");
        }
        id(f[1]); id(f[2]);
        if (!machine.equals(f[3])) { throw new IllegalArgumentException("WRONG_MACHINE"); }
        Operation op = operation(machine);
        if (!op.name().equals(f[4])) { throw new IllegalArgumentException("WRONG_OPERATION"); }
        Map<String, String> data = data(f, 5);
        for (String key : data.keySet()) {
            if (!Arrays.asList("bottlePresent", "filled", "lidAvailable", "lidPresent",
                    "manufacturingComplete", "labelPayload", "collectionAvailable", "timeoutTicks", "simMode").contains(key)) {
                throw new IllegalArgumentException("UNKNOWN_JOB_FIELD");
            }
            if (!Arrays.asList("labelPayload", "timeoutTicks", "simMode").contains(key)
                    && !"true".equals(data.get(key)) && !"false".equals(data.get(key))) {
                throw new IllegalArgumentException("INVALID_BOOLEAN");
            }
        }
        int limit = Integer.parseInt(data.containsKey("timeoutTicks") ? data.get("timeoutTicks") : "20");
        if (limit < 1 || limit > 1000) { throw new IllegalArgumentException("INVALID_TIMEOUT"); }
        if (data.containsKey("simMode")) {
            if (!Boolean.getBoolean("eric.finishing.testMode")) { throw new IllegalArgumentException("TEST_MODE_DISABLED"); }
            if (!Arrays.asList("NORMAL", "FAULT", "STALL", "BAD_CORRELATION", "MISSING_EVIDENCE").contains(data.get("simMode"))) {
                throw new IllegalArgumentException("INVALID_SIM_MODE");
            }
        }
        return new FinishingJob(f[1], f[2], machine, op, data);
    }

    public static Map<String, String> data(String[] fields, int offset) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        for (int n = offset; n < fields.length; n++) {
            int equals = fields[n].indexOf('=');
            if (equals < 1) { throw new IllegalArgumentException("INVALID_DATA"); }
            String key = fields[n].substring(0, equals);
            if (!key.matches("[A-Za-z][A-Za-z0-9_]*") || result.containsKey(key)) {
                throw new IllegalArgumentException("DUPLICATE_OR_INVALID_KEY");
            }
            result.put(key, decode(fields[n].substring(equals + 1)));
        }
        return result;
    }

    public static String data(Map<String, String> values) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            result.append('|').append(entry.getKey()).append('=').append(encode(entry.getValue()));
        }
        return result.toString();
    }

    public static String correlation(FinishingJob job) {
        return job.getJobId() + "|" + job.getWorkpieceId() + "|" + job.getMachineId();
    }

    public static boolean matches(String[] frame, FinishingJob job) {
        return frame.length >= 4 && job != null && job.getJobId().equals(frame[1])
                && job.getWorkpieceId().equals(frame[2]) && job.getMachineId().equals(frame[3]);
    }

    public static String[] requiredEvidence(String machine) {
        if ("LidLoaderController".equals(machine)) { return new String[]{"lidPresent", "placementComplete", "armHome"}; }
        if ("CapperController".equals(machine)) { return new String[]{"capSecured", "clampHome", "gripperHome", "cycleComplete"}; }
        if ("LabelerController".equals(machine)) { return new String[]{"bottleStopped", "labelApplied", "bottleReleased"}; }
        operation(machine);
        return new String[]{"bottleCollected", "outputPathClear"};
    }

    private static String encode(String value) {
        try { return URLEncoder.encode(value, "UTF-8"); }
        catch (UnsupportedEncodingException impossible) { throw new AssertionError(impossible); }
    }
    private static String decode(String value) {
        try { return URLDecoder.decode(value, "UTF-8"); }
        catch (UnsupportedEncodingException impossible) { throw new AssertionError(impossible); }
    }
}
