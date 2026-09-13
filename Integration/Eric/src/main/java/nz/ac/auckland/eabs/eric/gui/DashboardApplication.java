package nz.ac.auckland.eabs.eric.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;

/** Standalone read-only GUI shell for connecting to the shared coordinator. */
public final class DashboardApplication {
    private DashboardApplication() { }

    public static void main(String[] args) {
        VisualizationBridge bridge = new VisualizationBridge(command -> {
            throw new IllegalStateException("Orders and controls belong to POS/coordinator");
        });

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("COMPSYS 704 - EABS Group 7");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new EabsDashboardPanel(
                    bridge, EabsDashboardPanel.Mode.READ_ONLY));
            frame.setMinimumSize(new Dimension(1180, 720));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
