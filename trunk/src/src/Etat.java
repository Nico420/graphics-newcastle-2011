package src;

import java.io.IOException;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import tools.TextureLoader;

public abstract class Etat {
	boolean lighting = false;
	TextureLoader tl = new TextureLoader();
	
	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;
	
	public abstract int update(int delta) ;
	public abstract void renderGL() throws IOException;
	
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;

	}
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	
}
