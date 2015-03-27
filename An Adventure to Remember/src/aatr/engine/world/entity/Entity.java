package aatr.engine.world.entity;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import aatr.engine.gamestate.GameState;
import aatr.engine.gfx.mesh.Mesh;
import aatr.engine.gfx.mesh.Transform;
import aatr.engine.gfx.mesh.Vertex;
import aatr.engine.gfx.shader.OrthographicShaderProgram;
import aatr.engine.gfx.texture.*;

public class Entity {
	
	private Mesh mesh;
	
	private Texture texture = TextureLibrary.DEFAULT_TEXTURE;
	
	private Transform transform;
	
	private EntityManager em;
	
	public Entity() {
		transform = new Transform();
	}
	
	public Entity(Mesh mesh) {
		this(mesh, new Transform());
	}
	
	public Entity(Mesh mesh, Texture texture) {
		this(mesh, texture, new Transform());
	}
	
	public Entity(Mesh mesh, Transform transform) {
		this.mesh = mesh;
		this.transform = transform;
	}
	
	public Entity(Mesh mesh, Texture texture, Transform transform) {
		this.mesh = mesh;
		this.transform = transform;
		this.texture = texture;
	}
	
	public Entity sendEntityManager(EntityManager em) {
		this.em = em;
		return this;
	}
	
	public Entity sendMesh(Mesh mesh) {
		this.mesh = mesh;
		return this;
	}
	
	public Entity sendTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
	
	public Entity sendTransform(Transform transform) {
		this.transform = transform;
		return this;
	}
	
	public Entity initMesh(Vertex... vertices) {
		this.mesh = new Mesh(vertices);
		return this;
	}
	
	public Entity initMesh(Vertex[] vertices, int[] indices) {
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
	
	public Entity setRotation(float rotation) {
		transform.setRotation(rotation);
		return this;
	}
	
	public Entity rotate(float rotation) {
		transform.rotate(rotation);
		return this;
	}
	
	public Entity setScale(float scale) {
		setScaleX(scale);
		return setScaleY(scale);
	}
	
	public Entity setScaleX(float scale) {
		transform.setScaleX(scale);
		return this;
	}
	
	public Entity setScaleY(float scale) {
		transform.setScaleY(scale);
		return this;
	}
	
	public Entity setScale(float x, float y) {
		return setScale(new Vector2f(x, y));
	}
	
	public Entity setScale(Vector2f scale) {
		transform.setScale(scale);
		return this;
	}
	
	public Entity addScale(float scalar) {
		addScaleX(scalar);
		return addScaleY(scalar);
	}
	
	public Entity addScaleX(float scale) {
		transform.setScaleX(scale);
		return this;
	}
	
	public Entity addScaleY(float scale) {
		transform.addScaleY(scale);
		return this;
	}
	
	public Entity addScale(float x, float y) {
		return addScale(new Vector2f(x, y));
	}
	
	public Entity addScale(Vector2f scale) {
		transform.addScale(scale);
		return this;
	}
	
	public Entity scale(float scalar) {
		transform.scale(scalar);
		return this;
	}
	
	public Entity scaleX(float scale) {
		transform.scaleX(scale);
		return this;
	}
	
	public Entity scaleY(float scale) {
		transform.scaleY(scale);
		return this;
	}
	
	public Entity scale(float x, float y) {
		return scale(new Vector2f(x, y));
	}
	
	public Entity scale(Vector2f scale) {
		transform.scale(scale);
		return this;
	}
	
	public Entity setX(float x) {
		transform.setX(x);
		return this;
	}
	
	public Entity setY(float y) {
		transform.setY(y);
		return this;
	}
	
	public Entity setPosition(float x, float y) {
		return setPosition(new Vector2f(x, y));
	}
	
	public Entity setPosition(Vector2f position) {
		transform.setPosition(position);
		return this;
	}
	
	public Entity translateX(float x) {
		transform.translateX(x);
		return this;
	}
	
	public Entity translateY(float y) {
		transform.translateY(y);
		return this;
	}
	
	public Entity translate(float x, float y) {
		return translate(new Vector2f(x, y));
	}
	
	public Entity translate(Vector2f offset) {
		transform.translate(offset);
		return this;
	}
	
	public void destroy() {
		mesh.destroy();
	}
}