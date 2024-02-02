package com.enderzombi102.MinigameParadise.modes.teletimer;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TeleTimer extends ModeBase {

	static TeleTimer instance;
	private final BukkitRunnable timerThread;
	private final boolean usePearls;

	public TeleTimer(int teleTime, boolean usePearls) {
		broadcastPrefixedMessage("setting up TeleTimer mode");
		// timer
		final int time = teleTime * 60;
		broadcastPrefixedMessage("timer: " + time / 60 + " minutes");
		this.timerThread = new TeleTimerTimer(time);
		this.timerThread.runTaskTimer(MinigameParadise.instance, 20, 20);
		// set instance
		TeleTimer.instance = this;
		// attributes
		this.usePearls = usePearls;
	}

	@Override
	public void stop() {
		this.timerThread.cancel();
	}

	public void teleport() {
		broadcastPrefixedMessage("Teleport!");
		for ( final Player player : Bukkit.getOnlinePlayers() ) {
			if (this.usePearls) {
				player.launchProjectile( EnderPearl.class );
			} else {
				final List<Block> blocks = player.getLineOfSight(Util.transparentBlocks, 200);
				final Location location = blocks.get( blocks.size() - 1 ).getLocation();
				player.teleport( location );
			}
		}
	}
}
