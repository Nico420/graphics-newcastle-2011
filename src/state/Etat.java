package state;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
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
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

import src.SnakeGame;

/**
 * Abstract class for states.
 * 
 * @author Nicolas
 * 
 */
public abstract class Etat {

	/** Font */
	private UnicodeFont font;

	/** Font */
	private UnicodeFont fontMenu;
	/** Font */
	private UnicodeFont fontPower;
	/** Font */
	private UnicodeFont fontTitre;

	/** last fps time */
	private long lastFPS;

	/** Game instance */
	private SnakeGame snakeGame;

	/**
	 * State constructor
	 * 
	 * @param pSnakeGame
	 *            game instance
	 */
	@SuppressWarnings("unchecked")
	public Etat(SnakeGame pSnakeGame) {
		setSnakeGame(pSnakeGame);
		// Font Creation
		initGL();
		// load font from file
		try {
			Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
			setFont(new UnicodeFont(awtFont));
			getFont().addAsciiGlyphs();
			getFont().getEffects().add(new ColorEffect(java.awt.Color.WHITE));
			getFont().loadGlyphs();

			InputStream inputStream = ResourceLoader
					.getResourceAsStream("texture/FOO.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(36f); // set font size
			setFontMenu(new UnicodeFont(awtFont2));
			getFontMenu().addAsciiGlyphs();
			getFontMenu().getEffects().add(
					new ColorEffect(java.awt.Color.WHITE));
			getFontMenu().loadGlyphs();
			awtFont2 = awtFont2.deriveFont(60f); // set font size
			setFontTitre(new UnicodeFont(awtFont2));
			getFontTitre().addAsciiGlyphs();
			getFontTitre().getEffects().add(
					new ColorEffect(java.awt.Color.WHITE));
			getFontTitre().loadGlyphs();
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			setFontPower(new UnicodeFont(awtFont2));
			getFontPower().addAsciiGlyphs();
			getFontPower().getEffects().add(
					new ColorEffect(java.awt.Color.WHITE));
			getFontPower().loadGlyphs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get font
	 * 
	 * @return Font
	 */
	public UnicodeFont getFont() {
		return font;
	}

	/**
	 * Get font
	 * 
	 * @return Font
	 */
	public UnicodeFont getFontMenu() {
		return fontMenu;
	}

	/**
	 * Get font
	 * 
	 * @return Font
	 */
	public UnicodeFont getFontPower() {
		return fontPower;
	}

	/**
	 * Get font
	 * 
	 * @return Font
	 */
	public UnicodeFont getFontTitre() {
		return fontTitre;
	}

	/**
	 * Get game instance
	 * 
	 * @return Game instance
	 */
	public SnakeGame getSnakeGame() {
		return snakeGame;
	}

	/**
	 * Get time
	 * 
	 * @return time
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * OpenGL initialisation
	 */
	protected void initGL() {
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
	 */
	public void pollInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					this.getSnakeGame().setEtat(new Menu(this.getSnakeGame()));
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					try {
						if (Display.isFullscreen()) {
							Display.setFullscreen(false);
						} else {
							Display.setFullscreen(true);
						}
					} catch (LWJGLException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	/**
	 * Drawing the state
	 */
	public abstract void renderGL();

	/**
	 * Set a new font
	 * 
	 * @param pFont
	 *            new font to set
	 */
	public void setFont(UnicodeFont pFont) {
		this.font = pFont;
	}

	/**
	 * Set a new font
	 * 
	 * @param pFontMenu
	 *            new font to set
	 */
	public void setFontMenu(UnicodeFont pFontMenu) {
		this.fontMenu = pFontMenu;
	}

	/**
	 * Set a new font
	 * 
	 * @param pFontPower
	 *            new font to set
	 */
	public void setFontPower(UnicodeFont pFontPower) {
		this.fontPower = pFontPower;
	}

	/**
	 * Set a new font
	 * 
	 * @param pFontTitre
	 *            new font to set
	 */
	public void setFontTitre(UnicodeFont pFontTitre) {
		this.fontTitre = pFontTitre;
	}

	/**
	 * Set a game
	 * 
	 * @param pSnakeGame
	 *            Game to set.
	 */
	public void setSnakeGame(SnakeGame pSnakeGame) {
		this.snakeGame = pSnakeGame;
	}

	/**
	 * Updtaing the state
	 * 
	 * @param delta
	 *            Delta
	 * @throws LWJGLException
	 *             Exception
	 */
	public void update(int delta) throws LWJGLException {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					this.getSnakeGame().setEtat(this);
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_A) {
				if (Display.isFullscreen()) {
					Display.setFullscreen(false);
				} else {
					Display.setFullscreen(true);
				}
			}
		}
	}

	/**
	 * Update fps
	 */
	protected void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			lastFPS += 1000;
		}

	}

}
