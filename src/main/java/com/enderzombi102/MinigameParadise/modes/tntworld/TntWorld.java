package com.enderzombi102.MinigameParadise.modes.tntworld;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class TntWorld extends ModeBase {

	private final TntWorldListener listener;


	public TntWorld() {
		broadcastPrefixedMessage("started!");
		this.listener = new TntWorldListener();
		Bukkit.getPluginManager().registerEvents(this.listener, MinigameParadise.instance);
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}

	private class TntWorldListener implements Listener {
		@EventHandler
		public void onPlayerMove(PlayerMoveEvent evt) {
			if ( evt.getFrom().getBlock().getLocation() == evt.getTo().getBlock().getLocation() ) {
				return;
			}
			evt.getPlayer().getLocation().getWorld().spawn( evt.getFrom(), TNTPrimed.class );
		}
	}
}
