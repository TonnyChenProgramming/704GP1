package com.g7.ip.gui;

import com.g7.ip.Dao;
import nz.ac.auckland.eabs.zhiyuan.coordinator.IpBatchManagerModel;

import javax.swing.BorderFactory;
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
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The real POS front end (brief 4.2: an on-line purchase order system), replacing the
 * console order-entry loop this project started with. Same shape as Eric's own
 * EabsDashboardPanel: a plain Swing GUI constructed in-process and handed a direct
 * reference to the already-running backing model, no default Look &amp; Feel override --
 * deliberately the same boxy Metal chrome Eric's dashboard already runs in.
 *
 * "Place Order" is a cart: no product picker (brief 4.2 -- a product IS its bottle size
 * and liquid specification, not a separate choice) and no cap on how many distinct
 * capacity/ratio combinations one submission can cover. "Track Order" resolves a
 * customer-visible PO reference back to its admission/completion state, and polls for
 * updates on its own so the operator does not have to keep re-clicking Track.
 */
public final class PosGuiFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final int COL_NUM = 0;
    private static final int COL_CAPACITY = 1;
    private static final int COL_DOSE_A = 2;
    private static final int COL_DOSE_B = 3;
    private static final int COL_QTY = 4;
    private static final int COL_FORMULATION = 5;

    /** Bottle sizes the physical loader/conveyor actually support -- capacity is a fixed
     * choice, not free text, the same way a real line can't be handed an arbitrary bottle. */
    private static final String[] BOTTLE_CAPACITIES = {"250ml", "500ml", "1L"};

    private static final Color READY = new Color(76, 175, 80);
    private static final Color DONE = new Color(66, 133, 244);
    private static final Color FAULT = new Color(220, 53, 69);
    private static final Color HOLDING = new Color(255, 152, 0);
    private static final Color OK_BG = new Color(226, 239, 218);

    private static final Pattern DERIVED_PRODUCT = Pattern.compile("^FORM-(.+)-(\\d+)-(\\d+)$");
    private static final int POLL_INTERVAL_MS = 2000;

    private final IpBatchManagerModel model;
    /** Seeded from the database at startup (see the constructor) -- never left at its default
     * of 1 once that seeding completes, precisely to avoid the UNIQUE-constraint collision a
     * fixed, reused database file causes across JVM restarts. */
    private final AtomicInteger poSequence = new AtomicInteger(1);

    // Place Order tab.
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
    private final JLabel footerStatus = new JLabel("Ready -- connected to POS database.");

    // Track Order tab.
    private final DefaultComboBoxModel<String> poHistoryModel = new DefaultComboBoxModel<>();
    private final JComboBox<String> poCombo = new JComboBox<>(poHistoryModel);
    private final JLabel summaryLabel = new JLabel(" ");
    private final JLabel statusPill = new JLabel("", SwingConstants.CENTER);
    private final JLabel progressText = new JLabel(" ");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel diagLabel = new JLabel(" ");
    private String lastTrackedPo = null;
    private boolean trackedOrderSettled = false;

    public PosGuiFrame(IpBatchManagerModel model) {
        super("COMPSYS 704 - EABS Group 7 - Purchase Order System");
        this.model = model;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Place Order", buildPlaceOrderTab());
        tabs.addTab("Track Order", buildTrackOrderTab());
        setContentPane(tabs);

        addLine();
        setMinimumSize(new Dimension(1180, 760));
        pack();
        setLocationRelativeTo(null);

        // Live status: re-run the last lookup on a timer instead of requiring a click every
        // time. Stops polling once the tracked order reaches a settled (terminal) state so it
        // does not keep hammering the DB worker for an order that can no longer change.
        Timer pollTimer = new Timer(POLL_INTERVAL_MS, event -> {
            if (lastTrackedPo != null && !trackedOrderSettled) {
                model.lookupOrder(lastTrackedPo, status -> SwingUtilities.invokeLater(() -> applyTrackResult(lastTrackedPo, status)));
            }
        });
        pollTimer.start();

        // Seed the PO counter from whatever this database file already has under today's
        // prefix, so restarting against a reused -Dip.database=...db file (see
        // RunCoordinatorPosGui.launch) can never re-issue a customer_po that already exists
        // and fails the UNIQUE constraint on submit. Submitting is disabled until this
        // returns so there is no window where a stale counter=1 could still be used.
        submitButton.setEnabled(false);
        footerStatus.setText("Loading PO sequence...");
        model.highestPoSequence(currentYearPrefix(), highest -> SwingUtilities.invokeLater(() -> {
            poSequence.set(highest + 1);
            submitButton.setEnabled(true);
            footerStatus.setText("Ready -- connected to POS database.");
        }));
    }

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
        footerStatus.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        footerStatus.setOpaque(true);
        footerStatus.setBackground(OK_BG);
        south.add(footerStatus, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);
        return root;
    }

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
        statusPanel.setLayout(new javax.swing.BoxLayout(statusPanel, javax.swing.BoxLayout.Y_AXIS));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Order status"));

        summaryLabel.setFont(summaryLabel.getFont().deriveFont(Font.BOLD, 14f));
        summaryLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        summaryLabel.setBorder(BorderFactory.createEmptyBorder(4, 2, 10, 2));

        JPanel statusRow = new JPanel(new BorderLayout(16, 0));
        statusRow.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        statusPill.setOpaque(true);
        statusPill.setForeground(Color.WHITE);
        statusPill.setBackground(HOLDING);
        statusPill.setPreferredSize(new Dimension(220, 34));
        statusPill.setFont(statusPill.getFont().deriveFont(Font.BOLD, 12f));
        statusRow.add(statusPill, BorderLayout.WEST);

        JPanel progressPanel = new JPanel(new BorderLayout(0, 3));
        progressText.setFont(progressText.getFont().deriveFont(Font.PLAIN, 11.5f));
        progressPanel.add(progressText, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.SOUTH);
        statusRow.add(progressPanel, BorderLayout.CENTER);

        diagLabel.setOpaque(true);
        diagLabel.setBackground(OK_BG);
        diagLabel.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        diagLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        diagLabel.setFont(diagLabel.getFont().deriveFont(Font.PLAIN, 11.5f));

        statusPanel.add(summaryLabel);
        statusPanel.add(statusRow);
        statusPanel.add(javax.swing.Box.createVerticalStrut(10));
        statusPanel.add(diagLabel);
        clearTrackResult();
        root.add(statusPanel, BorderLayout.CENTER);
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
        footerStatus.setBackground(new Color(255, 243, 205));
        footerStatus.setText("Submitting " + rowCount + " order line(s)...");
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
                            footerStatus.setBackground(OK_BG);
                            footerStatus.setText("Submitted " + rowCount + " order line(s).");
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

    private void trackOrder() {
        Object selected = poCombo.getEditor().getItem();
        String po = selected == null ? "" : String.valueOf(selected).trim();
        if (po.isEmpty()) { return; }
        lastTrackedPo = po;
        trackedOrderSettled = false;
        model.lookupOrder(po, status -> SwingUtilities.invokeLater(() -> applyTrackResult(po, status)));
    }

    private void clearTrackResult() {
        summaryLabel.setText("Enter a PO reference and click Track.");
        statusPill.setText("");
        statusPill.setBackground(Color.LIGHT_GRAY);
        progressText.setText(" ");
        progressBar.setValue(0);
        progressBar.setStringPainted(false);
        diagLabel.setText(" ");
    }

    private void applyTrackResult(String po, Dao.OrderStatus status) {
        if (!po.equals(lastTrackedPo)) { return; } // a newer lookup superseded this one
        if (status == null) {
            summaryLabel.setText("No order found for PO " + po + ".");
            statusPill.setText("NOT FOUND");
            statusPill.setBackground(FAULT);
            progressText.setText(" ");
            progressBar.setValue(0);
            progressBar.setStringPainted(false);
            diagLabel.setText("Check the reference and try again.");
            trackedOrderSettled = true;
            return;
        }

        String formulation = formatFormulation(status.productId);
        summaryLabel.setText("PO " + status.customerPo + " | Customer " + status.customerId
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
            statusPill.setText("COMPLETED");
            statusPill.setBackground(READY);
        } else if (batchFaulted) {
            statusPill.setText("BATCH FAULT");
            statusPill.setBackground(FAULT);
        } else if ("ADMITTED".equals(status.status)) {
            statusPill.setText("ADMITTED - IN PRODUCTION");
            statusPill.setBackground(DONE);
        } else {
            statusPill.setText("PENDING");
            statusPill.setBackground(HOLDING);
        }
        trackedOrderSettled = done || batchFaulted;

        int percent = status.quantity <= 0 ? 0
                : Math.min(100, (int) Math.round(100.0 * status.completedInBatch / status.quantity));
        progressBar.setValue(percent);
        progressBar.setStringPainted(true);
        progressText.setText(status.completedInBatch + " / " + status.quantity + " bottles completed");

        if (done) {
            diagLabel.setText("All " + status.quantity + " bottles for this order have completed.");
        } else if (status.batchId == null) {
            diagLabel.setText("Not yet admitted into a batch -- waiting for enough pending demand of the same formulation.");
        } else if (batchFaulted) {
            diagLabel.setText("Batch #" + status.batchId + " ended in FAULT.");
        } else if ("RUNNING".equals(status.batchStatus)) {
            diagLabel.setText("Batch #" + status.batchId + " is currently RUNNING on the production line.");
        } else {
            diagLabel.setText("Batch #" + status.batchId + " status: " + status.batchStatus);
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

    private static String currentYearPrefix() {
        return "PO-" + new SimpleDateFormat("yyyy", Locale.ROOT).format(new Date()) + "-";
    }

    private String nextPoReference() {
        return currentYearPrefix() + String.format(Locale.ROOT, "%04d", poSequence.getAndIncrement());
    }
}
