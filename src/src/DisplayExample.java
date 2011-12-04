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

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import snakeMoteur.Position;

@SuppressWarnings("unused")
public class DisplayExample {

	public final static int LEFT = 1;
	public final static int RIGHT = 2;
	public final static int UP = 3;
	public final static int DOWN = 4;
	public static int mouvement = 0;
	public static boolean continuer = true;
	public static boolean partieenCours = false;
	/** position of quad */
	float x = 11, y = 11;

	float xpomme = 50, ypomme = 50;

	private static int i = 0;
	int longueurSerpent = 3;
	ArrayList<snakeMoteur.Position> positions = new ArrayList<Position>();
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
		// for (int i = 0; i < Display.getAvailableDisplayModes().length; i++) {
		// System.out.println(Display.getAvailableDisplayModes()[i]);
		// }
		return res;
	}

	public void start() throws LWJGLException {

		try {
			int bestDisplay = chooseBestDisplay();
			DisplayMode dm = Display.getAvailableDisplayModes()[bestDisplay];
			// System.out.println(dm);
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
		for (int i = 0; i < longueurSerpent - 1; i++) {
			positions.add(new Position(x - 0.1f, y - 0.1f));
		}
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

		// Check the snake
		if (mouvement!=0 && continuer){
			continuer = !positions.subList(3, positions.size()-1).contains(new Position(x, y));
		}
		// Check the wall
		if (y >= 90 || y <= 10 || x <= 10 || x >= 90)
			continuer = false;

		// Check the apple
		if ((xpomme < x + 5 && xpomme > x - 5)
				&& (ypomme < y + 5 && ypomme > y - 5)) {
			longueurSerpent++;
			generateNewPomme();
		}
		// movement
		Iterator<Position> ite = positions.iterator();
		while (ite.hasNext()) {
			Position e = ite.next();
			GL11.glVertex3f(e.getX() - 2, e.getY() - 2, 0);
			GL11.glVertex3f(e.getX() + 2, e.getY() - 2, 0);
			GL11.glVertex3f(e.getX() + 2, e.getY() + 2, 0);
			GL11.glVertex3f(e.getX() - 2, e.getY() + 2, 0);
		}
		updateFPS(); // update FPS Counter

	}

	private void generateNewPomme() {
		xpomme = (float) (10 + 80 * Math.random());
		ypomme = (float) (10 + 80 * Math.random());

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

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear
																			// The
																			// Screen
																			// And
																			// The
																			// Depth
																			// Buffer
		GL11.glLoadIdentity();
		if (continuer) {
			createMap();
			createSnake(delta);
			createPomme();
		} else {
			afficherPerdu();
		}

		/*
		 * GL11.glPushMatrix(); GL11.glTranslatef(x, y, 0);
		 * GL11.glRotatef(rotation, 0f, 0f, 1f); GL11.glTranslatef(-x, -y, 0);
		 * GL11.glBegin(GL11.GL_TRIANGLES); GL11.glVertex3f(x - 10, y - 10, 0);
		 * GL11.glVertex3f(x + 20, y - 10, 0); GL11.glVertex3f(x + 20, y + 20,
		 * 0); GL11.glEnd(); GL11.glPopMatrix(); System.out.println(x + "|" +
		 * y);
		 */

		/*
		 * GL11.glLoadIdentity(); //GL11.glTranslatef(0,0 , 0);
		 * GL11.glPushMatrix(); GL11.glRotatef(rtri, 0, 45, 0);
		 * GL11.glBegin(GL11.GL_QUADS); GL11.glVertex3f(10,30,0);
		 * GL11.glVertex3f(20, 30, 0); GL11.glVertex3f(20, 60, 0);
		 * GL11.glVertex3f(10, 60, 0); GL11.glEnd(); GL11.glPopMatrix();
		 * 
		 * 
		 * /* GL11.glColor3f(0f,0f,1f);
		 * 
		 * GL11.glPushMatrix(); GL11.glTranslated(0, ttri, 0);
		 * GL11.glRotatef(rtri, 1, 0, 0); GL11.glBegin(GL11.GL_TRIANGLES);
		 * GL11.glVertex3f(100,0,0); GL11.glVertex3f(200, 0, 0);
		 * GL11.glVertex3f(200, 100, 0); GL11.glEnd(); GL11.glPopMatrix();
		 */

		// Designing a 3d object
		// GL11.glScalef(0.1f, 0.1f, 0.1f);
		// draw3DQuad(50f,50f,50f,10f);
		// drawPyramid();
	}

	private void createPomme() {
		GL11.glPushMatrix();
		GL11.glColor3f(0.0f, 1.0f, 0.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(xpomme - 2, ypomme - 2, 0);
		GL11.glVertex3f(xpomme + 2, ypomme - 2, 0);
		GL11.glVertex3f(xpomme + 2, ypomme + 2, 0);
		GL11.glVertex3f(xpomme - 2, ypomme + 2, 0);

		GL11.glEnd();
		GL11.glPopMatrix();

	}

	private void afficherPerdu() {
		GL11.glPushMatrix();
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(10, 10, 0);
		GL11.glVertex3f(90, 10, 0);
		GL11.glVertex3f(90, 90, 0);
		GL11.glVertex3f(10, 90, 0);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	private void createSnake(int delta) {
		GL11.glPushMatrix();

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glColor3f(0, 0, 1.0f);
		Iterator<Position> ite = positions.iterator();
		while (ite.hasNext()) {
			Position e = ite.next();
			GL11.glVertex3f(e.getX() - 3, e.getY() - 3, 0);
			GL11.glVertex3f(e.getX() + 3, e.getY() - 3, 0);
			GL11.glVertex3f(e.getX() + 3, e.getY() + 3, 0);
			GL11.glVertex3f(e.getX() - 3, e.getY() + 3, 0);
		}
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glVertex3f(x - 3, y - 3, 0);
		GL11.glVertex3f(x + 3, y - 3, 0);
		GL11.glVertex3f(x + 3, y + 3, 0);
		GL11.glVertex3f(x - 3, y + 3, 0);

		GL11.glEnd();
		GL11.glPopMatrix();
		positions.add(0, new Position(x, y));
		if (positions.size() >= 30 * longueurSerpent) {
			positions.remove(positions.size() - 1);
		}

	}

	private void createMap() {
		GL11.glPushMatrix();
		GL11.glColor3f(1.0f, 1.0f, 0.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(10, 10, 0);
		GL11.glVertex3f(90, 10, 0);
		GL11.glVertex3f(90, 90, 0);
		GL11.glVertex3f(10, 90, 0);
		GL11.glEnd();
		GL11.glPopMatrix();

	}

	private void drawPyramid() {
		GL11.glPushMatrix();
		GL11.glLoadIdentity();

		GL11.glLoadIdentity(); // Reset The View
		GL11.glTranslatef(-1.5f, 0.0f, -8.0f); // Move Left 1.5 Units And Into
												// The Screen 6.0
		GL11.glRotatef(rotation, 1.0f, 1.0f, 0.0f); // Rotate The Triangle On
													// The Y axis ( NEW )
		GL11.glBegin(GL11.GL_TRIANGLES); // Start Drawing A Triangle
		GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
		GL11.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Front)
		GL11.glColor3f(0.0f, 1.0f, 0.0f); // Green
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f); // Left Of Triangle (Front)
		GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		GL11.glVertex3f(1.0f, -1.0f, 1.0f); // Right Of Triangle (Front)
		GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
		GL11.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Right)
		GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		GL11.glVertex3f(1.0f, -1.0f, 1.0f); // Left Of Triangle (Right)
		GL11.glColor3f(0.0f, 1.0f, 0.0f); // Green
		GL11.glVertex3f(1.0f, -1.0f, -1.0f); // Right Of Triangle (Right)
		GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
		GL11.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Back)
		GL11.glColor3f(0.0f, 1.0f, 0.0f); // Green
		GL11.glVertex3f(1.0f, -1.0f, -1.0f); // Left Of Triangle (Back)
		GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f); // Right Of Triangle (Back)
		GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
		GL11.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Left)
		GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f); // Left Of Triangle (Left)
		GL11.glColor3f(0.0f, 1.0f, 0.0f); // Green
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f); // Right Of Triangle (Left)
		GL11.glEnd(); // Done Drawing The Pyramid

		GL11.glLoadIdentity(); // Reset The Current Modelview Matrix
		GL11.glTranslatef(1.5f, 0.0f, -9.0f); // Move Right 1.5 Units And Into
												// The Screen 7.0
		GL11.glRotatef(rotation, 1.0f, 1.0f, 1.0f); // Rotate The Quad On The X
													// axis ( NEW )
		GL11.glBegin(GL11.GL_QUADS); // Draw A Quad
		GL11.glColor3f(0.0f, 1.0f, 0.0f); // Set The Color To Blue
		GL11.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left Of The Quad (Top)
		GL11.glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Right Of The Quad (Top)
		GL11.glColor3f(1.0f, 0.5f, 0.0f); // Set The Color To Orange
		GL11.glVertex3f(1.0f, -1.0f, 1.0f); // Top Right Of The Quad (Bottom)
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f); // Top Left Of The Quad (Bottom)
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad
												// (Bottom)
		GL11.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad
												// (Bottom)
		GL11.glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Red
		GL11.glVertex3f(1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Front)
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Front)
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Front)
		GL11.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Front)
		GL11.glColor3f(1.0f, 1.0f, 0.0f); // Set The Color To Yellow
		GL11.glVertex3f(1.0f, -1.0f, -1.0f); // Top Right Of The Quad (Back)
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f); // Top Left Of The Quad (Back)
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f); // Bottom Left Of The Quad (Back)
		GL11.glVertex3f(1.0f, 1.0f, -1.0f); // Bottom Right Of The Quad (Back)
		GL11.glColor3f(0.0f, 0.0f, 1.0f); // Set The Color To Blue
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Left)
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Left)
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Left)
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Left)
		GL11.glColor3f(1.0f, 0.0f, 1.0f); // Set The Color To Violet
		GL11.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Right)
		GL11.glVertex3f(1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Right)
		GL11.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Right)
		GL11.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Right)
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	private void draw3DQuad(float x, float y, float z, float size) {
		float a = size / 2;

		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glRotatef(30, 0, 1, 1);
		GL11.glRotatef(rotation / 3, 1f, 0f, 0f);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(x - a, y - a, z - a);
		GL11.glVertex3f(x + a, y - a, z - a);
		GL11.glVertex3f(x + a, y + a, z - a);
		GL11.glVertex3f(x - a, y + a, z - a);
		GL11.glColor3f(0f, 1f, 1f);
		GL11.glVertex3f(x - a, y - a, z + a);
		GL11.glVertex3f(x + a, y - a, z + a);
		GL11.glVertex3f(x + a, y + a, z + a);
		GL11.glVertex3f(x - a, y + a, z + a);
		GL11.glColor3f(1f, 0f, 0f);
		GL11.glVertex3f(x - a, y - a, z + a);
		GL11.glVertex3f(x + a, y - a, z + a);
		GL11.glVertex3f(x + a, y - a, z - a);
		GL11.glVertex3f(x - a, y - a, z - a);
		GL11.glColor3f(0f, 0f, 1f);
		GL11.glVertex3f(x + a, y + a, z + a);
		GL11.glVertex3f(x - a, y + a, z + a);
		GL11.glVertex3f(x - a, y + a, z - a);
		GL11.glVertex3f(x + a, y + a, z - a);
		GL11.glEnd();
		GL11.glPopMatrix();

	}

	private void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 100, 100, 0, 10, -10);
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