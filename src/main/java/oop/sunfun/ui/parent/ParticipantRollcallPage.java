package oop.sunfun.ui.parent;

import oop.sunfun.database.dao.ParticipantDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.util.Pair;
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
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public final class ParticipantRollcallPage extends GenericPage {

    private static final String PAGE_NAME = "Appello di ";

    private final ParticipantData participantData;

    public ParticipantRollcallPage(final CloseEvents closeEvent, final AccountData account,
                                   final ParticipantData participant) {
        super(PAGE_NAME + participant.name() + " " + participant.surname(), closeEvent);
        this.participantData = participant;
        // Create components
        final AbstractButton btnGoBack = new JButton("Torna alla dashboard");
        // Add them to the page
        this.add(getAllPresences(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(btnGoBack, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        btnGoBack.addActionListener(e -> this.switchPage(new ManageParticipantPage(CloseEvents.EXIT_PROGRAM, account)));
        // Finalize page
        this.buildWindow();
    }

    private Component getAllPresences() {
        // Create the panel
        final JComponent tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        // Get the groups
        final List<Date> dates = ParticipantDAO.getAllEnrolledDates(participantData.codFisc()).stream().toList();
        // Add the groups to the table
        IntStream.range(0, dates.size()).forEach(i -> {
            final Date date = dates.get(i);
            // Add them to the pane
            final AtomicReference<Pair<Boolean, Boolean>> presence =
                    new AtomicReference<>(new Pair<>(false, false));
            final Optional<Pair<Boolean, Boolean>> presenceData = ParticipantDAO.checkPresence(participantData.codFisc(),
                    date);
            presenceData.ifPresent(presence::set);
            tablePanel.add(new JLabel(date.toString()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JLabel("Entrato? " + presence.get().x()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JLabel("Uscito? " + presence.get().y()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setFillAll()
                    .setWidth(3)
                    .build()
            );
        });
        // Add the table to the panel
        return new JScrollPane(tablePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }
}
