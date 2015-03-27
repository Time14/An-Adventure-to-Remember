package aatr.engine.world;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11.*;

import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.gfx.mesh.Transform;
import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.util.Util;
import aatr.engine.world.entity.Entity;
import aatr.engine.world.tile.Tile;

public class Chunk {

	public static final int GRID_DIMENSIONS = 16;

	private Tile[][] tiles = new Tile[GRID_DIMENSIONS][GRID_DIMENSIONS];

	private Vertex[] verts;

	private Entity entity;

	//#EdvardQuickFix!
	private static Tile[][] convertTiles(Tile[] tiles) {
		Tile[][] tiles2D = new Tile[GRID_DIMENSIONS][GRID_DIMENSIONS];
		
		for (int i = 0; i < GRID_DIMENSIONS; i++)
			for (int j = 0; j < GRID_DIMENSIONS; j++)
				tiles2D[i][j] = tiles[i + j * GRID_DIMENSIONS];
		
		return tiles2D;
	}

	public Chunk(Tile[] tiles) {
		this(convertTiles(tiles));
	}


	public Chunk(Tile[][] tiles) {
		this.tiles = tiles;
		init();
	}

	public void init() {
		verts = new Vertex[((int) Math.pow(GRID_DIMENSIONS, 2)) * 4];

		for (int x = 0; x < tiles.length; x++) {

			for (int y = 0; y < tiles[x].length; y++) {

				Vertex[] vertHolder = tiles[x][y].getVertices(x, y);

				for (int i = 0; i < vertHolder.length; i++) {
					verts[(int) (x * (vertHolder.length) + i + GRID_DIMENSIONS
							* vertHolder.length * y)] = vertHolder[i];
				}
			}
		}

		entity = new Entity(new Mesh(verts).setMode(GL11.GL_QUADS), tiles[0][0]
				.getTileSet().getTexture());
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public void destroy() {
		for (Tile[] ts : tiles)
			for (Tile t : ts)
				t.destroy();
		entity.destroy();
	}

	public int getActiveTiles() {
		int i = 0;
		for (Tile[] ts : tiles)
			for (Tile t : ts)
				if (t != null)
					i++;

		return i;
	}

	public Chunk draw() {
		entity.draw();
		return this;
	}

	public Entity getEntity() {
		return entity;
	}
}
