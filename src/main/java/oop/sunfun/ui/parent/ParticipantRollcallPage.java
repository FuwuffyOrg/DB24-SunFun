package oop.sunfun.ui.parent;

import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.login.ParticipantData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;

public final class ParticipantRollcallPage extends GenericPage {

    private static final String PAGE_NAME = "Appello";

    public ParticipantRollcallPage(final CloseEvents closeEvent, final AccountData account,
                                   final ParticipantData participant) {
        super(PAGE_NAME, closeEvent);
        // TODO: Pagina dell'appello (calendario delle giornate in cui lui deve essere al centro estivo)
        // Finalize page
        this.buildWindow();
    }
}
