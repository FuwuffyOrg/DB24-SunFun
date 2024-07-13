package oop.sunfun.ui.forum;

import oop.sunfun.database.dao.ForumDAO;
import oop.sunfun.database.data.forum.CommentData;
import oop.sunfun.database.data.forum.DiscussionData;
import oop.sunfun.database.data.login.AccountData;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.Anchors;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;

public final class ForumPostPage extends GenericPage {

    private final AccountData accountData;

    public ForumPostPage(final DiscussionData discussion, final CloseEvents closeEvent, final AccountData account) {
        super(discussion.getTitle(), closeEvent);
        this.accountData = account;
        // Get the comments on the post
        final Component lblDescription = new JLabel(discussion.getDescription());
        final Component commentArea = this.getCommentArea(discussion);
        final AbstractButton btnGotoDiscussion = new JButton("Return to discussions");
        final JTextComponent txtComment = new JTextArea();
        final AbstractButton btnComment = new JButton("Send");
        this.add(lblDescription, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setWidth(3)
                .setAnchor(Anchors.TOP)
                .setFillAll()
                .build()
        );
        this.add(commentArea, new GridBagConstraintBuilder()
                .setRow(1).setColumn(0)
                .setWidth(3)
                .setAnchor(Anchors.CENTER)
                .setFillAll()
                .build()
        );
        this.add(btnGotoDiscussion, new GridBagConstraintBuilder()
                .setRow(2).setColumn(0)
                .setFillAll()
                .setAnchor(Anchors.BOTTOM_LEFT)
                .setWeightColumn(0.03d)
                .build()
        );
        this.add(txtComment, new GridBagConstraintBuilder()
                .setRow(2).setColumn(1)
                .setFillAll()
                .setAnchor(Anchors.BOTTOM)
                .setWeightColumn(0.1d)
                .build()
        );
        this.add(btnComment, new GridBagConstraintBuilder()
                .setRow(2).setColumn(2)
                .setFillAll()
                .setAnchor(Anchors.BOTTOM_RIGHT)
                .setWeightColumn(0.01d)
                .build()
        );
        // Add events
        btnGotoDiscussion.addActionListener(e -> this.switchPage(new ForumPage(CloseEvents.EXIT_PROGRAM, account)));
        // TODO: add comment action
        // Finalize the window
        this.buildWindow();
    }

    private Component getCommentArea(final DiscussionData discussion) {
        final JComponent commentPanel = new JPanel();
        commentPanel.setLayout(new GridBagLayout());
        final Component commentArea = new JScrollPane(commentPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        final List<CommentData> comments = ForumDAO.getCommentsFromDiscussion(discussion.getDiscussionNumber());
        IntStream.range(0, comments.size()).forEach(i -> {
            // Add the panel to the categories
            commentPanel.add(this.createCommentHeader(comments.get(i)), new GridBagConstraintBuilder()
                    .setRow(i).setColumn(0)
                    .setFillAll()
                    .build()
            );
        });
        return commentArea;
    }

    private Component createCommentHeader(final CommentData comment) {
        final JComponent commentHeader = new JPanel();
        commentHeader.setLayout(new GridBagLayout());
        final JLabel lblPerson = new JLabel(comment.getName() + " " + comment.getSurname());
        final JLabel lblComment = new JLabel(comment.getResponse());
        commentHeader.add(lblPerson, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setFillAll()
                .build()
        );
        commentHeader.add(lblComment, new GridBagConstraintBuilder()
                .setRow(0).setColumn(1)
                .setFillAll()
                .build()
        );
        return commentHeader;
    }
}
