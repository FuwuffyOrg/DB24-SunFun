package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.PeriodDAO;
import oop.sunfun.database.data.admin.PeriodData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public final class PeriodPage extends GenericPage {

    private static final String PAGE_NAME = "Gestione Periodi";

    private final AccountData accountData;

    private final JXDatePicker dateInizio;
    private final JXDatePicker dateFine;

    public PeriodPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // Add inputs to add a new period
        final Component lblInizio = new JLabel("Data di inizio:");
        final Component lblFine = new JLabel("Data di fine:");
        this.dateInizio = new JXDatePicker();
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
                PeriodDAO.createPeriod(new PeriodData(startDate, endDate));
                getDatesBetween(startDate, endDate).forEach(PeriodDAO::createDate);
                this.switchPage(new PeriodPage(CloseEvents.EXIT_PROGRAM, account));
            }
        });
        // Finalize the window
        this.buildWindow();
    }

    private Component getPeriodTable() {
        // Create the panel
        final JComponent tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        // Get the periods
        final List<PeriodData> periods = PeriodDAO.getAllPeriods().stream().toList();
        // Add the periods to the table
        IntStream.range(0, periods.size()).forEach(i -> {
            final PeriodData period = periods.get(i);
            final Component lblDateStart = new JLabel(period.startDate().toString());
            final Component lblDateEnd = new JLabel(period.endDate().toString());
            final AbstractButton btnDeletePeriod = new JButton("Elimina");
            // Add them to the panel
            tablePanel.add(lblDateStart, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(lblDateEnd, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(1)
                    .setFillAll()
                    .build()
            );
            tablePanel.add(btnDeletePeriod, new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(2)
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
            btnDeletePeriod.addActionListener(e -> {
                PeriodDAO.deletePeriod(period);
                PeriodDAO.deleteDatesBetween(period.startDate(), period.endDate());
                this.switchPage(new PeriodPage(CloseEvents.EXIT_PROGRAM, this.accountData));
            });
        });
        // Add the table to the panel
        return new JScrollPane(tablePanel);
    }

    private static Set<Date> getDatesBetween(final Date startDate, final Date endDate) {
        final Set<Date> datesInRange = new HashSet<>();
        final Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        final Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        while (startCalendar.before(endCalendar)) {
            final Date result = startCalendar.getTime();
            datesInRange.add(result);
            startCalendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    private boolean isDataValid() {
        if (this.dateInizio.getDate().compareTo(this.dateFine.getDate()) >= 0) {
            return false;
        }
        return this.dateInizio.getDate() != null && this.dateFine.getDate() != null;
    }
}
