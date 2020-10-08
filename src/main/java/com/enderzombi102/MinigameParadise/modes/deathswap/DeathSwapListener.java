package com.enderzombi102.MinigameParadise.modes.deathswap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import com.enderzombi102.MinigameParadise.MinigameParadise;


public class DeathSwapListener implements Listener {

	public static DeathSwapListener instance;

	public DeathSwapListener() {
		DeathSwapListener.instance = this;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		// check if the mode "deathswap" is active
		if (! ( MinigameParadise.currentMode instanceof DeathSwap ) ) {
			// its not
			return;
		}
		DeathSwap.instance.checkWin( event.getEntity() );
	}

	@EventHandler
	public void onPlayerEnterNetherPortal(PlayerPortalEvent event) {
		// check if the mode "deathswap" is active
		if (! ( MinigameParadise.currentMode instanceof DeathSwap ) ) {
			// its not
			return;
		}
		if (! ( (DeathSwap) MinigameParadise.currentMode ).allowNether ) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("travel to the nether has been disabled by the rules");
		}
	}
}
