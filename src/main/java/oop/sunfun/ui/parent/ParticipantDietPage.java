package oop.sunfun.ui.parent;

import oop.sunfun.database.dao.FoodDAO;
import oop.sunfun.database.dao.ParentDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.login.ParticipantData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public final class ParticipantDietPage extends GenericPage {

    private static final String PAGE_NAME = "Gestione Diete ";

    private final AccountData accountData;

    private final ParticipantData participantData;

    public ParticipantDietPage(final CloseEvents closeEvent, final AccountData account,
                               final ParticipantData participant) {
        super(PAGE_NAME + participant.name() + " " + participant.surname(), closeEvent);
        this.accountData = account;
        this.participantData = participant;
        // Create the components to add to the page
        final AbstractButton btnGoBack = new JButton("Torna ai partecipanti");
        // Add the two panels
        this.add(this.getAllergyPanel(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        );
        this.add(this.getDietPanel(), new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setWeightRow(0.02d)
                .setFillAll()
                .build()
        );
        this.add(btnGoBack, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setWeightColumn(0.02d)
                .setWeightRow(0.02d)
                .setFillAll()
                .build()
        );
        // Add button events
        btnGoBack.addActionListener(e -> this.switchPage(new ManageParticipantPage(CloseEvents.EXIT_PROGRAM, account)));
        // Finalize Window
        this.buildWindow();
    }

    private Component getDietPanel() {
        final JComponent dietsPanel = new JPanel();
        dietsPanel.setLayout(new GridBagLayout());
        // Create the combo box for the diet
        final JComboBox<String> comboDiet = new JComboBox<>();
        comboDiet.addItem(null);
        FoodDAO.getAllDiets().forEach(d -> comboDiet.addItem(d.name()));
        this.participantData.dieta().ifPresent(comboDiet::setSelectedItem);
        // Create the button to update the diet
        final AbstractButton btnUpdateDiet = new JButton("Aggiorna dieta");
        // Finalize the panel
        dietsPanel.add(comboDiet, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        dietsPanel.add(btnUpdateDiet, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        // Add button event
        btnUpdateDiet.addActionListener(e -> {
            final String diet = (String) comboDiet.getSelectedItem();
            ParentDAO.updateParticipantDiet(diet, this.participantData);
            final ParticipantData newParticipant = new ParticipantData(this.participantData.codiceFiscale(),
                    Optional.ofNullable(diet), this.participantData.group(), this.participantData.name(),
                    this.participantData.surname(), this.participantData.dateOfBirth());
            this.switchPage(new ParticipantDietPage(CloseEvents.EXIT_PROGRAM, this.accountData, newParticipant));
        });
        return dietsPanel;
    }

    private Component getAllergyPanel() {
        final JComponent allergyPanel = new JPanel();
        return allergyPanel;
    }
}
