package oop.sunfun.ui.forum;

import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;

public class CreateForumPage extends GenericPage {

    private static final String PAGE_NAME = "Create A Post";

    public CreateForumPage(final CloseEvents closeEvent) {
        super(PAGE_NAME, closeEvent);
    }
}
