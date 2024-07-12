package oop.sunfun.ui.forum;

import oop.sunfun.database.data.forum.DiscussionData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;

public final class ForumPostPage extends GenericPage {

    private final AccountData accountData;

    public ForumPostPage(final DiscussionData discussion, final CloseEvents closeEvent, final AccountData account) {
        super(discussion.getTitle(), closeEvent);
        this.accountData = account;
        // Finalize the window
        this.buildWindow();
    }
}
