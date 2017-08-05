package me.puppyize.wolfcommand.database;

import me.puppyize.wolfcommand.database.upgrades.MySQL_0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL implements _DB_BASE {

    private Connection conn;
    private String host, database, username, password;
    private int port;

    public MySQL(String host, int port, String database, String username, String password) throws Exception {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

         this.upgradeDatabase();
    }

    @Override
    public void getConnection() throws ClassNotFoundException, SQLException {
        if (this.conn != null && !this.conn.isClosed()) {
            return;
        }

        synchronized (this) {
            if (this.conn != null && !this.conn.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        }
    }

    private Statement getStatement() throws SQLException, ClassNotFoundException {
        this.getConnection();
        return this.conn.createStatement();
    }

    @Override
    public void closeConnection() throws SQLException {
        if(this.conn != null){
            this.conn.close();
        }
    }

    @Override
    public int getVersion() throws SQLException {


        return 0;
    }

    @Override
    public void upgradeDatabase() throws SQLException, ClassNotFoundException {
        int versions = this.getVersion();

        switch (versions){
            case 0:
                new MySQL_0(this.conn);
            case 1:
                // Not Implemented Yet
            case 2:
                // Not Implemented Yet
        }
    }
}
