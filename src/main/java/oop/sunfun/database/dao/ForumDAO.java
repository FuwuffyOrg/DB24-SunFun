package oop.sunfun.database.dao;

import oop.sunfun.database.data.forum.CategoryData;
import oop.sunfun.database.data.forum.CommentData;
import oop.sunfun.database.data.forum.DiscussionData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ForumDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(ForumDAO.class.getName());

    private static final String FIND_ALL_CATEGORIES = "SELECT `categoria`.nome FROM `categoria`";

    private static final String GET_ALL_POSTS_FROM_CATEGORY = "SELECT d.*, a.nome, a.cognome FROM discussione d, "
            + "account_data a WHERE d.fk_categoria = ? ORDER BY d.num_discussione";

    private static final String GET_COMMENTS_FROM_ID = "SELECT r.num_risposta, r.testo, a.nome, a.cognome "
            + "FROM `risposta` r, `account_data` a WHERE r.fk_discussione = ?";

    private static final String CREATE_FORUM_POST = "INSERT INTO `discussione`(`titolo`, `descrizione`, "
            + "`fk_categoria`, `fk_account`) VALUES (?,?,?,?)";

    private static final String CREATE_COMMENT_POST = "INSERT INTO `risposta`(`testo`, `fk_discussione`, "
            + "`fk_account`) VALUES (?,?,?)";

    private ForumDAO() {
        super();
    }

    public static Set<CategoryData> getAllCategories() {
        final Set<CategoryData> categories = new HashSet<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(FIND_ALL_CATEGORIES);
            for (final Map<String, Object> category : queryData) {
                categories.add(new CategoryData((String) category.get("nome")));
            }
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't fetch the categories", err);
            DB_CONNECTION.closeConnection();
        }
        return categories;
    }

    public static List<DiscussionData> getAllPostsFromCategory(final CategoryData category) {
        final List<DiscussionData> categories = new ArrayList<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_ALL_POSTS_FROM_CATEGORY,
                    category.name());
            for (final Map<String, Object> discussion : queryData) {
                categories.add(new DiscussionData(discussion));
            }
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't fetch the posts from the category " + category.name(), err);
            DB_CONNECTION.closeConnection();
        }
        return categories;
    }

    public static List<CommentData> getCommentsFromDiscussion(final int discussionId) {
        final List<CommentData> comments = new ArrayList<>();
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(GET_COMMENTS_FROM_ID, discussionId);
            for (final Map<String, Object> comment : queryData) {
                comments.add(new CommentData(comment));
            }
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't fetch the comments for the discussion " + discussionId, err);
            DB_CONNECTION.closeConnection();
        }
        return comments;
    }

    public static void addNewDiscussion(final String title, final String description,
                                        final CategoryData category, final String accountEmail) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_FORUM_POST, title, description, category.name(), accountEmail);
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't create a new discussion", err);
            DB_CONNECTION.closeConnection();
        }
    }

    public static void addNewComment(final String accountEmail, final String description, final int discussionId) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_COMMENT_POST, description, discussionId, accountEmail);
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't create a new comment for " + discussionId, err);
            DB_CONNECTION.closeConnection();
        }
    }
}
