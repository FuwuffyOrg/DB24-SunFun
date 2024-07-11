package oop.sunfun.database.connection;

import java.sql.*;
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

    @Override
    public ResultSet getQueryData(final String query, final Object... parameters) throws SQLException {
        if (!this.isConnectionValid()) {
            throw new SQLException("You need to establish a connection to the server before running a query.");
        }
        final PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        IntStream.range(0, parameters.length).forEach(i -> {
            try {
                preparedStatement.setObject(i + 1, parameters[i]);
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return preparedStatement.executeQuery(query);
    }

    @Override
    public void setQueryData(final String query, final Object... parameters) throws SQLException {
        if (!this.isConnectionValid()) {
            throw new SQLException("You need to establish a connection to the server before running a query.");
        }
        final PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        IntStream.range(0, parameters.length).forEach(i -> {
            try {
                preparedStatement.setObject(i + 1, parameters[i]);
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        });
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
