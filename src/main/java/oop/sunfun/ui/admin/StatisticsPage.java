package oop.sunfun.ui.admin;

import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.GenericPage;

public class StatisticsPage extends GenericPage {
    /**
     * The title of the page.
     */
    private static final String PAGE_NAME = "Pagina delle Statistiche";

    public StatisticsPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);

        // Finalize the page
        this.buildWindow();
    }
}
