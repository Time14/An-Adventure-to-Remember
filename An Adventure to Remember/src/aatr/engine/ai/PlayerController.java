package aatr.engine.ai;

import org.lwjgl.input.Keyboard;

import sk.stb.STB;
import aatr.engine.ai.PawnController;
import aatr.engine.debug.Debug;
import aatr.engine.world.World;
import aatr.engine.world.entity.Entity;
import aatr.engine.world.tile.TileProperty;

public class PlayerController extends PawnController {

	public static final String STB_PLAYER_TIMER = "STB_PLAYER_TIMER";
	
	private Direction direction = Direction.LEFT;
	
	
	private static boolean isWalking;
	
	@Override
	public Direction control(Entity pawn) {
		
		boolean w = Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean a = Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean s = Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean d = Keyboard.isKeyDown(Keyboard.KEY_D);
		
		isWalking = pawn.getIsWalking();
		
		pawn.setFaceDirection(direction);
		
		if(!isWalking && STB.done(STB_PLAYER_TIMER)) {
			if(w) {
				if(direction == Direction.UP && pawn.getEntityManager().isFree(pawn.getX(), pawn.getY() - 1, pawn.getLayer()) && 
						!pawn.getGameState().getWorld(pawn.getLayer()).getTile(pawn.getX(), pawn.getY() - 1).is(TileProperty.SOLID)) {
					isWalking = true;
				}
			} else if(a) {
				if(direction == Direction.LEFT && pawn.getEntityManager().isFree(pawn.getX() - 1, pawn.getY(), pawn.getLayer()) && 
						!pawn.getGameState().getWorld(pawn.getLayer()).getTile(pawn.getX() - 1, pawn.getY()).is(TileProperty.SOLID)) {
					isWalking = true;
				}
			} else if(d) {
				if(direction == Direction.RIGHT && pawn.getEntityManager().isFree(pawn.getX() + 1, pawn.getY(), pawn.getLayer()) && 
						!pawn.getGameState().getWorld(pawn.getLayer()).getTile(pawn.getX() + 1, pawn.getY()).is(TileProperty.SOLID)) { 
					isWalking = true;
				}
			} else if(s) {
				if(direction == Direction.DOWN && pawn.getEntityManager().isFree(pawn.getX(), pawn.getY() + 1, pawn.getLayer()) && 
						!pawn.getGameState().getWorld(pawn.getLayer()).getTile(pawn.getX(), pawn.getY() + 1).is(TileProperty.SOLID)) { 
					isWalking = true;
				}
			}
		}
		if(isWalking) {
			pawn.setDirection(direction);
			pawn.setIsWalking(isWalking);
		}
		return null;
	}
	
	public void update(double tick) {
		STB.update(tick, STB_PLAYER_TIMER);
	}
	
	public void checkKeyboard(int key, boolean pressed) {
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
		STB.start(STB_PLAYER_TIMER, .1);
		isWalking = false;
	}
}