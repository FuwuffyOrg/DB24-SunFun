package oop.sunfun.ui.login;

import oop.sunfun.database.dao.AccountDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.FormPage;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class LoginPage extends FormPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Login";

    /**
     * Components of the form used by the FormPage class.
     */
    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    /**
     * Textbox to keep the email of the user.
     */
    private static final JComponent TXT_EMAIL;

    /**
     * Textbox to keep the password of the user.
     */
    private static final JComponent TXT_PASSWORD;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        TXT_EMAIL = new JTextField();
        TXT_PASSWORD = new JPasswordField();
        FORM_COMPONENTS.put(new JLabel("Email:"), new Pair<>(TXT_EMAIL, 256));
        FORM_COMPONENTS.put(new JLabel("Password:"), new Pair<>(TXT_PASSWORD, 24));
    }

    /**
     * Constructor of the login page to log in to the application.
     * @param closeEvent The event that happens when you close the window.
     */
    public LoginPage(final CloseEvents closeEvent) {
        super(PAGE_NAME, closeEvent, 1, FORM_COMPONENTS,
                () -> new LandingPage(CloseEvents.EXIT_PROGRAM, AccountDAO.getAccount(
                        ((JTextComponent) TXT_EMAIL).getText(), ((JTextComponent) TXT_PASSWORD).getText()).get()),
                () -> new RegisterPage(CloseEvents.EXIT_PROGRAM),
                () -> {

                });
        // Finish the window.
        this.buildWindow();
    }

    @Override
    protected boolean isDataValid() {
        final Optional<AccountData> account = AccountDAO.getAccount(((JTextComponent) TXT_EMAIL).getText(),
                ((JTextComponent) TXT_PASSWORD).getText());
        return super.isDataValid() && account.isPresent();
    }
}
