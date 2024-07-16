package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.FoodDAO;
import oop.sunfun.database.data.food.AllergenData;
import oop.sunfun.database.data.food.DietData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.stream.IntStream;

// TODO: Make into form
public final class AllergiesPage extends GenericPage {

    private static final String PAGE_NAME = "Gestione Allergie/Intolleranze";

    private final AccountData accountData;

    private final JTextComponent txtName;

    private final JTextComponent txtDescription;

    public AllergiesPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // Create the elements to create a new food item
        final Component lblName = new JLabel("Nome:");
        final Component lblDescription = new JLabel("Descrizione:");
        final AbstractButton btnAddDiet = new JButton("Aggiungi come dieta");
        final AbstractButton btnAddAllergen = new JButton("Aggiungi come allergene");
        final AbstractButton btnDashboard = new JButton("Torna alla dashboard");
        this.txtName = new JTextField();
        this.txtDescription = new JTextField();
        // Add the panels to the page
        this.add(createDietsPanel(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(createSubstancesPanel(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(lblName, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(this.txtName, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(lblDescription, new GridBagConstraintBuilder()
                .setRow(2).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(this.txtDescription, new GridBagConstraintBuilder()
                .setRow(2).setColumn(1)
                .setFillAll()
                .build()
        );
        final JComponent btnPanel = new JPanel();
        btnPanel.setLayout(new GridBagLayout());
        btnPanel.add(btnAddDiet, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        btnPanel.add(btnDashboard, new GridBagConstraintBuilder()
                .setRow(0).setColumn(1)
                .setFillAll()
                .build()
        );
        btnPanel.add(btnAddAllergen, new GridBagConstraintBuilder()
                .setRow(0).setColumn(2)
                .setFillAll()
                .build()
        );
        this.add(btnPanel, new GridBagConstraintBuilder()
                .setRow(3).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        );
        // Add the events
        btnDashboard.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, account)));
        btnAddDiet.addActionListener(e -> {
            if (isDataValid()) {
                FoodDAO.createNewDiet(new DietData(txtName.getText(), txtDescription.getText()));
                this.switchPage(new AllergiesPage(CloseEvents.EXIT_PROGRAM, account));
            }
        });
        btnAddAllergen.addActionListener(e -> {
            if (isDataValid()) {
                FoodDAO.createNewAllergen(new AllergenData(txtName.getText(), txtDescription.getText()));
                this.switchPage(new AllergiesPage(CloseEvents.EXIT_PROGRAM, account));
            }
        });
        // Finalize the page
        this.buildWindow();
    }

    private boolean isDataValid() {
        final int nameLengthLimit = 30;
        final int descriptionLengthLimit = 255;
        final int minSize = 4;
        this.resetHighlights();
        final String name = txtName.getText();
        final String description = txtDescription.getText();
        if (name.length() > nameLengthLimit || name.length() < minSize) {
            highlightTextComponent(txtName);
            return false;
        } else if (description.length() > descriptionLengthLimit || description.length() < minSize) {
            highlightTextComponent(txtDescription);
            return false;
        }
        return true;
    }

    private Component createDietsPanel() {
        final JComponent dietsPanel = new JPanel();
        final JComponent listDietsPanel = new JPanel();
        final JComponent addDietPanel = new JPanel();
        dietsPanel.setLayout(new GridBagLayout());
        listDietsPanel.setLayout(new GridBagLayout());
        addDietPanel.setLayout(new GridBagLayout());
        // Get all the diets for the diet panel
        final List<DietData> diets = FoodDAO.getAllDiets().stream().toList();
        IntStream.range(0, diets.size()).forEach(i -> {
            final DietData diet = diets.get(i);
            // Add the components to the panel
            final Component lblDietName = new JLabel(diet.name());
            final Component lblDietDescription = new JLabel(diet.description());
            final AbstractButton btnDeleteDiet = new JButton("Delete");
            listDietsPanel.add(lblDietName, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            listDietsPanel.add(lblDietDescription, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            listDietsPanel.add(btnDeleteDiet, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            listDietsPanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(3)
                    .setFillAll()
                    .build()
            );
            // Add delete action
            btnDeleteDiet.addActionListener(e -> {
                FoodDAO.deleteDiet(diet);
                this.switchPage(new AllergiesPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            });
        });
        // Populate the panel
        dietsPanel.add(new JScrollPane(listDietsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        dietsPanel.add(addDietPanel, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        return dietsPanel;
    }

    private Component createSubstancesPanel() {
        final JComponent substancesPanel = new JPanel();
        final JComponent listSubstancesPanel = new JPanel();
        final JComponent addSubstancePanel = new JPanel();
        substancesPanel.setLayout(new GridBagLayout());
        listSubstancesPanel.setLayout(new GridBagLayout());
        addSubstancePanel.setLayout(new GridBagLayout());
        // Get all the diets for the diet panel
        final List<AllergenData> allergens = FoodDAO.getAllAllergens().stream().toList();
        IntStream.range(0, allergens.size()).forEach(i -> {
            final AllergenData allergen = allergens.get(i);
            // Add the components to the panel
            final Component lblAllergenName = new JLabel(allergen.name());
            final Component lblAllergenDescription = new JLabel(allergen.description());
            final AbstractButton btnDeleteAllergen = new JButton("Delete");
            listSubstancesPanel.add(lblAllergenName, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            listSubstancesPanel.add(lblAllergenDescription, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            listSubstancesPanel.add(btnDeleteAllergen, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            listSubstancesPanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(3)
                    .setFillAll()
                    .build()
            );
            // Add delete action
            btnDeleteAllergen.addActionListener(e -> {
                FoodDAO.deleteAllergen(allergen);
                this.switchPage(new AllergiesPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            });
        });
        // Populate the panel
        substancesPanel.add(new JScrollPane(listSubstancesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        substancesPanel.add(addSubstancePanel, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        return substancesPanel;
    }
}
