package aatr.game;

import aatr.engine.gamestate.GameStateManager;
import aatr.engine.gfx.Window;

public class Game extends GameCore {
	
	public static final String TITLE = "The Circle of Time";
	
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	
	public Game() {
		start(TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public GameCore registerGameStates(GameStateManager gsm) {
		
		gsm.registerState(new GameStateTest(gsm));
		gsm.registerState(new GameStateTest2(gsm));
		
		return this;
	}
}