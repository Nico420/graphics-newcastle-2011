package src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import src.SnakeGame;
import tools.Position;

public class Apple extends Eatable {

	private float rotation;
	private float bounce;
	private boolean direction = false;

	public Apple() {
		super(0,0, Color.GREEN, Eatable.GROW_UP);
		while((new Position(x,y)).checkCollapse(SnakeGame.walls, SnakeGame.WALL_SIZE)){
		x = (float) (-(SnakeGame.MAP_SIZE-SnakeGame.APPLE_SIZE) + (SnakeGame.MAP_SIZE * 2 - SnakeGame.APPLE_SIZE)
				* Math.random());
		y = (float) (-(SnakeGame.MAP_SIZE-SnakeGame.APPLE_SIZE) + (SnakeGame.MAP_SIZE * 2 - SnakeGame.APPLE_SIZE)
				* Math.random());	
		}
	}

	public Apple(float x, float y) {
		super(x, y, Color.GREEN, Eatable.GROW_UP);
	}

	public void draw() {
		float a = SnakeGame.APPLE_SIZE / 2;
		glPushMatrix();
		glLoadIdentity();
		glRotatef(20, 0, 1, 0);
		glRotatef(-20, 1, 0, 0);

		// Make the apple bounce !
		glTranslatef(x, y, -bounce);
		if (!direction)
			bounce = (float) ((bounce + 0.001) % 2);
		else
			bounce = (float) ((bounce - 0.001) % 2);
		if (bounce < 0.10) {
			direction = false;
		} else if (bounce > 1.90) {
			direction = true;
		}

		// Make the apple turn !
		glRotatef(rotation, 0, 0, 1);
		rotation+=0.05;
		rotation%=360;
		/*
		 * glBegin(GL_SPHERE_MAP);
		 * 
		 * glEnd();
		 */
		float xTemp = x;
		float yTemp = y;
		x = 0;
		y = 0;
		glBegin(GL_QUADS);
		// Apple base
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y - a, 0);
		glVertex3f(x + a, y - a, 0);
		glVertex3f(x + a, y + a, 0);
		glVertex3f(x - a, y + a, 0);
		// Apple sides down
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y - a, 0);
		glColor3f(0, 1, 0);
		glVertex3f(x - SnakeGame.APPLE_SIZE, y - SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glVertex3f(x - SnakeGame.APPLE_SIZE, y + SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y + a, 0);
		glVertex3f(x + a, y - a, 0);
		glColor3f(0, 1, 0);
		glVertex3f(x + SnakeGame.APPLE_SIZE, y - SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glVertex3f(x + SnakeGame.APPLE_SIZE, y + SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x + a, y + a, 0);

		glVertex3f(x - a, y - a, 0);
		glColor3f(0, 1, 0);
		glVertex3f(x - SnakeGame.APPLE_SIZE, y - SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glVertex3f(x + SnakeGame.APPLE_SIZE, y - SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x + a, y - a, 0);
		glVertex3f(x - a, y + a, 0);
		glColor3f(0, 1, 0);
		glVertex3f(x - SnakeGame.APPLE_SIZE, y + SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glVertex3f(x + SnakeGame.APPLE_SIZE, y + SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x + a, y + a, 0);

		// Apple sides up

		glColor3f(0, 1, 0);
		glVertex3f(x - SnakeGame.APPLE_SIZE, y - SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, -SnakeGame.APPLE_SIZE * 2);
		glColor3f(0, 1, 0);
		glVertex3f(x - SnakeGame.APPLE_SIZE, y + SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glVertex3f(x + SnakeGame.APPLE_SIZE, y - SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x + a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -SnakeGame.APPLE_SIZE * 2);
		glColor3f(0, 1, 0);
		glVertex3f(x + SnakeGame.APPLE_SIZE, y + SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);

		glVertex3f(x - SnakeGame.APPLE_SIZE, y - SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glColor3f(0, 1, 0);
		glVertex3f(x + SnakeGame.APPLE_SIZE, y - SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glVertex3f(x - SnakeGame.APPLE_SIZE, y + SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y + a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -SnakeGame.APPLE_SIZE * 2);
		glColor3f(0, 1, 0);
		glVertex3f(x + SnakeGame.APPLE_SIZE, y + SnakeGame.APPLE_SIZE,
				-SnakeGame.APPLE_SIZE);

		// Apple top
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, -SnakeGame.APPLE_SIZE * 2);

		a /= 2;
		// Apple "snail"
		glColor3f(0.5f, 0.2f, 0f);
		// Bottom
		glVertex3f(x - a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, -SnakeGame.APPLE_SIZE * 2);
		// Sides
		glVertex3f(x - a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x - a, y - a, -SnakeGame.APPLE_SIZE * 2 - 1);
		glVertex3f(x + a, y - a, -SnakeGame.APPLE_SIZE * 2 - 1);

		glVertex3f(x - a, y + a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, -SnakeGame.APPLE_SIZE * 2 - 1);
		glVertex3f(x + a, y + a, -SnakeGame.APPLE_SIZE * 2 - 1);

		glVertex3f(x + a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, -SnakeGame.APPLE_SIZE * 2 - 1);
		glVertex3f(x + a, y + a, -SnakeGame.APPLE_SIZE * 2 - 1);

		glVertex3f(x - a, y - a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, -SnakeGame.APPLE_SIZE * 2);
		glVertex3f(x - a, y - a, -SnakeGame.APPLE_SIZE * 2 - 1);
		glVertex3f(x - a, y + a, -SnakeGame.APPLE_SIZE * 2 - 1);

		// Top
		glVertex3f(x - a, y - a, -SnakeGame.APPLE_SIZE * 2 - 1);
		glVertex3f(x + a, y - a, -SnakeGame.APPLE_SIZE * 2 - 1);
		glVertex3f(x + a, y + a, -SnakeGame.APPLE_SIZE * 2 - 1);
		glVertex3f(x - a, y + a, -SnakeGame.APPLE_SIZE * 2 - 1);
		glEnd();
		glPopMatrix();

		x = xTemp;
		y = yTemp;

	}

}
