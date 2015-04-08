package aatr.engine.gfx.gui;

import javax.swing.text.html.parser.Entity;
import aatr.engine.gfx.renderer.QuadRenderer;
import aatr.engine.gfx.texture.Texture;

public abstract class GUIButton extends GUIElement{
	
	private Texture tex;
	private Texture hovTex;
	
	private QuadRenderer render;
	
	GUIButton(float x, float y, float width, float height,Texture texture) {
		super(x, y, width, height);
		render = new QuadRenderer(x, y, width, height);
	}
	
	public void setTexture(Texture texture ) {
		tex = texture;
	}
	
	public void setHoverTexture(Texture texture) {
		hovTex = texture;
	}
	
	public void draw() {
		
	}
}
