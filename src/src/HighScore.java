package src;

import static org.lwjgl.opengl.GL11.glClear;
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

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;

import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import tools.Fichier;

/**
 * State High Score, displaying the highScore.
 * 
 * @author Nicolas
 * 
 */
@SuppressWarnings("deprecation")
public class HighScore extends Etat {

	/** Offset. */
	private static final int OFFSET = 35;

	/** Score starting position. */
	private static final int SCORE_STARTING_POSITION_Y = 230;

	/** Score starting postion. */
	private static final int SCORE_POSITION_X = 250;

	/** Title 2 position. */
	private static final int TITLE2_POSITION_Y = 110;

	/** Title 2 position. */
	private static final int TITLE2_POSITION_X = 100;

	/** Title position. */
	private static final int TITLE_POSITION_Y = 10;

	/** Title position. */
	private static final int TITLE_POSITION_X = 200;

	/** Alpha color. */
	private static final float ALPHA_COLOR = 0.5f;

	/** Score list. */
	private LinkedList<Integer> score;

	/** Construct highscore state. */
	public HighScore() {
		super();
		initGL();
		score = Fichier.getScore("highScore.txt");
	}

	@Override
	public int update(int delta) {

		updateFPS();
		return SnakeGame.HIGHSCORE;
	}

	@Override
	public void renderGL() {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		Color.white.bind();

		fontTitre.drawString(TITLE_POSITION_X, TITLE_POSITION_Y, "SNAKE 3D",
				Color.red);
		fontTitre.drawString(TITLE2_POSITION_X, TITLE2_POSITION_Y,
				"HIGH SCORE TABLE", Color.green);
		for (int i = 0; i < score.size(); i++) {
			fontMenu.drawString(SCORE_POSITION_X, SCORE_STARTING_POSITION_Y
					+ OFFSET * i, (i + 1) + " - " + score.get(i), Color.yellow);
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

	@Override
	public int pollInput() {
		super.pollInput();
		return SnakeGame.HIGHSCORE;
	}

}
