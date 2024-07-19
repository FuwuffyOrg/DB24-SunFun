package oop.sunfun.ui.activity;

import oop.sunfun.database.data.admin.GroupData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.GenericPage;

public class GroupRollcallPage extends GenericPage {

    private static final String PAGE_NAME = "Appello del Gruppo ";

    public GroupRollcallPage(final CloseEvents closeEvent, final AccountData account, final String groupName) {
        super(PAGE_NAME + groupName, closeEvent);
        // TODO: Finish this page for the rollcall of a group and presences for any given day
        // TODO: Connect to voluntary and educator

        // Finalize the window
        this.buildWindow();
    }
}
