package aatr.engine.gamestate;

public abstract class GameState {
	
	protected GameStateManager gsm;
	public final int ID;
	
	public GameState(GameStateManager gsm, int id) {
		ID = id;
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void checkMouse(int button, boolean pressed);
	public abstract void checkKeyboard(int key, boolean pressed);
	public abstract void update(double tick);
	public abstract void draw();
	public abstract void exit();
	
}