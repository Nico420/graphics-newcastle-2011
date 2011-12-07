package src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import tools.GLFont;
import tools.MazeReader;
import tools.Position;

public class Snake {

	GLFont font;
	private static final int LEFT = 1;
	private static final int RIGHT = 2;
	private static final int UP = 3;
	private static final int DOWN = 4;

	public static final int SNAKE_SIZE = 3;
	public static final int MAP_SIZE = 90;
	public static final int APPLE_SIZE = 2;
	public static final int WALL_SIZE = 5;

	public List<Position> positions = new ArrayList<Position>();
	public List<Position> walls = new ArrayList<Position>();


	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	public int mouvement = 0;
	public float x;
	public float y;
	public float xTemp = 0;
	public float yTemp = 0;

	public boolean switchView = false;

	public static boolean exit = false;

	public static int snakeLenght = 0;

	public boolean perdu = false;

	public boolean appleEat = false;

	Apple apple = new Apple();

	public int chooseBestDisplay() throws LWJGLException {
		int res = 3;
		return res;
	}

	public void start() throws LWJGLException, InterruptedException, FileNotFoundException {

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

		walls = MazeReader.buildWallList("maze.txt");
		
		System.out.println(walls);
		//Initialize Snake start_position
		while((new Position(x,y).checkCollapse(walls, WALL_SIZE))){
			x = (float) (-MAP_SIZE + MAP_SIZE*2*Math.random());
			y = (float) (-MAP_SIZE + MAP_SIZE*2*Math.random());
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

	private void update(int delta) throws InterruptedException {
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			if (!(mouvement == RIGHT))
				mouvement = LEFT;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			if (!(mouvement == LEFT))
				mouvement = RIGHT;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			if (!(mouvement == DOWN))
				mouvement = UP;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			if (!(mouvement == UP))
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
		// Adding a new position for snake
		if (Math.sqrt(Math.pow(xTemp - x, 2) + Math.pow(yTemp - y, 2)) > (SNAKE_SIZE * 2)) {
			draw3DQuad(xTemp, yTemp, 0, SNAKE_SIZE * 2);
			positions.add(new Position(xTemp, yTemp));
			// Rempla�er par boolean mangage de pomme
			if (!appleEat) {
				positions = positions.subList(1, snakeLenght + 1);
			} else {
				appleEat = false;
			}
			xTemp = x;
			yTemp = y;
		}

		Position actual = new Position(x, y);
		// Check for the wall collision

		if (!perdu)
			perdu = actual.checkCollapse(walls, WALL_SIZE);

		if (x < -MAP_SIZE + SNAKE_SIZE) {
			x = -MAP_SIZE + SNAKE_SIZE;
			perdu = true;
		}
		if (x > MAP_SIZE - SNAKE_SIZE) {
			x = MAP_SIZE - SNAKE_SIZE;
			perdu = true;
		}
		if (y < -MAP_SIZE + SNAKE_SIZE) {
			y = -MAP_SIZE + SNAKE_SIZE;
			perdu = true;
		}
		if (y > MAP_SIZE - SNAKE_SIZE) {
			y = MAP_SIZE - SNAKE_SIZE;
			perdu = true;
		}

		// Check Apple detection
		if (x - SNAKE_SIZE < apple.getX() && x + SNAKE_SIZE > apple.getX()
				&& y - SNAKE_SIZE < apple.getY() && y + SNAKE_SIZE > apple.getY()) {
			snakeLenght++;
			float xPommeTemp = (float) (-MAP_SIZE + 2 * MAP_SIZE * Math.random());
			float yPommeTemp = (float) (-MAP_SIZE + 2 * MAP_SIZE * Math.random());
			while(new Position(xPommeTemp,yPommeTemp).checkCollapse(walls, APPLE_SIZE)){
				xPommeTemp = (float) (-MAP_SIZE + 2 * MAP_SIZE * Math.random());
				yPommeTemp = (float) (-MAP_SIZE + 2 * MAP_SIZE * Math.random());
			}
			apple.setY(yPommeTemp);
			apple.setX(xPommeTemp);
			appleEat = true;
		}

		// Check Snake hitting himself
		if (!perdu)
			perdu = actual.checkCollapse(positions, SNAKE_SIZE / 2);
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

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear
															// The
															// Screen
															// And
															// The
															// Depth
															// Buffer
		glViewport(100, 100, Display.getWidth() - 100,
				Display.getHeight() - 100);

		glLoadIdentity();
		if (switchView) {
			glRotatef(85, 1, 0, 0);
			glTranslatef(0, 0, -100);
		} else {
			glRotatef(20, 0, 1, 0);
			glRotatef(-20, 1, 0, 0);
		}

		if (perdu) {
			glPushMatrix();
			glBegin(GL_QUADS);
			glColor3f(1, 0, 0);
			glVertex3f(0 - MAP_SIZE, 0 - MAP_SIZE, 0);
			glVertex3f(0 + MAP_SIZE, 0 - MAP_SIZE, 0);
			glVertex3f(0 + MAP_SIZE, 0 + MAP_SIZE, 0);
			glVertex3f(0 - MAP_SIZE, 0 + MAP_SIZE, 0);
			glEnd();
			glPopMatrix();
		} else {
			// Dessin de la carte
			glPushMatrix();
			drawMap();

			glColor3f(0, 0, 1);
			drawSnakeHead(x, y, 0, SNAKE_SIZE * 2);
			// Dessin du serpent
			// draw3DQuad(xTemp, yTemp, 0, SNAKE_SIZE * 2);
			for (int i = 0; i < positions.size(); i++) {
				draw3DQuad(positions.get(i).getX(), positions.get(i).getY(), 0,
						SNAKE_SIZE * 2);
			}

			// Draw Walls
			glColor3f(1, 0, 0);
			for (int i = 0; i < walls.size(); i++) {
				draw3DQuad(walls.get(i).getX(), walls.get(i).getY(), 0,
						WALL_SIZE * 2);
			}

			// Dessin de la pomme
			glColor3f(0, 1, 0);
			apple.draw();
			//drawApple(xPomme, yPomme, 0, APPLE_SIZE * 2);
			glPopMatrix();
		}

		// draw3DQuad(x, y, 0, 10);
	}

	private void drawMap() {
		// Navigable map
		glBegin(GL_QUADS);
		glColor3f(0, 1, 1);
		glVertex3f(0 - MAP_SIZE, 0 - MAP_SIZE, 0);
		glColor3f(1, 1, 0);
		glVertex3f(0 + MAP_SIZE, 0 - MAP_SIZE, 0);
		glColor3f(1, 1, 1);
		glVertex3f(0 + MAP_SIZE, 0 + MAP_SIZE, 0);
		glColor3f(1, 0, 1);
		glVertex3f(0 - MAP_SIZE, 0 + MAP_SIZE, 0);
		glEnd();
		// Walls around it
		
		for (int i = -MAP_SIZE; i < MAP_SIZE; i+=WALL_SIZE) {
			glColor3f(1, 0, 0);
			draw3DQuad(i, -(MAP_SIZE), 0, WALL_SIZE);
			glColor3f(1, 1, 0);
			draw3DQuad(i, (MAP_SIZE), 0, WALL_SIZE);
			glColor3f(1, 1, 1);
			draw3DQuad((MAP_SIZE), i, 0, WALL_SIZE);
			glColor3f(0, 1, 1);
			draw3DQuad(-(MAP_SIZE), i, 0, WALL_SIZE);
		}

	}

	private void drawApple(float x, float y, float z, int size) {
		draw3DQuad(x, y, z, APPLE_SIZE);
	}

	private void drawSnakeHead(float x, float y, float z, int size) {
		draw3DQuad(x, y, z, size);
	}

	public static void draw3DQuad(float x, float y, float z, float size) {
		float a = size / 2;

		glPushMatrix();
		glBegin(GL_QUADS);
		glVertex3f(x - a, y - a, z - a);
		glVertex3f(x + a, y - a, z - a);
		glVertex3f(x + a, y + a, z - a);
		glVertex3f(x - a, y + a, z - a);
		glVertex3f(x - a, y - a, z + a);
		glVertex3f(x + a, y - a, z + a);
		glVertex3f(x + a, y + a, z + a);
		glVertex3f(x - a, y + a, z + a);
		glVertex3f(x - a, y - a, z + a);
		glVertex3f(x + a, y - a, z + a);
		glVertex3f(x + a, y - a, z - a);
		glVertex3f(x - a, y - a, z - a);
		glVertex3f(x + a, y + a, z + a);
		glVertex3f(x - a, y + a, z + a);
		glVertex3f(x - a, y + a, z - a);
		glVertex3f(x + a, y + a, z - a);
		glEnd();
		glPopMatrix();

	}

	private void initGL() {
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		glClearDepth(1.0f); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Really Nice
															// Perspective
															// Calculations
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-100, 100, 100, -100, 100, -100);
		glMatrixMode(GL_MODELVIEW);
		// font = new GLFont( new Font("Trebuchet", Font.BOLD, 18) );
	}

	public static void main(String[] argv) throws LWJGLException,
			InterruptedException, FileNotFoundException {
		Snake displayExample = new Snake();
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