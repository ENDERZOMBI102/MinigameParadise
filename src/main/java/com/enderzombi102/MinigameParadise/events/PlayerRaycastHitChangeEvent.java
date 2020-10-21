package com.enderzombi102.MinigameParadise.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerRaycastHitChangeEvent extends PlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();
	private final Block hitResult;

	public PlayerRaycastHitChangeEvent(Player who, Block hitResult) {
		super(who);
		this.hitResult = hitResult;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public Block getHitResult() {
		return this.hitResult;
	}

}
