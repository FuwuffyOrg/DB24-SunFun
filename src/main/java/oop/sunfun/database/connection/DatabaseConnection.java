package oop.sunfun.database.connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Implementation of the IDatabaseConnection interface.
 */
public final class DatabaseConnection implements IDatabaseConnection {
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

    private boolean isConnectionValid() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    @Override
    public void openConnection() throws SQLException {
        if (!this.isConnectionValid()) {
            connection = DriverManager.getConnection(this.url, this.user, this.password);
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        if (this.isConnectionValid()) {
            this.connection.close();
        }
    }

    private PreparedStatement prepareStatement(final String query, final Object... parameters) throws SQLException {
        final PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; ++i) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
        return preparedStatement;
    }

    @Override
    public List<Map<String, Object>> getQueryData(final String query, final Object... parameters) throws SQLException {
        if (!this.isConnectionValid()) {
            throw new SQLException("You need to establish a connection to the server before running a query.");
        }
        try (final PreparedStatement preparedStatement = this.prepareStatement(query, parameters)) {
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
    public void setQueryData(final String query, final Object... parameters) throws SQLException {
        if (!this.isConnectionValid()) {
            throw new SQLException("You need to establish a connection to the server before running a query.");
        }
        try (final PreparedStatement preparedStatement = this.prepareStatement(query, parameters)) {
            preparedStatement.executeUpdate();
        }
    }
}
