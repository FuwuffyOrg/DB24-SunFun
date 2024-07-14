package oop.sunfun;

import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.login.RegisterPage;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Insets;

public final class Main {
    private Main() {
        // Useless constructor.
    }

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
     * Entrypoint of the program.
     * @param args The default arguments of any application.
     */
    public static void main(final String[] args) {
        Main.setupTheme();

        final JFrame registerPage = new RegisterPage(CloseEvents.EXIT_PROGRAM);
        registerPage.setVisible(true);
    }
}
