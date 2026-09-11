package nz.ac.auckland.eabs.eric;

import nz.ac.auckland.eabs.eric.gui.DashboardState;
import nz.ac.auckland.eabs.eric.gui.EabsDashboardPanel;
import nz.ac.auckland.eabs.eric.gui.OperatorCommand;
import nz.ac.auckland.eabs.eric.gui.OperatorCommandType;
import nz.ac.auckland.eabs.eric.gui.VisualizationBridge;
import nz.ac.auckland.eabs.eric.model.Location;
import nz.ac.auckland.eabs.eric.model.MachineState;
import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.model.WorkpieceStatus;
import nz.ac.auckland.eabs.eric.tracking.WorkpieceSnapshot;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/** Shared GP view tested without an IP dependency or a desktop window. */
public final class SharedDashboardTest {
    private SharedDashboardTest() { }

    public static void runAll() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            testReadOnlyView();
            testLegacyOperatorMode();
        });
        testBackgroundPublication();
        System.out.println("SHARED GP-IP DASHBOARD TESTS PASSED");
    }

    private static void testReadOnlyView() {
        AtomicInteger sent = new AtomicInteger();
        VisualizationBridge bridge = new VisualizationBridge(c -> sent.incrementAndGet());
        EabsDashboardPanel panel = new EabsDashboardPanel(bridge, EabsDashboardPanel.Mode.READ_ONLY);
        require(panel.getMode() == EabsDashboardPanel.Mode.READ_ONLY, "Explicit read-only mode");
        require(named(panel, "operatorControls") == null, "Read-only must create no operator controls");
        require(named(panel, "readOnlyNotice") instanceof JLabel, "Read-only notice is visible");
        Container machines = (Container) named(panel, "machineStates");
        require(machines.getComponentCount() == 8, "GP-only baseline keeps eight machines");
        require(label(panel, "FillerController").contains("NO DATA"), "Missing feedback is not OFFLINE");
        require(named(panel, "machine:BackupFillerController") == null, "No IP machine dependency at startup");

        Map<String, MachineState> states = new LinkedHashMap<String, MachineState>();
        states.put("FillerController", MachineState.FAULT);
        states.put("BackupFillerController", MachineState.BUSY);
        states.put("ConveyorController", MachineState.OFFLINE);
        states.put("LidLoaderController", MachineState.READY);
        Map<String, String> amounts = new LinkedHashMap<String, String>();
        amounts.put("liquidA", "0");
        amounts.put("liquidB", "65.0000");
        WorkpieceSnapshot confirmed = bottle("WP-CONFIRMED", amounts);
        WorkpieceSnapshot missing = bottle("WP-UNKNOWN", null);
        bridge.publish(state(states, Arrays.asList(confirmed, missing)));
        require(machines.getComponentCount() == 9, "Additional machine card appears");
        require(label(panel, "BackupFillerController").contains("BUSY"), "Backup state is shown");
        require(label(panel, "ConveyorController").contains("OFFLINE"), "Explicit OFFLINE retained");
        JTable table = (JTable) named(panel, "workpieces");
        require(table.getRowCount() == 2 && table.getColumnCount() == 8, "All bottles and amount columns shown");
        require("Confirmed A (raw)".equals(table.getColumnName(6)), "No inferred mL unit");
        require("0".equals(table.getValueAt(0, 6)), "Confirmed zero is not missing");
        require("65.0000".equals(table.getValueAt(0, 7)), "Raw precision preserved");
        require("Not available".equals(table.getValueAt(1, 6)), "Missing amount is not zero");
        require(!table.isCellEditable(0, 6), "Amounts are not editable");
        render(panel, "shared-dashboard-readonly-preview.png");
        require(table.getTableHeader().getHeight() > 0, "Amount column labels are visible in the preview");

        states.put("BackupFillerController", MachineState.FAULT);
        bridge.publish(state(states, Collections.singletonList(missing)));
        require(machines.getComponentCount() == 9, "Updates do not duplicate machine cards");
        require(label(panel, "BackupFillerController").contains("FAULT"), "Backup updates in place");
        require(table.getRowCount() == 1 && "Not available".equals(table.getValueAt(0, 7)),
                "Replacing the state cannot leave old measurements on another bottle");
        states.remove("BackupFillerController");
        bridge.publish(state(states, Collections.emptyList()));
        require(machines.getComponentCount() == 8, "Disabled extension disappears on the next complete snapshot");
        require(named(panel, "machine:BackupFillerController") == null, "No stale backup state");
        require(table.getRowCount() == 0, "Empty snapshot clears rows");

        states.put("Test<Station>&", MachineState.READY);
        bridge.publish(state(states, Collections.emptyList()));
        require(label(panel, "Test<Station>&").contains("Test&lt;Station&gt;&amp;"),
                "Machine identifiers render as data, not HTML");
        require(sent.get() == 0, "Rendering/read-only mode must never emit commands");
    }

    private static void testLegacyOperatorMode() {
        List<OperatorCommand> sent = new ArrayList<OperatorCommand>();
        VisualizationBridge bridge = new VisualizationBridge(sent::add);
        EabsDashboardPanel panel = new EabsDashboardPanel(bridge);
        require(panel.getMode() == EabsDashboardPanel.Mode.OPERATOR, "Old constructor remains compatible");
        require(named(panel, "operatorControls") != null, "Existing operator controls remain");
        require(sent.isEmpty(), "Construction must not send a speed/start command");
        button(panel, "Start").doClick();
        button(panel, "Pause admission").doClick();
        button(panel, "Safe stop").doClick();
        require(sent.size() == 3, "One command per click");
        require(sent.get(0).getType() == OperatorCommandType.START, "Start command preserved");
        require(sent.get(1).getType() == OperatorCommandType.PAUSE_ADMISSION,
                "Pause admission must not be changed to simulation pause");
        require(sent.get(2).getType() == OperatorCommandType.SAFE_STOP, "Safe-stop intent preserved");
        bridge.publish(DashboardState.empty());
        require(sent.size() == 3, "State refresh does not issue commands");
        render(panel, "shared-dashboard-operator-preview.png");
    }

    private static void testBackgroundPublication() throws Exception {
        VisualizationBridge bridge = new VisualizationBridge(c -> {
            throw new AssertionError("Read-only update emitted command");
        });
        EabsDashboardPanel[] panel = new EabsDashboardPanel[1];
        SwingUtilities.invokeAndWait(() -> panel[0] = new EabsDashboardPanel(
                bridge, EabsDashboardPanel.Mode.READ_ONLY));
        // This call is off the Swing event thread, as a SystemJ publisher would be.
        bridge.publish(state(Collections.singletonMap("BackupFillerController", MachineState.DONE),
                Collections.singletonList(bottle("BACKGROUND", null))));
        SwingUtilities.invokeAndWait(() -> {
            require(label(panel[0], "BackupFillerController").contains("DONE"), "Off-EDT publication reaches UI");
            require("BACKGROUND".equals(((JTable) named(panel[0], "workpieces")).getValueAt(0, 0)),
                    "Bottle update applied on event thread");
        });
    }

    private static DashboardState state(Map<String, MachineState> machines, List<WorkpieceSnapshot> bottles) {
        Map<Integer, String> occupancy = new LinkedHashMap<Integer, String>();
        for (WorkpieceSnapshot bottle : bottles) {
            occupancy.put(bottle.getRotaryPosition(), bottle.getWorkpieceId());
        }
        return new DashboardState("ORDER-001", "PRODUCT-A", "BATCH-001", "A20/B80",
                3, 0, 0, bottles.size(), 3 - bottles.size(), true, "",
                "Recovery preview: waiting for backup completion; fixtures, not a live GP order",
                machines, occupancy, bottles);
    }

    /** Presentation fixtures only: tracker evidence rules are tested separately. */
    private static WorkpieceSnapshot bottle(String id, Map<String, String> amounts) {
        boolean unknown = "WP-UNKNOWN".equals(id);
        return new WorkpieceSnapshot(id, "ORDER-001", "BATCH-001", "PRODUCT-A", "A20/B80",
                unknown ? Location.ROTARY_P5_EXIT : Location.ROTARY_P6, unknown ? 5 : 6,
                null, Operation.PLACE_LID, WorkpieceStatus.WAITING,
                Arrays.asList(Operation.FILL_TWO_LIQUIDS, Operation.PLACE_LID),
                Collections.singletonList(Operation.FILL_TWO_LIQUIDS),
                Collections.singletonList("BackupFillerController"),
                new EnumMap<Operation, String>(Operation.class),
                Collections.emptyList(), Collections.emptyList(), "", "", amounts);
    }

    private static Component named(Component root, String name) {
        if (name.equals(root.getName())) { return root; }
        if (root instanceof Container) {
            for (Component child : ((Container) root).getComponents()) {
                Component found = named(child, name);
                if (found != null) { return found; }
            }
        }
        return null;
    }

    private static String label(Container panel, String machine) {
        Component found = named(panel, "machine:" + machine);
        require(found instanceof JLabel, "Machine label missing: " + machine);
        return ((JLabel) found).getText();
    }

    private static JButton button(Container root, String text) {
        for (Component child : root.getComponents()) {
            if (child instanceof JButton && text.equals(((JButton) child).getText())) {
                return (JButton) child;
            }
            if (child instanceof Container) {
                JButton found = button((Container) child, text);
                if (found != null) { return found; }
            }
        }
        return null;
    }

    private static void render(EabsDashboardPanel panel, String filename) {
        panel.setSize(1320, 780);
        layout(panel);
        BufferedImage image = new BufferedImage(1320, 780, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        panel.printAll(graphics);
        graphics.dispose();
        try {
            Files.createDirectories(Paths.get("build"));
            ImageIO.write(image, "png", Paths.get("build", filename).toFile());
        } catch (IOException failure) { throw new IllegalStateException(failure); }
    }

    private static void layout(Container parent) {
        parent.doLayout();
        for (Component child : parent.getComponents()) {
            if (child instanceof Container) { layout((Container) child); }
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) { throw new AssertionError(message); }
    }
}
