package aatr.engine.world;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.gfx.renderer.Renderer;
import aatr.engine.util.Util;
import aatr.engine.world.tile.Tile;
import aatr.engine.world.tile.TileSet;

public class Chunk {

	public static final int GRID_DIMENSIONS = 16;
	
	private TileSet tileSet;
	
	private Tile[][] tiles = new Tile[GRID_DIMENSIONS][GRID_DIMENSIONS];
	
	private Vertex[] verts;
	
	private Renderer renderer;
	
	//#EdvardQuickFix!
	private static Tile[][] convertTiles(Tile[] tiles) {
		Tile[][] tiles2D = new Tile[GRID_DIMENSIONS][GRID_DIMENSIONS];
		
		for (int i = 0; i < GRID_DIMENSIONS; i++)
			for (int j = 0; j < GRID_DIMENSIONS; j++)
				tiles2D[i][j] = tiles[i + j * GRID_DIMENSIONS];
		
		return tiles2D;
	}

	public Chunk(Tile[] tiles, TileSet tileSet) {
		this(convertTiles(tiles), tileSet);
	}


	public Chunk(Tile[][] tiles, TileSet tileSet) {
		this.tiles = tiles;
		this.tileSet = tileSet;
		init();
	}

	public void init() {
		verts = new Vertex[((int) Math.pow(GRID_DIMENSIONS, 2)) * 4];

		for (int x = 0; x < tiles.length; x++) {

			for (int y = 0; y < tiles[x].length; y++) {

				Vertex[] vertHolder = tiles[x][y].getVertices(tileSet, x, y);

				for (int i = 0; i < vertHolder.length; i++) {
					verts[(int) (x * (vertHolder.length) + i + GRID_DIMENSIONS
							* vertHolder.length * y)] = vertHolder[i];
				}
			}
		}

		renderer = new Renderer(new Mesh(verts, GL15.GL_DYNAMIC_DRAW).setMode(GL11.GL_QUADS), tiles[0][0]
				.getTileSet().getTexture());
	}
	
	public void setTile(Tile tile) {
		
		int x = tile.getX() % GRID_DIMENSIONS;
		int y = tile.getY() % GRID_DIMENSIONS;
		
		tiles[x][y] = tile.setTileSet(tileSet);
		
		renderer.getMesh().changeVBOData(Vertex.SIZE * Vertex.LENGTH * (y * GRID_DIMENSIONS + x), Util.toFloatBuffer(tile.getVertices()));
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public void destroy() {
		for (Tile[] ts : tiles)
			for (Tile t : ts)
				t.destroy();
		renderer.destroy();
	}

	public int getActiveTiles() {
		int i = 0;
		for (Tile[] ts : tiles)
			for (Tile t : ts)
				if (t != null)
					i++;

		return i;
	}
	
	public TileSet getTileSet() {
		return tileSet;
	}

	public Chunk draw() {
		renderer.draw();
		return this;
	}

	public Renderer getEntity() {
		return renderer;
	}
}
