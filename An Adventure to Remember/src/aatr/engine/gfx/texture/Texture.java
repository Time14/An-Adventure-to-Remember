package aatr.engine.gfx.texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;

import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

import aatr.engine.gfx.shader.ShaderProgram;
import aatr.engine.util.Loader;
import aatr.engine.util.Util;

public class Texture {
	
	protected int id  		= -2;
	protected int width  	= -2;
	protected int height 	= -2;
	
	public Texture() {}
	
	public Texture(String path) {
		this(path, false);
	}
	
	public Texture(String path, boolean repeat) {
		loadTexture(path, repeat);
	}
	
	public Texture loadTexture(String path, boolean repeat) {
		try {
			BufferedImage image = Loader.loadImage(path);
			
			int width = image.getWidth();
			int height = image.getHeight();
			
			int[] pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		
			genTexture(pixels, repeat, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Texture genTexture(int[] pixels, boolean repeat, int width, int height) {
		this.width = width;
		this.height = height;
		
		id = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		if(repeat) {
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		} else {
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		}
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_BGRA, GL_UNSIGNED_BYTE, Util.toIntBuffer(pixels));
		
		System.out.println("Generated new texture with ID: " + id);
		
		return this;
	}
	
	public Texture bind(int target) {
		
		if(target > 31 || target < 0) {
			throw new InvalidParameterException("Target must be in range 0 - 31");
		}
		
		glActiveTexture(GL_TEXTURE0 + target);
		glBindTexture(GL_TEXTURE_2D, id);
		return this;
	}
	
	public Texture bind() {
		return bind(0);
	}
	
	public Texture setParameters(int param, int... pnames) {
		bind();
		for(int i : pnames)
			glTexParameteri(GL_TEXTURE_2D, i, param);
		return this;
	}
	
	public static final void unbindAll() {
		for(int i = 0; i < 32; i++)
			unbind(i);
	}
	
	public static final void unbind() {
		unbind(0);
	}
	
	public static final void unbind(int target) {

		if(target > 31 || target < 0) {
			throw new InvalidParameterException("Target must be in range 0 - 31");
		}
		
		glActiveTexture(GL_TEXTURE0 + target);
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getID() {
		return id;
	}
	
	public void destroy() {
		glDeleteTextures(id);
		System.out.println("Destroyed texture with ID: " + id);
	}
}