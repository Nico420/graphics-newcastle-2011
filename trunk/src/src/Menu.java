package src;

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

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


@SuppressWarnings("deprecation")
public class Menu extends Etat {

	int menuChoice = 0;
	int menuChoiceTemp = 0;

	
	/** Boolean flag on whether AntiAliasing is enabled or not */

	
	public Menu(){
		super();
	}
	@Override
	public int update(int delta) {
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
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					menuChoice = menuChoiceTemp + 1;
				}
			}
		}
		updateFPS();
		return menuChoice;
	}

	@Override
	public void renderGL() throws IOException {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		Color.white.bind();

		
		//font2.drawString(10, 10, "NICE LOOKING FONTS!", Color.green);
		// Title

		// Creating and using texture
		Texture icone = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("texture/snake_icone.png"));
		Texture pomme = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("texture/pomme_ml.png"));
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

		fontTitre.drawString(200, 10, "SNAKE 3D",
				Color.red);

		//start.bind();
		fontMenu.drawString(200, 250, "START NEW GAME",
				Color.yellow);
		fontMenu.drawString(200, 300, "HIGH SCORE",
				Color.yellow);
		fontMenu.drawString(200, 350, "EXIT",
				Color.yellow);

	}

}