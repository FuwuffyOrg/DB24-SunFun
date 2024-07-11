package oop.sunfun.ui.layout;

import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;
import oop.sunfun.ui.behavior.CloseEvents;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
