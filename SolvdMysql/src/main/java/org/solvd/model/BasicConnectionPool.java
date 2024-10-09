package org.solvd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BasicConnectionPool {
    private static String url;
    private static String user;
    private static String password;
    private static List<Connection> connectionPool = new ArrayList<>();
    private static final List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 5;

    private BasicConnectionPool(String url, String user, String password, List<Connection> pool) {
        BasicConnectionPool.url = url;
        BasicConnectionPool.user = user;
        BasicConnectionPool.password = password;
        connectionPool = pool;
    }

    public static BasicConnectionPool create() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        user = resourceBundle.getString("user");
        password = resourceBundle.getString("password");
        url = resourceBundle.getString("url");
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new BasicConnectionPool(url, user, password, pool);
    }

    public Connection getConnection() {
        synchronized (connectionPool) {
            while (connectionPool.isEmpty()) {
                try {
                    connectionPool.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupt status
                    throw new RuntimeException("Thread interrupted while waiting for a connection", e);
                }
            }
            Connection connection = connectionPool.remove(connectionPool.size() - 1);
            usedConnections.add(connection);
            return connection;
        }
    }

    public boolean releaseConnection(Connection connection) {
        synchronized (connectionPool) {
            connectionPool.add(connection);
            usedConnections.remove(connection);
            connectionPool.notifyAll();
            return true;
        }
    }

    private static Connection createConnection(String url, String user, String password) {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}