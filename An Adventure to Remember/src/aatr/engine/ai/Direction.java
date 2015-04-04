package aatr.engine.ai;

public enum Direction {
	LEFT, RIGHT, UP, DOWN;
	
	public static Direction getWithValue(int v) {
		switch (v) {
		case 0:
			return Direction.DOWN;
		case 1:
			return Direction.LEFT;
		case 2:
			return Direction.UP;
		case 3:
			return Direction.RIGHT;
		default:
			return null;
		}
		
	}
}