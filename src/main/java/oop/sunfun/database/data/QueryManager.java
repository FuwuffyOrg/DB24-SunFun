package oop.sunfun.database.data;

import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.connection.SunFunDatabase;
import oop.sunfun.database.data.forum.CategoryData;
import oop.sunfun.database.data.login.AccountData;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class QueryManager {
    private static final Logger LOGGER = Logger.getLogger(QueryManager.class.getName());

    private static final IDatabaseConnection DB_CONNECTION;
    static {
        DB_CONNECTION = SunFunDatabase.getDatabaseInstance();
    }

    private static final String FIND_ALL_CATEGORIES = "SELECT * FROM `categoria`";

    private static final String FIND_ACCOUNT_BY_EMAIL_PASSWORD = "SELECT * FROM `account` WHERE `email` = ? AND password = ?";

    private QueryManager() {
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

    public static Optional<AccountData> getAccountByEmailAndPassword(final String email, final String password) {
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(FIND_ACCOUNT_BY_EMAIL_PASSWORD, email, password);
            // If there's more than one account, there must have been an error
            if (queryData.size() > 1) {
                LOGGER.log(Level.WARNING, "There's two accounts with the same email and password");
            }
            // Get the data and build an account record
            return Optional.of(new AccountData(queryData.getFirst()));
        } catch (final SQLException err) {
            LOGGER.log(Level.SEVERE, "Couldn't fetch the categories", err);
            DB_CONNECTION.closeConnection();
        }
        return Optional.empty();
    }
}
