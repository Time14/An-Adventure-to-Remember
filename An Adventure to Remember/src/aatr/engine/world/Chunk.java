package aatr.engine.world;

import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.world.tile.Tile;

public class Chunk {
	
	public static final int GRID_DIMENSIONS = 16;
	
	private Tile[][] tiles = new Tile[GRID_DIMENSIONS][GRID_DIMENSIONS];
	
	private Vertex[] verts;
	
	public Chunk(Tile[][] tiles) {
		this.tiles = tiles;
		verts = new Vertex[((int)Math.pow(GRID_DIMENSIONS, 2)) * 4];
	}
	
	public void draw() {
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles.length; y++) {
				Vertex[] vertHolder = tiles[x][y].getVertices();
				for(int i = 0; i  < vertHolder.length; i++)
					verts[(int)(x * 4 + i + GRID_DIMENSIONS * y)] = vertHolder[i];
			}
		}
		
	}
}
