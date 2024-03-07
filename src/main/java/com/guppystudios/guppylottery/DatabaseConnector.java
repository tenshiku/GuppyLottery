package com.guppystudios.guppylottery;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    private final GuppyLottery plugin;
    private Connection connection;

    public DatabaseConnector(GuppyLottery plugin){
        this.plugin = plugin;
    }

    public void connect() {
        try {
            if (useMySQL()) {
                connectMySQL();
            } else {
                connectSQLite();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean useMySQL(){
        if (plugin.getConfig().contains("useMySQL")){
            return plugin.getConfig().getBoolean("useMySQL");
        } else {
            plugin.getLogger().warning("Configuration option 'useMySQL' not found. Defaulting to SQL File.");
            return false;
        }
    }

    private void connectMySQL() throws SQLException, ClassNotFoundException {
        String host = plugin.getConfig().getString("mysql.host");
        String port = plugin.getConfig().getString("mysql.port");
        String database = plugin.getConfig().getString("mysql.database");
        String username = plugin.getConfig().getString("mysql.username");
        String password = plugin.getConfig().getString("mysql.password");

        String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcUrl, username, password);

        createTables(); //Create tables if they dont exist
    }

    private void connectSQLite() throws SQLException {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()){
            dataFolder.mkdir();
        }

        String sqlitePath = dataFolder.getAbsolutePath() + File.separator + "data.db";
        String jdbcUrl = "jdbc:sqlite:" + sqlitePath;

        connection = DriverManager.getConnection(jdbcUrl);

        createTables(); // Create tables if they don't exist
    }

    private void createTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS lottery_players ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "player_uuid VARCHAR(36) NOT NULL,"
                    + "tickets_bought INT DEFAULT 0,"
                    + "money_won DOUBLE DEFAULT 0.0,"
                    + "wins INT DEFAULT 0"
                    + ")");
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
