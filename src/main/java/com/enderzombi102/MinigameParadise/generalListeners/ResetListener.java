package com.enderzombi102.MinigameParadise.generalListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class ResetListener implements Listener {
	@EventHandler
	public void OnPlayerJoin(PlayerLoginEvent evt) {
		evt.disallow( PlayerLoginEvent.Result.KICK_OTHER, "The server is resetting, wait a bit more!");
	}
	@EventHandler
	public void OnServerListPing(ServerListPingEvent evt ) {
		evt.setMotd("The world is resetting, wait a bit!");
	}
}
