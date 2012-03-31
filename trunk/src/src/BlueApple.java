package src;

import static org.lwjgl.opengl.GL11.glColor3d;

import java.awt.Color;

/**
 * Create an Apple with the reduce Snake action.
 * 
 * @author Nicolas
 * 
 */
public class BlueApple extends Eatable {

	/** Color Maximum value. */
	private static final int COLOR_MAX = 255;

	/**
	 * Build a blue apple.
	 */
	public BlueApple() {
		super(0, 0, Color.BLUE, Eatable.REDUCE);
	}

	@Override
	public final void draw() {
		double red = ((float) color.getRed()) / COLOR_MAX;
		double green = ((float) color.getGreen()) / COLOR_MAX;
		double blue = ((float) color.getBlue()) / COLOR_MAX;
		glColor3d(red, green, blue);
		super.draw();
	}

}
