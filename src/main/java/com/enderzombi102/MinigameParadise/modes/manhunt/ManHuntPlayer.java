package com.enderzombi102.MinigameParadise.modes.manhunt;

import org.bukkit.entity.Player;

public abstract class ManHuntPlayer {

	public boolean isAlive = true;
	public final Player handle;

	protected ManHuntPlayer(Player handle) {
		this.handle = handle;
	}

	public abstract void onRespawn();
	public abstract boolean onDamage(double damage);
	public abstract void onMove();

}
