package oop.sunfun.ui.forum;

import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.connection.SunFunDatabase;
import oop.sunfun.database.data.forum.CategoryData;
import oop.sunfun.database.data.forum.DiscussionData;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.Anchors;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class ForumPage extends GenericPage {
    private static final Logger LOGGER = Logger.getLogger(ForumPage.class.getName());

    private static final String PAGE_NAME = "Forums";

    public ForumPage(final CloseEvents closeEvent) {
        super(PAGE_NAME, closeEvent);
        // Create the tabs with all the posts
        final JTabbedPane pane = this.createTabs();
        // Add them to the page
        this.addPanelComponent(pane, new GridBagConstraintBuilder()
                .setRow(0).setColumn(0)
                .setAnchor(Anchors.TOP)
                .setFillAll()
                .build()
        );
        // Finalize the window
        this.buildWindow();
    }

    private JTabbedPane createTabs() {
        // Create the basic stuff for the query and display
        final JTabbedPane pane = new JTabbedPane();
        final String categoryQuery = "SELECT * FROM `categoria`";
        final IDatabaseConnection database = SunFunDatabase.getDatabaseInstance();
        try {
            database.openConnection();
            // Get all the categories
            final List<Map<String, Object>> results = database.getQueryData(categoryQuery);
            for (final Map<String, Object> cat : results) {
                final CategoryData category = new CategoryData((String) cat.get("nome"));
                // Add a new panel for each name
                final JComponent panel = this.addPanelCategory(category);
                pane.add(panel, category.name());
            }
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't fetch the categories", err);
            database.closeConnection();
            this.close();
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
        // TODO: Fix query to get email too
        final String categoryQuery = "SELECT * FROM `discussione` WHERE `fk_categoria` = ?";
        final IDatabaseConnection database = SunFunDatabase.getDatabaseInstance();
        try {
            database.openConnection();
            // Get all the discussions with that name
            final List<Map<String, Object>> results = database.getQueryData(categoryQuery, category.name());
            IntStream.range(0, results.size()).forEach(i -> {
                for (final Map<String, Object> post : results) {
                    // Add the panel to the categories
                    panel.add(this.createDiscussionHeader(new DiscussionData(post)), new GridBagConstraintBuilder()
                            .setRow(i).setColumn(0)
                            .setFillHorizontal()
                            .build()
                    );
                }
            });
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't fetch the posts for the name " + category.name(), err);
            database.closeConnection();
            this.close();
        }
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
        btnEnterDiscussion.addActionListener(e -> this.switchPage(new ForumPostPage(discussion, CloseEvents.EXIT_PROGRAM)));
        return discussionHeader;
    }
}
