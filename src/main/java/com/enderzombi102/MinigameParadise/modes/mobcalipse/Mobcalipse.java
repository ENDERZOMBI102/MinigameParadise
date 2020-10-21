package com.enderzombi102.MinigameParadise.modes.mobcalipse;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mobcalipse extends ModeBase {

	private final MobcalipseListener listener;
	public boolean randomDrops;
	public int maxDrops;

	public Mobcalipse() {
		broadcastPrefixedMessage("starting the Mobcalipse!");
		this.listener = new MobcalipseListener();
		Bukkit.getPluginManager().registerEvents(this.listener , MinigameParadise.instance);
		broadcastPrefixedMessage("the Dropcalipse is started! good luck!");
	}

	public ItemStack randomizeDrop(Item item) {
		ItemStack itemStack = item.getItemStack();
		int amount = new Random().nextInt( 10 );
		itemStack.setAmount( amount );
		return itemStack;
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}

	private class MobcalipseListener implements Listener {

		@EventHandler
		public void OnEntityDrop(EntityDropItemEvent evt) {
			Bukkit.broadcastMessage(evt.getItemDrop().getItemStack().toString());
			ItemStack drop = randomizeDrop( evt.getItemDrop() );
			evt.getItemDrop().setItemStack( drop );
		}

		@EventHandler
		public void onEntitySpawn(EntitySpawnEvent evt) {
			switch ( evt.getEntityType() ) {
				// all those mobs will not be affected
				case PLAYER:
				case BOAT:
				case ARROW:
				case MINECART_MOB_SPAWNER:
				case AREA_EFFECT_CLOUD:
				case MINECART_FURNACE:
				case THROWN_EXP_BOTTLE:
				case WANDERING_TRADER:
				case MINECART_COMMAND:
				case MINECART_HOPPER:
				case SPECTRAL_ARROW:
				case MINECART_CHEST:
				case EXPERIENCE_ORB:
				case SHULKER_BULLET:
				case SPLASH_POTION:
				case FALLING_BLOCK:
				case TRADER_LLAMA:
				case ENDER_CRYSTAL:
				case WITHER_SKULL:
				case MUSHROOM_COW:
				case MINECART_TNT:
				case FISHING_HOOK:
				case DROPPED_ITEM:
				case LEASH_HITCH:
				case EVOKER_FANGS:
				case ENDER_SIGNAL:
				case ENDER_PEARL:
				case ARMOR_STAND:
				case PRIMED_TNT:
				case LLAMA_SPIT:
				case ITEM_FRAME:
				case LIGHTNING:
				case SNOWBALL:
				case UNKNOWN:
				case FIREWORK:
				case MINECART:
				case PAINTING:
				case TRIDENT:
				case STRIDER:
				case VILLAGER:
				case DOLPHIN:
				case EGG:
				case BAT:
				case CAT:
					return;
					// will have a target
				case COD:
				case COW:
				case FOX:
				case PIG:
				case BEE:
				case MULE:
				case HORSE:
				case LLAMA:
				case PANDA:
				case SHEEP:
				case SQUID:
				case DONKEY:
				case OCELOT:
				case PARROT:
				case RABBIT:
				case PUFFERFISH:
				case POLAR_BEAR:
				case WOLF:
					LivingEntity entity = (LivingEntity) evt.getEntity();
					List<Entity> targets = Lists.newArrayList( entity.getWorld().getNearbyEntities(entity.getBoundingBox(), ent -> ent.getType() == EntityType.PLAYER ).iterator() );
					if (targets.size() > 0) {
						entity.attack( targets.get(0) );
					}
					return;
				case CREEPER:
					( (Creeper) evt.getEntity() ).setPowered( new Random().nextBoolean() );
					return;
				case HUSK:
					( (Husk) evt.getEntity() ).setBaby( new Random().nextBoolean() );
					break;
				case ZOMBIE:
					( (Zombie) evt.getEntity() ).setBaby( new Random().nextBoolean() );
					break;

			}
			//all the other mobs will have armor
			LivingEntity entity = (LivingEntity) evt.getEntity();
			entity.getEquipment().setArmorContents( randomArmor() );
			ItemStack weapon = Util.weapon.get( new Random().nextInt( Util.weapon.size() ) );
			if (weapon != null) {
				weapon.addEnchantment(
						Util.weapon_enchants.get(new Random().nextInt(Util.weapon_enchants.size())), // enchant
						new Random().nextInt(5) // level
				);
			}
			entity.getEquipment().setItemInMainHand( weapon );
		}
	}

	private ItemStack[] randomArmor() {
		ItemStack [] armor = {null, null, null, null};
		armor[0] = Util.helmet.get( new Random().nextInt( Util.helmet.size() ) );
		armor[1] = Util.chestplate.get( new Random().nextInt( Util.chestplate.size() ) );
		armor[2] = Util.leggins.get( new Random().nextInt( Util.leggins.size() ) );
		armor[3] = Util.boots.get( new Random().nextInt( Util.boots.size() ) );
		return armor;
	}


}
