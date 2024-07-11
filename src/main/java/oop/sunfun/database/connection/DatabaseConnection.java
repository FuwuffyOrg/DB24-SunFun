package oop.sunfun.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public Connection getConnection() throws SQLException {
        if (!this.isConnectionValid()) {
            connection = DriverManager.getConnection(this.url, this.user, this.password);
        }
        return connection;
    }

    @Override
    public void closeConnection() throws SQLException {
        if (this.isConnectionValid()) {
            this.connection.close();
        }
    }

    @Override
    public ResultSet executeQuery(final String query) throws SQLException {
        if (!this.isConnectionValid()) {
            throw new SQLException("You need to establish a connection to the server before running a query.");
        }
        final Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }
}
