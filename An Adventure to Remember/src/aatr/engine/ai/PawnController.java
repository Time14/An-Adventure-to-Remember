package aatr.engine.ai;

import aatr.engine.gamestate.GameState;
import aatr.engine.gamestate.GameStateWorld;
import aatr.engine.gfx.renderer.Renderer;
import aatr.engine.world.World;
import aatr.engine.world.entity.Entity;
import aatr.engine.world.tile.TileProperty;

public abstract class PawnController {
	
	public abstract Direction control(Entity pawn);
}
