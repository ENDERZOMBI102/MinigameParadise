package com.enderzombi102.MinigameParadise.generalListeners;

import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitChangeEvent;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerEventListener implements Listener {

	private static final HashMap<UUID, Location> playerHits = new HashMap<>();

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent evt) {
		if ( evt.getFrom().getBlock().getLocation() == evt.getTo().getBlock().getLocation() ) {
			return;
		}
		List<Block> blocks = evt.getPlayer().getLineOfSight(Util.transparentBlocks, 100);
		Block hitBlock = blocks.get( blocks.size() - 1 );
		Bukkit.getPluginManager().callEvent( new PlayerRaycastHitEvent(evt.getPlayer(), hitBlock) );
		UUID player = evt.getPlayer().getUniqueId();
		if ( playerHits.containsKey( player ) ) {
			Bukkit.getPluginManager().callEvent( new PlayerRaycastHitChangeEvent( evt.getPlayer(), hitBlock ) );
			playerHits.put( player, hitBlock.getLocation() );
		}

	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		playerHits.put( evt.getPlayer().getUniqueId(), null );
	}

}
