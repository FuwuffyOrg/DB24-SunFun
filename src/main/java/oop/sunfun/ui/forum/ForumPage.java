package oop.sunfun.ui.forum;

import oop.sunfun.database.dao.ForumDAO;
import oop.sunfun.database.data.forum.CategoryData;
import oop.sunfun.database.data.forum.DiscussionData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.Anchors;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public final class ForumPage extends GenericPage {
    private static final Logger LOGGER = Logger.getLogger(ForumPage.class.getName());

    private static final String PAGE_NAME = "Forums";

    private final AccountData accountData;

    public ForumPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // Create the tabs with all the posts
        final JTabbedPane pane = this.createTabs();
        // Add them to the page
        this.add(pane, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(2)
                .setAnchor(Anchors.TOP)
                .setFillAll()
                .build()
        );
        // Add a button to create a new post
        final AbstractButton btnNewPost = new JButton("Crea un nuovo post");
        final AbstractButton btnDashboard = new JButton("Torna alla dashboard");
        this.add(btnNewPost, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setFillAll()
                .build());
        this.add(btnDashboard, new GridBagConstraintBuilder()
                .setRow(1).setColumn(1)
                .setFillAll()
                .build());
        btnNewPost.addActionListener(e -> this.switchPage(new CreateForumPage(CloseEvents.EXIT_PROGRAM, this.accountData)));
        btnDashboard.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM, this.accountData)));
        // Finalize the window
        this.buildWindow();
    }

    private JTabbedPane createTabs() {
        // Create the basic stuff for the query and display
        final JTabbedPane pane = new JTabbedPane();
        final Set<CategoryData> categories = ForumDAO.getAllCategories();
        for (final CategoryData category : categories) {
            final JComponent panel = this.addPanelCategory(category);
            pane.add(panel, category.name());
        }
        return pane;
    }

    private JComponent addPanelCategory(final CategoryData category) {
        // Create the stuff to display
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        final JScrollPane scrollPanel = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Fetch all forum posts from that name
        final List<DiscussionData> discussions = ForumDAO.getAllPostsFromCategory(category);
        IntStream.range(0, discussions.size()).forEach(i -> {
            // Add the panel to the categories
            panel.add(this.createDiscussionHeader(discussions.get(i)), new GridBagConstraintBuilder()
                    .setRow(i).setColumn(0)
                    .setFillHorizontal()
                    .build()
            );
        });
        return scrollPanel;
    }

    private JPanel createDiscussionHeader(final DiscussionData discussion) {
        final JPanel discussionHeader = new JPanel();
        discussionHeader.setLayout(new GridBagLayout());
        final JLabel lblPerson = new JLabel(discussion.getEmail());
        final JLabel lblTitle = new JLabel(discussion.getTitle());
        final AbstractButton btnEnterDiscussion = new JButton("Enter Discussion");
        discussionHeader.add(lblPerson, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setMarginAll(4)
                .setPadAll(2)
                .setFillHorizontal()
                .build());
        discussionHeader.add(lblTitle, new GridBagConstraintBuilder()
                .setRow(0).setColumn(1)
                .setMarginAll(4)
                .setPadAll(2)
                .setFillHorizontal()
                .build());
        discussionHeader.add(btnEnterDiscussion, new GridBagConstraintBuilder()
                .setRow(0).setColumn(2)
                .setMarginAll(4)
                .setPadAll(2)
                .setFillHorizontal()
                .build());
        btnEnterDiscussion.addActionListener(e -> this.switchPage(new ForumPostPage(discussion, CloseEvents.EXIT_PROGRAM,
                this.accountData)));
        return discussionHeader;
    }
}
