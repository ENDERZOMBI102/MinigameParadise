package com.enderzombi102.MinigameParadise.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerRaycastHitChangeEvent extends PlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();
	private final Block oldBlock;
	private final Block newBlock;

	public PlayerRaycastHitChangeEvent(Player who, Block oldBlock, Block newBlock) {
		super(who);
		this.oldBlock = oldBlock;
		this.newBlock = newBlock;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public Block getOldHitResult() {
		return this.oldBlock;
	}

	public Block getNewHitResult() {
		return this.newBlock;
	}

}
