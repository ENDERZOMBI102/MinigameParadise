package com.enderzombi102.MinigameParadise.modes.chunkeffect;

import com.enderzombi102.MinigameParadise.Random;
import com.enderzombi102.MinigameParadise.events.PlayerChangedChunkEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

import static com.enderzombi102.MinigameParadise.modes.chunkeffect.ChunkEffect.effects;

class ChunkEffectListener implements Listener {

	@EventHandler
	public void onPlayerChangeChunk(PlayerChangedChunkEvent evt) {
		// in case we didn't set an effect for a chunk, set it
		if (! ChunkEffect.instance.chunkEffects.containsKey( evt.getTo() ) ) {
			ChunkEffect.instance.chunkEffects.put(
					evt.getTo(),
					new PotionEffect(
							Random.randomEntry( effects ), // type
							Integer.MAX_VALUE, // duration
							Random.randomInt(100), // level
							false, // particles
							false // particles 2
					)
			);
		}

		// get new effect
		PotionEffect effect = ChunkEffect.instance.chunkEffects.get( evt.getTo() );
		if ( evt.getPlayer().hasPotionEffect( effect.getType() ) ) return;
		// remove the old effect and apply the new one
		evt.getPlayer().addPotionEffect( effect );
		evt.getPlayer().removePotionEffect(
				ChunkEffect.instance.chunkEffects.get( evt.getFrom() ).getType()
		);
	}

}
