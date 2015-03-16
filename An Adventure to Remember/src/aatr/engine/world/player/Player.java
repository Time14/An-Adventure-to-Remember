package aatr.engine.world.player;

import org.lwjgl.input.Keyboard;

import aatr.engine.gamestate.GameState;
import aatr.engine.world.entity.EntityQuad;
import aatr.engine.world.World;

public class Player extends EntityQuad {
	
	private int x = 0, y = 0;
	
	private float speed = 500;
	
	public static final float SIDE = 64;
	
	private Direction dir;
	
	private boolean isWalking;
	
	private GameState gs;
	
	public Player(GameState gs) {
		super(0, 0, SIDE, SIDE);
		this.gs = gs;
		dir = Direction.DOWN;
		isWalking = false;
	}
	
	public void checkKeyboard(int key, boolean pressed) {
		if (pressed && !isWalking) {
			switch (key) {
			case Keyboard.KEY_A:
				faceDir(Direction.LEFT);
				break;
			case Keyboard.KEY_D:
				faceDir(Direction.RIGHT);
				break;
			case Keyboard.KEY_W:
				faceDir(Direction.UP);
				break;
			case Keyboard.KEY_S:
				faceDir(Direction.DOWN);
				break;
			
			}
		}
	}
	
	public void update(double tick) {
		if (isWalking) {
			switch (dir) {
			case RIGHT:
				super.translate((float)(speed * tick), 0);
				if((x + 1) * World.GRID_SIZE < super.getTransform().position.x) {
					super.getTransform().position.x = (x + 1) * World.GRID_SIZE;
					x++;
					isWalking = false;
				}
				break;
			case LEFT:
				super.translate((float)(-speed * tick), 0);
				if((x - 1) * World.GRID_SIZE > super.getTransform().position.x) {
					super.getTransform().position.x = (x - 1) * World.GRID_SIZE;
					x--;
					isWalking = false;
				}
				break;
			case DOWN:
				super.translate(0,(float)(speed * tick));
				if((y + 1) * World.GRID_SIZE < super.getTransform().position.y) {
					super.getTransform().position.y = (y + 1) * World.GRID_SIZE;
					y++;
					isWalking = false;
				}
				break;
			case UP:
				super.translate(0,(float)(-speed * tick));
				if((y - 1) * World.GRID_SIZE > super.getTransform().position.y) {
					super.getTransform().position.y = (y - 1) * World.GRID_SIZE;
					y--;
					isWalking = false;
				}
				break;
			}
			
		} else {
			
		boolean w = Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean a = Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean s = Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean d = Keyboard.isKeyDown(Keyboard.KEY_D);
		
		switch (dir) {
		case UP:
			if(w)
				walk(Direction.UP);
			break;
		case DOWN:
			if(s)
				walk(Direction.DOWN);	
			break;
		case RIGHT:
			if(d)
				walk(Direction.RIGHT);
			break;
		case LEFT:
			if(a)
				walk(Direction.LEFT);
			break;
		}
		if (!isWalking) {
			if(w)
				walk(Direction.UP);
			else if(a)
				walk(Direction.LEFT);
			else if(s)
				walk(Direction.DOWN);
			else if(d)
				walk(Direction.RIGHT);
			}
		}
	}
	
	public void faceDir(Direction dir) {
		this.dir = dir;
	}
	
	public void walk(Direction dir) {
		if(isWalking)  
			return;
		
		if( this.dir == dir)
			this.isWalking = true;
		
		this.dir = dir;
	}
}