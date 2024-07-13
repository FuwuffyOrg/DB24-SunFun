package oop.sunfun.ui.util.layout;

import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;
import oop.sunfun.ui.util.behavior.CloseEvents;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

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
     * Sets up everything to do with theming.
     */
    public static void setupTheme() {
        final int cornersRoundness = 15;
        final int focusSize = 2;
        final int scrollbarRoundness = 999;
        final Insets trackInset = new Insets(2, 4, 2, 4);
        final Insets thumbInsets = new Insets(2, 2, 2, 2);
        FlatNordIJTheme.setup();
        // Rounded borders
        UIManager.put("Button.arc", cornersRoundness);
        UIManager.put("Component.arc", cornersRoundness);
        UIManager.put("CheckBox.arc", cornersRoundness);
        UIManager.put("ProgressBar.arc", cornersRoundness);
        UIManager.put("TextComponent.arc", cornersRoundness);
        // Arrows on number stuff
        UIManager.put("Component.arrowType", "triangle");
        // Focus size
        UIManager.put("Component.focusWidth", focusSize);
        // Show buttons on scrollbars
        UIManager.put("ScrollBar.showButtons", true);
        // Track for scrollbars
        UIManager.put("ScrollBar.trackArc", scrollbarRoundness);
        UIManager.put("ScrollBar.thumbArc", scrollbarRoundness);
        UIManager.put("ScrollBar.trackInsets", trackInset);
        UIManager.put("ScrollBar.thumbInsets", thumbInsets);
        // Tab separator stuff
        UIManager.put("TabbedPane.showTabSeparators", true);
    }

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
        final double ratio = SCREEN_DIMENSIONS.getWidth() / SCREEN_DIMENSIONS.getHeight();
        final double scaleAmount = 1.25d * (SCREEN_DIMENSIONS.getWidth() / 1920.0d);
        // Pack the window to little size
        this.pack();
        // Set the minimum size as the packed size
        final Dimension minimumDim = this.getSize();
        minimumDim.setSize(Math.min(minimumDim.getWidth(), SCREEN_DIMENSIONS.getWidth() / 2.0d),
                Math.min(minimumDim.getHeight(), SCREEN_DIMENSIONS.getHeight() / 2.0d));
        this.setMinimumSize(minimumDim);
        // Calculate new dimensions for the window
        final Dimension dimensions = this.getSize();
        this.setSize(new Dimension(
                (int) (dimensions.getWidth() * scaleAmount * ratio),
                (int) (dimensions.getHeight() * scaleAmount))
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
     * Closes the window as if you pressed the X button, running the proper event.
     */
    public final void close() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
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
