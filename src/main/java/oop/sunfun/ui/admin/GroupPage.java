package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.GroupDAO;
import oop.sunfun.database.data.admin.GroupData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.FormPage;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class GroupPage extends FormPage {

    private static final String PAGE_NAME = "Gestione Gruppi";

    private final AccountData accountData;

    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    private static final JComponent TXT_NOME;
    private static final JComponent TXT_ETA_MIN;
    private static final JComponent TXT_ETA_MAX;

    static {
        FORM_COMPONENTS = new HashMap<>();
        TXT_NOME = new JTextField();
        TXT_ETA_MIN = new JTextField();
        TXT_ETA_MAX = new JTextField();
        FORM_COMPONENTS.put(new JLabel("Nome del gruppo:"), new Pair<>(TXT_NOME, 20));
        FORM_COMPONENTS.put(new JLabel("Eta minima:"), new Pair<>(TXT_ETA_MIN, 1));
        FORM_COMPONENTS.put(new JLabel("Eta massima:"), new Pair<>(TXT_ETA_MAX, 1));
    }

    public GroupPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent, 1, FORM_COMPONENTS,
                () -> new LandingPage(CloseEvents.EXIT_PROGRAM, account),
                () -> GroupDAO.createGroup(new GroupData(((JTextComponent) TXT_NOME).getText(),
                            Integer.parseInt(((JTextComponent) TXT_ETA_MIN).getText()),
                            Integer.parseInt(((JTextComponent) TXT_ETA_MAX).getText()))));
        this.accountData = account;
        // Create the group table
        this.add(this.getGroupTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(4)
                .setFillAll()
                .build()
        );
        // Finalize the window
        this.buildWindow();
    }

    private Component getGroupTable() {
        // Create the panel
        final JComponent tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        // Get the groups
        final List<GroupData> groups = GroupDAO.getAllGroups().stream().toList();
        // Add the groups to the table
        IntStream.range(0, groups.size()).forEach(i -> {
            final GroupData group = groups.get(i);
            final Component lblName = new JLabel(group.name());
            final Component lblEtaMin = new JLabel(String.valueOf(group.minAge()));
            final Component lblEtaMax = new JLabel(String.valueOf(group.maxAge()));
            final AbstractButton btnDeleteGroup = new JButton("Elimina");
            // Add them to the panel
            tablePanel.add(lblName, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblEtaMin, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblEtaMax, new GridBagConstraintBuilder()
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
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }
}
