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

public final class WorldLoader {

	/*
	 * w = width, h = height t = tile id s = tile set id Mock .map file : 1 =
	 * tile, \n = new row w h stststst\n stststst\n stststst\n stststst\n Or : ~
	 * = 2 tiles, ! = new row, skrivs i chunk form, s� allt fr�n en chunk kommer
	 * f�rst, sedan n�sta chunk, ect. wh~~!~~!~~!~~!
	 */

	public static final WorldData loadMapFromFile(String path) {
		try {

			File mapFile = new File(path);

			ByteBuffer mapData = loadFile(mapFile);

			// Loading width and height
			short width = mapData.getShort();
			short height = mapData.getShort();

			// Loading tileset
			short ts = mapData.getShort();

			// Loading border chunk
			short id1 = mapData.getShort();
			short id2 = mapData.getShort();
			short id3 = mapData.getShort();
			short id4 = mapData.getShort();

			Tile[][] borderChunkTiles = new Tile[Chunk.GRID_DIMENSIONS][Chunk.GRID_DIMENSIONS];
			for (int x = 0; x < Chunk.GRID_DIMENSIONS; x += 2) {
				for (int y = 0; y < Chunk.GRID_DIMENSIONS; y += 2) {
					borderChunkTiles[x][y] = new Tile(ts, id1);
					borderChunkTiles[x + 1][y] = new Tile(ts, id2);
					borderChunkTiles[x + 1][y + 1] = new Tile(ts, id4);
					borderChunkTiles[x][y + 1] = new Tile(ts, id3);
				}
			}

			Tile[] border = new Tile[] { new Tile(ts, id1), new Tile(ts, id2),
					new Tile(ts, id3), new Tile(ts, id4) };

			// Loading chunks
			Chunk[][] map = new Chunk[width][height];

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					
					Tile[][] chunkData = new Tile[Chunk.GRID_DIMENSIONS][Chunk.GRID_DIMENSIONS];
					
					for(int i = 0; i < chunkData.length; i++) {
						for(int j = 0; j < chunkData[i].length; j++) {
							chunkData[i][j] = new Tile(ts, mapData.getShort());
						}
					}
					
					map[x][y] = new Chunk(chunkData);
				}
			}

			return new WorldData(width, height, map, border, new Chunk(
					borderChunkTiles));

		} catch (IOException e) {
			System.err.println("Error 404 file not found \"" + path + "\"");
			e.printStackTrace();
			return null;
		}
	}

	private static final ByteBuffer loadFile(File mapFile) throws IOException {

		RandomAccessFile raf = new RandomAccessFile(mapFile, "r");
		
		ByteBuffer buffer = Util.createByteBuffer((int) raf.length()).order(
				ByteOrder.LITTLE_ENDIAN);

		FileChannel fc = raf.getChannel();
		fc.read(buffer);
		raf.close();
		buffer.flip();

		return buffer;
	}

}
