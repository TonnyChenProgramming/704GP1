package com.g7.ip.gui;

import com.g7.ip.Dao;
import com.g7.ip.DeviationDetector;
import nz.ac.auckland.eabs.zhiyuan.coordinator.IpBatchManagerModel;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The factory-facing view: what the operator watches, as opposed to what the customer orders
 * (com.g7.ip.gui.PosGuiFrame). First and most important tab, per direct instruction, is the
 * per-bottle digital twin -- the whole production journey of one physical bottle, station by
 * station -- with production-dashboard/fault-log tabs deliberately left for a later pass.
 * Same in-process, direct-model-reference construction as PosGuiFrame and Eric's own
 * EabsDashboardPanel, and the same plain Metal chrome (no custom Look &amp; Feel anywhere in
 * this project).
 */
public final class FactoryGuiFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final Color READY = new Color(76, 175, 80);
    private static final Color FAULT = new Color(220, 53, 69);
    private static final Color HOLDING = new Color(255, 152, 0);
    private static final Color OFFLINE = new Color(155, 155, 155);
    private static final Color PENDING_BG = new Color(238, 242, 247);
    private static final Color PENDING_BORDER = new Color(90, 100, 115);

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

    private static final int POLL_INTERVAL_MS = 2000;

    private final IpBatchManagerModel model;
    private final DefaultComboBoxModel<String> bottleHistoryModel = new DefaultComboBoxModel<>();
    private final JComboBox<String> bottleCombo = new JComboBox<>(bottleHistoryModel);
    private final JLabel summaryLabel = new JLabel(" ");
    private final JLabel deviationBadge = new JLabel("", SwingConstants.CENTER);
    private final JLabel deviationReason = new JLabel(" ");
    private final JPanel timelinePanel = new JPanel();

    private String lastLookedUpBottle = null;
    private boolean journeySettled = false;

    public FactoryGuiFrame(IpBatchManagerModel model) {
        super("COMPSYS 704 - EABS Group 7 - Factory View");
        this.model = model;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Bottle Traceability", buildTraceabilityTab());
        setContentPane(tabs);

        setMinimumSize(new Dimension(760, 720));
        pack();
        setLocationRelativeTo(null);

        refreshRecentBottles();

        Timer pollTimer = new Timer(POLL_INTERVAL_MS, event -> {
            if (lastLookedUpBottle != null && !journeySettled) {
                model.lookupBottleHistory(lastLookedUpBottle, (history, deviation) ->
                        SwingUtilities.invokeLater(() -> applyResult(lastLookedUpBottle, history, deviation)));
            }
        });
        pollTimer.start();
    }

    private JPanel buildTraceabilityTab() {
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
        summaryLabel.setFont(summaryLabel.getFont().deriveFont(Font.BOLD, 13f));
        summaryLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        JPanel devRow = new JPanel(new BorderLayout(12, 0));
        devRow.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        deviationBadge.setOpaque(true);
        deviationBadge.setForeground(Color.WHITE);
        deviationBadge.setBackground(OFFLINE);
        deviationBadge.setPreferredSize(new Dimension(170, 30));
        deviationBadge.setFont(deviationBadge.getFont().deriveFont(Font.BOLD, 12f));
        devRow.add(deviationBadge, BorderLayout.WEST);
        deviationReason.setFont(deviationReason.getFont().deriveFont(Font.PLAIN, 11.5f));
        devRow.add(deviationReason, BorderLayout.CENTER);

        headerPanel.add(summaryLabel);
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
        clearHeader();
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
                SwingUtilities.invokeLater(() -> applyResult(bottleId, history, deviation)));
    }

    private void clearHeader() {
        summaryLabel.setText("Enter a bottle ID (e.g. B-7-0003) and click Look up.");
        deviationBadge.setText("");
        deviationBadge.setBackground(OFFLINE);
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

    private void applyResult(String bottleId, Dao.BottleHistory history, DeviationDetector.Result deviation) {
        if (!bottleId.equals(lastLookedUpBottle)) { return; } // superseded by a newer lookup

        if (history == null) {
            summaryLabel.setText("No history found for bottle " + bottleId + ".");
            deviationBadge.setText("NOT FOUND");
            deviationBadge.setBackground(FAULT);
            deviationReason.setText("Check the ID and try again -- it is minted the moment a bottle is admitted at the loader.");
            renderPendingRoute();
            journeySettled = true;
            return;
        }

        refreshRecentBottles();

        summaryLabel.setText("Bottle " + history.bottleId
                + "  |  Workpiece " + history.workpieceId
                + "  |  Batch #" + history.batchId
                + "  |  Order " + (history.orderId == null ? "-" : ("#" + history.orderId))
                + "  |  " + PosGuiFrame.formatFormulation(history.productId));

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
}
