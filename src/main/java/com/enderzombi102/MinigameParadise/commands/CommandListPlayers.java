package com.enderzombi102.MinigameParadise.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

public class CommandListPlayers implements TabExecutor {
	
	protected String getUsage() {
		return "/ListPlayers <gamemode> [onlynumber]";
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> comp = new ArrayList<>();
		if (args.length < 2) {
			comp.add("survival");
			comp.add("creative");
			comp.add("spectator");
			comp.add("adventure");
			comp.add("all");
		}
		if (args.length == 2) {
			comp.add("true");
			comp.add("false");
		}
		return comp;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 1) return false;
		// get all players
		ArrayList<Player> players = Lists.newArrayList( Bukkit.getOnlinePlayers() );
		// remove the players that aren't in the required gamemode
		switch (args[0]) {
		case "survival":
			players.removeIf( new Predicate<Player>() {

				@Override
				public boolean test(Player player) {
					return player.getGameMode() != GameMode.SURVIVAL;
				}
			
			});
			break;
		case "creative":
			players.removeIf( new Predicate<Player>() {

				@Override
				public boolean test(Player player) {
					return player.getGameMode() != GameMode.CREATIVE;
				}
			
			});
			break;
		case "adventure":
			players.removeIf( new Predicate<Player>() {

				@Override
				public boolean test(Player player) {
					return player.getGameMode() != GameMode.ADVENTURE;
				}
			
			});
			break;
		case "spectator":
			players.removeIf( new Predicate<Player>() {

				@Override
				public boolean test(Player player) {
					return player.getGameMode() != GameMode.SPECTATOR;
				}
			
			});
			break;
		case "all":
			args[0] = "";
			break;
		default:
			return false;
		}
		// send size if [onlynumber] is true
		if (args.length >= 2) {
			if (Boolean.parseBoolean(args[1])) {
				sender.sendMessage("there's "+players.size()+" players in "+args[0]);
				return true;
			}
		}
		// put all player names in a string
		String tmp = args[0].equals("") ? "players: " : "players in "+args[0]+": ";
		for ( Player player : players) {
			tmp += ( player.getName() + ", " );
		}
		//send that string
		sender.sendMessage( tmp );
		return true;
	}

}
