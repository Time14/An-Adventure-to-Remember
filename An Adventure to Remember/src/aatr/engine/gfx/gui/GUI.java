package aatr.engine.gfx.gui;

import java.util.ArrayList;

public class GUI {
	ArrayList<GUIElement> elements;
	
	boolean isVisable;
	
	public GUI() {}
	
	public void click(float x, float y) {
		for(GUIElement el : elements) {
			if(el.checkPoint(x, y)) {
				el.onClick();
			}
		}
	}
	
	public void update() {
		for(GUIElement el : elements) {
			el.update();
		}
	}
	
	public void draw() {
		for(GUIElement el : elements) {
			el.draw();
		}
	}
}
