package com.enderzombi102.MinigameParadise.modes.tagged;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class Tagged extends ModeBase {

	static Tagged instance;
	public TaggedListener listener = new TaggedListener();
	public final ArrayList<Player> alivePlayers = new ArrayList<>( Bukkit.getOnlinePlayers() );
	public final ArrayList<Player> deadPlayers = new ArrayList<>();

	public Tagged() {
		broadcastPrefixedMessage("Starting Tagged!");
		Util.registerListener(this.listener);
		Tagged.instance = this;
		broadcastPrefixedMessage("Tagged started!");
		broadcastPrefixedMessage("Hit another player with the empty hand to kill him!");
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
		Tagged.instance = null;

		for ( Player player : Bukkit.getOnlinePlayers() ) {
			player.setHealthScale(1.0);
			logger.debug("resetted " + player.getName() + "'s healthscale to 1.0");
		}
		MinigameParadise.activeModes.remove(this);
	}

	public void checkWin() {
		// win check
		if ( this.alivePlayers.size() != 1 ) return;

		// scoreboard
		StringBuilder builder = new StringBuilder();
		builder.append(" - ")
				.append( this.alivePlayers.get(0).getName() )
				.append(" tagged ")
				.append( (int) (this.alivePlayers.get(0).getHealthScale() - 1) * 10 )
				.append(" players\n");
		// winner
		int max = (int) ( this.alivePlayers.get(0).getHealthScale() - 1 ) * 10;
		String winner = this.alivePlayers.get(0).getName();
		// looser
		int min = (int) ( this.alivePlayers.get(0).getHealthScale() - 1 ) * 10;
		String looser = this.alivePlayers.get(0).getName();

		// check who got the most tags
		for (Player player : this.deadPlayers ) {
			if ( ( player.getHealthScale() - 1 ) * 10 > max ) {
				max = (int) ( player.getHealthScale() - 1 ) * 10;
				winner = player.getName();
			}
			if ( ( player.getHealthScale() - 1 ) * 10 < min ) {
				min = (int) ( player.getHealthScale() - 1 ) * 10;
				looser = player.getName();
			}
			builder.append(" - ")
					.append( player.getName() )
					.append(" tagged ")
					.append( (int) (player.getHealthScale() - 1) * 10 )
					.append(" players\n");
		}

		broadcastPrefixedMessage("Game finished!");
		broadcastPrefixedMessage("Last man standing: " + this.alivePlayers.get(0).getName() );
		broadcastPrefixedMessage("Winner: " + winner);
		broadcastPrefixedMessage("Last: " + looser);
		broadcastPrefixedMessage("Players points:\n" + builder.toString() );
		this.stop();
	}
}
