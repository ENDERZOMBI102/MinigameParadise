package com.enderzombi102.MinigameParadise.modes.manhunt;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.stream.Collectors;

public class Hunted extends ManHuntPlayer {

	public Location lastLocation;

	public Hunted(Player handle) {
		super(handle);
	}

	@Override
	public void onRespawn() {}

	@Override
	public boolean onDamage(double damage) {
		boolean returnCode= false;
		// this damage would kill him?
		if ( damage >= this.handle.getHealth() ) {
			// if deathSpectator is true, put a target into spectator mode as he died
			if ( ManHunt.instance.deathSpectator ) {
				// "kill" the hunted
				returnCode = true;
				this.handle.closeInventory(InventoryCloseEvent.Reason.DEATH);
				this.handle.getInventory().clear();
				this.handle.setGameMode(GameMode.SPECTATOR);
			}

			// if there is more hunted players alive, target another hunted player
			if (! ManHunt.instance.allPreyDead() ) {
				// cycle in the hunters
				for (Hunter hunter : ManHunt.instance.getHunters() ) {
					// update only the hunters who targeted this player
					if ( this.handle == hunter.handle ) {
						// update their target to the first hunted player
						hunter.setPrey( ManHunt.instance.getPreys(true).get(0).handle );
					}
				}
			}
			// check if every damaged is dead
			ManHunt.instance.checkFinish();
		}
		return returnCode;
	}

	@Override
	public void onMove() {

	}

}
