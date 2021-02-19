package com.enderzombi102.MinigameParadise.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.enderzombi102.MinigameParadise.modes.chunkeffect.ChunkEffect;
import com.enderzombi102.MinigameParadise.modes.dropcalipse.Dropcalipse;
import com.enderzombi102.MinigameParadise.modes.explodingcursor.ExplodingCursor;
import com.enderzombi102.MinigameParadise.modes.manhunt.ManHunt;
import com.enderzombi102.MinigameParadise.modes.manhunt.ModeStartAbortException;
import com.enderzombi102.MinigameParadise.modes.mobcalipse.Mobcalipse;
import com.enderzombi102.MinigameParadise.modes.tagged.Tagged;
import com.enderzombi102.MinigameParadise.modes.teletimer.TeleTimer;
import com.enderzombi102.MinigameParadise.modes.tntworld.TntWorld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.modes.deathswap.DeathSwap;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import com.enderzombi102.MinigameParadise.modes.bedrockpaint.BedrockPainter;
import com.enderzombi102.MinigameParadise.modes.blockpainter.BlockPainter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CommandMode implements TabExecutor {
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args ) {
		// arg count check
		if (args.length < 2) return false;
		// start/stop check
		if ( !args[0].equals("start") && !args[0].equals("stop") && !args[0].equals("help") ) return false;
		if ( args[0].equals("start") ) return this.execStart(sender, args);
		else if ( args[0].equals("stop") ) return this.execStop(sender, args);
		else return this.execHelp(sender, args);
	}

	private boolean execStart(@NotNull CommandSender sender, String[] args) {
		try {
			// handle command
			switch (args[1]) {
				case "deathswap":
					//deathswap mode
					if ( checkMode(DeathSwap.class) ) return false;
					int time = 5;
					boolean hardcore = true, allowNether = true;
					if (args.length >= 3) time = Integer.parseInt(args[2]);
					if (args.length >= 4) hardcore = Boolean.parseBoolean(args[3]);
					if (args.length >= 5) allowNether = Boolean.parseBoolean(args[4]);
					MinigameParadise.activeModes.add( new DeathSwap( time, hardcore, allowNether ) );
					return true;
				case "bedrockpainter":
					if ( checkMode(BedrockPainter.class) ) return false;
					MinigameParadise.activeModes.add( new BedrockPainter() );
					return true;
				case "blockpainter":
					if ( checkMode(BlockPainter.class) ) return false;
					MinigameParadise.activeModes.add( new BlockPainter() );
					return true;
				case "dropcalipse":
					if ( checkMode(Dropcalipse.class) ) return false;
					int maxDrops = 255;
					boolean randomDrops = false;
					if (args.length >= 3) maxDrops = Integer.parseInt(args[2]);
					if (args.length >= 4) randomDrops = Boolean.parseBoolean(args[3]);
					MinigameParadise.activeModes.add( new Dropcalipse( randomDrops, maxDrops ) );
					return true;
				case "mobcalipse":
					if (checkMode(Mobcalipse.class) ) return false;
					MinigameParadise.activeModes.add( new Mobcalipse() );
					return true;
				case "tntworld":
					if ( checkMode(TntWorld.class) ) return false;
					MinigameParadise.activeModes.add( new TntWorld() );
					return true;
				case "explodingcursor":
					if (checkMode(ExplodingCursor.class)) return false;
					MinigameParadise.activeModes.add( new ExplodingCursor() );
					return true;
				case "manhunt":
					if ( checkMode(ManHunt.class) ) return false;
					boolean deathSpectator = Boolean.parseBoolean(args[2]);
					boolean giveCompassOnRespawn = Boolean.parseBoolean(args[3]);
					String[] targets = Arrays.copyOfRange(args, 4, args.length);
					// no players no party
					if (targets.length == 0) {
						sender.sendMessage(ChatColor.RED + "ERROR: Missing hunted players names!");
						return false;
					}
					try {
						MinigameParadise.activeModes.add(
								new ManHunt( targets, deathSpectator, giveCompassOnRespawn, sender )
						);
					} catch (ModeStartAbortException e) {
						return true;
					}
					return true;
				case "teletimer":
					if ( checkMode(TeleTimer.class) ) return false;
					int teleTimer = Integer.parseInt(args[2]);
					boolean usePearls = Boolean.parseBoolean(args[3]);
					MinigameParadise.activeModes.add( new TeleTimer( teleTimer, usePearls ) );
					return true;
				case "tagged":
					if ( checkMode(Tagged.class) ) return false;
					MinigameParadise.activeModes.add( new Tagged() );
					return true;
				case "chunkeffect":
					if ( checkMode(ChunkEffect.class) ) return false;
					MinigameParadise.activeModes.add( new ChunkEffect() );
					return true;
				default:
					return false;
			}
		} catch (IndexOutOfBoundsException e) {
			sender.sendMessage( ChatColor.RED + "ERROR: Missing parameters!");
			return true;
		}
	}

	private boolean execStop(@NotNull CommandSender sender, String[] args) {
		// handle command
		switch(args[1]) {
			case "deathswap":
				//deathswap mode
				if (! checkMode(DeathSwap.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"DeathSwap\" is not active.");
					return false;
				}
				stopMode( DeathSwap.class );
				return true;
			case "bedrockpainter":
				if (! checkMode(BedrockPainter.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"BedrockPainter\" is not active.");
					return false;
				}
				stopMode( BedrockPainter.class );
				return true;
			case "blockpainter":
				if (! checkMode(BlockPainter.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"BlockPainter\" is not active.");
					return false;
				}
				stopMode( BlockPainter.class );
				return true;
			case "dropcalipse":
				if (! checkMode(Dropcalipse.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"Dropcalispe\" is not active.");
					return false;
				}
				stopMode( Dropcalipse.class );
				return true;
			case "mobcalipse":
				if (! checkMode(Mobcalipse.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"Mobcalipse\" is not active.");
					return false;
				}
				stopMode( Mobcalipse.class );
				return true;
			case "tntworld":
				if (! checkMode(TntWorld.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"TnTWorld\" is not active.");
					return false;
				}
				stopMode( TntWorld.class );
				return true;
			case "explodingcursor":
				if (! checkMode(ExplodingCursor.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"ExplodingCursor\" is not active.");
					return false;
				}
				stopMode( ExplodingCursor.class );
				return true;
			case "manhunt":
				if (! checkMode(ManHunt.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"ManHunt\" is not active.");
					return false;
				}
				stopMode( ManHunt.class );
				return true;
			case "teletimer":
				if (! checkMode(TeleTimer.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"TeleTimer\" is not active.");
					return false;
				}
				stopMode( TeleTimer.class );
				return true;
			case "tagged":
				if (! checkMode(Tagged.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"Tagged\" is not active.");
					return false;
				}
				stopMode( Tagged.class );
				return true;
			case "chunkeffect":
				if (! checkMode(ChunkEffect.class) ) {
					sender.sendMessage(ChatColor.RED + "ERROR: Mode \"Tagged\" is not active.");
					return false;
				}
				stopMode( ChunkEffect.class );
				return true;
			default:
				return false;
		}
	}

	private boolean execHelp(@NotNull CommandSender sender, String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append( ChatColor.DARK_AQUA );
		builder.append( "displaying manhunt parameters\n" );
		builder.append( ChatColor.BLUE );
		builder.append("/mode start ");
		builder.append(args[1]);
		switch(args[1]) {
			case "deathswap":
				builder.append(" <time>");
				builder.append(" <hardcore>");
				builder.append(" <allowNether>");
				break;
			case "bedrockpainter":
			case "blockpainter":
				break;
			case "dropcalipse":
				builder.append(" <maxDrops>");
				builder.append(" <randomDrops>");
				break;
			case "mobcalipse":
			case "tntworld":
			case "explodingcursor":
				break;
			case "manhunt":
				builder.append(" <huntedSpectateOnDeath>");
				builder.append(" <giveCompassOnRespawn>");
				builder.append(" <hunted...>");
				break;
			case "teletimer":
				builder.append(" <time>");
				builder.append(" <usePearls>");
				break;
			case "tagged":
			case "chunkeffect":
				break;
			default:
				return false;
		}
		sender.sendMessage( builder.toString() );
		return true;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args ) {
		ArrayList<String> comp = new ArrayList<>();
		switch (args.length - 1) {
			case 0:
				comp.add("start");
				comp.add("stop");
				comp.add("help");
				break;
			case 1:
				if ( args[0].equals("start") || args[0].equals("help") ) {
					comp.add("deathswap");
					comp.add("bedrockpainter");
					comp.add("blockpainter");
					comp.add("dropcalipse");
					comp.add("mobcalipse");
					comp.add("tntworld");
					comp.add("explodingcursor");
					comp.add("manhunt");
					comp.add("teletimer");
					comp.add("tagged");
					comp.add("chunkeffect");
					if ( args[0].equals("start") ) {
						for (ModeBase mode : MinigameParadise.activeModes) {
							comp.remove(mode.getClass().getSimpleName().toLowerCase());
						}
					}
				} else {
					for (ModeBase mode : MinigameParadise.activeModes) {
						comp.add( mode.getClass().getSimpleName().toLowerCase() );
					}
				}
				break;
			case 2:
				if ( args[1].equals("manhunt")  && args[0].equals("start") ) {
					comp.add("true");
					comp.add("false");
				} else if (args[1].equals("dropcalipse") && args[0].equals("start") ) {
					comp.add("255");
				}
				break;
			case 3:
				if ( ( args[1].equals("dropcalipse") || args[1].equals("teletimer") || args[1].equals("manhunt") ) && args[0].equals("start") ) {
					comp.add("true");
					comp.add("false");
				}
				break;
			case 4:
				if ( args[0].equals("start") ) {
					if ( args[1].equals("deathswap") ) {
						comp.add("true");
						comp.add("false");
						break;
					}
				}
			default:
				if ( args[0].equals("start") ) {
					if (args[1].equals("manhunt")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (!(Arrays.toString(args).contains(player.getName()) || player.getName().equals(sender.getName()))) {
								comp.add(player.getName());
							}
						}
					}
				}
				break;
		}
		return comp;
	}

	private boolean checkMode(Class<? extends ModeBase> cls) {
		for ( ModeBase mode : MinigameParadise.activeModes ) {
			if ( cls.isInstance(mode) ) return true;
		}
		return false;
	}

	private void stopMode(Class<? extends ModeBase> cls ) {
		Bukkit.broadcastMessage( "[" + cls.getSimpleName() + "] stopping..");
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
