package src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import src.Game;
import tools.Position;

public class Apple extends Eatable {

	private float rotation;
	private float bounce;
	private boolean direction = false;

	public Apple() {
		super(0,0, Color.GREEN, Eatable.GROW_UP);
		while((new Position(x,y)).checkCollapse(Game.walls, Game.WALL_SIZE, Game.APPLE_SIZE)){
		x = (float) (-(Game.MAP_SIZE-Game.APPLE_SIZE) + (Game.MAP_SIZE * 2 - Game.APPLE_SIZE)
				* Math.random());
		y = (float) (-(Game.MAP_SIZE-Game.APPLE_SIZE) + (Game.MAP_SIZE * 2 - Game.APPLE_SIZE)
				* Math.random());	
		}
		
	}

	public Apple(float x, float y) {
		super(x, y, Color.GREEN, Eatable.GROW_UP);
	}

	public void draw() {
		float a = Game.APPLE_SIZE / 2;
		glPushMatrix();
		glLoadIdentity();
		Game.setCamera();
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
		rotation+=0.3;
		rotation%=360;

		
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
		glVertex3f(x - Game.APPLE_SIZE, y - Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glVertex3f(x - Game.APPLE_SIZE, y + Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y + a, 0);
		glVertex3f(x + a, y - a, 0);
		glColor3f(0, 1, 0);
		glVertex3f(x + Game.APPLE_SIZE, y - Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glVertex3f(x + Game.APPLE_SIZE, y + Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x + a, y + a, 0);

		glVertex3f(x - a, y - a, 0);
		glColor3f(0, 1, 0);
		glVertex3f(x - Game.APPLE_SIZE, y - Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glVertex3f(x + Game.APPLE_SIZE, y - Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x + a, y - a, 0);
		glVertex3f(x - a, y + a, 0);
		glColor3f(0, 1, 0);
		glVertex3f(x - Game.APPLE_SIZE, y + Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glVertex3f(x + Game.APPLE_SIZE, y + Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x + a, y + a, 0);

		// Apple sides up

		glColor3f(0, 1, 0);
		glVertex3f(x - Game.APPLE_SIZE, y - Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, -Game.APPLE_SIZE * 2);
		glColor3f(0, 1, 0);
		glVertex3f(x - Game.APPLE_SIZE, y + Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glVertex3f(x + Game.APPLE_SIZE, y - Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x + a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -Game.APPLE_SIZE * 2);
		glColor3f(0, 1, 0);
		glVertex3f(x + Game.APPLE_SIZE, y + Game.APPLE_SIZE,
				-Game.APPLE_SIZE);

		glVertex3f(x - Game.APPLE_SIZE, y - Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, -Game.APPLE_SIZE * 2);
		glColor3f(0, 1, 0);
		glVertex3f(x + Game.APPLE_SIZE, y - Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glVertex3f(x - Game.APPLE_SIZE, y + Game.APPLE_SIZE,
				-Game.APPLE_SIZE);
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y + a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -Game.APPLE_SIZE * 2);
		glColor3f(0, 1, 0);
		glVertex3f(x + Game.APPLE_SIZE, y + Game.APPLE_SIZE,
				-Game.APPLE_SIZE);

		// Apple top
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, -Game.APPLE_SIZE * 2);

		a /= 2;
		// Apple "snail"
		glColor3f(0.5f, 0.2f, 0f);
		// Bottom
		glVertex3f(x - a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, -Game.APPLE_SIZE * 2);
		// Sides
		glVertex3f(x - a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y - a, -Game.APPLE_SIZE * 2 - 1);
		glVertex3f(x + a, y - a, -Game.APPLE_SIZE * 2 - 1);

		glVertex3f(x - a, y + a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, -Game.APPLE_SIZE * 2 - 1);
		glVertex3f(x + a, y + a, -Game.APPLE_SIZE * 2 - 1);

		glVertex3f(x + a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, -Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, -Game.APPLE_SIZE * 2 - 1);
		glVertex3f(x + a, y + a, -Game.APPLE_SIZE * 2 - 1);

		glVertex3f(x - a, y - a, -Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, -Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y - a, -Game.APPLE_SIZE * 2 - 1);
		glVertex3f(x - a, y + a, -Game.APPLE_SIZE * 2 - 1);

		// Top
		glVertex3f(x - a, y - a, -Game.APPLE_SIZE * 2 - 1);
		glVertex3f(x + a, y - a, -Game.APPLE_SIZE * 2 - 1);
		glVertex3f(x + a, y + a, -Game.APPLE_SIZE * 2 - 1);
		glVertex3f(x - a, y + a, -Game.APPLE_SIZE * 2 - 1);
		glEnd();
		glPopMatrix();

		x = xTemp;
		y = yTemp;

	}

}
