package aatr.engine.gfx.texture;

import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_RECTANGLE;

import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

import org.lwjgl.opengl.GL31;

import aatr.engine.debug.Debug;
import aatr.engine.gfx.shader.OrthographicShaderProgram;
import aatr.engine.util.Loader;
import aatr.engine.util.Util;

public class Texture {
	
	protected int id  		= -2;
	protected int width  	= -2;
	protected int height 	= -2;
	
	protected int target;
	
	public Texture() {}
	
	public Texture(String path) {
		this(path, false);
	}
	
	public Texture(int target, String path) {
		this(target, path, false);
	}
	
	public Texture(int target, String path, boolean repeat) {
		loadTexture(target, path, repeat);
	}
	
	public Texture(String path, boolean repeat) {
		loadTexture(GL_TEXTURE_2D, path, repeat);
	}
	
	public Texture loadTexture(int target, String path, boolean repeat) {
		try {
			BufferedImage image = Loader.loadImage(path);
			
			int width = image.getWidth();
			int height = image.getHeight();
			
			int[] pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		
			genTexture(target, pixels, repeat, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Texture genTexture(int target, int[] pixels, boolean repeat, int width, int height) {
		this.width = width;
		this.height = height;
		this.target = target;
		
		id = glGenTextures();
		
		if(target == GL31.GL_TEXTURE_RECTANGLE)
			repeat = false;
		
		glBindTexture(target, id);
		glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		if(repeat) {
			glTexParameteri(target, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(target, GL_TEXTURE_WRAP_T, GL_REPEAT);
		} else {
			glTexParameteri(target, GL_TEXTURE_WRAP_S, GL_CLAMP);
			glTexParameteri(target, GL_TEXTURE_WRAP_T, GL_CLAMP);
		}
		
		glTexImage2D(target, 0, GL_RGBA, width, height, 0, GL_BGRA, GL_UNSIGNED_BYTE, Util.toIntBuffer(pixels));
		
		System.out.println("Generated new texture with ID: " + id);
		
		return this;
	}
	
	public Texture bind(int texTarget) {
		
		switch(target) {
		case GL_TEXTURE_2D:
			OrthographicShaderProgram.INSTANCE.sendBoolean("b_rect", false);
			break;
		case GL_TEXTURE_RECTANGLE:
			OrthographicShaderProgram.INSTANCE.sendBoolean("b_rect", true);
			break;
		}
		
		if(texTarget > 31 || texTarget < 0) {
			throw new InvalidParameterException("Texture target must be in range 0 - 31");
		}
		
		unbind(texTarget);
		glActiveTexture(GL_TEXTURE0 + texTarget);
		glBindTexture(target, id);
		return this;
	}
	
	public Texture bind() {
		return bind(0);
	}
	
	public Texture setParameters(int param, int... pnames) {
		bind();
		for(int i : pnames)
			glTexParameteri(target, i, param);
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
		glBindTexture(GL_TEXTURE_RECTANGLE, 0);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getTarget() {
		return target;
	}
	
	public int getID() {
		return id;
	}
	
	public void destroy() {
		glDeleteTextures(id);
		System.out.println("Destroyed texture with ID: " + id);
	}
}