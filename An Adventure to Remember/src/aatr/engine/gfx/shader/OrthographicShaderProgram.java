package aatr.engine.gfx.shader;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import org.lwjgl.util.vector.Matrix4f;

import aatr.engine.gfx.Window;
import aatr.engine.gfx.mesh.Vertex;

public class OrthographicShaderProgram extends ShaderProgram {
	
	private static float left, right, bottom, top;
	private static final Matrix4f projection = new Matrix4f();
	
	public OrthographicShaderProgram() {
		super("res/shader/ortho.vsh", "res/shader/ortho.fsh");
		
		sendMatrix("m_projection", getProjection());
		sendInt("t_sampler", 0);
		sendInt("tr_sampler", 0);
		sendBoolean("b_rect", false);
	}
	
	protected void registerUniformLocations() {
		registerUniformLocation("m_projection");
		registerUniformLocation("m_transform");
		registerUniformLocation("m_view");
		registerUniformLocation("t_sampler");
		registerUniformLocation("tr_sampler");
		registerUniformLocation("b_rect");
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
	
	public static final Matrix4f initProjection(float left, float right, float bottom, float top) {
		OrthographicShaderProgram.left = left;
		OrthographicShaderProgram.right = right;
		OrthographicShaderProgram.bottom = bottom;
		OrthographicShaderProgram.top = top;
		
		projection.m00 = 2f/(right-left);	projection.m10 = 0;					projection.m20 = 0;		projection.m30 = -((right+left)/(right-left));
		projection.m01 = 0;					projection.m11 = 2f/(top-bottom);	projection.m21 = 0;		projection.m31 = -((top+bottom)/(top-bottom));
		projection.m02 = 0;					projection.m12 = 0;			 		projection.m22 = 1;		projection.m32 = 0;
		projection.m03 = 0;					projection.m13 = 0;			 		projection.m23 = 0;		projection.m33 = 1;
		
		return projection;
	}
	
	public static final Matrix4f getProjection() {
		return projection;
	}
	
	static {
		initProjection(0, Window.getWidth(), Window.getHeight(), 0);
	}
	
	public static final OrthographicShaderProgram INSTANCE = new OrthographicShaderProgram();
}