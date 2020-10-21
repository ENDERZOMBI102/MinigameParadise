package com.enderzombi102.MinigameParadise.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerRaycastHitEvent extends PlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();
	private final Block hitResult;

	public PlayerRaycastHitEvent(Player who, Block hitresult) {
		super(who);
		this.hitResult = hitresult;
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
