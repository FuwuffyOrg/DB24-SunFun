package oop.sunfun.ui.parent;

import oop.sunfun.database.dao.FoodDAO;
import oop.sunfun.database.dao.ParentDAO;
import oop.sunfun.database.data.food.AllergenData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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
        // Label for the combo
        final Component lblDiet = new JLabel("Dieta: ");
        // Create the button to update the diet
        final AbstractButton btnUpdateDiet = new JButton("Aggiorna dieta");
        // Finalize the panel
        dietsPanel.add(lblDiet, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        dietsPanel.add(comboDiet, new GridBagConstraintBuilder()
                .setRow(0).setColumn(1)
                .setFillAll()
                .build()
        );
        dietsPanel.add(btnUpdateDiet, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        );
        // Add button event
        btnUpdateDiet.addActionListener(e -> {
            final String diet = (String) comboDiet.getSelectedItem();
            ParentDAO.updateParticipantDiet(diet, this.participantData);
            final ParticipantData newParticipant = new ParticipantData(this.participantData.codiceFiscale(),
                    this.participantData.accountEmail(), Optional.ofNullable(diet), this.participantData.group(),
                    this.participantData.name(), this.participantData.surname(), this.participantData.dateOfBirth());
            this.switchPage(new ParticipantDietPage(CloseEvents.EXIT_PROGRAM, this.accountData, newParticipant));
        });
        return dietsPanel;
    }

    private Component getAllergyPanel() {
        final JComponent allergyPanel = new JPanel();
        allergyPanel.setLayout(new GridBagLayout());
        final List<AllergenData> currentAllergens = FoodDAO.getAllAllergensOfParticipant(
                participantData.codiceFiscale()).stream().toList();
        // Add the allergens to the table
        final JComponent currentAllergyPanel = new JPanel();
        currentAllergyPanel.setLayout(new GridBagLayout());
        IntStream.range(0, currentAllergens.size()).forEach(i -> {
            final AllergenData allergen = currentAllergens.get(i);
            final Component lblName = new JLabel(allergen.name());
            final Component lblDescription = new JLabel(String.valueOf(allergen.description()));
            final AbstractButton btnDeleteIntolerance = new JButton("Rimuovi Intolleranza");
            // Add them to the panel
            currentAllergyPanel.add(lblName, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            currentAllergyPanel.add(lblDescription, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            currentAllergyPanel.add(btnDeleteIntolerance, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            currentAllergyPanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(3)
                    .setFillAll()
                    .build()
            );
            // Add delete event
            btnDeleteIntolerance.addActionListener(e -> {
                FoodDAO.deleteAllergenFromParticipant(allergen.name(), this.participantData.codiceFiscale());
                this.switchPage(new ParticipantDietPage(CloseEvents.EXIT_PROGRAM, this.accountData,
                        this.participantData));
            });
        });
        // Create list of current allergens
        final Component currentAllergiesScroll = new JScrollPane(currentAllergyPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Create all allergen combo box
        final Component lblAllergen = new JLabel("Allergene:");
        final List<AllergenData> allergens = FoodDAO.getAllAllergens().stream().toList();
        final JComboBox<String> comboAllergen = new JComboBox<>();
        allergens.forEach(a -> comboAllergen.addItem(a.name()));
        final AbstractButton btnAddAllergen = new JButton("Aggiungi intolleranza");
        // Add the components to the page
        allergyPanel.add(currentAllergiesScroll, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(3)
                .setFillAll()
                .build()
        );
        allergyPanel.add(lblAllergen, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        allergyPanel.add(comboAllergen, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        allergyPanel.add(comboAllergen, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        allergyPanel.add(btnAddAllergen, new GridBagConstraintBuilder()
                .setRow(1).setColumn(2)
                .setWeightColumn(0.0075d)
                .setFillAll()
                .build()
        );
        btnAddAllergen.addActionListener(e -> {
            FoodDAO.createAllergenForParticipant((String) comboAllergen.getSelectedItem(),
                    this.participantData.codiceFiscale());
            this.switchPage(new ParticipantDietPage(CloseEvents.EXIT_PROGRAM, this.accountData, this.participantData));
        });
        return allergyPanel;
    }
}
