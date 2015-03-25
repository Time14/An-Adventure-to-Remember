package aatr.engine.world;

import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.world.player.Player;
import aatr.engine.world.tile.Tile;
import aatr.engine.world.tile.TileSet;

public class World {
	public static final int GRID_SIZE = 5;
	public static final int MAX_DRAWDISTANCE = 1;
	private Chunk[][] map;
	
	public World() {
		initTestChunk();
	}
	
	public World(String mapPath) {
		
	}
	
	
	public void initTestChunk() {
		
		
		int ts = 1;
		int dim = 10;
		map = new Chunk[dim][dim];
		
		for(int cx = 0; cx < dim; cx++) {
			for(int cy = 0; cy < dim; cy++) {
				Tile[][] tileArray = new Tile[16][16];
				for(int x = 0; x < Chunk.GRID_DIMENSIONS; x++) {
					for(int y = 0; y < Chunk.GRID_DIMENSIONS; y++) {
						tileArray[x][y] = new Tile(ts, x + cx * Chunk.GRID_DIMENSIONS + (y + cy * Chunk.GRID_DIMENSIONS)*40, x, y);
					}
				}
				map[cx][cy] = new Chunk(tileArray); 
				map[cx][cy].getEntity().translate(cx*GRID_SIZE*Chunk.GRID_DIMENSIONS, cy*GRID_SIZE*Chunk.GRID_DIMENSIONS);
			}
		}
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
	
	public Tile getTile(int cx, int cy, int x, int y) {
		return map[cx][cy].getTile(x, y);
	}
	
	public void draw(Player player) {
		int x = ((int)(player.getX() - player.getX() % Chunk.GRID_DIMENSIONS) / Chunk.GRID_DIMENSIONS) - MAX_DRAWDISTANCE;
		int y = ((int)(player.getY() - player.getY() % Chunk.GRID_DIMENSIONS) / Chunk.GRID_DIMENSIONS) - MAX_DRAWDISTANCE;
		for(int i = x; i < (x+MAX_DRAWDISTANCE*2 + 1); i++) {
			for(int j = y; j < (y+MAX_DRAWDISTANCE*2 + 1); j++) {
				if(0 > i || i > map.length - 1 || 0 > j || j > map[i].length - 1)
					continue;
				System.out.println(i + "\t" + map.length);
				System.out.println(j + "\t" + map[i].length);
				map[i][j].draw();
			}
		}
	}
	
	public void destroy() {
		for(Chunk[] cs : map)
			for(Chunk c : cs)
				c.destroy();
	}
	
	
}
