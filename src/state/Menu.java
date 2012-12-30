package state;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import src.SnakeGame;

/**
 * Class for displaying and interacting with the Menu.
 * 
 * @author Nicolas
 * 
 */
public class Menu extends Etat {

	/** Max menu choices. */
	private static final int MAX_MENU_CHOICE = 4;

	/** Icon. */
	private Texture icone;
	/** Current menu choice. */
	private int menuChoice = 0;

	/** Temp menu choice. */
	private int menuChoiceTemp = 0;
	/** Apple texture. */
	private Texture pomme;

	/**
	 * Boolean flag on whether AntiAliasing is enabled or not.
	 * 
	 * @param snakeGame
	 *            Associated game.
	 */

	public Menu(SnakeGame snakeGame) {
		super(snakeGame);
		initGL();
		try {
			icone = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("texture/snake_icone.png"));
			pomme = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("texture/pomme_ml.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Keyboard reaction.
	 * 
	 */
	public void pollInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {

				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					menuChoiceTemp = (menuChoiceTemp - 1) % MAX_MENU_CHOICE;
					if (menuChoiceTemp < 0) {
						menuChoiceTemp = 3;
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					menuChoiceTemp = (menuChoiceTemp + 1) % MAX_MENU_CHOICE;
					if (menuChoiceTemp < 0) {
						menuChoiceTemp = 2;
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN
						|| Keyboard.getEventKey() == Keyboard.KEY_NUMPADENTER) {
					menuChoice = menuChoiceTemp + 1;
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					try {
						if (Display.isFullscreen()) {
							Display.setFullscreen(false);
						} else {
							Display.setFullscreen(true);
						}
					} catch (LWJGLException e) {
						e.printStackTrace();
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					this.getSnakeGame().setExit(true);
				}
			}
		}
	}

	@Override
	public void renderGL() {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		Color.white.bind();

		// Title

		// Creating and using texture

		// Selector
		icone.bind();
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2d(1, 0);
		glVertex3f(10, 10, 0);
		glTexCoord2d(1, 1);
		glVertex3f(10, 80, 0);
		glTexCoord2d(0, 1);
		glVertex3f(80, 80, 0);
		glTexCoord2d(0, 0);
		glVertex3f(80, 10, 0);
		glEnd();

		pomme.bind();
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2d(1, 0);
		glVertex3f(140, 250 + 50 * menuChoiceTemp, 0);
		glTexCoord2d(1, 1);
		glVertex3f(140, 300 + 50 * menuChoiceTemp, 0);
		glTexCoord2d(0, 1);
		glVertex3f(190, 300 + 50 * menuChoiceTemp, 0);
		glTexCoord2d(0, 0);
		glVertex3f(190, 250 + 50 * menuChoiceTemp, 0);
		glEnd();

		getFontTitre().drawString(200, 10, "SNAKE 3D", Color.red);

		// start.bind();
		getFontMenu().drawString(200, 250, "1 Player", Color.yellow);
		getFontMenu().drawString(200, 300, "2 Players", Color.yellow);
		getFontMenu().drawString(200, 350, "HIGH SCORE", Color.yellow);
		getFontMenu().drawString(200, 400, "EXIT", Color.yellow);

	}

	@Override
	public void update(int delta) {
		updateFPS();
		switch (menuChoice) {
		case 1:
			this.getSnakeGame().setEtat(new EnterName(this.getSnakeGame(), 1));
			break;
		case 2:
			this.getSnakeGame().setEtat(new EnterName(this.getSnakeGame(), 2));
			break;
		case 3:
			this.getSnakeGame().setEtat(new HighScore(this.getSnakeGame()));
			break;
		case 4:
			this.getSnakeGame().setExit(true);
			break;
		default:
			break;
		}
	}

}