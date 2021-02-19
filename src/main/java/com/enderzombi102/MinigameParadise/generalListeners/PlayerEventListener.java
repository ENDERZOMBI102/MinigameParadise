package com.enderzombi102.MinigameParadise.generalListeners;

import com.enderzombi102.MinigameParadise.ChunkLocation;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.events.PlayerChangedChunkEvent;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitChangeEvent;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerEventListener implements Listener {

	private static final HashMap<UUID, Location> playerHits = new HashMap<>();
	private static final HashMap<UUID, ChunkLocation> playerLastChunk = new HashMap<>();

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent evt) {

		final UUID playerIdentifier = evt.getPlayer().getUniqueId();
		final Player player = evt.getPlayer();

		// chunk events
		if (! (playerLastChunk.containsKey( playerIdentifier ) || playerLastChunk.get( playerIdentifier ).sameChunk(player) ) ) {
			Bukkit.getPluginManager().callEvent(
					new PlayerChangedChunkEvent(
							player,
							playerLastChunk.get(playerIdentifier),
							new ChunkLocation(
								evt.getTo().getWorld(),
								evt.getTo().getChunk().getX(),
								evt.getTo().getChunk().getZ()
							)
					)
			);
		}

		// raytrace events
		// check if the player pitch/yaw position is the same
		Location oldPos = evt.getFrom(), newPos = evt.getTo();
		int [] pos = {
				( ( Float ) oldPos.getYaw() ).intValue(),
				( ( Float ) newPos.getYaw() ).intValue(),
				( ( Float ) oldPos.getPitch() ).intValue(),
				( ( Float ) newPos.getPitch() ).intValue()
		};
		// get block (max distance: 100)
		List<Block> blocks = player.getLineOfSight(Util.transparentBlocks, 100);
		Block hitBlock = blocks.get( blocks.size() - 1 );
		if ( pos[0] == pos[1] || pos[2] == pos[3] ) {
			return;
		}

		Bukkit.getPluginManager().callEvent( new PlayerRaycastHitEvent(evt.getPlayer(), hitBlock) );
		if ( playerHits.containsKey(playerIdentifier) ) {
			if ( playerHits.get(playerIdentifier).getBlock().getType() != hitBlock.getType() ) {
				Bukkit.getPluginManager().callEvent(
						new PlayerRaycastHitChangeEvent(
								player,
								playerHits.get(playerIdentifier).getBlock(),
								hitBlock
						)
				);
				playerHits.put(playerIdentifier, hitBlock.getLocation() );
			}
		} else {
			playerHits.put(playerIdentifier, hitBlock.getLocation() );
		}

	}
}
