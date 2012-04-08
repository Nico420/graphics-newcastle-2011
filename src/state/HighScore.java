package state;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

import java.util.LinkedList;

import org.newdawn.slick.Color;

import src.SnakeGame;
import tools.Fichier;

/**
 * State High Score, displaying the highScore.
 * 
 * @author Nicolas
 * 
 */
public class HighScore extends Etat {

	/** Offset. */
	private static final int OFFSET = 35;

	/** Score starting postion. */
	private static final int SCORE_POSITION_X = 250;

	/** Score starting position. */
	private static final int SCORE_STARTING_POSITION_Y = 230;

	/** Title position. */
	private static final int TITLE_POSITION_X = 200;

	/** Title position. */
	private static final int TITLE_POSITION_Y = 10;

	/** Title 2 position. */
	private static final int TITLE2_POSITION_X = 100;

	/** Title 2 position. */
	private static final int TITLE2_POSITION_Y = 110;

	/** Score list. */
	private LinkedList<Integer> score;

	/**
	 * Construct highscore state.
	 * 
	 * @param snakeGame Game instance
	 */
	public HighScore(SnakeGame snakeGame) {
		super(snakeGame);
		initGL();
		score = Fichier.getScore("highScore.txt");
	}

	@Override
	public void pollInput() {
		super.pollInput();
	}

	@Override
	public void renderGL() {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		Color.white.bind();

		getFontTitre().drawString(TITLE_POSITION_X, TITLE_POSITION_Y,
				"SNAKE 3D", Color.red);
		getFontTitre().drawString(TITLE2_POSITION_X, TITLE2_POSITION_Y,
				"HIGH SCORE TABLE", Color.green);
		for (int i = 0; i < score.size(); i++) {
			getFontMenu().drawString(SCORE_POSITION_X,
					SCORE_STARTING_POSITION_Y + OFFSET * i,
					(i + 1) + " - " + score.get(i), Color.yellow);
		}

	}

	@Override
	public void update(int delta) {
		updateFPS();
	}

}
