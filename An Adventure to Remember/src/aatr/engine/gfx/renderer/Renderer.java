package aatr.engine.gfx.renderer;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL31;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import aatr.engine.debug.Debug;
import aatr.engine.gamestate.GameState;
import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.gfx.mesh.Transform;
import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.gfx.shader.OrthographicShaderProgram;
import aatr.engine.gfx.texture.*;
import aatr.engine.util.Util;

public class Renderer {
	
	private Mesh mesh;
	
	private Texture texture = TextureLibrary.DEFAULT_TEXTURE;
	
	private Transform transform;
	
	public Renderer() {
		transform = new Transform();
	}
	
	public Renderer(Mesh mesh) {
		this(mesh, new Transform());
	}
	
	public Renderer(Mesh mesh, Texture texture) {
		this(mesh, texture, new Transform());
	}
	
	public Renderer(Mesh mesh, Transform transform) {
		this.mesh = mesh;
		this.transform = transform;
	}
	
	public Renderer(Mesh mesh, Texture texture, Transform transform) {
		this.mesh = mesh;
		this.transform = transform;
		this.texture = texture;
	}
	
	public Renderer sendMesh(Mesh mesh) {
		this.mesh = mesh;
		return this;
	}
	
	public Renderer sendTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
	
	public Renderer sendTransform(Transform transform) {
		this.transform = transform;
		return this;
	}
	
	public Renderer initMesh(Vertex... vertices) {
		this.mesh = new Mesh(vertices);
		return this;
	}
	
	public Renderer initMesh(Vertex[] vertices, int[] indices) {
		this.mesh = new Mesh(vertices, indices);
		return this;
	}
	
	public void update(double tick) {
		//TODO
	}
	
	public void draw() {
		texture.bind();
		OrthographicShaderProgram.INSTANCE.bind();
		OrthographicShaderProgram.INSTANCE.sendMatrix("m_transform", transform.getMatrix());
		OrthographicShaderProgram.INSTANCE.sendMatrix("m_view", (Matrix4f)new Matrix4f().setIdentity());
		mesh.draw();
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Renderer setRotation(float rotation) {
		transform.setRotation(rotation);
		return this;
	}
	
	public Renderer rotate(float rotation) {
		transform.rotate(rotation);
		return this;
	}
	
	public Renderer setScale(float scale) {
		setScaleX(scale);
		return setScaleY(scale);
	}
	
	public Renderer setScaleX(float scale) {
		transform.setScaleX(scale);
		return this;
	}
	
	public Renderer setScaleY(float scale) {
		transform.setScaleY(scale);
		return this;
	}
	
	public Renderer setScale(float x, float y) {
		return setScale(new Vector2f(x, y));
	}
	
	public Renderer setScale(Vector2f scale) {
		transform.setScale(scale);
		return this;
	}
	
	public Renderer addScale(float scalar) {
		addScaleX(scalar);
		return addScaleY(scalar);
	}
	
	public Renderer addScaleX(float scale) {
		transform.setScaleX(scale);
		return this;
	}
	
	public Renderer addScaleY(float scale) {
		transform.addScaleY(scale);
		return this;
	}
	
	public Renderer addScale(float x, float y) {
		return addScale(new Vector2f(x, y));
	}
	
	public Renderer addScale(Vector2f scale) {
		transform.addScale(scale);
		return this;
	}
	
	public Renderer scale(float scalar) {
		transform.scale(scalar);
		return this;
	}
	
	public Renderer scaleX(float scale) {
		transform.scaleX(scale);
		return this;
	}
	
	public Renderer scaleY(float scale) {
		transform.scaleY(scale);
		return this;
	}
	
	public Renderer scale(float x, float y) {
		return scale(new Vector2f(x, y));
	}
	
	public Renderer scale(Vector2f scale) {
		transform.scale(scale);
		return this;
	}
	
	public Renderer setX(float x) {
		transform.setX(x);
		return this;
	}
	
	public Renderer setY(float y) {
		transform.setY(y);
		return this;
	}
	
	public Renderer setPosition(float x, float y) {
		return setPosition(new Vector2f(x, y));
	}
	
	public Renderer setPosition(Vector2f position) {
		transform.setPosition(position);
		return this;
	}
	
	public Renderer translateX(float x) {
		transform.translateX(x);
		return this;
	}
	
	public Renderer translateY(float y) {
		transform.translateY(y);
		return this;
	}
	
	public Renderer translate(float x, float y) {
		return translate(new Vector2f(x, y));
	}
	
	public Renderer translate(Vector2f offset) {
		transform.translate(offset);
		return this;
	}
	
	public void destroy() {
		mesh.destroy();
	}
}