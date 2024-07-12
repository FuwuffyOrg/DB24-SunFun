package oop.sunfun.database.data;

import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.connection.SunFunDatabase;
import oop.sunfun.database.data.forum.CategoryData;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class QueryManager {
    private static final Logger LOGGER = Logger.getLogger(QueryManager.class.getName());

    private static final String FIND_ALL_CATEGORIES = "SELECT * FROM `categoria`";

    private QueryManager() {
        // Useless constructor
    }

    public static Set<CategoryData> getAllCategories() {
        final Set<CategoryData> categories = new HashSet<>();
        final IDatabaseConnection databaseConnection = SunFunDatabase.getDatabaseInstance();
        try {
            databaseConnection.openConnection();
            final List<Map<String, Object>> queryData = databaseConnection.getQueryData(FIND_ALL_CATEGORIES);
            for (final Map<String, Object> category : queryData) {
                categories.add(new CategoryData((String) category.get("nome")));
            }
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't fetch the categories", err);
            databaseConnection.closeConnection();
        }
        return categories;
    }
}
