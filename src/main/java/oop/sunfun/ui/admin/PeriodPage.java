package oop.sunfun.ui.admin;

import oop.sunfun.database.dao.PeriodDAO;
import oop.sunfun.database.data.admin.PeriodData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.FormPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public final class PeriodPage extends FormPage {

    private static final String PAGE_NAME = "Gestione Periodi";

    private final AccountData accountData;

    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;
    private static final JXDatePicker DATE_START;
    private static final JXDatePicker DATE_END;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        DATE_START = new JXDatePicker();
        DATE_END = new JXDatePicker();
        FORM_COMPONENTS.put(new JLabel("Inizio del periodo:"), new Pair<>(DATE_START, 0));
        FORM_COMPONENTS.put(new JLabel("Fine del periodo:"), new Pair<>(DATE_END, 0));
    }

    public PeriodPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent, 1, FORM_COMPONENTS,
                () -> new LandingPage(CloseEvents.EXIT_PROGRAM, account),
                () -> {
                    final Date startDate = DATE_START.getDate();
                    final Date endDate = DATE_END.getDate();
                    if (startDate.compareTo(endDate) < 0) {
                        PeriodDAO.createPeriod(new PeriodData(startDate, endDate));
                        getDatesBetween(startDate, endDate).forEach(PeriodDAO::createDate);
                    }
                });
        this.accountData = account;
        // Create the period table
        this.add(this.getPeriodTable(), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(3)
                .setFillAll()
                .build()
        );
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
        return new JScrollPane(tablePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
}
