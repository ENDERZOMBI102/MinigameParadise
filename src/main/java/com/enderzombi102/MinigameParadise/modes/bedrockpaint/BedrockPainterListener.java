package com.enderzombi102.MinigameParadise.modes.bedrockpaint;

import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitChangeEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

class BedrockPainterListener implements Listener {

	@EventHandler
	public void OnPlayerLook(PlayerRaycastHitChangeEvent evt) {
		// get the block pointed by the player, ignoring non-solid blocks
		Block block = evt.getNewHitResult(); // sight.get( sight.size() - 1 );
		// is it a solid block?
		if ( Util.solid.contains( block.getType() ) ) {
			// replace the pointed block with bedrock
			block.setType( Material.BEDROCK );
		}
	}
}
