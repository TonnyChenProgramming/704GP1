package nz.ac.auckland.eabs.eric;

import nz.ac.auckland.eabs.eric.systemj.FinishingCycle;
import nz.ac.auckland.eabs.eric.systemj.FinishingPlantEndpoint;
import nz.ac.auckland.eabs.eric.systemj.FinishingWire;
import java.util.Map;

/** Deterministic model tests complement (never replace) the real SystemJ run. */
public final class FinishingEndpointTest {
    private static int checks;
    private FinishingEndpointTest() { }

    public static void runAll() {
        checks = 0;
        String previous = System.getProperty("eric.finishing.testMode");
        System.setProperty("eric.finishing.testMode", "true");
        try {
            for (String machine : new String[]{"LidLoaderController", "CapperController", "LabelerController", "UnloaderController"}) {
                exercise(machine);
            }
            System.clearProperty("eric.finishing.testMode");
            FinishingCycle production = new FinishingCycle("LidLoaderController");
            production.control("PERMIT|INIT|true");
            require(!production.accept(job("LidLoaderController", "TEST-DISABLED", "|simMode=FAULT")), "Test injection is opt-in");
        } finally {
            if (previous == null) { System.clearProperty("eric.finishing.testMode"); }
            else { System.setProperty("eric.finishing.testMode", previous); }
        }
        System.out.println("ERIC DYNAMIC FINISHING MODEL TESTS PASSED: " + checks + " checks");
    }

    private static void exercise(String machine) {
        FinishingCycle cycle = new FinishingCycle(machine);
        FinishingPlantEndpoint plant = new FinishingPlantEndpoint(machine);
        require(!cycle.accept(job(machine, "NO-PERMIT", "")), "Missing permit cannot actuate");
        require(plant.getCycles() == 0, "Rejection starts no plant cycle");
        cycle.control("PERMIT|INIT|true");
        for (int n = 1; n <= 3; n++) {
            String id = "DYNAMIC-" + n;
            require(cycle.accept(job(machine, id, "")), "Dynamic ID accepted");
            require(cycle.reply().equals("BUSY|" + id + "|WP-" + id + "|" + machine), "BUSY correlation");
            drive(cycle, plant);
            require("DONE".equals(cycle.state()), "Normal plant completes");
            Map<String, String> evidence = data(cycle.reply());
            for (String key : FinishingWire.requiredEvidence(machine)) { require("true".equals(evidence.get(key)), "Terminal sensor " + key); }
            if ("LabelerController".equals(machine)) {
                require(("Bottle|WP-" + id + "=A+B").equals(evidence.get("printedPayload")), "Encoded label preserved");
            }
            require(cycle.acknowledge("ACK|STALE|WP-" + id).isEmpty(), "Bad ACK never drives plant");
            require("DONE".equals(cycle.state()), "Bad ACK cannot clear DONE");
            cycle.acknowledge("ACK|" + id + "|WP-" + id);
            require("READY".equals(cycle.state()), "Matching ACK releases controller");
            require(!cycle.accept(job(machine, id, "")), "Duplicate START rejected after ACK");
            require(plant.getCycles() == n, "At most one cycle per accepted job");
        }
        for (String mode : new String[]{"FAULT", "STALL", "BAD_CORRELATION", "MISSING_EVIDENCE"}) {
            String id = "FAULT-" + mode;
            require(cycle.accept(job(machine, id, "|simMode=" + mode + "|timeoutTicks=5")), "Fault scenario accepted");
            drive(cycle, plant);
            require("FAULT".equals(cycle.state()), "Failed/stale/incomplete evidence cannot produce DONE");
            require("true".equals(data(cycle.reply()).get("safe")) && plant.isSafe(), "Abort confirmed safe");
            acknowledge(cycle, plant, "RESET|" + id + "|WP-" + id);
            require("FAULT".equals(cycle.state()), "Reset requires plant fault clear");
            repair(cycle, plant, id);
        }

        // Safety stop is captured even if permission is restored before observe().
        String stopped = "STOP-ACTIVE";
        require(cycle.accept(job(machine, stopped, "|simMode=STALL")), "Stop test accepted");
        step(cycle, plant); step(cycle, plant);
        cycle.control("PERMIT|LOSS|false"); cycle.control("PERMIT|RESTORE|true"); cycle.control("CLEAR_STOP|CLEAR|ignored");
        drive(cycle, plant);
        require("FAULT".equals(cycle.state()), "Transient permit loss is latched");
        acknowledge(cycle, plant, "CLEAR_FAULT|" + stopped + "|WP-" + stopped);
        require(cycle.reply().startsWith("ACK_REJECTED|"), "Invalid clear-stop cannot bypass stop latch");
        cycle.control("CLEAR_STOP|CLEAR-VALID"); repair(cycle, plant, stopped);

        // A stop received after BUSY but before BEGIN must not act on the previous bottle.
        int before = plant.getCycles();
        require(cycle.accept(job(machine, "BEFORE-BEGIN", "")), "New pending job");
        cycle.control("STOP|BEFORE"); drive(cycle, plant);
        require("true".equals(data(cycle.reply()).get("safe")), "Idle abort is correlated to pending job");
        require(plant.getCycles() == before, "Stop before BEGIN causes no cycle");
        cycle.control("CLEAR_STOP|BEFORE-CLEAR"); repair(cycle, plant, "BEFORE-BEGIN");

        // Do not claim safe or allow reset when the stop acknowledgement is wrong.
        require(cycle.accept(job(machine, "UNKNOWN-STOP", "")), "Unknown stop job accepted");
        step(cycle, plant); cycle.control("STOP|UNKNOWN-STOP");
        require(cycle.nextPlantCommand().startsWith("ABORT|"), "Stop sent to plant");
        cycle.observe("EVIDENCE|WRONG|WRONG|" + machine + "|ABORTED|phase=0|safe=true|active=false");
        require("FAULT".equals(cycle.state()) && "false".equals(data(cycle.reply()).get("safe")), "Unconfirmed stop is unsafe/unknown");
        cycle.control("CLEAR_STOP|UNKNOWN-CLEAR");
        require(cycle.acknowledge("RESET|UNKNOWN-STOP|WP-UNKNOWN-STOP").isEmpty(), "No plant reset without safe confirmation");
        require("FAULT".equals(cycle.state()), "Unknown physical state remains latched");

        FinishingCycle replay = new FinishingCycle(machine);
        replay.control("PERMIT|OLD|true"); replay.control("PERMIT|NEW|false");
        require(replay.control("PERMIT|OLD|true").startsWith("CONTROL_DUPLICATE|"), "Old control is idempotent");
        require(!replay.accept(job(machine, "REPLAY", "")), "Old grant cannot overwrite safety loss");
        require(replay.control("PERMIT|OLD|false").startsWith("CONTROL_REJECTED|"), "Conflicting control ID rejected");
    }

    private static String job(String machine, String id, String extra) { return FinishingSystemJPlan.job(machine, id, "WP-" + id, extra); }
    private static Map<String, String> data(String frame) { return FinishingWire.data(FinishingWire.fields(frame), 4); }
    private static void step(FinishingCycle cycle, FinishingPlantEndpoint plant) { cycle.observe(plant.accept(cycle.nextPlantCommand())); }
    private static void drive(FinishingCycle cycle, FinishingPlantEndpoint plant) {
        for (int n = 0; n < 30 && !cycle.latched(); n++) { step(cycle, plant); }
        require(cycle.latched(), "Cycle must terminate within its bound");
    }
    private static void acknowledge(FinishingCycle cycle, FinishingPlantEndpoint plant, String request) {
        String command = cycle.acknowledge(request);
        if (!command.isEmpty()) { cycle.finishAcknowledgement(plant.accept(command)); }
    }
    private static void repair(FinishingCycle cycle, FinishingPlantEndpoint plant, String id) {
        acknowledge(cycle, plant, "CLEAR_FAULT|" + id + "|WP-" + id);
        require("FAULT".equals(cycle.state()), "Repair alone does not reset or retry");
        acknowledge(cycle, plant, "RESET|" + id + "|WP-" + id);
        require("READY".equals(cycle.state()), "Confirmed repair and reset releases controller");
    }
    private static void require(boolean value, String message) { checks++; if (!value) { throw new AssertionError(message); } }
}
