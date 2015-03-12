package aatr.game;

import org.lwjgl.input.Keyboard;

import aatr.engine.gamestate.GameState;
import aatr.engine.gamestate.GameStateManager;
import aatr.engine.gfx.Window;
import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.gfx.texture.TextureLibrary;
import aatr.engine.world.Entity;
import aatr.engine.world.EntityManager;
import aatr.engine.world.EntityQuad;
import static org.lwjgl.opengl.GL11.*;

public class GameStateTest extends GameState {
	
	public static final int STATE_ID = 0;
	
	public GameStateTest(GameStateManager gsm) {
		super(gsm, STATE_ID);
	}
	
	private EntityManager em;
	
	public void init() {
		em = new EntityManager(this);
		
		em.addEntity("Test", new EntityQuad(this, 0, 0, 400, 300, TextureLibrary.getTexture("Place Holder")));
		
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
	
	public void checkKeyboard(int key, boolean pressed) {}
	
	public void update(double tick) {}
	
	public void draw() {em.draw();}
	
	public void exit() {em.destroy();}
}