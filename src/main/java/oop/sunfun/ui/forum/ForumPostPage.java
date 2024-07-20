package oop.sunfun.ui.forum;

import oop.sunfun.database.dao.ForumDAO;
import oop.sunfun.database.data.forum.CommentData;
import oop.sunfun.database.data.forum.DiscussionData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.util.Pair;
import oop.sunfun.ui.util.behavior.CloseEvents;
import oop.sunfun.ui.util.pages.FormPage;
import oop.sunfun.ui.util.layout.GridBagConstraintBuilder;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class ForumPostPage extends FormPage {
    /**
     * Components of the form used by the FormPage class.
     */
    private static final Map<Component, Pair<JComponent, Integer>> FORM_COMPONENTS;

    /**
     * Textbox to keep a comment's contents before posting it to the page.
     */
    private static final JComponent TXT_COMMENT;

    static {
        FORM_COMPONENTS = new LinkedHashMap<>();
        TXT_COMMENT = new JTextArea();
        FORM_COMPONENTS.put(new JLabel("Commento:"), new Pair<>(TXT_COMMENT, 10000));
    }

    /**
     * Constructor for the page of this forum post.
     * @param discussion The discussion the page is for.
     * @param closeEvent The event that happens when you close the page.
     * @param account The account that called this page.
     */
    public ForumPostPage(final DiscussionData discussion, final CloseEvents closeEvent, final AccountData account) {
        super(discussion.title(), closeEvent, 2, FORM_COMPONENTS,
                () -> new ForumPostPage(discussion,  CloseEvents.EXIT_PROGRAM, account),
                () -> new ForumPage(CloseEvents.EXIT_PROGRAM, account),
                () -> ForumDAO.addNewComment(account.email(), ((JTextComponent) TXT_COMMENT).getText(),
                        discussion.discussionNumber()));
        // Get the comments on the post
        this.add(new JLabel(discussion.description()), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(3)
                .setFillAll()
                .build()
        );
        this.add(this.getCommentArea(discussion), new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setWidth(3)
                .setFillAll()
                .build()
        );
        // Finalize the window
        this.buildWindow();
    }

    /**
     * Panel containing all the comments in a table.
     * @param discussion The discussion's information.
     * @return The panel with the comments.
     */
    private Component getCommentArea(final DiscussionData discussion) {
        final JComponent commentPanel = new JPanel();
        commentPanel.setLayout(new GridBagLayout());
        final Component commentArea = new JScrollPane(commentPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        final List<CommentData> comments = ForumDAO.getCommentsFromDiscussion(discussion.discussionNumber());
        IntStream.range(0, comments.size()).forEach(i -> {
            // Add the panel to the categories
            commentPanel.add(this.createCommentHeader(comments.get(i)), new GridBagConstraintBuilder()
                    .setRow(i * 2).setColumn(0)
                    .setFillAll()
                    .build()
            );
            commentPanel.add(new JSeparator(), new GridBagConstraintBuilder()
                    .setRow((i * 2) + 1).setColumn(0)
                    .setFillAll()
                    .build()
            );
        });
        return commentArea;
    }

    /**
     * Creates a row in the table containing the comment's information.
     * @param comment The comment data to display.
     * @return The row of the table with the comment data.
     */
    private Component createCommentHeader(final CommentData comment) {
        final JComponent commentHeader = new JPanel();
        commentHeader.setLayout(new GridBagLayout());
        commentHeader.add(new JLabel(comment.name() + " " + comment.surname()), new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        commentHeader.add(new JLabel(comment.response()), new GridBagConstraintBuilder()
                .setRow(0).setColumn(1)
                .setFillAll()
                .build()
        );
        return commentHeader;
    }
}
