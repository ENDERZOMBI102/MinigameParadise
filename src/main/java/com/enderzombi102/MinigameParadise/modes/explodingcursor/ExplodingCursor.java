package com.enderzombi102.MinigameParadise.modes.explodingcursor;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitEvent;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;


public class ExplodingCursor extends ModeBase {

	private final ExplodingCursorListener listener;

	public ExplodingCursor() {
		broadcastPrefixedMessage("started!");
		this.listener = new ExplodingCursorListener();
		Bukkit.getPluginManager().registerEvents( this.listener, MinigameParadise.instance);
	}


	@Override
	public void stop() {
		HandlerList.unregisterAll( this.listener );
	}

	private class ExplodingCursorListener implements Listener {

		@EventHandler
		public void onPlayerRaycastHit(PlayerRaycastHitEvent evt) {
			evt.getHitResult().getWorld().createExplosion( evt.getHitResult().getLocation(), 5 );
		}
	}
}
