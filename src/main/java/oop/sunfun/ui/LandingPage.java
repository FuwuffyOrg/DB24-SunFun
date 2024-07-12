package oop.sunfun.ui;

import oop.sunfun.database.data.AccountData;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;

public class LandingPage extends GenericPage {

    private static final String PAGE_NAME = "SunFun Hub";

    public LandingPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
    }
}
