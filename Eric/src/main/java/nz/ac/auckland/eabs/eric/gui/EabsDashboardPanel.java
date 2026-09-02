package nz.ac.auckland.eabs.eric.gui;

import nz.ac.auckland.eabs.eric.model.MachineState;
import nz.ac.auckland.eabs.eric.tracking.WorkpieceSnapshot;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;

/** Live symbolic GP view. All buttons submit intent through the bridge. */
public final class EabsDashboardPanel extends JPanel
        implements VisualizationBridge.StateListener {
    private static final Color OFFLINE = new Color(155, 155, 155);
    private static final Color READY = new Color(76, 175, 80);
    private static final Color BUSY = new Color(255, 193, 7);
    private static final Color DONE = new Color(66, 133, 244);
    private static final Color FAULT = new Color(220, 53, 69);
    private static final Color HOLDING = new Color(255, 152, 0);

    private final VisualizationBridge bridge;
    private final JLabel orderSummary = new JLabel("No active order");
    private final JLabel safetySummary = new JLabel("UNSAFE");
    private final JLabel waitSummary = new JLabel("Waiting for coordinator");
    private final Map<String, JLabel> machineCards =
            new LinkedHashMap<String, JLabel>();
    private final Map<Integer, JLabel> rotaryCards =
            new LinkedHashMap<Integer, JLabel>();
    private final DefaultTableModel workpieceModel =
            new DefaultTableModel(
                    new Object[] {
                        "Workpiece", "Location", "State",
                        "Current", "Next", "Completed"
                    },
                    0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    public EabsDashboardPanel(VisualizationBridge bridge) {
        this.bridge = bridge;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(buildHeader(), BorderLayout.NORTH);
        add(buildCentre(), BorderLayout.CENTER);
        add(buildControls(), BorderLayout.SOUTH);
        bridge.addListener(this);
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout(10, 4));
        orderSummary.setFont(orderSummary.getFont().deriveFont(Font.BOLD, 15f));
        safetySummary.setOpaque(true);
        safetySummary.setHorizontalAlignment(SwingConstants.CENTER);
        safetySummary.setPreferredSize(new Dimension(220, 34));
        safetySummary.setForeground(Color.WHITE);
        safetySummary.setBackground(FAULT);
        waitSummary.setOpaque(true);
        waitSummary.setBackground(new Color(255, 243, 205));
        waitSummary.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        panel.add(orderSummary, BorderLayout.CENTER);
        panel.add(safetySummary, BorderLayout.EAST);
        panel.add(waitSummary, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildCentre() {
        JPanel centre = new JPanel(new BorderLayout(8, 8));
        centre.add(buildLineView(), BorderLayout.NORTH);

        JTable workpieceTable = new JTable(workpieceModel);
        workpieceTable.setFillsViewportHeight(true);
        workpieceTable.setRowHeight(24);
        JScrollPane tableScroll = new JScrollPane(workpieceTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder(
                "Bottle twins - confirmed state"));
        centre.add(tableScroll, BorderLayout.CENTER);
        centre.add(buildMachineView(), BorderLayout.SOUTH);
        return centre;
    }

    private JPanel buildLineView() {
        JPanel line = new JPanel(new GridLayout(1, 9, 6, 0));
        line.setBorder(BorderFactory.createTitledBorder(
                "Input conveyor -> six-position rotary table -> output path"));
        line.add(pathCard("Input"));
        for (int position = 1; position <= 6; position++) {
            JLabel card = pathCard("P" + position + "\nempty");
            rotaryCards.put(position, card);
            line.add(card);
        }
        line.add(pathCard("Labeler"));
        line.add(pathCard("Collection"));
        return line;
    }

    private JLabel pathCard(String text) {
        JLabel label = new JLabel(html(text), SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(238, 242, 247));
        label.setBorder(BorderFactory.createLineBorder(new Color(90, 100, 115)));
        label.setPreferredSize(new Dimension(90, 58));
        return label;
    }

    private JPanel buildMachineView() {
        JPanel machines = new JPanel(new GridLayout(2, 4, 6, 6));
        machines.setBorder(BorderFactory.createTitledBorder(
                "Controller states"));
        addMachine(machines, "BottleLoaderController");
        addMachine(machines, "ConveyorController");
        addMachine(machines, "RotaryTableController");
        addMachine(machines, "FillerController");
        addMachine(machines, "LidLoaderController");
        addMachine(machines, "CapperController");
        addMachine(machines, "LabelerController");
        addMachine(machines, "UnloaderController");
        return machines;
    }

    private void addMachine(JPanel panel, String machineId) {
        JLabel card = new JLabel(
                html(machineId + "\nOFFLINE"),
                SwingConstants.CENTER);
        card.setOpaque(true);
        card.setBackground(OFFLINE);
        card.setForeground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 4));
        machineCards.put(machineId, card);
        panel.add(card);
    }

    private JPanel buildControls() {
        JPanel controls = new JPanel(new GridLayout(2, 1, 0, 2));
        JPanel productionControls =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        JTextField orderField = new JTextField(10);
        orderField.setToolTipText("Order identifier");
        productionControls.add(new JLabel("Order:"));
        productionControls.add(orderField);
        productionControls.add(button(
                "Submit",
                OperatorCommandType.SUBMIT_ORDER,
                "",
                orderField));
        productionControls.add(button(
                "Start", OperatorCommandType.START, "", null));
        productionControls.add(button(
                "Pause admission",
                OperatorCommandType.PAUSE_ADMISSION,
                "",
                null));
        productionControls.add(button(
                "Safe stop",
                OperatorCommandType.SAFE_STOP,
                "",
                null));
        controls.add(productionControls);

        JPanel recoveryControls =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        recoveryControls.add(button(
                "Acknowledge hazard",
                OperatorCommandType.ACKNOWLEDGE_HAZARD,
                "",
                null));
        recoveryControls.add(button(
                "Safe reset",
                OperatorCommandType.SAFE_RESET,
                "",
                null));

        JComboBox<String> speed =
                new JComboBox<String>(new String[] {"0.5x", "1x", "2x", "5x"});
        speed.setSelectedItem("1x");
        speed.addActionListener(event -> bridge.submitOperatorCommand(
                new OperatorCommand(
                        OperatorCommandType.SET_SIMULATION_SPEED,
                        "",
                        String.valueOf(speed.getSelectedItem()))));
        recoveryControls.add(new JLabel("Speed:"));
        recoveryControls.add(speed);

        JComboBox<String> faultTarget = new JComboBox<String>(
                new String[] {
                    "LidLoaderController",
                    "CapperController",
                    "LabelerController",
                    "UnloaderController"
                });
        recoveryControls.add(faultTarget);
        JButton inject = new JButton("Inject test fault");
        inject.addActionListener(event -> bridge.submitOperatorCommand(
                new OperatorCommand(
                        OperatorCommandType.INJECT_SIMULATED_FAULT,
                        String.valueOf(faultTarget.getSelectedItem()),
                        "operator test")));
        recoveryControls.add(inject);
        controls.add(recoveryControls);
        return controls;
    }

    private JButton button(
            String text,
            OperatorCommandType type,
            String target,
            JTextField valueField) {
        JButton button = new JButton(text);
        button.addActionListener(event -> bridge.submitOperatorCommand(
                new OperatorCommand(
                        type,
                        target,
                        valueField == null ? "" : valueField.getText())));
        return button;
    }

    @Override
    public void onState(DashboardState state) {
        if (SwingUtilities.isEventDispatchThread()) {
            applyState(state);
        } else {
            SwingUtilities.invokeLater(() -> applyState(state));
        }
    }

    private void applyState(DashboardState state) {
        orderSummary.setText(
                "Order " + valueOrDash(state.getOrderId())
                        + " | Product " + valueOrDash(state.getProductId())
                        + " | Batch " + valueOrDash(state.getBatchId())
                        + " | Recipe " + valueOrDash(state.getRecipe())
                        + " | Req " + state.getRequested()
                        + " | In " + state.getInProcess()
                        + " | Done " + state.getCompleted()
                        + " | Reject " + state.getRejected()
                        + " | Rem " + state.getRemaining());

        safetySummary.setText(
                state.isSafetyPermit()
                        ? "SAFE - production permitted"
                        : "UNSAFE - " + valueOrDash(state.getHazardReason()));
        safetySummary.setBackground(state.isSafetyPermit() ? READY : FAULT);
        waitSummary.setText(
                "Diagnostic: " + valueOrDash(state.getWaitReason()));
        waitSummary.setBackground(
                state.getWaitReason().isEmpty()
                        ? new Color(226, 239, 218)
                        : new Color(255, 230, 153));

        for (int position = 1; position <= 6; position++) {
            String workpiece = state.getRotaryOccupancy().get(position);
            JLabel card = rotaryCards.get(position);
            card.setText(html(
                    "P" + position + "\n"
                            + (workpiece == null ? "empty" : workpiece)));
            card.setBackground(
                    workpiece == null
                            ? new Color(238, 242, 247)
                            : HOLDING);
        }

        for (Map.Entry<String, JLabel> entry : machineCards.entrySet()) {
            MachineState machineState =
                    state.getMachineStates().get(entry.getKey());
            if (machineState == null) {
                machineState = MachineState.OFFLINE;
            }
            entry.getValue().setText(
                    html(entry.getKey() + "\n" + machineState));
            entry.getValue().setBackground(machineColour(machineState));
        }

        workpieceModel.setRowCount(0);
        for (WorkpieceSnapshot workpiece : state.getWorkpieces()) {
            workpieceModel.addRow(new Object[] {
                workpiece.getWorkpieceId(),
                workpiece.getLocation()
                        + (workpiece.getRotaryPosition() == 0
                                ? ""
                                : " / P" + workpiece.getRotaryPosition()),
                workpiece.getStatus(),
                workpiece.getCurrentOperation(),
                workpiece.getNextOperation(),
                workpiece.getCompletedOperations()
            });
        }
    }

    private static Color machineColour(MachineState state) {
        if (state == MachineState.READY) {
            return READY;
        }
        if (state == MachineState.BUSY) {
            return BUSY;
        }
        if (state == MachineState.DONE) {
            return DONE;
        }
        if (state == MachineState.FAULT) {
            return FAULT;
        }
        return OFFLINE;
    }

    private static String html(String text) {
        return "<html><div style='text-align:center'>"
                + text.replace("\n", "<br>")
                + "</div></html>";
    }

    private static String valueOrDash(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }
}
