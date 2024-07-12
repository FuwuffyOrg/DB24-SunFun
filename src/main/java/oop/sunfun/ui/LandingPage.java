package oop.sunfun.ui;

import oop.sunfun.database.data.AccountData;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;

public class LandingPage extends GenericPage {

    public LandingPage(final String title, final CloseEvents closeEvent, final AccountData account) {
        super(title, closeEvent);
    }


}
