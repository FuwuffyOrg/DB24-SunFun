package oop.sunfun.ui;

import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public abstract class GenericPage extends JFrame {
    private final JPanel contentPanel;

    public GenericPage(final String title, final int width, final int height) {
        // Add a close operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Sets the size of the window
        this.setSize(width, height);
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
        final Dimension currentDim = this.getSize();
        // Pack the window to little size
        this.pack();
        // Set the minimum size as the packed size
        this.setMinimumSize(this.getSize());
        // Return the dimensions to the original ones
        this.setSize(currentDim);
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
