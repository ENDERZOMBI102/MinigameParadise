package com.enderzombi102.MinigameParadise.modes.blockpainter;

import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitChangeEvent;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

class BlockPainterListener implements Listener {

	@EventHandler
	public void OnPlayerLook(PlayerRaycastHitChangeEvent evt) {
		// get the block pointed by the player
		Block block = evt.getNewHitResult(); // sight.get( sight.size() - 1 );
		// is a solid block?
		if ( Util.solid.contains( block.getType() ) ) {
			// yes, get a random block
			int randID;
			do {
				randID = new Random().nextInt( Util.solid.size() );
			} while( Util.solid.get(randID) == block.getType() );
			// replace the pointed block by the one we chosen
			block.setType( Util.solid.get(randID) );
		}
	}
}