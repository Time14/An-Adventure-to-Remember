package aatr.engine.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import aatr.engine.util.Util;
import aatr.engine.world.tile.Tile;

public class WorldLoader {

	WorldLoader() {
	}

	/*
	 * w = width, h = height t = tile id s = tile set id Mock .map file : 1 =
	 * tile, \n = new row w h stststst\n stststst\n stststst\n stststst\n Or : ~
	 * = 2 tiles, ! = new row, skrivs i chunk form, så allt från en chunk kommer
	 * först, sedan nästa chunk, ect. wh~~!~~!~~!~~!
	 */

	public WorldData loadMapFromFile(String path) {
		try {
			
			File mapFile = new File(path);
			
			ByteBuffer mapData = loadFile(mapFile);
			
			short width = mapData.getShort();
			short height = mapData.getShort();
			
			Chunk[][] map = new Chunk[width][height];

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {

					short cx = (short) ((x - x % Chunk.GRID_DIMENSIONS) / Chunk.GRID_DIMENSIONS);
					short cy = (short) ((y - y % Chunk.GRID_DIMENSIONS) / Chunk.GRID_DIMENSIONS);
					Tile[] tiles = new Tile[Chunk.GRID_DIMENSIONS
							* Chunk.GRID_DIMENSIONS];
					
					for (int i = 0; i < (Chunk.GRID_DIMENSIONS * Chunk.GRID_DIMENSIONS); i++)
						tiles[i] = new Tile(mapData.getShort(),
								mapData.getShort(), x, y);
					map[cx][cy] = new Chunk(tiles);
				}
			}
			
			return new WorldData(width, height, map);

		} catch (IOException e) {
			System.err.println("Error 404 file not found \"" + path + "\"");
			e.printStackTrace();
			return null;
		}
	}

	private ByteBuffer loadFile(File mapFile)
			throws IOException {
		
		
		RandomAccessFile raf = new RandomAccessFile(mapFile, "r");
		
		ByteBuffer buffer = Util.createByteBuffer((int) raf.length()).order(ByteOrder.LITTLE_ENDIAN);;
		
		FileChannel fc = raf.getChannel();
		fc.read(buffer);
		raf.close();
		buffer.flip();
		
		return buffer;
	}

}
