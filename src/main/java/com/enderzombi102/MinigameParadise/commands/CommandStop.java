package com.enderzombi102.MinigameParadise.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.enderzombi102.MinigameParadise.MinigameParadise;

public class CommandStop implements TabExecutor {

	public String getUsage() {
		return "/stop <mode>";
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
		if (args.length == 0) {
			return false;
		}
		// handle command
		switch(args[0]) {
			case "deathswap":
				sender.getServer().broadcastMessage("stopping DeathSwap");
				//deathswap mode
				MinigameParadise.currentMode.stop();
				MinigameParadise.currentMode = null;
				sender.getServer().broadcastMessage("DeathSwap stopped!");
				return true;
			default:
				return false;
		}
	}

	@Override
	public List<String> onTabComplete( CommandSender sender, Command command, String alias, String[] args ) {
		ArrayList<String> comp = new ArrayList<>();
		if (args.length < 2) {
			comp.add("deathswap");
		}
		return comp;
	}
}
