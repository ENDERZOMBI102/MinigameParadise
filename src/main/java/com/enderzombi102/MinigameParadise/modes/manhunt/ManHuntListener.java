package com.enderzombi102.MinigameParadise.modes.manhunt;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.*;
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

import java.util.ArrayList;


class ManHuntListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent evt) {
		for ( Hunter hunter : ManHunt.instance.getHunters() ) hunter.onMove();
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt) {
		// is the entity a player?
		if ( evt.getEntity() instanceof Player ) {
			Player damaged = (Player) evt.getEntity();
			for ( ManHuntPlayer player : ManHunt.instance.players ) {
				if ( player.handle == damaged ) {
					// the returned value is the cancellation value
					evt.setCancelled( player.onDamage( evt.getFinalDamage() ) );
				}
			}
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerPostRespawnEvent evt) {
		for ( ManHuntPlayer player : ManHunt.instance.players ) {
			if ( player.handle == evt.getPlayer() ) player.onRespawn();
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
		// target check: is the player a prey?
		if (  ) return;
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

}
