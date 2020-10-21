package com.enderzombi102.MinigameParadise;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Util {

	public static List<Material> solid = new ArrayList<>();
	public static List<Material> unsolid = new ArrayList<>();
	public static Set<Material> transparentBlocks;
	public static ArrayList<ItemStack> boots = new ArrayList<>();
	public static ArrayList<ItemStack> leggins = new ArrayList<>();
	public static ArrayList<ItemStack> chestplate = new ArrayList<>();
	public static ArrayList<ItemStack> helmet = new ArrayList<>();
	public static ArrayList<ItemStack> weapon = new ArrayList<>();
	public static ArrayList<Enchantment> weapon_enchants = new ArrayList<>();


	public static void setupLists() {
		// put into solid everything that is solid
		solid = Stream.of( Material.values() ).filter( Material::isSolid ).collect( Collectors.toList() );
		solid.remove(Material.BAMBOO);
		solid.remove(Material.BLACK_BANNER);
		solid.remove(Material.BLACK_WALL_BANNER);
		solid.remove(Material.BLUE_BANNER);
		solid.remove(Material.BLUE_WALL_BANNER);
		solid.remove(Material.RED_BANNER);
		solid.remove(Material.RED_WALL_BANNER);
		solid.remove(Material.ORANGE_BANNER);
		solid.remove(Material.ORANGE_WALL_BANNER);
		solid.remove(Material.CYAN_BANNER);
		solid.remove(Material.CYAN_WALL_BANNER);
		solid.remove(Material.WHITE_BANNER);
		solid.remove(Material.WHITE_WALL_BANNER);
		solid.remove(Material.GREEN_BANNER);
		solid.remove(Material.GREEN_WALL_BANNER);
		solid.remove(Material.LIME_BANNER);
		solid.remove(Material.LIME_WALL_BANNER);
		solid.remove(Material.LIGHT_BLUE_BANNER);
		solid.remove(Material.LIGHT_BLUE_WALL_BANNER);
		solid.remove(Material.GRAY_BANNER);
		solid.remove(Material.GRAY_WALL_BANNER);
		solid.remove(Material.LIGHT_GRAY_BANNER);
		solid.remove(Material.LIGHT_GRAY_WALL_BANNER);
		solid.remove(Material.MAGENTA_BANNER);
		solid.remove(Material.MAGENTA_WALL_BANNER);
		solid.remove(Material.YELLOW_BANNER);
		solid.remove(Material.YELLOW_WALL_BANNER);
		solid.remove(Material.PINK_BANNER);
		solid.remove(Material.PINK_WALL_BANNER);
		solid.remove(Material.BROWN_BANNER);
		solid.remove(Material.BROWN_WALL_BANNER);
		solid.remove(Material.PURPLE_BANNER);
		solid.remove(Material.PURPLE_WALL_BANNER);
		solid.remove(Material.WATER);
		solid.remove(Material.LAVA);

		// put into unsolid everything that isn't solid and an item
		unsolid = Stream.of( Material.values() ).filter( mat -> !mat.isSolid() && !mat.isItem() && !mat.isOccluding() ).collect( Collectors.toList() );

		transparentBlocks = Stream.of( Material.values() ).filter( material -> !material.isOccluding() ).collect( Collectors.toSet() );

		boots.add( null );
		boots.add( new ItemStack(Material.DIAMOND_BOOTS) );
		boots.add( new ItemStack(Material.LEATHER_BOOTS) );
		boots.add( new ItemStack(Material.CHAINMAIL_BOOTS) );
		boots.add( new ItemStack(Material.IRON_BOOTS) );
		boots.add( new ItemStack(Material.GOLDEN_BOOTS) );

		leggins.add( null );
		leggins.add( new ItemStack(Material.DIAMOND_LEGGINGS) );
		leggins.add( new ItemStack(Material.LEATHER_LEGGINGS) );
		leggins.add( new ItemStack(Material.CHAINMAIL_LEGGINGS) );
		leggins.add( new ItemStack(Material.IRON_LEGGINGS) );
		leggins.add( new ItemStack(Material.GOLDEN_LEGGINGS) );

		chestplate.add( null );
		chestplate.add( new ItemStack(Material.DIAMOND_CHESTPLATE) );
		chestplate.add( new ItemStack(Material.LEATHER_CHESTPLATE) );
		chestplate.add( new ItemStack(Material.CHAINMAIL_CHESTPLATE) );
		chestplate.add( new ItemStack(Material.IRON_CHESTPLATE) );
		chestplate.add( new ItemStack(Material.GOLDEN_CHESTPLATE) );

		helmet.add( null );
		helmet.add( new ItemStack(Material.DIAMOND_HELMET) );
		helmet.add( new ItemStack(Material.LEATHER_HELMET) );
		helmet.add( new ItemStack(Material.CHAINMAIL_HELMET) );
		helmet.add( new ItemStack(Material.IRON_HELMET) );
		helmet.add( new ItemStack(Material.GOLDEN_HELMET) );

		weapon.add( new ItemStack(Material.DIAMOND_AXE) );
		weapon.add( new ItemStack(Material.IRON_AXE) );
		weapon.add( new ItemStack(Material.GOLDEN_AXE) );
		weapon.add( new ItemStack(Material.DIAMOND_SWORD) );
		weapon.add( new ItemStack(Material.IRON_SWORD) );
		weapon.add( new ItemStack(Material.GOLDEN_SWORD) );
		weapon.add( new ItemStack(Material.BOW) );
		weapon.add( new ItemStack(Material.STONE_AXE) );
		weapon.add( new ItemStack(Material.TOTEM_OF_UNDYING) );

		weapon_enchants.add( Enchantment.FIRE_ASPECT);
		weapon_enchants.add( Enchantment.DAMAGE_ALL);
		weapon_enchants.add( Enchantment.ARROW_FIRE);
		weapon_enchants.add( null );

	}


}
