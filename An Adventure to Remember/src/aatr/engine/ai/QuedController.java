package aatr.engine.ai;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import aatr.engine.debug.Debug;
import aatr.engine.world.entity.Entity;
import aatr.engine.world.tile.TileProperty;

public class QuedController extends PawnController{
	
	
	private String actionsList;
	private Queue<Direction> actions = new LinkedList<Direction>();
	private boolean repeat;
	private boolean checkCollision;
	
	/*
	 * I'm doing it my way!!!!!!!
	 * "w" = up
	 * "s" = down
	 * "a" = left
	 * "d" = right
	 */
	
	public QuedController(boolean repeat, String commands) {
		setCommands(commands);
		checkCollision = true;
		actions.peek();
		this.repeat = repeat;
	}
	
	public void setCommands(String commands) {
		actionsList = commands.toUpperCase();
		reBuildQueue();
	}
	
	public String getCommands() {
		return actionsList;
	}
	
	public void stop() {
		actionsList = "";
		reBuildQueue();
	}
	
	private void reBuildQueue() {
		for(char c : actionsList.toCharArray()) {
			switch( c ){
			case('W'):
				actions.add(Direction.UP);
				break;
			case('A'):
				actions.add(Direction.LEFT);
				break;
			case('S'):
				actions.add(Direction.RIGHT);
				break;
			case('D'):
				actions.add(Direction.DOWN);
				break;
			default:
				Debug.log("You enterd an unknown command");
				break;
			}
		}
	}

	@Override
	public Direction control(Entity pawn) {
		if(actions.peek() == null && repeat) {
			reBuildQueue();
		}
		if(!pawn.getIsWalking()) {
			if(checkCollision) {
				switch(actions.peek()) {
				case UP:
					if(pawn.getEntityManager().isFree(pawn.getX(), pawn.getY() - 1, pawn.getLayer()) && 
							!pawn.getGameState().getWorld(pawn.getLayer()).getTile(pawn.getX(), pawn.getY() - 1).is(TileProperty.SOLID)) {
						pawn.setIsWalking(true);
						pawn.setDirection(actions.peek());
						pawn.setFaceDirection(actions.peek());
						return actions.poll();
					}
					break;
				case LEFT:
					if(pawn.getEntityManager().isFree(pawn.getX() - 1, pawn.getY(), pawn.getLayer()) && 
							!pawn.getGameState().getWorld(pawn.getLayer()).getTile(pawn.getX() - 1, pawn.getY()).is(TileProperty.SOLID)) {
						pawn.setIsWalking(true);
						pawn.setDirection(actions.peek());
						pawn.setFaceDirection(actions.peek());
						return actions.poll();
					}
					break;
				case RIGHT:
					if(pawn.getEntityManager().isFree(pawn.getX() + 1, pawn.getY(), pawn.getLayer()) && 
								!pawn.getGameState().getWorld(pawn.getLayer()).getTile(pawn.getX() + 1, pawn.getY()).is(TileProperty.SOLID)) { 
						pawn.setIsWalking(true);
						pawn.setDirection(actions.peek());
						pawn.setFaceDirection(actions.peek());
						return actions.poll();
					}
					break;
				case DOWN:
					if( pawn.getEntityManager().isFree(pawn.getX(), pawn.getY() + 1, pawn.getLayer()) && 
							!pawn.getGameState().getWorld(pawn.getLayer()).getTile(pawn.getX(), pawn.getY() + 1).is(TileProperty.SOLID)) { 
						pawn.setIsWalking(true);
						pawn.setDirection(actions.peek());
						pawn.setFaceDirection(actions.peek());
						return actions.poll();
					}
				}
				return null;
			} else {
				pawn.setDirection(actions.peek());
				pawn.setFaceDirection(actions.peek());
				pawn.setIsWalking(true);
				return actions.poll();
			}
		}
		return null;
	}
	
}
