package com.enderzombi102.MinigameParadise.modes.tagged;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TaggedListener implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent evt) {
		// entity check
		if (! ( evt.getEntity() instanceof Player && evt.getDamager() instanceof Player ) ) return;

		final Player tagger = (Player) evt.getDamager();
		final Player tagged = (Player) evt.getEntity();

		// player check
		if (! Tagged.instance.alivePlayers.contains( tagger ) ) return;
		if (! Tagged.instance.alivePlayers.contains( tagged ) ) return;

		// empty hand check
		if ( tagger.getInventory().getItem( tagger.getInventory().getHeldItemSlot() ) != null ) return;

		// finish game for tagged player
		Tagged.instance.alivePlayers.remove( tagged );
		Tagged.instance.deadPlayers.add( tagged );
		tagged.setGameMode( GameMode.SPECTATOR );
		tagged.getInventory().clear();
		tagged.sendActionBar("You have been tagged by " + tagger.getName() + "!");

		// reward tagger player
		tagger.setHealthScale( tagger.getHealthScale() + 0.1 );
		tagger.sendActionBar("You tagged " + tagged.getName() + "!");

		// let everyone know tagger tagged tagged
		Bukkit.broadcastMessage( "[Tagged] " + tagger.getName() + " tagged " + tagged.getName() + "." );

		Tagged.instance.checkWin();
	}

}
