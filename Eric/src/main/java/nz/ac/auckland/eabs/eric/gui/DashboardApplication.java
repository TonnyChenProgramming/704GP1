package nz.ac.auckland.eabs.eric.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;

/** Standalone GUI shell for connecting to the shared coordinator adapter. */
public final class DashboardApplication {
    private DashboardApplication() { }

    public static void main(String[] args) {
        VisualizationBridge bridge = new VisualizationBridge(command ->
                System.out.println(
                        "OPERATOR_COMMAND " + command.getType()
                                + " target=" + command.getTarget()
                                + " value=" + command.getValue()));

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("COMPSYS 704 - EABS Group 7");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new EabsDashboardPanel(bridge));
            frame.setMinimumSize(new Dimension(1180, 720));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
