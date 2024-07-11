package oop.sunfun.ui;

import oop.sunfun.ui.layout.Anchors;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public abstract class GenericPage extends JFrame {
    private final JPanel contentPanel;

    public GenericPage(final String title) {
        // Add a close operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the window title
        this.setTitle(title);
        // Center the window to the screen
        this.setLocationRelativeTo(null);
        // Set a basic JPanel as default view
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new GridBagLayout());
        this.setContentPane(this.contentPanel);
    }

    public final void buildWindow() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double ratio = screenSize.getWidth() / screenSize.getHeight();
        final double scaleHeight = 2.0d;
        final double scaleWidth = scaleHeight * ratio;
        // Pack the window to little size
        this.pack();
        // Set the minimum size as the packed size
        final Dimension minimumDim = this.getSize();
        this.setMinimumSize(minimumDim);
        // Calculate new dimensions for the window
        final Dimension dimensions = this.getSize();
        this.setSize(new Dimension(
                (int) (dimensions.getWidth() * scaleWidth),
                (int) (dimensions.getHeight() * scaleHeight))
        );
        // Display the window
        this.setVisible(true);
    }

    public final void addPanelComponent(final Component component, final int row, final int column) {
        final GridBagConstraints constraint = new GridBagConstraintBuilder()
                .setRow(row)
                .setColumn(column)
                .build();
        this.contentPanel.add(component, constraint);
    }

    public final void addPanelComponent(final Component component, final int row, final int column, final double rowWeight, final double columnWeight) {
        final GridBagConstraints constraint = new GridBagConstraintBuilder()
                .setRow(row)
                .setColumn(column)
                .setWeightRow(rowWeight)
                .setWeightColumn(columnWeight)
                .build();
        this.contentPanel.add(component, constraint);
    }
}
