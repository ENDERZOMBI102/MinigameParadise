package com.enderzombi102.MinigameParadise.modes.dropcalipse;

import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Dropcalipse extends ModeBase {

	public static Dropcalipse instance;
	private DropcalipseListener listener;
	public boolean randomDrops;
	public int maxDrops;

	public Dropcalipse(boolean randomDrops, int maxDrops) {
		broadcastPrefixedMessage("starting the Dropcalipse!");
		Dropcalipse.instance = this;
		this.randomDrops = randomDrops;
		this.maxDrops = maxDrops;
		this.listener = new DropcalipseListener();
		broadcastPrefixedMessage("the Dropcalipse is started! good luck!");
	}




	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}


	private class DropcalipseListener implements Listener {
		@EventHandler
		public void OnBlockBreak(BlockDropItemEvent evt) {
			for ( Item item : evt.getItems() ) {
				ItemStack itemStack = item.getItemStack();
				itemStack.setAmount( new Random().nextInt( Dropcalipse.instance.maxDrops ) );
				if (Dropcalipse.instance.randomDrops) {
					itemStack.setType( Material.values()[ new Random().nextInt( Material.values().length-1 ) ] );
				}
				item.setItemStack( itemStack );
			}
		}
	}
}
