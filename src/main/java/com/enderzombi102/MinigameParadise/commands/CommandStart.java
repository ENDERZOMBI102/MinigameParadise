package com.enderzombi102.MinigameParadise.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.modes.deathswap.DeathSwap;

public class CommandStart implements TabExecutor {

	public String getUsage() {
		return "/start <mode> [mode parameters]";
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
		if (args.length == 0) {
			return false;
		}
		// handle command
		switch(args[0]) {
			case "deathswap":
				//deathswap mode
				int time = 5;
				boolean hardcore = true, doImmediateRespawn = true;
				if ( args.length >= 2 ) time = Integer.parseInt( args[1] );
				if ( args.length >= 3 ) hardcore = Boolean.parseBoolean( args[2] );
				if ( args.length >= 4 ) doImmediateRespawn = Boolean.parseBoolean( args[3] );
				MinigameParadise.currentMode = new DeathSwap( time, hardcore, doImmediateRespawn );
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
