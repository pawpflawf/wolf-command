package me.puppyize.wolfcommand.database;

import java.sql.SQLException;

public interface _DB_BASE {
    void getConnection() throws ClassNotFoundException, SQLException;
    void closeConnection() throws SQLException;

    int getVersion() throws SQLException;

    void upgradeDatabase() throws SQLException, ClassNotFoundException;

}
