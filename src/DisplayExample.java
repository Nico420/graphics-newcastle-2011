package src;
// DisplayExample
// Just creates a blank (black) window, ready for OpenGL to render to
//
// Exercises
// 
// 01 - Create display windows of different sizes, are all sizes valid? What other parameters are they about display modes?
// 02 - Find out what valid display modes you can use (at runtime)? Can you set the display mode to one of them?
// 03 - Could you write a simple algorithm to choose the "best" available display mode
// 04 - Create a window of the present desktop resolution. Is there anything wrong with it?
// 05 - Can you solve the problem above? 

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;


@SuppressWarnings("unused")
public class DisplayExample {

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
			
			
			
			
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// init OpenGL here
		GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
	    GL11.glLoadIdentity();
	    glColor3f(0f, 1.0f, 1.0f);
		while (!Display.isCloseRequested()) {
			// render OpenGL here
			GL11.glBegin(GL_TRIANGLES);
			GL11.glVertex3f(0,0,0);
			GL11.glVertex3f(100,0,0);
			GL11.glVertex3f(100,100,0);
			GL11.glEnd();
			Display.update(); //flushes OpenGL pipeline and swaps back and front buffers. perhaps waits for v-sync.
		}

		Display.destroy();
	}

	public static void main(String[] argv) {
		DisplayExample displayExample = new DisplayExample();
		displayExample.start();
	}
}
