package oop.sunfun.ui.forum;

import oop.sunfun.database.dao.ForumDAO;
import oop.sunfun.database.data.forum.CategoryData;
import oop.sunfun.database.data.forum.DiscussionData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.LandingPage;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.layout.Anchors;
import oop.sunfun.ui.util.pages.GenericPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public final class ForumPage extends GenericPage {

    private static final String PAGE_NAME = "Forums";

    private final AccountData accountData;

    public ForumPage(final CloseEvents closeEvent, final AccountData account) {
        super(PAGE_NAME, closeEvent);
        this.accountData = account;
        // Create the tabs with all the posts and add them to the page
        this.add(this.createTabs(), new GridBagConstraintBuilder()
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
        btnNewPost.addActionListener(e -> this.switchPage(new CreateForumPage(CloseEvents.EXIT_PROGRAM,
                this.accountData)));
        btnDashboard.addActionListener(e -> this.switchPage(new LandingPage(CloseEvents.EXIT_PROGRAM,
                this.accountData)));
        // Finalize the window
        this.buildWindow();
    }

    private JComponent createTabs() {
        // Create the basic stuff for the query and display
        final JComponent pane = new JTabbedPane();
        final Set<CategoryData> categories = ForumDAO.getAllCategories();
        for (final CategoryData category : categories) {
            pane.add(this.createPanelCategory(category), category.name());
        }
        return pane;
    }

    private Component createPanelCategory(final CategoryData category) {
        // Create the stuff to display
        final JComponent panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        final Component scrollPanel = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Fetch all forum posts from that name
        final List<DiscussionData> discussions = ForumDAO.getAllPostsFromCategory(category);
        IntStream.range(0, discussions.size()).forEach(i -> {
            // Add the panel to the categories
            panel.add(this.createDiscussionHeader(discussions.get(i)), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            panel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setFillAll()
                    .build()
            );
        });
        return scrollPanel;
    }

    private JComponent createDiscussionHeader(final DiscussionData discussion) {
        final JComponent discussionHeader = new JPanel();
        discussionHeader.setLayout(new GridBagLayout());
        final Component lblPerson = new JLabel(discussion.name() + " " + discussion.surname());
        final Component lblTitle = new JLabel(discussion.title());
        final AbstractButton btnEnterDiscussion = new JButton("Enter Discussion");
        discussionHeader.add(lblPerson, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setMarginAll(2)
                .setFillAll()
                .build());
        discussionHeader.add(lblTitle, new GridBagConstraintBuilder()
                .setRow(0).setColumn(1)
                .setMarginAll(2)
                .setFillAll()
                .build());
        discussionHeader.add(btnEnterDiscussion, new GridBagConstraintBuilder()
                .setRow(0).setColumn(2)
                .setMarginAll(2)
                .setFillAll()
                .build());
        btnEnterDiscussion.addActionListener(e -> this.switchPage(new ForumPostPage(discussion,
                CloseEvents.EXIT_PROGRAM, this.accountData)));
        return discussionHeader;
    }
}
