package com.enderzombi102.MinigameParadise.modes.manhunt;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.Arrays;
import java.util.UUID;

class ManHuntListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent evt) {
		if ( ManHunt.instance.targets.contains( evt.getPlayer().getUniqueId() ) ) {
			for ( Player player : Bukkit.getOnlinePlayers() ) {
				if (! ManHunt.instance.targets.contains( player.getUniqueId() ) ) {
					// set the compass only if the player that caused the event was being targeted by this player
					if ( ManHunt.instance.playerTargets.get( player.getUniqueId() ) == evt.getPlayer().getUniqueId() ) {
						player.setCompassTarget(evt.getTo());
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt) {
		if ( ManHunt.instance.deathSpectator ) {
			if (evt.getEntity() instanceof Player) {
				Player player = (Player) evt.getEntity();
				if ( ManHunt.instance.targets.contains( player.getUniqueId() ) ) {
					// put a target into spectator mode as he died
					if (evt.getFinalDamage() >= player.getHealth()) {
						evt.setCancelled(true);
						player.setGameMode(GameMode.SPECTATOR);
						player.getInventory().clear();
						player.closeInventory(InventoryCloseEvent.Reason.DEATH);

						// TODO: does this work?
						// if there is more targets target another player
						int newTarget = ManHunt.instance.targets.indexOf( player.getUniqueId() ) + 1;
						ManHunt.instance.targets.remove( player.getUniqueId() );
						if ( newTarget >= ManHunt.instance.targets.size() ) newTarget -= 2;
						if ( newTarget <= 0 ) newTarget = 0;
						if ( ManHunt.instance.targets.get(newTarget) != null) {
							for ( UUID key : ManHunt.instance.playerTargets.keySet() ) {
								if ( ManHunt.instance.playerTargets.get(key) == player.getUniqueId() ) {
									ManHunt.instance.playerTargets.put(key, ManHunt.instance.targets.get(newTarget) );
								}
							}
						}

						// check if every player is dead
						ManHunt.instance.checkFinish();
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerPostRespawnEvent evt) {
		if (ManHunt.instance.giveCompassOnRespawn) {
			if (! ManHunt.instance.targets.contains( evt.getPlayer().getUniqueId() ) ) {
				ItemStack stack = new ItemStack(Material.COMPASS);
				CompassMeta meta = (CompassMeta) stack.getItemMeta();
				// set lore and name
				meta.setDisplayName("Tracker Compass");
				meta.setLore(
						Arrays.asList(
								"Right click to change target",
								"Tracking: " + Bukkit.getPlayer(
												ManHunt.instance.playerTargets.get(
														evt.getPlayer().getUniqueId()
												)
										).getName()
						)
				);
				// update the itemstack
				stack.setItemMeta( meta );
				evt.getPlayer().getInventory().addItem( stack );
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		// TODO: check with other 2 players if this work
		// action check
		if (! ( evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK ) ) return;
		// compass check
		if ( evt.getItem().getType() != Material.COMPASS )  return;
		if (! evt.getItem().getItemMeta().getDisplayName().equals("Tracker Compass") ) return;
		// targets check
		if ( ManHunt.instance.targets.size() == 1 ) return;

		final UUID player = evt.getPlayer().getUniqueId();
		// target check
		if ( ManHunt.instance.targets.contains(player) ) return;

		UUID target = ManHunt.instance.playerTargets.get(player);
		int index = ManHunt.instance.targets.indexOf(target) + 1;

		if ( index > ManHunt.instance.targets.size() - 1) {
			// check if there's only one target, if so return as we can't cycle
			if ( ManHunt.instance.targets.size() == 1 ) return;
			index = 0;
		}

		target = ManHunt.instance.targets.get(index);
		final String targetName = Bukkit.getPlayer(target).getName();

		// update metadata
		final CompassMeta meta = (CompassMeta) evt.getItem().getItemMeta();
		meta.setDisplayName("Tracker Compass");
		meta.setLore(
				Arrays.asList(
						"Right click to change target",
						"Tracking: " + targetName
				)
		);
		evt.getItem().setItemMeta( meta );

		// finalize
		evt.getPlayer().sendActionBar("Target changed to " + targetName );
		ManHunt.instance.playerTargets.put( player, target );

	}
}
