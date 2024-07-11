package oop.sunfun.ui;

import oop.sunfun.database.connection.DatabaseConnection;
import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public final class LoginPage extends GenericPage {
    private final JTextComponent txtEmail;
    private final JTextComponent txtPassword;

    public LoginPage(final String title, final CloseEvents closeEvent) {
        super(title, closeEvent);
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
        btnRegister.addActionListener(e -> {
            final JFrame registerPage = new RegisterPage("SunFun Register", CloseEvents.EXIT_PROGRAM);
            registerPage.setVisible(true);
            LoginPage.this.dispose();
        });
        btnLogin.addActionListener(e -> {
            if (LoginPage.this.isDataValid()) {
                final String accountQuery = "SELECT * FROM `account` WHERE `email` = ? AND password = ?";
                final IDatabaseConnection database = new DatabaseConnection("sunfun",
                        "jdbc:mysql://localhost", "root", "");
                try {
                    database.openConnection();
                    final List<Map<String, Object>> results = database.getQueryData(accountQuery,
                            txtEmail.getText(), txtPassword.getText());
                    // TODO: Make actual login happen!
                    database.closeConnection();
                } catch (final SQLException err) {
                    err.printStackTrace();
                    this.close();
                }
                LoginPage.this.close();
            }
        });
        // Finish the window.
        this.buildWindow();
    }

    private boolean isDataValid() {
        final int passwordLengthLimit = 24;
        final int emailLengthLimit = 256;
        final int minSize = 4;
        if (this.txtPassword.getText().length() > passwordLengthLimit
                || this.txtPassword.getText().length() < minSize) {
            return false;
        }
        return this.txtEmail.getText().length() <= emailLengthLimit && this.txtEmail.getText().length() >= minSize;
    }
}
