package com.enderzombi102.MinigameParadise.modes.deathswap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;


public class DeathSwapListener implements Listener {

	public static DeathSwapListener instance;

	public DeathSwapListener() {
		DeathSwapListener.instance = this;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		DeathSwap.instance.checkWin( event.getEntity() );
	}

	@EventHandler
	public void onPlayerEnterNetherPortal(PlayerPortalEvent event) {
		if (! DeathSwap.instance.allowNether ) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("travel to the nether has been disabled by the rules");
		}
	}
}
