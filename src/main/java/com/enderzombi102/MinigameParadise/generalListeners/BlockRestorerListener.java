package com.enderzombi102.MinigameParadise.generalListeners;

import com.enderzombi102.MinigameParadise.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class BlockRestorerListener implements Listener {

	private final HashMap<Location, BlockData> blocks = new HashMap<>();
	public static BlockRestorerListener INSTANCE = new BlockRestorerListener();
	public boolean active = false;

	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		Location location = evt.getBlock().getLocation();
		if (! blocks.containsKey(location) ) {
			blocks.put( location, evt.getBlock().getBlockData() );
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {
		Location location = evt.getBlock().getLocation();
		if (! blocks.containsKey(location) ) {
			blocks.put( location, evt.getBlock().getBlockData() );
		}
	}

	@EventHandler
	public void onBlockExplode(BlockExplodeEvent evt) {
		Location location = evt.getBlock().getLocation();
		if (! blocks.containsKey(location) ) {
			blocks.put( location, evt.getBlock().getBlockData() );
		}
	}

	@EventHandler
	public void onBlockInteract(PlayerInteractEvent evt) {
		if ( evt.hasBlock() ) {
			Location location = evt.getClickedBlock().getLocation();
			if (! blocks.containsKey(location) ) {
				blocks.put( location, evt.getClickedBlock().getBlockData() );
			}
		}
	}

	public void start() {
		Util.registerListener(this);
		this.active = true;
	}

	public void restore() {
		this.restore( -1 );
	}

	public void restore(int blockCount) {
		int i = 0;
		for ( Location loc : blocks.keySet() ) {
			if ( i == blockCount ) break;
			loc.getBlock().setBlockData(blocks.get(loc), false);
			i++;
		}
	}

	public void restoreAndStop() {
		this.restore();
		this.stop();
	}

	public void stop() {
		if (!active) throw new RuntimeException("Called stop() when not started");
		HandlerList.unregisterAll(this);
	}

}
