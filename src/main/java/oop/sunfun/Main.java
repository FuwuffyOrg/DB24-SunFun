package oop.sunfun;

import oop.sunfun.ui.LoginPage;
import oop.sunfun.ui.RegisterPage;
import oop.sunfun.ui.layout.GenericPage;

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

        final JFrame loginPage = new LoginPage();
        loginPage.setVisible(true);
        final JFrame registerPage = new RegisterPage();
        registerPage.setVisible(true);
    }
}
