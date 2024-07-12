package oop.sunfun.database.dao;

import oop.sunfun.database.data.forum.CategoryData;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ForumDAO extends AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(ForumDAO.class.getName());

    private static final String FIND_ALL_CATEGORIES = "SELECT * FROM `categoria`";

    private ForumDAO() {
        // Useless constructor
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
}
