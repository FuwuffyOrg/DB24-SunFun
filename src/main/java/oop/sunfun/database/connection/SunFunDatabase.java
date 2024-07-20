package oop.sunfun.database.connection;

import io.github.cdimascio.dotenv.Dotenv;

public final class SunFunDatabase extends DatabaseConnection {
    /**
     * Attribute to keep the singleton instance of this class.
     */
    private static IDatabaseConnection singletonInstance;

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
     * The database's password for logging in to the server.
     */
    private static final String DATABASE_PASSWORD;

    static {
        final Dotenv dotenv = Dotenv.load();
        DATABASE_NAME = dotenv.get("DATABASE_NAME");
        DATABASE_URL = dotenv.get("DATABASE_URL");
        DATABASE_USERNAME = dotenv.get("DATABASE_USERNAME");
        DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD");
    }

    /**
     * Constructor for the database using the .env file's properties.
     */
    private SunFunDatabase() {
        super(DATABASE_NAME, DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }

    /**
     * Method to get the instance of this database.
     * @return The database's object instance.
     */
    public static IDatabaseConnection getDatabaseInstance() {
        if (singletonInstance == null) {
            singletonInstance = new SunFunDatabase();
        }
        return singletonInstance;
    }
}
