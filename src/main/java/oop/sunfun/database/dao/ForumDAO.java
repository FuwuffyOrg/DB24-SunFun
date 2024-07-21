package oop.sunfun.database.dao;

import oop.sunfun.database.data.forum.CategoryData;
import oop.sunfun.database.data.forum.CommentData;
import oop.sunfun.database.data.forum.DiscussionData;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ForumDAO extends AbstractDAO {
    /**
     * Logger to help diagnose sql and database errors.
     */
    private static final Logger LOGGER = Logger.getLogger(ForumDAO.class.getName());

    /**
     * Query to fetch all the forum categories within the database.
     */
    private static final String FIND_ALL_CATEGORIES = "SELECT `categoria`.`nome` FROM `categoria`;";

    /**
     * Query to fetch all the posts of a category within the database.
     */
    private static final String GET_ALL_POSTS_FROM_CATEGORY = "SELECT `d`.*, `a`.`nome`, "
            + "`a`.`cognome` FROM `discussione` `d` JOIN `account_data` `a` ON `d`.`fk_account` = `a`.`email` "
            + "WHERE `d`.`fk_categoria`=? ORDER BY `d`.`num_discussione`;";

    /**
     * Query to fetch all the comments of a forum post within the database.
     */
    private static final String GET_COMMENTS_FROM_ID = "SELECT `r`.`num_risposta`, `r`.`testo`, `a`.`nome`, "
            + "`a`.`cognome` FROM `risposta` `r` JOIN `account_data` `a` ON `r`.`fk_account` = `a`.`email` "
            + "WHERE `r`.`fk_discussione`=?;";

    /**
     * Query to create a new forum post.
     */
    private static final String CREATE_FORUM_POST = "INSERT INTO `discussione`(`titolo`, `descrizione`, "
            + "`fk_categoria`, `fk_account`) VALUES (?,?,?,?);";

    /**
     * Query to create a new comment for a forum post.
     */
    private static final String CREATE_COMMENT_POST = "INSERT INTO `risposta`(`testo`, `fk_discussione`, "
            + "`fk_account`) VALUES (?,?,?);";

    /**
     * Fetches all the forum categories from the database.
     * @return All the forum categories within the database.
     */
    public static Set<CategoryData> getAllCategories() {
        final Set<CategoryData> categories = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(FIND_ALL_CATEGORIES);
            for (final Map<String, Object> category : queryData) {
                categories.add(new CategoryData((String) category.get("nome")));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the categories", err);
            DB_CONNECTION.closeConnection();
        }
        return categories;
    }

    /**
     * Fetches all the forum posts of a given categories from the database.
     * @param category The category to fetch the posts from.
     * @return All the forum posts of a given category within the database.
     */
    public static Set<DiscussionData> getAllPostsFromCategory(final CategoryData category) {
        final Set<DiscussionData> categories = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_POSTS_FROM_CATEGORY,
                    category.name());
            for (final Map<String, Object> discussion : queryData) {
                final int numDiscussion = (Integer) discussion.get("num_discussione");
                final String title = (String) discussion.get("titolo");
                final String description = (String) discussion.get("description");
                final String name = (String) discussion.get("nome");
                final String surname = (String) discussion.get("cognome");
                categories.add(new DiscussionData(numDiscussion, title, description, name, surname));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the posts from the category " + category.name(),
                    err);
            DB_CONNECTION.closeConnection();
        }
        return categories;
    }

    /**
     * Fetches all the comments of a forum post from the database.
     * @param discussionId The id of the discussion to fetch the comments from.
     * @return All the comments of a forum post within the database.
     */
    public static Set<CommentData> getCommentsFromDiscussion(final int discussionId) {
        final Set<CommentData> comments = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_COMMENTS_FROM_ID, discussionId);
            for (final Map<String, Object> comment : queryData) {
                final int numResponse = (Integer) comment.get("num_risposta");
                final String text = (String) comment.get("testo");
                final String name = (String) comment.get("nome");
                final String surname = (String) comment.get("cognome");
                comments.add(new CommentData(numResponse, text, name, surname));
            }
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the comments for the discussion " + discussionId, err);
            DB_CONNECTION.closeConnection();
        }
        return comments;
    }

    /**
     * Creates a new discussion in the database.
     * @param title The title of the discussion.
     * @param description The description of the discussion.
     * @param category The category the discussion will be part of.
     * @param accountEmail The email of the creator of the discussion.
     */
    public static void addNewDiscussion(final String title, final String description,
                                        final CategoryData category, final String accountEmail) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_FORUM_POST, title, description, category.name(), accountEmail);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create a new discussion", err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Creates a new comment in the database.
     * @param accountEmail The email of the creator of the comment.
     * @param description What the comment says.
     * @param discussionId The discussion the comment is for.
     */
    public static void addNewComment(final String accountEmail, final String description, final int discussionId) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_COMMENT_POST, description, discussionId, accountEmail);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create a new comment for " + discussionId, err);
            DB_CONNECTION.closeConnection();
        }
    }
}
