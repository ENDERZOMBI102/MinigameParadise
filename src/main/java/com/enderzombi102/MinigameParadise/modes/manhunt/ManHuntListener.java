package com.enderzombi102.MinigameParadise.modes.manhunt;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ManHuntListener implements Listener {

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
	public void onPlayerDamage(EntityDamageEvent evt) {
		if ( ManHunt.instance.deathSpectator ) {
			if (evt.getEntity() instanceof Player) {
				Player player = (Player) evt.getEntity();
				if ( ManHunt.instance.targets.contains(player.getUniqueId() ) ) {
					// put a target into spectator mode as he died
					if (evt.getFinalDamage() >= player.getHealth()) {
						evt.setCancelled(true);
						player.setGameMode(GameMode.SPECTATOR);
						player.getInventory().clear();
						player.closeInventory(InventoryCloseEvent.Reason.DEATH);
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
				evt.getPlayer().getInventory().addItem( new ItemStack(Material.COMPASS) );
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		// compass check
		if (evt.getItem().getType() != Material.COMPASS) return;

		final UUID player = evt.getPlayer().getUniqueId();
		// target check
		if (! ManHunt.instance.targets.contains(player) ) return;

		UUID target = ManHunt.instance.playerTargets.get(player);
		int index = ManHunt.instance.targets.indexOf(target) + 1;

		if ( index > ManHunt.instance.targets.size() - 1) {
			// check if there's only one target, if so return as we can't cycle
			if ( ManHunt.instance.targets.size() == 1 ) return;
			index = 0;
		}

		target = ManHunt.instance.targets.get(index);

		evt.getPlayer().sendActionBar("Now targeting: " + Bukkit.getPlayer(target).getDisplayName() );
		ManHunt.instance.playerTargets.put( player, target );

	}
}
