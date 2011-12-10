package src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import tools.*;

public class SnakeGame {

	GLFont font;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;

	public static final int SNAKE_SIZE = 3;
	public static final int MAP_SIZE = 90;
	public static final int APPLE_SIZE = 2;
	public static final int WALL_SIZE = 5;

	static int textureMur = 0;
	static int textureSerpent = 0;
	static int textureSol = 0;
	// public List<Position> positions = new ArrayList<Position>();
	public static List<Position> walls = new ArrayList<Position>();

	float lightPosition1[] = { -MAP_SIZE, -MAP_SIZE, 1f, 1f };

	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	public boolean switchView = false;

	public static boolean exit = false;

	public boolean perdu = false;

	public boolean appleEat = false;

	Apple apple;
	Snake snake;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SnakeGame [perdu=" + perdu + ", appleEat=" + appleEat
				+ ", apple=" + apple + ", snake=" + snake + "]";
	}

	public int chooseBestDisplay() throws LWJGLException {
		int res = 3;
		return res;
	}

	public void start() throws LWJGLException, InterruptedException,
			FileNotFoundException {

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

		initializeGame();
		initGL();
		getDelta();
		lastFPS = getTime();

		while (!Display.isCloseRequested() && !exit) { // Done Drawing The Quad
			if (perdu)
				System.out.println(this
						+ " "
						+ (new Position(snake.getX(), snake.getY())
								.checkCollapse(walls, WALL_SIZE)));
			int delta = getDelta();
			update(delta);
			renderGL();
			pollInput();
			Display.update(); // flushes OpenGL pipeline and swaps back and
								// front buffers. perhaps waits for v-sync.
		}

		Display.destroy();
	}

	private void initializeGame() throws FileNotFoundException {
		snake = new Snake();
		walls = MazeReader.buildWallList("maze.txt");
		apple = new Apple();
		// Initialize Snake start_position
		snake.intialize(walls, WALL_SIZE);

	}

	private void update(int delta) throws InterruptedException {
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			if (!(snake.getMouvement() == RIGHT))
				snake.setMouvement(LEFT);
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			if (!(snake.getMouvement() == LEFT))
				snake.setMouvement(RIGHT);
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			if (!(snake.getMouvement() == DOWN))
				snake.setMouvement(UP);
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			if (!(snake.getMouvement() == UP))
				snake.setMouvement(DOWN);

		switch (snake.getMouvement()) {
		case LEFT:
			snake.setX(snake.getX() - 0.05f * delta);
			break;
		case RIGHT:
			snake.setX(snake.getX() + 0.05f * delta);
			break;
		case UP:
			snake.setY(snake.getY() - 0.05f * delta);
			break;
		case DOWN:
			snake.setY(snake.getY() + 0.05f * delta);
			break;
		}
		// Adding a new position for snake
		appleEat = snake.addPosition(appleEat);

		Position actual = new Position(snake.getX(), snake.getY());
		// Check for the wall collision

		if (!perdu)
			perdu = actual.checkCollapse(walls, WALL_SIZE);

		if (snake.getX() < -MAP_SIZE + SNAKE_SIZE) {
			snake.setY(-MAP_SIZE + SNAKE_SIZE);
			perdu = true;
		}
		if (snake.getX() > MAP_SIZE - SNAKE_SIZE) {
			snake.setY(MAP_SIZE - SNAKE_SIZE);
			perdu = true;
		}
		if (snake.getY() < -MAP_SIZE + SNAKE_SIZE) {
			snake.setY(-MAP_SIZE + SNAKE_SIZE);
			perdu = true;
		}
		if (snake.getY() > MAP_SIZE - SNAKE_SIZE) {
			snake.setY(MAP_SIZE - SNAKE_SIZE);
			perdu = true;
		}

		// Check Apple detection
		if (snake.getX() - SNAKE_SIZE < apple.getX()
				&& snake.getX() + SNAKE_SIZE > apple.getX()
				&& snake.getY() - SNAKE_SIZE < apple.getY()
				&& snake.getY() + SNAKE_SIZE > apple.getY()) {
			snake.setLenght(snake.lenght + 1);
			appleEat = true;
			apple = new Apple();
		}

		// Check Snake hitting himself
		if (!perdu)
			perdu = actual.checkCollapse(snake.positions, SNAKE_SIZE / 2);
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
		GLU.gluLookAt(0f, -3f, 33f, // where is the eye
				0f, -3f, 0f, // what point are we looking at
				0f, 1f, 0f); // which way is up
		// font.print(1, 1, "test");
		// GLApp.print( 40, 210,
		// "Text rendered with the default GLApp.print() function uses");
		glLoadIdentity();
		GLU.gluPerspective(40f, // zoom in or out of view
				0, // shape of viewport rectangle
				.1f, // Min Z: how far from eye position does view start
				500f); // max Z: how far from eye position does view extend

		if (switchView) {
			glRotatef(-85, 1, 0, 0);
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
			glBindTexture(GL_TEXTURE_2D, textureSol);
			drawMap();
			glBindTexture(GL_TEXTURE_2D, textureSerpent);
			snake.draw();
			// Draw Walls
			glBindTexture(GL_TEXTURE_2D, textureMur);
			glColor3f(1, 1, 1);
			for (int i = 0; i < walls.size(); i++) {
				draw3DQuad(walls.get(i).getX(), walls.get(i).getY(), 0,
						WALL_SIZE * 2);
			}

			// Dessin de la pomme
			apple.draw();
			// drawApple(xPomme, yPomme, 0, APPLE_SIZE * 2);
			glPopMatrix();
		}

		// draw3DQuad(x, y, 0, 10);
	}

	private void drawMap() {
		// Navigable map
		glBegin(GL_QUADS);
		glColor3f(1f, 0.5f, 0.5f);
		glVertex3f(0 - (MAP_SIZE + WALL_SIZE), 0 - (MAP_SIZE + WALL_SIZE), 0.1f);
		glVertex3f(0 + MAP_SIZE + WALL_SIZE, 0 - (MAP_SIZE + WALL_SIZE), 0.1f);
		glVertex3f(0 + MAP_SIZE + WALL_SIZE, 0 + MAP_SIZE + WALL_SIZE, 0.1f);
		glVertex3f(0 - (MAP_SIZE + WALL_SIZE), 0 + MAP_SIZE + WALL_SIZE, 0.1f);

		glColor3f(1, 1, 1);
		glTexCoord2d(0, 5);
		glVertex3f(0 - MAP_SIZE, 0 - MAP_SIZE, 0);
		glTexCoord2d(5, 5);
		glVertex3f(0 + MAP_SIZE, 0 - MAP_SIZE, 0);
		glTexCoord2d(5, 0);
		glVertex3f(0 + MAP_SIZE, 0 + MAP_SIZE, 0);
		glTexCoord2d(0, 0);
		glVertex3f(0 - MAP_SIZE, 0 + MAP_SIZE, 0);

		glEnd();

		// Walls around it
		/*
		 * for (int i = -MAP_SIZE; i <= MAP_SIZE; i += WALL_SIZE) { glColor3f(1,
		 * 0, 0); draw3DQuad(i, -(MAP_SIZE+WALL_SIZE/2), -WALL_SIZE, WALL_SIZE);
		 * draw3DQuad(i, (MAP_SIZE+WALL_SIZE/2), -WALL_SIZE, WALL_SIZE);
		 * draw3DQuad((MAP_SIZE+WALL_SIZE/2), i, -WALL_SIZE, WALL_SIZE);
		 * draw3DQuad(-(MAP_SIZE+WALL_SIZE/2), i, -WALL_SIZE, WALL_SIZE); }
		 */

	}

	public static void draw3DQuad(float x, float y, float z, float size) {
		float a = size / 2;

		glPushMatrix();
		glBegin(GL_QUADS);
		// glColor3f(1, 0, 0);
		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, z - a);
		glTexCoord2d(1, 1);
		glVertex3f(x + a, y - a, z - a);
		glTexCoord2d(1, 0);
		glVertex3f(x + a, y + a, z - a);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y + a, z - a);
		// glColor3f(1, 1, 0);
		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, z + a);
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y - a, z + a);
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y + a, z + a);
		glTexCoord2d(0, 1);
		glVertex3f(x - a, y + a, z + a);
		// glColor3f(1, 0, 1);
		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, z + a);
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y - a, z + a);
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y - a, z - a);
		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, z - a);
		// glColor3f(1, 1, 1);
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y + a, z + a);
		glTexCoord2d(0, 1);
		glVertex3f(x - a, y + a, z + a);
		glTexCoord2d(0, 1);
		glVertex3f(x - a, y + a, z - a);
		glTexCoord2d(0, 1);
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
		glEnable(GL_TEXTURE_2D);
		textureMur = GLApp.makeTexture("texture/metal091.jpg");
		textureSerpent = GLApp.makeTexture("texture/serpent.jpg");
		textureSol = GLApp.makeTexture("texture/herbe.jpg");
		 

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-100, 100, 100, -100, 100, -100);
		glMatrixMode(GL_MODELVIEW);
		font = new GLFont(new Font("Trebuchet", Font.BOLD, 18));
	}

	public static void main(String[] argv) throws LWJGLException,
			InterruptedException, FileNotFoundException {
		SnakeGame displayExample = new SnakeGame();
		displayExample.start();
	}

	public void pollInput() throws LWJGLException, FileNotFoundException {

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
				if (Keyboard.getEventKey() == Keyboard.KEY_R) {
					System.out.println("R Key Pressed - Game restart");
					initializeGame();
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