package oop.sunfun.ui;

import oop.sunfun.database.connection.DatabaseConnection;
import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.enums.ParentType;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import java.awt.Component;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class RegisterPage extends GenericPage {
    final JTextComponent txtCodiceFiscale;
    final JTextComponent txtName;
    final JTextComponent txtSurname;
    final JTextComponent txtPhone;
    final JTextComponent txtEmail;
    final JTextComponent txtPassword;
    final JTextComponent txtPasswordConfirm;
    final ButtonGroup radParentTypeGroup;

    public RegisterPage(final String title, final CloseEvents closeEvent) {
        super(title, closeEvent);
        // Add two labels and text boxes for inputting username and password.
        final Component lblCodiceFiscale = new JLabel("Codice Fiscale: ");
        final Component lblName = new JLabel("Nome: ");
        final Component lblSurname = new JLabel("Cognome: ");
        final Component lblPhone = new JLabel("Numero di telefono: ");
        final Component lblEmail = new JLabel("Email: ");
        final Component lblPassword = new JLabel("Password: ");
        final Component lblPasswordConfirm = new JLabel("Conferma Password: ");
        this.txtCodiceFiscale = new JTextField();
        this.txtName = new JTextField();
        this.txtSurname = new JTextField();
        this.txtPhone = new JTextField();
        this.txtEmail = new JTextField();
        this.txtPassword = new JPasswordField();
        this.txtPasswordConfirm = new JPasswordField();
        this.radParentTypeGroup = new ButtonGroup();
        final AbstractButton btnRegister = new JButton("Register");
        final AbstractButton btnLogin = new JButton("Goto Login");
        // Add all the components.
        this.addPanelComponent(lblCodiceFiscale,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(0)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtCodiceFiscale,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(3)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblName,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(0)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtName,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(3)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblSurname,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(0)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtSurname,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(3)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblPhone,
                new GridBagConstraintBuilder()
                        .setRow(3).setColumn(0)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtPhone,
                new GridBagConstraintBuilder()
                        .setRow(3).setColumn(3)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblEmail,
                new GridBagConstraintBuilder()
                        .setRow(4).setColumn(0)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtEmail,
                new GridBagConstraintBuilder()
                        .setRow(4).setColumn(3)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblPassword,
                new GridBagConstraintBuilder()
                        .setRow(5).setColumn(0)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtPassword,
                new GridBagConstraintBuilder()
                        .setRow(5).setColumn(3)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblPasswordConfirm,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(0)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtPasswordConfirm,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(3)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        // Add the parent type radio buttons
        AtomicInteger index = new AtomicInteger(0);
        EnumSet.allOf(ParentType.class).forEach(p -> {
            final AbstractButton radio = new JRadioButton(p.getTextValue());
            radio.setActionCommand(p.getTextValue());
            radio.setSelected(true);
            this.radParentTypeGroup.add(radio);
            this.addPanelComponent(radio,
                    new GridBagConstraintBuilder()
                            .setRow(7).setColumn(index.get())
                            .setFillAll()
                            .build()
            );
            index.addAndGet(1);
        });
        this.addPanelComponent(btnRegister,
                new GridBagConstraintBuilder()
                        .setRow(8).setColumn(0)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(btnLogin,
                new GridBagConstraintBuilder()
                        .setRow(8).setColumn(3)
                        .setWidth(3)
                        .setFillAll()
                        .build()
        );
        // Add events
        btnLogin.addActionListener(e -> {
            final JFrame loginPage = new LoginPage("SunFun Register", CloseEvents.EXIT_PROGRAM);
            loginPage.setVisible(true);
            RegisterPage.this.dispose();
        });
        btnRegister.addActionListener(e -> {
            if (RegisterPage.this.isDataValid()) {
                final String accountQuery = "INSERT INTO `account`(`email`, `password`, `tipologia`) " +
                        "VALUES ('%s','%s','Parente')";

                final String parenteQuery = "INSERT INTO `parente`(`codice_fiscale`, `fk_account`, " +
                        "`nome`, `cognome`, `cellulare`, `grado_di_parentela`) VALUES ('%s','%s','%s','%s','%s','%s')";

                final IDatabaseConnection database = new DatabaseConnection("sunfun",
                        "jdbc:mysql://localhost", "root", "");
                try {
                    database.openConnection();
                    database.setQueryData(String.format(accountQuery, txtEmail.getText(), txtPassword.getText()));
                    database.setQueryData(String.format(parenteQuery, txtCodiceFiscale.getText(),
                            txtEmail.getText(), txtName.getText(), txtSurname.getText(), txtPhone.getText(),
                            this.radParentTypeGroup.getSelection().getActionCommand()));
                    database.closeConnection();
                } catch (final SQLException err) {
                    err.printStackTrace();
                    this.close();
                };
                final JFrame loginPage = new LoginPage("SunFun Register", CloseEvents.EXIT_PROGRAM);
                loginPage.setVisible(true);
                RegisterPage.this.dispose();
            }
        });
        // Finish the window.
        this.buildWindow();
    }

    private boolean isDataValid() {
        final int nameLengthLimit = 36;
        final int passwordLengthLimit = 24;
        final int emailLengthLimit = 256;
        final int codiceFiscaleLength = 16;
        final int phoneNumberLength = 10;
        if (this.txtCodiceFiscale.getText().length() != codiceFiscaleLength) {
            return false;
        }
        if (this.txtName.getText().length() > nameLengthLimit || this.txtName.getText().length() < 2) {
            return false;
        }
        if (this.txtSurname.getText().length() > nameLengthLimit || this.txtSurname.getText().length() < 2) {
            return false;
        }
        if (this.txtPassword.getText().length() > passwordLengthLimit || this.txtPassword.getText().length() < 2) {
            return false;
        }
        if (!Objects.equals(this.txtPassword.getText(), this.txtPasswordConfirm.getText())) {
            return false;
        }
        if (this.txtEmail.getText().length() > emailLengthLimit || this.txtEmail.getText().length() < 6) {
            return false;
        }
        return this.txtPhone.getText().length() == phoneNumberLength;
    }
}
