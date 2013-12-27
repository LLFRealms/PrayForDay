package com.llfrealms.PrayForDay;

import org.bukkit.command.*;

public class Commands implements CommandExecutor 
{
	private PrayForDay plugin;
	public Commands(PrayForDay plugin) {
		this.plugin = plugin;
	}
	public void commandSetup()
	{
		plugin.commands.add("prayforsave");
		plugin.commands.add("prayforload");
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
		return false;
    }

}
