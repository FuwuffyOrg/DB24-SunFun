package oop.sunfun.ui.parent;

import oop.sunfun.database.dao.AccountDAO;
import oop.sunfun.database.dao.ParentDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.login.AccountType;
import oop.sunfun.database.data.login.ParticipantData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public final class AddParticipantPage extends GenericPage {

    private static final String PAGE_NAME = "Aggiungi un partecipante";

    private final AccountData accountData;

    private final JTextComponent txtCodiceFiscale;
    private final JTextComponent txtName;
    private final JTextComponent txtSurname;
    private final JTextComponent txtEmail;
    private final JXDatePicker dateBirth;
    private final JTextComponent txtPassword;
    private final JTextComponent txtPasswordConfirm;

    public AddParticipantPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        final Component lblCodiceFiscale = new JLabel("Codice Fiscale: ");
        final Component lblName = new JLabel("Nome: ");
        final Component lblSurname = new JLabel("Cognome: ");
        final Component lblEmail = new JLabel("Email: ");
        final Component lblDateOfBirth = new JLabel("Data di Nascita: ");
        final Component lblPassword = new JLabel("Password: ");
        final Component lblPasswordConfirm = new JLabel("Conferma Password: ");
        this.txtCodiceFiscale = new JTextField();
        this.txtName = new JTextField();
        this.txtSurname = new JTextField();
        this.txtEmail = new JTextField();
        this.dateBirth = new JXDatePicker();
        this.txtPassword = new JPasswordField();
        this.txtPasswordConfirm = new JPasswordField();
        final AbstractButton btnAddParticipant = new JButton("Registra Partecipante");
        final AbstractButton btnDasbhoard = new JButton("Goto Dashboard");
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
        this.add(lblEmail,
                new GridBagConstraintBuilder()
                        .setRow(3).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(txtEmail,
                new GridBagConstraintBuilder()
                        .setRow(3).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.add(lblDateOfBirth,
                new GridBagConstraintBuilder()
                        .setRow(4).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(dateBirth,
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
        this.add(btnAddParticipant,
                new GridBagConstraintBuilder()
                        .setRow(7).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.add(btnDasbhoard,
                new GridBagConstraintBuilder()
                        .setRow(7).setColumn(1)
                        .setFillAll()
                        .build()
        );
        // Add events
        btnDasbhoard.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, account)));
        btnAddParticipant.addActionListener(e -> {
            if (isDataValid()) {
                AccountDAO.createAccount(this.txtEmail.getText(), this.txtPassword.getText(), AccountType.PARTECIPANTE);
                ParentDAO.createParticipant(new ParticipantData(this.txtCodiceFiscale.getText(),
                                Optional.empty(), Optional.empty(), this.txtName.getText(),
                                this.txtSurname.getText(), this.dateBirth.getDate()), this.txtEmail.getText());
                ParentDAO.addRitiroParente(account.codFisc(), this.txtCodiceFiscale.getText());
                this.switchPage(new ManageParticipantPage(CloseEvents.EXIT_PROGRAM, account));
            }
        });
        // Finalize the window
        this.buildWindow();
    }

    private boolean isDataValid() {
        final int minSize = 4;
        final int nameLengthLimit = 36;
        final int passwordLengthLimit = 24;
        final int emailLengthLimit = 256;
        final int codiceFiscaleLength = 16;
        this.resetHighlights();
        final String codiceFiscale = this.txtCodiceFiscale.getText();
        final String name = this.txtName.getText();
        final String surname = this.txtSurname.getText();
        final String email = this.txtEmail.getText();
        final String password = this.txtPassword.getText();
        final String passwordConfirm = this.txtPasswordConfirm.getText();
        final Date dateOfBirth = this.dateBirth.getDate();
        if (codiceFiscale.length() != codiceFiscaleLength) {
            GenericPage.highlightTextComponent(this.txtCodiceFiscale);
            return false;
        } else if (name.length() > nameLengthLimit || name.length() < minSize) {
            GenericPage.highlightTextComponent(this.txtName);
            return false;
        } else if (surname.length() > nameLengthLimit || surname.length() < minSize) {
            GenericPage.highlightTextComponent(this.txtSurname);
            return false;
        } else if (email.length() > emailLengthLimit || email.length() < minSize) {
            GenericPage.highlightTextComponent(this.txtEmail);
            return false;
        } else if (dateOfBirth == null) {
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
