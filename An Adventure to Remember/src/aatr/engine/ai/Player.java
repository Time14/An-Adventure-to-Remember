package aatr.engine.ai;

import org.lwjgl.input.Keyboard;

import sk.stb.STB;
import aatr.engine.ai.PawnController;
import aatr.engine.world.entity.Entity;

public class Player extends PawnController {

	public static final String STB_PLAYER_TIMER = "STB_PLAYER_TIMER";
	
	private static Direction direction;
	
	
	private static boolean isWalking;
	
	@Override
	Direction chooseDirection(Entity pawn) {
		
		boolean w = Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean a = Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean s = Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean d = Keyboard.isKeyDown(Keyboard.KEY_D);
		
		isWalking = pawn.getIsWalking();
		if(!isWalking && STB.done(STB_PLAYER_TIMER))
			if(w) {
				if(direction == Direction.UP) 
					isWalking = true;
					return direction;
			} else if(a) {
				if(direction == Direction.LEFT) 
					isWalking = true;
					return direction;
			} else if(s) {
				if(direction == Direction.RIGHT) 
					isWalking = true;
					return direction;
			} else if(d) {
				if(direction == Direction.DOWN) 
					isWalking = true;
					return direction;
			}
		return null;
	}
	
	public static void update(double tick) {
		STB.update(tick, STB_PLAYER_TIMER);
	}
	
	public static void checkKeyboard(int key, boolean pressed) {
		if (pressed && !isWalking) {
			switch (key) {
			case Keyboard.KEY_A:
				if(direction != Direction.LEFT) {
					direction = Direction.LEFT;
					STB.reset(STB_PLAYER_TIMER);
				}
				break;
			case Keyboard.KEY_D:
				if(direction != Direction.RIGHT) {
					direction = Direction.RIGHT;
					STB.reset(STB_PLAYER_TIMER);
				}
				break;
			case Keyboard.KEY_W:
				if(direction != Direction.UP) {
					direction = Direction.UP;
					STB.reset(STB_PLAYER_TIMER);
				}
				break;
			case Keyboard.KEY_S:
				if(direction != Direction.DOWN) {
					direction = Direction.DOWN;
					STB.reset(STB_PLAYER_TIMER);
				}
				break;
			}
		}
	}

	static {
		direction = Direction.UP;
		STB.start(STB_PLAYER_TIMER, .1);
		isWalking = false;
	}
}