package src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

/**
 * Create an Apple with the reduce Snake action
 * 
 * @author Nicolas
 * 
 */
public class BlueApple extends Eatable {

	/**
	 * Build a blue apple.
	 */
	public BlueApple() {
		super(0, 0, Color.BLUE, Eatable.REDUCE);
	}

	@Override
	public final void draw() {
		double red = ((float) color.getRed()) / 255;
		double green = ((float) color.getGreen()) / 255;
		double blue = ((float) color.getBlue()) / 255;
		glColor3d(red, green, blue);
		super.draw();
	}

}
