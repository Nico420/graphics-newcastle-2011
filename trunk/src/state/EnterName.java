package state;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import src.SnakeGame;

/**
 * Count down before the game display.
 * 
 * @author Nicolas
 * 
 */
public class EnterName extends Etat {

	/** Max size for name */
	private static final int TAILLE_MAX_PRENOM = 10;
	/** Text */
	private String text;
	/** String offset. */
	private static final int OFFSET = 10;

	/**
	 * Starting the game, Enter snake name
	 * 
	 * @param snakeGame
	 *            Game instace
	 */
	public EnterName(SnakeGame snakeGame) {
		super(snakeGame);
		text = "";
		// TODO Auto-generated constructor stub
	}

	@Override
	public void pollInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN
						|| Keyboard.getEventKey() == Keyboard.KEY_NUMPADENTER) {
					this.getSnakeGame().setEtat(new Game(this.getSnakeGame()));
				} else if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					this.getSnakeGame().setEtat(new Menu(this.getSnakeGame()));
				} else if (Keyboard.getEventKey() == Keyboard.KEY_BACK) {
					if (text.length() > 0) {
						text = text.substring(0, text.length() - 1);
					}
				} else {
					if (text.length() <= TAILLE_MAX_PRENOM - 1) {
						text += Keyboard.getEventCharacter();
					}

				}
			}

		}
	}

	@Override
	public void renderGL() {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		Color.green.bind();
		getFont().drawString(SnakeGame.WIDTH / 2 - OFFSET - 50,
				SnakeGame.HEIGHT / 2 - OFFSET - 20, "Player : ");
		getFont().drawString(SnakeGame.WIDTH / 2 - OFFSET - text.length() * 10,
				SnakeGame.HEIGHT / 2 - OFFSET, text);
	}

	@Override
	public void update(int delta) {
		updateFPS();
	}

}
