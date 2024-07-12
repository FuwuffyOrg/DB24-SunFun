package oop.sunfun.database.connection;

import java.util.Optional;

public final class SunFunDatabase extends DatabaseConnection {
    /**
     * Attribute to keep the singleton instance of this class.
     */
    private static Optional<IDatabaseConnection> singletonInstance = Optional.empty();

    /**
     * The database's name within the server.
     */
    private static final String DATABASE_NAME = "sunfun";

    /**
     * The database's url.
     */
    private static final String DATABASE_URL = "jdbc:mysql://localhost";

    /**
     * The database's username for logging in to the server.
     */
    private static final String DATABASE_USERNAME = "root";

    /**
     * The database's passowrd for logging in to the server.
     */
    private static final String DATABASE_PASSWORD = "";

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
