package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.PeriodDAO;
import oop.sunfun.database.data.admin.PeriodData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.login.AccountType;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.Set;

public final class PeriodPage extends GenericPage {

    private static final String PAGE_NAME = "Gestione Periodi";

    final JXDatePicker dateInizio;
    final JXDatePicker dateFine;

    public PeriodPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        // Add inputs to add a new period
        final Component lblInizio = new JLabel("Data di inizio:");
        final Component lblFine = new JLabel("Data di inizio:");
        this.dateInizio = new JXDatePicker();;
        this.dateFine = new JXDatePicker();
        final AbstractButton btnAddPeriod = new JButton("Aggiungi il periodo");
        final AbstractButton btnGoBack = new JButton("Torna alla dashboard");
        // Create the period table
        this.add(this.getPeriodTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(3)
                .setFillAll()
                .build()
        );
        this.add(lblInizio, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(lblFine, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(dateInizio, new GridBagConstraintBuilder()
                .setRow(2).setColumn(0)
                .setFillAll()
                .build()
        );
        this.add(dateFine, new GridBagConstraintBuilder()
                .setRow(2).setColumn(1)
                .setFillAll()
                .build()
        );
        this.add(btnAddPeriod, new GridBagConstraintBuilder()
                .setRow(2).setColumn(2)
                .setFillAll()
                .build()
        );
        this.add(btnGoBack, new GridBagConstraintBuilder()
                .setRow(3).setColumn(0)
                .setWidth(3)
                .setFillAll()
                .build()
        );
        // Add events
        // Return to the landing page
        btnGoBack.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, account)));
        // Try to add the period
        btnAddPeriod.addActionListener(e -> {
            if (isDataValid()) {
                final Date startDate = this.dateInizio.getDate();
                final Date endDate = this.dateFine.getDate();
                PeriodDAO.createParent(startDate, endDate);
                this.switchPage(new PeriodPage(CloseEvents.EXIT_PROGRAM, account));
            }
        });
        // Finalize the window
        this.buildWindow();
    }

    private Component getPeriodTable() {
        // TODO: Find way to put buttons in here to delete periods
        final Set<PeriodData> periods = PeriodDAO.getAllPeriods();
        final Object[][] periodsForTable = periods.stream()
                .map(p -> new String[]{p.startDate().toString(), p.endDate().toString()})
                .toArray(Object[][]::new);
        final AbstractTableModel tableModel = new DefaultTableModel(periodsForTable, new String[]{"Data Inizio", "Data Fine"});
        final Component table = new JTable(tableModel);
        return new JScrollPane(table);
    }

    private boolean isDataValid() {
        if (this.dateInizio.getDate().compareTo(this.dateFine.getDate()) >= 0) {
            return false;
        }
        return this.dateInizio.getDate() != null && this.dateFine.getDate() != null;
    }
}
