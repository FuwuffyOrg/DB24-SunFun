package oop.sunfun.ui.login;

import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.connection.SunFunDatabase;
import oop.sunfun.database.data.AccountData;
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
        this.addPanelComponent(lblEmail,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtEmail,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblPassword,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtPassword,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(btnLogin,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(btnRegister,
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
                final String accountQuery = "SELECT * FROM `account` WHERE `email` = ? AND password = ?";
                final IDatabaseConnection database = SunFunDatabase.getDatabaseInstance();
                try {
                    database.openConnection();
                    // Get all the results from the query
                    final List<Map<String, Object>> results = database.getQueryData(accountQuery,
                            txtEmail.getText(), txtPassword.getText());
                    // Get the account
                    final AccountData account = getAccountData(results);
                    // Go to landing page
                    this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, account));
                } catch (final SQLException err) {
                    LOGGER.log(Level.SEVERE, "Couldn't fetch the account data", err);
                    database.closeConnection();
                    this.close();
                }
            }
        });
        // Finish the window.
        this.buildWindow();
    }

    private static AccountData getAccountData(final List<Map<String, Object>> results) {
        // If there's more than one account, there must have been an error
        if (results.size() > 1) {
            LOGGER.log(Level.WARNING, "There's two accounts with the same email and password");
        }
        // Get the data and build an account record
        final Map<String, Object> data = results.getFirst();
        return new AccountData((String) data.get("email"),
                (String) data.get("tipologia"));
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
