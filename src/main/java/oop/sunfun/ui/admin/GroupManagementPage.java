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

import javax.swing.*;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.time.Period;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupManagementPage extends GenericPage {
    private static final Logger LOGGER = Logger.getLogger(GroupManagementPage.class.getName());

    private static final String PAGE_NAME = "Gestione Gruppi";

    public GroupManagementPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
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
            final Set<ParticipantData> participants = ParticipantDAO.getAllParticipants();
            final Set<GroupData> groups = GroupDAO.getAllGroups();
            final Set<Pair<String, Integer>> participantsAge = participants.stream().map(p -> {
                // Calculating the age of someone is pain
                final Calendar birthDateCalendar = Calendar.getInstance();
                birthDateCalendar.setTime(p.dateOfBirth());
                final Calendar currentDateCalendar = Calendar.getInstance();
                currentDateCalendar.setTime(new Date());
                int age = currentDateCalendar.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR);
                if (currentDateCalendar.get(Calendar.MONTH) < birthDateCalendar.get(Calendar.MONTH) ||
                        (currentDateCalendar.get(Calendar.MONTH) == birthDateCalendar.get(Calendar.MONTH) &&
                                currentDateCalendar.get(Calendar.DAY_OF_MONTH) <
                                        birthDateCalendar.get(Calendar.DAY_OF_MONTH))) {
                    age--;
                }
                return new Pair<>(p.codFisc(), age);
            }).collect(Collectors.toSet());
            // Map the groups to possible participants
            final Map<GroupData, Set<Pair<String, Integer>>> groupsToParticipants = groups.stream()
                    .collect(Collectors.toMap(
                            gr -> gr,
                            gr -> participantsAge.stream()
                                    .filter(p -> gr.maxAge() <= p.y() && gr.minAge() >= p.y())
                                    .collect(Collectors.toSet())
                    ));
            // TODO: Finish or redo this method fully
        });
        // Finalize the page
        this.buildWindow();
    }

    private JComponent createTabs() {
        // Create the basic stuff for the query and display
        final JComponent pane = new JTabbedPane();
        final Set<GroupData> categories = GroupDAO.getAllGroups();
        for (final GroupData group : categories) {
            pane.add(this.createGroupPanel(group), group.name());
        }
        return pane;
    }

    private Component createGroupPanel(final GroupData group) {
        // Create the stuff to display
        final JComponent panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        final Component scrollPanel = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Fetch all forum posts from that name
        final List<ParticipantData> participants = GroupDAO.getParticipantsFromGroup(group.name())
                .stream().toList();
        IntStream.range(0, participants.size()).forEach(i -> {
            // Add the panel to the categories
            final ParticipantData participant = participants.get(i);
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
            panel.add(new JLabel(participant.group().get()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            panel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setFillAll()
                    .build()
            );
        });
        return scrollPanel;
    }
}
