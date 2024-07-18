package oop.sunfun.ui.parent;

import oop.sunfun.database.dao.ParentDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.person.ParentData;
import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.FormPage;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Component;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class ParticipantMembershipPage extends FormPage {

    private static final String PAGE_NAME = "Gestione Iscrizione ";

    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    private static final JComboBox<String> COMBO_PARENT;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        COMBO_PARENT = new JComboBox<>();
        FORM_COMPONENTS.put(new JLabel("Parente:"), new Pair<>(COMBO_PARENT, 0));
    }

    public ParticipantMembershipPage(final CloseEvents closeEvent, final AccountData account,
                                     final ParticipantData participant) {
        super(PAGE_NAME + participant.name(), closeEvent, 1, FORM_COMPONENTS,
            () -> new ParticipantMembershipPage(CloseEvents.EXIT_PROGRAM, account, participant),
            () -> new ManageParticipantPage(closeEvent, account),
            () -> {
                // TODO: Add parent to ritiro
            });
        // Update parents
        COMBO_PARENT.removeAllItems();
        final Set<ParentData> parents = ParentDAO.getAllParents();
        parents.forEach(p -> COMBO_PARENT.addItem(p.nome() + " " + p.surname()));
        // TODO: Add panels to check ritiro and periodi iscritti
        // Finalize Window
        this.buildWindow();
    }
}
