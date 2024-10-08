package oop.sunfun.ui.util.pages;

import oop.sunfun.ui.util.behavior.CloseEvents;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

public abstract class GenericPage extends JFrame {
    /**
     * The color used for error highlighting.
     */
    private static final Color HIGHLIGHT_COLOR = new Color(255, 0, 0, 100);

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
     * @param closeEvent Describes what happens when the window closes.
     */
    public GenericPage(final String title, final CloseEvents closeEvent) {
        // Add a close operation
        this.setDefaultCloseOperation(closeEvent.getEventValue());
        // Set the window title
        this.setTitle(title);
        // Set a basic JPanel as default view
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new GridBagLayout());
        this.setContentPane(this.contentPanel);
    }

    /**
     * Finalizes the window and displays it after having added all the components to it.
     */
    public final void buildWindow() {
        final double screenSizeMult = 0.5d;
        final double minimumDimMult = 1.2d;
        this.pack();
        // Set the minimum size as the packed size
        final Dimension minimumDim = this.getSize();
        minimumDim.setSize(Math.min(minimumDim.getWidth(), SCREEN_DIMENSIONS.getWidth() * screenSizeMult),
                Math.min(minimumDim.getHeight(), SCREEN_DIMENSIONS.getHeight() * screenSizeMult));
        this.setMinimumSize(minimumDim);
        // Calculate new dimensions for the window
        this.setSize(new Dimension(
                (int) (minimumDim.getWidth() * minimumDimMult),
                (int) (minimumDim.getHeight() * minimumDimMult))
        );
        // Center the window to the screen
        this.setLocationRelativeTo(null);
        // Display the window
        this.validate();
    }

    /**
     * Method to highlight an item in the page (mostly used for error highlighting).
     * @param textField The text field to highlight.
     */
    protected static void highlightTextComponent(final JTextComponent textField) {
        textField.setBackground(HIGHLIGHT_COLOR);
    }

    /**
     * Method to reset all highlighted items in the page.
     */
    protected final void resetHighlights() {
        for (final Component c : this.contentPanel.getComponents()) {
            c.setBackground(UIManager.getColor("TextField.background"));
        }
    }

    /**
     * Method to switch over the page to another.
     * @param page The page to switch view to.
     */
    protected final void switchPage(final GenericPage page) {
        page.setVisible(true);
        this.dispose();
    }
}
