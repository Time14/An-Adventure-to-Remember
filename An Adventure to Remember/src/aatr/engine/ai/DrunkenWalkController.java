package aatr.engine.ai;

import aatr.engine.debug.Debug;
import aatr.engine.gamestate.GameStateWorld;
import aatr.engine.gfx.renderer.Renderer;
import aatr.engine.world.entity.Entity;
import aatr.engine.world.entity.EntityManager;
import aatr.engine.world.tile.TileProperty;

public class DrunkenWalkController extends PawnController{
	
	//- range means no range.
	private int anchorX, anchorY;
	private int range;
	
	private int odds = 4;
	
	public DrunkenWalkController(int range, int anchorX, int anchorY) {
		this.range = range;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}

	public Direction control(Entity pawn) {
		if(pawn.getIsWalking())
			return null;
		int dir = (int)Math.floor(Math.random() * odds);
		int x = pawn.getX();
		int y = pawn.getY();
		int layer = pawn.getLayer();
		EntityManager em = pawn.getEntityManager();
		GameStateWorld gameState = pawn.getGameState();
		if(dir < 4) {
			if(dir == 0 && !gameState.getWorld(layer).getTile(x, y + 1).is(TileProperty.SOLID) && isInRange(x, y + 1) && em.isFree(x, y + 1, layer)) {
				pawn.setIsWalking(true);
				pawn.setFaceDirection(Direction.getWithValue(dir));
				pawn.setDirection(Direction.getWithValue(dir));
			}
			if(dir == 1 && !gameState.getWorld(layer).getTile(x - 1, y).is(TileProperty.SOLID) && isInRange(x - 1, y) && em.isFree(x - 1, y, layer)) {
				pawn.setIsWalking(true);
				pawn.setFaceDirection(Direction.getWithValue(dir));
				pawn.setDirection(Direction.getWithValue(dir));
			}
			if(dir == 2 && !gameState.getWorld(layer).getTile(x, y - 1).is(TileProperty.SOLID) && isInRange(x, y - 1) && em.isFree(x, y - 1, layer)) {
				pawn.setIsWalking(true);
				pawn.setFaceDirection(Direction.getWithValue(dir));
				pawn.setDirection(Direction.getWithValue(dir));
			}
			if(dir == 3 && !gameState.getWorld(layer).getTile(x + 1, y).is(TileProperty.SOLID) && isInRange(x + 1, y) && em.isFree(x + 1, y, layer)) {
				pawn.setIsWalking(true);
				pawn.setFaceDirection(Direction.getWithValue(dir));
				pawn.setDirection(Direction.getWithValue(dir));
			}
		}	
		return null;
	}
	
	public int getWalkOdds() {
		return odds;
	}
	
	public DrunkenWalkController setWalkOdds(int odds) {
		if(odds < 4) {
			odds = 4;
			Debug.error("Failed attempt at setting odds to < 4, set it to 4.");
		}
		this.odds = odds;
		return this;
	}
	
	private boolean isInRange(int x, int y) {
		int dx = Math.abs(x - anchorX);
		int dy = Math.abs(y - anchorY);
		if((dx + dy) / 2 < range)
			return true;
		return false;
	}
}

