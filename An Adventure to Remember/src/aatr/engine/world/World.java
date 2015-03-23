package aatr.engine.world;

import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.world.tile.Tile;
import aatr.engine.world.tile.TileSet;

public class World {
	public static final int GRID_SIZE = 64;
	private Chunk[][] map;
	
	public World() {
		initTestChunk();
	}
	
	public World(String mapPath) {
		
	}
	
	
	public void initTestChunk() {
		
		map = new Chunk[1][1];
		
		Tile[][] tileArray = new Tile[16][16];
		
		for(int x = 0; x < Chunk.GRID_DIMENSIONS; x++) {
			for(int y = 0; y < Chunk.GRID_DIMENSIONS; y++) {
				tileArray[x][y] = new Tile(TileSet.tileSets.get(0), 0, x, y);
			}
		}
		
		map[0][0] = new Chunk(tileArray);
	}
	
	 /*
	  * All .map files have 3 shorts in the header
	  * short width = The width of the map
	  * short height = THe height of the map
	  * short tileSet = The id number reffering to the tileset to be used
	  */
	public World loadMap(String mapPath) {
		short width, height, tileSet;
		//Read file and asign values
		short[] mapStream;
		
		
		
//		for(int x = 0; x < width; x++) {
//			for(int y = 0; y < height; y++) {
//				map[x][y] = new Chunk()
//			}
//		}
		
		return this;
	}
	
	public void draw() {
		for(Chunk[] ca : map) {
			for(Chunk c : ca) {
				c.draw();
			}
		}
	}
	
	
}
