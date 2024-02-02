package com.enderzombi102.MinigameParadise.modes.explodingcursor;

import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitEvent;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;


public class ExplodingCursor extends ModeBase {

	private final ExplodingCursorListener listener;

	public ExplodingCursor() {
		broadcastPrefixedMessage("started!");
		this.listener = new ExplodingCursorListener();
		Util.registerListener(this.listener);
	}


	@Override
	public void stop() {
		HandlerList.unregisterAll( this.listener );
	}

}
