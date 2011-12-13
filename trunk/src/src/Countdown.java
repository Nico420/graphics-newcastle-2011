package src;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.newdawn.slick.Color;

public class Countdown extends Etat {

	public Countdown() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

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

	@SuppressWarnings("deprecation")
	@Override
	public void renderGL() throws IOException {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		int number = (int) Math.ceil(countDown);
		Color.green.bind();
		if (number >= 1)
			fontMenu.drawString(SnakeGame.WIDTH/2 -10,SnakeGame.HEIGHT/2-10 , number + " !");
		else
			fontMenu.drawString(SnakeGame.WIDTH/2 -50, SnakeGame.HEIGHT/2-10, "START !");
	}

}
