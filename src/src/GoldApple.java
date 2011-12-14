package src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

public class GoldApple extends Eatable {

	public GoldApple() {
		super(0,0, new Color(255,218,0), Eatable.MULTI);
	}

	public void draw() {	
		double red = ((float)color.getRed())/255;
		double green = ((float)color.getGreen())/255;
		double blue = ((float)color.getBlue())/255;
		glColor3d(red, green, blue);
		super.draw();
	}
}

