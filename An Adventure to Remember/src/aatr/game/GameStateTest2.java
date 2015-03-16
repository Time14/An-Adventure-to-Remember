package aatr.game;

import org.lwjgl.input.Keyboard;

import aatr.engine.gamestate.GameState;
import aatr.engine.gamestate.GameStateManager;

public class GameStateTest2 extends GameState {
	
	public static final int STATE_ID = 1;
	
	public GameStateTest2(GameStateManager gsm) {
		super(gsm, STATE_ID);
	}
	
	public void init() {}
	public void checkMouse(int button, boolean pressed) {}
	public void checkKeyboard(int key, boolean pressed) {
		if(!pressed) {
			switch(key) {
			case Keyboard.KEY_RETURN: gsm.enterState(GameStateTest.STATE_ID);
			}
		}
	}
	public void update(double tick) {}
	public void draw() {}
	public void exit() {}
	
}