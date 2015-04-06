package aatr.engine.gfx.animation;

import aatr.engine.gfx.texture.Texture;
import aatr.engine.world.entity.Entity;

public abstract class Animation {
	
	protected static String ANIMATION_NAME;
		
	protected int frame;
	protected int[] frames;
	protected double frameLength;
	
	protected float animationSpeed;
	protected boolean isRepeatable;
	
	protected Texture texture;
	
	protected Entity pawn;

	abstract void bindPawn(Entity pawn);
	public Entity getPawn() {
		return pawn;
	}
	
	abstract void bindTexture(Texture texture);
	public Texture getTexture() {
		return texture;
	}
	
	public void nextFrame() {
		frame++;
		frame %= frames.length;
	}
	public void previousFrame() {
		frame--;
		if(frame < 0) 
			frame = frames.length - 1;
	}
	
	public int getFrame() {
		return frame;
	}
	public void setFrame(int frame) {
		this.frame = frame % frames.length;
	}
	
	public float getAnimationSpeed() {
		return animationSpeed;
	}
	public void setAnimationSpeed(float animationSpeed) {
		this.animationSpeed = animationSpeed;
	}
	
	public boolean isRepeatable() {
		return isRepeatable;
	}
	public void setRepeatable(boolean isRepeatable) {
		this.isRepeatable = isRepeatable;
	}
	
	abstract void update(double tick);
	
	abstract void draw();
	
}
