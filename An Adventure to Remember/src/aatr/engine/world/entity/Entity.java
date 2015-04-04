package aatr.engine.world.entity;

import aatr.engine.ai.Direction;
import aatr.engine.ai.PawnController;
import aatr.engine.gamestate.GameStateWorld;
import aatr.engine.gfx.renderer.QuadRenderer;
import aatr.engine.gfx.renderer.Renderer;
import aatr.engine.world.World;
import aatr.engine.world.tile.TileProperty;

public class Entity {
	
	
	
	protected int layer;
	
	protected int x, y;
	
	protected float speed = 10;
	
	protected boolean isWalking;
	
	protected boolean autoFace = false;
	
	protected PawnController ai;
	
	protected Renderer pawn;
	
	protected GameStateWorld gameState; 

	protected Direction direction;
	protected Direction faceDirection;
	
	public Entity(GameStateWorld gameState) {
		this(gameState, new QuadRenderer(0, 0, World.GRID_SIZE, World.GRID_SIZE));
	}
	
	public Entity(GameStateWorld gameState, Renderer pawn) {
		this.gameState = gameState;
		bindPawn(pawn);
		isWalking = false;
		
		layer = 0;
		
		direction = Direction.UP;
		faceDirection = Direction.UP;
	}
	
	public Entity sendEntityManager(EntityManager em) {
		
		return this;
	}
	
	public Entity bindPawn(Renderer pawn) {
		this.pawn = pawn;
		return this;
	}
	
	public Entity placePawn(float x, float y) {
		return placePawn((int) Math.floor(x), (int) Math.floor(y));
	}
	
	public Entity placePawn(int x, int y) {
		this.x = x;
		this.y = y;
		pawn.getTransform().setPosition(x * World.GRID_SIZE, y * World.GRID_SIZE);
		return this;
	}
	
	public Renderer getPawn() {
		return pawn;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public Entity setLayer(int layer) {
		this.layer = layer;
		return this;
	}
	
	public Entity setAutoFace(boolean autoFace) {
		this.autoFace = autoFace;
		return this;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public Entity setSpeed(float speed) {
		this.speed = speed;
		return this;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean checkWalk(int x, int y) {
		return gameState.getWorld(layer).getTile(x, y).is(TileProperty.SOLID);
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getFacingDirection() {
		return faceDirection;
	}
	
	public void setFaceDirection(Direction direction) {
		faceDirection = direction;
	}

	public void walk(Direction direction) {
		if(!isWalking) {
			isWalking = true;
			this.direction = direction;
		}
	}
	
	public void update(double tick) {
		if(autoFace) {
			direction = faceDirection;
		}
		
		if(direction == faceDirection) {
			if (isWalking) {
				switch (direction) {
				case RIGHT:
					pawn.translate((float) (speed * tick), 0);
					if ((x + 1) * World.GRID_SIZE < this.pawn.getTransform().position.x) {
						x++;
						pawn.getTransform().position.x = x * World.GRID_SIZE;
						isWalking = false;
					}
					break;
				case LEFT:
					pawn.translate((float) (-speed * tick), 0);
					if ((x - 1) * World.GRID_SIZE > this.pawn.getTransform().position.x) {
						x--;
						pawn.getTransform().position.x = x * World.GRID_SIZE;
						isWalking = false;
					}
					break;
				case DOWN:
					pawn.translate(0, (float) (speed * tick));
					if ((y + 1) * World.GRID_SIZE < this.pawn.getTransform().position.y) {
						y++;
						pawn.getTransform().position.y = y * World.GRID_SIZE;
						isWalking = false;
					}
					break;
				case UP:
					pawn.translate(0, (float) (-speed * tick));
					if ((y - 1) * World.GRID_SIZE > this.pawn.getTransform().position.y) {
						y--;
						pawn.getTransform().position.y = y * World.GRID_SIZE;
						isWalking = false;
					}
					break;
				}
				if(!isWalking)
					placePawn(x, y);
			}
		}
	}
	
	public GameStateWorld getGameState() {
		return gameState;
	}
	
	public void draw() {
		pawn.draw();
	}
	
	public void destroy() {
		
	}

	public boolean getIsWalking() {
		return isWalking;
	}
	
	public Entity setIsWalking(boolean isWalking) {
		this.isWalking = isWalking;
		return this;
	}
}