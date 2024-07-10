package oop.sunfun.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface to determine how a database connection should work throughout this project.
 */
public interface IDatabaseConnection {

    /**
     * Method to attempt the connection to a server.
     * @return The singleton instance of the connection.
     * @throws SQLException If the connection can't be made.
     */
    Connection getConnection() throws SQLException;

    /**
     * Closes the connection to the database.
     * @throws SQLException If the connection can't be closed.
     */
    void closeConnection() throws SQLException;
}
