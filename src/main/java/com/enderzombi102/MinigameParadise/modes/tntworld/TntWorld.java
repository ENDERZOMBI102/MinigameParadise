package com.enderzombi102.MinigameParadise.modes.tntworld;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class TntWorld extends ModeBase {

	private final TntWorldListener listener;
	private static final HashMap<UUID, Integer> playerTntCountdown = new HashMap<>();


	public TntWorld() {
		broadcastPrefixedMessage("started!");
		this.listener = new TntWorldListener();
		Bukkit.getPluginManager().registerEvents(this.listener, MinigameParadise.instance);
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}

	private class TntWorldListener implements Listener {
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
				if ( playerTntCountdown.get( player.getUniqueId() ) > 3 ) {
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
}
