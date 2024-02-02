package com.enderzombi102.MinigameParadise.modes.mobcalipse;

import com.enderzombi102.MinigameParadise.Random;
import com.enderzombi102.MinigameParadise.Util;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

class MobcalipseListener implements Listener {

	@EventHandler
	public void OnPlayerSleep(PlayerBedLeaveEvent evt) {
		if ( Random.possibility(1) ) {
			evt.getBed().breakNaturally();
			Mobcalipse.instance.logger.info("");
		}
	}

	@EventHandler
	public void OnEntityDrop(EntityDropItemEvent evt) {
		Bukkit.broadcastMessage( evt.getItemDrop().getItemStack().toString() ); // debug
		ItemStack itemStack = evt.getItemDrop().getItemStack();
		// randomize item drop count
		int amount = Random.randomInt(10);
		itemStack.setAmount( amount );
		evt.getItemDrop().setItemStack( itemStack );
	}

	@SuppressWarnings("unchecked")
	@SneakyThrows
	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent evt) {
		boolean editWeapon = true;
		switch ( evt.getEntityType() ) {
			// all those mobs will not be affected
			case PLAYER:
			case WANDERING_TRADER:
			case TRADER_LLAMA:
			case MUSHROOM_COW:
			case EVOKER_FANGS:
			case ARMOR_STAND:
			case UNKNOWN:
			case STRIDER:
			case VILLAGER:
			case DOLPHIN:
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
				return;
			case WOLF:
				Wolf wolf = (Wolf) evt.getEntity();
				wolf.setAware(true);
				wolf.setAngry(true);
				wolf.setAbsorptionAmount(4);
				return;
			case CREEPER:
				Creeper creeper = (Creeper) evt.getEntity();
				creeper.setPowered( Random.possibility(4) );
				creeper.setExplosionRadius( Random.randomInt(20, 1) );
				creeper.setSilent( Random.possibility(20) );
				creeper.setGlowing( Random.possibility(3) );
				return;
			case HUSK:
				Husk husk = (Husk) evt.getEntity();
				husk.setBaby( Random.possibility(47) );
				husk.setSilent( Random.possibility(20) );
				husk.setGlowing( Random.possibility(3) );
				break;
			case ZOMBIE:
				Zombie zombie = (Zombie) evt.getEntity();
				zombie.setBaby( Random.possibility(46) );
				zombie.setSilent( Random.possibility(20) );
				zombie.setGlowing( Random.possibility(3) );
				if ( Random.possibility(30) ) zombie.setHealth( Random.randomInt(100, 20) );
				else zombie.setHealth( Random.randomInt(40, 20) );
				break;
			case WITCH:
				Witch witch = (Witch) evt.getEntity();
				witch.setHealth( Random.randomInt(40, 20) );
				witch.setSilent( Random.possibility(20) );
				witch.setGlowing( Random.possibility(3) );
				break;
			case PHANTOM:
				Phantom phantom = (Phantom) evt.getEntity();
				if ( Random.possibility(10) ) phantom.setSize( Random.randomInt(40, 2) );
				phantom.setCanPickupItems(true);
				phantom.setSilent( Random.possibility(30) );
				phantom.setGlowing( Random.possibility(3) );
				break;
			case ENDERMITE:
				Endermite endermite = (Endermite) evt.getEntity();
				endermite.setInvulnerable( Random.possibility(1) );
				break;
			case SKELETON:
				Skeleton skeleton = (Skeleton) evt.getEntity();
				skeleton.setInvisible( Random.possibility(10) );
				skeleton.setSilent( Random.possibility(20) );
				skeleton.setGlowing( Random.possibility(3) );
				skeleton.setHealth( Random.randomInt(50, 20) );
				break;
			case PIGLIN:
				Piglin piglin = (Piglin) evt.getEntity();
				ItemStack piglinItem = piglin.getEquipment().getItemInMainHand();
				if ( Random.possibility(40) ) {
					List<ItemStack> weapons = ( (List<ItemStack>) Util.weapon.clone() )
							.stream().filter(itemStack -> itemStack.getType() == Material.BOW)
							.collect( Collectors.toList() );
					piglinItem.setType( Random.randomEntry( weapons ).getType() );
				}
				if ( Random.possibility(50) ) {
					Enchantment enchant = Random.randomEntry( Util.weapon_enchants );
					if (enchant != null) piglinItem.addEnchantment( enchant, Random.randomInt(20) );
				}
				piglin.getEquipment().setItemInMainHand( piglinItem );
				editWeapon = false;
				break;
		}
		LivingEntity entity = evt.getEntity();
		// common attributes
		if (entity.getType() == EntityType.ZOMBIE ||
				entity.getType() == EntityType.SKELETON ||
				entity.getType() == EntityType.HUSK) {

			if (Random.possibility(20)) {
				Util.addInfPotion(entity, PotionEffectType.INCREASE_DAMAGE, 2, false);
			} else if (Random.possibility(20)) {
				Util.addInfPotion(entity, PotionEffectType.DAMAGE_RESISTANCE, 3, false);
			}
		}
		if (entity.getType() == EntityType.CREEPER || entity.getType() == EntityType.ZOMBIE) {
			if (Random.possibility(40)) Util.addInfPotion(entity, PotionEffectType.JUMP, 5, false);
		}
		//all the other mobs will have armor
		entity.getEquipment().setArmorContents( randomArmor() );
		if (editWeapon) {
			ItemStack weapon = Random.randomEntry( Util.weapon );
			if (weapon != null) {
				weapon.addEnchantment(
						Random.randomEntry( Util.weapon_enchants), // enchant
						Random.randomInt(5 ) // level
				);
			}
			entity.getEquipment().setItemInMainHand(weapon);
		}
	}

	@EventHandler
	public void OnEntityDamage(EntityDamageByEntityEvent evt) {
		switch (evt.getEntityType()) {
			case SKELETON:
			case WITCH:
			case PHANTOM:
			case ZOMBIFIED_PIGLIN:
			case ZOMBIE_VILLAGER:
			case WITHER_SKELETON:
			case CAVE_SPIDER:
			case VINDICATOR:
			case ENDERMITE:
			case SILVERFISH:
			case MAGMA_CUBE:
			case PILLAGER:
			case RAVAGER:
			case ZOMBIE:
			case SPIDER:
			case BLAZE:
			case HUSK:
				break;
			default:
				return;
		}
		if ( evt.getDamager().getType() != EntityType.PLAYER ) {
			// if the damage was dealt by an entity, discard it and search for targets
			evt.setCancelled(true);
			Mob ent = (Mob) evt.getEntity();
			List<Entity> possibleTargets = ent.getNearbyEntities(20, 20, 20)
					.stream().filter(entity -> entity.getType() == EntityType.PLAYER)
					.collect( Collectors.toList() );
			if (possibleTargets.size() > 0) ent.setTarget( (LivingEntity) possibleTargets.get(0) );
		} else {
			// if the damage was dealt by a player, attack it
			( (Mob) evt.getEntity() ).setTarget( (LivingEntity) evt.getDamager()  );
		}
	}

	// returns random chosen armor (diamond, chain, leather, gold, iron, null)
	@NotNull
	private ItemStack[] randomArmor() {
		ItemStack[] armor = {null, null, null, null};
		armor[3] = Random.randomEntry( Util.helmet );
		armor[2] = Random.randomEntry( Util.chestplate );
		armor[1] = Random.randomEntry( Util.leggins );
		armor[0] = Random.randomEntry( Util.boots );
		return armor;
	}
}
