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
