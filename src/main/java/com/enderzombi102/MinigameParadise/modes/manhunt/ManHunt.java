package com.enderzombi102.MinigameParadise.modes.manhunt;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.generalListeners.BlockRestorerListener;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import com.enderzombi102.MinigameParadise.modes.ModeStartAbortException;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ManHunt extends ModeBase {

	public static ManHunt instance;
	final ArrayList<Player> targets = new ArrayList<>();
	final HashMap<Player, Player> playerTargets = new HashMap<>();
	final boolean deathSpectator;
	final boolean giveCompassOnRespawn;
	private final Location startPoint;
	public ManHuntListener listener;

	public ManHunt(String[] targets, boolean deathSpectator, boolean giveCompassOnRespawn, CommandSender sender) throws ModeStartAbortException {
		final boolean debug = true;
		broadcastPrefixedMessage("Starting manhunt!");
		instance = this;
		// get the player entity for every username
		for ( String target : targets ) {
			Player player = Bukkit.getPlayer( target );
			if ( player != null ) {
				this.targets.add( player );
			} else {
				// not a valid username, skip it
				sender.sendMessage(ChatColor.RED + "Unknown player " + target );
			}

		}

		// do we have valid players?
		if ( this.targets.size() == 0 ) {
			sender.sendMessage( ChatColor.RED + "No valid players provided, aborting");
			throw new ModeStartAbortException();
		}

		// setup attributes
		this.startPoint = ( (Player) sender ).getLocation();
		this.deathSpectator = deathSpectator;
		this.giveCompassOnRespawn = giveCompassOnRespawn;
		this.listener = new ManHuntListener();
		// start listeners
		Util.registerListener(this.listener);
		BlockRestorerListener.INSTANCE.start();

		// give every player an initial target and the Tracking Compass
		for ( Player player : Bukkit.getOnlinePlayers() ) {
			if ( (! ManHunt.instance.targets.contains( player ) ) || debug ) {
				this.playerTargets.put( player, this.targets.get(0) );
				this.giveCompass( player );
			}
		}

		// build message
		StringBuilder builder = new StringBuilder("ManHunt started! good luck to ");
		builder.append( this.targets.get(0).getName() );
		for ( int i = 1; i < this.targets.size(); i++ ) {
			if ( i == this.targets.size() - 1 ) {
				builder.append( " and " + this.targets.get(i).getName() + "!" );
			} else {
				builder.append( ", " + this.targets.get(i).getName() );
			}
		}
		broadcastPrefixedMessage( builder.toString() );
	}

	@Override
	public void stop() {
		// restore blocks
		BlockRestorerListener.INSTANCE.restoreAndStop();
		// clear inventories, spawnpoints and location
		for (Player player : Bukkit.getOnlinePlayers() ) {
			player.getInventory().clear();
			player.setBedSpawnLocation(this.startPoint);
			player.teleport(this.startPoint);
		}
		// normal stuff
		HandlerList.unregisterAll(this.listener);
		ManHunt.instance = null;
	}

	public void checkFinish() {
		for (Player target : ManHunt.instance.targets ) {
			if ( target.getGameMode() == GameMode.SURVIVAL ) {
				return;
			}
		}
		// if reaches here, we're finished
		this.stop();
		MinigameParadise.activeModes.remove(this);
	}

	public void giveCompass(Player player) {
		// get the stack
		ItemStack stack = new ItemStack(Material.COMPASS);
		updateCompassMeta(
				stack,
				this.playerTargets.get( player ).getName()
		);
		updateCompassMeta(
				stack,
				this.playerTargets.get( player ).getLocation()
		);
		player.getInventory().addItem( stack );
	}

	static void updateCompassMeta(ItemStack stack, String target) {
		// get the metadata
		CompassMeta meta = (CompassMeta) stack.getItemMeta();
		// set lore and name
		TextComponent comp = new TextComponent();
		comp.setText("Tracking Compass");
		comp.setBold(false);
		comp.setColor( net.md_5.bungee.api.ChatColor.LIGHT_PURPLE );
		meta.setDisplayNameComponent( new TextComponent[]{ comp } );
		meta.setLore(
				Arrays.asList(
						"Right click to change target",
						"Tracking: " + target
				)
		);
		// update the itemstack
		stack.setItemMeta( meta );
	}

	static void updateCompassMeta(ItemStack stack, Location loc) {
		// get the metadata
		CompassMeta meta = (CompassMeta) stack.getItemMeta();
		// set lodestone location
		meta.setLodestone(loc);
		meta.setLodestoneTracked(true);
		// update the itemstack
		stack.setItemMeta( meta );
	}
}
