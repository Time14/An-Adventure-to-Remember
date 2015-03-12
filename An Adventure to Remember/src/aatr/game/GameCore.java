package aatr.game;

import aatr.engine.gamestate.GameStateManager;
import aatr.engine.gfx.Window;
import aatr.engine.util.Time;

public abstract class GameCore {
	
	private GameStateManager gsm;
	
	private String default_title;
	private float default_width;
	private float default_height;
	
	public GameCore() {}
	
	public void start(String title, int width, int height) {
		
		default_title = title;
		default_width = width;
		default_height = height;
		
		try {
			Window.create(title, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		gsm = new GameStateManager(this);
		
		while(Window.isRunning())
			loop();
		
		Window.destroy();
		
	}
	
	private void loop() {
		Time.update();
		gsm.update(Time.getDelta());
		Window.setTitle(default_title + " - FPS: " + Time.getFPS());
	}
	
	public abstract GameCore registerGameStates(GameStateManager gsm);
	
}