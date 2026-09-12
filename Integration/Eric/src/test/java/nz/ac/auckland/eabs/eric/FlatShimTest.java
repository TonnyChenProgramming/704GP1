package nz.ac.auckland.eabs.eric;

import nz.ac.auckland.eabs.eric.systemj.FlatFinishingShim;
import nz.ac.auckland.eabs.eric.systemj.FinishingCycle;
import nz.ac.auckland.eabs.eric.systemj.FinishingPlantEndpoint;

/** Deterministic endpoint/codec tests. The separate SystemJ test verifies actual rendezvous. */
public final class FlatShimTest {
    private FlatShimTest() { }
    public static void runAll() {
        String simulation = System.getProperty("eric.finishing.flatSimulation");
        String testMode = System.getProperty("eric.finishing.testMode");
        try {
            System.clearProperty("eric.finishing.flatSimulation");
            expectFailure(() -> new FlatFinishingShim("LID"));
            System.setProperty("eric.finishing.flatSimulation", "true");
            System.setProperty("eric.finishing.testMode", "true");
            for (String station : new String[]{"LID", "CAPPER", "LABELLER", "UNLOADER"}) { runPlan(station); }
            testUnconfirmedStop();
            testBadCorrelation();
            testInvalidRepair();
            System.clearProperty("eric.finishing.testMode");
            FlatFinishingShim shim = boot();
            shim.received("START|J|WP|LID|PLACE_LID|simMode=FAULT");
            require(shim.nextValue().endsWith("START_REJECTED_EXTRA_DATA_NOT_SUPPORTED"), "Fault injection escaped test gate");
            System.out.println("ERIC FLAT SHIM JAVA TESTS PASSED");
        } finally { restore("eric.finishing.flatSimulation", simulation); restore("eric.finishing.testMode", testMode); }
    }
    private static void runPlan(String station) {
        String machine = "LID".equals(station) ? "LidLoaderController" : "CAPPER".equals(station)
                ? "CapperController" : "LABELLER".equals(station) ? "LabelerController" : "UnloaderController";
        FlatFinishingShim shim = new FlatFinishingShim(station);
        FinishingCycle cycle = new FinishingCycle(machine);
        FinishingPlantEndpoint plant = new FinishingPlantEndpoint(machine);
        FlatShimPlan plan = new FlatShimPlan(station);
        String pending = "READY|" + machine, controlReply = null;
        int repairCount = 0, resetCount = 0;
        for (int turns = 0; !"FINISHED".equals(plan.action()); turns++) {
            require(turns < 2000, "Nonterminating shim model");
            String action = shim.nextAction(), value = shim.nextValue();
            if ("COMMAND_IN".equals(action)) {
                require("JOB".equals(plan.action()), "Expected command"); shim.received(plan.value()); plan.sent();
            } else if ("ACK_IN".equals(action)) {
                require("ACK".equals(plan.action()), "Expected ACK"); shim.received(plan.value()); plan.sent();
            } else if ("REPORT_OUT".equals(action)) { plan.check(value); shim.afterSend(); }
            else if ("CONTROL_OUT".equals(action)) { controlReply = cycle.control(value); shim.afterSend(); }
            else if ("CONTROL_IN".equals(action)) { shim.received(controlReply); controlReply = null; }
            else if ("JOB_OUT".equals(action)) { cycle.accept(value); pending = cycle.reply(); shim.afterSend(); }
            else if ("ACK_OUT".equals(action)) {
                if (value.startsWith("CLEAR_FAULT|")) { repairCount++; }
                if (value.startsWith("RESET|")) { resetCount++; }
                String plantCommand = cycle.acknowledge(value);
                if (!plantCommand.isEmpty()) { cycle.finishAcknowledgement(plant.accept(plantCommand)); }
                pending = cycle.reply(); shim.afterSend();
            } else if ("REPORT_IN".equals(action)) {
                if (pending == null) {
                    for (int step = 0; !cycle.latched(); step++) {
                        require(step < 100, "Nonterminating plant"); cycle.observe(plant.accept(cycle.nextPlantCommand()));
                    }
                    pending = cycle.reply();
                }
                String report = pending; pending = report.startsWith("REJECTED|") ? "READY|" + machine : null;
                shim.received(report);
            } else { throw new AssertionError("Unexpected shim action " + action); }
        }
        require(repairCount == 4 && resetCount == 4, "Recovery must run once for each accepted fault, not rejected START");
        require(shim.ignoredAcknowledgements() == 2, "Wrong ACK test did not exercise both correlations");
        require("COMMAND_IN".equals(shim.nextAction()), "Final READY not released");
        System.out.println("ERIC FLAT SHIM JAVA " + station + ": " + plan.checks() + " reports");
    }
    private static FlatFinishingShim boot() {
        FlatFinishingShim shim = new FlatFinishingShim("LID");
        shim.received("READY|LidLoaderController");
        require("PERMIT|shim-boot-LID|true".equals(shim.nextValue()), "Wrong boot grant");
        shim.afterSend(); shim.received("CONTROL_ACK|shim-boot-LID|LidLoaderController"); shim.afterSend();
        return shim;
    }
    private static FlatFinishingShim busy() {
        FlatFinishingShim shim = boot(); shim.received("START|J|WP|LID|PLACE_LID"); shim.afterSend();
        shim.received("BUSY|J|WP|LidLoaderController"); shim.afterSend(); return shim;
    }
    private static void testUnconfirmedStop() {
        FlatFinishingShim shim = busy();
        shim.received("FAULT|J|WP|LidLoaderController|safe=false|reason=STOP_UNCONFIRMED"); shim.afterSend();
        require("HOLD".equals(shim.nextAction()), "Unsafe fault must not auto-reset or advertise READY");
    }
    private static void testBadCorrelation() {
        FlatFinishingShim shim = busy();
        expectFailure(() -> shim.received("DONE|OLD|WP|LidLoaderController|safe=true"));
        require("REPORT_IN".equals(shim.nextAction()), "Bad correlation released the job");
        expectFailure(() -> shim.received("DONE|J|WP|LidLoaderController|safe=true"));
    }
    private static void testInvalidRepair() {
        FlatFinishingShim shim = busy();
        shim.received("FAULT|J|WP|LidLoaderController|safe=true|reason=JAM"); shim.afterSend();
        require(shim.nextValue().startsWith("CLEAR_FAULT|"), "Missing repair"); shim.afterSend();
        expectFailure(() -> shim.received("ACK_REJECTED|J|WP|LidLoaderController|reason=UNSAFE_RESET"));
        require("REPORT_IN".equals(shim.nextAction()), "Unconfirmed repair must not release READY");
    }
    private static void expectFailure(Runnable action) {
        try { action.run(); } catch (IllegalArgumentException | IllegalStateException expected) { return; }
        throw new AssertionError("Expected rejection");
    }
    private static void restore(String key, String value) { if (value == null) { System.clearProperty(key); } else { System.setProperty(key, value); } }
    private static void require(boolean condition, String message) { if (!condition) { throw new AssertionError(message); } }
}
