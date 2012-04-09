package state;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import src.SnakeGame;

/**
 * Count down before the game display.
 * 
 * @author Nicolas
 * 
 */
public class Countdown extends Etat {

	/** Countdown speed. */
	private static final float COUNTDOWN_SPEED = 0.0015f;

	/** Countdown time. */
	private static final int COUNTDOWN_TIME = 3;

	/** String offset. */
	private static final int OFFSET = 10;

	/** String offset. */
	private static final int OFFSET_2 = 60;

	/** Starting value for the countdown. */
	private float countDown;

	/**
	 * Starting the game, countdown screen.
	 * @param snakeGame 
	 */
	public Countdown(SnakeGame snakeGame) {
		super(snakeGame);
		countDown = COUNTDOWN_TIME;
	}

	@Override
	public void pollInput() {
		try {
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
						this.getSnakeGame().setEtat(new Menu(this.getSnakeGame()));
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
			e.printStackTrace();
		}
	}

	@Override
	public void renderGL() {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		int number = (int) Math.ceil(countDown);
		Color.green.bind();
		if (number >= 1) {
			getFontMenu().drawString(SnakeGame.WIDTH / 2 - OFFSET, SnakeGame.HEIGHT
					/ 2 - OFFSET, number + " !");
		} else {
			getFontMenu().drawString(SnakeGame.WIDTH / 2 - OFFSET_2,
					SnakeGame.HEIGHT / 2 - OFFSET, "START !", Color.green);
		}
	}


	@Override
	public void update(int delta) {
		countDown -= delta * COUNTDOWN_SPEED;
		updateFPS();
		if (countDown <= 0.9) {
			this.getSnakeGame().setEtat(new Game(this.getSnakeGame()));
		}

	}

}
