package com.enderzombi102.MinigameParadise.modes.explodingcursor;

import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

class ExplodingCursorListener implements Listener {

	@EventHandler
	public void onPlayerRaycastHit(PlayerRaycastHitEvent evt) {
		evt.getHitResult().getWorld().createExplosion( evt.getHitResult().getLocation(), 5 );
	}
}
