package com.enderzombi102.MinigameParadise.modes.bedrockpaint;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.events.PlayerRaycastHitChangeEvent;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class BedrockPainter extends ModeBase {

	private final BedrockPainterListener listener;

	public BedrockPainter() {
		broadcastPrefixedMessage("Starting..");
		this.listener = new BedrockPainterListener();
		Bukkit.getPluginManager().registerEvents(this.listener , MinigameParadise.instance);
		broadcastPrefixedMessage("Started!");
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}
}
