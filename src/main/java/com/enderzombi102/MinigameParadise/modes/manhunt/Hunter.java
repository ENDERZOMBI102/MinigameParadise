package com.enderzombi102.MinigameParadise.modes.manhunt;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.enderzombi102.MinigameParadise.modes.manhunt.ManHunt.updateCompassMeta;

public class Hunter extends ManHuntPlayer {

	private Player prey = null;

	public Hunter(Player handle) {
		super(handle);
	}

	public void setPrey(Player player) {
		this.prey = player;
	}

	@Override
	public void onMove() {

	}

	@Override
	public void onRespawn() {
		if (ManHunt.instance.giveCompassOnRespawn) {
			// on respawn, give the hunter another compass
			this.giveCompass();
		}
	}

	@Override
	public boolean onDamage(double damage) {
		return false;
	}

	public void updateCompassLocation() {
		for ( ItemStack stack : this.handle.getInventory() ) {
			// its a compass?
			if ( stack.getType() != Material.COMPASS ) continue;
			// its the tracker compass?
			if (! stack.getItemMeta().getDisplayName().equals("Tracking Compass") ) continue;

			// update it
			updateCompassMeta( stack, this.prey.getLocation() );
		}
	}

	public void giveCompass() {
		// get the stack
		ItemStack stack = new ItemStack(Material.COMPASS);
		updateCompassMeta(
				stack,
				this.prey.getName()
		);
		updateCompassMeta(
				stack,
				this.prey.getLocation()
		);
		this.handle.getInventory().addItem( stack );
	}

}
