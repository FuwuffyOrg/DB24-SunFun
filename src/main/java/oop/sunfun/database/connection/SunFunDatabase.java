package oop.sunfun.database.connection;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Optional;

public final class SunFunDatabase extends DatabaseConnection {
    /**
     * Attribute to keep the singleton instance of this class.
     */
    private static Optional<IDatabaseConnection> singletonInstance = Optional.empty();

    /**
     * The database's name within the server.
     */
    private static final String DATABASE_NAME;

    /**
     * The database's url.
     */
    private static final String DATABASE_URL;

    /**
     * The database's username for logging in to the server.
     */
    private static final String DATABASE_USERNAME;

    /**
     * The database's passowrd for logging in to the server.
     */
    private static final String DATABASE_PASSWORD;

    static {
        final Dotenv dotenv = Dotenv.load();
        DATABASE_NAME = dotenv.get("DATABASE_NAME");
        DATABASE_URL = dotenv.get("DATABASE_URL");
        DATABASE_USERNAME = dotenv.get("DATABASE_USERNAME");
        DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD");
    }

    private SunFunDatabase() {
        super(DATABASE_NAME, DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }

    /**
     * Method to get the instance of this database.
     * @return The database's object instance.
     */
    public static IDatabaseConnection getDatabaseInstance() {
        if (SunFunDatabase.singletonInstance.isEmpty()) {
            SunFunDatabase.singletonInstance = Optional.of(new SunFunDatabase());
        }
        return SunFunDatabase.singletonInstance.get();
    }
}
