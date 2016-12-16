package me.puppyize.wolfcommand.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Allows connections into SQLite DB
 * </p>
 *
 * @author Pawpy Firelyte <dev@puppyize.me>
 */
public class SQLite {
    private static Connection connection;

    // Open & Close Connections
    public static Connection getConnection(){
        if(connection != null){
            try{
                connection.setAutoCommit(false);
                return connection;
            } catch (SQLException ex){
                Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try{
                connection = DriverManager.getConnection("jdbc:sqlite:" + Bukkit.getPluginManager().getPlugin("WolfCommand").getDataFolder().getAbsolutePath() + "/WolfCommand.db");
                connection.setAutoCommit(false);
                return connection;
            } catch (SQLException ex){
                ex.printStackTrace();
                Bukkit.getPluginManager().getPlugin("WolfCommand").getLogger().log(Level.SEVERE, "Failed to get connection with WolfCommand.db");
            }
        }
        return connection;
    }
    public static void closeConnection(Connection connection){
        try{
            if(connection != null){
                connection.close();
            }
        } catch(SQLException ex){
            System.err.println(ex);
        }
    }

    // Player Ownership
    public static void addWolf(Player p, Wolf w){
        UUID player = p.getUniqueId();
        UUID wolf = w.getUniqueId();


    }

}
