package oop.sunfun.database.dao;

import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.connection.SunFunDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDAO {

    /**
     * The connection to the database, protected to assure it's only one (for performance).
     */
    protected static final IDatabaseConnection DB_CONNECTION;

    static {
        DB_CONNECTION = SunFunDatabase.getDatabaseInstance();
    }

    /**
     * Method used to properly log errors within the various DAO objects.
     * @param logger The logger used to log.
     * @param logLevel The level at which to log the error at.
     * @param message The message to log.
     * @param error The error to trace with the log.
     */
    public static void bracedLog(final Logger logger, final Level logLevel, final String message,
                                 final Throwable error) {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, message, error);
        }
    }

    /**
     * Method used to properly log errors within the various DAO objects.
     * @param logger The logger used to log.
     * @param logLevel The level at which to log the error at.
     * @param message The message to log.
     */
    public static void bracedLog(final Logger logger, final Level logLevel, final String message) {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, message);
        }
    }
}
