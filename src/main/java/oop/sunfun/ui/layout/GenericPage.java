package oop.sunfun.ui.layout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

public abstract class GenericPage extends JFrame {
    /**
     * The screen display's dimensions.
     */
    private static final Dimension SCREEN_DIMENSIONS = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * The main panel of the window, used to display everything.
     */
    private final JPanel contentPanel;

    /**
     * Basic constructor for a generic window in the project.
     * @param title The title of the window.
     */
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

    /**
     * Finalizes the window and displays it after having added all the components to it.
     */
    public final void buildWindow() {
        final double ratio = SCREEN_DIMENSIONS.getWidth() / SCREEN_DIMENSIONS.getHeight();
        final double scaleAmount = 2.0d * (SCREEN_DIMENSIONS.getWidth() / 1920.0d);
        // Pack the window to little size
        this.pack();
        // Set the minimum size as the packed size
        final Dimension minimumDim = this.getSize();
        this.setMinimumSize(minimumDim);
        // Calculate new dimensions for the window
        final Dimension dimensions = this.getSize();
        this.setSize(new Dimension(
                (int) (dimensions.getWidth() * scaleAmount * ratio),
                (int) (dimensions.getHeight() * scaleAmount))
        );
        // Display the window
        this.validate();
        this.setVisible(true);
    }

    /**
     * Adds a component to the main panel of the window, this is the only way to properly add stuff.
     * @param component The component to add to the window.
     * @param constraint The constraints to use to properly position the component.
     */
    public final void addPanelComponent(final Component component, final GridBagConstraints constraint) {
        this.contentPanel.add(component, constraint);
    }
}
