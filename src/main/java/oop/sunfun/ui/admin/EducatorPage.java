package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.AccountDAO;
import oop.sunfun.database.dao.EducatorDAO;
import oop.sunfun.database.dao.GroupDAO;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.person.EducatorData;
import oop.sunfun.database.data.person.VoluntaryData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.stream.IntStream;

public class EducatorPage extends GenericPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Gestione Educatori";

    /**
     * The account data on the page.
     */
    private final AccountData accountData;

    public EducatorPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        final AbstractButton btnGoBack = new JButton("Torna alla dashboard");
        final AbstractButton btnAddNewEducator = new JButton("Aggiungi un nuovo personale");
        this.add(getEducatorTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(getVoluntaryTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(btnGoBack, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(btnAddNewEducator, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        // Set action events
        btnGoBack.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, account)));
        btnAddNewEducator.addActionListener(e -> this.switchPage(new AddEducatorPage(CloseEvents.EXIT_PROGRAM,
                account)));
        // Finalize page
        this.buildWindow();
    }

    private Component getEducatorTable() {
        // Create the panel
        final JComponent tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        // Get the groups
        final List<EducatorData> educators = EducatorDAO.getAllEducators().stream().toList();
        // Add the groups to the table
        IntStream.range(0, educators.size()).forEach(i -> {
            final EducatorData educator = educators.get(i);
            final Component lblCodFisc = new JLabel(String.valueOf(educator.codFisc()));
            final Component lblName = new JLabel(educator.name());
            final Component lblSurname = new JLabel(String.valueOf(educator.surname()));
            final Component lblEmail = new JLabel(String.valueOf(educator.accountEmail()));
            final Component lblNumber = new JLabel(String.valueOf(educator.phoneNumber()));
            final JComboBox<String> comboGroup = new JComboBox<>();
            comboGroup.addItem(null);
            GroupDAO.getAllGroups().forEach(p -> comboGroup.addItem(p.name()));
            educator.group().ifPresent(comboGroup::setSelectedItem);
            final AbstractButton btnChangeEducatorGroup = new JButton("Aggiorna Gruppo");
            final AbstractButton btnDeleteEducator = new JButton("Elimina");
            // Add them to the panel
            tablePanel.add(lblCodFisc, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblName, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblSurname, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblEmail, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(3)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblNumber, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(4)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(comboGroup, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(5)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(btnChangeEducatorGroup, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(6)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(btnDeleteEducator, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(7)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setWidth(8)
                    .setFillAll()
                    .build()
            );
            // Add delete event
            btnChangeEducatorGroup.addActionListener(e -> {
                EducatorDAO.changeEducatorGroup(educator, (String) comboGroup.getSelectedItem());
                this.switchPage(new EducatorPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            });
            btnDeleteEducator.addActionListener(e -> {
                AccountDAO.eraseAccount(educator.accountEmail());
                this.switchPage(new EducatorPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            });
        });
        // Add the table to the panel
        return new JScrollPane(tablePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private Component getVoluntaryTable() {
        // Create the panel
        final JComponent tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        // Get the groups
        final List<VoluntaryData> voluntaries = EducatorDAO.getAllVoluntary().stream().toList();
        // Add the groups to the table
        IntStream.range(0, voluntaries.size()).forEach(i -> {
            final VoluntaryData voluntary = voluntaries.get(i);
            final Component lblCodFisc = new JLabel(String.valueOf(voluntary.codFisc()));
            final Component lblName = new JLabel(voluntary.name());
            final Component lblSurname = new JLabel(String.valueOf(voluntary.surname()));
            final Component lblEmail = new JLabel(String.valueOf(voluntary.accountEmail()));
            final AbstractButton btnDeleteEducator = new JButton("Elimina");
            // Add them to the panel
            tablePanel.add(lblCodFisc, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblName, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblSurname, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblEmail, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(3)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(btnDeleteEducator, new GridBagConstraintBuilder()
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
            btnDeleteEducator.addActionListener(e -> {
                AccountDAO.eraseAccount(voluntary.accountEmail());
                this.switchPage(new EducatorPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            });
        });
        // Add the table to the panel
        return new JScrollPane(tablePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }
}
