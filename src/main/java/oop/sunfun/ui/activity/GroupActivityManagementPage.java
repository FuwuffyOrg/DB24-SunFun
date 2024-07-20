package oop.sunfun.ui.activity;

import oop.sunfun.database.dao.ActivityDAO;
import oop.sunfun.database.dao.GroupDAO;
import oop.sunfun.database.dao.PeriodDAO;
import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.admin.GroupData;
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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

public final class GroupActivityManagementPage extends FormPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Gestione attivita dei gruppi";

    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    private static final JComboBox<Date> COMBO_DATE;
    private static final JComboBox<String> COMBO_GROUP;
    private static final JComboBox<String> COMBO_ACTIVITY;
    private static final JComboBox<Time> COMBO_TIME_START;
    private static final JComboBox<Time> COMBO_TIME_END;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        COMBO_DATE = new JComboBox<>();
        COMBO_GROUP = new JComboBox<>();
        COMBO_ACTIVITY = new JComboBox<>();
        COMBO_TIME_START = new JComboBox<>();
        COMBO_TIME_END = new JComboBox<>();
        final int nTimes = 96;
        final long millisInDay = 24 * 60 * 60 * 1000;
        final long deltaMillis = millisInDay / nTimes;
        IntStream.range(0, nTimes).forEach(i -> {
            COMBO_TIME_START.addItem(new Time(i * deltaMillis));
            COMBO_TIME_END.addItem(new Time(i * deltaMillis));
        });
        FORM_COMPONENTS.put(new JLabel("Data:"), new Pair<>(COMBO_DATE, 0));
        FORM_COMPONENTS.put(new JLabel("Gruppo:"), new Pair<>(COMBO_GROUP, 0));
        FORM_COMPONENTS.put(new JLabel("Attivita:"), new Pair<>(COMBO_ACTIVITY, 0));
        FORM_COMPONENTS.put(new JLabel("Inizio:"), new Pair<>(COMBO_TIME_START, 0));
        FORM_COMPONENTS.put(new JLabel("Fine:"), new Pair<>(COMBO_TIME_END, 0));
    }

    /**
     * The account data on the page.
     */
    private final AccountData accountData;

    public GroupActivityManagementPage(final CloseEvents closeEvent, final AccountData account,
                                       final Optional<Date> currentDate) {
        super(PAGE_NAME, closeEvent, 2, FORM_COMPONENTS,
                () -> new GroupActivityManagementPage(CloseEvents.EXIT_PROGRAM, account,
                        Optional.ofNullable((Date) COMBO_DATE.getSelectedItem())),
                () -> new LandingPage(CloseEvents.EXIT_PROGRAM, account),
                () -> {
                    currentDate.ifPresent(d -> ActivityDAO.addActivityToGroup((String) COMBO_GROUP.getSelectedItem(),
                            (String) COMBO_ACTIVITY.getSelectedItem(), d, (Time) COMBO_TIME_START.getSelectedItem(),
                            (Time) COMBO_TIME_END.getSelectedItem()));
                });
        this.accountData = account;
        // Update the combo boxes
        COMBO_DATE.removeAllItems();
        COMBO_GROUP.removeAllItems();
        COMBO_ACTIVITY.removeAllItems();
        PeriodDAO.getAllDates().forEach(COMBO_DATE::addItem);
        GroupDAO.getAllGroups().forEach(g -> COMBO_GROUP.addItem(g.name()));
        ActivityDAO.getAllActivities().forEach(a -> COMBO_ACTIVITY.addItem(a.name()));
        // Add a button to refresh the activities based on the date
        final AbstractButton btnUpdatePage = new JButton("Aggiorna attivita");
        // Add the panel if the date has been selected
        currentDate.ifPresent(d -> this.add(getGroupTabs(d), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        ));
        this.add(btnUpdatePage, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        );
        btnUpdatePage.addActionListener(e -> this.switchPage(new GroupActivityManagementPage(CloseEvents.EXIT_PROGRAM,
                account, Optional.ofNullable((Date) COMBO_DATE.getSelectedItem()))));
        // Finalize the window
        this.buildWindow();
    }

    private Component getGroupTabs(final Date d) {
        final JComponent tabs = new JTabbedPane();
        final Set<GroupData> groups = GroupDAO.getAllGroups();
        groups.forEach(g -> tabs.add(getActivitiesPanel(d, g), g.name()));
        return tabs;
    }

    private Component getActivitiesPanel(final Date d, final GroupData group) {
        // Create a panel
        final JComponent activitiesPanel = new JPanel();
        activitiesPanel.setLayout(new GridBagLayout());
        final List<Map.Entry<ActivityData, Pair<Time, Time>>> activities =
                ActivityDAO.getActivitiesDoneByGroupDuring(group.name(), d).entrySet().stream().toList();
        IntStream.range(0, activities.size()).forEach(i -> {
            // Get the various info to display
            final ActivityData activityData = activities.get(i).getKey();
            final Time startTime = activities.get(i).getValue().x();
            final Time endTime = activities.get(i).getValue().y();
            final AbstractButton btnReviews = new JButton("Recensioni");
            activitiesPanel.add(new JLabel(activityData.name()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            activitiesPanel.add(new JLabel(activityData.description()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            activitiesPanel.add(new JLabel(String.valueOf(activityData.avgGrade())),
                    new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            activitiesPanel.add(new JLabel(startTime.toString()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(3)
                    .setFillAll()
                    .build()
            );
            activitiesPanel.add(new JLabel(endTime.toString()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(4)
                    .setFillAll()
                    .build()
            );
            activitiesPanel.add(btnReviews, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(5)
                    .setFillAll()
                    .build()
            );
            activitiesPanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(6)
                    .setFillAll()
                    .build()
            );
            btnReviews.addActionListener(e -> this.switchPage(new ActivityReviewPage(CloseEvents.EXIT_PROGRAM,
                    this.accountData, activityData)));
        });
        return new JScrollPane(activitiesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
