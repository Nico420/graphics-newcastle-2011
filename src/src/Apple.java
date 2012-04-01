package src;

import java.awt.Color;

/**
 * Implementation of the classic apples.
 * 
 * @author Nicolas
 * 
 */
public class Apple extends Eatable {

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
