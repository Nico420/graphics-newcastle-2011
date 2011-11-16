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
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

@SuppressWarnings("unused")
public class DisplayExample {

	public final static int LEFT=1;
	public final static int RIGHT=2;
	public final static int UP=3;
	public final static int DOWN=4;
	public static int mouvement = 0;
	
	/** position of quad */
	float x = 0, y = 0;
	/** angle of quad rotation */
	float rotation = 0;
	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	public static boolean exit = false;

	public int chooseBestDisplay() throws LWJGLException {
		int res = 3;
		for (int i = 0; i < Display.getAvailableDisplayModes().length; i++) {
			//System.out.println(Display.getAvailableDisplayModes()[i]);
		}
		return res;
	}

	public void start() throws LWJGLException {

		try {
			int bestDisplay = chooseBestDisplay();
			DisplayMode dm = Display.getAvailableDisplayModes()[bestDisplay];
			//System.out.println(dm);
			Display.setDisplayMode(dm);
			Display.setFullscreen(false);
			Display.setResizable(true);
			Display.setTitle("Graphics");
			Display.create();

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initGL();
		getDelta();
		lastFPS = getTime();

		while (!Display.isCloseRequested() && !exit) { // Done Drawing The Quad
			int delta = getDelta();

			update(delta);
			renderGL();
			pollInput();
			Display.update(); // flushes OpenGL pipeline and swaps back and
								// front buffers. perhaps waits for v-sync.
		}

		Display.destroy();
	}

	private void update(int delta) {
		rotation += 0.15f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			mouvement=LEFT;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			mouvement=RIGHT;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			mouvement=UP;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			mouvement=DOWN;
		
		switch(mouvement){
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
		if (x < 0)
			x = 0;
		if (x > 100)
			x = 100;
		if (y < 0)
			y = 0;
		if (y > 100)
			y = 100;
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

	private void renderGL() throws LWJGLException {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear
																			// The
																			// Screen
																			// And
																			// The
																			// Depth
																			// Buffer

		//GL11.glLoadIdentity();

		//GL11.glTranslatef(50, 50, 0);
		// set the color of the quad (R,G,B,A)
		GL11.glColor3f(0f, 1f, 0f);
		//GL11.glViewport(0, 0, 50, 50);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glVertex3f(0, 0, 0);
		GL11.glEnd();

		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-x, -y, 0);
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex3f(x - 10, y - 10, 0);
			GL11.glVertex3f(x + 20, y - 10, 0);
			GL11.glVertex3f(x + 20, y + 20, 0);
		GL11.glEnd();
		GL11.glPopMatrix();
		System.out.println(x+"|"+y);

		/*
		 * GL11.glLoadIdentity(); //GL11.glTranslatef(0,0 , 0);
		 * GL11.glPushMatrix(); GL11.glRotatef(rtri, 0, 45, 0);
		 * GL11.glBegin(GL11.GL_QUADS); GL11.glVertex3f(10,30,0);
		 * GL11.glVertex3f(20, 30, 0); GL11.glVertex3f(20, 60, 0);
		 * GL11.glVertex3f(10, 60, 0); GL11.glEnd(); GL11.glPopMatrix();
		 */

		/*
		 * GL11.glColor3f(0f,0f,1f);
		 * 
		 * GL11.glPushMatrix(); GL11.glTranslated(0, ttri, 0);
		 * GL11.glRotatef(rtri, 1, 0, 0); GL11.glBegin(GL11.GL_TRIANGLES);
		 * GL11.glVertex3f(100,0,0); GL11.glVertex3f(200, 0, 0);
		 * GL11.glVertex3f(200, 100, 0); GL11.glEnd(); GL11.glPopMatrix();
		 */
		
		//Designing a 3d object
		GL11.glScalef(0.1f, 0.1f, 0.1f);
		draw3DQuad(50f,50f,50f,10f);

	}

	private void draw3DQuad(float x, float y, float z,float size) {
		float a = size/2;
		
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glRotatef(30, 0, 1, 1);
		GL11.glRotatef(rotation/3, 1f, 0f, 0f);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3f(x-a, y-a, z-a);
			GL11.glVertex3f(x+a, y-a, z-a);
			GL11.glVertex3f(x+a, y+a, z-a);
			GL11.glVertex3f(x-a, y+a, z-a);
			GL11.glColor3f(0f, 1f, 1f);
			GL11.glVertex3f(x-a, y-a, z+a);
			GL11.glVertex3f(x+a, y-a, z+a);
			GL11.glVertex3f(x+a, y+a, z+a);
			GL11.glVertex3f(x-a, y+a, z+a);
			GL11.glColor3f(1f, 0f, 0f);
			GL11.glVertex3f(x-a, y-a, z+a);
			GL11.glVertex3f(x+a, y-a, z+a);
			GL11.glVertex3f(x+a, y-a, z-a);
			GL11.glVertex3f(x-a, y-a, z-a);
			GL11.glColor3f(0f, 0f, 1f);
			GL11.glVertex3f(x+a, y+a, z+a);
			GL11.glVertex3f(x-a, y+a, z+a);
			GL11.glVertex3f(x-a, y+a, z-a);
			GL11.glVertex3f(x+a, y+a, z-a);
		GL11.glEnd();
		GL11.glPopMatrix();
		
	}

	private void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 100, 100, 0, 100, -100);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

	}

	public static void main(String[] argv) throws LWJGLException {
		DisplayExample displayExample = new DisplayExample();
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