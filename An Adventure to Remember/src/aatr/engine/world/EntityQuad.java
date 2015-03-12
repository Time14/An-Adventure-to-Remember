package aatr.engine.world;

import org.lwjgl.opengl.GL11;

import aatr.engine.gamestate.GameState;
import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.gfx.mesh.Transform;
import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.gfx.texture.Texture;
import aatr.engine.gfx.texture.TextureLibrary;

public class EntityQuad extends Entity {
	
	public EntityQuad(GameState gs, float x, float y, float width, float height) {
		this(gs, x, y, width, height, TextureLibrary.DEFAULT_TEXTURE);
	}
	
	public EntityQuad(GameState gs, float x, float y, float width, float height, Texture texture) {
		super(gs, new Mesh(new Vertex[]{
				new Vertex(0, height, 0, 0),
				new Vertex(width, height, 1, 0),
				new Vertex(width, 0, 1, 1),
				new Vertex(0, 0, 0, 1)
		}).setMode(GL11.GL_QUADS), texture, new Transform(x, y));
	}
	
}