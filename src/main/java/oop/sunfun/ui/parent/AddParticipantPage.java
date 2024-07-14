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
                // TODO: add diets and groups???
                AccountDAO.createAccount(this.txtEmail.getText(), this.txtPassword.getText(), AccountType.PARTECIPANTE);
                ParentDAO.createParticipant(new ParticipantData(this.txtCodiceFiscale.getText(), "", "",
                        this.txtName.getText(), this.txtSurname.getText(), this.dateBirth.getDate()),
                        this.txtEmail.getText());
                ParentDAO.addRitiroParente(this.txtCodiceFiscale.getText(), account.codFisc());
                this.switchPage(new ManageParticipantPage(CloseEvents.EXIT_PROGRAM, account));
            }
        });
        // Finalize the window
        this.buildWindow();
    }

    private boolean isDataValid() {
        // TODO: Complete this method
        return true;
    }
}
