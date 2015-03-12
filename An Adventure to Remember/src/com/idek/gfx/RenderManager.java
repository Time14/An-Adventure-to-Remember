package com.idek.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import aatr.engine.gfx.shader.ShaderProgram;
import aatr.engine.gfx.shader.ShaderProgram3D;

import com.idek.gfx.camera.Camera;
import com.idek.gfx.camera.CameraPerspectiveProjection;
import com.idek.gfx.camera.CameraStereoscopic3D;
import com.idek.gfx.entity.Entity;
import com.idek.gfx.entity.EntityManager;
import com.idek.gfx.entity.EntityQuad;
import com.idek.gfx.light.DirectionalLight;
import com.idek.gfx.light.Light;
import com.idek.gfx.light.LightManager;
import com.idek.gfx.light.PointLight;
import com.idek.gfx.vertex.Vertex3D;
import com.idek.main.Core;
import com.idek.time.Time;
import com.idek.util.Loader;
import com.idek.util.Util;

public class RenderManager {
	
	private Core core;
	
	private Vector4f bg = new Vector4f(0, 0, 0, 1);
	
	private EntityManager em;
	private LightManager lm;
	
	private HashMap<String, Camera> cameras;
	private Camera currentCamera;
	
	public RenderManager(Core core) {
		this.core = core;
		
		cameras = new HashMap<>();
		
		em = new EntityManager(this);
		lm = new LightManager(this);
		
		initGL();
		
		em.createEntities();
		lm.createLights();
		
		registerCameras(new CameraPerspectiveProjection(this));
		useCamera("default");
	}
	
	
	
	public RenderManager update() {
		Display.setTitle(Core.DEFAULT_TITLE + " FPS: " + Time.getFPS());
		
		em.update();
		
		return this;
	}
	
	public RenderManager draw() {
		
		currentCamera.drawScene();
		
		return this;
	}
	
	public void registerCameras(Camera defaultCamera) {
		registerCamera("default", defaultCamera);
		
	}
	
	public RenderManager registerCamera(String name, Camera camera) {
		cameras.put(name, camera);
		return this;
	}
	
	public RenderManager useCamera(String name) {
		currentCamera = cameras.get(name);
		return this;
	}
	
	public Camera getCamera(String name) {
		return cameras.get(name);
	}
	
	public Camera getCurrentCamera() {
		return currentCamera;
	}
	
	public ShaderProgram getShaderProgram() {
		return currentCamera.getShaderProgram();
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
	
	public LightManager getLightManager() {
		return lm;
	}
	
	private RenderManager initGL() {
		try {
			System.out.println("OpenGL v." + glGetString(GL_VERSION));
			
//			glEnable(GL_MULTISAMPLE);
//			glEnable(GL_LINE_SMOOTH);
			
			glEnable(GL_DEPTH_TEST);
			glDepthMask(true);
			
			glEnable(GL_DEPTH_CLAMP);
			
			glEnable(GL_CULL_FACE);
			glCullFace(GL_BACK);
			glFrontFace(GL_CW);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public RenderManager setFullsreen(boolean b) {
		try {
			if(b) {
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				Display.setFullscreen(b);
				adjustViewportToDisplay();
			} else {
				Display.setDisplayMode(new DisplayMode(Core.DEFAULT_WIDTH, Core.DEFAULT_HEIGHT));
				Display.setFullscreen(b);
				adjustViewportToDisplay();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public RenderManager adjustViewportToDisplay() {
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
	
		return this;
	}
	
	public RenderManager cleanUp() {
		em.cleanUp();
		lm.cleanUp();
		
		for(Camera c : cameras.values())
			c.cleanUp();
		
		return this;
	}
}