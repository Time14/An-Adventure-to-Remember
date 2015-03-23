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
		
		tileArray[0][0] = new Tile(TileSet.tileSets.get(0), 8, 0, 0);
		tileArray[1][0] = new Tile(TileSet.tileSets.get(0), 9, 1, 0);
		tileArray[2][0] = new Tile(TileSet.tileSets.get(0), 10, 2, 0);
		tileArray[0][1] = new Tile(TileSet.tileSets.get(0), 16, 0, 1);
		tileArray[1][1] = new Tile(TileSet.tileSets.get(0), 17, 1, 1);
		tileArray[2][1] = new Tile(TileSet.tileSets.get(0), 18, 2, 1);
		tileArray[0][2] = new Tile(TileSet.tileSets.get(0), 24, 0, 2);
		tileArray[1][2] = new Tile(TileSet.tileSets.get(0), 25, 1, 2);
		tileArray[2][2] = new Tile(TileSet.tileSets.get(0), 26, 2, 2);
		
		tileArray[3][0] = new Tile(TileSet.tileSets.get(0), 11, 3, 0);
		tileArray[4][0] = new Tile(TileSet.tileSets.get(0), 12, 4, 0);
		tileArray[5][0] = new Tile(TileSet.tileSets.get(0), 13, 5, 0);
		tileArray[3][1] = new Tile(TileSet.tileSets.get(0), 19, 3, 1);
		tileArray[4][1] = new Tile(TileSet.tileSets.get(0), 20, 4, 1);
		tileArray[5][1] = new Tile(TileSet.tileSets.get(0), 21, 5, 1);
		tileArray[3][2] = new Tile(TileSet.tileSets.get(0), 27, 3, 2);
		tileArray[4][2] = new Tile(TileSet.tileSets.get(0), 28, 4, 2);
		tileArray[5][2] = new Tile(TileSet.tileSets.get(0), 29, 5, 2);
		
		tileArray[7][7] = new Tile(TileSet.tileSets.get(0), 3, 7, 7);
		
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
