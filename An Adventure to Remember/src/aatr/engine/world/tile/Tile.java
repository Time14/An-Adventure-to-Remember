package aatr.engine.world.tile;

import org.lwjgl.util.vector.Vector2f;

import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.world.World;
import aatr.engine.world.entity.EntityQuad;

public class Tile {
	
	public static final int HEIGHT = World.GRID_SIZE;
	
	private TileSet tileSet;
	private int id;
	//Maybe not?
	private int x;
	private int y;
	
	private TileProperty[] properties;
	
	public Tile(TileSet tileSet, int id, int x, int y) {
		this.id = id;
		this.tileSet = tileSet;
		this.x = x;
		this.y = y;
	}
	
	
	public Vertex[] getVertices() {
		Vertex[] verts = new Vertex[4];
		Vector2f[] texCords = tileSet.getTexCoords(id);
	 	
		verts[0] = new Vertex(new Vector2f(x * HEIGHT, y * HEIGHT), texCords[0]);
		verts[1] = new Vertex(new Vector2f(x * HEIGHT + HEIGHT, y * HEIGHT), texCords[1]);
		verts[2] = new Vertex(new Vector2f(x * HEIGHT + HEIGHT, y * HEIGHT + HEIGHT), texCords[2]);
		verts[3] = new Vertex(new Vector2f(x * HEIGHT, y * HEIGHT + HEIGHT), texCords[3]);
		
		return verts;
	}
	
	public TileSet getTileSet() {
		return tileSet;
	}
}


/*
 * World
 * 		Chunks
 * 			Grid of tiles (16 * 16)
 * 				
 * */
 