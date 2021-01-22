package com.enderzombi102.MinigameParadise.modes.dropcalipse;

import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Dropcalipse extends ModeBase {

	public static Dropcalipse instance;
	private final DropcalipseListener listener;
	public boolean randomDrops;
	public int maxDrops;

	public Dropcalipse(boolean randomDrops, int maxDrops) {
		broadcastPrefixedMessage("starting the Dropcalipse!");
		Dropcalipse.instance = this;
		this.randomDrops = randomDrops;
		this.maxDrops = maxDrops;
		this.listener = new DropcalipseListener();
		Util.registerListener(this.listener);
		broadcastPrefixedMessage("the Dropcalipse is started! good luck!");
	}

	public ItemStack randomize(Item item) {

		ItemStack itemStack = item.getItemStack();
		int amount;
		do {
			amount = new Random().nextInt( this.maxDrops );
		} while( amount < 10);
		itemStack.setAmount( amount );
		if ( this.randomDrops ) {
			itemStack.setType( Material.values()[ new Random().nextInt( Material.values().length-1 ) ] );
		}
		return itemStack;
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}

	private static class DropcalipseListener implements Listener {

		@EventHandler
		public void OnBlockDrop(BlockDropItemEvent evt) {
			for (Item item : evt.getItems()) {
				Bukkit.broadcastMessage(item.getItemStack().toString());
				item.setItemStack( Dropcalipse.instance.randomize(item) );
			}
		}

		@EventHandler
		public void OnEntityDrop(EntityDropItemEvent evt) {
			Bukkit.broadcastMessage(evt.getItemDrop().getItemStack().toString());
			ItemStack drop = Dropcalipse.instance.randomize( evt.getItemDrop() );
			evt.getItemDrop().setItemStack( drop );
		}
	}
}
