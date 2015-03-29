package aatr.engine.audio;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

import aatr.engine.audio.AudioLibrary;

import aatr.engine.core.GameCore;
import aatr.engine.util.Util;

public final class AudioManager {

	public static final int MAX_SOURCES = 256;
	public static final int MAX_LOOP_SOURCES = 10;
	public static final int MAX_TEMP_SOURCES = MAX_SOURCES - MAX_LOOP_SOURCES;

	private static AudioHandler audioHandler;
	private static Thread thread;

	public static final synchronized void start(GameCore core) {

		audioHandler = new AudioHandler(core);
		thread = new Thread(audioHandler, "Audio Handler");

		thread.start();
	}

	public static final synchronized Thread getThread() {
		return thread;
	}

	public static final synchronized void fadeLoopGain(float target,
			float duration, int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_FADE_GAIN, new float[] { s, target,
							duration }));
	}

	public static final synchronized void fadeLoopPitch(float target,
			float duration, int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_FADE_PITCH, new float[] { s, target,
							duration }));
	}

	public static final synchronized void setLoopGain(float target,
			int... source) {
		fadeLoopGain(target, 0, source);
	}

	public static final synchronized void setLoopPitch(float target,
			int... source) {
		fadeLoopPitch(target, 0, source);
	}

	// Instant Stop
	// public static final synchronized void stop(String... audio) {
	// for(String s : audio)
	// audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s),
	// false, AudioEvent.EVENT_STOP));
	// }

	// Instant Loop Stop
	public static final synchronized void stopLoop(int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_STOP, new float[] { s }));
	}

	// Interpolated Stop
	// public static final synchronized void stop(float duration, String...
	// audio) {
	// for(String s : audio) {
	// audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s),
	// false, AudioEvent.EVENT_STOP_FADE, new float[]{duration}));
	// }
	// }

	// Interpolated Loop Stop
	public static final synchronized void stopLoop(float duration,
			int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_STOP_FADE, new float[] { s, duration }));
	}

	// Instant Pause
	// public static final synchronized void pause(String... audio) {
	// for(String s : audio)
	// audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s),
	// false, AudioEvent.EVENT_PAUSE));
	// }

	// Instant Loop Pause
	public static final synchronized void pauseLoop(int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_PAUSE, new float[] { s }));
	}

	// Interpolated Pause
	// public static final synchronized void pause(float duration, String...
	// audio) {
	// for(String s : audio) {
	// audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s),
	// false, AudioEvent.EVENT_PAUSE_FADE, new float[]{duration}));
	// }
	// }

	// Interpolated Loop Pause
	public static final synchronized void pauseLoop(float duration,
			int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_PAUSE_FADE, new float[] { s, duration }));
	}

	// Interpolated Play
	public static final synchronized void play(float gain, float pitch,
			float duration, String... audio) {
		for (String s : audio) {
			audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s), false,
					AudioEvent.EVENT_PLAY_FADE, new float[] { gain, pitch,
							duration }));
		}
	}

	// Interpolated Loop Play
	public static final synchronized void playLoop(int source, float gain,
			float pitch, float duration, String audio) {
		audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(audio), true,
				AudioEvent.EVENT_PLAY_FADE, new float[] { source, gain, pitch,
						duration }));
	}

	// Instant Play
	public static final synchronized void play(float gain, float pitch,
			String... audio) {
		for (String s : audio) {
			audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s), false,
					AudioEvent.EVENT_PLAY, new float[] { gain, pitch }));
		}
	}

	// Instant Loop Play
	public static final synchronized void playLoop(int source, float gain,
			float pitch, String audio) {
		audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(audio), true,
				AudioEvent.EVENT_PLAY, new float[] { source, gain, pitch }));
	}

	public static final synchronized void destroy() {
		destroy(0);
	}

	public static final synchronized void destroy(int error) {
		audioHandler.setError(error);
		audioHandler.running = false;
	}

	/* Audio Handler Class */

}