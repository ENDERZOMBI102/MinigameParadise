package com.enderzombi102.MinigameParadise;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

public final class LogHelper {
	
	private static Logger logger = Bukkit.getLogger();
	
	public static void Info(String txt) {
		LogHelper.logger.info(txt);
	}
	
	public static void Warn(String txt) {
		LogHelper.logger.warning(txt);
	}
	
	public static void Error(String txt) {
		LogHelper.logger.severe(txt);
	}
}
