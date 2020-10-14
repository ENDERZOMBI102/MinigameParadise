package com.enderzombi102.MinigameParadise.modes.deathswap;

import java.util.ArrayList;
import com.google.common.collect.Lists;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.modes.ModeBase;

public class DeathSwap extends ModeBase {
	
	public static DeathSwap instance;
	public boolean allowNether;
	private final BukkitRunnable timerThread;
	private ArrayList< ArrayList<Player> > teams = new ArrayList<>();

	public DeathSwap(int swapTime, boolean hardcore, boolean allowNether) {
		// add event listener for this gamemode
		Bukkit.getPluginManager().registerEvents( new DeathSwapListener(), MinigameParadise.instance );
		// send mode info
		broadcastPrefixedMessage("setting up DeathSwap mode");
		int time = swapTime * 60;
		broadcastPrefixedMessage("timer: " + time / 60 + " minutes");
		// make the teams
		broadcastPrefixedMessage("making teams");
		this.makeTeams();
		// set world
		broadcastPrefixedMessage("setting up world");
		this.allowNether = allowNether;
		setupWorld(hardcore);
		// start the timer thread
		this.timerThread = new TimerThread(time);
		this.timerThread.runTaskTimer(MinigameParadise.instance, 20, 20);
		broadcastPrefixedMessage("the clock is ticking! timer started!");
	}
	
	private void makeTeams() {
		ArrayList<Player> players = Lists.newArrayList( Bukkit.getOnlinePlayers() );
		// remove all players that aren't in survival mode
		players.removeIf( player -> player.getGameMode() != GameMode.SURVIVAL );
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
		this.teams.forEach(team -> {
			// swap!
			team.get(0).sendMessage("swap!");
			team.get(1).sendMessage("swap!");
			Location p1 = team.get(0).getLocation();
			p1.setWorld( team.get(0).getWorld() );
			Location p2 = team.get(1).getLocation();
			p2.setWorld( team.get(1).getWorld() );
			team.get(1).teleport(p1);
			team.get(0).teleport(p2);
		});
	}
	
	public void checkWin( Player nowDeadPlayer ) {
		for (ArrayList<Player> team : this.teams ) {
			
			if ( team.get(0).equals( nowDeadPlayer ) ) {
				broadcastPrefixedMessage(team.get(1).getName()+" Wins his SwapBattle!");
				team.get(0).setGameMode( GameMode.SPECTATOR );
				break;
			} else if ( team.get(1).equals( nowDeadPlayer ) ) {
				broadcastPrefixedMessage(team.get(0).getName()+" Wins his SwapBattle!");
				team.get(1).setGameMode( GameMode.SPECTATOR );
				break;
			}
			
		}
	}

	private static void setupWorld( boolean hardcore ) {
		for ( World world : Bukkit.getWorlds() ) {
			world.setDifficulty(Difficulty.PEACEFUL);
			world.setDifficulty(hardcore ? Difficulty.HARD : Difficulty.NORMAL);
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
		HandlerList.unregisterAll(DeathSwapListener.instance);
		this.timerThread.cancel();
	}
	
	
}
