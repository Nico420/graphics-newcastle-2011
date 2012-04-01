package state;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import src.Apple;
import src.BlueApple;
import src.Eatable;
import src.GoldApple;
import src.Snake;
import src.SnakeGame;
import tools.MazeReader;
import tools.Position;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import tools.Fichier;

/**
 * Game state. In this state, you control the snake, interact with the map,...
 * 
 * @author Nicolas
 * 
 */
@SuppressWarnings("deprecation")
public class Game extends Etat {

	private static final int DEATH_TIMING = 500;
	private static final int BULLET_TIME = 5;
	public static final int LEFT = 3;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 4;

	HashMap<String, String> options = new HashMap<String, String>();
	public static final int SNAKE_SIZE = 3;
	public static final int MAP_SIZE = 100;
	public static final int APPLE_SIZE = 2;
	public static final int WALL_SIZE = 5;

	public static float SPEED;
	public static float SPEED_BULLET = 0.02f;

	Texture textureSerpent;
	Texture textureSol;
	Texture textureMur;

	private boolean perdu = false;

	public static int tailReduce = 0;
	public static int pointMulti = 0;
	public static int bulletTime;
	public static int mortSerpent;
	public int appleEat = 0;
	private static int bulletTimeTimer = 0;;

	private static ArrayList<Eatable> object = new ArrayList<Eatable>();
	public static float rotation;
	static Snake snake;

	private static int delta;

	public static List<Position> walls = new ArrayList<Position>();

	public Game() throws IOException {
		try {
			options = Fichier.lire("options.txt");
			SPEED = options.get("SPEED").equals(null) ? 0.07f : Float
					.parseFloat(options.get("SPEED"));
			SPEED_BULLET = options.get("SPEED_BULLET").equals(null) ? 0.02f
					: Float.parseFloat(options.get("SPEED_BULLET"));
			SnakeGame.APPLENUMBER = options.get("APPLENUMBER") == null ? 5
					: Integer.parseInt(options.get("APPLENUMBER"));
		} catch (Exception e) {
			System.err.println("Fichier d'options manquant ou incorrect.");

		}

		initializeGame();
		initGL();
	}

	@Override
	public int update(int delta) {
		Game.setDelta(delta);
		if (!perdu) {

			snake.update(delta, SnakeGame.switchView);
			// Adding a new position for snake, and notify snake lenght
			// if
			// needed.
			appleEat = snake.addPosition(appleEat);
			perdu = snake.checkWallCollision(walls);
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
						pointMulti = 4000;
					}
					// Generate new item
					if (Math.random() > 0.9)
						getObject().set(i, new BlueApple());
					else if (Math.random() < 0.1)
						getObject().set(i, new GoldApple());
					else
						getObject().set(i, new Apple());
				}
			}
		} else {
			if (mortSerpent < 0) {
				Fichier.ecrire("highScore.txt", snake.getScore());
				SnakeGame.score = snake.getScore();
				return SnakeGame.PERDU;
			} else
				mortSerpent -= delta;
		}
		updateFPS();
		return SnakeGame.GAME;
	}

	@Override
	public void renderGL(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// Score Display
		creationTexte();
		creationJeu();
	}

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

		for (int i = 0; i < getObject().size(); i++)
			getObject().get(i).draw();
		glPopMatrix();

	}

	private void creationTexte(){

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

	private void affichePower() {
		fontPower.drawString(20, 250, "POWERS UP", Color.yellow);
		if (tailReduce > 0) {
			fontPower.drawString(20, 300, "Tail reduction", Color.green);
			tailReduce -= getDelta();
		} else {
			fontPower.drawString(20, 300, "Tail reduction", Color.red);
		}
		if (pointMulti > 0) {
			fontPower.drawString(20, 340, "Points X5", Color.green);
			pointMulti -= getDelta();
		} else {
			fontPower.drawString(20, 340, "Points X5", Color.red);
		}
		if (bulletTimeTimer > 0) {
			fontPower.drawString(20, 380, "(B)ullet-time (" + bulletTime + ")",
					Color.green);
			bulletTimeTimer -= getDelta();
		} else {
			fontPower.drawString(20, 380, "(B)ullet-time (" + bulletTime + ")",
					Color.red);
			snake.setSpeed(Game.SPEED);
		}

	}

	private void afficheRaccourci() {

		font.drawString(20, 450, "Shortcuts : ");
		font.drawString(20, 470, "(R) : Restart");
		font.drawString(20, 490, "(A) : Full screen");
		font.drawString(20, 510, "(V) : Switch view");
		font.drawString(20, 530, "(Esc) : Quit");

	}

	private void afficheScore(Snake s){
		fontMenu.drawString(20, 50, "Snake 3D", Color.blue);
		fontPower.drawString(20, 150, "Score : \n" + s.getScore(), Color.red);
	}

	public static void setCamera() {
		if (SnakeGame.switchView) {
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
		for (int i = 0; i < walls.size(); i++) {
			drawMur(walls.get(i).getX(), walls.get(i).getY(), WALL_SIZE,
					WALL_SIZE * 2);
		}
		glPopMatrix();
	}

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

	private void initializeGame() throws IOException {
		bulletTime = BULLET_TIME;
		mortSerpent = DEATH_TIMING;
		snake = new Snake("Nico", new ArrayList<Position>(), 0f, 0f,
				Color.blue, 0);
		walls = MazeReader.buildWallList("maze.txt");
		if (walls.size() == 0) {
			float x = (float) (-((Game.MAP_SIZE) - Game.SNAKE_SIZE) + ((Game.MAP_SIZE) * 2 - Game.SNAKE_SIZE)
					* Math.random());
			float y = (float) (-((Game.MAP_SIZE) - Game.SNAKE_SIZE) + ((Game.MAP_SIZE) * 2 - Game.SNAKE_SIZE)
					* Math.random());
			walls.add(new Position(x, y));
		}
		setObject(new ArrayList<Eatable>());
		for (int i = 0; i < SnakeGame.APPLENUMBER; i++) {
			// Randow other object
			getObject().add(new Apple());
		}
		// Initialize Snake start_position
		snake.initialize(walls, WALL_SIZE);
		SnakeGame.switchView = false;
		textureSerpent = TextureLoader.getTexture("JPG",
				ResourceLoader.getResourceAsStream("texture/serpent.jpg"));
		textureSol = TextureLoader.getTexture("JPG",
				ResourceLoader.getResourceAsStream("texture/herbe.jpg"));
		textureMur = TextureLoader.getTexture("JPG",
				ResourceLoader.getResourceAsStream("texture/metal.jpg"));
	}

	@Override
	protected void initGL(){

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
		float lightAmbient[] = { 0.5f, 0.5f, 0.5f, 1.0f }; // Ambient Light
															// Values
		float lightDiffuse[] = { lDR, lDR, lDR, 1.0f }; // Diffuse LightValues
		// light Coming from a corner
		float lightPosition[] = { SnakeGame.MAP_MILIEU.getX(),
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

	public int pollInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if ((bulletTime > 0)
						&& Keyboard.getEventKey() == Keyboard.KEY_B) {
					snake.setSpeed(Game.SPEED_BULLET);
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
					return SnakeGame.MENU;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					SnakeGame.switchView = SnakeGame.switchView ? false : true;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_R) {
					return SnakeGame.RESTART;
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
						&& SnakeGame.switchView && snake.getDirection() == 0) {
					snake.setDirection(Game.DOWN);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT
						&& SnakeGame.switchView) {
					snake.setMouvement(Game.RIGHT);
					if (snake.getDirection() == 0) {
						snake.setDirection(Game.DOWN);
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT
						&& SnakeGame.switchView) {
					snake.setMouvement(Game.LEFT);
					if (snake.getDirection() == 0) {
						snake.setDirection(Game.DOWN);
					}
				}
			}
		}
		return SnakeGame.GAME;
	}

	public static int getDelta() {
		return delta;
	}

	public static void setDelta(int delta) {
		Game.delta = delta;
	}

	public static ArrayList<Eatable> getObject() {
		return object;
	}

	public static void setObject(ArrayList<Eatable> object) {
		Game.object = object;
	}
}
