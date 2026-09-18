package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The one real console, shared by BatchManagerCD and SafetyMonitorCD when both run in the
 * same process (coordinator_real.xml) -- two independent Scanners on System.in would
 * otherwise race for lines. Safety keywords (hazard/clear/reset) are intercepted here and
 * never reach the order queue; every other line is handed to the batch order entry loop
 * exactly as before.
 */
final class SharedConsole {
    static final String EOF_MARKER = "##END_OF_CONSOLE_INPUT##";
    private static final BlockingQueue<String> orderLines = new ArrayBlockingQueue<String>(64);
    private static volatile SafetyMonitorModel safety;
    private static boolean started = false;

    static synchronized void attachSafety(SafetyMonitorModel model) {
        safety = model;
        ensureStarted();
    }

    /** The attached SafetyMonitorModel, if SafetyMonitorCD is present in this profile (every
     * XML wiring that includes it constructs one very early, well before any GUI button click
     * could reach this) -- null otherwise. Lets IpBatchManagerModel forward a GUI-triggered
     * reset exactly the way a typed 'reset' already does, without IpGuiFrame needing its own
     * reference to a completely separate clock domain's model object. */
    static SafetyMonitorModel safety() {
        return safety;
    }

    static synchronized void ensureStarted() {
        if (started) { return; }
        started = true;
        Thread reader = new Thread(new Runnable() {
            public void run() { readLoop(); }
        }, "shared-console");
        reader.setDaemon(true);
        reader.start();
    }

    static BlockingQueue<String> orderLines() {
        ensureStarted();
        return orderLines;
    }

    private static void readLoop() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String raw = in.nextLine();
            SafetyMonitorModel current = safety;
            if (current != null && current.tryConsole(raw.trim())) { continue; }
            try {
                orderLines.put(raw);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        try {
            orderLines.put(EOF_MARKER);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        }
    }
}
