package oop.sunfun.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the IDatabaseConnection interface.
 */
public class DatabaseConnection implements IDatabaseConnection {
    /**
     * Logger to help show errors in try catches.
     */
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    /**
     * The url location of the db.
     */
    private final String url;

    /**
     * The username of the user to log in to the db.
     */
    private final String user;

    /**
     * The password of the user to log in to the db.
     */
    private final String password;

    /**
     * The connection to the database to run queries with.
     */
    private Connection connection;

    /**
     * Constructor for the connection to the database.
     * @param dbName The name of the database to connect to.
     * @param dbUrl The url of the database's location.
     * @param dbUser The username to log in to the database.
     * @param dbPassword The password to log in to the database.
     */
    public DatabaseConnection(final String dbName, final String dbUrl, final String dbUser, final String dbPassword) {
        this.url = dbUrl + "/" + dbName;
        this.user = dbUser;
        this.password = dbPassword;
    }

    @Override
    public final void openConnection() throws SQLException {
        if (!this.isConnectionOpen()) {
            connection = DriverManager.getConnection(this.url, this.user, this.password);
        }
    }

    @Override
    public final void closeConnection() {
        try {
            if (this.isConnectionOpen()) {
                this.connection.close();
            }
        } catch (final SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not close the connection of the database!", e);
        }
    }

    @Override
    public final boolean isConnectionOpen() throws SQLException {
        return connection != null && !this.connection.isClosed();
    }

    private PreparedStatement prepareStatement(final String query, final Object... parameters) throws SQLException {
        final PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; ++i) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
        return preparedStatement;
    }

    @Override
    public final List<Map<String, Object>> getQueryData(final String query, final Object... parameters)
            throws SQLException {
        if (!this.isConnectionOpen()) {
            throw new SQLException("You need to establish a connection to the server before running a query.");
        }
        try (PreparedStatement preparedStatement = this.prepareStatement(query, parameters)) {
            // Create the empty map to fill
            final List<Map<String, Object>> resultList = new ArrayList<>();
            // Execute the query
            final ResultSet results = preparedStatement.executeQuery();
            // Get the data from the query
            final ResultSetMetaData metaData = results.getMetaData();
            // While there's still data
            while (results.next()) {
                // Add it to the map
                final Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); ++i) {
                    final String columnName = metaData.getColumnName(i);
                    final Object columnValue = results.getObject(i);
                    row.put(columnName, columnValue);
                }
                resultList.add(row);
            }
            // Finish the query
            results.close();
            return resultList;
        }
    }

    @Override
    public final void setQueryData(final String query, final Object... parameters) throws SQLException {
        if (!this.isConnectionOpen()) {
            throw new SQLException("You need to establish a connection to the server before running a query.");
        }
        try (PreparedStatement preparedStatement = this.prepareStatement(query, parameters)) {
            preparedStatement.executeUpdate();
        }
    }
}
