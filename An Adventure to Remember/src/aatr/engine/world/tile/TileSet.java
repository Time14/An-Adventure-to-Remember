package aatr.engine.world.tile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import aatr.engine.gfx.texture.Texture;
import aatr.engine.gfx.texture.TextureLibrary;
import aatr.engine.util.Util;

public class TileSet {
	
	public static final ArrayList<TileSet> tileSets;
	
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
	
	
	//Loading Tilesets
	static {
		tileSets = new ArrayList<>();
		
		File file = new File("res/map/tilesets/");
		
		File[] files = file.listFiles();
		
		file = new File("res/map/headers/");
		
		File[] headers = file.listFiles();
		
		if(files.length > headers.length)
			throw new IllegalStateException("Insufficient amount of tileset headers");
		else if(headers.length > files.length)
			throw new IllegalStateException("Insufficient amount of tileset textures");
		
		for(int i = 0; i < files.length; i++) {
			try {
				//2 shorts for each header
				ByteBuffer buffer = Util.createByteBuffer(2 * Short.BYTES);
				
				buffer.order(ByteOrder.LITTLE_ENDIAN);
				
				RandomAccessFile raf = new RandomAccessFile(headers[i], "r");
				
				FileChannel fc = raf.getChannel();
				
				fc.read(buffer);
				
				buffer.flip();
				
				short width = buffer.getShort();
				short height = buffer.getShort();
				
				fc.close();
				
				Texture texture = new Texture(files[i].getPath());
				
				TextureLibrary.registerTexture("TileSet_" + i, texture);
				tileSets.add(new TileSet("TileSet_" + i, width, height));
				
				
			} catch (IOException e) {
//				System.err.println("\"" + files[i].getPath() + "\"");
				e.printStackTrace();
			}
		}
	}
}