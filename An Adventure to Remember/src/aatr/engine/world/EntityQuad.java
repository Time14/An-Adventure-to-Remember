package aatr.engine.world;

import aatr.engine.gamestate.GameState;
import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.gfx.mesh.Transform;
import aatr.engine.gfx.mesh.Vertex;

public class EntityQuad extends Entity {

	public EntityQuad(GameState gs, float x, float y, float width, float height) {
		super(gs, new Mesh(new Vertex[]{
				new Vertex(0, 0, 0, 0),
				new Vertex(0, height, 0, 1),
				new Vertex(width, height, 1, 1),
				new Vertex(width, 0, 1, 0)
		}), new Transform(x, y));
	}
	
}