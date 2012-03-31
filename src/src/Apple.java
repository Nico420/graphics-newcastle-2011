package src;

import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.GL_QUADS;

import java.awt.Color;

/**
 * Implementation of the classic apples.
 * 
 * @author Nicolas
 * 
 */
public class Apple extends Eatable {

	/** Apple blue. */
	private static final float APPLE_BLUE = 0f;
	/** Apple green. */
	private static final float APPLE_GREEN = 0.2f;
	/** Apple red. */
	private static final float APPLE_RED = 0.5f;
	/** Rotation limit. */
	private static final int ROTATION_LIMIT = 360;
	/** Rotation speed. */
	private static final double ROTATION_SPEED = 0.3;
	/** Bounce up limit. */
	private static final double BOUNCE_UPLIMIT = 1.90;
	/** Bounce down limit. */
	private static final double BOUNCE_DOWNLIMIT = 0.10;
	/** Bounce height. */
	private static final int BOUNCE_HEIGHT = 2;
	/** Bounce speed. */
	private static final double BOUNCE_SPEED = 0.001;

	/** Set up an Apple. */
	public Apple() {
		super(0, 0, Color.GREEN, Eatable.GROW_UP);
	}

	/**
	 * Drawing the apple.
	 */
	public final void draw() {
		super.draw();
	}

}
