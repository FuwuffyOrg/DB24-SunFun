package oop.sunfun.database.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface to determine how a database connection
 * should work throughout this project.
 */
public interface IDatabaseConnection {

    /**
     * Method to attempt the connection to a server.
     * @throws SQLException If the connection can't be made.
     */
    void openConnection() throws SQLException;

    /**
     * Method to close the connection to the database.
     * @throws SQLException If the connection can't be closed.
     */
    void closeConnection() throws SQLException;

    /**
     * Method to run a GET query on the database.
     * @param query The query to execute on the server.
     * @return The set of the results of the query.
     * @throws SQLException If the query couldn't run successfully.
     */
    ResultSet getQueryData(String query) throws SQLException;

    /**
     * Method to run a POST query on the database.
     * @param query The query to execute on the server.
     * @throws SQLException If the query couldn't run successfully.
     */
    void setQueryData(String query) throws SQLException;
}
