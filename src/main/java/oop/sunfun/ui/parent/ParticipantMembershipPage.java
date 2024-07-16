package oop.sunfun.ui.parent;

import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.database.data.person.ParticipantData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;

public final class ParticipantMembershipPage extends GenericPage {

    private static final String PAGE_NAME = "Gestione Iscrizione ";

    public ParticipantMembershipPage(final CloseEvents closeEvent, final AccountData account,
                                     final ParticipantData participant) {
        super(PAGE_NAME + participant.name() + " " + participant.surname(), closeEvent);
        // TODO: Tasto gestione iscrizione (per i periodi a cui esso é iscritto e chi puo venirlo a prendere)
        // Finalize Window
        this.buildWindow();
    }
}
