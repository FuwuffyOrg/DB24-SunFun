package oop.sunfun.ui.activity;

import oop.sunfun.database.dao.ActivityDAO;
import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.activity.ReviewData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.stream.IntStream;

public final class ActivityReviewPage extends GenericPage {

    private static final String PAGE_NAME = "Recensioni dell'attivita: ";

    private static final int MAX_GRADE = 5;

    private final ActivityData activityData;

    private final JComboBox<Integer> comboGrade;

    private final JTextComponent txtDescription;

    public ActivityReviewPage(final CloseEvents closeEvent, final AccountData account, final ActivityData activity) {
        super(PAGE_NAME + activity.name(), closeEvent);
        this.activityData = activity;
        // Create the components to add to the page
        final Component lblGrade = new JLabel("Voto:");
        final Component lblDescription = new JLabel("Descrizione:");
        this.comboGrade = new JComboBox<>();
        this.txtDescription = new JTextArea();
        final AbstractButton btnAddReview = new JButton("Aggiungi recensione");
        final AbstractButton btnGotoDashboard = new JButton("Torna alla dashboard");
        IntStream.range(0, MAX_GRADE + 1).forEach(this.comboGrade::addItem);
        // Add the components to the page
        this.add(getReviewsTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(4)
                .setFillAll()
                .build()
        );
        this.add(lblGrade, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(lblDescription, new GridBagConstraintBuilder()
                .setRow(2).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(this.comboGrade, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(this.txtDescription, new GridBagConstraintBuilder()
                .setRow(2).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(btnAddReview, new GridBagConstraintBuilder()
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
        btnAddReview.addActionListener(e -> {
            if (isDataValid()) {
                final Integer grade = (Integer) this.comboGrade.getSelectedItem();
                if (grade == null) {
                    throw new IllegalStateException("No grade selected for the review.");
                }
                ActivityDAO.createNewReviewForActivity(grade, txtDescription.getText(), activityData.name(),
                        account.email());
                this.switchPage(new ActivityReviewPage(CloseEvents.EXIT_PROGRAM, account, activity));
            }
        });
        // Finalize the window
        this.buildWindow();
    }

    private Component getReviewsTable() {
        // Create the panel
        final JComponent tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        // Get the groups
        final List<ReviewData> reviews = ActivityDAO.getReviewsFromActivity(this.activityData.name()).stream().toList();
        // Add the groups to the table
        IntStream.range(0, reviews.size()).forEach(i -> {
            final ReviewData review = reviews.get(i);
            final Component lblName = new JLabel(review.accountName());
            final Component lblSurname = new JLabel(review.accountSurname());
            final Component lblDescription = new JLabel(review.description());
            final Component lblGrade = new JLabel(String.valueOf(review.grade()));
            // Add them to the panel
            tablePanel.add(lblName, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblSurname, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblDescription, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblGrade, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(3)
                    .setWeightColumn(0.01d)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(4)
                    .setFillAll()
                    .build()
            );
        });
        // Add the table to the panel
        return new JScrollPane(tablePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private boolean isDataValid() {
        final int minSize = 4;
        final int descriptionLimit = 10000;
        this.resetHighlights();
        final String description = this.txtDescription.getText();
        final Integer gradeValue = (Integer) this.comboGrade.getSelectedItem();
        if (description.length() > descriptionLimit || description.length() < minSize) {
            highlightTextComponent(this.txtDescription);
            return false;
        }
        return gradeValue != null;
    }
}
