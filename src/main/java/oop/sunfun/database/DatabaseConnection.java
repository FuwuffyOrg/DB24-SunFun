package oop.sunfun.database;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Implementation of the IDatabaseConnection interface.
 */
public class DatabaseConnection implements IDatabaseConnection {
    private final String url;
    private final String user;
    private final String password;

    private Connection connection;

    public DatabaseConnection(final String dbName, final String url, final String user, final String password) {
        this.url = url + "/" + dbName;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(this.url, this.user, this.password);
        }
        return connection;
    }

    @Override
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            this.connection.close();
        }
    }
}
