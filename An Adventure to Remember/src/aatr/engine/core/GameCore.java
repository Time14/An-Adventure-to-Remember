package aatr.engine.core;

import aatr.engine.audio.AudioLibrary;
import aatr.engine.audio.AudioManager;
import aatr.engine.gamestate.GameStateManager;
import aatr.engine.gfx.Window;
import aatr.engine.gfx.shader.OrthographicShaderProgram;
import aatr.engine.gfx.texture.TextureLibrary;
import aatr.engine.util.Time;

public abstract class GameCore {
	
	private GameStateManager gsm;
	private TextureLibrary tl;
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
		
		tl = new TextureLibrary();
		registerTextures(tl);
		
		AudioManager.start(this);
		
		gsm = new GameStateManager(this);
		
		while(Window.isRunning())
			loop();
		
		
		destroy();
	}
	
	private void loop() {
		Time.update();
		gsm.update(Time.getDelta());
		Window.setTitle(default_title + " - FPS: " + Time.getFPS());
	}
	
	public abstract void registerTextures(TextureLibrary texLib);
	public abstract void registerAudio();
	public abstract void registerGameStates(GameStateManager gsm);
	
	private void destroy() {
		//Destroying objects
		gsm.destroy();
		
		//Destroying shaders
		OrthographicShaderProgram.INSTANCE.destroy();
		
		try {
			AudioManager.destroy();
			AudioManager.getThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TextureLibrary.destroy();
		Window.destroy();
	}
}