package oop.sunfun.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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

    public void addPanelComponent(final Component component, final GridBagConstraints constraint) {
        this.contentPanel.add(component, constraint);
    }
}
