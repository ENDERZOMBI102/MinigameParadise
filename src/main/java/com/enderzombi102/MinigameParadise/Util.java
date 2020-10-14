package com.enderzombi102.MinigameParadise;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Material;

public class Util {

	public static List<Material> solid = new ArrayList<>();
	public static List<Material> unsolid = new ArrayList<>();


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
	}


}
