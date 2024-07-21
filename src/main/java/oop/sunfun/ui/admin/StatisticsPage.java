package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.StatisticsDAO;
import oop.sunfun.database.data.food.AllergenData;
import oop.sunfun.database.data.food.DietData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
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
import java.util.List;
import java.util.stream.IntStream;

public class StatisticsPage extends GenericPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Pagina delle Statistiche";

    /**
     * Constructor for the page that displays some statistics about the camp.
     * @param closeEvent The event that happens when you close the window.
     * @param account The account used to call this window.
     */
    public StatisticsPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        final AbstractButton btnGoBack = new JButton("Torna alla dashboard");
        this.add(new JLabel("Diete piu seguite:"), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        );
        this.add(getDietsSection(), new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        );
        this.add(new JLabel("Discussioni piu popolari:"), new GridBagConstraintBuilder()
                .setRow(2).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(getDiscussionsSection(), new GridBagConstraintBuilder()
                .setRow(3).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(new JLabel("Intolleranze piu popolari:"), new GridBagConstraintBuilder()
                .setRow(2).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(getAllergensSection(), new GridBagConstraintBuilder()
                .setRow(3).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(getActivitySection(), new GridBagConstraintBuilder()
                .setRow(4).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(btnGoBack, new GridBagConstraintBuilder()
                .setRow(4).setColumn(1)
                .setFillAll()
                .build()
        );
        btnGoBack.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, account)));
        // Finalize the page
        this.buildWindow();
    }

    /**
     * Creates a component with the most popular discussions.
     * @return The component with the discussions.
     */
    private Component getDiscussionsSection() {
        final JComponent panelDiscussions = new JPanel();
        panelDiscussions.setLayout(new GridBagLayout());
        // Get the most popular discussions
        final List<Pair<String, Integer>> educators = StatisticsDAO.getMostPopularForumPosts().stream().toList();
        IntStream.range(0, educators.size()).forEach(i -> {
            final Pair<String, Integer> educator = educators.get(i);
            panelDiscussions.add(new JLabel(educator.x()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            panelDiscussions.add(new JLabel(String.valueOf(educator.y())), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            panelDiscussions.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i + 1) * 2).setColumn(0)
                    .setWidth(2)
                    .setFillAll()
                    .build()
            );
        });
        return new JScrollPane(panelDiscussions, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Creates a component with the allergens that affect most people.
     * @return The component with the allergens.
     */
    private Component getAllergensSection() {
        final JComponent panelAllergens = new JPanel();
        panelAllergens.setLayout(new GridBagLayout());
        // Get the most popular discussions
        final List<Pair<AllergenData, Integer>> allergens = StatisticsDAO.getMostAllergenicAllergens()
                .stream().toList();
        IntStream.range(0, allergens.size()).forEach(i -> {
            final Pair<AllergenData, Integer> allergen = allergens.get(i);
            panelAllergens.add(new JLabel(allergen.x().name()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            panelAllergens.add(new JLabel(allergen.x().description()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            panelAllergens.add(new JLabel(String.valueOf(allergen.y())), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            panelAllergens.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i + 1) * 2).setColumn(0)
                    .setWidth(3)
                    .setFillAll()
                    .build()
            );
        });
        return new JScrollPane(panelAllergens, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Creates a component with the diets followed by most people.
     * @return The component with the diets.
     */
    private Component getDietsSection() {
        final JComponent panelDiets = new JPanel();
        panelDiets.setLayout(new GridBagLayout());
        // Get the most popular discussions
        final List<Pair<DietData, Integer>> diets = StatisticsDAO.getMostFollowedDiets().stream().toList();
        IntStream.range(0, diets.size()).forEach(i -> {
            final Pair<DietData, Integer> diet = diets.get(i);
            panelDiets.add(new JLabel(diet.x().name()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            panelDiets.add(new JLabel(diet.x().description()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            panelDiets.add(new JLabel(String.valueOf(diet.y())), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            panelDiets.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i + 1) * 2).setColumn(0)
                    .setWidth(3)
                    .setFillAll()
                    .build()
            );
        });
        return new JScrollPane(panelDiets, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Creates a component with the longest activity.
     * @return The component with the activity information.
     */
    private Component getActivitySection() {
        final JComponent panelActivity = new JPanel();
        panelActivity.setLayout(new GridBagLayout());
        StatisticsDAO.getLongestActivity().ifPresent(a -> panelActivity.add(
                new JLabel("L'attivita con durata piu lunga e: " + a.x() + ", " + a.y() + " minuti.")));
        return panelActivity;
    }
}
