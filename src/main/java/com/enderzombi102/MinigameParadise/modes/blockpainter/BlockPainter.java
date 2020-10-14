package com.enderzombi102.MinigameParadise.modes.blockpainter;

import java.util.List;
import java.util.Random;
import com.google.common.collect.Sets;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;


public class BlockPainter extends ModeBase {

	private final BlockPainterListener listener;

	public BlockPainter() {
		broadcastPrefixedMessage("Starting..");
		this.listener = new BlockPainterListener();
		Bukkit.getPluginManager().registerEvents(this.listener , MinigameParadise.instance);
		broadcastPrefixedMessage("Started!");
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}

	public class BlockPainterListener implements Listener {

		@EventHandler
		public void OnPlayerLook(PlayerMoveEvent evt) {
			// get the block pointed by the player
			List<Block> sight = evt.getPlayer().getLineOfSight( Sets.complementOf( Util.unsolid ), 100);
			Block block = sight.get( sight.size() - 1 );
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
}
