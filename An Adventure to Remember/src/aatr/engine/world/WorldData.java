package aatr.engine.world;

import aatr.engine.world.tile.Tile;

public class WorldData {
	
	public final short WIDTH, HEIGHT;
	public final Chunk[][] CHUNKS;
	
	public final Tile[] BORDER;
	public final Chunk BORDER_CHUNK;
	
	public WorldData(short width, short height, Chunk[][] chunks, Tile[] border, Chunk borderChunk) {
		WIDTH = width;
		HEIGHT = height;
		CHUNKS = chunks;
		BORDER = border;
		BORDER_CHUNK = borderChunk;
	}	
}