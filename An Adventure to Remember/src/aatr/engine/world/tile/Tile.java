package aatr.engine.world.tile;

import org.lwjgl.util.vector.Vector2f;

public class Tile {
	
	private TileSet tileSet;
	private int id;
	
	private TileProperty[] properties;
	
	public Tile(TileSet tileSet, int id) {
		this.id = id;
		this.tileSet = tileSet;
	}
	
	public Vector2f[] getTexCoords() {
		Vector2f[] data = tileSet.getTexCoords(id);
		if(data.length != 4)
			throw new IllegalStateException("Invalid amount of texture coordinates (must be 4)");
		return data;
	}
	
}
