package aatr.engine.world.tile;

import org.lwjgl.util.vector.Vector2f;

import aatr.engine.gfx.texture.Texture;
import aatr.engine.gfx.texture.TextureLibrary;

public class TileSet {
	
	private Texture texture;
	
	private int tilesX;
	private int tilesY;
	private int tilesTotal;
	
	private int tileSize;
	
	private int width;
	private int height;
	
	private Vector2f[][] texCoords;
	
	
	public TileSet(String textureName, int tiles) {
		this(textureName, tiles, tiles);
	}
	
	public TileSet(String textureName, int tilesX, int tilesY) {
		texture = TextureLibrary.getTexture(textureName);
		
		width = texture.getWidth();
		height = texture.getHeight();
		
		this.tilesY = tilesY;
		this.tilesX = tilesX;
		
		tileSize = width / tilesX;
		if (tileSize != height / tilesY)
			throw new IllegalStateException("Tiles are not quadratic, (Kill the rectangles!)");
		
		tilesTotal = tilesY + tilesX;
		
		texCoords = new Vector2f[tilesTotal][4];
		
		for(int i = 0; i < tilesTotal; i++) {
			
			int x = i % tilesX;
			int y = i / tilesX;
			
			texCoords[i][0] = new Vector2f(x * tileSize, y * tileSize);
			texCoords[i][1] = new Vector2f((x + 1) * tileSize, y * tileSize);
			texCoords[i][2] = new Vector2f((x + 1) * tileSize, (y + 1) * tileSize);
			texCoords[i][3] = new Vector2f(x * tileSize, (y + 1) * tileSize);
		}
	}
	
	public Vector2f[] getTexCoords(int id) {
		return texCoords[id];
	}
	
}