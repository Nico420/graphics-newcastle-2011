package src;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
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
			fontMenu.drawString(SnakeGame.WIDTH / 2 - 10,
					SnakeGame.HEIGHT / 2 - 10, number + " !");
		else
			fontMenu.drawString(SnakeGame.WIDTH / 2 - 60,
					SnakeGame.HEIGHT / 2 - 10, "START !",Color.green);
	}

	@Override
	protected void initGL() throws IOException {
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
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
		glViewport(0, 0, Display.getWidth(),
				Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluOrtho2D(0, SnakeGame.WIDTH, SnakeGame.HEIGHT, 0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

	}

	@Override
	public int pollInput() throws LWJGLException {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					return SnakeGame.MENU;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Pressed");
					if (Display.isFullscreen())
						Display.setFullscreen(false);
					else
						Display.setFullscreen(true);
				}
			}
			
		}
		return SnakeGame.COUNTDOWN;
	}

}
