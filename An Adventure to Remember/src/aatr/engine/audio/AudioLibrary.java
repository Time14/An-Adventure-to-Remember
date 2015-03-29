package aatr.engine.audio;

import java.util.HashMap;

public final class AudioLibrary {
	
//	public static final AudioClip DEFAULT_TEXTURE = new AudioClip();
	
	private static final HashMap<String, Audio> audioClips = new HashMap<>();
	
	
	public static final void registerAudio(String name, Audio audio) {
		audioClips.put(name, audio);
	}
	
	public static final Audio getAudio(String name) {
		Audio audio = audioClips.get(name);
		
		if(audio == null) {
			AudioManager.destroy(1);
			throw new IllegalArgumentException("Unregistered sound \"" + name + "\"");
		}
			
		return audio;
	}
	
	public static final void destroy() {
		for(Audio audio : audioClips.values())
			audio.destroy();
	}
}