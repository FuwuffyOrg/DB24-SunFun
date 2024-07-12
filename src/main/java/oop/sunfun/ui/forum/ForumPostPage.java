package oop.sunfun.ui.forum;

import oop.sunfun.database.data.forum.DiscussionData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;

public class ForumPostPage extends GenericPage {

    private final AccountData accountData;

    public ForumPostPage(DiscussionData discussion, CloseEvents closeEvent, final AccountData account) {
        super(discussion.getTitle(), closeEvent);
        this.accountData = account;
        // Finalize the window
        this.buildWindow();
    }
}
