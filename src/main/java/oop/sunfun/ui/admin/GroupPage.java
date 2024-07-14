package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.GroupDAO;
import oop.sunfun.database.data.admin.GroupData;
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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.stream.IntStream;

public final class GroupPage extends GenericPage {

    private static final String PAGE_NAME = "Gestione Gruppi";

    private final AccountData accountData;

    private final JTextComponent txtNome;
    private final JTextComponent txtEtaMin;
    private final JTextComponent txtEtaMax;

    public GroupPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // Add inputs to add a new group
        final Component lblNome = new JLabel("Nome del gruppo:");
        final Component lblEtaMin = new JLabel("Etá minima:");
        final Component lblEtaMax = new JLabel("Etá massima:");
        this.txtNome = new JTextField();
        this.txtEtaMin = new JTextField();
        this.txtEtaMax = new JTextField();
        final AbstractButton btnAddGroup = new JButton("Aggiungi il gruppo");
        final AbstractButton btnGoBack = new JButton("Torna alla dashboard");
        // Create the group table
        this.add(this.getGroupTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(4)
                .setFillAll()
                .build()
        );
        this.add(lblNome, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(lblEtaMin, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(lblEtaMax, new GridBagConstraintBuilder()
                .setRow(1).setColumn(2)
                .setFillAll()
                .build()
        );
        this.add(this.txtNome, new GridBagConstraintBuilder()
                .setRow(2).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(this.txtEtaMin, new GridBagConstraintBuilder()
                .setRow(2).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(this.txtEtaMax, new GridBagConstraintBuilder()
                .setRow(2).setColumn(2)
                .setFillAll()
                .build()
        );
        this.add(btnAddGroup, new GridBagConstraintBuilder()
                .setRow(2).setColumn(3)
                .setFillAll()
                .build()
        );
        this.add(btnGoBack, new GridBagConstraintBuilder()
                .setRow(3).setColumn(0)
                .setWidth(4)
                .setFillAll()
                .build()
        );
        // Add events
        // Return to the landing page
        btnGoBack.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, account)));
        // Try to add the group
        btnAddGroup.addActionListener(e -> {
            if (isDataValid()) {
                GroupDAO.createGroup(new GroupData(txtNome.getText(), Integer.parseInt(txtEtaMin.getText()),
                        Integer.parseInt(txtEtaMax.getText())));
                this.switchPage(new GroupPage(CloseEvents.EXIT_PROGRAM, account));
            }
        });
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
                    .setWidth(3)
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

    private boolean isDataValid() {
        final int minSize = 2;
        final int nameLengthLimit = 20;
        final String isAgeNumberRegex = "\\b(0*(?:[1-9][0-9]?|100))\\b";
        this.resetHighlights();
        if (txtNome.getText().length() > nameLengthLimit || txtNome.getText().length() < minSize) {
            GenericPage.highlightTextComponent(txtNome);
            return false;
        } else if (!txtEtaMin.getText().matches(isAgeNumberRegex)) {
            GenericPage.highlightTextComponent(txtEtaMin);
            return false;
        } else if (!txtEtaMax.getText().matches(isAgeNumberRegex)) {
            GenericPage.highlightTextComponent(txtEtaMax);
            return false;
        }
        final int etaMin = Integer.parseInt(txtEtaMin.getText());
        final int etaMax = Integer.parseInt(txtEtaMax.getText());
        if (etaMin > etaMax) {
            GenericPage.highlightTextComponent(txtEtaMin);
            GenericPage.highlightTextComponent(txtEtaMax);
            return false;
        }
        return true;
    }
}
