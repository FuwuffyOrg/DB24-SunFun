package oop.sunfun.ui.parent;

import oop.sunfun.database.dao.AccountDAO;
import oop.sunfun.database.dao.ParentDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.login.AccountType;
import oop.sunfun.database.data.person.ParentType;
import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.FormPage;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class AddParticipantPage extends FormPage {

    private static final String PAGE_NAME = "Aggiungi un partecipante";

    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    private static final JComponent TXT_COD_FISC;
    private static final JComponent TXT_NAME;
    private static final JComponent TXT_SURNAME;
    private static final JComponent TXT_PHONE;
    private static final JComponent TXT_EMAIL;
    private static final JXDatePicker DATE_BIRTH;
    private static final JComponent TXT_PASSWORD;
    private static final JComponent TXT_PASSWORD_CONFIRM;
    private static final JComboBox<String> COMBO_PARENT_TYPE;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        TXT_COD_FISC = new JTextField();
        TXT_NAME = new JTextField();
        TXT_SURNAME = new JTextField();
        TXT_PHONE = new JTextField();
        TXT_EMAIL = new JTextField();
        DATE_BIRTH = new JXDatePicker();
        TXT_PASSWORD = new JPasswordField();
        TXT_PASSWORD_CONFIRM = new JPasswordField();
        COMBO_PARENT_TYPE = new JComboBox<>();
        EnumSet.allOf(ParentType.class).forEach(p -> COMBO_PARENT_TYPE.addItem(p.getTextValue()));
        FORM_COMPONENTS.put(new JLabel("Codice fiscale:"), new Pair<>(TXT_COD_FISC, 16));
        FORM_COMPONENTS.put(new JLabel("Nome:"), new Pair<>(TXT_NAME, 36));
        FORM_COMPONENTS.put(new JLabel("Cognome:"), new Pair<>(TXT_SURNAME, 36));
        FORM_COMPONENTS.put(new JLabel("Telefono:"), new Pair<>(TXT_PHONE, 10));
        FORM_COMPONENTS.put(new JLabel("Email:"), new Pair<>(TXT_EMAIL, 256));
        FORM_COMPONENTS.put(new JLabel("Password:"), new Pair<>(TXT_PASSWORD, 24));
        FORM_COMPONENTS.put(new JLabel("Conferma Password:"), new Pair<>(TXT_PASSWORD_CONFIRM, 24));
    }

    public AddParticipantPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent, 1, FORM_COMPONENTS,
                () -> new ManageParticipantPage(CloseEvents.EXIT_PROGRAM, account),
                () -> {
                    AccountDAO.createAccount(((JTextComponent) TXT_EMAIL).getText(),
                            ((JTextComponent) TXT_PASSWORD).getText(), AccountType.PARTECIPANTE);
                    ParentDAO.createParticipant(new ParticipantData(((JTextComponent) TXT_COD_FISC).getText(),
                            ((JTextComponent) TXT_EMAIL).getText(), Optional.empty(), Optional.empty(),
                            ((JTextComponent) TXT_NAME).getText(), ((JTextComponent) TXT_SURNAME).getText(),
                            DATE_BIRTH.getDate()));
                    ParentDAO.addRitiroParente(account.codFisc(), ((JTextComponent) TXT_COD_FISC).getText());
                });
        // Finalize the window
        this.buildWindow();
    }

    @Override
    protected boolean isDataValid() {
        return Objects.equals(((JTextComponent) TXT_PASSWORD).getText(),
                ((JTextComponent) TXT_PASSWORD_CONFIRM).getText()) && super.isDataValid();
    }
}
