package aatr.engine.gfx;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public final class Window {
	
	private static int fpsCap = 120;
	private static boolean vsync = true;
	private static boolean running = false;
	
	private static int width, height;
	
	public static final void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public static final void swapBuffers() {
		Display.update();
		if(!vsync)
			Display.sync(fpsCap);
		
		if(Display.isCloseRequested())
			stop();
		
	}
	
	public static final void adjustViewport(int x, int y, int width, int height) {
		glViewport(x, y, width, height);
	}
	
	public static final void adjustViewport() {
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	public static final boolean isFullscreen() {
		return Display.isFullscreen();
	}
	
	public static final void setFullscreen(boolean fullscreen) throws LWJGLException {
		if(fullscreen)
			Display.setDisplayMode(Display.getDesktopDisplayMode());
		Display.setDisplayMode(new DisplayMode(width, height));
		Display.setFullscreen(fullscreen);
		adjustViewport();
	}
	
	public static final void create(String title, int width, int height) throws LWJGLException {
		setTitle(title);
		setDimensions(width, height, false);
		Display.create();
		running = true;
		enableVSync(vsync);
		
		//OpenGL Initialization
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static final String getTitle() {
		return Display.getTitle();
	}
	
	public static final void setTitle(String title) {
		Display.setTitle(title);
	}
	
	public static final int getWidth() {
		return Display.getWidth();
	}
	
	public static final void setWidth(int width, boolean adjust) {
		try {
			Display.setDisplayMode(new DisplayMode(width, Display.getHeight()));
			
			Window.width = width;
			
			if(adjust)
				adjustViewport();
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static final int getHeight() {
		return Display.getHeight();
	}
	
	public static final void setHeight(int height, boolean adjust) {
		try {
			Display.setDisplayMode(new DisplayMode(Display.getWidth(), height));
			
			Window.height = height;
			
			if(adjust)
				adjustViewport();
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static final void setDimensions(int width, int height, boolean adjust) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			
			Window.width = width;
			Window.height = height;
			
			if(adjust)
				adjustViewport();
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static final int getFPSCap() {
		return fpsCap;
	}
	
	public static final void setFPSCap(int fpsCap) {
		Window.fpsCap = fpsCap;
	}
	
	public static final boolean hasVSync() {
		return vsync;
	}
	
	public static final void enableVSync(boolean b) {
		vsync = b;
		Display.setVSyncEnabled(b);
	}
	
	public static final void stop() {
		running = false;
	}
	
	public static final void destroy() {
		Display.destroy();
	}
	
	public static final boolean isRunning() {
		return running;
	}
	
}