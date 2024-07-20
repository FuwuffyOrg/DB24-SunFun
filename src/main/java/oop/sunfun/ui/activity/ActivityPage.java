package oop.sunfun.ui.activity;

import oop.sunfun.database.dao.ActivityDAO;
import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.FormPage;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class ActivityPage extends FormPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Gestione Attivita";

    /**
     * The account data on the page.
     */
    private final AccountData accountData;

    /**
     * Components of the form used by the FormPage class.
     */
    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    /**
     * Textbox to keep the activity's name.
     */
    private static final JComponent TXT_NAME;

    /**
     * Textbox to keep the activity's description.
     */
    private static final JComponent TXT_DESCRIPTION;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        TXT_NAME = new JTextField();
        TXT_DESCRIPTION = new JTextField();
        FORM_COMPONENTS.put(new JLabel("Nome dell'Attivita:"), new Pair<>(TXT_NAME, 50));
        FORM_COMPONENTS.put(new JLabel("Descrizione:"), new Pair<>(TXT_DESCRIPTION, 10000));
    }

    /**
     * Constructor for the activity management of this database.
     * @param closeEvent The event that happens when you close the window.
     * @param account The account that called this window.
     */
    public ActivityPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent, 1, FORM_COMPONENTS,
                () -> new ActivityPage(CloseEvents.EXIT_PROGRAM, account),
                () -> new LandingPage(CloseEvents.EXIT_PROGRAM, account),
                () -> ActivityDAO.createNewActivity(new ActivityData(((JTextComponent) TXT_NAME).getText(),
                        ((JTextComponent) TXT_DESCRIPTION).getText(), 0.0f)));
        this.accountData = account;
        // Add the components to the page
        this.add(getActivityTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(4)
                .setFillAll()
                .build()
        );
        // Finalize the window
        this.buildWindow();
    }

    /**
     * Creates a table with all the activities in the database.
     * @return The table with the activities of the database.
     */
    private Component getActivityTable() {
        // Create the panel
        final JComponent tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        // Get the groups
        final List<ActivityData> activities = ActivityDAO.getAllActivities().stream().toList();
        // Add the groups to the table
        IntStream.range(0, activities.size()).forEach(i -> {
            final ActivityData activity = activities.get(i);
            final Component lblName = new JLabel(activity.name());
            final Component lblGrade = new JLabel(String.valueOf(activity.avgGrade()));
            final Component lblDescription = new JLabel(activity.description());
            final AbstractButton btnCheckReviews = new JButton("Recensioni");
            final AbstractButton btnDeleteActivity = new JButton("Elimina");
            // Add them to the panel
            tablePanel.add(lblName, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblGrade, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setWeightColumn(0.01d)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblDescription, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(btnCheckReviews, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(3)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(btnDeleteActivity, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(4)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(5)
                    .setFillAll()
                    .build()
            );
            // Add delete event
            btnDeleteActivity.addActionListener(e -> {
                ActivityDAO.deleteActivity(activity);
                this.switchPage(new ActivityPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            });
            btnCheckReviews.addActionListener(e -> {
                this.switchPage(new ActivityReviewPage(CloseEvents.EXIT_PROGRAM, this.accountData, activity));
            });
        });
        // Add the table to the panel
        return new JScrollPane(tablePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }
}
