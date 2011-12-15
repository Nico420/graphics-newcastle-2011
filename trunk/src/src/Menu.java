package src;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Class for displaying and interacting with the Menu
 * @author Nicolas
 *
 */
@SuppressWarnings("deprecation")
public class Menu extends Etat {

	int menuChoice = 0;
	int menuChoiceTemp = 0;

	Texture icone;
	Texture pomme;
	
	/**
	 * Boolean flag on whether AntiAliasing is enabled or not
	 * 
	 * @throws IOException
	 */

	public Menu() throws IOException {
		super();
		initGL();
		icone = TextureLoader.getTexture("PNG",
				ResourceLoader.getResourceAsStream("texture/snake_icone.png"));
		pomme = TextureLoader.getTexture("PNG",
				ResourceLoader.getResourceAsStream("texture/pomme_ml.png"));
	}

	@Override
	public int update(int delta) {
		updateFPS();
		return menuChoice;
	}

	@Override
	public void renderGL() throws IOException {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		Color.white.bind();

		// font2.drawString(10, 10, "NICE LOOKING FONTS!", Color.green);
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

		fontTitre.drawString(200, 10, "SNAKE 3D", Color.red);

		// start.bind();
		fontMenu.drawString(200, 250, "START NEW GAME", Color.yellow);
		fontMenu.drawString(200, 300, "HIGH SCORE", Color.yellow);
		fontMenu.drawString(200, 350, "EXIT", Color.yellow);

	}

	@Override
	protected void initGL() throws IOException {
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
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

	public int pollInput() throws LWJGLException {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {

				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					menuChoiceTemp = (menuChoiceTemp - 1) % 3;
					if (menuChoiceTemp < 0)
						menuChoiceTemp = 2;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					menuChoiceTemp = (menuChoiceTemp + 1) % 3;
					if (menuChoiceTemp < 0)
						menuChoiceTemp = 2;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == Keyboard.KEY_NUMPADENTER) {
					menuChoice = menuChoiceTemp + 1;
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					if (Display.isFullscreen())
						Display.setFullscreen(false);
					else
						Display.setFullscreen(true);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					return SnakeGame.QUIT;
				}
			}
		}
		return SnakeGame.MENU;
	}

}