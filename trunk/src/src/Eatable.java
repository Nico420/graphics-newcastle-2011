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

import state.Game;
import tools.Position;

/**
 * Implementation of items that you can find on the map.
 * 
 * @author Nicolas
 * 
 */
public class Eatable {

	/** Blue. */
	private static final float BLUE = 0f;
	/** Bounce down limit. */
	private static final double BOUNCE_DOWN_LIMIT = 1.90;
	/** Bounce speed. */
	private static final double BOUNCE_SPEED = 0.001;
	/** Bounce up limit. */
	private static final double BOUNCE_UP_LIMIT = 0.10;
	/** Green. */
	private static final float GREEN = 0.2f;
	/** Grow up. */
	public static final int GROW_UP = 1;
	/** Multiply. */
	public static final int MULTI = 4;
	/** Red. */
	private static final float RED = 0.5f;
	/** Reduce. */
	public static final int REDUCE = 2;
	/** Rotation limit. */
	private static final int ROTATION_LIMIT = 360;
	/** Rotation speed. */
	private static final double ROTATION_SPEED = 0.3;
	/** Slow. */
	public static final int SLOW = 3;

	/** Each item as an action(Reduce the snake, multi points,...). */
	private int action;
	/** Bounce. */
	private float bounce;
	/** Color. */
	private Color color;

	/** Direction. */
	private boolean direction = false;
	/** Rotation. */
	private float rotation;

	/**
	 * x position of the item.
	 */
	private float x;

	/**
	 * x position of the item.
	 */
	private float y;

	/**
	 * During the creation of an item, we have to check for not placing it on a
	 * wall.
	 * 
	 * @param pX
	 *            x position
	 * @param pY
	 *            y position
	 * @param pColor
	 *            item color
	 * @param pAction
	 *            action
	 */
	public Eatable(float pX, float pY, Color pColor, int pAction) {
		super();
		this.color = pColor;
		this.action = pAction;
		while ((new Position(this.x, this.y)).checkCollapse(Game.getWalls(),
				Game.WALL_SIZE, Game.APPLE_SIZE)
				|| (new Position(this.x, this.y)).checkCollapse(
						Game.getObject(), Game.SNAKE_SIZE * 2, Game.APPLE_SIZE)) {
			this.x = (float) (-(Game.MAP_SIZE - Game.APPLE_SIZE) + ((Game.MAP_SIZE - Game.APPLE_SIZE) * 2)
					* Math.random());
			this.y = (float) (-(Game.MAP_SIZE - Game.APPLE_SIZE) + ((Game.MAP_SIZE - Game.APPLE_SIZE) * 2)
					* Math.random());
		}
	}

	/**
	 * Drawing an item This is the default drawing, making an Apple. If you want
	 * to create another drawing, just redefine this method in the new item
	 * Class.
	 */
	public void draw() {
		float a = Game.APPLE_SIZE / 2;
		glPushMatrix();
		glColor3f(color.getRed(), color.getGreen(), color.getBlue());
		glTranslated(SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getY(),
				0);
		// Make the apple bounce !
		glTranslatef(x, y, bounce);

		if (!direction) {
			bounce = (float) ((bounce + BOUNCE_SPEED) % 2);
		} else {
			bounce = (float) ((bounce - BOUNCE_SPEED) % 2);
		}
		if (bounce < BOUNCE_UP_LIMIT) {
			direction = false;
		} else if (bounce > BOUNCE_DOWN_LIMIT) {
			direction = true;
		}

		// Make the apple turn !
		glRotatef(rotation, 0, 0, 1);
		rotation += ROTATION_SPEED;
		rotation %= ROTATION_LIMIT;

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
		glColor3f(RED, GREEN, BLUE);
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

	/**
	 * @return the action
	 */
	public int getAction() {
		return action;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param pAction
	 *            the action to set
	 */
	public void setAction(int pAction) {
		this.action = pAction;
	}

	/**
	 * @param pColor
	 *            the color to set
	 */
	public void setColor(Color pColor) {
		this.color = pColor;
	}

	/**
	 * @param pX
	 *            the x to set
	 */
	public void setX(float pX) {
		this.x = pX;
	}

	/**
	 * @param pY
	 *            the y to set
	 */
	public void setY(float pY) {
		this.y = pY;
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

}
