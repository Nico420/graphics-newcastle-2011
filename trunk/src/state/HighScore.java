package state;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

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
	public int pollInput() {
		super.pollInput();
		return SnakeGame.HIGHSCORE;
	}

}
