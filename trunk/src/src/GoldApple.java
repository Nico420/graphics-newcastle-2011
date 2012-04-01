package src;

import static org.lwjgl.opengl.GL11.glColor3d;

import java.awt.Color;

/**
 * Create an Apple with the multi points action.
 * 
 * @author Nicolas
 * 
 */
public class GoldApple extends Eatable {

	/** Apple Blue. */
	private static final int APPLE_BLUE = 0;
	/** Apple Green. */
	private static final int APPLE_GREEN = 218;
	/** Apple Red. */
	private static final int APPLE_RED = 255;
	/** Max color vlaue. */
	private static final int MAX_COLOR = 255;

	/**
	 * Build a gold apple.
	 */
	public GoldApple() {
		super(0, 0, new Color(APPLE_RED, APPLE_GREEN, APPLE_BLUE),
				Eatable.MULTI);
	}

	/**
	 * @inheritDoc
	 */
	public void draw() {
		double red = ((float) getColor().getRed()) / MAX_COLOR;
		double green = ((float) getColor().getGreen()) / MAX_COLOR;
		double blue = ((float) getColor().getBlue()) / MAX_COLOR;
		glColor3d(red, green, blue);
		super.draw();
	}
}
