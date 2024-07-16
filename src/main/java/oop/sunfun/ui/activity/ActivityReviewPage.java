package oop.sunfun.ui.activity;

import oop.sunfun.database.dao.ActivityDAO;
import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.activity.ReviewData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.FormPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.JComboBox;
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

public final class ActivityReviewPage extends FormPage {

    private static final String PAGE_NAME = "Recensioni dell'attivita: ";

    private static final int MAX_GRADE = 5;

    private final ActivityData activityData;

    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    private static final JComboBox<Integer> COMBO_GRADE;
    private static final JComponent TXT_DESCRIPTION;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        COMBO_GRADE = new JComboBox<>();
        IntStream.range(0, MAX_GRADE + 1).forEach(COMBO_GRADE::addItem);
        TXT_DESCRIPTION = new JTextField();
        FORM_COMPONENTS.put(new JLabel("Voto:"), new Pair<>(COMBO_GRADE, 0));
        FORM_COMPONENTS.put(new JLabel("Descrizione:"), new Pair<>(TXT_DESCRIPTION, 10000));
    }

    public ActivityReviewPage(final CloseEvents closeEvent, final AccountData account, final ActivityData activity) {
        super(PAGE_NAME + activity.name(), closeEvent, 1, FORM_COMPONENTS,
                () -> new ActivityPage(CloseEvents.EXIT_PROGRAM, account),
                () -> ActivityDAO.createNewReviewForActivity((int) COMBO_GRADE.getSelectedItem(),
                        ((JTextComponent) TXT_DESCRIPTION).getText(), activity.name(), account.email()));
        this.activityData = activity;
        // Add the components to the page
        this.add(getReviewsTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(4)
                .setFillAll()
                .build()
        );
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

    @Override
    protected boolean isDataValid() {
        final Integer grade = (Integer) COMBO_GRADE.getSelectedItem();
        if (grade == null) {
            throw new IllegalStateException("No grade selected for the review.");
        }
        return super.isDataValid();
    }
}
