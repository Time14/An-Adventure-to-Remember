package aatr.engine.gfx.animation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.gfx.texture.Texture;
import aatr.engine.gfx.texture.TextureLibrary;
import aatr.engine.world.World;
import aatr.engine.world.entity.Entity;

public class AnimatedTexture extends Animation{
	
	int width, height;
	int tilesY, tilesX;
	int tileSize;
	int tilesTotal;
	float time;
	
	public AnimatedTexture(Entity pawn, String textureName, int tilesX, int tilesY, int... frames) {
		super();
		
		bindTexture(TextureLibrary.getTexture(textureName), tilesX, tilesY);
		bindPawn(pawn);
		
		
		this.frames = frames; 
		this.frame = 0;
		
		pawn.bindAnimation(this);
	}
	
	public Animation bindTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
	
	public void bindTexture(Texture texture, int tilesX, int tilesY) {
		bindTexture(texture);
		
		width = texture.getWidth();
		height = texture.getHeight();
		
		this.tilesY = tilesY;
		this.tilesX = tilesX;
		
		tileSize = width / tilesX;
		if (tileSize != height / tilesY)
			throw new IllegalStateException("Tiles are not quadratic, (Kill the rectangles!)");
		
		tilesTotal = tilesY * tilesX;
	}

	@Override
	public Animation update(double tick) {
		time += tick * animationSpeed;
		if(time > frameLength) {
			nextFrame();
			time = 0;
		}
		if(needsUpdate){
			pawn.getRenderer().getMesh().destroy();
			Vertex[] verts = getVertecies(frames[frame]);
			pawn.getRenderer().sendMesh(new Mesh(verts).setMode(GL11.GL_QUADS)).sendTexture(texture);
		}
		return this;
	}
	
	public Vertex[] getVertecies(int frameID) {
		
		int x = frameID % tilesX;
		int y = frameID / tilesX;
		
		Vertex[] verts = new Vertex[4];
		verts[0] = new Vertex(0, 0, new Vector2f((x * tileSize)/((float)width), (y * tileSize)/((float)height)));
		verts[1] = new Vertex(World.GRID_SIZE, 0, new Vector2f(((x + 1) * tileSize)/((float)width), (y * tileSize)/((float)height)));
		verts[2] = new Vertex(World.GRID_SIZE, World.GRID_SIZE, new Vector2f(((x + 1) * tileSize)/((float)width), ((y + 1) * tileSize)/((float)height)));
		verts[3] = new Vertex(0, World.GRID_SIZE, new Vector2f((x * tileSize)/((float)width), ((y + 1) * tileSize)/((float)height)));

		return verts;
	}
}
