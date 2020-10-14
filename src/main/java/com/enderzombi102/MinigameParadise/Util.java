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
		// put into unsolid everything that isn't solid and an item
		unsolid = Stream.of( Material.values() ).filter( mat -> !mat.isSolid() && !mat.isItem() ).collect( Collectors.toList() );
	}


}
