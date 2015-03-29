package aatr.engine.audio;

import org.lwjgl.openal.AL10;

import aatr.engine.util.Util;

public class AudioSource {
	
	private int id;
	
	private float gain = 1f, pitch = 1f;
	
	private float targetPitch = 1f, targetGain = 1f;
	
	private float deltaPitch, deltaGain;
	
	private boolean loop;
	
	//true if to stop on gain zero and false if to pause
	private boolean stop;
	
	
	public AudioSource(boolean loop) {
		id = AL10.alGenSources();
		this.loop = loop;
	}
	
	public void play(Audio audio) {
		
		AL10.alSourcei(id, AL10.AL_BUFFER, audio.getID());
		AL10.alSourcef(id, AL10.AL_PITCH, pitch);
		AL10.alSourcef(id, AL10.AL_GAIN, gain);
		AL10.alSourcef(id, AL10.AL_MAX_GAIN, 1);
		AL10.alSourcef(id, AL10.AL_MIN_GAIN, 0);
		AL10.alSource(id, AL10.AL_POSITION, Util.toFloatBuffer(new float[]{0, 0, 0}));
		AL10.alSource(id, AL10.AL_VELOCITY, Util.toFloatBuffer(new float[]{0, 0, 0}));
		AL10.alSourcei(id, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
		
		AL10.alSourcePlay(id);
	}
	
	public void update(double tick) {
		if(id == 1)System.out.println(AL10.alGetSourcef(id, AL10.AL_GAIN));
		adjustGain(tick);
		adjustPitch(tick);
	}
	
	private void adjustGain(double tick) {
		if(gain < targetGain) {
			addGain((float)tick * deltaGain);
			if(gain >= targetGain)
				setGain(targetGain);
		} else if(gain > targetGain) {
//			System.out.println((float)tick * deltaGain);
			addGain((float)tick * deltaGain);
			if(gain <= targetGain) {
				setGain(targetGain);
				if(gain == 0) {
					if(stop)
						stop();
					else
						pause();
				}
			}
		}
	}
	
	private void adjustPitch(double tick) {
		if(pitch < targetPitch) {
			addPitch((float)tick * deltaPitch);
			if(pitch >= targetPitch)
				setPitch(targetPitch);
		} else if(pitch > targetPitch) {
			addPitch((float)tick * deltaPitch);
			if(pitch <= targetPitch)
				setPitch(targetPitch);
		}
	}
	
	public void pause() {
		AL10.alSourcePause(id);
	}
	
	public void stop() {
		AL10.alSourceStop(id);
	}
	
	public void fadePitch(float target, float time) {
		targetPitch = target;
		deltaPitch = target - pitch;
		
		if(time == 0) {
			setPitch(target);
			return;
		}
		
		deltaPitch /= time;
	}
	
	public void fadeGain(float target, float time) {
		targetGain = target;
		deltaGain = target - gain;
		
		if(time == 0) {
			setGain(target);
			return;
		}
		
		deltaGain /= time;
	}
	
	public void addGain(float gain) {
		this.gain += gain;
		AL10.alSourcef(id, AL10.AL_GAIN, this.gain);
	}
	
	public void addPitch(float pitch) {
		this.pitch += pitch;
		AL10.alSourcef(id, AL10.AL_PITCH, this.pitch);
	}
	
	public void setGain(float gain) {
		this.gain = gain;
		targetGain = gain;
		AL10.alSourcef(id, AL10.AL_GAIN, gain);
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
		targetPitch = pitch;
		AL10.alSourcef(id, AL10.AL_PITCH, pitch);
	}
	
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public boolean isPlaying() {
		return AL10.alGetSourcei(id, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	public void setZeroStop(boolean stop) {
		this.stop = stop;
	}
	
	public void destroy() {
		AL10.alDeleteSources(id);
	}
}