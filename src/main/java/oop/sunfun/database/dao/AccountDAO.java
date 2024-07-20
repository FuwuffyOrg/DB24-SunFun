package oop.sunfun.database.dao;

import oop.sunfun.database.data.login.AccountType;
import oop.sunfun.database.data.login.AccountData;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AccountDAO extends AbstractDAO {
    /**
     * Logger to help diagnose sql and database errors.
     */
    private static final Logger LOGGER = Logger.getLogger(AccountDAO.class.getName());

    /**
     * Query to get the account using that email and password.
     */
    private static final String FIND_ACCOUNT_BY_EMAIL_PASSWORD = "SELECT * FROM `account_data` WHERE `email` = ? "
            + "AND password = PASSWORD(?)";

    /**
     * Query to create the account with a given email and password.
     */
    private static final String CREATE_ACCOUNT_BY_EMAIL_PASSOWRD = "INSERT INTO `account`(`email`, `password`, "
            + "`tipologia`) VALUES (?,PASSWORD(?),?)";

    /**
     * Query to delete the account, based on the email.
     */
    private static final String DELETE_ACCOUNT_BY_EMAIL = "DELETE `a` FROM ACCOUNT `a` WHERE `a`.`email` = ?";

    /**
     * Tries to fetch the account with password and email.
     * @param email The email of the account.
     * @param password The password used on that account.
     * @return A valid optional if an account is found with those informations.
     */
    public static Optional<AccountData> getAccount(final String email, final String password) {
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(FIND_ACCOUNT_BY_EMAIL_PASSWORD,
                    email, password);
            // If there's more than one account, there must have been an error
            if (queryData.size() > 1) {
                bracedLog(LOGGER, Level.WARNING, "There's two accounts with the same email and password");
            } else if (queryData.isEmpty()) {
                return Optional.empty();
            }
            // Get the data and build an account record
            final Map<String, Object> account = queryData.getFirst();
            final AccountType type = AccountType.getFromString((String) account.get("tipologia"));
            return Optional.of(new AccountData(email, (String) account.get("codice_fiscale"), type));
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't fetch the account data", err);
            DB_CONNECTION.closeConnection();
        }
        return Optional.empty();
    }

    /**
     * Creates a new account within the database.
     * @param email The email of the account.
     * @param password The password of the account.
     * @param accountType The type of the account for permissions.
     */
    public static void createAccount(final String email, final String password, final AccountType accountType) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_ACCOUNT_BY_EMAIL_PASSOWRD, email, password, accountType.getTextValue());
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't create the account " + email, err);
            DB_CONNECTION.closeConnection();
        }
    }

    /**
     * Deletes an account with the given email.
     * @param email The email of the account to delete.
     */
    public static void eraseAccount(final String email) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(DELETE_ACCOUNT_BY_EMAIL, email);
        } catch (final SQLException err) {
            bracedLog(LOGGER, Level.SEVERE, "Couldn't delete the account " + email, err);
            DB_CONNECTION.closeConnection();
        }
    }
}
