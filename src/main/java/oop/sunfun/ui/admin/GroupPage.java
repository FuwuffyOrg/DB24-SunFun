package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.GroupDAO;
import oop.sunfun.database.data.admin.GroupData;
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

public final class GroupPage extends FormPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Gestione Inserimento Gruppi";

    /**
     * The account data on the page.
     */
    private final AccountData accountData;

    /**
     * Components of the form used by the FormPage class.
     */
    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    /**
     * Textbox to keep the group's name in for the form.
     */
    private static final JComponent TXT_NAME;

    /**
     * Textbox to keep the group's minimum age for the form.
     */

    private static final JComponent TXT_ETA_MIN;
    /**
     * Textbox to keep the group's maximum age for the form.
     */
    private static final JComponent TXT_ETA_MAX;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        TXT_NAME = new JTextField();
        TXT_ETA_MIN = new JTextField();
        TXT_ETA_MAX = new JTextField();
        FORM_COMPONENTS.put(new JLabel("Nome del gruppo:"), new Pair<>(TXT_NAME, 20));
        FORM_COMPONENTS.put(new JLabel("Eta minima:"), new Pair<>(TXT_ETA_MIN, 3));
        FORM_COMPONENTS.put(new JLabel("Eta massima:"), new Pair<>(TXT_ETA_MAX, 3));
    }

    /**
     * Constructor for the page to manage existing groups.
     * @param closeEvent The event that happens when you close the page.
     * @param account The account that called this page.
     */
    public GroupPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent, 1, FORM_COMPONENTS,
                () -> new GroupPage(CloseEvents.EXIT_PROGRAM, account),
                () -> new LandingPage(CloseEvents.EXIT_PROGRAM, account),
                () -> GroupDAO.createGroup(new GroupData(((JTextComponent) TXT_NAME).getText(),
                            Integer.parseInt(((JTextComponent) TXT_ETA_MIN).getText()),
                            Integer.parseInt(((JTextComponent) TXT_ETA_MAX).getText()))));
        this.accountData = account;
        // Create the group table
        this.add(this.getGroupTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(2)
                .setFillAll()
                .build()
        );
        // Finalize the window
        this.buildWindow();
    }

    /**
     * Creates a table with all the current group's information.
     * @return The table with the groups.
     */
    private Component getGroupTable() {
        // Create the panel
        final JComponent tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        // Get the groups
        final List<GroupData> groups = GroupDAO.getAllGroups().stream().toList();
        // Add the groups to the table
        IntStream.range(0, groups.size()).forEach(i -> {
            final GroupData group = groups.get(i);
            final AbstractButton btnDeleteGroup = new JButton("Elimina");
            // Add them to the panel
            tablePanel.add(new JLabel(group.name()), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JLabel(String.valueOf(group.minAge())), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JLabel(String.valueOf(group.maxAge())), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(btnDeleteGroup, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(3)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(4)
                    .setFillAll()
                    .build()
            );
            // Add delete event
            btnDeleteGroup.addActionListener(e -> {
                GroupDAO.deleteGroup(group.name());
                this.switchPage(new GroupPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            });
        });
        // Add the table to the panel
        return new JScrollPane(tablePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    @Override
    protected boolean isDataValid() {
        final int minAge = Integer.parseInt(((JTextComponent) TXT_ETA_MIN).getText());
        final int maxAge = Integer.parseInt(((JTextComponent) TXT_ETA_MAX).getText());
        return super.isDataValid() && minAge < maxAge;
    }
}
