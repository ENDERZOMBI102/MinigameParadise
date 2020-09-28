package com.enderzombi102.MinigameParadise.modes.deathswap;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import com.google.common.collect.Lists;

public class DeathSwap extends ModeBase {
	
	public static DeathSwap instance;
	private int time;
	private BukkitRunnable timerThread;
	private ArrayList< ArrayList<Player> > teams = new ArrayList<>();
	
	public DeathSwap(int swapTime, boolean hardcore, boolean doImmediateRespawn) {
		// set singleton
		DeathSwap.instance = this;
		// send mode info
		broadcastPrefixedMessage("setting DeathSwap mode");
		this.time = swapTime * 60;
		broadcastPrefixedMessage("timer: " + this.time / 60 + " minutes");
		// make the teams
		broadcastPrefixedMessage("making teams");
		this.makeTeams();
		// set world
		broadcastPrefixedMessage("setting up world");
		setupWorld();
		// start the timer thread
		this.timerThread = new TimerThread(this.time);
		this.timerThread.runTaskTimer(MinigameParadise.instance, 20, 20);
		broadcastPrefixedMessage("the clock is ticking! timer started!");
	}
	
	private void makeTeams() {
		ArrayList<Player> players = Lists.newArrayList( Bukkit.getOnlinePlayers() );
		// remove all players that aren't in survival mode
		players.removeIf( new Predicate<Player>() {

			@Override
			public boolean test(Player player) {
				return player.getGameMode() != GameMode.SURVIVAL;
			}
			
		});
		int teamCount = players.size() / 2, playerIndex = 0;
		broadcastPrefixedMessage("and now the antagonists!");
		for (int i=0; i<teamCount;i++) {
			ArrayList<Player> tmp = new ArrayList<>();
			tmp.add( players.toArray( new Player [1] )[playerIndex] );
			playerIndex++;
			tmp.add( players.toArray( new Player [1] )[playerIndex] );
			playerIndex++;
			this.teams.add(tmp);
			broadcastPrefixedMessage( tmp.get(0).getName()+" vs "+tmp.get(1).getName() );
		}
	}
	
	
	public void swap() {
		// cycle in the teams
		this.teams.forEach( new Consumer< ArrayList<Player> >() {

			@Override
			public void accept(ArrayList<Player> t) {
				// swap!
				t.get(0).sendMessage("swap!");
				t.get(1).sendMessage("swap!");
				Location p1 = t.get(0).getLocation();
				p1.setWorld( t.get(0).getWorld() );
				Location p2 = t.get(1).getLocation();
				p2.setWorld( t.get(1).getWorld() );
				t.get(1).teleport(p1);
				t.get(0).teleport(p2);
			}
			
		});
	}
	
	public void checkWin( Player ent ) {
		for (ArrayList<Player> team : this.teams ) {
			
			if ( team.get(0).equals(ent) ) {
				broadcastPrefixedMessage(team.get(1).getName()+" Wins his SwapBattle!");
				team.get(0).setGameMode(GameMode.SPECTATOR);
				break;
			} else if ( team.get(1).equals(ent) ) {
				broadcastPrefixedMessage(team.get(0).getName()+" Wins his SwapBattle!");
				team.get(1).setGameMode(GameMode.SPECTATOR);
				break;
			}
			
		}
	}

	private static void setupWorld() {
		for ( World world : Bukkit.getWorlds() ) {
			world.setDifficulty(Difficulty.PEACEFUL);
			world.setDifficulty(Difficulty.NORMAL);
			for ( Entity entity : world.getEntitiesByClasses(Item.class) ) {
				entity.remove();
			}
		}
		Location spawn = Bukkit.getWorlds().get(0).getSpawnLocation();
		for ( Player player : Bukkit.getOnlinePlayers() ) {
			if (player.getGameMode() != GameMode.SURVIVAL) continue;
			player.getInventory().clear();
			player.teleport( spawn );
		}
	}

	@Override
	public void stop() {
		this.timerThread.cancel();
	}
	
	
}
