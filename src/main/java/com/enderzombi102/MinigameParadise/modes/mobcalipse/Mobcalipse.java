package com.enderzombi102.MinigameParadise.modes.mobcalipse;

import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import lombok.SneakyThrows;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Mobcalipse extends ModeBase {

	private final MobcalipseListener listener;
	private Difficulty oldDifficulty;
	private int oldSpawnLimit;
	private boolean oldDoLightCycle;

	public Mobcalipse() {
		broadcastPrefixedMessage("starting the Mobcalipse!");
		this.listener = new MobcalipseListener();
		Bukkit.getPluginManager().registerEvents(this.listener , MinigameParadise.instance);
		broadcastPrefixedMessage("preparing the world..");
		this.setupWorld();
		broadcastPrefixedMessage("the Mobpcalipse is started! good luck!");
	}

	public void setupWorld() {
		oldDifficulty = Bukkit.getWorlds().get(0).getDifficulty();
		oldSpawnLimit = Bukkit.getWorlds().get(0).getMonsterSpawnLimit();
		oldDoLightCycle = Bukkit.getWorlds().get(0).getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
		for (World world : Bukkit.getWorlds() ) {
			world.setDifficulty(Difficulty.PEACEFUL);
			world.setDifficulty(Difficulty.NORMAL);
			world.setTime(18000);
			world.setMonsterSpawnLimit(300);
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		}
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
		for (World world : Bukkit.getWorlds() ) {
			world.setDifficulty(oldDifficulty);
			world.setMonsterSpawnLimit(oldSpawnLimit);
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, oldDoLightCycle);
		}
	}

	private class MobcalipseListener implements Listener {

		@EventHandler
		public void OnPlayerSleep(PlayerBedLeaveEvent evt) {
			if ( Util.randomPercent(1) ) evt.getBed().breakNaturally();
		}

		@EventHandler
		public void OnEntityDrop(EntityDropItemEvent evt) {
			Bukkit.broadcastMessage(evt.getItemDrop().getItemStack().toString());
			ItemStack drop = randomizeDrop( evt.getItemDrop() );
			evt.getItemDrop().setItemStack( drop );
		}

		@SneakyThrows
		@EventHandler
		public void onEntitySpawn(EntitySpawnEvent evt) {
			boolean editWeapon = true;
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
					return;
				case WOLF:
					Wolf wolf = (Wolf) evt.getEntity();
					wolf.setAware(true);
					wolf.setAngry(true);
					wolf.setAbsorptionAmount(4);
					return;
				case CREEPER:
					Creeper creeper = (Creeper) evt.getEntity();
					creeper.setPowered( Util.randomPercent(4) );
					creeper.setExplosionRadius( Util.randomInt(20, 1) );
					creeper.setSilent( Util.randomPercent(20) );
					creeper.setGlowing( Util.randomPercent(3) );
					return;
				case HUSK:
					Husk husk = (Husk) evt.getEntity();
					husk.setBaby( Util.randomPercent(47) );
					husk.setSilent( Util.randomPercent(20) );
					husk.setGlowing( Util.randomPercent(3) );
					break;
				case ZOMBIE:
					Zombie zombie = (Zombie) evt.getEntity();
					zombie.setBaby( Util.randomPercent(46) );
					zombie.setSilent( Util.randomPercent(20) );
					zombie.setGlowing( Util.randomPercent(3) );
					if ( Util.randomPercent(30) ) zombie.setHealth( Util.randomInt(100, 20) );
					else zombie.setHealth( Util.randomInt(40, 20) );
					break;
				case WITCH:
					Witch witch = (Witch) evt.getEntity();
					witch.setHealth( Util.randomInt(40, 20) );
					witch.setSilent( Util.randomPercent(20) );
					witch.setGlowing( Util.randomPercent(3) );
					break;
				case PHANTOM:
					Phantom phantom = (Phantom) evt.getEntity();
					if ( Util.randomPercent(10) ) phantom.setSize( Util.randomInt(40, 2) );
					phantom.setCanPickupItems(true);
					phantom.setSilent( Util.randomPercent(30) );
					phantom.setGlowing( Util.randomPercent(3) );
					break;
				case ENDERMITE:
					Endermite endermite = (Endermite) evt.getEntity();
					endermite.setInvulnerable( Util.randomPercent(1) );
					break;
				case SKELETON:
					Skeleton skeleton = (Skeleton) evt.getEntity();
					skeleton.setInvisible( Util.randomPercent(10) );
					skeleton.setSilent( Util.randomPercent(20) );
					skeleton.setGlowing( Util.randomPercent(3) );
					skeleton.setHealth( Util.randomInt(50, 20) );
					break;
				case PIGLIN:
					Piglin piglin = (Piglin) evt.getEntity();
					ItemStack piglinItem = piglin.getEquipment().getItemInMainHand();
					if ( Util.randomPercent(40) ) {
						List<ItemStack> weapons = ( (List<ItemStack>) Util.weapon.clone() )
								.stream().filter(itemStack -> itemStack.getType() == Material.BOW)
								.collect( Collectors.toList() );
						piglinItem.setType( Util.randomEntry( weapons ).getType() );
					}
					if ( Util.randomPercent(50) ) {
						Enchantment enchant = Util.randomEntry( Util.weapon_enchants );
						if (enchant != null) piglinItem.addEnchantment( enchant, Util.randomInt(20) );
					}
					piglin.getEquipment().setItemInMainHand( piglinItem );
					editWeapon = false;
					break;
			}
			//all the other mobs will have armor
			LivingEntity entity = (LivingEntity) evt.getEntity();
			entity.getEquipment().setArmorContents( randomArmor() );
			if (editWeapon) {
				ItemStack weapon = Util.randomEntry( Util.weapon );
				if (weapon != null) {
					weapon.addEnchantment(
							Util.randomEntry( Util.weapon_enchants), // enchant
							Util.randomInt(5 ) // level
					);
				}
				entity.getEquipment().setItemInMainHand(weapon);
			}
		}

		@EventHandler
		public void OnEntityMove(EntityDamageByEntityEvent evt) {
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
				evt.setDamage(0);
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
	}

	// returns random choosen armor (diamond, chain, leather, gold, iron, null)
	private ItemStack[] randomArmor() {
		ItemStack[] armor = {null, null, null, null};
		armor[3] = Util.randomEntry( Util.helmet );
		armor[2] = Util.randomEntry( Util.chestplate );
		armor[1] = Util.randomEntry( Util.leggins );
		armor[0] = Util.randomEntry( Util.boots );
		return armor;
	}
}
