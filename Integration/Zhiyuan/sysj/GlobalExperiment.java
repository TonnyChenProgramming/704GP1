package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Availability experiment control plane V4 -- EVENT ALIGNED.
 *
 * Final-experiment intent:
 *   same B2 testbench + same semantic event + same 500 ms HALT
 *   centralised CoordinatorCD vs decomposed BATCH/PRODUCTION/SAFETY CDs.
 *
 * EVENT mode is the recommended mode:
 *   - an event source calls markEvent("FILLER_DONE"), etc.;
 *   - occurrence N is counted globally within the selected B2 mission;
 *   - afterEventMs defaults to 0;
 *   - the target CD enters fail-silent functional-plane HALT;
 *   - the out-of-band clock/control reaction continues so recovery timing
 *     remains independent of the halted functional plane.
 *
 * The HALT is deliberately a reaction-boundary crash-stop abstraction:
 * already-entered synchronous rendezvous/await statements are not forcibly
 * killed by Java; subsequent functional-plane iterations are gated.
 *
 * Required experiment properties:
 *   -Dexperiment.enabled=true
 *   -Dexperiment.target=PRODUCTION
 *   -Dexperiment.batch=B2
 *   -Dexperiment.injectMode=EVENT
 *   -Dexperiment.injectEvent=FILLER_DONE
 *   -Dexperiment.injectOccurrence=1
 *   -Dexperiment.afterEventMs=0
 *   -Dexperiment.durationMs=500
 *   -Dexperiment.exitOnResult=true
 *   -Dexperiment.resultFile=<path>
 */
public final class GlobalExperiment {

    private static final boolean ENABLED =
        Boolean.parseBoolean(System.getProperty("experiment.enabled", "false"));

    private static final String TARGET =
        normalise(System.getProperty("experiment.target", "NONE"));

    private static final String REQUESTED_BATCH =
        System.getProperty("experiment.batch", "").trim();

    private static final String INJECT_MODE =
        normalise(System.getProperty("experiment.injectMode", "EVENT"));

    private static final String INJECT_EVENT =
        normalise(System.getProperty("experiment.injectEvent", "BATCH_ACCEPTED"));

    private static final int INJECT_OCCURRENCE =
        Math.max(1, parseInt(System.getProperty("experiment.injectOccurrence", "1"), 1));

    private static final long AFTER_EVENT_MS =
        Math.max(0L, parseLong(System.getProperty("experiment.afterEventMs", "0"), 0L));

    // TIME/TICK remain available only for compatibility/diagnostics.
    private static final long INJECT_AFTER_MS =
        Math.max(0L, parseLong(System.getProperty("experiment.injectAfterMs", "0"), 0L));

    private static final long INJECT_TICK =
        parseLong(System.getProperty("experiment.injectTick", "-1"), -1L);

    private static final long DURATION_MS =
        Math.max(0L, parseLong(System.getProperty("experiment.durationMs", "0"), 0L));

    private static final boolean EXIT_ON_RESULT =
        Boolean.parseBoolean(System.getProperty("experiment.exitOnResult", "false"));

    private static final boolean LOG_ALL_EVENTS =
        Boolean.parseBoolean(System.getProperty("experiment.logAllEvents", "false"));

    private static final String RESULT_FILE =
        System.getProperty("experiment.resultFile", "").trim();

    private static long coordinatorClock = -1L;
    private static long batchClock = -1L;
    private static long productionClock = -1L;
    private static long safetyClock = -1L;

    private static long coordinatorStartTick = -1L;
    private static long batchStartTick = -1L;
    private static long productionStartTick = -1L;
    private static long safetyStartTick = -1L;

    private static long missionStartNs = -1L;
    private static long missionEndNs = -1L;

    private static long matchedEventNs = -1L;
    private static int matchedEventOccurrence = 0;
    private static boolean eventMatched = false;

    private static long haltStartNs = -1L;
    private static long haltPlannedEndNs = -1L;
    private static long haltEndNs = -1L;

    private static long haltStartLocalTick = -1L;
    private static long haltEndLocalTick = -1L;
    private static long localTicksDuringHalt = 0L;

    private static boolean armed = false;
    private static boolean finished = false;
    private static boolean haltTriggered = false;
    private static boolean haltRecovered = false;

    private static String batchId = "";

    private static final Map<String, Integer> eventCounts =
        new LinkedHashMap<String, Integer>();

    private GlobalExperiment() {}

    private static int parseInt(String value, int fallback) {
        try { return Integer.parseInt(value.trim()); }
        catch (Exception ignored) { return fallback; }
    }

    private static long parseLong(String value, long fallback) {
        try { return Long.parseLong(value.trim()); }
        catch (Exception ignored) { return fallback; }
    }

    private static String normalise(String value) {
        return value == null ? "" : value.trim().toUpperCase();
    }

    private static boolean supportedTarget(String component) {
        return "COORDINATOR".equals(component)
            || "BATCH".equals(component)
            || "PRODUCTION".equals(component)
            || "SAFETY".equals(component);
    }

    private static long getClockUnsafe(String component) {
        if ("COORDINATOR".equals(component)) return coordinatorClock;
        if ("BATCH".equals(component)) return batchClock;
        if ("PRODUCTION".equals(component)) return productionClock;
        if ("SAFETY".equals(component)) return safetyClock;
        return -1L;
    }

    private static void incrementClockUnsafe(String component) {
        if ("COORDINATOR".equals(component)) coordinatorClock++;
        else if ("BATCH".equals(component)) batchClock++;
        else if ("PRODUCTION".equals(component)) productionClock++;
        else if ("SAFETY".equals(component)) safetyClock++;
    }

    private static long getStartTickUnsafe(String component) {
        if ("COORDINATOR".equals(component)) return coordinatorStartTick;
        if ("BATCH".equals(component)) return batchStartTick;
        if ("PRODUCTION".equals(component)) return productionStartTick;
        if ("SAFETY".equals(component)) return safetyStartTick;
        return -1L;
    }

    private static long relativeTickUnsafe(String component) {
        long start = getStartTickUnsafe(component);
        if (!armed || start < 0L) return -1L;
        return getClockUnsafe(component) - start;
    }

    private static String batchIdFromFrame(String frame) {
        if (frame == null) return "";
        String[] p = frame.split("\\|", -1);
        return p.length >= 2 ? p[1] : "";
    }

    private static boolean configuredFaultTarget() {
        if (!supportedTarget(TARGET) || DURATION_MS <= 0L) return false;

        if ("EVENT".equals(INJECT_MODE)) {
            return INJECT_EVENT.length() > 0 && INJECT_OCCURRENCE >= 1;
        }
        if ("TIME".equals(INJECT_MODE)) return INJECT_AFTER_MS >= 0L;
        if ("TICK".equals(INJECT_MODE)) return INJECT_TICK >= 0L;

        return false;
    }

    /**
     * Out-of-band experiment-control reaction. It is NEVER gated by the fault.
     * This makes HALT recovery timing independent of the functional plane.
     */
    public static synchronized void clockTick(String component) {
        String c = normalise(component);
        if (!supportedTarget(c)) return;

        incrementClockUnsafe(c);

        if (!ENABLED || !armed || finished) return;

        long now = System.nanoTime();

        if (c.equals(TARGET)) {
            updateHaltUnsafe(c, now);

            if (haltTriggered && !haltRecovered) {
                localTicksDuringHalt++;
            }
        }
    }

    /**
     * Arms the selected B2 mission immediately AFTER validation/acceptance.
     * The caller should then emit BATCH_ACCEPTED as the first semantic event.
     */
    public static synchronized void arm(String acceptedActivateFrame) {
        if (!ENABLED || armed || finished) return;

        String candidate = batchIdFromFrame(acceptedActivateFrame);
        if (candidate.length() == 0) return;

        if (REQUESTED_BATCH.length() != 0
                && !REQUESTED_BATCH.equals(candidate)) return;

        armed = true;
        batchId = candidate;

        coordinatorStartTick = coordinatorClock;
        batchStartTick = batchClock;
        productionStartTick = productionClock;
        safetyStartTick = safetyClock;

        missionStartNs = System.nanoTime();

        eventCounts.clear();

        eventMatched = false;
        matchedEventNs = -1L;
        matchedEventOccurrence = 0;

        haltTriggered = false;
        haltRecovered = false;
        haltStartNs = -1L;
        haltPlannedEndNs = -1L;
        haltEndNs = -1L;
        haltStartLocalTick = -1L;
        haltEndLocalTick = -1L;
        localTicksDuringHalt = 0L;

        System.out.println(
            "EXPERIMENT ARMED" +
            " batch=" + batchId +
            " target=" + TARGET +
            " injectMode=" + INJECT_MODE +
            " injectEvent=" + INJECT_EVENT +
            " injectOccurrence=" + INJECT_OCCURRENCE +
            " afterEventMs=" + AFTER_EVENT_MS +
            " durationMs=" + DURATION_MS
        );
        System.out.flush();
    }

    /**
     * Marks an architecture-independent semantic production event.
     *
     * For afterEventMs=0 the HALT timestamp begins immediately at this event.
     * The current reaction is allowed to finish; subsequent target functional
     * iterations are blocked by shouldRun().
     */
    public static synchronized void markEvent(String eventName) {
        if (!ENABLED || !armed || finished) return;

        String e = normalise(eventName);
        if (e.length() == 0) return;

        Integer previous = eventCounts.get(e);
        int occurrence = previous == null ? 1 : previous.intValue() + 1;
        eventCounts.put(e, Integer.valueOf(occurrence));

        long now = System.nanoTime();

        if (LOG_ALL_EVENTS || e.equals(INJECT_EVENT)) {
            System.out.println(
                "EXPERIMENT EVENT" +
                " name=" + e +
                " occurrence=" + occurrence +
                " missionElapsedMs=" + nsToMs(now - missionStartNs)
            );
            System.out.flush();
        }

        if (!configuredFaultTarget()
                || !"EVENT".equals(INJECT_MODE)
                || eventMatched
                || !e.equals(INJECT_EVENT)
                || occurrence != INJECT_OCCURRENCE) {
            return;
        }

        eventMatched = true;
        matchedEventNs = now;
        matchedEventOccurrence = occurrence;

        // Exact event-aligned start when no post-event delay was requested.
        if (AFTER_EVENT_MS == 0L) {
            startHaltUnsafe(TARGET, now);
        }
    }

    /** Functional-plane gate for one coordinator CD. */
    public static synchronized boolean shouldRun(String component) {
        String c = normalise(component);

        if (!ENABLED || !armed || finished) return true;
        if (!c.equals(TARGET) || !configuredFaultTarget()) return true;

        long now = System.nanoTime();
        updateHaltUnsafe(c, now);

        return !(haltTriggered && !haltRecovered);
    }

    private static void updateHaltUnsafe(String component, long nowNs) {
        if (!configuredFaultTarget() || finished) return;

        if (!haltTriggered) {
            boolean trigger = false;

            if ("EVENT".equals(INJECT_MODE)) {
                trigger = eventMatched
                    && matchedEventNs >= 0L
                    && nowNs - matchedEventNs >= AFTER_EVENT_MS * 1000000L;
            } else if ("TIME".equals(INJECT_MODE)) {
                trigger = missionStartNs >= 0L
                    && nowNs - missionStartNs >= INJECT_AFTER_MS * 1000000L;
            } else if ("TICK".equals(INJECT_MODE)) {
                trigger = relativeTickUnsafe(component) >= INJECT_TICK;
            }

            if (trigger) startHaltUnsafe(component, nowNs);
        }

        if (haltTriggered
                && !haltRecovered
                && nowNs >= haltPlannedEndNs) {

            haltRecovered = true;
            haltEndNs = nowNs;
            haltEndLocalTick = relativeTickUnsafe(component);

            System.out.println(
                "EXPERIMENT HALT_END" +
                " target=" + TARGET +
                " actualDownMs=" + nsToMs(haltEndNs - haltStartNs) +
                " haltEndLocalTick=" + haltEndLocalTick +
                " localTicksDuringHalt=" + localTicksDuringHalt
            );
            System.out.flush();
        }
    }

    private static void startHaltUnsafe(String component, long nowNs) {
        if (haltTriggered || !configuredFaultTarget()) return;

        haltTriggered = true;
        haltStartNs = nowNs;
        haltPlannedEndNs = haltStartNs + DURATION_MS * 1000000L;
        haltStartLocalTick = relativeTickUnsafe(component);

        double afterMatchedMs =
            eventMatched && matchedEventNs >= 0L
                ? nsToMs(haltStartNs - matchedEventNs)
                : -1.0;

        System.out.println(
            "EXPERIMENT HALT_START" +
            " target=" + TARGET +
            " mode=" + INJECT_MODE +
            " event=" + INJECT_EVENT +
            " occurrence=" + INJECT_OCCURRENCE +
            " missionElapsedMs=" + nsToMs(haltStartNs - missionStartNs) +
            " afterMatchedEventMs=" + afterMatchedMs +
            " haltStartLocalTick=" + haltStartLocalTick +
            " requestedDurationMs=" + DURATION_MS
        );
        System.out.flush();
    }

    /**
     * Records DRAINED or FAULT before the synchronous batchResult send.
     */
    public static synchronized void recordBatchResult(String resultFrame) {
        if (!ENABLED || !armed || finished || resultFrame == null) return;

        boolean drained = resultFrame.startsWith("DRAINED|");
        boolean faulted = resultFrame.startsWith("FAULT|");

        if (!drained && !faulted) return;

        String terminalBatch = batchIdFromFrame(resultFrame);
        if (!batchId.equals(terminalBatch)) return;

        long now = System.nanoTime();

        if (supportedTarget(TARGET)) {
            updateHaltUnsafe(TARGET, now);
        }

        missionEndNs = now;

        long actualDownNs = 0L;
        if (haltTriggered) {
            long effectiveEnd = haltRecovered ? haltEndNs : missionEndNs;
            actualDownNs = Math.max(0L, effectiveEnd - haltStartNs);
        }

        String terminalStatus = drained ? "DRAINED" : "FAULT";
        String faultReason = "NONE";

        if (faulted) {
            String[] p = resultFrame.split("\\|", 3);
            if (p.length >= 3) faultReason = sanitise(p[2]);
        }

        double eventMatchMissionMs =
            eventMatched && matchedEventNs >= 0L
                ? nsToMs(matchedEventNs - missionStartNs)
                : -1.0;

        double haltStartAfterEventMs =
            haltTriggered && eventMatched && matchedEventNs >= 0L
                ? nsToMs(haltStartNs - matchedEventNs)
                : -1.0;

        String line =
            "EXPERIMENT_RESULT" +
            " batch=" + batchId +
            " terminalStatus=" + terminalStatus +
            " faultReason=" + faultReason +
            " target=" + TARGET +
            " injectMode=" + INJECT_MODE +
            " injectEvent=" + INJECT_EVENT +
            " injectOccurrence=" + INJECT_OCCURRENCE +
            " afterEventMs=" + AFTER_EVENT_MS +
            " eventMatched=" + eventMatched +
            " matchedEventOccurrence=" + matchedEventOccurrence +
            " eventMatchMissionMs=" + eventMatchMissionMs +
            " requestedDurationMs=" + DURATION_MS +
            " haltTriggered=" + haltTriggered +
            " haltRecovered=" + haltRecovered +
            " haltStartAfterEventMs=" + haltStartAfterEventMs +
            " haltStartLocalTick=" + haltStartLocalTick +
            " haltEndLocalTick=" + haltEndLocalTick +
            " localTicksDuringHalt=" + localTicksDuringHalt +
            " actualDownMs=" + nsToMs(actualDownNs) +
            " missionMs=" + nsToMs(missionEndNs - missionStartNs) +
            " coordinatorElapsedTicks=" + elapsedTicksUnsafe("COORDINATOR") +
            " batchElapsedTicks=" + elapsedTicksUnsafe("BATCH") +
            " productionElapsedTicks=" + elapsedTicksUnsafe("PRODUCTION") +
            " safetyElapsedTicks=" + elapsedTicksUnsafe("SAFETY");

        System.out.println(line);
        System.out.flush();

        writeResultFile(line);
        finished = true;
    }

    public static synchronized void exitIfRequested() {
        if (EXIT_ON_RESULT && finished) {
            System.out.flush();
            System.err.flush();
            System.exit(0);
        }
    }

    public static synchronized void batchResult(String resultFrame) {
        recordBatchResult(resultFrame);
        exitIfRequested();
    }

    public static synchronized long localTick(String component) {
        String c = normalise(component);
        return supportedTarget(c) ? relativeTickUnsafe(c) : -1L;
    }

    public static boolean enabled() { return ENABLED; }

    public static String target() { return TARGET; }

    private static long elapsedTicksUnsafe(String component) {
        long relative = relativeTickUnsafe(component);
        return relative < 0L ? 0L : relative + 1L;
    }

    private static double nsToMs(long ns) {
        return ((double) ns) / 1000000.0;
    }

    private static String sanitise(String value) {
        if (value == null) return "";
        return value.replace(' ', '_')
                    .replace('\n', '_')
                    .replace('\r', '_');
    }

    private static void writeResultFile(String line) {
        if (RESULT_FILE.length() == 0) return;

        try {
            Path path = Paths.get(RESULT_FILE);
            Path parent = path.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            Files.write(
                path,
                (line + System.lineSeparator())
                    .getBytes(StandardCharsets.UTF_8)
            );
        } catch (IOException failure) {
            System.err.println(
                "EXPERIMENT RESULT_FILE_WRITE_FAILED " + failure.getMessage()
            );
        }
    }
}
