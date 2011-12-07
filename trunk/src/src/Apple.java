package src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import src.Snake;

public class Apple extends Eatable {

	public Apple() {
		super((float) (-Snake.MAP_SIZE + Snake.MAP_SIZE * 2 * Math.random()),
				(float) (-Snake.MAP_SIZE + Snake.MAP_SIZE * 2 * Math.random()),
				Color.GREEN, Eatable.GROW_UP);
	}

	public Apple(float x, float y) {
		super(x, y, Color.GREEN, Eatable.GROW_UP);
	}

	public void draw() {
		//Snake.draw3DQuad(x, y, 0, Snake.APPLE_SIZE);
		int z =0;
		float a = Snake.APPLE_SIZE/2;
		glPushMatrix();
		//Draw the apple, la faire tourner ?? (static int)
		/*glBegin(GL_SPHERE_MAP);
		
		glEnd();*/
		glBegin(GL_QUADS);
		glVertex3f(x - a, y - a, z - a);
		glVertex3f(x + a, y - a, z - a);
		glVertex3f(x + a, y + a, z - a);
		glVertex3f(x - a, y + a, z - a);
		glVertex3f(x - a, y - a, z + a);
		glVertex3f(x + a, y - a, z + a);
		glVertex3f(x + a, y + a, z + a);
		glVertex3f(x - a, y + a, z + a);
		glVertex3f(x - a, y - a, z + a);
		glVertex3f(x + a, y - a, z + a);
		glVertex3f(x + a, y - a, z - a);
		glVertex3f(x - a, y - a, z - a);
		glVertex3f(x + a, y + a, z + a);
		glVertex3f(x - a, y + a, z + a);
		glVertex3f(x - a, y + a, z - a);
		glVertex3f(x + a, y + a, z - a);
		glEnd();
		glPopMatrix();
		
		
	}

}
