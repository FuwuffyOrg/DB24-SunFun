package oop.sunfun.database.dao;

import oop.sunfun.database.connection.IDatabaseConnection;
import oop.sunfun.database.connection.SunFunDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDAO {

    protected static final IDatabaseConnection DB_CONNECTION;
    static {
        DB_CONNECTION = SunFunDatabase.getDatabaseInstance();
    }

    public static void bracedLog(final Logger logger, final Level logLevel, final String message,
                                 final Throwable error) {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, message, error);
        }
    }

    public static void bracedLog(final Logger logger, final Level logLevel, final String message) {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, message);
        }
    }
}
