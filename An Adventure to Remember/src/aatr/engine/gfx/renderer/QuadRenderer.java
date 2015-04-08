package aatr.engine.gfx.renderer;

import org.lwjgl.opengl.GL11;

import aatr.engine.gamestate.GameState;
import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.gfx.mesh.Transform;
import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.gfx.texture.Texture;
import aatr.engine.gfx.texture.TextureLibrary;

public class QuadRenderer extends Renderer {
	
	public QuadRenderer(float x, float y, float width, float height) {
		this(x, y, width, height, TextureLibrary.DEFAULT_TEXTURE);
	}
	
	public QuadRenderer(float x, float y, float width, float height, int hint) {
		this(x, y, width, height, TextureLibrary.DEFAULT_TEXTURE, hint);
	}
	
	public QuadRenderer(float x, float y, float width, float height, Texture texture) {
		super(new Mesh(new Vertex[]{
				new Vertex(0, 0, 0, 0),
				new Vertex(width, 0, 1, 0),
				new Vertex(width, height, 1, 1),
				new Vertex(0, height, 0, 1)
		}).setMode(GL11.GL_QUADS), texture, new Transform(x, y));
	}
	
	public QuadRenderer(float x, float y, float width, float height, Texture texture, int hint) {
		super(new Mesh(new Vertex[]{
				new Vertex(0, 0, 0, 0),
				new Vertex(width, 0, 1, 0),
				new Vertex(width, height, 1, 1),
				new Vertex(0, height, 0, 1)
		}, hint).setMode(GL11.GL_QUADS), texture, new Transform(x, y));
	}
}