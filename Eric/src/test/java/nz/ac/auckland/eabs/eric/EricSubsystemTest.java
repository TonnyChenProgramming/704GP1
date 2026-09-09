package nz.ac.auckland.eabs.eric;

import nz.ac.auckland.eabs.eric.controllers.AbstractFinishingController;
import nz.ac.auckland.eabs.eric.controllers.CapperController;
import nz.ac.auckland.eabs.eric.controllers.LabelerController;
import nz.ac.auckland.eabs.eric.controllers.LidLoaderController;
import nz.ac.auckland.eabs.eric.controllers.UnloaderController;
import nz.ac.auckland.eabs.eric.gui.DashboardState;
import nz.ac.auckland.eabs.eric.gui.EabsDashboardPanel;
import nz.ac.auckland.eabs.eric.gui.OperatorCommand;
import nz.ac.auckland.eabs.eric.gui.OperatorCommandType;
import nz.ac.auckland.eabs.eric.gui.VisualizationBridge;
import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.Location;
import nz.ac.auckland.eabs.eric.model.MachineReport;
import nz.ac.auckland.eabs.eric.model.MachineState;
import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.model.WorkpieceStatus;
import nz.ac.auckland.eabs.eric.persistence.RecoverableFileWorkpieceRepository;
import nz.ac.auckland.eabs.eric.plants.CapperPlant;
import nz.ac.auckland.eabs.eric.plants.LabelerPlant;
import nz.ac.auckland.eabs.eric.plants.LidLoaderPlant;
import nz.ac.auckland.eabs.eric.plants.UnloaderPlant;
import nz.ac.auckland.eabs.eric.tracking.WorkpieceSnapshot;
import nz.ac.auckland.eabs.eric.tracking.WorkpieceTracker;

import javax.swing.SwingUtilities;
import javax.imageio.ImageIO;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/** Dependency-free acceptance tests for Eric's GP work package. */
public final class EricSubsystemTest {
    private static final List<Operation> NORMAL_ROUTE = Arrays.asList(
            Operation.LOAD_BOTTLE,
            Operation.MOVE_TO_ROTARY,
            Operation.FILL_TWO_LIQUIDS,
            Operation.PLACE_LID,
            Operation.CAP_BOTTLE,
            Operation.MOVE_TO_LABELLER,
            Operation.APPLY_LABEL,
            Operation.UNLOAD);

    private EricSubsystemTest() { }

    public static void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "true");
        testNormalRouteTrackingAndPersistence();
        testControllerFaultAndSafeReset();
        testVisualizationBoundary();
        DosedAmountsTrackerTest.runAll();
        System.out.println("ERIC GP SUBSYSTEM TESTS PASSED");
    }

    private static void testNormalRouteTrackingAndPersistence()
            throws Exception {
        Path archive = Paths.get("build", "test-completed-workpieces.properties");
        Files.deleteIfExists(archive);
        WorkpieceTracker tracker = new WorkpieceTracker(
                new RecoverableFileWorkpieceRepository(archive));
        tracker.createTwin(
                "WP-001", "ORDER-001", "BATCH-001", "PRODUCT-A",
                "LIQUID-1=20.0%;LIQUID-2=80.0%", NORMAL_ROUTE);
        tracker.confirmLocation(
                "WP-001", Location.INPUT_QUEUE, 0, "loader entry photo-eye");

        confirmGeneric(
                tracker,
                job("J1", "BottleLoaderController", Operation.LOAD_BOTTLE),
                Location.INPUT_CONVEYOR, 0, "loader placed sensor");
        confirmGeneric(
                tracker,
                job("J2", "ConveyorController", Operation.MOVE_TO_ROTARY),
                Location.ROTARY_P1, 1, "bottleAtPos1");
        confirmGeneric(
                tracker,
                job("J3", "FillerController", Operation.FILL_TWO_LIQUIDS),
                Location.ROTARY_P2_FILLER, 2,
                "amount and overflow sensors");

        runOwnedController(
                tracker,
                new LidLoaderController(new LidLoaderPlant()),
                job(
                        "J4", LidLoaderController.MACHINE_ID,
                        Operation.PLACE_LID,
                        data(
                                "bottlePresent", "true",
                                "filled", "true",
                                "lidAvailable", "true")),
                Location.ROTARY_P3_LID, 3, "lid placement sensor");

        runOwnedController(
                tracker,
                new CapperController(new CapperPlant()),
                job(
                        "J5", CapperController.MACHINE_ID,
                        Operation.CAP_BOTTLE,
                        data(
                                "bottlePresent", "true",
                                "lidPresent", "true")),
                Location.ROTARY_P4_CAPPER, 4,
                "cap secured and gripper home sensors");

        confirmGeneric(
                tracker,
                job(
                        "J6", "ConveyorController",
                        Operation.MOVE_TO_LABELLER),
                Location.LABELLER, 0, "labeller photo-eye");

        String label = "PRODUCT-A|WP-001|20.0-80.0|BATCH-001";
        runOwnedController(
                tracker,
                new LabelerController(new LabelerPlant()),
                job(
                        "J7", LabelerController.MACHINE_ID,
                        Operation.APPLY_LABEL,
                        data(
                                "bottlePresent", "true",
                                "manufacturingComplete", "true",
                                "labelPayload", label)),
                Location.OUTPUT_CONVEYOR, 0, "label-applied sensor");

        runOwnedController(
                tracker,
                new UnloaderController(new UnloaderPlant()),
                job(
                        "J8", UnloaderController.MACHINE_ID,
                        Operation.UNLOAD,
                        data(
                                "bottlePresent", "true",
                                "collectionAvailable", "true")),
                Location.OUTPUT_COLLECTION, 0, "collection sensor");

        WorkpieceSnapshot archived =
                tracker.completeAndArchive("WP-001", label);
        require(
                archived.getStatus() == WorkpieceStatus.COMPLETED,
                "Normal route must complete");
        require(
                archived.getCompletedOperations().equals(NORMAL_ROUTE),
                "Every operation must be confirmed in route order");
        require(
                archived.getActualStationsUsed().size() == NORMAL_ROUTE.size(),
                "Every actual station must be retained");
        require(
                !tracker.findActive("WP-001").isPresent(),
                "Archived bottle must leave the live map");

        WorkpieceTracker restarted = new WorkpieceTracker(
                new RecoverableFileWorkpieceRepository(archive));
        WorkpieceSnapshot recovered = restarted.findArchived("WP-001").get();
        require(
                recovered.getLabelPayload().equals(label),
                "Restart must recover the final label payload");
        require(
                recovered.getOperationTimestamps().size() == NORMAL_ROUTE.size(),
                "Restart must recover operation timestamps");

        restarted.createTwin(
                "WP-002", "ORDER-001", "BATCH-001", "PRODUCT-A",
                "LIQUID-1=20.0%;LIQUID-2=80.0%", NORMAL_ROUTE);
        restarted.createTwin(
                "WP-003", "ORDER-001", "BATCH-001", "PRODUCT-A",
                "LIQUID-1=20.0%;LIQUID-2=80.0%", NORMAL_ROUTE);
        require(
                restarted.activeSnapshots().size() == 2,
                "Multiple bottle identities must coexist");
    }

    private static void testControllerFaultAndSafeReset() {
        LabelerPlant plant = new LabelerPlant();
        LabelerController controller = new LabelerController(plant);
        FinishingJob job = new FinishingJob(
                "FAULT-JOB", "WP-FAULT",
                LabelerController.MACHINE_ID,
                Operation.APPLY_LABEL,
                data(
                        "bottlePresent", "true",
                        "manufacturingComplete", "true",
                        "labelPayload", "TEST"));
        plant.failNextCycle("simulated label jam");
        List<MachineReport> reports = controller.execute(job, true);
        require(
                reports.size() == 2
                        && reports.get(0).getState() == MachineState.BUSY
                        && reports.get(1).getState() == MachineState.FAULT,
                "Fault cycle must report BUSY then FAULT");
        require(
                controller.getState() == MachineState.FAULT,
                "Controller fault must latch");
        expectFailure(
                () -> controller.resetFault(true),
                "Reset must fail while the plant fault remains latched");
        plant.clearFault();
        controller.resetFault(true);
        require(
                controller.getState() == MachineState.READY,
                "Cleared plant plus safety permit must enable reset");

        List<MachineReport> unsafe = controller.execute(job, false);
        require(
                unsafe.size() == 1
                        && unsafe.get(0).getState() == MachineState.FAULT,
                "Missing safety permit must reject the job as FAULT");
        controller.resetFault(true);

        LidLoaderController timeoutController =
                new LidLoaderController(new LidLoaderPlant());
        FinishingJob timeoutJob = new FinishingJob(
                "TIMEOUT-JOB", "WP-TIMEOUT",
                LidLoaderController.MACHINE_ID,
                Operation.PLACE_LID,
                data(
                        "bottlePresent", "true",
                        "filled", "true",
                        "lidAvailable", "true",
                        "timeoutTicks", "1"));
        List<MachineReport> timedOut =
                timeoutController.execute(timeoutJob, true);
        require(
                timedOut.size() == 2
                        && timedOut.get(0).getState() == MachineState.BUSY
                        && timedOut.get(1).getState() == MachineState.FAULT
                        && timedOut.get(1).getDetail().contains("timeout"),
                "A plant cycle beyond its configured deadline must fault");
        timeoutController.resetFault(true);

        CapperPlant preconditionPlant = new CapperPlant();
        CapperController preconditionController =
                new CapperController(preconditionPlant);
        FinishingJob invalidJob = new FinishingJob(
                "INVALID-JOB", "WP-INVALID",
                CapperController.MACHINE_ID,
                Operation.CAP_BOTTLE,
                data(
                        "bottlePresent", "true",
                        "lidPresent", "false"));
        List<MachineReport> rejected =
                preconditionController.execute(invalidJob, true);
        require(
                rejected.size() == 2
                        && rejected.get(1).getState() == MachineState.FAULT
                        && rejected.get(1).getDetail().contains("lidPresent"),
                "A false plant precondition must produce an explained FAULT");
        preconditionPlant.clearFault();
        preconditionController.resetFault(true);
    }

    private static void testVisualizationBoundary() throws Exception {
        AtomicReference<OperatorCommand> command =
                new AtomicReference<OperatorCommand>();
        AtomicReference<DashboardState> received =
                new AtomicReference<DashboardState>();
        VisualizationBridge bridge = new VisualizationBridge(command::set);
        bridge.addListener(received::set);

        Map<String, MachineState> machineStates =
                new LinkedHashMap<String, MachineState>();
        machineStates.put(
                LidLoaderController.MACHINE_ID,
                MachineState.READY);
        Map<Integer, String> occupancy =
                new LinkedHashMap<Integer, String>();
        occupancy.put(3, "WP-009");
        DashboardState state = new DashboardState(
                "ORDER-009", "PRODUCT-A", "BATCH-009", "20/80",
                10, 2, 0, 3, 5, true, "",
                "Rotary table waiting for CapperController DONE for WP-009",
                machineStates,
                occupancy,
                Collections.<WorkpieceSnapshot>emptyList());
        bridge.publish(state);
        require(
                received.get() == state,
                "GUI listener must receive the latest immutable snapshot");

        bridge.submitOperatorCommand(new OperatorCommand(
                OperatorCommandType.SAFE_STOP, "", ""));
        require(
                command.get().getType() == OperatorCommandType.SAFE_STOP,
                "GUI command must reach only the coordinator-facing sink");

        SwingUtilities.invokeAndWait(() -> {
            EabsDashboardPanel panel = new EabsDashboardPanel(bridge);
            panel.setSize(1180, 720);
            layoutRecursively(panel);
            BufferedImage preview =
                    new BufferedImage(1180, 720, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = preview.createGraphics();
            panel.printAll(graphics);
            graphics.dispose();
            try {
                ImageIO.write(
                        preview,
                        "png",
                        Paths.get("build", "dashboard-preview.png").toFile());
            } catch (IOException failure) {
                throw new IllegalStateException(
                        "Could not write dashboard preview", failure);
            }
        });
    }

    private static void layoutRecursively(Container container) {
        container.doLayout();
        for (java.awt.Component component : container.getComponents()) {
            if (component instanceof Container) {
                layoutRecursively((Container) component);
            }
        }
    }

    private static void runOwnedController(
            WorkpieceTracker tracker,
            AbstractFinishingController controller,
            FinishingJob job,
            Location location,
            int rotaryPosition,
            String locationEvidence) {
        tracker.dispatch(job);
        require(
                !tracker.findActive(job.getWorkpieceId()).get()
                        .getCompletedOperations().contains(job.getOperation()),
                "START must not mark an operation complete");

        List<MachineReport> reports = controller.execute(job, true);
        require(
                reports.size() == 2
                        && reports.get(0).getState() == MachineState.BUSY
                        && reports.get(1).getState() == MachineState.DONE,
                "Successful cycle must report BUSY then DONE");
        tracker.acceptReport(reports.get(0));

        FinishingJob stale = new FinishingJob(
                "STALE-" + job.getJobId(),
                job.getWorkpieceId(),
                job.getMachineId(),
                job.getOperation(),
                job.getOperationData());
        expectFailure(
                () -> tracker.acceptReport(MachineReport.done(
                        stale,
                        "stale result",
                        Collections.<String, String>emptyMap())),
                "Tracker must reject a stale completion");

        tracker.acceptReport(reports.get(1));
        tracker.confirmLocation(
                job.getWorkpieceId(),
                location,
                rotaryPosition,
                locationEvidence);
        require(
                controller.getState() == MachineState.DONE,
                "DONE must remain latched before acknowledgement");
        expectFailure(
                () -> controller.execute(job, true),
                "Latched DONE controller must reject another job");
        controller.acknowledgeDone(job.getJobId(), job.getWorkpieceId());
        require(
                controller.readinessReport().getState() == MachineState.READY,
                "Acknowledgement must return the controller to READY");
    }

    private static void confirmGeneric(
            WorkpieceTracker tracker,
            FinishingJob job,
            Location location,
            int rotaryPosition,
            String evidence) {
        tracker.dispatch(job);
        tracker.acceptReport(MachineReport.busy(job));
        tracker.acceptReport(MachineReport.done(
                job,
                "confirmed by integration harness",
                Collections.singletonMap("evidence", evidence)));
        tracker.confirmLocation(
                job.getWorkpieceId(),
                location,
                rotaryPosition,
                evidence);
    }

    private static FinishingJob job(
            String jobId,
            String machineId,
            Operation operation) {
        return job(
                jobId, machineId, operation,
                Collections.<String, String>emptyMap());
    }

    private static FinishingJob job(
            String jobId,
            String machineId,
            Operation operation,
            Map<String, String> data) {
        return new FinishingJob(
                jobId, "WP-001", machineId, operation, data);
    }

    private static Map<String, String> data(String... pairs) {
        Map<String, String> values = new LinkedHashMap<String, String>();
        for (int index = 0; index < pairs.length; index += 2) {
            values.put(pairs[index], pairs[index + 1]);
        }
        return values;
    }

    private static void expectFailure(
            ThrowingAction action,
            String message) {
        boolean failed = false;
        try {
            action.run();
        } catch (RuntimeException expected) {
            failed = true;
        }
        require(failed, message);
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private interface ThrowingAction {
        void run();
    }
}
