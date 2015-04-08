package aatr.engine.gfx.gui;

import org.lwjgl.util.vector.Vector2f;

import aatr.engine.gfx.texture.Texture;

public abstract class GUIElement {
	private float x;
	private float y;
	private float width;
	private float height;	
	
	public GUIElement(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;	
	}
	
	abstract void onClick();
	
	abstract void onHover();
	
	abstract void onUnHover();
	
	abstract void draw();
	
	abstract void update();
	
	public final boolean checkPoint(Vector2f mPos) {
		return checkPoint(mPos.getX(), mPos.getY());
	}
	
	public final boolean checkPoint(float px, float py) {
		return checkPoint(this,  px, py);
	}
	
	public final boolean checkPoint(GUIElement gui, Vector2f mPos) {
		return checkPoint(gui, mPos.getX(), mPos.getY());
	}
	
	public final boolean checkPoint(GUIElement gui, float px, float py) {
		return checkPoint(gui.getX(), gui.getY(), gui.getX() + gui.getWidth(), gui.getY() + gui.getHeight(), px, py);
	}

	public final boolean checkPoint(float minx, float miny, float maxx, float maxy, float px, float py) {
		if(minx > px || miny > py)
			return false;
		if(maxx < px || maxy < py)
			return false;
		return true;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}	
}

