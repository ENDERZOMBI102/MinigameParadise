package com.enderzombi102.MinigameParadise;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.Objects;

public class ChunkLocation {

	final World world;
	final int x;
	final int z;

	public ChunkLocation(World world, int x, int z) {
		this.world = world;
		this.x = x;
		this.z = z;
	}

	public boolean sameChunk(Entity entity) {
		return this.sameChunk( entity.getChunk() );
	}

	public boolean sameChunk(Location loc) {
		return this.sameChunk( loc.getChunk() );
	}

	public boolean sameChunk(Chunk chunk) {
		return this.world.equals( chunk.getWorld() ) && this.x == chunk.getX() && this.z == chunk.getZ();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if ( o == null || getClass() != o.getClass() ) return false;
		ChunkLocation other = (ChunkLocation) o;
		return this.world.equals(other.world) && this.x == other.x && this.z == other.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, z);
	}
}
