package src;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.io.IOException;

import org.newdawn.slick.Color;

public class Countdown extends Etat {

	public float countDown = 3;

	@Override
	public int update(int delta) {
		countDown -= 0.0015f;
		updateFPS();
		if (countDown > 0)
			return SnakeGame.COUNTDOWN;
		else {
			return SnakeGame.GAME;
		}

	}

	@Override
	public void renderGL() throws IOException {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		int number = (int) Math.ceil(countDown);
		Color.green.bind();
		if (number >= 1)
			fontMenu.drawString(SnakeGame.WIDTH/2 -10,SnakeGame.HEIGHT/2-10 , number + " !");
		else
			fontMenu.drawString(SnakeGame.WIDTH/2 -30, SnakeGame.HEIGHT/2-10, "START !");
	}

}
