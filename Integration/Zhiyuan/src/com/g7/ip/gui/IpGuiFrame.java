package com.g7.ip.gui;

import com.g7.ip.Dao;
import com.g7.ip.DeviationDetector;
import nz.ac.auckland.eabs.zhiyuan.coordinator.IpBatchManagerModel;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * One Swing GUI for everything on the IP/POS side of this project -- Place Order, Track
 * Order, Track Bottle (the digital twin) and Faults History all live here as tabs of the
 * same window, sharing the one IpBatchManagerModel (and therefore the one Dao/JDBC
 * connection/database file) this frame is constructed with. Eric's own EabsDashboardPanel is
 * a deliberately separate, untouched window -- this frame never reaches into it and is never
 * reached from it. Same in-process, direct-model-reference construction as that dashboard,
 * and the same plain Metal chrome (no custom Look &amp; Feel anywhere in this project).
 *
 * Previously four tabs were two separate frames (PosGuiFrame, FactoryGuiFrame); they have
 * been merged into this one on request, since they were always going to share one database
 * and one running process anyway.
 */
public final class IpGuiFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final Color READY = new Color(76, 175, 80);
    private static final Color DONE_COLOR = new Color(66, 133, 244);
    private static final Color FAULT = new Color(220, 53, 69);
    private static final Color HOLDING = new Color(255, 152, 0);
    private static final Color OK_BG = new Color(226, 239, 218);
    private static final Color OPEN_BG = new Color(253, 235, 235);
    private static final Color RESOLVED_BG = new Color(232, 245, 233);
    private static final Color PENDING_BG = new Color(238, 242, 247);
    private static final Color PENDING_BORDER = new Color(90, 100, 115);

    private static final int POLL_INTERVAL_MS = 2000;
    private static final String[] BOTTLE_CAPACITIES = {"250ml", "500ml", "1L"};
    private static final Pattern DERIVED_PRODUCT = Pattern.compile("^FORM-(.+)-(\\d+)-(\\d+)$");
    private static final Map<String, String> STATION_NAMES = buildStationNames();

    private static Map<String, String> buildStationNames() {
        Map<String, String> m = new HashMap<>();
        m.put("loader", "Loader");
        m.put("conveyor_in", "Conveyor (in)");
        m.put("filler", "Filler");
        m.put("lid", "Lid Loader");
        m.put("capper", "Capper");
        m.put("conveyor_out", "Conveyor (out)");
        m.put("labeller", "Labeller");
        m.put("unloader", "Unloader");
        return m;
    }

    private final IpBatchManagerModel model;
    private final AtomicInteger poSequence = new AtomicInteger(1);

    // ---- Place Order tab ----
    private static final int COL_NUM = 0;
    private static final int COL_CAPACITY = 1;
    private static final int COL_DOSE_A = 2;
    private static final int COL_DOSE_B = 3;
    private static final int COL_QTY = 4;
    private static final int COL_FORMULATION = 5;
    private final JTextField customerField = new JTextField("UoA CS704 G7", 24);
    private final DefaultTableModel lineModel = new DefaultTableModel(
            new Object[] {"#", "Bottle capacity", "Liquid A %", "Liquid B %", "Quantity", "Formulation"}, 0) {
        private static final long serialVersionUID = 1L;
        @Override public boolean isCellEditable(int row, int column) {
            return column != COL_NUM && column != COL_FORMULATION;
        }
    };
    private final JTable lineTable = new JTable(lineModel);
    private final JButton addLineButton = new JButton("Add line");
    private final JButton removeLineButton = new JButton("Remove selected line");
    private final JButton submitButton = new JButton("Submit order lines");
    private final JLabel placeOrderFooter = new JLabel("Loading PO sequence...");

    // ---- Track Order tab ----
    private final DefaultComboBoxModel<String> poHistoryModel = new DefaultComboBoxModel<>();
    private final JComboBox<String> poCombo = new JComboBox<>(poHistoryModel);
    private final JLabel orderSummaryLabel = new JLabel(" ");
    private final JLabel orderStatusPill = new JLabel("", SwingConstants.CENTER);
    private final JLabel orderProgressText = new JLabel(" ");
    private final JProgressBar orderProgressBar = new JProgressBar(0, 100);
    private final JLabel orderDiagLabel = new JLabel(" ");
    private String lastTrackedPo = null;
    private boolean trackedOrderSettled = false;

    // ---- Track Bottle tab ----
    private final DefaultComboBoxModel<String> bottleHistoryModel = new DefaultComboBoxModel<>();
    private final JComboBox<String> bottleCombo = new JComboBox<>(bottleHistoryModel);
    private final JLabel bottleSummaryLabel = new JLabel(" ");
    private final JLabel deviationBadge = new JLabel("", SwingConstants.CENTER);
    private final JLabel deviationReason = new JLabel(" ");
    private final JPanel timelinePanel = new JPanel();
    private String lastLookedUpBottle = null;
    private boolean journeySettled = false;

    // ---- Faults History tab ----
    private final DefaultTableModel faultsModel = new DefaultTableModel(
            new Object[] {"ID", "Bottle", "Batch", "Device", "Type", "Reason", "Reported", "Status", "Resolution"}, 0) {
        private static final long serialVersionUID = 1L;
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable faultsTable = new JTable(faultsModel);
    private final List<Dao.FaultRow> currentFaults = new ArrayList<>();
    private final JLabel faultsSummary = new JLabel(" ");
    private final JButton resolveFaultButton = new JButton("Resolve selected fault");

    public IpGuiFrame(IpBatchManagerModel model) {
        super("COMPSYS 704 - EABS Group 7 - IP Control Centre");
        this.model = model;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Place Order", buildPlaceOrderTab());
        tabs.addTab("Track Order", buildTrackOrderTab());
        tabs.addTab("Track Bottle", buildTrackBottleTab());
        tabs.addTab("Faults History", buildFaultsHistoryTab());

        JPanel content = new JPanel(new BorderLayout());
        content.add(tabs, BorderLayout.CENTER);
        content.add(buildSafetyStrip(), BorderLayout.SOUTH);
        setContentPane(content);

        addLine();
        setMinimumSize(new Dimension(1180, 810));
        pack();
        setLocationRelativeTo(null);

        refreshRecentBottles();
        refreshFaults();
        refreshSafetyStrip();

        // Seed the PO counter from whatever this database already has under today's prefix --
        // see IpBatchManagerModel.highestPoSequence for why this must not simply start at 1.
        submitButton.setEnabled(false);
        model.highestPoSequence(currentYearPrefix(), highest -> SwingUtilities.invokeLater(() -> {
            poSequence.set(highest + 1);
            submitButton.setEnabled(true);
            placeOrderFooter.setText("Ready -- connected to POS database.");
        }));

        Timer pollTimer = new Timer(POLL_INTERVAL_MS, event -> {
            if (lastTrackedPo != null && !trackedOrderSettled) {
                model.lookupOrder(lastTrackedPo, status -> SwingUtilities.invokeLater(() -> applyOrderTrackResult(lastTrackedPo, status)));
            }
            if (lastLookedUpBottle != null && !journeySettled) {
                model.lookupBottleHistory(lastLookedUpBottle, (history, deviation) ->
                        SwingUtilities.invokeLater(() -> applyBottleResult(lastLookedUpBottle, history, deviation)));
            }
            refreshFaults();
            refreshSafetyStrip();
        });
        pollTimer.start();
    }

    // ================================================================== Safety strip

    private final JLabel safetyPill = new JLabel("", SwingConstants.CENTER);
    private final JButton safetyResetButton = new JButton("Reset / Resume Production");

    /** Persistent bar under the tabs (not its own tab) -- a hazard can happen while the
     * operator is looking at any of the four tabs, so this stays visible regardless of which
     * one is selected, the same reasoning as Eric's own dashboard keeping its safety summary
     * in a fixed header rather than behind a tab. */
    private JPanel buildSafetyStrip() {
        JPanel strip = new JPanel(new BorderLayout(10, 0));
        strip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        safetyPill.setOpaque(true);
        safetyPill.setForeground(Color.WHITE);
        safetyPill.setPreferredSize(new Dimension(240, 26));
        safetyPill.setFont(safetyPill.getFont().deriveFont(Font.BOLD, 11.5f));
        strip.add(safetyPill, BorderLayout.WEST);
        safetyResetButton.addActionListener(event -> {
            boolean wired = model.triggerSafetyReset();
            if (!wired) {
                JOptionPane.showMessageDialog(this,
                        "No Safety Monitor is wired into this profile's coordinator_*.xml.",
                        "Not available", JOptionPane.INFORMATION_MESSAGE);
            }
            refreshSafetyStrip();
        });
        strip.add(safetyResetButton, BorderLayout.EAST);
        return strip;
    }

    private void refreshSafetyStrip() {
        boolean unsafe = model.isSafetyHazardActive();
        boolean halted = model.isHalted();
        if (unsafe) {
            safetyPill.setText("HAZARD ACTIVE");
            safetyPill.setBackground(FAULT);
        } else if (halted) {
            safetyPill.setText("ON HOLD -- RESET REQUIRED");
            safetyPill.setBackground(HOLDING);
        } else {
            safetyPill.setText("SAFE");
            safetyPill.setBackground(READY);
        }
    }

    // ================================================================== Place Order

    private JPanel buildPlaceOrderTab() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer details"));
        customerPanel.add(new JLabel("Company / customer ID:"));
        customerPanel.add(customerField);
        root.add(customerPanel, BorderLayout.NORTH);

        JPanel linesPanel = new JPanel(new BorderLayout(0, 6));
        linesPanel.setBorder(BorderFactory.createTitledBorder("Order lines"));
        lineTable.setRowHeight(24);
        lineTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] widths = {32, 120, 80, 80, 80, 420};
        for (int c = 0; c < widths.length; c++) {
            lineTable.getColumnModel().getColumn(c).setPreferredWidth(widths[c]);
        }
        JComboBox<String> capacityEditor = new JComboBox<>(BOTTLE_CAPACITIES);
        lineTable.getColumnModel().getColumn(COL_CAPACITY).setCellEditor(new DefaultCellEditor(capacityEditor));
        linesPanel.add(new JScrollPane(lineTable), BorderLayout.CENTER);

        JPanel lineButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        addLineButton.addActionListener(event -> addLine());
        removeLineButton.addActionListener(event -> removeSelectedLine());
        lineButtons.add(addLineButton);
        lineButtons.add(removeLineButton);
        linesPanel.add(lineButtons, BorderLayout.SOUTH);
        root.add(linesPanel, BorderLayout.CENTER);

        JPanel submitRow = new JPanel(new BorderLayout());
        JLabel note = new JLabel("Each line is checked against the formulation catalog and submitted as its own purchase order.");
        note.setFont(note.getFont().deriveFont(Font.PLAIN, 11f));
        submitButton.setFont(submitButton.getFont().deriveFont(Font.BOLD));
        submitButton.addActionListener(event -> submitLines());
        submitRow.add(note, BorderLayout.WEST);
        submitRow.add(submitButton, BorderLayout.EAST);

        JPanel south = new JPanel(new BorderLayout(0, 4));
        south.add(submitRow, BorderLayout.NORTH);
        placeOrderFooter.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        placeOrderFooter.setOpaque(true);
        placeOrderFooter.setBackground(OK_BG);
        south.add(placeOrderFooter, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);
        return root;
    }

    private void addLine() {
        int n = lineModel.getRowCount() + 1;
        lineModel.addRow(new Object[] {n, "500ml", 60, 40, 1, ""});
    }

    private void removeSelectedLine() {
        int row = lineTable.getSelectedRow();
        if (row < 0) { return; }
        lineModel.removeRow(row);
        for (int i = 0; i < lineModel.getRowCount(); i++) {
            lineModel.setValueAt(i + 1, i, COL_NUM);
        }
    }

    private void submitLines() {
        String customerId = customerField.getText().trim();
        if (customerId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a company / customer ID first.", "Missing details", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int rowCount = lineModel.getRowCount();
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Add at least one order line first.", "Nothing to submit", JOptionPane.WARNING_MESSAGE);
            return;
        }

        final int[] doseA = new int[rowCount];
        final int[] doseB = new int[rowCount];
        final int[] qty = new int[rowCount];
        final String[] capacity = new String[rowCount];
        for (int row = 0; row < rowCount; row++) {
            try {
                capacity[row] = String.valueOf(lineModel.getValueAt(row, COL_CAPACITY)).trim();
                doseA[row] = Integer.parseInt(String.valueOf(lineModel.getValueAt(row, COL_DOSE_A)).trim());
                doseB[row] = Integer.parseInt(String.valueOf(lineModel.getValueAt(row, COL_DOSE_B)).trim());
                qty[row] = Integer.parseInt(String.valueOf(lineModel.getValueAt(row, COL_QTY)).trim());
                if (capacity[row].isEmpty()) { throw new NumberFormatException("empty capacity"); }
            } catch (NumberFormatException invalid) {
                JOptionPane.showMessageDialog(this,
                        "Line " + (row + 1) + " has an invalid value. Capacity must be text; A%/B%/Quantity must be whole numbers.",
                        "Fix line " + (row + 1), JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        setLinesEnabled(false);
        placeOrderFooter.setBackground(new Color(255, 243, 205));
        placeOrderFooter.setText("Submitting " + rowCount + " order line(s)...");
        final int[] remaining = {rowCount};

        for (int row = 0; row < rowCount; row++) {
            final int fRow = row;
            final String poReference = nextPoReference();
            model.submitCartLine(poReference, customerId, capacity[row], doseA[row], doseB[row], qty[row],
                    (accepted, newFormulation, message) -> SwingUtilities.invokeLater(() -> {
                        String text = accepted
                                ? (newFormulation ? "New formulation -- " + poReference : "Existing formulation -- " + poReference)
                                : message;
                        if (fRow < lineModel.getRowCount()) {
                            lineModel.setValueAt(text, fRow, COL_FORMULATION);
                        }
                        if (accepted) {
                            addPoToHistory(poReference);
                        }
                        remaining[0]--;
                        if (remaining[0] == 0) {
                            setLinesEnabled(true);
                            placeOrderFooter.setBackground(OK_BG);
                            placeOrderFooter.setText("Submitted " + rowCount + " order line(s).");
                        }
                    }));
        }
    }

    private void addPoToHistory(String poReference) {
        if (poHistoryModel.getIndexOf(poReference) < 0) {
            poHistoryModel.insertElementAt(poReference, 0);
        }
    }

    private void setLinesEnabled(boolean enabled) {
        addLineButton.setEnabled(enabled);
        removeLineButton.setEnabled(enabled);
        submitButton.setEnabled(enabled);
        lineTable.setEnabled(enabled);
        customerField.setEnabled(enabled);
    }

    // ================================================================== Track Order

    private JPanel buildTrackOrderTab() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel findPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        findPanel.setBorder(BorderFactory.createTitledBorder("Find order"));
        findPanel.add(new JLabel("PO reference:"));
        poCombo.setEditable(true);
        poCombo.setPreferredSize(new Dimension(200, poCombo.getPreferredSize().height));
        findPanel.add(poCombo);
        JButton trackButton = new JButton("Track");
        trackButton.addActionListener(event -> trackOrder());
        findPanel.add(trackButton);
        root.add(findPanel, BorderLayout.NORTH);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Order status"));

        orderSummaryLabel.setFont(orderSummaryLabel.getFont().deriveFont(Font.BOLD, 14f));
        orderSummaryLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        orderSummaryLabel.setBorder(BorderFactory.createEmptyBorder(4, 2, 10, 2));

        JPanel statusRow = new JPanel(new BorderLayout(16, 0));
        statusRow.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        orderStatusPill.setOpaque(true);
        orderStatusPill.setForeground(Color.WHITE);
        orderStatusPill.setBackground(HOLDING);
        orderStatusPill.setPreferredSize(new Dimension(220, 34));
        orderStatusPill.setFont(orderStatusPill.getFont().deriveFont(Font.BOLD, 12f));
        statusRow.add(orderStatusPill, BorderLayout.WEST);

        JPanel progressPanel = new JPanel(new BorderLayout(0, 3));
        orderProgressText.setFont(orderProgressText.getFont().deriveFont(Font.PLAIN, 11.5f));
        progressPanel.add(orderProgressText, BorderLayout.NORTH);
        progressPanel.add(orderProgressBar, BorderLayout.SOUTH);
        statusRow.add(progressPanel, BorderLayout.CENTER);

        orderDiagLabel.setOpaque(true);
        orderDiagLabel.setBackground(OK_BG);
        orderDiagLabel.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        orderDiagLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        orderDiagLabel.setFont(orderDiagLabel.getFont().deriveFont(Font.PLAIN, 11.5f));

        statusPanel.add(orderSummaryLabel);
        statusPanel.add(statusRow);
        statusPanel.add(Box.createVerticalStrut(10));
        statusPanel.add(orderDiagLabel);
        clearOrderTrackResult();
        root.add(statusPanel, BorderLayout.CENTER);
        return root;
    }

    private void trackOrder() {
        Object selected = poCombo.getEditor().getItem();
        String po = selected == null ? "" : String.valueOf(selected).trim();
        if (po.isEmpty()) { return; }
        lastTrackedPo = po;
        trackedOrderSettled = false;
        model.lookupOrder(po, status -> SwingUtilities.invokeLater(() -> applyOrderTrackResult(po, status)));
    }

    private void clearOrderTrackResult() {
        orderSummaryLabel.setText("Enter a PO reference and click Track.");
        orderStatusPill.setText("");
        orderStatusPill.setBackground(Color.LIGHT_GRAY);
        orderProgressText.setText(" ");
        orderProgressBar.setValue(0);
        orderProgressBar.setStringPainted(false);
        orderDiagLabel.setText(" ");
    }

    private void applyOrderTrackResult(String po, Dao.OrderStatus status) {
        if (!po.equals(lastTrackedPo)) { return; } // a newer lookup superseded this one
        if (status == null) {
            orderSummaryLabel.setText("No order found for PO " + po + ".");
            orderStatusPill.setText("NOT FOUND");
            orderStatusPill.setBackground(FAULT);
            orderProgressText.setText(" ");
            orderProgressBar.setValue(0);
            orderProgressBar.setStringPainted(false);
            orderDiagLabel.setText("Check the reference and try again.");
            trackedOrderSettled = true;
            return;
        }

        String formulation = formatFormulation(status.productId);
        orderSummaryLabel.setText("PO " + status.customerPo + " | Customer " + status.customerId
                + " | " + formulation
                + " | Batch " + (status.batchId == null ? "-" : ("#" + status.batchId))
                + " | Requested " + status.quantity
                + " | Completed " + status.completedInBatch);

        // Orders.status never reaches 'COMPLETED' in the schema (only PENDING/ADMITTED are
        // ever written) -- completion is derived from the order's own bottle count instead,
        // which is the authoritative signal and also the one the progress bar already uses.
        boolean done = status.quantity > 0 && status.completedInBatch >= status.quantity;
        boolean batchFaulted = "FAULT".equals(status.batchStatus);

        if (done) {
            orderStatusPill.setText("COMPLETED");
            orderStatusPill.setBackground(READY);
        } else if (batchFaulted) {
            orderStatusPill.setText("BATCH FAULT");
            orderStatusPill.setBackground(FAULT);
        } else if ("ADMITTED".equals(status.status)) {
            orderStatusPill.setText("ADMITTED - IN PRODUCTION");
            orderStatusPill.setBackground(DONE_COLOR);
        } else {
            orderStatusPill.setText("PENDING");
            orderStatusPill.setBackground(HOLDING);
        }
        trackedOrderSettled = done || batchFaulted;

        int percent = status.quantity <= 0 ? 0
                : Math.min(100, (int) Math.round(100.0 * status.completedInBatch / status.quantity));
        orderProgressBar.setValue(percent);
        orderProgressBar.setStringPainted(true);
        orderProgressText.setText(status.completedInBatch + " / " + status.quantity + " bottles completed");

        if (done) {
            orderDiagLabel.setText("All " + status.quantity + " bottles for this order have completed.");
        } else if (status.batchId == null) {
            orderDiagLabel.setText("Not yet admitted into a batch -- waiting for enough pending demand of the same formulation.");
        } else if (batchFaulted) {
            orderDiagLabel.setText("Batch #" + status.batchId + " ended in FAULT.");
        } else if ("RUNNING".equals(status.batchStatus)) {
            orderDiagLabel.setText("Batch #" + status.batchId + " is currently RUNNING on the production line.");
        } else {
            orderDiagLabel.setText("Batch #" + status.batchId + " status: " + status.batchStatus);
        }
    }

    /** productId is customer-invisible plumbing (see IpBatchManagerModel.deriveProductId) --
     * this reverses it back into the capacity/mix the customer actually asked for, falling
     * back to the raw id for orders that came in through the older console/product-id path. */
    private static String formatFormulation(String productId) {
        if (productId == null) { return "Formulation -"; }
        Matcher m = DERIVED_PRODUCT.matcher(productId);
        if (m.matches()) {
            return "Capacity " + m.group(1) + " | Mix " + m.group(2) + "/" + m.group(3);
        }
        return "Product " + productId;
    }

    // ================================================================== Track Bottle

    private JPanel buildTrackBottleTab() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel findPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        findPanel.setBorder(BorderFactory.createTitledBorder("Find bottle"));
        findPanel.add(new JLabel("Bottle ID:"));
        bottleCombo.setEditable(true);
        bottleCombo.setPreferredSize(new Dimension(200, bottleCombo.getPreferredSize().height));
        findPanel.add(bottleCombo);
        JButton lookupButton = new JButton("Look up");
        lookupButton.addActionListener(event -> lookupBottle());
        findPanel.add(lookupButton);
        root.add(findPanel, BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout(0, 10));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createTitledBorder("Bottle"));
        bottleSummaryLabel.setFont(bottleSummaryLabel.getFont().deriveFont(Font.BOLD, 13f));
        bottleSummaryLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        JPanel devRow = new JPanel(new BorderLayout(12, 0));
        devRow.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        deviationBadge.setOpaque(true);
        deviationBadge.setForeground(Color.WHITE);
        deviationBadge.setBackground(Color.LIGHT_GRAY);
        deviationBadge.setPreferredSize(new Dimension(170, 30));
        deviationBadge.setFont(deviationBadge.getFont().deriveFont(Font.BOLD, 12f));
        devRow.add(deviationBadge, BorderLayout.WEST);
        deviationReason.setFont(deviationReason.getFont().deriveFont(Font.PLAIN, 11.5f));
        devRow.add(deviationReason, BorderLayout.CENTER);

        headerPanel.add(bottleSummaryLabel);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(devRow);
        centre.add(headerPanel, BorderLayout.NORTH);

        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));
        timelinePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        JScrollPane timelineScroll = new JScrollPane(timelinePanel);
        timelineScroll.setBorder(BorderFactory.createTitledBorder("Production journey"));
        timelineScroll.getVerticalScrollBar().setUnitIncrement(16);
        centre.add(timelineScroll, BorderLayout.CENTER);

        root.add(centre, BorderLayout.CENTER);
        renderPendingRoute();
        clearBottleHeader();
        return root;
    }

    private void refreshRecentBottles() {
        model.recentBottleIds(25, ids -> SwingUtilities.invokeLater(() -> {
            String current = bottleHistoryModel.getSize() > 0 ? (String) bottleHistoryModel.getSelectedItem() : null;
            bottleHistoryModel.removeAllElements();
            for (String id : ids) { bottleHistoryModel.addElement(id); }
            if (current != null) { bottleHistoryModel.setSelectedItem(current); }
        }));
    }

    private void lookupBottle() {
        Object selected = bottleCombo.getEditor().getItem();
        String bottleId = selected == null ? "" : String.valueOf(selected).trim();
        if (bottleId.isEmpty()) { return; }
        lastLookedUpBottle = bottleId;
        journeySettled = false;
        model.lookupBottleHistory(bottleId, (history, deviation) ->
                SwingUtilities.invokeLater(() -> applyBottleResult(bottleId, history, deviation)));
    }

    private void clearBottleHeader() {
        bottleSummaryLabel.setText("Enter a bottle ID (e.g. B-7-0003) and click Look up.");
        deviationBadge.setText("");
        deviationBadge.setBackground(Color.LIGHT_GRAY);
        deviationReason.setText(" ");
    }

    /** Draws every expected station as PENDING -- the resting state before any bottle has
     * been looked up, and also what an in-progress bottle's not-yet-reached stations show. */
    private void renderPendingRoute() {
        timelinePanel.removeAll();
        for (String station : IpBatchManagerModel.EXPECTED_STATIONS) {
            timelinePanel.add(stationRow(friendlyName(station), "PENDING", null, PENDING_BG, PENDING_BORDER, Color.BLACK));
        }
        timelinePanel.revalidate();
        timelinePanel.repaint();
    }

    private void applyBottleResult(String bottleId, Dao.BottleHistory history, DeviationDetector.Result deviation) {
        if (!bottleId.equals(lastLookedUpBottle)) { return; } // superseded by a newer lookup

        if (history == null) {
            bottleSummaryLabel.setText("No history found for bottle " + bottleId + ".");
            deviationBadge.setText("NOT FOUND");
            deviationBadge.setBackground(FAULT);
            deviationReason.setText("Check the ID and try again -- it is minted the moment a bottle is admitted at the loader.");
            renderPendingRoute();
            journeySettled = true;
            return;
        }

        refreshRecentBottles();

        bottleSummaryLabel.setText("Bottle " + history.bottleId
                + "  |  Workpiece " + history.workpieceId
                + "  |  Batch #" + history.batchId
                + "  |  Order " + (history.orderId == null ? "-" : ("#" + history.orderId))
                + "  |  " + formatFormulation(history.productId));

        if (deviation != null && deviation.deviated) {
            deviationBadge.setText("DEVIATED");
            deviationBadge.setBackground(FAULT);
        } else {
            deviationBadge.setText("NO DEVIATION");
            deviationBadge.setBackground(READY);
        }
        deviationReason.setText(deviation == null ? " " : deviation.reason);

        // Map recorded events onto the full expected route, in order -- a station with no
        // recorded row yet is still shown, as PENDING, so the operator can see exactly how
        // far through the line this specific bottle has got.
        timelinePanel.removeAll();
        boolean sawFault = false;
        boolean reachedUnloaderDone = false;
        for (String station : IpBatchManagerModel.EXPECTED_STATIONS) {
            Dao.BottleEventRow match = null;
            for (Dao.BottleEventRow row : history.events) {
                if (station.equals(row.location)) { match = row; break; }
            }
            if (match == null) {
                timelinePanel.add(stationRow(friendlyName(station), "PENDING", null, PENDING_BG, PENDING_BORDER, Color.BLACK));
                continue;
            }
            Color bg;
            Color fg = Color.WHITE;
            if ("DONE".equals(match.status)) {
                bg = READY;
                if ("unloader".equals(station)) { reachedUnloaderDone = true; }
            } else if ("FAULT".equals(match.status)) {
                bg = FAULT;
                sawFault = true;
            } else {
                bg = HOLDING; // ABORTED
                sawFault = true;
            }
            timelinePanel.add(stationRow(friendlyName(station), match.status, match.eventTimestamp, bg, bg, fg));
        }
        timelinePanel.revalidate();
        timelinePanel.repaint();

        journeySettled = reachedUnloaderDone || sawFault;
    }

    private JPanel stationRow(String stationName, String status, String timestamp,
            Color background, Color borderColor, Color textColor) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(true);
        row.setBackground(background);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        row.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        JLabel nameLabel = new JLabel(stationName);
        nameLabel.setForeground(textColor);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 12.5f));
        row.add(nameLabel, BorderLayout.WEST);

        JLabel statusLabel = new JLabel(status, SwingConstants.CENTER);
        statusLabel.setForeground(textColor);
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD, 11.5f));
        row.add(statusLabel, BorderLayout.CENTER);

        JLabel timeLabel = new JLabel(timestamp == null ? " " : timestamp, SwingConstants.RIGHT);
        timeLabel.setForeground(textColor);
        timeLabel.setFont(timeLabel.getFont().deriveFont(Font.PLAIN, 10.5f));
        row.add(timeLabel, BorderLayout.EAST);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        wrap.add(row, BorderLayout.CENTER);
        wrap.add(Box.createVerticalStrut(4), BorderLayout.SOUTH);
        return wrap;
    }

    private static String friendlyName(String station) {
        String friendly = STATION_NAMES.get(station);
        return friendly != null ? friendly : station;
    }

    // ================================================================== Faults History

    private JPanel buildFaultsHistoryTab() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel headerRow = new JPanel(new BorderLayout());
        faultsSummary.setFont(faultsSummary.getFont().deriveFont(Font.BOLD, 12f));
        headerRow.add(faultsSummary, BorderLayout.WEST);
        root.add(headerRow, BorderLayout.NORTH);

        faultsTable.setRowHeight(22);
        faultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] widths = {40, 100, 60, 110, 100, 300, 130, 90, 220};
        for (int c = 0; c < widths.length; c++) {
            faultsTable.getColumnModel().getColumn(c).setPreferredWidth(widths[c]);
        }
        faultsTable.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground("OPEN".equals(value) ? OPEN_BG : RESOLVED_BG);
                }
                return c;
            }
        });
        root.add(new JScrollPane(faultsTable), BorderLayout.CENTER);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        resolveFaultButton.addActionListener(event -> resolveSelectedFault());
        buttonRow.add(resolveFaultButton);
        root.add(buttonRow, BorderLayout.SOUTH);

        return root;
    }

    private void refreshFaults() {
        model.listFaults(faults -> SwingUtilities.invokeLater(() -> {
            Integer selectedId = null;
            int selectedRow = faultsTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < currentFaults.size()) {
                selectedId = currentFaults.get(selectedRow).faultId;
            }
            currentFaults.clear();
            currentFaults.addAll(faults);
            faultsModel.setRowCount(0);
            int open = 0;
            for (Dao.FaultRow f : faults) {
                if ("OPEN".equals(f.status)) { open++; }
                faultsModel.addRow(new Object[] {
                        f.faultId, f.bottleId, f.batchId, f.deviceName, f.faultType, f.faultReason,
                        f.faultTimestamp, f.status, f.resolution == null ? "" : f.resolution
                });
            }
            faultsSummary.setText(open + " open, " + (faults.size() - open) + " resolved");
            if (selectedId != null) {
                for (int i = 0; i < currentFaults.size(); i++) {
                    if (currentFaults.get(i).faultId == selectedId) {
                        faultsTable.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            }
        }));
    }

    private void resolveSelectedFault() {
        int row = faultsTable.getSelectedRow();
        if (row < 0 || row >= currentFaults.size()) {
            JOptionPane.showMessageDialog(this, "Select a fault first.", "Nothing selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Dao.FaultRow fault = currentFaults.get(row);
        if (!"OPEN".equals(fault.status)) {
            JOptionPane.showMessageDialog(this, "Fault #" + fault.faultId + " is already resolved.", "Already resolved", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String resolution = JOptionPane.showInputDialog(this,
                "Resolution note for fault #" + fault.faultId + " (" + fault.deviceName + "):",
                "Resolve fault", JOptionPane.QUESTION_MESSAGE);
        if (resolution == null) { return; } // cancelled
        if (resolution.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a resolution note.", "Missing note", JOptionPane.WARNING_MESSAGE);
            return;
        }
        resolveFaultButton.setEnabled(false);
        model.resolveFault(fault.faultId, resolution.trim(), (success, message) -> SwingUtilities.invokeLater(() -> {
            resolveFaultButton.setEnabled(true);
            if (!success) {
                JOptionPane.showMessageDialog(this, "Could not resolve: " + message, "Error", JOptionPane.ERROR_MESSAGE);
            }
            refreshFaults();
        }));
    }

    // ================================================================== shared helpers

    private static String currentYearPrefix() {
        return "PO-" + new SimpleDateFormat("yyyy", Locale.ROOT).format(new Date()) + "-";
    }

    private String nextPoReference() {
        return currentYearPrefix() + String.format(Locale.ROOT, "%04d", poSequence.getAndIncrement());
    }
}
