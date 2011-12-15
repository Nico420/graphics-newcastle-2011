package src;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.awt.Color;
import tools.Position;

public class Eatable {

	public static final int GROW_UP = 1;
	public static final int REDUCE = 2;
	public static final int SLOW = 3;
	public static final int MULTI = 4;

	protected float rotation;
	protected float bounce;
	protected boolean direction = false;

	public float x;
	public float y;

	public Color color;
	public int action;

	/**
	 * @param x
	 * @param y
	 * @param color
	 * @param action
	 */
	public Eatable(float x, float y, Color color, int action) {
		super();
		this.color = color;
		this.action = action;
		while ((new Position(this.x, this.y)).checkCollapse(Game.walls,
				Game.WALL_SIZE, Game.APPLE_SIZE) || (new Position(this.x, this.y)).checkCollapse(Game.object,
						Game.SNAKE_SIZE * 2, Game.APPLE_SIZE)) {
			this.x = (float) (-(Game.MAP_SIZE - Game.APPLE_SIZE) + ((Game.MAP_SIZE - Game.APPLE_SIZE) * 2)
					* Math.random());
			this.y = (float) (-(Game.MAP_SIZE - Game.APPLE_SIZE) + ((Game.MAP_SIZE - Game.APPLE_SIZE) * 2)
					* Math.random());
		}
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the action
	 */
	public int getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(int action) {
		this.action = action;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Eatable [x=" + x + ", y=" + y + ", color=" + color
				+ ", action=" + action + "]";
	}

	public void draw() {
		float a = Game.APPLE_SIZE / 2;
		glPushMatrix();
		glTranslated(SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getY(),
				0);
		// Make the apple bounce !
		glTranslatef(x, y, bounce);

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
		rotation += Game.delta * 0.3;
		rotation %= 360;

		float xTemp = x;
		float yTemp = y;
		x = 0;
		y = 0;
		glBegin(GL_QUADS);
		// Apple base
		glVertex3f(x - a, y - a, 0);
		glVertex3f(x + a, y - a, 0);
		glVertex3f(x + a, y + a, 0);
		glVertex3f(x - a, y + a, 0);
		// Apple sides down
		glVertex3f(x - a, y - a, 0);
		glVertex3f(x - Game.APPLE_SIZE, y - Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x - Game.APPLE_SIZE, y + Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x - a, y + a, 0);
		glVertex3f(x + a, y - a, 0);
		glVertex3f(x + Game.APPLE_SIZE, y - Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x + Game.APPLE_SIZE, y + Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x + a, y + a, 0);

		glVertex3f(x - a, y - a, 0);
		glVertex3f(x - Game.APPLE_SIZE, y - Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x + Game.APPLE_SIZE, y - Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x + a, y - a, 0);
		glVertex3f(x - a, y + a, 0);
		glVertex3f(x - Game.APPLE_SIZE, y + Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x + Game.APPLE_SIZE, y + Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x + a, y + a, 0);

		// Apple sides up
		glVertex3f(x - Game.APPLE_SIZE, y - Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x - a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, Game.APPLE_SIZE * 2);
		glVertex3f(x - Game.APPLE_SIZE, y + Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x + Game.APPLE_SIZE, y - Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x + a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, Game.APPLE_SIZE * 2);
		glVertex3f(x + Game.APPLE_SIZE, y + Game.APPLE_SIZE, Game.APPLE_SIZE);

		glVertex3f(x - Game.APPLE_SIZE, y - Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x - a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x + Game.APPLE_SIZE, y - Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x - Game.APPLE_SIZE, y + Game.APPLE_SIZE, Game.APPLE_SIZE);
		glVertex3f(x - a, y + a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, Game.APPLE_SIZE * 2);
		glVertex3f(x + Game.APPLE_SIZE, y + Game.APPLE_SIZE, Game.APPLE_SIZE);

		// Apple top
		glVertex3f(x - a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, Game.APPLE_SIZE * 2);

		a /= 2;
		// Apple "snail"
		glColor3f(0.5f, 0.2f, 0f);
		// Bottom
		glVertex3f(x - a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, Game.APPLE_SIZE * 2);
		// Sides
		glVertex3f(x - a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y - a, Game.APPLE_SIZE * 2 + 1);
		glVertex3f(x + a, y - a, Game.APPLE_SIZE * 2 + 1);

		glVertex3f(x - a, y + a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, Game.APPLE_SIZE * 2 + 1);
		glVertex3f(x + a, y + a, Game.APPLE_SIZE * 2 + 1);

		glVertex3f(x + a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y + a, Game.APPLE_SIZE * 2);
		glVertex3f(x + a, y - a, Game.APPLE_SIZE * 2 + 1);
		glVertex3f(x + a, y + a, Game.APPLE_SIZE * 2 + 1);

		glVertex3f(x - a, y - a, Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y + a, Game.APPLE_SIZE * 2);
		glVertex3f(x - a, y - a, Game.APPLE_SIZE * 2 + 1);
		glVertex3f(x - a, y + a, Game.APPLE_SIZE * 2 + 1);

		// Top
		glVertex3f(x - a, y - a, Game.APPLE_SIZE * 2 + 1);
		glVertex3f(x + a, y - a, Game.APPLE_SIZE * 2 + 1);
		glVertex3f(x + a, y + a, Game.APPLE_SIZE * 2 + 1);
		glVertex3f(x - a, y + a, Game.APPLE_SIZE * 2 + 1);
		glEnd();
		glPopMatrix();

		x = xTemp;
		y = yTemp;
	}

}
