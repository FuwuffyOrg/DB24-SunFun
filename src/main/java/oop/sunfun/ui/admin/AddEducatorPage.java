package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.AccountDAO;
import oop.sunfun.database.dao.EducatorDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.login.AccountType;
import oop.sunfun.database.data.person.EducatorData;
import oop.sunfun.database.data.person.VoluntaryData;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.FormPage;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class AddEducatorPage extends FormPage {

    private static final String PAGE_NAME = "Aggiunta Educatori";

    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    private static final JComponent TXT_COD_FISC;
    private static final JComponent TXT_NAME;
    private static final JComponent TXT_SURNAME;
    private static final JComponent TXT_PHONE;
    private static final JComponent TXT_EMAIL;
    private static final JComponent TXT_PASSWORD;
    private static final JComponent TXT_PASSWORD_CONFIRM;
    private static final AbstractButton RADIO_EDUCATOR;
    private static final AbstractButton RADIO_VOLUNTARY;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        TXT_COD_FISC = new JTextField();
        TXT_NAME = new JTextField();
        TXT_SURNAME = new JTextField();
        TXT_PHONE = new JTextField();
        TXT_EMAIL = new JTextField();
        TXT_PASSWORD = new JPasswordField();
        TXT_PASSWORD_CONFIRM = new JPasswordField();
        RADIO_EDUCATOR = new JRadioButton("Educatore");
        RADIO_VOLUNTARY = new JRadioButton("Volontario");
        final ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(RADIO_EDUCATOR);
        radioGroup.add(RADIO_VOLUNTARY);
        FORM_COMPONENTS.put(new JLabel("Codice fiscale:"), new Pair<>(TXT_COD_FISC, 16));
        FORM_COMPONENTS.put(new JLabel("Nome:"), new Pair<>(TXT_NAME, 36));
        FORM_COMPONENTS.put(new JLabel("Cognome:"), new Pair<>(TXT_SURNAME, 36));
        FORM_COMPONENTS.put(new JLabel("Telefono:"), new Pair<>(TXT_PHONE, 10));
        FORM_COMPONENTS.put(new JLabel("Email:"), new Pair<>(TXT_EMAIL, 256));
        FORM_COMPONENTS.put(new JLabel(""), new Pair<>(RADIO_EDUCATOR, 0));
        FORM_COMPONENTS.put(new JLabel(""), new Pair<>(RADIO_VOLUNTARY, 0));
        FORM_COMPONENTS.put(new JLabel("Password:"), new Pair<>(TXT_PASSWORD, 24));
        FORM_COMPONENTS.put(new JLabel("Conferma Password:"), new Pair<>(TXT_PASSWORD_CONFIRM, 24));
    }

    public AddEducatorPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent, FORM_COMPONENTS,
                () -> new EducatorPage(CloseEvents.EXIT_PROGRAM, account),
                () -> {
                    if (RADIO_EDUCATOR.isSelected()) {
                        AccountDAO.createAccount(((JTextComponent) TXT_EMAIL).getText(),
                                ((JTextComponent) TXT_PASSWORD).getText(), AccountType.EDUCATORE);
                        EducatorDAO.createEducator(new EducatorData(((JTextComponent) TXT_COD_FISC).getText(),
                                ((JTextComponent) TXT_NAME).getText(), ((JTextComponent) TXT_SURNAME).getText(),
                                ((JTextComponent) TXT_EMAIL).getText(), ((JTextComponent) TXT_PHONE).getText(),
                                Optional.empty()));
                    } else if (RADIO_VOLUNTARY.isSelected()) {
                        AccountDAO.createAccount(((JTextComponent) TXT_EMAIL).getText(),
                                ((JTextComponent) TXT_PASSWORD).getText(), AccountType.VOLONTARIO);
                        EducatorDAO.createVoluntary(new VoluntaryData(((JTextComponent) TXT_COD_FISC).getText(),
                                ((JTextComponent) TXT_EMAIL).getText(), ((JTextComponent) TXT_NAME).getText(),
                                ((JTextComponent) TXT_SURNAME).getText()));
                    }
                });
        // Finalize the window
        this.buildWindow();
    }
}
