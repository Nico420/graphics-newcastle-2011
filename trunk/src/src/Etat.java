package src;

import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glShadeModel;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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


@SuppressWarnings("deprecation")
public abstract class Etat {
	boolean lighting = false;
	TextureLoader tl = new TextureLoader();
	
	private float lightAmbient[] = { 0.5f, 0.5f, 0.5f, 1.0f };  // Ambient Light Values ( NEW )
    private float lightDiffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };      // Diffuse Light Values ( NEW )
    private float lightPosition[] = { 0.0f, 0.0f, 2.0f, 1.0f }; // Light Position ( NEW )
    
	protected TrueTypeFont font;
	protected TrueTypeFont fontMenu;
	protected TrueTypeFont fontTitre;
	private boolean antiAlias = true;
	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;
	
	public Etat() throws IOException{
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int update(int delta) throws LWJGLException{
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					return SnakeGame.MENU;
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_A) {
				System.out.println("A Key Pressed");
				if (Display.isFullscreen())
					Display.setFullscreen(false);
				else
					Display.setFullscreen(true);
			}
		}
		
		return -1;
	}
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
	
	private void initGL() throws IOException {
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		glClearDepth(1.0f); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Really Nice
															// Perspective
															// Calculations
		glEnable(GL_COLOR_MATERIAL);
		glEnable(GL_LIGHTING);
		glEnable(GL_TEXTURE_2D);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SnakeGame.WIDTH, SnakeGame.HEIGHT, 0, 100, -100);
		GLU.gluPerspective(0.0f, (float) Display.getWidth() / (float) Display.getWidth(),0.1f,100.0f);

		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);

		
		glMatrixMode(GL_MODELVIEW);
		ByteBuffer temp = ByteBuffer.allocateDirect(16);
        temp.order(ByteOrder.nativeOrder());
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, (FloatBuffer)temp.asFloatBuffer().put(lightAmbient).flip());              // Setup The Ambient Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, (FloatBuffer)temp.asFloatBuffer().put(lightDiffuse).flip());              // Setup The Diffuse Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION,(FloatBuffer)temp.asFloatBuffer().put(lightPosition).flip());         // Position The Light
        GL11.glEnable(GL11.GL_LIGHT1);
	}
	
	
}
