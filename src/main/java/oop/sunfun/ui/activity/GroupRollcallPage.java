package oop.sunfun.ui.activity;

import oop.sunfun.database.dao.GroupDAO;
import oop.sunfun.database.dao.ParticipantDAO;
import oop.sunfun.database.dao.PeriodDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.LandingPage;
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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public final class GroupRollcallPage extends FormPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Appello del Gruppo ";

    /**
     * Components of the form used by the FormPage class.
     */
    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    /**
     * Combobox to choose the date on which to do the roll call.
     */
    private static final JComboBox<Date> COMBO_DATE;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        COMBO_DATE = new JComboBox<>();
        FORM_COMPONENTS.put(new JLabel("Data:"), new Pair<>(COMBO_DATE, 0));
    }

    /**
     * The account data on the page.
     */
    private final AccountData accountData;

    /**
     * Constructor for the group rollcall page.
     * @param closeEvent The event that happens when you close the page.
     * @param account The account that called this page.
     * @param groupName The group that needs to be roll called.
     * @param date A valid optional if a date has been selected for this page.
     */
    public GroupRollcallPage(final CloseEvents closeEvent, final AccountData account,
                             final String groupName, final Optional<Date> date) {
        super(PAGE_NAME + groupName, closeEvent, 1, FORM_COMPONENTS,
                () -> new GroupRollcallPage(CloseEvents.EXIT_PROGRAM, account, groupName,
                        Optional.ofNullable((Date) COMBO_DATE.getSelectedItem())),
                () -> new LandingPage(CloseEvents.EXIT_PROGRAM, account),
                () -> {

                });
        this.accountData = account;
        // Update the dates in the combo box
        PeriodDAO.getAllDates().forEach(COMBO_DATE::addItem);
        // Add the roll call table
        date.ifPresent(d ->
            this.add(getRollCallTable(d, groupName), new GridBagConstraintBuilder()
                    .setRow(0).setColumn(0)
                    .setWidth(2)
                    .setFillAll()
                    .build()
            )
        );
        // Finalize the window
        this.buildWindow();
    }

    /**
     * Creates a table with the rollcall information.
     * @param d The date on which to do the rollcall.
     * @param group The group on which to check the rollcall.
     * @return A table with rollcall information.
     */
    private Component getRollCallTable(final Date d, final String group) {
        // Create the panel
        final JComponent tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        // Get the
        final List<ParticipantData> participants = GroupDAO.getParticipantsFromGroup(group).stream().toList();
        // Add the groups to the table
        IntStream.range(0, participants.size()).forEach(i -> {
            final ParticipantData participant = participants.get(i);
            // Check for the current presence value
            final Optional<Pair<Boolean, Boolean>> currentPresence = ParticipantDAO.checkPresence(participant.codFisc(), d);
            // Add everything to the page
            final AbstractButton checkEntry = new JCheckBox("Entrata");
            final AbstractButton checkExit = new JCheckBox("Uscita");
            final AbstractButton btnUpdatePresence = new JButton("Aggiorna Presenza");
            currentPresence.ifPresent(p -> {
                checkEntry.setSelected(p.x());
                checkExit.setSelected(p.y());
            });
            // Add them to the panel
            tablePanel.add(new JLabel(participant.name()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JLabel(participant.surname()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(checkEntry, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(checkExit, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(3)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(btnUpdatePresence, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(4)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(5)
                    .setFillAll()
                    .build()
            );
            // Add delete event
            btnUpdatePresence.addActionListener(e -> {
                GroupDAO.addOrUpdatePresence(participant.codFisc(), d, checkEntry.isSelected(),
                        checkExit.isSelected());
                this.switchPage(new GroupRollcallPage(CloseEvents.EXIT_PROGRAM, accountData, group, Optional.of(d)));
            });
        });
        // Add the table to the panel
        return new JScrollPane(tablePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }
}
