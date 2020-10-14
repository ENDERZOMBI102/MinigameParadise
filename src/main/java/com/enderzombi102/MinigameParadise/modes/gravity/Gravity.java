package com.enderzombi102.MinigameParadise.modes.gravity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;

public class Gravity extends ModeBase {

	private GravityListener listener;


	public Gravity() {
		broadcastPrefixedMessage("starting..");
		this.listener = new GravityListener();
		Bukkit.getPluginManager().registerEvents(this.listener, MinigameParadise.instance);
		broadcastPrefixedMessage("started!");
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}

	private class GravityListener implements Listener {

		@EventHandler
		public void OnPlayerMove(PlayerMoveEvent evt) {
			Location player = evt.getPlayer().getLocation();
			int [] points = { player.getBlockX()-5, player.getBlockX()+5, player.getBlockZ()-5, player.getBlockZ()+5 };
			// cycle in the X axis
			for ( int x = points[0]; x < points[1]; x++) {
				// cycle in the Z axis
				for ( int z = points[0]; z < points[1]; z++) {
					// cycle in the Y axis
					for ( int y = 0; y < 200; y++) {
						Block block = player.getChunk().getBlock(x, y, z);
						// not every block has to be checked
						if (
								block.getType() == Material.OBSIDIAN ||
								Util.unsolid.contains( block.getType() ) ||
								block.getType() == Material.BEDROCK ||
								block.getType().isInteractable() ||
								block.isLiquid()
						) continue;
						// get the block location and data
						Location loc = block.getLocation();
						BlockData data = block.getBlockData();
						// replace block with air
						block.setType(Material.AIR);
						// spawn falling block
						evt.getPlayer().getWorld().spawnFallingBlock( loc , data );
					}
				}
			}
		}
	}
}
