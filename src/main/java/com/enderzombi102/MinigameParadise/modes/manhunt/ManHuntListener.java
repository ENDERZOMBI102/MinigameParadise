package com.enderzombi102.MinigameParadise.modes.manhunt;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;


class ManHuntListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent evt) {
		// is the player hunted?
		if ( ManHunt.instance.targets.contains( evt.getPlayer() ) ) {
			ManHunt.instance.playerTargets.forEach( (hunter, target) -> {
				// does this hunter hunt this player?
				if ( target == evt.getPlayer() ) {
					// is the hunter is the same dimension as the player?
					if ( target.getWorld() == hunter.getWorld() ) {
						// as we don't know where the compass is, try to find it
						for ( ItemStack stack : hunter.getInventory() ) {
							// its a compass?
							if ( stack.getType() != Material.COMPASS ) continue;
							// its the tracker compass?
							if (! stack.getItemMeta().getDisplayName().equals("Tracking Compass") ) continue;

							// update it
							ManHunt.updateCompassMeta( stack, target.getLocation() );
						}
					}
				}
			});
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt) {
		// is the entity a player?
		if ( evt.getEntity() instanceof Player ) {
			Player damaged = (Player) evt.getEntity();
			if ( ManHunt.instance.targets.contains(damaged) ) {
				// this damage would kill him?
				if ( evt.getFinalDamage() >= damaged.getHealth() ) {
					// if deathSpectator is true, put a target into spectator mode as he died
					if ( ManHunt.instance.deathSpectator ) {
						// "kill" the hunted
						evt.setCancelled(true);
						damaged.closeInventory(InventoryCloseEvent.Reason.DEATH);
						damaged.getInventory().clear();
						damaged.setGameMode(GameMode.SPECTATOR);
					}
					ManHunt.instance.targets.remove(damaged);

					// if there is more hunted players alive, target another hunted player
					if ( ManHunt.instance.targets.size() > 0 ) {
						// cycle in the hunters
						for (Player hunter : ManHunt.instance.playerTargets.keySet()) {
							// update only the hunters who targeted this player
							if (ManHunt.instance.playerTargets.get(hunter) == damaged) {
								// update their target to the first hunted player
								ManHunt.instance.playerTargets.put( hunter, ManHunt.instance.targets.get(0) );
							}
						}
					}
					// check if every damaged is dead
					ManHunt.instance.checkFinish();
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerPostRespawnEvent evt) {
		if (ManHunt.instance.giveCompassOnRespawn) {
			// on respawn, give the hunter another compass
			if (! ManHunt.instance.targets.contains( evt.getPlayer() ) ) {
				ManHunt.instance.giveCompass( evt.getPlayer() );
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		final ItemStack stack = evt.getItem();
		// action check: is the player right clicking?
		if (! ( evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK ) ) return;
		// compass check: is the player holding the tracker compass?
		if ( stack == null || stack.getType() != Material.COMPASS )  return;
		if (! stack.getItemMeta().getDisplayName().equals("Tracking Compass") ) return;
		// target check: is the player a target?
		if ( ManHunt.instance.targets.contains( evt.getPlayer() ) ) return;
		// targets check: there are more hunted players?
		if ( ManHunt.instance.targets.size() == 1 ) return;

		final Player hunter = evt.getPlayer();
		Player target = ManHunt.instance.playerTargets.get(hunter);
		final int huntedSize = ManHunt.instance.targets.size();

		if ( hunter.isSneaking() ) {
			// cycle in the hunted players

			int index = ManHunt.instance.targets.indexOf(target) + 1;

			if (index > huntedSize) {
				index = 0;
			}

			target = ManHunt.instance.targets.get(index);

			// update hunter
			ManHunt.instance.playerTargets.put(hunter, target);
			hunter.sendActionBar( "Now targeting " + target.getDisplayName() );
			ManHunt.updateCompassMeta( stack, target.getDisplayName() );
		} else {
			// check if the compass can track the hunted player
			if ( target.getWorld() != hunter.getWorld() ) {
				target.sendActionBar( ChatColor.RED + "Can't track player" );
			} else {
				// update location
				ManHunt.updateCompassMeta( stack, target.getLocation() );
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent evt) {
		// put its target to null, as if their target moves it'll cause a NullPointerException
		ManHunt.instance.playerTargets.put( evt.getPlayer(), null );
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		// if a player joined, and we already have him in the playerTargets list, set their
		// target to the first hunted player
		ManHunt.instance.playerTargets.computeIfPresent(
				evt.getPlayer(),
				(hunter, hunted) -> ManHunt.instance.playerTargets.put( evt.getPlayer(), ManHunt.instance.targets.get(0) )
		);
	}

}
