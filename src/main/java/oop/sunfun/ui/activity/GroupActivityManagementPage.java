package oop.sunfun.ui.activity;

import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.GenericPage;

public final class GroupActivityManagementPage extends GenericPage {

    private static final String PAGE_NAME = "Gestione attivitá dei gruppi ";

    public GroupActivityManagementPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        // TODO: Finish this page to change activities of the groups during a given day

        // Finalize the window
        this.buildWindow();
    }
}
