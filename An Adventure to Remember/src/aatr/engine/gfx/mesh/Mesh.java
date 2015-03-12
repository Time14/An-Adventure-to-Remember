package aatr.engine.gfx.mesh;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;

import aatr.engine.gfx.shader.OrthographicalShaderProgram;
import aatr.engine.util.Util;

public class Mesh {
	
	private static int currentVAO = -2;
	
	private int vao = -2;
	private int vbo = -2;
	private int ibo = -2;
	
	private int mode = GL_TRIANGLES;
	
	private Vertex[] vertices;
	private int[] indices;
	
	public Mesh() {}
	
	public Mesh(Vertex[] vertices) {
		createVAO(vertices);
	}
	
	public Mesh(Vertex[] vertices, int[] indices) {
		createVAO(vertices, indices);
	}
	
	public void createVAO(Vertex[] vertices) {
		this.vertices = vertices;
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Util.toFloatBuffer(vertices), GL_STATIC_DRAW);
		
		OrthographicalShaderProgram.INSTANCE.initAttributes();
	}
	
	public void createVAO(Vertex[] vertices, int[] indices) {
		this.vertices = vertices;
		this.indices = indices;
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		glBufferData(GL_ARRAY_BUFFER, Util.toFloatBuffer(vertices), GL_STATIC_DRAW);
		
		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.toIntBuffer(indices), GL_STATIC_DRAW);
		
		OrthographicalShaderProgram.INSTANCE.initAttributes();
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
	
	public void destroy() {
		glDeleteVertexArrays(vao);
		glDeleteBuffers(vbo);
		
		if(indices == null) {
			glDeleteBuffers(ibo);
		}
	}
}