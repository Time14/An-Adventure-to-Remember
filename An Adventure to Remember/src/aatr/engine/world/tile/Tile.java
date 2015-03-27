package aatr.engine.world.tile;

import java.util.ArrayList;

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
	
	private ArrayList<TileProperty> properties;
	
	public Tile(int tileSetID, int id) {
		this(TileSet.tileSets.get(tileSetID), id, 0, 0);
	}
	
	public Tile(int tileSetID, int id, int x, int y) {
		this(TileSet.tileSets.get(tileSetID), id, x, y);
	}
	
	public Tile(TileSet tileSet, int id, int x, int y) {
		this.id = id;
		this.tileSet = tileSet;
		this.x = x;
		this.y = y;
		this.properties = new ArrayList<TileProperty>();
	}
	
	
	public Vertex[] getVertices() {
		return getVertices(x, y);
	}
	
	public Vertex[] getVertices(int x, int y) {
		if(id == -1)
			return null;
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
	
	public void destroy() {
		//TODO Add in a destroy 
		return;
	}
	
	public Tile addProperty(TileProperty... tp) {
		for(TileProperty t : tp) {
			this.properties.add(t);
		}
		return this;
	}
	
	public ArrayList<TileProperty> getTileProperties() {
		return properties;
	}
	
	public boolean is(TileProperty tp) {
		for(TileProperty p : properties) {
			if(p == tp)
				return true;
		}
		return false;
	}
}


/*
 * World
 * 		Chunks
 * 			Grid of tiles (16 * 16)
 * 				
 * */
 