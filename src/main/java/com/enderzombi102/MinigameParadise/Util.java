package com.enderzombi102.MinigameParadise;

import org.bukkit.Material;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

	public static List<Material> unsolid = null;


	public static void setupLists() {
		unsolid = Stream.of( Material.values() ).filter( Material::isSolid ).collect( Collectors.toList() );
	}


}
