package com.rankup.empire.currencies.feat.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLProvider {

    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS `empire_currencies` (" +
            "user_id CHAR(36) NOT NULL PRIMARY KEY, " +
            "user_name VARCHAR(64), " +
            "coins DOUBLE " +
            ");";

    protected final HikariDataSource source = new HikariDataSource();

    public MySQLProvider(String host, int port, String database, String user, String password) {
        source.setPoolName("essentials");
        source.setMaxLifetime(1800000L);

        source.setMaximumPoolSize(10);
        source.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        source.setUsername(user);
        source.setPassword(password);

        source.addDataSourceProperty("autoReconnect", "true");
        source.addDataSourceProperty("useSLL", "false");
        source.addDataSourceProperty("characterEncoding", "utf-8");
        source.addDataSourceProperty("encoding", "UTF-8");
        source.addDataSourceProperty("useUnicode", "true");
        source.addDataSourceProperty("rewriteBatchedStatements", "true");
        source.addDataSourceProperty("jdbcCompliantTruncation", "false");
        source.addDataSourceProperty("cachePrepStmts", "true");
        source.addDataSourceProperty("prepStmtCacheSize", "275");
        source.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }

    public void init() {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
                statement.execute();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void closeConnection() {
        source.close();
    }

    public Connection getConnection() {
        try {
            return source.getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

}