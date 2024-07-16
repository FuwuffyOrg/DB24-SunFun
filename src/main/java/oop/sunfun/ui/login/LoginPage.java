package oop.sunfun.ui.login;

import oop.sunfun.database.dao.AccountDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.util.Optional;

// TODO: Change to form, need to change form functionality tho
public final class LoginPage extends GenericPage {
    private static final String PAGE_NAME = "Login";

    private final JTextComponent txtEmail;
    private final JTextComponent txtPassword;

    public LoginPage(final CloseEvents closeEvent) {
        super(PAGE_NAME, closeEvent);
        // Add two labels and text boxes for inputting username and password.
        final Component lblEmail = new JLabel("Email: ");
        final Component lblPassword = new JLabel("Password: ");
        this.txtEmail = new JTextField();
        this.txtPassword = new JPasswordField();
        final AbstractButton btnLogin = new JButton("Login");
        final AbstractButton btnRegister = new JButton("Goto Register");
        // Add all the components.
        this.add(lblEmail,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(txtEmail,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.add(lblPassword,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(txtPassword,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.add(btnLogin,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(btnRegister,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(1)
                        .setFillAll()
                        .build()
        );
        // Add events
        // Event to go to the register page
        btnRegister.addActionListener(e -> {
            // Go to register page
            this.switchPage(new RegisterPage(CloseEvents.EXIT_PROGRAM));
        });
        // Event to log into the application
        btnLogin.addActionListener(e -> {
            if (LoginPage.this.isDataValid()) {
                final Optional<AccountData> account = AccountDAO.getAccount(txtEmail.getText(),
                        txtPassword.getText());
                account.ifPresent(accountData -> this.switchPage(new LandingPage(
                        CloseEvents.EXIT_PROGRAM, accountData)));
            }
        });
        // Finish the window.
        this.buildWindow();
    }

    private boolean isDataValid() {
        final int passwordLengthLimit = 24;
        final int emailLengthLimit = 256;
        final int minSize = 4;
        this.resetHighlights();
        final String email = this.txtEmail.getText();
        final String password = this.txtEmail.getText();
        if (email.length() > emailLengthLimit || email.length() < minSize) {
            GenericPage.highlightTextComponent(this.txtEmail);
            return false;
        } else if (password.length() > passwordLengthLimit || password.length() < minSize) {
            GenericPage.highlightTextComponent(this.txtPassword);
            return false;
        }
        return true;
    }
}
