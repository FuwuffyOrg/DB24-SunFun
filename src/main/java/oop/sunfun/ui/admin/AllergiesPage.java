package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.FoodDAO;
import oop.sunfun.database.data.food.AllergenData;
import oop.sunfun.database.data.food.DietData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.FormPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class AllergiesPage extends FormPage {

    private static final String PAGE_NAME = "Gestione Allergie/Intolleranze";

    private final AccountData accountData;

    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    private static final JComponent TXT_NAME;
    private static final JComponent TXT_DESCRIPTION;
    private static final AbstractButton RADIO_DIET;
    private static final AbstractButton RADIO_ALLERGEN;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        TXT_NAME = new JTextField();
        TXT_DESCRIPTION = new JTextArea();
        RADIO_DIET = new JRadioButton();
        RADIO_ALLERGEN = new JRadioButton();
        final ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(RADIO_DIET);
        radioGroup.add(RADIO_ALLERGEN);
        FORM_COMPONENTS.put(new JLabel("Nome:"), new Pair<>(TXT_NAME, 30));
        FORM_COMPONENTS.put(new JLabel("Descrizione:"), new Pair<>(TXT_DESCRIPTION, 255));
        FORM_COMPONENTS.put(new JLabel("Dieta"), new Pair<>(RADIO_DIET, 0));
        FORM_COMPONENTS.put(new JLabel("Allergene"), new Pair<>(RADIO_ALLERGEN, 0));
    }

    public AllergiesPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent, 1, FORM_COMPONENTS,
                () -> new AllergiesPage(CloseEvents.EXIT_PROGRAM, account),
                () -> new LandingPage(CloseEvents.EXIT_PROGRAM, account),
                () -> {
                    if (RADIO_DIET.isSelected()) {
                        FoodDAO.createNewDiet(new DietData(((JTextComponent) TXT_NAME).getText(),
                                ((JTextComponent) TXT_DESCRIPTION).getText()));
                    } else if (RADIO_ALLERGEN.isSelected()) {
                        FoodDAO.createNewAllergen(new AllergenData(((JTextComponent) TXT_NAME).getText(),
                                ((JTextComponent) TXT_DESCRIPTION).getText()));
                    }
                });
        this.accountData = account;
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
        // Finalize the page
        this.buildWindow();
    }

    private Component createDietsPanel() {
        final JComponent listDietsPanel = new JPanel();
        listDietsPanel.setLayout(new GridBagLayout());
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
        return new JScrollPane(listDietsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private Component createSubstancesPanel() {
        final JComponent listSubstancesPanel = new JPanel();
        listSubstancesPanel.setLayout(new GridBagLayout());
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
        return new JScrollPane(listSubstancesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
