package com.enderzombi102.MinigameParadise.modes.blockpainter;

import java.util.List;
import java.util.Random;

import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitChangeEvent;
import com.google.common.collect.Sets;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;


public class BlockPainter extends ModeBase {

	private final BlockPainterListener listener;

	public BlockPainter() {
		broadcastPrefixedMessage("Starting..");
		this.listener = new BlockPainterListener();
		Util.registerListener(this.listener);
		broadcastPrefixedMessage("Started!");
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}
}
