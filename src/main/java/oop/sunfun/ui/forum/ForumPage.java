package oop.sunfun.ui.forum;

import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;

public class ForumPage extends GenericPage {

    private static final String PAGE_NAME = "Forums";

    public ForumPage(final CloseEvents closeEvent) {
        super(PAGE_NAME, closeEvent);
    }
}
