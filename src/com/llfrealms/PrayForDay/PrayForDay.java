package com.llfrealms.PrayForDay;

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
		Utilities.sendMessage(consoleMessage, "[" + pluginName + "] &aYou better start Praying For Day!");
		
		Utilities.connectSetup();
    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    }

}
