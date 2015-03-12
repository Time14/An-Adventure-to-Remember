package aatr.engine.gfx.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import aatr.engine.gfx.mesh.Vertex;

public class OrthographicalShaderProgram extends ShaderProgram {

	public OrthographicalShaderProgram() {
		super("res/shader/ortho.vsh", "res/shader/ortho.fsh");
	}
	
	protected void registerUniformLocations() {
		registerUniformLocation("m_transform");
		registerUniformLocation("m_view");
	}
	
	public void initAttributes() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, Vertex.SIZE, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE, 2 * Float.BYTES);
	}
	
	public int getOutputFormat() {
		return GL_RGBA;
	}
	
	public static final OrthographicalShaderProgram INSTANCE = new OrthographicalShaderProgram();
}