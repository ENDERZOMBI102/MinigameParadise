package com.enderzombi102.MinigameParadise.commands;

import java.util.ArrayList;
import java.util.List;

import com.enderzombi102.MinigameParadise.modes.dropcalipse.Dropcalipse;
import com.enderzombi102.MinigameParadise.modes.explodingcursor.ExplodingCursor;
import com.enderzombi102.MinigameParadise.modes.mobcalipse.Mobcalipse;
import com.enderzombi102.MinigameParadise.modes.tntworld.TntWorld;
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
					stopMode( DeathSwap.class );
				}
				return true;
			case "bedrockpainter":
				if (start) {
					MinigameParadise.activeModes.add( new BedrockPainter() );
				} else {
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
			case "dropcalipse":
				if (start) {
					int maxDrops = 255;
					boolean randomDrops = false;
					if (args.length >= 3) maxDrops = Integer.parseInt( args[2] );
					if (args.length >= 4) randomDrops = Boolean.parseBoolean( args[3] );
					MinigameParadise.activeModes.add( new Dropcalipse( randomDrops, maxDrops ) );
				} else {
					stopMode( Dropcalipse.class );
				}
				return true;
			case "mobcalipse":
				if (start) {
					MinigameParadise.activeModes.add( new Mobcalipse() );
				} else {
					stopMode( Mobcalipse.class );
				}
				return true;
			case "tntworld":
				if (start) {
					MinigameParadise.activeModes.add( new TntWorld() );
				} else {
					stopMode( TntWorld.class );
				}
				return true;
			case "explodingcursor":
				if (start) {
					MinigameParadise.activeModes.add( new ExplodingCursor() );
				} else {
					stopMode( ExplodingCursor.class );
				}
				return true;
			default:
				return false;
		}
	}
//			case "":
//				if (start) {
//					MinigameParadise.activeModes.add( new );
//				} else {
//					stopMode( .class );
//				}
//				return true;

	@Override
	public List<String> onTabComplete( CommandSender sender, Command command, String alias, String[] args ) {
		ArrayList<String> comp = new ArrayList<>();
		switch (args.length - 1) {
			case 0:
				comp.add("start");
				comp.add("stop");
				break;
			case 1:
				if ( args[0].equals("start") ) {
					comp.add("deathswap");
					comp.add("bedrockpainter");
					comp.add("blockpainter");
					comp.add("dropcalipse");
					comp.add("mobcalipse");
					comp.add("tntworld");
					comp.add("explodingcursor");
				} else {
					for (ModeBase mode : MinigameParadise.activeModes) {
						comp.add( mode.getClass().getSimpleName().toLowerCase() );
					}
				}
				break;
			case 2:
				if ( args[1].equals("dropcalipse") && args[0].equals("start") ) {
					comp.add("255");
				}
				break;
			case 3:
				if ( args[1].equals("dropcalipse") && args[0].equals("start") ) {
					comp.add("true");
					comp.add("false");
				}
			case 4:
				if ( args[1].equals("deathswap") && args[0].equals("start") ) {
					comp.add("true");
					comp.add("false");
				}
				break;
			default:
				break;
		}
		return comp;
	}


	private void stopMode(Class cls ) {
		Bukkit.broadcastMessage( "[" + cls.getSimpleName() + "] stopping..");
		//BedrockPainter mode
		for (ModeBase mode : MinigameParadise.activeModes) {
			if ( cls.isInstance( mode ) ) {
				mode.stop();
				MinigameParadise.activeModes.remove( mode );
				break;
			}
		}
		Bukkit.broadcastMessage( "[" + cls.getSimpleName() + "] stopped!" );
	}
}
