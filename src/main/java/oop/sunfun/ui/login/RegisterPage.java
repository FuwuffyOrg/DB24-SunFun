package oop.sunfun.ui.login;

import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.connection.SunFunDatabase;
import oop.sunfun.database.data.enums.ParentType;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

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
        this.addPanelComponent(lblCodiceFiscale,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(0)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtCodiceFiscale,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(1)
                        .setWeightColumn(0.1d)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblName,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(0)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtName,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(1)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblSurname,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(0)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtSurname,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(1)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblPhone,
                new GridBagConstraintBuilder()
                        .setRow(3).setColumn(0)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtPhone,
                new GridBagConstraintBuilder()
                        .setRow(3).setColumn(1)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblEmail,
                new GridBagConstraintBuilder()
                        .setRow(4).setColumn(0)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtEmail,
                new GridBagConstraintBuilder()
                        .setRow(4).setColumn(1)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblPassword,
                new GridBagConstraintBuilder()
                        .setRow(5).setColumn(0)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtPassword,
                new GridBagConstraintBuilder()
                        .setRow(5).setColumn(1)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblPasswordConfirm,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(0)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtPasswordConfirm,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(1)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblParentType,
                new GridBagConstraintBuilder()
                        .setRow(7).setColumn(0)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(comboParentType,
                new GridBagConstraintBuilder()
                        .setRow(7).setColumn(1)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(btnRegister,
                new GridBagConstraintBuilder()
                        .setRow(8).setColumn(0)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(btnLogin,
                new GridBagConstraintBuilder()
                        .setRow(8).setColumn(1)
                        .setWidth(1)
                        .setFillAll()
                        .build()
        );
        // Add events
        btnLogin.addActionListener(e -> {
            this.switchPage(new LoginPage(CloseEvents.EXIT_PROGRAM));
        });
        btnRegister.addActionListener(e -> {
            if (RegisterPage.this.isDataValid()) {
                final String accountQuery = "INSERT INTO `account`(`email`, `password`, `tipologia`) "
                        + "VALUES (?,?,'Parente')";

                final String parenteQuery = "INSERT INTO `parente`(`codice_fiscale`, `fk_account`, "
                        + "`nome`, `cognome`, `cellulare`, `grado_di_parentela`) VALUES (?,?,?,?,?,?)";

                final IDatabaseConnection database = SunFunDatabase.getDatabaseInstance();
                try {
                    database.openConnection();
                    database.setQueryData(accountQuery, this.txtEmail.getText(), this.txtPassword.getText());
                    database.setQueryData(parenteQuery, this.txtCodiceFiscale.getText(), this.txtEmail.getText(),
                            this.txtName.getText(), this.txtSurname.getText(), this.txtPhone.getText(),
                            this.comboParentType.getSelectedItem());
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
        if (this.txtCodiceFiscale.getText().length() != codiceFiscaleLength) {
            GenericPage.highlightTextComponent(this.txtCodiceFiscale);
            return false;
        }
        if (this.txtName.getText().length() > nameLengthLimit || this.txtName.getText().length() < minSize) {
            GenericPage.highlightTextComponent(this.txtName);
            return false;
        }
        if (this.txtSurname.getText().length() > nameLengthLimit || this.txtSurname.getText().length() < minSize) {
            GenericPage.highlightTextComponent(this.txtSurname);
            return false;
        }
        if (this.txtPhone.getText().length() != phoneNumberLength) {
            GenericPage.highlightTextComponent(this.txtPhone);
            return false;
        }
        if (this.txtEmail.getText().length() > emailLengthLimit || this.txtEmail.getText().length() < minSize) {
            GenericPage.highlightTextComponent(this.txtEmail);
            return false;
        }
        if (this.txtPassword.getText().length() > passwordLengthLimit
                || this.txtPassword.getText().length() < minSize) {
            GenericPage.highlightTextComponent(this.txtPassword);
            GenericPage.highlightTextComponent(this.txtPasswordConfirm);
            return false;
        }
        if (!Objects.equals(this.txtPassword.getText(), this.txtPasswordConfirm.getText())) {
            GenericPage.highlightTextComponent(this.txtPassword);
            GenericPage.highlightTextComponent(this.txtPasswordConfirm);
            return false;
        }
        return true;
    }
}