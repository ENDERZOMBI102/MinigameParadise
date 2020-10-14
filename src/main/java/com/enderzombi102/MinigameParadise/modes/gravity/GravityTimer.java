package com.enderzombi102.MinigameParadise.modes.gravity;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

public class GravityTimer extends BukkitRunnable {

	public boolean running = true;

	public void run() {
		if (this.running) {
			for ( Player player : Bukkit.getOnlinePlayers() ) {
				// don't count players in creative or spectator
				if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) continue;

				Location originalLocation = player.getLocation();

				for (int x = 0; x < 10; x++) {
					for (int y = 0; y < 10; y++) {
						for (int z = 0; z < 10; z++) {
							Block block = new Location(
									player.getWorld(),
									originalLocation.getX() + x,
									originalLocation.getY() + y,
									originalLocation.getZ() + z
							).getBlock();
							// not every block has to be checked
							if (
									block.getType() == Material.OBSIDIAN ||
									block.getType() == Material.BEDROCK ||
									block.getType().isInteractable() ||
									block.isLiquid() ||
									block.getRelative(BlockFace.DOWN).isEmpty()
							) continue;
							updateBlock( block.getLocation() );
						}
					}
				}
			}
		}
	}

	private void updateBlock(Location loc) {
		// get the block location and data
		BlockData data = loc.getBlock().getBlockData();
		// replace block with air
		loc.getBlock().setType(Material.AIR);
		// spawn falling block
		loc.getWorld().spawnFallingBlock( loc , data );
	}

}
