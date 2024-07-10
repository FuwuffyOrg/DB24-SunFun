package oop.sunfun;

import com.formdev.flatlaf.FlatDarkLaf;
import oop.sunfun.ui.LoginPage;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class Main {
    private Main() {
        // Useless constructor.
    }

    /**
     * Entrypoint of the program.
     * @param args The default arguments of any application.
     */
    public static void main(final String[] args) {
        try {
            // Use FlatDarkLaf for dark theme, or FlatLightLaf for light theme
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (final UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        final JFrame loginPage = new LoginPage();
    }
}
