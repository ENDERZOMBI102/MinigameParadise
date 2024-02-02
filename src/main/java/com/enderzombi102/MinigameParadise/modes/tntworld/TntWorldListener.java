package com.enderzombi102.MinigameParadise.modes.tntworld;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static com.enderzombi102.MinigameParadise.modes.tntworld.TntWorld.playerTntCountdown;

class TntWorldListener implements Listener {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent evt) {
		Location oldPos = evt.getFrom(), newPos = evt.getTo();
		int [] pos = {
				( ( Double ) oldPos.getX() ).intValue(),
				( ( Double ) newPos.getX() ).intValue(),
				( ( Double ) oldPos.getZ() ).intValue(),
				( ( Double ) newPos.getZ() ).intValue()
		};
		if ( pos[0] == pos[1] && pos[2] == pos[3] ) {
			return;
		}
		// get player
		Player player = evt.getPlayer();
		// check if the player is present in the hashmap
		if ( playerTntCountdown.containsKey( player.getUniqueId() ) ) {
			// it is, do corrisponding action
			if ( playerTntCountdown.get( player.getUniqueId() ) > 5 ) {
				player.getLocation().getWorld().spawn( evt.getFrom(), TNTPrimed.class );
				playerTntCountdown.put( player.getUniqueId(), 0 );
			} else {
				playerTntCountdown.put( player.getUniqueId(), playerTntCountdown.get( player.getUniqueId() ) + 1 );
			}
		} else {
			// its not, add it
			playerTntCountdown.put( player.getUniqueId(), 0 );
		}
	}
}
