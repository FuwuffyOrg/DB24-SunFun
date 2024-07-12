package oop.sunfun.database.connection;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
     */
    void closeConnection();

    /**
     * Method to check if a database's connection is still up.
     * @return True if the connection is up, false otherwise.
     * @throws SQLException If the action was not successful.
     */
    boolean isConnectionOpen() throws SQLException;

    /**
     * Method to run a SELECT query on the database.
     * @param query The query to execute on the server.
     * @param parameters The ? in the query will be replaced by these objects.
     * @return The set of the results of the query.
     * @throws SQLException If the query couldn't run successfully.
     */
    List<Map<String, Object>> getQueryData(String query, Object... parameters) throws SQLException;

    /**
     * Method to run a INSERT query on the database.
     * @param query The query to execute on the server.
     * @param parameters The ? in the query will be replaced by these objects.
     * @throws SQLException If the query couldn't run successfully.
     */
    void setQueryData(String query, Object... parameters) throws SQLException;
}
