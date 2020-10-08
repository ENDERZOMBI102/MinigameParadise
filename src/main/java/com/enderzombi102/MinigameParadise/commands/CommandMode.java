package com.enderzombi102.MinigameParadise.commands;

import java.util.ArrayList;
import java.util.List;

import com.enderzombi102.MinigameParadise.modes.bedrockpaint.BedrockPainter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.modes.deathswap.DeathSwap;

public class CommandMode implements TabExecutor {

	public String getUsage() {
		return "/mode <start|stop> <mode> [mode parameters]";
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
		// arg count check
		if (args.length < 2) return false;
		// start/stop check
		if ( !args[0].equals("start") && !args[0].equals("stop") ) return false;
		boolean start = false;
		if ( args[0].equals("start") ) start = true;

		// handle command
		switch(args[1]) {
			case "deathswap":
				//deathswap mode
				if (start) {
					int time = 5;
					boolean hardcore = true, allowNether = true;
					if (args.length >= 3) time = Integer.parseInt(args[1]);
					if (args.length >= 4) hardcore = Boolean.parseBoolean(args[2]);
					if (args.length >= 5) allowNether = Boolean.parseBoolean(args[3]);
					MinigameParadise.currentMode = new DeathSwap(time, hardcore, allowNether);
				} else {
					sender.getServer().broadcastMessage("stopping DeathSwap");
					//deathswap mode
					MinigameParadise.currentMode.stop();
					MinigameParadise.currentMode = null;
					sender.getServer().broadcastMessage("DeathSwap stopped!");
				}
				return true;
			case "bedrockpaint":
				if (start) {
					MinigameParadise.currentMode = new BedrockPainter();
				} else {
					sender.getServer().broadcastMessage("stopping BedrockPainter");
					//deathswap mode
					MinigameParadise.currentMode.stop();
					MinigameParadise.currentMode = null;
					sender.getServer().broadcastMessage("BedrockPainter stopped!");
				}
				return true;
			default:
				return false;
		}
	}

	@Override
	public List<String> onTabComplete( CommandSender sender, Command command, String alias, String[] args ) {
		ArrayList<String> comp = new ArrayList<>();
		switch (args.length) {
			case 1:
				comp.add("start");
				comp.add("stop");
				break;
			case 2:
				comp.add("deathswap");
				comp.add("bedrockpaint");
				break;
			case 4:
			case 5:
				if ( args[1].equals("deathswap") ) {
					comp.add("true");
					comp.add("false");
				}
				break;
			default:
				break;
		}
		return comp;
	}
}
