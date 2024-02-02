package com.enderzombi102.MinigameParadise.modes.chunkeffect;

import com.enderzombi102.MinigameParadise.ChunkLocation;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class ChunkEffect extends ModeBase {

	static ChunkEffect instance;
	static final PotionEffectType[] effects = PotionEffectType.values();
	final HashMap<ChunkLocation, PotionEffect> chunkEffects = new HashMap<>();
	private final ChunkEffectListener listener;

	public ChunkEffect() {
		this.listener = new ChunkEffectListener();
		Util.registerListener( this.listener );
		ChunkEffect.instance = this;
		broadcastPrefixedMessage("Started!");
	}

	@SuppressWarnings("SuspiciousMethodCalls")
	@Override
	public void stop() {
		HandlerList.unregisterAll( this.listener );
		ChunkEffect.instance = null;
		for (Player player : Bukkit.getOnlinePlayers() ) {
			player.removePotionEffect( chunkEffects.get( player.getChunk() ).getType() );
		}
	}

}
