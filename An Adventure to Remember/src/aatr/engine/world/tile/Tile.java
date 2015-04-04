package aatr.engine.world.tile;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import aatr.engine.debug.Debug;
import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.gfx.renderer.QuadRenderer;
import aatr.engine.world.World;

public class Tile {
	
	public static final int HEIGHT = World.GRID_SIZE;
	
	private TileSet tileSet;
	private int id;
	//Maybe not?
	private int x;
	private int y;
	
	private ArrayList<TileProperty> properties;
	
	public Tile(int id) {
		this(id, -1, -1);
	}
	
	public Tile(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.properties = new ArrayList<TileProperty>();
	}
	
	
	public Vertex[] getVertices() {
		return getVertices(tileSet, x, y);
	}
	
	public Vertex[] getVertices(TileSet ts, int x, int y) {
		if(tileSet != ts)
			tileSet = ts;
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
	
	public Tile setTileSet(TileSet tileSet) {
		this.tileSet = tileSet;
		return this;
	}
	
	public void destroy() {
		//TODO Add in a destroy 
		return;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Tile addProperty(TileProperty... tp) {
		for(TileProperty t : tp) {
			if(!is(t)) {
				this.properties.add(t);
			}
		}
		return this;
	}
	
	public Tile removeProperty(TileProperty tp) {
		int l = properties.size();
		for(int i = 0; i < 3; i++)
			properties.remove(tp);
		return this;
	}
	
	public ArrayList<TileProperty> getTileProperties() {
		return properties;
	}
	
	public boolean is(TileProperty... tp) {
		for(TileProperty t : tp) {
			if(is(t))
				return true;
		}
		return false;
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
 