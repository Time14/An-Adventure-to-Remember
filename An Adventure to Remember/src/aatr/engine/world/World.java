package aatr.engine.world;

import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.world.tile.Tile;
import aatr.engine.world.tile.TileSet;

public class World {
	public static final int GRID_SIZE = 64;
	private TileSet[] tileSets;
	private Chunk[][] map;
	
	public World() {
		
	}
	
	public World(String mapPath) {
		loadMap(tileSet);
	}
	
	
	 /*
	  * All .map files have 3 shorts in the header
	  * short width = The width of the map
	  * short height = THe height of the map
	  * short tileSet = The id number reffering to the tileset to be used
	  */
	public World loadMap(String mapPath) {
		
		return this;
	}
	
	
}
