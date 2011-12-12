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

import tools.Texture;

public class Menu extends Etat {
	
	public static int menuChoice = 0;
	public static int menuChoiceTemp = 0;

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
		/*
		 * float itemWidth = 80; float itemHeight = 10;
		 */

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		// Title

		// Creating and using texture
		Texture start = tl.getTexture("texture/start.png");
		Texture icone = tl.getTexture("texture/snake_icone.png");
		Texture pomme = tl.getTexture("texture/pomme_ml.png");
		// Selector
		icone.bind();
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2d(1, 0);
		glVertex3f(-90, -90, 0);
		glTexCoord2d(1, 1);
		glVertex3f(-90, -70, 0);
		glTexCoord2d(0, 1);
		glVertex3f(-70, -70, 0);
		glTexCoord2d(0, 0);
		glVertex3f(-70, -90, 0);
		glEnd();
		
		
		pomme.bind();
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2d(1, 0);
		glVertex3f(-50, -15 + 30 * menuChoiceTemp, 0);
		glTexCoord2d(1, 1);
		glVertex3f(-50, -5 + 30 * menuChoiceTemp, 0);
		glTexCoord2d(0, 1);
		glVertex3f(-60, -5 + 30 * menuChoiceTemp, 0);
		glTexCoord2d(0, 0);
		glVertex3f(-60, -15 + 30 * menuChoiceTemp, 0);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3f(-50, -80, 0);
		glVertex3f(-50, -50, 0);
		glVertex3f(50, -50, 0);
		glVertex3f(50, -80, 0);
		glEnd();

		
		start.bind();
		glBegin(GL_QUADS);
		// Start New game
		glTexCoord2d(0, 0);
		glVertex3f(-40, -20, 0);
		glTexCoord2d(0, 1);
		glVertex3f(-40, 0, 0);
		glTexCoord2d(1, 1);
		glVertex3f(40, 0, 0);
		glTexCoord2d(1, 0);
		glVertex3f(40, -20, 0);
		glEnd();

		glBegin(GL_QUADS);
		// Get HIgh Score
		glVertex3f(-40, 30, 0);
		glVertex3f(-40, 10, 0);
		glVertex3f(40, 10, 0);
		glVertex3f(40, 30, 0);
		glEnd();

		glBegin(GL_QUADS);
		// Exit
		glVertex3f(-40, 60, 0);
		glVertex3f(-40, 40, 0);
		glVertex3f(40, 40, 0);
		glVertex3f(40, 60, 0);
		glEnd();
		/*
		 * glVertex3f(-50, -80, 0); glVertex3f(-50, -60, 0); glVertex3f(50, -60,
		 * 0); glVertex3f(50, -80, 0);
		 */

	}

}
