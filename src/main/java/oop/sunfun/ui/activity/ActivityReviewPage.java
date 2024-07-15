package oop.sunfun.ui.activity;

import oop.sunfun.database.data.activity.ActivityData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.GenericPage;

public final class ActivityReviewPage extends GenericPage {

    private static final String PAGE_NAME = "Recensioni dell'attivitá ";

    public ActivityReviewPage(final CloseEvents closeEvent, final AccountData account, final ActivityData activity) {
        super(PAGE_NAME + activity.name(), closeEvent);
        // TODO: Completa con le recensioni di una attivitá
        // Finalize the window
        this.buildWindow();
    }
}
