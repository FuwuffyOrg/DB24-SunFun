package oop.sunfun.ui.parent;

import oop.sunfun.database.dao.ParentDAO;
import oop.sunfun.database.dao.PeriodDAO;
import oop.sunfun.database.data.admin.MembershipType;
import oop.sunfun.database.data.admin.PeriodData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.person.ParentData;
import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;
import oop.sunfun.ui.util.pages.FormPage;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public final class ParticipantMembershipPage extends FormPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Gestione Iscrizione ";

    /**
     * Components of the form used by the FormPage class.
     */
    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    private static final JComboBox<String> COMBO_PARENT;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        COMBO_PARENT = new JComboBox<>();
        FORM_COMPONENTS.put(new JLabel("Parente:"), new Pair<>(COMBO_PARENT, 0));
    }

    /**
     * The account data on the page.
     */
    private final AccountData accountData;

    /**
     * The participant data on the page.
     */
    private final ParticipantData participantData;

    public ParticipantMembershipPage(final CloseEvents closeEvent, final AccountData account,
                                     final ParticipantData participant) {
        super(PAGE_NAME + participant.name(), closeEvent, 1, FORM_COMPONENTS,
            () -> new ParticipantMembershipPage(CloseEvents.EXIT_PROGRAM, account, participant),
            () -> new ManageParticipantPage(closeEvent, account),
            () -> ParentDAO.addRitiroParente((String) COMBO_PARENT.getSelectedItem(), participant.codFisc()));
        this.accountData = account;
        this.participantData = participant;
        // Update parents
        COMBO_PARENT.removeAllItems();
        final Set<ParentData> parents = ParentDAO.getAllParents();
        parents.forEach(p -> COMBO_PARENT.addItem(p.codFisc()));
        // Add other components
        this.add(createPeriodPanel(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(createPickupPage(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(1)
                .setFillAll()
                .build()
        );
        // Finalize Window
        this.buildWindow();
    }

    private Component createPeriodPanel() {
        final JComponent periodPanel = new JPanel();
        periodPanel.setLayout(new GridBagLayout());
        final List<PeriodData> periods = PeriodDAO.getAllPeriods().stream().toList();
        // TODO: Set default values for the stuff in here to correspond to data in db
        IntStream.range(0, periods.size()).forEach(i -> {
            final PeriodData period = periods.get(i);
            final AbstractButton buttonUpdate = new JButton("Aggiorna iscrizione");
            final JCheckBox checkFood = new JCheckBox("Pasti");
            final JComboBox<MembershipType> comboMembership = new JComboBox<>();
            EnumSet.allOf(MembershipType.class).forEach(comboMembership::addItem);
            // Add the panel to the categories
            periodPanel.add(new JLabel(period.startDate().toString()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            periodPanel.add(new JLabel(period.endDate().toString()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            periodPanel.add(checkFood, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            periodPanel.add(comboMembership, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(3)
                    .setFillAll()
                    .build()
            );
            periodPanel.add(buttonUpdate, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(4)
                    .setFillAll()
                    .build()
            );
            periodPanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(5)
                    .setFillAll()
                    .build()
            );
            buttonUpdate.addActionListener(e -> {
                PeriodDAO.addOrUpdateMembership(participantData.codFisc(), checkFood.isSelected(), period,
                        (MembershipType) comboMembership.getSelectedItem());
                this.switchPage(new ParticipantMembershipPage(CloseEvents.EXIT_PROGRAM, this.accountData,
                        this.participantData));
            });
        });
        return new JScrollPane(periodPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private Component createPickupPage() {
        final JComponent pickupPanel = new JPanel();
        pickupPanel.setLayout(new GridBagLayout());
        final List<ParentData> parents = ParentDAO.getAllParentsFromParticipant(participantData.codFisc())
                .stream().toList();
        IntStream.range(0, parents.size()).forEach(i -> {
            final ParentData parent = parents.get(i);
            final AbstractButton btnDelete = new JButton("Annulla ritiro");
            // Add the panel to the categories
            pickupPanel.add(new JLabel(parent.name()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            pickupPanel.add(new JLabel(parent.surname()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            pickupPanel.add(btnDelete, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            pickupPanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(3)
                    .setFillAll()
                    .build()
            );
            btnDelete.addActionListener(e -> {
                ParentDAO.deleteRitiroParente(parent.codFisc(), participantData.codFisc());
                this.switchPage(new ParticipantMembershipPage(CloseEvents.EXIT_PROGRAM, this.accountData,
                        this.participantData));
            });
        });
        return new JScrollPane(pickupPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
