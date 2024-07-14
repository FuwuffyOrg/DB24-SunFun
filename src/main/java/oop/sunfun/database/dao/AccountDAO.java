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
    private static final Logger LOGGER = Logger.getLogger(AccountDAO.class.getName());

    private static final String FIND_ACCOUNT_BY_EMAIL_PASSWORD = "SELECT * FROM `account_data` WHERE `email` = ? "
            + "AND password = ?";

    private static final String CREATE_ACCOUNT_BY_EMAIL_PASSOWRD = "INSERT INTO `account`(`email`, `password`, "
            + "`tipologia`) VALUES (?,?,?)";

    private AccountDAO() {
        // Useless constructor
    }

    public static Optional<AccountData> getAccount(final String email, final String password) {
        try {
            DB_CONNECTION.openConnection();
            final List<Map<String, Object>> queryData = DB_CONNECTION.getQueryData(FIND_ACCOUNT_BY_EMAIL_PASSWORD,
                    email, password);
            // If there's more than one account, there must have been an error
            if (queryData.size() > 1) {
                if (LOGGER.isLoggable(Level.WARNING)) {
                    LOGGER.log(Level.WARNING, "There's two accounts with the same email and password");
                }
            } else if (queryData.isEmpty()) {
                return Optional.empty();
            }
            // Get the data and build an account record
            final Map<String, Object> account = queryData.getFirst();
            final AccountType type = AccountType.getFromString((String) account.get("tipologia"));
            return Optional.of(new AccountData(email, (String) account.get("codice_fiscale"), type));
        } catch (final SQLException err) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE, "Couldn't fetch the account data", err);
            }
            DB_CONNECTION.closeConnection();
        }
        return Optional.empty();
    }

    public static void createAccount(final String email, final String password, final AccountType accountType) {
        try {
            DB_CONNECTION.openConnection();
            DB_CONNECTION.setQueryData(CREATE_ACCOUNT_BY_EMAIL_PASSOWRD, email, password, accountType.getTextValue());
        } catch (final SQLException err) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE, "Couldn't fetch the categories", err);
            }
            DB_CONNECTION.closeConnection();
        }
    }
}
