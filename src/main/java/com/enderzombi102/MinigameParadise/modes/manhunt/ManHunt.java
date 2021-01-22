package com.enderzombi102.MinigameParadise.modes.manhunt;

import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.generalListeners.BlockRestorerListener;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ManHunt extends ModeBase {

	public static ManHunt instance;
	final ArrayList<UUID> targets = new ArrayList<>();
	final HashMap<UUID, UUID> playerTargets = new HashMap<>();
	final boolean deathSpectator;
	final boolean giveCompassOnRespawn;
	private final Location startPoint;
	public ManHuntListener listener;

	public ManHunt(String[] targets, boolean deathSpectator, boolean giveCompassOnRespawn, CommandSender sender) {
		broadcastPrefixedMessage("Starting manhunt!");
		instance = this;
		for ( String target : targets ) {
			Player player = Bukkit.getPlayer( target );
			if ( player != null ) {
				this.targets.add( player.getUniqueId() );
			} else {
				sender.sendMessage(ChatColor.RED + "Unknown player " + target );
			}

		}
		this.startPoint = ( (Player) sender ).getLocation();
		this.deathSpectator = deathSpectator;
		this.giveCompassOnRespawn = giveCompassOnRespawn;
		this.listener = new ManHuntListener();
		Util.registerListener(this.listener);
		BlockRestorerListener.INSTANCE.start();

		for ( Player player : Bukkit.getOnlinePlayers() ) {
			if (! ManHunt.instance.targets.contains( player.getUniqueId() ) ) {
				player.getInventory().addItem( new ItemStack(Material.COMPASS) );
			}
		}

		StringBuilder builder = new StringBuilder("ManHunt started! good luck to ");
		builder.append( Bukkit.getPlayer( this.targets.get(0) ).getName() );
		for ( int i = 1; i < this.targets.size(); i++ ) {
			if ( i == this.targets.size() - 1 ) {
				builder.append( " and " + Bukkit.getPlayer( this.targets.get(i) ).getName() + "!" );
			} else {
				builder.append( ", " + Bukkit.getPlayer( this.targets.get(i) ).getName() );
			}
		}
		broadcastPrefixedMessage( builder.toString() );
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
		instance = null;
		if (BlockRestorerListener.INSTANCE.active) BlockRestorerListener.INSTANCE.restoreAndStop();
	}

	public void checkFinish() {
		for (UUID uuid : ManHunt.instance.targets ) {
			if ( Bukkit.getPlayer(uuid).getGameMode() == GameMode.SURVIVAL ) {
				return;
			}
		}
		// if reaches here, we're finished
		BlockRestorerListener.INSTANCE.restore();
		for (Player player : Bukkit.getOnlinePlayers() ) {
			player.getInventory().clear();
			player.setBedSpawnLocation(this.startPoint);
			player.teleport(this.startPoint);
		}
	}

}
