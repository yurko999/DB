package com.zazhitskiy.database;

import com.zazhitskiy.exception.DatabaseFailedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.zazhitskiy.constants.Messages.FAILED_CREATE_CONNECTION;
import static com.zazhitskiy.constants.Parameters.*;

public final class ConnectionToDB {
    private static volatile ConnectionToDB instance;
    private final Map<Long, Connection> connections;

    public ConnectionToDB() {
        this.connections = new HashMap<>();
    }

    public static ConnectionToDB getInstance() {
        if (instance == null) {
            synchronized (ConnectionToDB.class) {
                instance = new ConnectionToDB();
            }
        }
        return instance;
    }

    private Map<Long, Connection> getAllConnections() {
        return this.connections;
    }

    private void addConnection(Connection connection) {
        getAllConnections().put(Thread.currentThread().getId(), connection);
    }

    public Connection getConnection() {
        Connection connection = getAllConnections().get(Thread.currentThread().getId());
        if (connection == null) {
            try {
                Class.forName(MYSQL_DRIVER);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException | ClassNotFoundException ex) {
                throw new DatabaseFailedException(FAILED_CREATE_CONNECTION);
            }
            addConnection(connection);
        }
        return connection;
    }
}
