package oop.sunfun.ui.forum;

import oop.sunfun.database.data.forum.DiscussionData;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;

public class ForumPostPage extends GenericPage {

    public ForumPostPage(DiscussionData discussion, CloseEvents closeEvent) {
        super(discussion.getTitle(), closeEvent);
        // Finalize the window
        this.buildWindow();
    }
}
