package com.enderzombi102.MinigameParadise.modes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;

public abstract class ModeBase {

	public Logger logger = LogManager.getLogger( getClass().getSimpleName() );

	protected void broadcastPrefixedMessage(String text) {
		Bukkit.getServer().broadcastMessage("["+this.getClass().getSimpleName()+"] "+text);
	}
	
	public abstract void stop();
}