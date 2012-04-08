package state;

import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LIGHT1;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_NORMALIZE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import src.Apple;
import src.BlueApple;
import src.Eatable;
import src.GoldApple;
import src.Snake;
import src.SnakeGame;
import tools.Fichier;
import tools.MazeReader;
import tools.Position;

/**
 * Game state. In this state, you control the snake, interact with the map,...
 * 
 * @author Nicolas
 * 
 */

public class Game extends Etat {

	/** Apple size */
	public static final int APPLE_SIZE = 2;
	/** Bullet time */
	private static final int BULLET_TIME = 5;

	/** Bullet time */
	private static int bulletTime;
	/** Bullet time timer */
	private static int bulletTimeTimer = 0;
	/** Death timing */
	private static final int DEATH_TIMING = 500;

	/** Down direction */
	public static final int DOWN = 4;

	/** Left direction */
	public static final int LEFT = 3;
	/** Map size */
	public static final int MAP_SIZE = 100;

	/** Snake death */
	private static int mortSerpent;

	/** Eatables' list */
	private static ArrayList<Eatable> object = new ArrayList<Eatable>();

	/** Multi point */
	private static int pointMulti = 0;

	/** Right direction */
	public static final int RIGHT = 1;

	/** Rotation */
	private static float rotation;

	/** Snake size */
	public static final int SNAKE_SIZE = 3;

	/** Bullet time speed */
	private static float speedBullet = 0.02f;

	/** Tail reduce */
	private static int tailReduce = 0;

	/** Up direction */
	public static final int UP = 2;

	/** Wall size */
	public static final int WALL_SIZE = 5;

	/** Wall position list */
	private static List<Position> walls = new ArrayList<Position>();
	/**
	 * Draw walls
	 * 
	 * @param x
	 *            Wall position
	 * @param y
	 *            Wall position
	 * @param z
	 *            Wall position
	 * @param size
	 *            Wall size
	 */
	public static void drawMur(float x, float y, float z, float size) {
		float a = size / 2;

		glBegin(GL_QUADS);

		// glNormal3f(0, 0, -1);
		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, 0);
		glTexCoord2d(1, 1);
		glVertex3f(x + a, y - a, 0);
		glTexCoord2d(1, 0);
		glVertex3f(x + a, y + a, 0);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y + a, 0);

		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, size);
		glTexCoord2d(1, 1);
		glVertex3f(x + a, y - a, size);
		glTexCoord2d(1, 0);
		glVertex3f(x + a, y + a, size);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y + a, size);

		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, size);
		glTexCoord2d(1, 1);
		glVertex3f(x + a, y - a, size);
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y - a, 0);
		glTexCoord2d(1, 0);
		glVertex3f(x - a, y - a, 0);

		glTexCoord2d(0, 1);
		glVertex3f(x + a, y + a, size);
		glTexCoord2d(1, 1);
		glVertex3f(x - a, y + a, size);
		glTexCoord2d(1, 0);
		glVertex3f(x - a, y + a, 0);
		glTexCoord2d(0, 0);
		glVertex3f(x + a, y + a, 0);

		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, 0);
		glTexCoord2d(1, 1);
		glVertex3f(x - a, y + a, 0);
		glTexCoord2d(1, 0);
		glVertex3f(x - a, y + a, size);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y - a, size);

		glTexCoord2d(1, 0);
		glVertex3f(x + a, y + a, 0);
		glTexCoord2d(0, 0);
		glVertex3f(x + a, y - a, 0);
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y - a, size);
		glTexCoord2d(1, 1);
		glVertex3f(x + a, y + a, size);

		glEnd();

	}
	/**
	 * Eatables' list
	 * 
	 * @return List of Object
	 */
	public static ArrayList<Eatable> getObject() {
		return object;
	}

	/**
	 * Get multi points
	 * 
	 * @return Multi points
	 */
	public static int getPointMulti() {
		return pointMulti;
	}

	/**
	 * Get rotation
	 * 
	 * @return Rotation
	 */
	public static float getRotation() {
		return rotation;
	}

	/**
	 * Get the walls' list
	 * 
	 * @return List
	 */
	public static List<Position> getWalls() {
		return walls;
	}

	/**
	 * Set a new list
	 * 
	 * @param pObject
	 *            The new list
	 */
	public static void setObject(ArrayList<Eatable> pObject) {
		Game.object = pObject;
	}

	/**
	 * Set new multi points
	 * 
	 * @param pPointMulti
	 *            New multi points
	 */
	public static void setPointMulti(int pPointMulti) {
		Game.pointMulti = pPointMulti;
	}

	/**
	 * Set a new rotation
	 * 
	 * @param pRotation
	 *            New rotation to set
	 */
	public static void setRotation(float pRotation) {
		Game.rotation = pRotation;
	}

	/**
	 * Set a new walls' list
	 * 
	 * @param pWalls
	 *            New list
	 */
	public static void setWalls(List<Position> pWalls) {
		Game.walls = pWalls;
	}

	/** Apple eat */
	private int appleEat = 0;;

	/** Delta */
	private int delta;

	/** Lost or not */
	private boolean perdu = false;

	/** Snake */
	private Snake snake;

	/** Speed */
	private float speed;

	/** Texture */
	private Texture textureMur;

	/** Texture */
	private Texture textureSerpent;

	/** Texture */
	private Texture textureSol;

	/**
	 * Creating the game state
	 * 
	 * @param snakeGame
	 *            Game instance
	 */
	public Game(SnakeGame snakeGame) {
		super(snakeGame);
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("snake.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		speed = Float.parseFloat(prop.getProperty("speed"));
		speedBullet = Float.parseFloat(prop.getProperty("speedBullet"));
		snakeGame.setAppleNumber(Integer.parseInt(prop
				.getProperty("APPLENUMBER")));

		initializeGame();
		initGL();
	}

	/**
	 * Display power.
	 */
	private void affichePower() {
		getFontPower().drawString(20, 250, "POWERS UP", Color.yellow);
		if (tailReduce > 0) {
			getFontPower().drawString(20, 300, "Tail reduction", Color.green);
			tailReduce -= getDelta();
		} else {
			getFontPower().drawString(20, 300, "Tail reduction", Color.red);
		}
		if (getPointMulti() > 0) {
			getFontPower().drawString(20, 340, "Points X5", Color.green);
			setPointMulti(getPointMulti() - getDelta());
		} else {
			getFontPower().drawString(20, 340, "Points X5", Color.red);
		}
		if (bulletTimeTimer > 0) {
			getFontPower().drawString(20, 380,
					"(B)ullet-time (" + bulletTime + ")", Color.green);
			bulletTimeTimer -= getDelta();
		} else {
			getFontPower().drawString(20, 380,
					"(B)ullet-time (" + bulletTime + ")", Color.red);
			snake.setSpeed(speed);
		}

	}

	/**
	 * Display shortcuts
	 */
	private void afficheRaccourci() {

		getFont().drawString(20, 450, "Shortcuts : ");
		getFont().drawString(20, 470, "(R) : Restart");
		getFont().drawString(20, 490, "(A) : Full screen");
		getFont().drawString(20, 510, "(V) : Switch view");
		getFont().drawString(20, 530, "(Esc) : Quit");

	}

	/**
	 * Display score
	 * 
	 * @param s
	 *            Snake score to display
	 */
	private void afficheScore(Snake s) {
		getFontMenu().drawString(20, 50, "Snake 3D", Color.blue);
		getFontPower().drawString(20, 150, "Score : \n" + s.getScore(),
				Color.red);
	}

	/**
	 * Create the game
	 */
	private void creationJeu() {
		// Dessin de la carte
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		GLU.gluPerspective(90.0f,
				(float) Display.getWidth() / (float) Display.getHeight(), 0.1f,
				500.0f);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glEnable(GL_COLOR_MATERIAL);
		// transform for camera
		setCamera();

		glPushMatrix();

		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT1); // Enable Light One
		glEnable(GL_NORMALIZE);
		drawMap();
		textureSerpent.bind();
		snake.draw();
		// Draw Walls
		textureMur.bind();
		// glColor3f(1, 1, 1);

		for (int i = 0; i < getObject().size(); i++) {
			getObject().get(i).draw();
		}
		glPopMatrix();

	}

	/**
	 * Create the text
	 */
	private void creationTexte() {

		glMatrixMode(GL_PROJECTION);
		glDisable(GL_LIGHTING);
		glLoadIdentity();
		glOrtho(0, SnakeGame.WIDTH, SnakeGame.HEIGHT, 0, 100, -100);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		afficheScore(snake);
		afficheRaccourci();
		affichePower();

	}

	/**
	 * Draw the field
	 */
	private void drawMap() {
		glPushMatrix();
		// Navigable map
		textureSol.bind();
		glTranslated(SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getY(),
				0);
		glBegin(GL_QUADS);

		glColor3f(1, 1, 1);
		glNormal3f(0, 0, -1);
		glTexCoord2d(0, 0.5);
		glVertex3f(0 - MAP_SIZE, 0 - MAP_SIZE, 0);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(0 + MAP_SIZE, 0 - MAP_SIZE, 0);
		glTexCoord2d(0.5, 0);
		glVertex3f(0 + MAP_SIZE, 0 + MAP_SIZE, 0);
		glTexCoord2d(0, 0);
		glVertex3f(0 - MAP_SIZE, 0 + MAP_SIZE, 0);
		glEnd();
		// Walls around it and in it
		for (int i = -MAP_SIZE; i <= MAP_SIZE; i += WALL_SIZE) {
			// glColor3f(1, 0, 0);
			textureMur.bind();
			glPushMatrix();
			// setCamera();
			drawMur(i, -(MAP_SIZE + WALL_SIZE / 2), WALL_SIZE, WALL_SIZE);
			drawMur(i, (MAP_SIZE + WALL_SIZE / 2), WALL_SIZE, WALL_SIZE);
			drawMur((MAP_SIZE + WALL_SIZE / 2), i, WALL_SIZE, WALL_SIZE);
			drawMur(-(MAP_SIZE + WALL_SIZE / 2), i, WALL_SIZE, WALL_SIZE);
			glPopMatrix();
		}
		for (int i = 0; i < getWalls().size(); i++) {
			drawMur(getWalls().get(i).getX(), getWalls().get(i).getY(),
					WALL_SIZE, WALL_SIZE * 2);
		}
		glPopMatrix();
	}

	/**
	 * Get delta
	 * 
	 * @return delta
	 */
	public int getDelta() {
		return delta;
	}

	@Override
	protected void initGL() {

		glClearDepth(1.0f); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Really Nice
															// Perspective
															// Calculations
		// glEnable(GL_COLOR_MATERIAL);
		glEnable(GL_TEXTURE_2D);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SnakeGame.WIDTH, SnakeGame.HEIGHT, 0, 100, -100);
		glMatrixMode(GL_MODELVIEW);

		// Light Creation
		float lDR = 10.0f;
		float[] lightAmbient = { 0.5f, 0.5f, 0.5f, 1.0f }; // Ambient Light
															// Values
		float[] lightDiffuse = { lDR, lDR, lDR, 1.0f }; // Diffuse LightValues
		// light Coming from a corner
		float[] lightPosition = { SnakeGame.MAP_MILIEU.getX(),
				SnakeGame.MAP_MILIEU.getY(), 10.0f, 10.0f }; // Light Position

		ByteBuffer temp = ByteBuffer.allocateDirect(16);
		temp.order(ByteOrder.nativeOrder());
		glLight(GL_LIGHT1, GL_AMBIENT,
				(FloatBuffer) temp.asFloatBuffer().put(lightAmbient).flip()); // Setup
																				// The
																				// Ambient
																				// //Light
		glLight(GL_LIGHT1, GL_DIFFUSE,
				(FloatBuffer) temp.asFloatBuffer().put(lightDiffuse).flip()); // Setup
																				// The
																				// Diffuse
		// Light
		glLight(GL_LIGHT1, GL_POSITION,
				(FloatBuffer) temp.asFloatBuffer().put(lightPosition).flip()); // Position
																				// The
																				// //
																				// Light

	}

	/**
	 * Initialize the game.
	 * 
	 */
	private void initializeGame() {
		bulletTime = BULLET_TIME;
		mortSerpent = DEATH_TIMING;
		snake = new Snake("Nico", new ArrayList<Position>(), 0f, 0f,
				Color.blue, 0, this.speed);
		setWalls(MazeReader.buildWallList("maze.txt"));
		if (getWalls().size() == 0) {
			float x = (float) (-((Game.MAP_SIZE) - Game.SNAKE_SIZE) + ((Game.MAP_SIZE) * 2 - Game.SNAKE_SIZE)
					* Math.random());
			float y = (float) (-((Game.MAP_SIZE) - Game.SNAKE_SIZE) + ((Game.MAP_SIZE) * 2 - Game.SNAKE_SIZE)
					* Math.random());
			getWalls().add(new Position(x, y));
		}
		setObject(new ArrayList<Eatable>());
		for (int i = 0; i < getSnakeGame().getAppleNumber(); i++) {
			// Randow other object
			getObject().add(new Apple());
		}
		// Initialize Snake start_position
		snake.initialize(getWalls(), WALL_SIZE);
		SnakeGame.setSwitchView(false);
		try {
			textureSerpent = TextureLoader.getTexture("JPG",
					ResourceLoader.getResourceAsStream("texture/serpent.jpg"));
			textureSol = TextureLoader.getTexture("JPG",
					ResourceLoader.getResourceAsStream("texture/herbe.jpg"));
			textureMur = TextureLoader.getTexture("JPG",
					ResourceLoader.getResourceAsStream("texture/metal.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Keyboard interaction
	 */
	public void pollInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if ((bulletTime > 0)
						&& Keyboard.getEventKey() == Keyboard.KEY_B) {
					snake.setSpeed(Game.speedBullet);
					bulletTime--;
					bulletTimeTimer = 6000;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					try {
						if (Display.isFullscreen()) {
							Display.setFullscreen(false);
						} else {
							Display.setFullscreen(true);
						}
					} catch (LWJGLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					this.getSnakeGame().setEtat(new Menu(this.getSnakeGame()));
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					if (SnakeGame.isSwitchView()) {
						SnakeGame.setSwitchView(false);
					} else {
						SnakeGame.setSwitchView(true);
					}

				}
				if (Keyboard.getEventKey() == Keyboard.KEY_R) {
					this.getSnakeGame().setEtat(new Game(this.getSnakeGame()));
				}

				// Change the snake color
				if (Keyboard.getEventKey() == Keyboard.KEY_U) {
					float nRed = snake.getC().getRed() + 10;
					nRed %= 256;
					snake.setC(new Color(nRed / 255,
							snake.getC().getGreen() / 255, snake.getC()
									.getBlue() / 255));
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_I) {
					float nGreen = snake.getC().getGreen() + 10;
					nGreen %= 256;
					snake.setC(new Color(snake.getC().getRed() / 255,
							nGreen / 255, snake.getC().getBlue() / 255));
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_O) {
					float nBlue = snake.getC().getBlue() + 10;
					nBlue %= 256;
					snake.setC(new Color(snake.getC().getRed() / 255, snake
							.getC().getGreen() / 255, nBlue / 255));
				}

				if ((Keyboard.getEventKey() == Keyboard.KEY_UP || Keyboard
						.getEventKey() == Keyboard.KEY_DOWN)
						&& SnakeGame.isSwitchView()
						&& snake.getDirection() == 0) {
					snake.setDirection(Game.DOWN);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT
						&& SnakeGame.isSwitchView()) {
					snake.setMouvement(Game.RIGHT);
					if (snake.getDirection() == 0) {
						snake.setDirection(Game.DOWN);
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT
						&& SnakeGame.isSwitchView()) {
					snake.setMouvement(Game.LEFT);
					if (snake.getDirection() == 0) {
						snake.setDirection(Game.DOWN);
					}
				}
			}
		}
	}

	@Override
	public void renderGL() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// Fichier Display
		creationTexte();
		creationJeu();
	}

	/**
	 * Set camera view
	 */
	public void setCamera() {
		if (SnakeGame.isSwitchView()) {
			float zoom = 40f;
			float reculCam = 30;
			switch (snake.getDirection()) {
			case LEFT:
				glRotatef(270, 0, 0, 1);
				GLU.gluLookAt(SnakeGame.MAP_MILIEU.getX() + snake.getX()
						+ reculCam, SnakeGame.MAP_MILIEU.getY() + snake.getY(),
						zoom, SnakeGame.MAP_MILIEU.getX() + snake.getX(),
						SnakeGame.MAP_MILIEU.getY() + snake.getY(), 0f, 0f, 1f,
						0f);
				break;
			case DOWN:
			default:
				glRotatef(0, 0, 0, 1);
				GLU.gluLookAt(SnakeGame.MAP_MILIEU.getX() + snake.getX(),
						SnakeGame.MAP_MILIEU.getY() + snake.getY() - reculCam,
						zoom, SnakeGame.MAP_MILIEU.getX() + snake.getX(),
						SnakeGame.MAP_MILIEU.getY() + snake.getY(), 0f, 0f, 1f,
						0f);
				break;
			case UP:
				glRotatef(180, 0, 0, 1);
				GLU.gluLookAt(SnakeGame.MAP_MILIEU.getX() + snake.getX(),
						SnakeGame.MAP_MILIEU.getY() + snake.getY() + reculCam,
						zoom, SnakeGame.MAP_MILIEU.getX() + snake.getX(),
						SnakeGame.MAP_MILIEU.getY() + snake.getY(), 0f, 0f, 1f,
						0f);
				break;
			case RIGHT:
				glRotatef(90, 0, 0, 1);
				GLU.gluLookAt(SnakeGame.MAP_MILIEU.getX() + snake.getX()
						- reculCam, SnakeGame.MAP_MILIEU.getY() + snake.getY(),
						zoom, SnakeGame.MAP_MILIEU.getX() + snake.getX(),
						SnakeGame.MAP_MILIEU.getY() + snake.getY(), 0f, 0f, 1f,
						0f);
				break;
			}

		} else {
			glTranslatef(80, 10, 0);
			// Camera is on a corner,looking for the middle of the map.
			GLU.gluLookAt(SnakeGame.MAP_MILIEU.getX() - MAP_SIZE / 2 + 20,
					SnakeGame.MAP_MILIEU.getY() + 10, 150f,
					SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getY(),
					0f, 0f, 1f, 0f);
		}

	}

	/**
	 * Set a new delta
	 * 
	 * @param pDelta
	 *            new delta to set
	 */
	public void setDelta(int pDelta) {
		delta = pDelta;
	}

	@Override
	public void update(int pDelta) {
		this.setDelta(pDelta);
		if (!perdu) {

			snake.update(pDelta, SnakeGame.isSwitchView());
			// Adding a new position for snake, and notify snake lenght
			// if
			// needed.
			appleEat = snake.addPosition(appleEat);
			perdu = snake.checkWallCollision(getWalls());
			// Check Apple detection
			for (int i = 0; i < getObject().size(); i++) {
				Eatable item = getObject().get(i);
				if (snake.getX() - SNAKE_SIZE < item.getX()
						&& snake.getX() + SNAKE_SIZE > item.getX()
						&& snake.getY() - SNAKE_SIZE < item.getY()
						&& snake.getY() + SNAKE_SIZE > item.getY()) {
					snake.setLenght(snake.getLenght() + 1);
					appleEat = item.getAction();
					if (appleEat == Eatable.REDUCE) {
						tailReduce = 1000;
					} else if (appleEat == Eatable.MULTI) {
						setPointMulti(4000);
					}
					// Generate new item
					if (Math.random() > 0.9) {
						getObject().set(i, new BlueApple());
					} else if (Math.random() < 0.1) {
						getObject().set(i, new GoldApple());
					} else {
						getObject().set(i, new Apple());
					}
				}
			}
		} else {
			if (mortSerpent < 0) {
				Fichier.ecrire("highScore", snake.getScore(), snake.getName());
				this.getSnakeGame().setEtat(
						new Perdu(this.snake.getScore(), this.getSnakeGame()));
			} else {
				mortSerpent -= pDelta;
			}
		}
		updateFPS();
	}
}
