package oop.sunfun.ui.admin;

import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;
import oop.sunfun.ui.util.pages.GenericPage;

import javax.swing.*;
import java.awt.*;

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
        this.add(getDietsSection(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        );
        this.add(getDiscussionsSection(), new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(getAllergensSection(), new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(getActivitySection(), new GridBagConstraintBuilder()
                .setRow(2).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(btnGoBack, new GridBagConstraintBuilder()
                .setRow(2).setColumn(1)
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

        return panelDiscussions;
    }

    /**
     * Creates a component with the allergens that affect most people.
     * @return The component with the allergens.
     */
    private Component getAllergensSection() {
        final JComponent panelAllergens = new JPanel();
        panelAllergens.setLayout(new GridBagLayout());

        return panelAllergens;
    }

    /**
     * Creates a component with the diets followed by most people.
     * @return The component with the diets.
     */
    private Component getDietsSection() {
        final JComponent panelDiets = new JPanel();
        panelDiets.setLayout(new GridBagLayout());

        return panelDiets;
    }

    /**
     * Creates a component with the longest activity.
     * @return The component with the activity information.
     */
    private Component getActivitySection() {
        final JComponent panelActivity = new JPanel();
        panelActivity.setLayout(new GridBagLayout());

        return panelActivity;
    }
}
