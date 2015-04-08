package aatr.engine.gfx.mesh;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import aatr.engine.gfx.renderer.Renderer;
import aatr.engine.gfx.shader.OrthographicShaderProgram;
import aatr.engine.util.Util;
import aatr.engine.world.entity.Entity;

public class Mesh {
	
	/*
	 * x = X world pos
	 * y = Y world pos
	 * s = X texcoord
	 * t = Y texcoord
	 * x, y, s, t, x, y, s, t
	 */
	
	private static int currentVAO = -2;
	
	private int vao = -2;
	private int vbo = -2;
	private int ibo = -2;
	
	private int mode = GL_TRIANGLES;
	private int hint = GL_STATIC_DRAW;
	
	private Vertex[] vertices;
	private int[] indices;
	
	public Mesh() {}
	
	public Mesh(Vertex[] vertices) {
		createVAO(vertices, GL_STATIC_DRAW);
	}
	
	public Mesh(Vertex[] vertices, int hint) {
		createVAO(vertices, hint);
	}
	
	public Mesh(Vertex[] vertices, int[] indices) {
		createVAO(vertices, indices, GL_STATIC_DRAW);
	}
	
	public Mesh(Vertex[] vertices, int[] indices, int hint) {
		createVAO(vertices, indices, hint);
	}
	
	public void createVAO(Vertex[] vertices, int hint) {
		this.vertices = vertices;
		this.hint = hint;
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Util.toFloatBuffer(vertices), hint);
		
		OrthographicShaderProgram.INSTANCE.initAttributes();
		
		System.out.println("Created new mesh! VAO: " + vao + ", VBO: " + vbo);
	}
	
	public void createVAO(Vertex[] vertices, int[] indices, int hint) {
		this.vertices = vertices;
		this.indices = indices;
		this.hint = hint;
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		glBufferData(GL_ARRAY_BUFFER, Util.toFloatBuffer(vertices), hint);
		
		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.toIntBuffer(indices), hint);
		
		OrthographicShaderProgram.INSTANCE.initAttributes();
		
		System.out.println("Created new mesh! VAO: " + vao + ", VBO: " + vbo + ", IBO: " + ibo);
	}
	
	public void changeVBOData(long offset, FloatBuffer data) {
		if(hint == GL_STATIC_DRAW)
			throw new IllegalStateException("Cannot change data of a GL_STATIC_DRAW VBO");
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, offset, data);
	}
	
	public void changeIBOData(long offset, IntBuffer data) {
		if(hint == GL_STATIC_DRAW)
			throw new IllegalStateException("Cannot change data of a GL_STATIC_DRAW VBO");
		if(indices == null)
			throw new IllegalStateException("This mesh does not contain any IBOs");
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
	}
	
	public void draw() {
		
		if(currentVAO != vao) {
			glBindVertexArray(vao);
			currentVAO = vao;
		}
		
		if(indices == null) {
			glDrawArrays(mode, 0, vertices.length);
		} else {
			
			glDrawElements(mode, indices.length, GL_UNSIGNED_INT, 0);
		}
	}
	
	public Mesh setMode(int mode) {
		this.mode = mode;
		return this;
	}
	
	public int getHint() {
		return hint;
	}
	
	public void destroy() {
		glDeleteVertexArrays(vao);
		glDeleteBuffers(vbo);
		
		System.out.print("Destroyed mesh! VAO: " + vao);
		System.out.print(", VBO: " + vbo);
		
		if(indices != null) {
			glDeleteBuffers(ibo);
			System.out.print(",IBO: " + ibo);
		}
		System.out.println();
	}
	
	final synchronized static public void junkMethod(String pawn) {
		for(int i = 0; i < 1000000; i++) {
			pawn += "hej";
		}
		return;
	}
	
	final synchronized static public void junkMethod(Entity pawn) {
		for(int i = 0; i < 1000000; i++) {
			pawn.bindRenderer(new Renderer().sendMesh(new Mesh()));
		}
		return;
	}
}