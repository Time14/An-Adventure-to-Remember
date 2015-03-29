package aatr.game;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import aatr.engine.core.GameCore;
import aatr.engine.gamestate.GameStateManager;
import aatr.engine.gfx.Window;
import aatr.engine.gfx.texture.RenderableTexture;
import aatr.engine.gfx.texture.Texture;
import aatr.engine.gfx.texture.TextureLibrary;

public class Game extends GameCore {
	
	public static final String TITLE = "The Circle of Time";
	
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	
	public Game() {
		Window.enableVSync(false);
//		Window.setFPSCap(0);
		start(TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public void registerAudio() {
		
	}
	
	public void registerTextures(TextureLibrary texLib) {
		TextureLibrary.registerTexture("Place Holder", new Texture("res/Art/PlaceHolderArt.png"));
		TextureLibrary.registerTexture("Renderable Texture",
				new RenderableTexture(128, 128, GL11.GL_RGBA, new int[]{GL30.GL_COLOR_ATTACHMENT0}, false));
	}
	
	public void registerGameStates(GameStateManager gsm) {
		
		gsm.registerState(new GameStateTest(gsm));
		gsm.registerState(new GameStateTest2(gsm));
		
	}
}