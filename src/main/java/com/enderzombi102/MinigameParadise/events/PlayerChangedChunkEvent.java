package com.enderzombi102.MinigameParadise.events;

import com.enderzombi102.MinigameParadise.ChunkLocation;
import com.enderzombi102.MinigameParadise.generalListeners.PlayerEventListener;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerChangedChunkEvent extends PlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();
	private ChunkLocation oldLocation;
	private ChunkLocation newLocation;

	public PlayerChangedChunkEvent(@NotNull Player who, ChunkLocation oldLoc, ChunkLocation newLoc) {
		super(who);
	}

	@NotNull
	@Override
	public HandlerList getHandlers() {
		return null;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public ChunkLocation getTo() {
		return newLocation;
	}

	public @Nullable ChunkLocation getFrom() {
		return oldLocation;
	}
}
