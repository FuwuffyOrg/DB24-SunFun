package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.GroupDAO;
import oop.sunfun.database.dao.ParticipantDAO;
import oop.sunfun.database.data.admin.GroupData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;
import oop.sunfun.ui.util.pages.GenericPage;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupManagementPage extends GenericPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Gestione Gruppi";

    /**
     * The account data on the page.
     */
    private final AccountData accountData;

    /**
     * Creates a page to manage the group's participants.
     * @param closeEvent The event that happens when the page closes.
     * @param account The account that called this page.
     */
    public GroupManagementPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // Add stuff to page
        final AbstractButton btnGenerateGroups = new JButton("Genera I gruppi");
        final AbstractButton btnGoBack = new JButton("Torna alla dashboard");
        this.add(createTabs(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        );
        this.add(btnGoBack, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(btnGenerateGroups, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        // Set events
        btnGoBack.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, account)));
        btnGenerateGroups.addActionListener(e -> {
            // Get all the data for this to work
            final List<ParticipantData> participants = ParticipantDAO.getAllParticipants().stream().toList();
            final List<GroupData> groups = GroupDAO.getAllGroups().stream().toList();
            // Map the groups to have all the possible participants in it
            final Map<String, List<String>> possibleGroupParticipants = groups.stream().map(g -> {
                final List<String> possibleParticipants = List.copyOf(participants).stream()
                        .map(p -> {
                            // Get the dates in a calendar to get the age
                            final Calendar birthCalendar = Calendar.getInstance();
                            final Calendar today = Calendar.getInstance();
                            birthCalendar.setTime(p.dateOfBirth());
                            // Calculate age
                            int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
                            // Adjust age if the birthdate hasn't occurred yet this year
                            if (today.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH)
                                    || (today.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH)
                                    && today.get(Calendar.DAY_OF_MONTH)
                                    < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
                                age--;
                            }
                            return new Pair<>(p.codFisc(), age);
                        })
                        .filter(p -> p.y() >= g.minAge() && p.y() <= g.maxAge())
                        .map(Pair::x)
                        .collect(Collectors.toList());
                return new Pair<>(g.name(), possibleParticipants);
            }).collect(Collectors.toMap(Pair::x, Pair::y));
            // Start organizing the participants into groups
            IntStream.range(0, participants.size()).forEach(i -> {
                // Get the group and the participants of that group
                final String groupToChange = groups.get(i % possibleGroupParticipants.size()).name();
                final List<String> possibleParticipants = possibleGroupParticipants.get(groupToChange);
                // Add the participant to the group
                final Optional<String> participant = possibleParticipants.stream().findFirst();
                participant.ifPresent(p -> {
                    ParticipantDAO.updateParticipantGroup(p, groupToChange);
                    // Filter all participants to remove that one
                    possibleGroupParticipants.forEach((k, v) -> {
                        possibleGroupParticipants.put(k, v.stream().filter(s -> !Objects.equals(s, p)).toList());
                    });
                });
            });
            // Refresh the page
            this.switchPage(new GroupManagementPage(CloseEvents.EXIT_PROGRAM, account));
        });
        // Finalize the page
        this.buildWindow();
    }

    /**
     * Creates the tabs for all the groups in the db.
     * @return A pane with all the tabs of the groups.
     */
    private JComponent createTabs() {
        // Create the basic stuff for the query and display
        final JComponent pane = new JTabbedPane();
        final Set<GroupData> categories = GroupDAO.getAllGroups();
        for (final GroupData group : categories) {
            pane.add(this.createGroupPanel(group), group.name());
        }
        return pane;
    }

    /**
     * Creates a table with the group and all the participants that are in it.
     * @param group The group to check the participants from.
     * @return The table with all the participants of a group.
     */
    private Component createGroupPanel(final GroupData group) {
        // Create the stuff to display
        final JComponent panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        final Component scrollPanel = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Fetch all forum posts from that name
        final List<ParticipantData> participants = GroupDAO.getParticipantsFromGroup(group.name())
                .stream().toList();
        final Set<GroupData> groups = GroupDAO.getAllGroups();
        IntStream.range(0, participants.size()).forEach(i -> {
            // Add the panel to the categories
            final ParticipantData participant = participants.get(i);
            // Combo box to manually change group
            final JComboBox<String> comboGroup = new JComboBox<>();
            groups.forEach(g -> comboGroup.addItem(g.name()));
            participant.group().ifPresent(comboGroup::setSelectedItem);
            final AbstractButton btnUpdateGroup = new JButton("Aggiorna gruppo");
            // Add stuff to the panels
            panel.add(new JLabel(participant.name()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            panel.add(new JLabel(participant.surname()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            participant.dieta().ifPresent(d -> panel.add(new JLabel(d), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            ));
            panel.add(comboGroup, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(3)
                    .setFillAll()
                    .build()
            );
            panel.add(btnUpdateGroup, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(4)
                    .setFillAll()
                    .build()
            );
            panel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(5)
                    .setFillAll()
                    .build()
            );
            btnUpdateGroup.addActionListener(e -> {
                ParticipantDAO.updateParticipantGroup(participant.codFisc(),
                        (String) comboGroup.getSelectedItem());
                this.switchPage(new GroupManagementPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            });
        });
        return scrollPanel;
    }
}
