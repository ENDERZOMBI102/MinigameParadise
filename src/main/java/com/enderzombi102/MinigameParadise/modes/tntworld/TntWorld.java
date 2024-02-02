package com.enderzombi102.MinigameParadise.modes.tntworld;

import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class TntWorld extends ModeBase {

	private final TntWorldListener listener;
	static final HashMap<UUID, Integer> playerTntCountdown = new HashMap<>();


	public TntWorld() {
		broadcastPrefixedMessage("started!");
		this.listener = new TntWorldListener();
		Util.registerListener(this.listener);
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}
}
