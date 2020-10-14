package com.enderzombi102.MinigameParadise.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.modes.deathswap.DeathSwap;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import com.enderzombi102.MinigameParadise.modes.bedrockpaint.BedrockPainter;
import com.enderzombi102.MinigameParadise.modes.blockpainter.BlockPainter;

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
					MinigameParadise.activeModes.add( new DeathSwap(time, hardcore, allowNether) );
				} else {
//					sender.getServer().broadcastMessage("stopping DeathSwap");
//					//DeathSwap mode
//					for (ModeBase mode : MinigameParadise.activeModes) {
//						if ( mode instanceof DeathSwap ) {
//							mode.stop();
//							MinigameParadise.activeModes.remove(mode);
//							break;
//						}
//					}
//					sender.getServer().broadcastMessage("DeathSwap stopped!");
					stopMode( DeathSwap.class );
				}
				return true;
			case "bedrockpainter":
				if (start) {
					MinigameParadise.activeModes.add( new BedrockPainter() );
				} else {
//					sender.getServer().broadcastMessage("stopping BedrockPainter");
//					//BedrockPainter mode
//					for (ModeBase mode : MinigameParadise.activeModes) {
//						if ( mode instanceof BedrockPainter ) {
//							mode.stop();
//							MinigameParadise.activeModes.remove(mode);
//							break;
//						}
//					}
//					sender.getServer().broadcastMessage("BedrockPainter stopped!");
					stopMode( BedrockPainter.class );
				}
				return true;
			case "blockpainter":
				if (start) {
					MinigameParadise.activeModes.add( new BlockPainter() );
				} else {
					stopMode( BlockPainter.class );
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
				comp.add("bedrockpainter");
				comp.add("blockpainter");
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

	private void stopMode( Class cls ) {
		Bukkit.broadcastMessage( "stopping " + cls.getSimpleName() );
		//BedrockPainter mode
		for (ModeBase mode : MinigameParadise.activeModes) {
			if ( cls.isInstance( mode ) ) {
				mode.stop();
				MinigameParadise.activeModes.remove( mode );
				break;
			}
		}
		Bukkit.broadcastMessage( cls.getSimpleName() + " stopped!" );
	}
}
