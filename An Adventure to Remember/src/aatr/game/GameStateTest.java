package aatr.game;

import org.lwjgl.input.Keyboard;

import aatr.engine.gamestate.GameState;
import aatr.engine.gamestate.GameStateManager;
import aatr.engine.gamestate.GameStateWorld;
import aatr.engine.gfx.Window;
import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.gfx.texture.RenderableTexture;
import aatr.engine.gfx.texture.TextureLibrary;
import aatr.engine.world.World;
import aatr.engine.world.entity.Entity;
import aatr.engine.world.entity.EntityManager;
import aatr.engine.world.entity.EntityQuad;
import aatr.engine.world.player.Player;
import static org.lwjgl.opengl.GL11.*;

public class GameStateTest extends GameStateWorld {
	
	public static final int STATE_ID = 0;
	
	public GameStateTest(GameStateManager gsm) {
		super(gsm, STATE_ID);
	}
	
	public void checkMouse(int button, boolean pressed) {
		if(pressed) {
			switch(button) {
			case 0: gsm.enterState(GameStateTest2.STATE_ID);
			}
		}
	}
	
	public void checkKeyboard(int key, boolean pressed) {player.checkKeyboard(key, pressed);}
	
	
	public World getWorld()
	{
		return world;
	}
}