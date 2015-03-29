package aatr.engine.audio;

import static org.lwjgl.openal.AL10.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import org.lwjgl.openal.AL10;

import aatr.engine.util.Util;

public class Audio {
	
	public static final int WAV_HEADER_SIZE = 44;
	public static final int WAV_SAMPLE_RATE_OFFSET = 24;
	public static final int WAV_SAMPLE_RATE_SIZE = 4;
	public static final int WAV_NUM_CHANNELS_OFFSET = 22;
	public static final int WAV_NUM_CHANNELS_SIZE = 2;
	public static final int WAV_BITS_PER_SAMPLE_OFFSET = 34;
	public static final int WAV_BITS_PER_SAMPLE_SIZE = 2;
	
	private int id, frequency;
	private short channels, bitrate;
	
	public Audio(String path) {
		createAudio(path);
	}
	
	private void createAudio(String path) {
		id = AL10.alGenBuffers();
		
		try (RandomAccessFile raf = new RandomAccessFile(new File(path), "r")) {
			
			ByteBuffer buffer = Util.createByteBuffer((int)raf.length());
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			
			FileChannel fc = raf.getChannel();
			
			fc.read(buffer);
			
			buffer.flip();
			
			frequency = buffer.getInt(WAV_SAMPLE_RATE_OFFSET);
			
			channels = buffer.getShort(WAV_NUM_CHANNELS_OFFSET);
			
			bitrate = buffer.getShort(WAV_BITS_PER_SAMPLE_OFFSET);
			
			buffer.position(WAV_HEADER_SIZE);
			buffer.slice();
			
			if(bitrate == 8)
				AL10.alBufferData(id, channels == 1 ? AL10.AL_FORMAT_MONO8 : AL10.AL_FORMAT_STEREO8, buffer, frequency);
			else if(bitrate == 16)
				AL10.alBufferData(id, channels == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16, buffer, frequency);
			else
				throw new IllegalStateException("Bitrate must be either 8 or 16 bit (Illegal value: " + bitrate + ")");
			
//			System.out.println(frequency + "\t" + channels + "\t" + bitrate);
			
			if(alGetError() != AL_NO_ERROR) {
				System.err.println("An error occured when initializing audio data!");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		
	}
	
	public int getID() {
		return id;
	}
	
	public void destroy() {
		AL10.alDeleteBuffers(id);
	}
}