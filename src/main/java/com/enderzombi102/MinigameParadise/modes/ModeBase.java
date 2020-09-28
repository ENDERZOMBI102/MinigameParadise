package com.enderzombi102.MinigameParadise.modes;

import org.bukkit.Bukkit;

public abstract class ModeBase {
	
	protected void broadcastPrefixedMessage(String text) {
		Bukkit.getServer().broadcastMessage("["+this.getClass().getSimpleName()+"] "+text);
	}
	
	public abstract void stop();
}