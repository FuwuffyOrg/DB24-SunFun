package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.GroupDAO;
import oop.sunfun.database.data.admin.GroupData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;
import oop.sunfun.ui.util.pages.GenericPage;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class GroupManagementPage extends GenericPage {

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
