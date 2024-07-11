package oop.sunfun;

import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;
import oop.sunfun.ui.LoginPage;

import javax.swing.JFrame;

public final class Main {
    private Main() {
        // Useless constructor.
    }

    /**
     * Entrypoint of the program.
     * @param args The default arguments of any application.
     */
    public static void main(final String[] args) {
        FlatNordIJTheme.setup();
        final JFrame loginPage = new LoginPage();
    }
}
