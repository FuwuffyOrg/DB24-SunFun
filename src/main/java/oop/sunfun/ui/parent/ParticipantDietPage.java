package oop.sunfun.ui.parent;

import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.login.ParticipantData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;

public final class ParticipantDietPage extends GenericPage {

    private static final String PAGE_NAME = "Gestione Diete";

    private final AccountData accountData;

    public ParticipantDietPage(final CloseEvents closeEvent, final AccountData account,
                               final ParticipantData participant) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // TODO: gestione delle diete del partecipante
        // Finalize Window
        this.buildWindow();
    }
}
