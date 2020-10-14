package com.enderzombi102.MinigameParadise.modes.bedrockpaint;

import java.util.List;
import com.google.common.collect.Sets;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;

public class BedrockPainter extends ModeBase {

	private final BedrockPainterListener listener;

	public BedrockPainter() {
		broadcastPrefixedMessage("Starting..");
		this.listener = new BedrockPainterListener();
		Bukkit.getPluginManager().registerEvents(this.listener , MinigameParadise.instance);
		broadcastPrefixedMessage("Started!");
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}

	public class BedrockPainterListener implements Listener {

		@EventHandler
		public void OnPlayerLook(PlayerMoveEvent evt) {
			// get the block pointed by the player, ignoring non-solid blocks
			List<Block> sight = evt.getPlayer().getLineOfSight( null, 100);
			Block block = sight.get( sight.size() - 1 );
			// is it a solid block?
			if ( Util.solid.contains( block.getType() ) ) {
				// replace the pointed block with bedrock
				block.setType( Material.BEDROCK );
			}
		}
	}
}
