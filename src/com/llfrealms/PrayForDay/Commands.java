package com.llfrealms.PrayForDay;


import org.bukkit.command.*;
import org.bukkit.entity.Player;

import com.llfrealms.PrayForDay.LightSource;

public class Commands implements CommandExecutor 
{
	
	private PrayForDay plugin;
	public Commands(PrayForDay plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("prayforsave") && sender.hasPermission("llf.save"))
	    {
			plugin.saveConfig();
        	sender.sendMessage("Config saved");
        	return true;
	    }
		if(cmd.getName().equalsIgnoreCase("prayforload")  && sender.hasPermission("llf.load"))
	    {
			plugin.reloadConfig();
        	sender.sendMessage("Config reloaded");
        	return true;
	    }
		if(cmd.getName().equalsIgnoreCase("ltbl"))
	    {
			int light = Integer.parseInt(args[0]);
			Player player = (Player) sender;
			LightSource.createLightSource(player.getLocation(), light, player);
        	return true;
	    }
		if(cmd.getName().equalsIgnoreCase("fuckthelight"))
	    {
			Player player = (Player) sender;
			LightSource.deleteLightSource(player.getLocation(), player);
        	return true;
	    }
		return false;
    }

}
