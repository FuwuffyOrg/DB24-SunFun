package oop.sunfun;

import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;
import oop.sunfun.ui.LoginPage;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Insets;

public final class Main {
    private Main() {
        // Useless constructor.
    }

    /**
     * Sets up everything to do with theming.
     */
    private static void setupTheme() {
        FlatNordIJTheme.setup();
        // Rounded borders
        UIManager.put("Button.arc", 15);
        UIManager.put("Component.arc", 15);
        UIManager.put("CheckBox.arc", 15);
        UIManager.put("ProgressBar.arc", 15);
        UIManager.put("TextComponent.arc", 15);
        // Arrows on number stuff
        UIManager.put("Component.arrowType", "triangle");
        // Focus size
        UIManager.put("Component.focusWidth", 2);
        // Show buttons on scrollbars
        UIManager.put("ScrollBar.showButtons", true);
        // Track for scrollbars
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        // Tab separator stuff
        UIManager.put("TabbedPane.showTabSeparators", true);
    }

    /**
     * Entrypoint of the program.
     * @param args The default arguments of any application.
     */
    public static void main(final String[] args) {
        Main.setupTheme();
        final JFrame loginPage = new LoginPage();
    }
}
