package oop.sunfun.ui.login;

import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.connection.SunFunDatabase;
import oop.sunfun.database.dao.AccountDAO;
import oop.sunfun.database.dao.ParentDAO;
import oop.sunfun.database.data.login.AccountType;
import oop.sunfun.database.data.person.ParentType;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import java.awt.Component;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RegisterPage extends GenericPage {
    private static final Logger LOGGER = Logger.getLogger(RegisterPage.class.getName());

    private static final String PAGE_NAME = "Register";

    private final JTextComponent txtCodiceFiscale;
    private final JTextComponent txtName;
    private final JTextComponent txtSurname;
    private final JTextComponent txtPhone;
    private final JTextComponent txtEmail;
    private final JTextComponent txtPassword;
    private final JTextComponent txtPasswordConfirm;
    private final JComboBox<String> comboParentType;

    public RegisterPage(final CloseEvents closeEvent) {
        super(PAGE_NAME, closeEvent);
        // Add two labels and text boxes for inputting username and password.
        final Component lblCodiceFiscale = new JLabel("Codice Fiscale: ");
        final Component lblName = new JLabel("Nome: ");
        final Component lblSurname = new JLabel("Cognome: ");
        final Component lblPhone = new JLabel("Numero di telefono: ");
        final Component lblEmail = new JLabel("Email: ");
        final Component lblPassword = new JLabel("Password: ");
        final Component lblPasswordConfirm = new JLabel("Conferma Password: ");
        final Component lblParentType = new JLabel("Grado di Parentela: ");
        this.txtCodiceFiscale = new JTextField();
        this.txtName = new JTextField();
        this.txtSurname = new JTextField();
        this.txtPhone = new JTextField();
        this.txtEmail = new JTextField();
        this.txtPassword = new JPasswordField();
        this.txtPasswordConfirm = new JPasswordField();
        this.comboParentType = new JComboBox<>();
        EnumSet.allOf(ParentType.class).forEach(p -> comboParentType.addItem(p.getTextValue()));
        final AbstractButton btnRegister = new JButton("Register");
        final AbstractButton btnLogin = new JButton("Goto Login");
        // Add all the components.
        this.add(lblCodiceFiscale,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(txtCodiceFiscale,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(1)
                        .setWeightColumn(0.1d)
                        .setFillAll()
                        .build()
        );
        this.add(lblName,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(txtName,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.add(lblSurname,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(txtSurname,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.add(lblPhone,
                new GridBagConstraintBuilder()
                        .setRow(3).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(txtPhone,
                new GridBagConstraintBuilder()
                        .setRow(3).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.add(lblEmail,
                new GridBagConstraintBuilder()
                        .setRow(4).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(txtEmail,
                new GridBagConstraintBuilder()
                        .setRow(4).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.add(lblPassword,
                new GridBagConstraintBuilder()
                        .setRow(5).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(txtPassword,
                new GridBagConstraintBuilder()
                        .setRow(5).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.add(lblPasswordConfirm,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(txtPasswordConfirm,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.add(lblParentType,
                new GridBagConstraintBuilder()
                        .setRow(7).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(comboParentType,
                new GridBagConstraintBuilder()
                        .setRow(7).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.add(btnRegister,
                new GridBagConstraintBuilder()
                        .setRow(8).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(btnLogin,
                new GridBagConstraintBuilder()
                        .setRow(8).setColumn(1)
                        .setFillAll()
                        .build()
        );
        // Add events
        btnLogin.addActionListener(e -> {
            this.switchPage(new LoginPage(CloseEvents.EXIT_PROGRAM));
        });
        btnRegister.addActionListener(e -> {
            if (isDataValid()) {
                final IDatabaseConnection database = SunFunDatabase.getDatabaseInstance();
                try {
                    database.openConnection();
                    AccountDAO.createAccount(this.txtEmail.getText(), this.txtPassword.getText(), AccountType.PARENTE);
                    ParentDAO.createParent(this.txtCodiceFiscale.getText(), this.txtEmail.getText(),
                            this.txtName.getText(), this.txtSurname.getText(), this.txtPhone.getText(),
                            ParentType.getFromString((String) this.comboParentType.getSelectedItem()));
                } catch (final SQLException err) {
                    LOGGER.log(Level.SEVERE, "Couldn't register the account data", err);
                    database.closeConnection();
                    this.close();
                }
                this.switchPage(new LoginPage(CloseEvents.EXIT_PROGRAM));
            }
        });
        // Finish the window.
        this.buildWindow();
    }

    private boolean isDataValid() {
        final int minSize = 4;
        final int nameLengthLimit = 36;
        final int passwordLengthLimit = 24;
        final int emailLengthLimit = 256;
        final int codiceFiscaleLength = 16;
        final int phoneNumberLength = 10;
        this.resetHighlights();
        final String codiceFiscale = this.txtCodiceFiscale.getText();
        final String name = this.txtName.getText();
        final String surname = this.txtSurname.getText();
        final String phoneNumber = this.txtPhone.getText();
        final String email = this.txtEmail.getText();
        final String password = this.txtPassword.getText();
        final String passwordConfirm = this.txtPasswordConfirm.getText();
        if (codiceFiscale.length() != codiceFiscaleLength) {
            GenericPage.highlightTextComponent(this.txtCodiceFiscale);
            return false;
        } else if (name.length() > nameLengthLimit || name.length() < minSize) {
            GenericPage.highlightTextComponent(this.txtName);
            return false;
        } else if (surname.length() > nameLengthLimit || surname.length() < minSize) {
            GenericPage.highlightTextComponent(this.txtSurname);
            return false;
        } else if (phoneNumber.length() != phoneNumberLength) {
            GenericPage.highlightTextComponent(this.txtPhone);
            return false;
        } else if (email.length() > emailLengthLimit || email.length() < minSize) {
            GenericPage.highlightTextComponent(this.txtEmail);
            return false;
        } else if (password.length() > passwordLengthLimit || password.length() < minSize) {
            GenericPage.highlightTextComponent(this.txtPassword);
            GenericPage.highlightTextComponent(this.txtPasswordConfirm);
            return false;
        } else if (!Objects.equals(password, passwordConfirm)) {
            GenericPage.highlightTextComponent(this.txtPassword);
            GenericPage.highlightTextComponent(this.txtPasswordConfirm);
            return false;
        }
        return true;
    }
}
