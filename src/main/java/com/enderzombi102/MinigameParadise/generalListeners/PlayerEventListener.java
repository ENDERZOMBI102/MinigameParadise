package com.enderzombi102.MinigameParadise.generalListeners;

import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitChangeEvent;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerEventListener implements Listener {

	private static final HashMap<UUID, Location> playerHits = new HashMap<>();

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent evt) {
		// check if the player pitch/yaw position is the same
		Location oldPos = evt.getFrom(), newPos = evt.getTo();
		int [] pos = {
				( ( Float ) oldPos.getYaw() ).intValue(),
				( ( Float ) newPos.getYaw() ).intValue(),
				( ( Float ) oldPos.getPitch() ).intValue(),
				( ( Float ) newPos.getPitch() ).intValue()
		};
		// get block (max distance: 100)
		List<Block> blocks = evt.getPlayer().getLineOfSight(Util.transparentBlocks, 100);
		Block hitBlock = blocks.get( blocks.size() - 1 );
		if ( pos[0] == pos[1] || pos[2] == pos[3] ) {
			return;
		}

		Bukkit.getPluginManager().callEvent( new PlayerRaycastHitEvent(evt.getPlayer(), hitBlock) );
		UUID player = evt.getPlayer().getUniqueId();
		if ( playerHits.containsKey( player ) ) {
			if ( playerHits.get( player ).getBlock().getType() != hitBlock.getType() ) {
				Bukkit.getPluginManager().callEvent(
						new PlayerRaycastHitChangeEvent(
								evt.getPlayer(),
								playerHits.get( player ).getBlock(),
								hitBlock
						)
				);
				playerHits.put( player, hitBlock.getLocation() );
			}
		} else {
			playerHits.put( player, hitBlock.getLocation() );
		}

	}
}
