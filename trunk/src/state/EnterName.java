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
	/** Actual player */
	private int actualPlayer;
	/** Number of player */
	private int numberOfPlayers;
	/** String offset. */
	private static final int OFFSET = 10;
	/** Error string */
	private String error = "";
	/** Names' table */
	private String[] names;

	/**
	 * Starting the game, Enter snake name
	 * 
	 * @param snakeGame
	 *            Game instace
	 * @param pNumberOfPlayers
	 *            Number of player
	 */
	public EnterName(SnakeGame snakeGame, int pNumberOfPlayers) {
		super(snakeGame);
		numberOfPlayers = pNumberOfPlayers;
		names = new String[numberOfPlayers];
		actualPlayer = 0;
		text = "";
	}

	@Override
	public void pollInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN
						|| Keyboard.getEventKey() == Keyboard.KEY_NUMPADENTER) {
					if (!text.equals("")) {
						error = "";
						names[actualPlayer] = text;
						System.out.println(names[0] + " " + names[1]);
						if ((actualPlayer + 1) == numberOfPlayers) {
							this.getSnakeGame().setNomJoueur(names);
							this.getSnakeGame().setEtat(
									new Countdown(this.getSnakeGame()));
						} else {
							text = "";
							actualPlayer++;
						}
					} else {
						error = "Name can't be empty !";
					}
				} else if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					this.getSnakeGame().setEtat(new Menu(this.getSnakeGame()));
				} else if (Keyboard.getEventKey() == Keyboard.KEY_BACK) {
					if (text.length() > 0) {
						text = text.substring(0, text.length() - 1);
					}
				} else {
					if (text.length() <= TAILLE_MAX_PRENOM - 1) {
						String add = Keyboard.getEventCharacter() + "";
						if (add.matches("[A-Za-z0-9]")) {
							text += add;
						}
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
				SnakeGame.HEIGHT / 2 - OFFSET - 20,
				"Player " + (actualPlayer + 1) + " : ");
		getFont().drawString(SnakeGame.WIDTH / 2 - OFFSET,
				SnakeGame.HEIGHT / 2 - OFFSET, text);
		if (!error.equals("")) {
			getFont().drawString(SnakeGame.WIDTH / 2 - OFFSET,
					SnakeGame.HEIGHT / 2 - OFFSET + 40, error);
		}
	}

	@Override
	public void update(int delta) {
		updateFPS();
	}

}
