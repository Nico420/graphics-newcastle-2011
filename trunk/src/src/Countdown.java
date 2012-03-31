package src;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glDisable;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;

/**
 * Count down before the game display.
 * 
 * @author Nicolas
 * 
 */
public class Countdown extends Etat {

	/** Alpha color. */
	private static final float ALPHA_COLOR = 0.5f;

	/** String offset. */
	private static final int OFFSET_2 = 60;

	/** String offset. */
	private static final int OFFSET = 10;

	/** Countdown time. */
	private static final int COUNTDOWN_TIME = 3;

	/** Countdown speed. */
	private static final float COUNTDOWN_SPEED = 0.0015f;

	/** Starting value for the countdown. */
	private float countDown;

	/**
	 * Starting the game, countdown screen.
	 */
	public Countdown() {
		super();
		countDown = COUNTDOWN_TIME;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int update(int delta) {
		countDown -= delta * COUNTDOWN_SPEED;
		updateFPS();
		if (countDown > 0) {
			return SnakeGame.COUNTDOWN;
		} else {
			return SnakeGame.GAME;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void renderGL() {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		int number = (int) Math.ceil(countDown);
		Color.green.bind();
		if (number >= 1) {
			fontMenu.drawString(SnakeGame.WIDTH / 2 - OFFSET, SnakeGame.HEIGHT
					/ 2 - OFFSET, number + " !");
		} else {
			fontMenu.drawString(SnakeGame.WIDTH / 2 - OFFSET_2,
					SnakeGame.HEIGHT / 2 - OFFSET, "START !", Color.green);
		}
	}

	@Override
	protected void initGL() {
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.0f, 0.0f, ALPHA_COLOR); // Black Background
		glClearDepth(1.0f); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Really Nice
															// Perspective
															// Calculations
		glEnable(GL_TEXTURE_2D);

		glDisable(GL_LIGHTING);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluOrtho2D(0, SnakeGame.WIDTH, SnakeGame.HEIGHT, 0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

	}

	@Override
	public int pollInput() {
		try {
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
						return SnakeGame.MENU;
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
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SnakeGame.COUNTDOWN;
	}

}
