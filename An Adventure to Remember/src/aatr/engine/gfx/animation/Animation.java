package aatr.engine.gfx.animation;

import aatr.engine.gfx.texture.Texture;
import aatr.engine.world.entity.Entity;

public abstract class Animation {
	
	protected static String ANIMATION_NAME;
		
	protected int frame;
	protected int[] frames;
	protected float frameLength;
	
	protected float animationSpeed;
	protected boolean isRepeating;
	protected boolean playing;
	protected boolean needsUpdate;
	
	protected Texture texture;
	
	protected Entity pawn;

	public Animation() {
		frameLength = .1f;
		animationSpeed = 1;
		playing = true;
		needsUpdate = true;
		isRepeating = true;
		frame = 0;
	}
	
	public Animation bindPawn(Entity pawn) {
		this.pawn = pawn;
		return this;
	}
	public Entity getPawn() {
		return pawn;
	}
	
	public Animation bindTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
	public Texture getTexture() {
		return texture;
	}
	
	public Animation nextFrame() {
		needsUpdate = true;
		frame++;
		frame %= frames.length;
		return this;
	}
	public Animation previousFrame() {
		needsUpdate = true;
		frame--;
		if(frame < 0) 
			frame = frames.length - 1;
		return this;
	}
	
	public int getFrame() {
		return frame;
	}
	public Animation setFrame(int frame) {
		needsUpdate = true;
		this.frame = frame % frames.length;
		return this;
	}
	
	public float getAnimationSpeed() {
		return animationSpeed;
	}
	public Animation setAnimationSpeed(float animationSpeed) {
		this.animationSpeed = animationSpeed;
		return this;
	}
	
	public boolean isRepeating() {
		return isRepeating;
	}
	public Animation setRepeatable(boolean isRepeatable) {
		this.isRepeating = isRepeatable;
		return this;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public Animation stop() {
		playing = false;
		return this;
	}
	
	public Animation play() {
		playing = true;
		return this;
	}
	
	public Animation setPlaying(boolean isPlaying) {
		this.playing = isPlaying;
		return this;
	}
	
	
	public abstract Animation update(double tick);
	
}
