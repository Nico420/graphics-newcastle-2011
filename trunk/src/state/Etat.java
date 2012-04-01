package state;

import java.awt.Font;
import java.io.InputStream;
import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;

import org.newdawn.slick.util.ResourceLoader;

import src.SnakeGame;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glDisable;

import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
/**
 * Abstract class for states.
 * 
 * @author Nicolas
 * 
 */
@SuppressWarnings("deprecation")
public abstract class Etat {
	boolean lighting = false;
	TextureLoader tl = new TextureLoader();

	// ----------- Variables added for Lighting Test -----------//
	protected FloatBuffer matSpecular;
	protected FloatBuffer lightPosition;
	protected FloatBuffer whiteLight;
	protected FloatBuffer lModelAmbient;
	// ----------- END: Variables added for Lighting Test -----------//

	protected TrueTypeFont font;
	protected TrueTypeFont fontMenu;
	protected TrueTypeFont fontTitre;
	protected TrueTypeFont fontPower;

	private boolean antiAlias = true;
	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	public Etat() {
		// Font Creation
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, antiAlias);
		initGL();
		// load font from file
		try {
			InputStream inputStream = ResourceLoader
					.getResourceAsStream("texture/FOO.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(36f); // set font size
			fontMenu = new TrueTypeFont(awtFont2, antiAlias);
			awtFont2 = awtFont2.deriveFont(60f); // set font size
			fontTitre = new TrueTypeFont(awtFont2, antiAlias);
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			fontPower = new TrueTypeFont(awtFont2, antiAlias);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updtaing the state
	 * 
	 * @param delta
	 * @return New state to use
	 * @throws LWJGLException
	 */
	public int update(int delta) throws LWJGLException {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					return SnakeGame.MENU;
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_A) {
				if (Display.isFullscreen())
					Display.setFullscreen(false);
				else
					Display.setFullscreen(true);
			}
		}

		return -1;
	}

	/**
	 * Drawing the state
	 */
	public abstract void renderGL();

	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			fps = 0;
			lastFPS += 1000;
		}
		fps++;

	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * OpenGL initialisation
	 */
	protected void initGL(){
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		glClearDepth(1.0f); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Really Nice
															// Perspective
															// Calculations
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_LIGHTING);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluOrtho2D(0, SnakeGame.WIDTH, SnakeGame.HEIGHT, 0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	/**
	 * Keyboard reading
	 * 
	 * @return new state to use
	 */
	public int pollInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					return SnakeGame.MENU;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					try {
						if (Display.isFullscreen())
							Display.setFullscreen(false);
						else
							Display.setFullscreen(true);
					} catch (LWJGLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		return -1;
	}

}
