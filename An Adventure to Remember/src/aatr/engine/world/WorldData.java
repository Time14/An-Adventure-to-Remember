package aatr.engine.world;

public class WorldData {
	
	public final short WIDTH, HEIGHT;
	public final Chunk[][] CHUNKS;
	
	public WorldData(short width, short height, Chunk[][] chunks) {
		WIDTH = width;
		HEIGHT = height;
		CHUNKS = chunks;
	}	
}