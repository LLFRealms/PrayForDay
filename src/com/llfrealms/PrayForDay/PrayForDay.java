package com.llfrealms.PrayForDay;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.llfrealms.util.*;

public final class PrayForDay extends JavaPlugin
{
	public String pluginName = "PrayForDay";
	private ConsoleCommandSender consoleMessage = Bukkit.getConsoleSender();
	
	@Override
    public void onEnable(){
		this.saveDefaultConfig();
    	this.getConfig();
    	getCommand("ltbl").setExecutor(new Commands(this));
    	getCommand("fuckthelight").setExecutor(new Commands(this));
		Utilities.sendMessage(consoleMessage, "[" + pluginName + "] &aYou better start Praying For Day!");
		//Utilities.connectSetup();
    }
 
    @Override
    public void onDisable() {
        this.saveConfig();
        try {
			Utilities.stmt.close();
			Utilities.connection.close();
		} catch (SQLException e) {
		}
    }

}
