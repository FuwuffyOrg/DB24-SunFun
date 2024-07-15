package oop.sunfun.ui.activity;

import oop.sunfun.database.dao.ActivityDAO;
import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.stream.IntStream;

public final class ActivityPage extends GenericPage {

    private static final String PAGE_NAME = "Gestione Attivitá";

    private final AccountData accountData;

    private final JTextComponent txtName;

    private final JTextComponent txtDescription;

    public ActivityPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // Create the components to add to the page
        final Component lblName = new JLabel("Nome:");
        final Component lblDescription = new JLabel("Descrizione:");
        this.txtName = new JTextField();
        this.txtDescription = new JTextArea();
        final AbstractButton btnAddActivity = new JButton("Aggiungi attivita");
        final AbstractButton btnGotoDashboard = new JButton("Torna alla dashboard");
        // Add the components to the page
        this.add(getActivityTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(4)
                .setFillAll()
                .build()
        );
        this.add(lblName, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(lblDescription, new GridBagConstraintBuilder()
                .setRow(2).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(this.txtName, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(this.txtDescription, new GridBagConstraintBuilder()
                .setRow(2).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(btnAddActivity, new GridBagConstraintBuilder()
                .setRow(1).setColumn(2)
                .setHeight(2)
                .setFillAll()
                .build()
        );
        this.add(btnGotoDashboard, new GridBagConstraintBuilder()
                .setRow(1).setColumn(3)
                .setHeight(2)
                .setFillAll()
                .build()
        );
        // Handle events
        btnGotoDashboard.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, account)));
        btnAddActivity.addActionListener(e -> {
            if (isDataValid()) {
                ActivityDAO.createNewActivity(new ActivityData(txtName.getText(), txtDescription.getText(),
                        0.0f));
                this.switchPage(new ActivityPage(CloseEvents.EXIT_PROGRAM, account));
            }
        });
        // Finalize the window
        this.buildWindow();
    }

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

    private boolean isDataValid() {
        final int minSize = 2;
        final int nameLengthLimit = 50;
        final int descriptionLengthLimit = 10000;
        this.resetHighlights();
        final String name = this.txtName.getText();
        final String description = this.txtDescription.getText();
        if (name.length() > nameLengthLimit || name.length() < minSize) {
            highlightTextComponent(this.txtName);
            return false;
        } else if (description.length() > descriptionLengthLimit || description.length() < minSize) {
            highlightTextComponent(this.txtDescription);
            return false;
        }
        return true;
    }
}
