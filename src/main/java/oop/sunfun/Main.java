package oop.sunfun;

import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.login.RegisterPage;

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
        GenericPage.setupTheme();

        final JFrame registerPage = new RegisterPage(CloseEvents.EXIT_PROGRAM);
        registerPage.setVisible(true);
    }
}
