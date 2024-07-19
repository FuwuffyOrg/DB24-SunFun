package oop.sunfun.ui.activity;

import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.GenericPage;

public final class GroupActivityViewPage extends GenericPage {

    private static final String PAGE_NAME = "Attivitá del gruppo ";

    public GroupActivityViewPage(final CloseEvents closeEvent, final AccountData account, final String groupName) {
        super(PAGE_NAME + groupName, closeEvent);
        // TODO: Finish this page to look at the activities of a group during a given day

        // Finalize the window
        this.buildWindow();
    }
}
