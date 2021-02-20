package com.enderzombi102.MinigameParadise.modes.dropcalipse;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;

class DropcalipseListener implements Listener {

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
