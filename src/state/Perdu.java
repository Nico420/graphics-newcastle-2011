package state;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.newdawn.slick.Color;

import src.SnakeGame;

/**
 * Displaying the Game Over.
 * 
 * @author Nicolas
 * 
 */
public class Perdu extends Etat {
	/** Timer speed. */
	private static final float TIMER_SPEED = 0.0015f;

	/** Title position. */
	private static final int TITLE_POSITION_X = 200;

	/** Title position. */
	private static final int TITLE_POSITION_Y = 50;

	/** Loosing timing. */
	private float perdu;

	/** Get the score from the main application. */
	private int score;

	/**
	 * Constructor.
	 * 
	 * @param pScore
	 *            Initial score
	 * @param snakeGame
	 *            Associated game
	 */
	public Perdu(int pScore, SnakeGame snakeGame) {
		super(snakeGame);
		this.score = pScore;
		this.perdu = 2;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void pollInput() {
	}

	@Override
	public void renderGL() {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		getFontTitre().drawString(SnakeGame.WIDTH / 2 - TITLE_POSITION_X,
				SnakeGame.HEIGHT / 2 - TITLE_POSITION_Y, "GAME OVER !",
				Color.red);
		getFontMenu().drawString(SnakeGame.WIDTH / 2 - TITLE_POSITION_X,
				SnakeGame.HEIGHT / 2 + TITLE_POSITION_Y, "Final Fichier : "
						+ score, Color.yellow);
	}

	@Override
	public void update(int delta) {
		perdu -= delta * TIMER_SPEED;
		updateFPS();
		if (perdu > 0) {
			this.getSnakeGame().setEtat(new Menu(this.getSnakeGame()));
		}

	}

}
