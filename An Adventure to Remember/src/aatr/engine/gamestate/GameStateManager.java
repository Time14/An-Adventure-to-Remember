package aatr.engine.gamestate;

import java.util.HashMap;
import java.util.HashSet;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import aatr.engine.core.GameCore;
import aatr.engine.debug.Debug;
import aatr.engine.gfx.Window;

public class GameStateManager {
	
	private GameCore core;
	
	private HashMap<Integer, GameState> states = new HashMap<>();
	
	private GameState activeState;
	
	public GameStateManager(GameCore core) {
		this.core = core;
		
		states = new HashMap<>();
		
		core.registerGameStates(this);
		
		enterState(0);
	}
	
	public GameStateManager update(double tick) {
		
		checkMouse();
		checkKeyboard();
		activeState.update(tick);
		
		Window.clear();
		activeState.draw();
		Window.swapBuffers();
		
		return this;
	}
	
	private void checkMouse() {
		while(Mouse.next()) {
			activeState.checkMouse(Mouse.getEventButton(), Mouse.getEventButtonState());
		}
	}
	
	private void checkKeyboard() {
		while(Keyboard.next()) {
			activeState.checkKeyboard(Keyboard.getEventKey(), Keyboard.getEventKeyState());
		}
	}
	
	public GameStateManager enterState(int id) {
		
		if(activeState != null)
			activeState.exit();
		
		activeState = states.get(id);
		
		if(activeState == null)
			throw new IllegalStateException("Threre is no registered state with ID: " + id);
		
		if(activeState == null)
			throw new IllegalStateException("Unregistered ID: " + id);
		
		activeState.init();
		
		return this;
	}
	
	public GameStateManager registerState(GameState... states) {
		for(GameState gs : states) {
			this.states.put(gs.ID, gs);
		}
		return this;
	}
	
	public void destroy() {
		activeState.exit();
	}
}