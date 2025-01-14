package state;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import src.SnakeGame;
import tools.Fichier;

/**
 * State High Fichier, displaying the highScore.
 * 
 * @author Nicolas
 * 
 */
public class HighScore extends Etat {

	/** Offset. */
	private static final int OFFSET = 35;

	/** Fichier starting postion. */
	private static final int SCORE_POSITION_X = 250;

	/** Fichier starting position. */
	private static final int SCORE_STARTING_POSITION_Y = 230;

	/** Title position. */
	private static final int TITLE_POSITION_X = 200;

	/** Title position. */
	private static final int TITLE_POSITION_Y = 10;

	/** Title 2 position. */
	private static final int TITLE2_POSITION_X = 100;

	/** Title 2 position. */
	private static final int TITLE2_POSITION_Y = 110;

	/** Fichier list. */
	private ArrayList<String> score;

	/**
	 * Construct highscore state.
	 * 
	 * @param snakeGame
	 *            Game instance
	 */
	public HighScore(SnakeGame snakeGame) {
		super(snakeGame);
		initGL();
		score = Fichier.getScore("highscore");
	}

	@Override
	public void pollInput() {
		while (Keyboard.next()) {

			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					setGameFullScreen();
				} else {
					this.getSnakeGame().setEtat(new Menu(this.getSnakeGame()));
				}
			}
		}
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
