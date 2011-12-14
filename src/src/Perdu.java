package src;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;

public class Perdu extends Etat {
	int score;
	public Perdu(int score) throws IOException {
		super();
		this.score= score;
		// TODO Auto-generated constructor stub
	}

	public float countDown = 3;

	@Override
	public int update(int delta) {
		countDown -= 0.0015f;
		updateFPS();
		if (countDown > 0)
			return SnakeGame.PERDU;
		else {
			return SnakeGame.MENU;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void renderGL() throws IOException {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		fontTitre.drawString(SnakeGame.WIDTH / 2 - 200,
					SnakeGame.HEIGHT / 2 - 50, "GAME OVER !",Color.red);
		fontMenu.drawString(SnakeGame.WIDTH / 2 - 200,
				SnakeGame.HEIGHT / 2 +50, "Final Score : "+score,Color.yellow);
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

}
