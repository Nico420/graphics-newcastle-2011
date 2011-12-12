package src;

import static org.lwjgl.opengl.GL11.*;

import java.io.FileNotFoundException;
import java.io.IOException;
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

	public static final int LEFT = 3;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 4;

	public static final int SNAKE_SIZE = 3;
	public static final int MAP_SIZE = 90;
	public static final int APPLE_SIZE = 2;
	public static final int WALL_SIZE = 5;

	TextureLoader tl = new TextureLoader();

	static Texture textureMur;
	static Texture textureSerpent;
	static Texture textureSol;
	// public List<Position> positions = new ArrayList<Position>();
	public static List<Position> walls = new ArrayList<Position>();

	float lightPosition1[] = { -MAP_SIZE, -MAP_SIZE, 1f, 1f };

	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	public static int menuChoice = 0;
	public static int menuChoiceTemp = 0;

	public static boolean switchView = false;

	public static boolean exit = false;

	public boolean perdu = false;

	public boolean appleEat = false;

	public float countDown = 3;
	Apple apple;
	public static float rotation;
	static Snake snake;

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
			IOException {

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

		initGL();
		initializeGame();
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

	private void gameHome() throws IOException {

		/*
		 * float itemWidth = 80; float itemHeight = 10;
		 */

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		// Title

		// Creating and using texture
		Texture start = tl.getTexture("texture/start.png");
		Texture icone = tl.getTexture("texture/snake_icone.png");
		Texture pomme = tl.getTexture("texture/pomme_ml.png");
		// Selector
		icone.bind();
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2d(1, 0);
		glVertex3f(-90, -90, 0);
		glTexCoord2d(1, 1);
		glVertex3f(-90, -70, 0);
		glTexCoord2d(0, 1);
		glVertex3f(-70, -70, 0);
		glTexCoord2d(0, 0);
		glVertex3f(-70, -90, 0);
		glEnd();
		
		
		pomme.bind();
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2d(1, 0);
		glVertex3f(-50, -15 + 30 * menuChoiceTemp, 0);
		glTexCoord2d(1, 1);
		glVertex3f(-50, -5 + 30 * menuChoiceTemp, 0);
		glTexCoord2d(0, 1);
		glVertex3f(-60, -5 + 30 * menuChoiceTemp, 0);
		glTexCoord2d(0, 0);
		glVertex3f(-60, -15 + 30 * menuChoiceTemp, 0);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3f(-50, -80, 0);
		glVertex3f(-50, -50, 0);
		glVertex3f(50, -50, 0);
		glVertex3f(50, -80, 0);
		glEnd();

		
		start.bind();
		glBegin(GL_QUADS);
		// Start New game
		glTexCoord2d(0, 0);
		glVertex3f(-40, -20, 0);
		glTexCoord2d(0, 1);
		glVertex3f(-40, 0, 0);
		glTexCoord2d(1, 1);
		glVertex3f(40, 0, 0);
		glTexCoord2d(1, 0);
		glVertex3f(40, -20, 0);
		glEnd();

		glBegin(GL_QUADS);
		// Get HIgh Score
		glVertex3f(-40, 30, 0);
		glVertex3f(-40, 10, 0);
		glVertex3f(40, 10, 0);
		glVertex3f(40, 30, 0);
		glEnd();

		glBegin(GL_QUADS);
		// Exit
		glVertex3f(-40, 60, 0);
		glVertex3f(-40, 40, 0);
		glVertex3f(40, 40, 0);
		glVertex3f(40, 60, 0);
		glEnd();
		/*
		 * glVertex3f(-50, -80, 0); glVertex3f(-50, -60, 0); glVertex3f(50, -60,
		 * 0); glVertex3f(50, -80, 0);
		 */

	}

	private void initializeGame() throws FileNotFoundException {
		snake = new Snake();
		walls = MazeReader.buildWallList("maze.txt");
		apple = new Apple();
		// Initialize Snake start_position
		snake.intialize(walls, WALL_SIZE);
	}

	private void update(int delta) throws InterruptedException, LWJGLException {

		if (menuChoice == 0) {
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
						menuChoiceTemp = (menuChoiceTemp - 1) % 3;
						if (menuChoiceTemp < 0)
							menuChoiceTemp = 2;
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						menuChoiceTemp = (menuChoiceTemp + 1) % 3;
						if (menuChoiceTemp < 0)
							menuChoiceTemp = 2;
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
						menuChoice = menuChoiceTemp + 1;
					}
				}
			}
		} else {
			if (countDown > 0) {
				countDown -= 0.001f;
			} else {
				if (!perdu) {

					System.out.println(countDown);
					// Update snakeHead position and rotation
					snake.update(delta, switchView);
					// Adding a new position for snake, and notify snake lenght
					// if
					// needed.
					appleEat = snake.addPosition(appleEat);
					perdu = snake.checkWallCollision(walls);
					// Check Apple detection
					if (snake.getX() - SNAKE_SIZE < apple.getX()
							&& snake.getX() + SNAKE_SIZE > apple.getX()
							&& snake.getY() - SNAKE_SIZE < apple.getY()
							&& snake.getY() + SNAKE_SIZE > apple.getY()) {
						snake.setLenght(snake.lenght + 1);
						appleEat = true;
						apple = new Apple();
					}
				}
			}
		}
		updateFPS(); // update FPS Counter

	}

	private void drawCountDown(float number) {
		glColor3f(0, 0, 1);
		glBegin(GL_QUADS);
		// Start New game
		glVertex3f(-40, -20, 0);
		glVertex3f(-40, 0, 0);
		glVertex3f(40, 0, 0);
		glVertex3f(40, -20, 0);
		glEnd();

	}

	private void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;

	}

	private void renderGL() throws LWJGLException, IOException {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear
															// The
															// Screen
															// And
															// The
															// Depth
															// Buffer

		if (menuChoice == 0) {
			gameHome();
		} else if (menuChoice == 1) {
			if (countDown > 0) {
				drawCountDown(countDown);
			} else {

				glViewport(100, 100, Display.getWidth() - 100,
						Display.getHeight() - 100);
				glLoadIdentity();
				// Dessin de la carte
				glPushMatrix();
				setCamera();
				// glBindTexture(GL_TEXTURE_2D, textureSol);
				drawMap();
				textureSerpent.bind();
				// glBindTexture(GL_TEXTURE_2D, textureSerpent);
				snake.draw();
				// Draw Walls
				textureMur.bind();
				// glBindTexture(GL_TEXTURE_2D, textureMur);
				glColor3f(1, 1, 1);
				for (int i = 0; i < walls.size(); i++) {
					draw3DQuad(walls.get(i).getX(), walls.get(i).getY(),
							-WALL_SIZE, WALL_SIZE * 2);
				}
				apple.draw();
				glPopMatrix();
			}
		} else if (menuChoice == 2) {
			// affichage HIGHSCORE
		} else if (menuChoice == 3) {
			exit = true;
		}
	}

	public static void setCamera() {
		if (switchView) {
			float distance = 0.5f;
			switch (snake.getDirection()) {
			case LEFT:
				GLU.gluLookAt(snake.getX() - distance, snake.getY(), 1f, // where
						// is
						// the
						// eye
						snake.getX(), snake.getY(), 0f, // what point are we
														// looking
														// at
						0f, 0f, 1f); // which way is up
				break;
			case DOWN:
			default:
				GLU.gluLookAt(snake.getX(), snake.getY() + distance, 1f, // where
						// is
						// the
						// eye
						snake.getX(), snake.getY(), 0f, // what point are we
														// looking
														// at
						0f, 0f, 1f); // which way is up
				break;
			case UP:
				GLU.gluLookAt(snake.getX(), snake.getY() - distance, 1f, // where
						// is
						// the
						// eye
						snake.getX(), snake.getY(), 0f, // what point are we
														// looking
														// at
						0f, 0f, 1f); // which way is up
				break;
			case RIGHT:
				GLU.gluLookAt(snake.getX() + distance, snake.getY(), 1f, // where
						// is
						// the
						// eye
						snake.getX(), snake.getY(), 0f, // what point are we
														// looking
														// at
						0f, 0f, 1f); // which way is up
				break;
			}

		} else {
			glRotatef(-90, 0, 0, 1);
			GLU.gluLookAt(-10, -10, 20, // where is the eye
					0, 0, 0f, // what point are we looking at
					1f, 0f, 0f); // which way is up
		}

	}

	private void drawMap() {
		// Navigable map
		textureSol.bind();
		glBegin(GL_QUADS);
		glColor3f(1f, 0.5f, 0.5f);
		glVertex3f(0 - (MAP_SIZE + WALL_SIZE), 0 - (MAP_SIZE + WALL_SIZE), 0.1f);
		glVertex3f(0 + MAP_SIZE + WALL_SIZE, 0 - (MAP_SIZE + WALL_SIZE), 0.1f);
		glVertex3f(0 + MAP_SIZE + WALL_SIZE, 0 + MAP_SIZE + WALL_SIZE, 0.1f);
		glVertex3f(0 - (MAP_SIZE + WALL_SIZE), 0 + MAP_SIZE + WALL_SIZE, 0.1f);

		glColor3f(1, 1, 1);
		glTexCoord2d(0, 0.5);
		glVertex3f(0 - MAP_SIZE, 0 - MAP_SIZE, 0);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(0 + MAP_SIZE, 0 - MAP_SIZE, 0);
		glTexCoord2d(0.5, 0);
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
		/*
		 * glTexCoord2d(0, 1); glVertex3f(x - a, y - a, z + a); glTexCoord2d(0,
		 * 1); glVertex3f(x + a, y - a, z + a); glTexCoord2d(0, 1); glVertex3f(x
		 * + a, y + a, z + a); glTexCoord2d(0, 1); glVertex3f(x - a, y + a, z +
		 * a);
		 */
		// glColor3f(1, 0, 1);
		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, z + a);
		glTexCoord2d(1, 1);
		glVertex3f(x + a, y - a, z + a);
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y - a, z - a);
		glTexCoord2d(1, 0);
		glVertex3f(x - a, y - a, z - a);
		// glColor3f(1, 1, 1);
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y + a, z + a);
		glTexCoord2d(1, 1);
		glVertex3f(x - a, y + a, z + a);
		glTexCoord2d(1, 0);
		glVertex3f(x - a, y + a, z - a);
		glTexCoord2d(0, 0);
		glVertex3f(x + a, y + a, z - a);

		/*
		 * glTexCoord2d(0, 1); glVertex3f(x - a, y - a, z - a); glTexCoord2d(1,
		 * 1); glVertex3f(x + a, y - a, z - a); glTexCoord2d(1, 0); glVertex3f(x
		 * - a, y - a, z + a); glTexCoord2d(0, 0); glVertex3f(x + a, y - a, z +
		 * a);
		 * 
		 * glTexCoord2d(1, 0); glVertex3f(x + a, y + a, z - a); glTexCoord2d(0,
		 * 0); glVertex3f(x - a, y + a, z - a); glTexCoord2d(0, 1); glVertex3f(x
		 * + a, y + a, z + a); glTexCoord2d(1, 1); glVertex3f(x - a, y + a, z +
		 * a);
		 */

		glEnd();
		glPopMatrix();

	}

	private void initGL() throws IOException {
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		glClearDepth(1.0f); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Really Nice
															// Perspective
															// Calculations
		glEnable(GL_TEXTURE_2D);
		textureSerpent = tl.getTexture("texture/serpent.jpg");
		textureSol = tl.getTexture("texture/herbe.jpg");
		textureMur = tl.getTexture("texture/metal.jpg");

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-100, 100, 100, -100, 100, -100);
		glMatrixMode(GL_MODELVIEW);
	}

	public static void main(String[] argv) throws LWJGLException,
			InterruptedException, IOException {
		SnakeGame game = new SnakeGame();
		game.start();
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
					perdu = false;
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

	private int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
}