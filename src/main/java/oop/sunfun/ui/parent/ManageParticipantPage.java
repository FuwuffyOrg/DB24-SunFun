package oop.sunfun.ui.parent;

import oop.sunfun.database.dao.AccountDAO;
import oop.sunfun.database.dao.ParentDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.activity.GroupActivityViewPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import java.util.Optional;
import java.util.stream.IntStream;

public final class ManageParticipantPage extends GenericPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Gestione Partecipanti";

    /**
     * The account data on the page.
     */
    private final AccountData accountData;

    /**
     * Constructor for the participant management page for the parent.
     * @param closeEvent The event that happens when you close the page.
     * @param account The account that called this page.
     */
    public ManageParticipantPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        final AbstractButton btnDashboard = new JButton("Torna alla dashboard");
        final AbstractButton btnAddParticipant = new JButton("Aggiungi Partecipante");
        // Add the elements to the page
        this.add(this.createParticipantTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        );
        this.add(btnDashboard, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(btnAddParticipant, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        // Events
        btnDashboard.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, account)));
        btnAddParticipant.addActionListener(e -> this.switchPage(new AddParticipantPage(CloseEvents.EXIT_PROGRAM,
                account)));
        // Finalize the window
        this.buildWindow();
    }

    /**
     * Creates a table containing all the parent's participants.
     * @return The table with the participant's information.
     */
    private Component createParticipantTable() {
        final JComponent participantPanel = new JPanel();
        participantPanel.setLayout(new GridBagLayout());
        final List<ParticipantData> participants = ParentDAO.getAllParticipantsFromParent(accountData.codFisc())
                .stream().toList();
        IntStream.range(0, participants.size()).forEach(i -> {
            final ParticipantData participant = participants.get(i);
            final Component lblName = new JLabel(participant.name());
            final Component lblSurname = new JLabel(participant.surname());
            final AbstractButton btnRollcall = new JButton("Appello");
            final AbstractButton btnUnsubscribe = new JButton("Annulla iscrizione");
            final AbstractButton btnDiets = new JButton("Diete");
            final AbstractButton btnGroupActivities = new JButton("Attivitá");
            final AbstractButton btnMembership = new JButton("Iscrizione");
            // Add them to the panel
            participantPanel.add(lblName, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            participantPanel.add(lblSurname, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            participantPanel.add(btnRollcall, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            participantPanel.add(btnDiets, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(3)
                    .setFillAll()
                    .build()
            );
            participantPanel.add(btnMembership, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(4)
                    .setFillAll()
                    .build()
            );
            participantPanel.add(btnGroupActivities, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(4)
                    .setFillAll()
                    .build()
            );
            participantPanel.add(btnUnsubscribe, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(5)
                    .setFillAll()
                    .build()
            );
            participantPanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(6)
                    .setFillAll()
                    .build()
            );
            // Add delete event
            btnRollcall.addActionListener(e -> this.switchPage(new ParticipantRollcallPage(CloseEvents.EXIT_PROGRAM,
                    this.accountData, participant)));
            btnDiets.addActionListener(e -> this.switchPage(new ParticipantDietPage(CloseEvents.EXIT_PROGRAM,
                    this.accountData, participant)));
            btnMembership.addActionListener(e -> this.switchPage(new ParticipantMembershipPage(CloseEvents.EXIT_PROGRAM,
                    this.accountData, participant)));
            btnGroupActivities.setEnabled(false);
            participant.group().ifPresent(v -> btnGroupActivities.addActionListener(e ->
                    this.switchPage(new GroupActivityViewPage(CloseEvents.EXIT_PROGRAM, this.accountData, v,
                            Optional.empty()))));
            btnUnsubscribe.addActionListener(e -> {
                AccountDAO.eraseAccount(participant.accountEmail());
                this.switchPage(new ManageParticipantPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            });
        });
        return new JScrollPane(participantPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
