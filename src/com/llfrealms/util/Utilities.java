package com.llfrealms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.llfrealms.PrayForDay.*;


public class Utilities {
	
	private ConsoleCommandSender consoleMessage = Bukkit.getConsoleSender();
	
	public static Statement stmt = null;
	public static Connection connection = null;
	public static ResultSet result = null;
	public static String database, dbusername, dbpassword, host, prefix;
	public static int port;
	
	private static PrayForDay plugin;
	public Utilities(PrayForDay plugin) {
		Utilities.plugin = plugin;
	}
	public static void loadCommands()
	{
		for(int i = 0; i < plugin.commands.size(); i++)
		{
			plugin.getCommand(plugin.commands.get(i)).setExecutor(new Commands(plugin));
		}
	}
    public static String colorChat(String msg) 
    {
    	return msg.replace('&', (char) 167);
    }
    public static String getFinalArg(final String[] args, final int start)
    {
            final StringBuilder bldr = new StringBuilder();
            for (int i = start; i < args.length; i++)
            {
                    if (i != start)
                    {
                            bldr.append(" ");
                    }
                    bldr.append(args[i]);
            }
            return bldr.toString();
    }
    public static boolean sendMessage(CommandSender p, String message)
    {
        if (message ==null || message.isEmpty()) return true;
        if (message.contains("\n"))
                return sendMultilineMessage(p,message);
        if (p instanceof Player){
                if (((Player) p).isOnline())
                        p.sendMessage(colorChat(message));
        } else {
                p.sendMessage(colorChat(message));
        }
        return true;
    }
    public static boolean allTheSame(ArrayList<Boolean> l)
    {
    	for(int i = 1; i < l.size(); i++)
    	{
    		if(l.get(i) != l.get(0))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    public static boolean sendMultilineMessage(CommandSender p, String message)
    {
        if (message ==null || message.isEmpty()) return true;
        String[] msgs = message.split("\n");
        for (String msg: msgs){
                if (p instanceof Player){
                        if (((Player) p).isOnline())
                                p.sendMessage(colorChat(msg));
                } else {
                        p.sendMessage(colorChat(msg));
                }
        }
        return true;
    }	
    public void tableCheck()
    {
    	Utilities.sendMessage(consoleMessage, "Making sure our table exists");
    	String sql = "CREATE TABLE IF NOT EXISTS "+plugin.pluginName+"_rewarded" +
    				 "(user varchar(255),"+
    				 "reward varchar(255))";
    	String sql2 = "CREATE TABLE IF NOT EXISTS "+plugin.pluginName+"_users" +
				 "(user varchar(255))";
    	try {
    		stmt = connection.createStatement();
    		stmt.executeUpdate(sql);
    		stmt.executeUpdate(sql2);
		} catch (SQLException ex) {
            // handle any errors
        	warning("SQLException: " + ex.getMessage());
        	warning("SQLState: " + ex.getSQLState());
        	warning("VendorError: " + ex.getErrorCode());
        }
    	if (stmt != null) 
	    {
	        try {
	        	stmt.close();
	        } catch (SQLException sqlEx) { } // ignore

	        stmt = null;
	    }
    }
	public ResultSet query(String query)
	{
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
            // handle any errors
			warning("SQLException: " + ex.getMessage());
			warning("SQLState: " + ex.getSQLState());
			warning("VendorError: " + ex.getErrorCode());
        }
		return rs;
	}
	public void addRecord(String sql)
    {
    	try {
			stmt = connection.createStatement();
			 stmt.executeUpdate(sql);
		} catch (SQLException ex) {
            // handle any errors
			warning("SQLException: " + ex.getMessage());
			warning("SQLState: " + ex.getSQLState());
			warning("VendorError: " + ex.getErrorCode());
        }
    	if (stmt != null) 
	    {
	        try {
	        	stmt.close();
	        } catch (SQLException sqlEx) { } // ignore

	        stmt = null;
	    }
    }
	public static void connectSetup()
	{
		prefix = configString("MySQL.database.prefix");
        host = configString("MySQL.server.address");
        port = configInt("MySQL.server.port");
        database = configString("MySQL.database.database");
        dbusername = configString("MySQL.database.username");
        dbpassword = configString("MySQL.database.password");
	}
	public static String configString(String path)
	{
		return plugin.getConfig().getString(path);
	}
	public static int configInt(String path)
	{
		return plugin.getConfig().getInt(path);
	}
	public void info(String msg)
	{
		plugin.getLogger().info(msg);
	}
	public void severe(String msg)
	{
		plugin.getLogger().severe(msg);
	}
	public void warning(String msg)
	{
		plugin.getLogger().warning(msg);
	}
    public void connect() {
        String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            plugin.getLogger().info("Attempting connection to MySQL...");

            // Force driver to load if not yet loaded
            Class.forName("com.mysql.jdbc.Driver");
            Properties connectionProperties = new Properties();
            connectionProperties.put("user", dbusername);
            connectionProperties.put("password", dbpassword);
            connectionProperties.put("autoReconnect", "false");
            connectionProperties.put("maxReconnects", "0");
            connection = DriverManager.getConnection(connectionString, connectionProperties);
            sendMessage(consoleMessage,"["+plugin.pluginName+"] &aConnection to MySQL was a success!");
        }
        catch (SQLException ex) {
            connection = null;
            Utilities.sendMessage(consoleMessage, "&4[SEVERE] Connection to MySQL failed!");
            plugin.getLogger().info("SQLException: " + ex.getMessage());
        	info("SQLState: " + ex.getSQLState());
        	info("VendorError: " + ex.getErrorCode());
        }
        catch (ClassNotFoundException ex) {
            connection = null;
            severe("MySQL database driver not found!");
        }
    }

}
