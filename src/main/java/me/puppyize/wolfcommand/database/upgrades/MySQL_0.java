package me.puppyize.wolfcommand.database.upgrades;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class MySQL_0 {

    private Statement stmt;

    public MySQL_0(Connection conn) throws SQLException {
        this.stmt = conn.createStatement();

        try {
            this.create_config_table();

        } catch(SQLException e){
            getLogger().log(Level.SEVERE, e.getMessage());
        }
    }

    private void create_config_table() throws SQLException{
        this.stmt.executeUpdate("");
    }


}
