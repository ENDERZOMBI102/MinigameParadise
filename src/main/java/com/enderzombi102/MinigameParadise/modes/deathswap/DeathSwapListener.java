package com.enderzombi102.MinigameParadise.modes.deathswap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.enderzombi102.MinigameParadise.MinigameParadise;


public class DeathSwapListener implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		// check if the mode "deathswap" is active
		if (! ( MinigameParadise.currentMode != null && MinigameParadise.currentMode.getClass().getSimpleName().equals("DeathSwap") ) ) {
			// its not
			return;
		}
		DeathSwap.instance.checkWin( (Player) event.getEntity() );
	}
}
