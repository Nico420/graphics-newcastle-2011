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

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

@SuppressWarnings("unused")
public class Test {

	private static final int LEFT = 1;
	private static final int RIGHT = 2;
	private static final int UP = 3;
	private static final int DOWN = 4;
	/** angle of quad rotation */
	float rotation = 0;
	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;
	public int mouvement = 0;
	public float x;
	public float y;

	public boolean switchView = false;

	public static boolean exit = false;

	public int chooseBestDisplay() throws LWJGLException {
		int res = 3;
		return res;
	}

	public void start() throws LWJGLException {

		try {
			int bestDisplay = chooseBestDisplay();
			DisplayMode dm = Display.getAvailableDisplayModes()[bestDisplay];
			System.out.println(dm);
			Display.setDisplayMode(dm);
			Display.setFullscreen(false);
			Display.setResizable(true);
			Display.setTitle("Graphics");
			Display.create();

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// Initialize Snake position

		initGL();
		getDelta();
		lastFPS = getTime();

		while (!Display.isCloseRequested() && !exit) { // Done Drawing The Quad
			int delta = getDelta();
			update(delta);
			renderGL(delta);
			pollInput();
			Display.update(); // flushes OpenGL pipeline and swaps back and
								// front buffers. perhaps waits for v-sync.
		}

		Display.destroy();
	}

	private void update(int delta) {
		rotation += 0.15f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			mouvement = LEFT;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			mouvement = RIGHT;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			mouvement = UP;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			mouvement = DOWN;

		switch (mouvement) {
		case LEFT:
			x -= 0.05f * delta;
			break;
		case RIGHT:
			x += 0.05f * delta;
			break;
		case UP:
			y -= 0.05f * delta;
			break;
		case DOWN:
			y += 0.05f * delta;
			break;
		}
		// keep quad on the screen
		if (x < 10)
			x = 10;
		if (x > 90)
			x = 90;
		if (y < 10)
			y = 10;
		if (y > 90)
			y = 90;

		updateFPS(); // update FPS Counter

	}

	private void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;

	}

	private int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	private void renderGL(int delta) throws LWJGLException {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear
																			// The
																			// Screen
																			// And
																			// The
																			// Depth
																			// Buffer
		glLoadIdentity();
		if(switchView){
		glRotatef(85, 1, 0, 0);

		glTranslatef(0, 0, -50);
		}
		glPushMatrix();
		int a =10;
		x=0;y=0;
		glBegin(GL_QUADS);
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y - a, 5);
		glColor3f(1, 1, 0);
		glVertex3f(x + a, y - a, 5);

		glColor3f(1, 1,1);
		glVertex3f(x + a, y + a, 0);

		glColor3f(1, 0, 1);
		glVertex3f(x - a, y + a, 0);
		glEnd();
		glBegin(GL_QUADS);
		glColor3f(1, 0, 0);
		glVertex3f(x - a, y - a, 5);
		glColor3f(1, 1, 0);
		glVertex3f(x + a, y - a, 5);

		glColor3f(1, 1,1);
		glVertex3f(x + a, y + a, 0);

		glColor3f(1, 0, 1);
		glVertex3f(x - a, y + a, 0);
		glEnd();
		glPopMatrix();
		
		//draw3DQuad(x, y, 0, 10);
	}

	private void drawPyramid() {
		glPushMatrix();

		glLoadIdentity(); // Reset The View
		glTranslatef(10f, 10.0f, 0f); // Move Left 1.5 Units And Into
										// The Screen 6.0
		glRotatef(rotation, 1.0f, 1.0f, 0.0f); // Rotate The Triangle On
												// The Y axis ( NEW )
		glBegin(GL_TRIANGLES); // Start Drawing A Triangle
		glColor3f(1.0f, 0.0f, 0.0f); // Red
		glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Front)
		glColor3f(0.0f, 1.0f, 0.0f); // Green
		glVertex3f(-1.0f, -1.0f, 1.0f); // Left Of Triangle (Front)
		glColor3f(0.0f, 0.0f, 1.0f); // Blue
		glVertex3f(1.0f, -1.0f, 1.0f); // Right Of Triangle (Front)
		glColor3f(1.0f, 0.0f, 0.0f); // Red
		glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Right)
		glColor3f(0.0f, 0.0f, 1.0f); // Blue
		glVertex3f(1.0f, -1.0f, 1.0f); // Left Of Triangle (Right)
		glColor3f(0.0f, 1.0f, 0.0f); // Green
		glVertex3f(1.0f, -1.0f, -1.0f); // Right Of Triangle (Right)
		glColor3f(1.0f, 0.0f, 0.0f); // Red
		glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Back)
		glColor3f(0.0f, 1.0f, 0.0f); // Green
		glVertex3f(1.0f, -1.0f, -1.0f); // Left Of Triangle (Back)
		glColor3f(0.0f, 0.0f, 1.0f); // Blue
		glVertex3f(-1.0f, -1.0f, -1.0f); // Right Of Triangle (Back)
		glColor3f(1.0f, 0.0f, 0.0f); // Red
		glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Left)
		glColor3f(0.0f, 0.0f, 1.0f); // Blue
		glVertex3f(-1.0f, -1.0f, -1.0f); // Left Of Triangle (Left)
		glColor3f(0.0f, 1.0f, 0.0f); // Green
		glVertex3f(-1.0f, -1.0f, 1.0f); // Right Of Triangle (Left)
		glEnd(); // Done Drawing The Pyramid

		glLoadIdentity(); // Reset The Current Modelview Matrix
		glTranslatef(40f, 10.0f, 0f); // Move Right 1.5 Units And Into
										// The Screen 7.0
		glRotatef(rotation, 1.0f, 1.0f, 1.0f); // Rotate The Quad On The X
												// axis ( NEW )
		glBegin(GL_QUADS); // Draw A Quad
		glColor3f(0.0f, 1.0f, 0.0f); // Set The Color To Blue
		glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
		glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
		glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left Of The Quad (Top)
		glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Right Of The Quad (Top)
		glColor3f(1.0f, 0.5f, 0.0f); // Set The Color To Orange
		glVertex3f(1.0f, -1.0f, 1.0f); // Top Right Of The Quad (Bottom)
		glVertex3f(-1.0f, -1.0f, 1.0f); // Top Left Of The Quad (Bottom)
		glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad
											// (Bottom)
		glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad
										// (Bottom)
		glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Red
		glVertex3f(1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Front)
		glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Front)
		glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Front)
		glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Front)
		glColor3f(1.0f, 1.0f, 0.0f); // Set The Color To Yellow
		glVertex3f(1.0f, -1.0f, -1.0f); // Top Right Of The Quad (Back)
		glVertex3f(-1.0f, -1.0f, -1.0f); // Top Left Of The Quad (Back)
		glVertex3f(-1.0f, 1.0f, -1.0f); // Bottom Left Of The Quad (Back)
		glVertex3f(1.0f, 1.0f, -1.0f); // Bottom Right Of The Quad (Back)
		glColor3f(0.0f, 0.0f, 1.0f); // Set The Color To Blue
		glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Left)
		glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Left)
		glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Left)
		glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Left)
		glColor3f(1.0f, 0.0f, 1.0f); // Set The Color To Violet
		glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Right)
		glVertex3f(1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Right)
		glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Right)
		glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Right)
		glEnd();
		glPopMatrix();
	}

	private void draw3DQuad(float x, float y, float z, float size) {
		float a = size / 2;

		glPushMatrix();

		glBegin(GL_QUADS);
		glVertex3f(x - a, y - a, z - a);
		glVertex3f(x + a, y - a, z - a);
		glVertex3f(x + a, y + a, z - a);
		glVertex3f(x - a, y + a, z - a);
		glColor3f(0f, 1f, 1f);
		glVertex3f(x - a, y - a, z + a);
		glVertex3f(x + a, y - a, z + a);
		glVertex3f(x + a, y + a, z + a);
		glVertex3f(x - a, y + a, z + a);
		glColor3f(1f, 0f, 0f);
		glVertex3f(x - a, y - a, z + a);
		glVertex3f(x + a, y - a, z + a);
		glVertex3f(x + a, y - a, z - a);
		glVertex3f(x - a, y - a, z - a);
		glColor3f(0f, 0f, 1f);
		glVertex3f(x + a, y + a, z + a);
		glVertex3f(x - a, y + a, z + a);
		glVertex3f(x - a, y + a, z - a);
		glVertex3f(x + a, y + a, z - a);
		glEnd();
		glPopMatrix();

	}

	private void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-100, 100, 100, -100, 100, -100);
		glMatrixMode(GL_MODELVIEW);
	}

	public static void main(String[] argv) throws LWJGLException {
		Test displayExample = new Test();
		displayExample.start();
	}

	public void pollInput() throws LWJGLException {

		if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX();
			int y = Mouse.getY();
			System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			System.out.println("SPACE KEY IS DOWN");
		}
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Pressed");
					if (Display.isFullscreen())
						Display.setFullscreen(false);
					else
						Display.setFullscreen(true);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					System.out.println("S Key Pressed");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("D Key Pressed");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					System.out.println("Escape Key Pressed");
					exit = true;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					System.out.println("V Key Pressed");
					switchView = switchView ? false : true;
				}
			} else {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Released");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					System.out.println("S Key Released");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("D Key Released");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					System.out.println("Escape Key Released");
				}
			}
		}
	}
}