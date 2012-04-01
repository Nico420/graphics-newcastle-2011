package state;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

import org.newdawn.slick.Color;

import src.SnakeGame;

/**
 * Displaying the Game Over.
 * 
 * @author Nicolas
 * 
 */
public class Perdu extends Etat {
	/** Title position. */
	private static final int TITLE_POSITION_Y = 50;

	/** Title position. */
	private static final int TITLE_POSITION_X = 200;

	/** Timer speed. */
	private static final float TIMER_SPEED = 0.0015f;

	/** Get the score from the main application. */
	private int score;

	/** Loosing timing. */
	private float perdu;

	/**
	 * Constructor.
	 * 
	 * @param pScore
	 *            Initial score
	 */
	public Perdu(int pScore) {
		super();
		this.score = pScore;
		this.perdu = 2;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int update(int delta) {
		perdu -= delta * TIMER_SPEED;
		updateFPS();
		if (perdu > 0) {
			return SnakeGame.PERDU;
		} else {
			return SnakeGame.MENU;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void renderGL() {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		fontTitre.drawString(SnakeGame.WIDTH / 2 - TITLE_POSITION_X,
				SnakeGame.HEIGHT / 2 - TITLE_POSITION_Y, "GAME OVER !",
				Color.red);
		fontMenu.drawString(SnakeGame.WIDTH / 2 - TITLE_POSITION_X,
				SnakeGame.HEIGHT / 2 + TITLE_POSITION_Y, "Final Score : "
						+ score, Color.yellow);
	}

	@Override
	public int pollInput() {
		return SnakeGame.PERDU;
	}

}
