package oop.sunfun.ui.activity;

import oop.sunfun.database.dao.ActivityDAO;
import oop.sunfun.database.dao.PeriodDAO;
import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;
import oop.sunfun.ui.util.pages.FormPage;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.IntStream;

public final class GroupActivityViewPage extends FormPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Attivitá del gruppo ";

    /**
     * Components of the form used by the FormPage class.
     */
    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    /**
     * Combo box to select the date to check the activities during it.
     */
    private static final JComboBox<Date> COMBO_DATE;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        COMBO_DATE = new JComboBox<>();
        FORM_COMPONENTS.put(new JLabel("Data:"), new Pair<>(COMBO_DATE, 0));
    }

    /**
     * The account data on the page.
     */
    private final AccountData accountData;

    /**
     * Constructor for a page to check a group's activity timetable.
     * @param closeEvent The event that happens when you close the window.
     * @param account The account that called this window.
     * @param groupName The name of the group to check the activities from.
     * @param currentDate A valid optional if a date has been selected for this page.
     */
    public GroupActivityViewPage(final CloseEvents closeEvent, final AccountData account, final String groupName,
                                 final Optional<Date> currentDate) {
        super(PAGE_NAME + groupName, closeEvent, 1, FORM_COMPONENTS,
                () -> new GroupActivityViewPage(CloseEvents.EXIT_PROGRAM, account, groupName,
                        Optional.ofNullable((Date) COMBO_DATE.getSelectedItem())),
                () -> new LandingPage(CloseEvents.EXIT_PROGRAM, account),
                () -> {

                });
        this.accountData = account;
        // Update the dates
        PeriodDAO.getAllDates().forEach(COMBO_DATE::addItem);
        // Add the panel to the page
        currentDate.ifPresent(d -> this.add(getActivitiesTabs(d, groupName), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        ));
        // Finalize the window
        this.buildWindow();
    }

    /**
     * Creates tabs per each day with the activities they will do.
     * @param d The starting day to check the activities during.
     * @param groupName The name of the group to check activities for.
     * @return The tabs of seven days worth of activities.
     */
    private Component getActivitiesTabs(final Date d, final String groupName) {
        final int dateCount = 7;
        final JComponent tabs = new JTabbedPane();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        // Try to get 7 dates
        IntStream.range(0, dateCount).forEach(i -> {
            // Get the date and increment by one
            final Date trueDate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            tabs.add(getActivitiesPanel(d, groupName), new SimpleDateFormat("dd/MM/yyyy EEEE")
                    .format(trueDate));
        });
        return tabs;
    }

    /**
     * Creates a table with all the activities a group has to attend to.
     * @param d The date to check the activities.
     * @param groupName The name of the group to check them.
     * @return The table with the activities info.
     */
    private Component getActivitiesPanel(final Date d, final String groupName) {
        // Create a panel
        final JComponent datePanel = new JPanel();
        datePanel.setLayout(new GridBagLayout());
        final List<Entry<ActivityData, Pair<Time, Time>>> activities =
                ActivityDAO.getActivitiesDoneByGroupDuring(groupName, d).entrySet().stream().toList();
        IntStream.range(0, activities.size()).forEach(i -> {
            // Get the various info to display
            final ActivityData activityData = activities.get(i).getKey();
            final Time startTime = activities.get(i).getValue().x();
            final Time endTime = activities.get(i).getValue().y();
            final AbstractButton btnReviews = new JButton("Recensioni");
            datePanel.add(new JLabel(activityData.name()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            datePanel.add(new JLabel(activityData.description()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            datePanel.add(new JLabel(String.valueOf(activityData.avgGrade())), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            datePanel.add(new JLabel(startTime.toString()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            datePanel.add(new JLabel(endTime.toString()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            datePanel.add(btnReviews, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            datePanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(6)
                    .setFillAll()
                    .build()
            );
            btnReviews.addActionListener(e -> this.switchPage(new ActivityReviewPage(CloseEvents.EXIT_PROGRAM,
                    this.accountData, activityData)));
        });
        return new JScrollPane(datePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
