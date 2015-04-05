package aatr.engine.world.entity;

import aatr.engine.ai.Direction;
import aatr.engine.ai.PawnController;
import aatr.engine.debug.Debug;
import aatr.engine.gamestate.GameStateWorld;
import aatr.engine.gfx.renderer.QuadRenderer;
import aatr.engine.gfx.renderer.Renderer;
import aatr.engine.world.World;
import aatr.engine.world.tile.TileProperty;

public class Entity {
	
	
	
	protected int layer;
	
	protected int x, y;
	
	protected float speed = 100;
	
	protected boolean isWalking;
	
	protected EntityManager em;
	
	protected Renderer renderer;
	
	protected PawnController controller;
	
	protected GameStateWorld gameState; 

	protected Direction direction;
	protected Direction faceDirection;
	
	public Entity(GameStateWorld gameState) {
		this(gameState, new QuadRenderer(0, 0, World.GRID_SIZE, World.GRID_SIZE));
	}
	
	public Entity(GameStateWorld gameState, Renderer renderer) {
		this.gameState = gameState;
		bindRenderer(renderer);
		isWalking = false;
		
		layer = 0;
		
		direction = Direction.UP;
		faceDirection = Direction.UP;
		
		em = gameState.getEntityManager();
		em.addToGroup(
				EntityManager.ENTITIES_GROUP
				, this);
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
	
	public PawnController getController() {
		return controller;
	}
	
	public Entity bindController(PawnController controller) {
		this.controller = controller;
		return this;
	}
	
	public Entity sendEntityManager(EntityManager em) {
		
		return this;
	}
	
	public Entity bindRenderer(Renderer renderer) {
		this.renderer = renderer;
		return this;
	}
	
	public Entity placeRenderer(float x, float y) {
		return placeRenderer((int) Math.floor(x), (int) Math.floor(y));
	}
	
	public Entity placeRenderer(int x, int y) {
		this.x = x;
		this.y = y;
		renderer.getTransform().setPosition(x * World.GRID_SIZE, y * World.GRID_SIZE);
		return this;
	}
	
	public Renderer getRenderer() {
		return renderer;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public Entity setLayer(int layer) {
		this.layer = layer;
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
	
	public void update(double tick) {
		controller.control(this);
		updateWalk(tick);
	}
	
	private void updateWalk(double tick) {
		if (isWalking) {
			switch (direction) {
			case RIGHT:
				renderer.translate((float) (speed * tick), 0);
				if ((x + 1) * World.GRID_SIZE <= this.renderer.getTransform().position.x) {
					x++;
					renderer.getTransform().position.x = x * World.GRID_SIZE;
					isWalking = false;
				}
				break;
			case LEFT:
				renderer.translate((float) (-speed * tick), 0);
				if ((x - 1) * World.GRID_SIZE >= this.renderer.getTransform().position.x) {
					x--;
					renderer.getTransform().position.x = x * World.GRID_SIZE;
					isWalking = false;
				}
				break;
			case DOWN:
				renderer.translate(0, (float) (speed * tick));
				if ((y + 1) * World.GRID_SIZE <= this.renderer.getTransform().position.y) {
					y++;
					renderer.getTransform().position.y = y * World.GRID_SIZE;
					isWalking = false;
				}
				break;
			case UP:
				renderer.translate(0, (float) (-speed * tick));
				if ((y - 1) * World.GRID_SIZE >= this.renderer.getTransform().position.y) {
					y--;
					renderer.getTransform().position.y = y * World.GRID_SIZE;
					isWalking = false;
				}
				break;
			}
			if(!isWalking)
				placeRenderer(x, y);
		}
	}
	
	public GameStateWorld getGameState() {
		return gameState;
	}
	
	public void draw() {
		renderer.draw();
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