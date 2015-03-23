package aatr.game;

import org.lwjgl.input.Keyboard;

import aatr.engine.gamestate.GameState;
import aatr.engine.gamestate.GameStateManager;
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

public class GameStateTest extends GameState {
	
	public static final int STATE_ID = 0;
	
	public GameStateTest(GameStateManager gsm) {
		super(gsm, STATE_ID);
	}
	
	private EntityManager em;
	
	private Player player;
	
	private World world;
	
	public void init() {
		em = new EntityManager(this);
		
		player = new Player(this);
		world = new World();
//		em.addGroup("Boxes");
//		
//		for(int i = 0; i < 10; i++) {
//			for(int j = 0; j < 10; j++) {
//				em.addToGroup("Boxes", new EntityQuad(this, j * 80, i * 60, 80, 60));
//			}
//		}
		
	}
	
	public void checkMouse(int button, boolean pressed) {
		if(pressed) {
			switch(button) {
			case 0: gsm.enterState(GameStateTest2.STATE_ID);
			}
		}
	}
	
	public void checkKeyboard(int key, boolean pressed) {player.checkKeyboard(key, pressed);}
	
	public void update(double tick) {em.update(tick); player.update(tick);}
	
	public void draw() {
		world.draw();
		em.draw();
		player.draw();
	}
	
	public void exit() {em.destroy();}
}