package oop.sunfun.ui.login;

import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.connection.SunFunDatabase;
import oop.sunfun.database.data.QueryManager;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoginPage extends GenericPage {
    private static final Logger LOGGER = Logger.getLogger(LoginPage.class.getName());

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
                final Optional<AccountData> account = QueryManager.getAccountByEmailAndPassword(txtEmail.getText(), txtPassword.getText());
                account.ifPresent(accountData -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, accountData)));
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
        if (this.txtEmail.getText().length() > emailLengthLimit && this.txtEmail.getText().length() < minSize) {
            GenericPage.highlightTextComponent(this.txtEmail);
            return false;
        }
        if (this.txtPassword.getText().length() > passwordLengthLimit
                || this.txtPassword.getText().length() < minSize) {
            GenericPage.highlightTextComponent(this.txtPassword);
            return false;
        }
        return true;
    }
}
