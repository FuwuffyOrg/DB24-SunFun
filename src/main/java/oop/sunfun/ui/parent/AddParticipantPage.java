package oop.sunfun.ui.parent;

import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;

public final class AddParticipantPage extends GenericPage {

    private static final String PAGE_NAME = "Aggiungi un partecipante";

    private final AccountData accountData;

    public AddParticipantPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // TODO: Make this page
        // Finalize the window
        this.buildWindow();
    }
}
