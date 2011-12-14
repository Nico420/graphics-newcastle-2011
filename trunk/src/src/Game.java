package src;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import tools.MazeReader;
import tools.Position;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import tools.Fichier;

@SuppressWarnings("deprecation")
public class Game extends Etat {

	public static final int LEFT = 3;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 4;

	public static final int SNAKE_SIZE = 3;
	public static final int MAP_SIZE = 100;
	public static final int APPLE_SIZE = 2;
	public static final int WALL_SIZE = 5;

	Texture textureSerpent;
	Texture textureSol;
	Texture textureMur;

	public boolean perdu = false;

	public static int tailReduce = 0;
	public static int pointMulti = 0;
	public static int bulletTime;
	public static int mortSerpent;
	public int appleEat = 0;
	private static int bulletTimeTimer = 0;;

	static ArrayList<Eatable> object = new ArrayList<Eatable>();
	public static float rotation;
	static Snake snake;

	static int delta;

	public static List<Position> walls = new ArrayList<Position>();

	public Game() throws IOException {
		initializeGame();
		initGL();
	}

	@Override
	public int update(int delta) {
		Game.delta = delta;
		if (!perdu) {

			snake.update(delta, SnakeGame.switchView);
			// Adding a new position for snake, and notify snake lenght
			// if
			// needed.
			appleEat = snake.addPosition(appleEat);
			perdu = snake.checkWallCollision(walls);
			// Check Apple detection
			for (int i = 0; i < object.size(); i++) {
				Eatable item = object.get(i);
				if (snake.getX() - SNAKE_SIZE < item.getX()
						&& snake.getX() + SNAKE_SIZE > item.getX()
						&& snake.getY() - SNAKE_SIZE < item.getY()
						&& snake.getY() + SNAKE_SIZE > item.getY()) {
					snake.setLenght(snake.lenght + 1);
					appleEat = item.getAction();
					if (appleEat == Eatable.REDUCE) {
						tailReduce = 600;
					} else if (appleEat == Eatable.MULTI) {
						pointMulti = 2000;
					}
					// Generate new item
					if (Math.random() > 0.9)
						object.set(i, new BlueApple());
					else if (Math.random() < 0.1)
						object.set(i, new GoldApple());
					else
						object.set(i, new Apple());
				}
			}
		} else {
			Fichier.ecrire("highScore.txt", snake.getScore());
			SnakeGame.score = snake.getScore();
			tuerSerpent();
			if (mortSerpent < 0)
				return SnakeGame.PERDU;
			else
				mortSerpent -= delta;
		}
		updateFPS();
		return SnakeGame.GAME;
	}

	private void tuerSerpent() {
		glColor3f(1, 0, 0);
		drawMur(snake.getX(), snake.getY(), 0, SNAKE_SIZE * 2);
		for (int i = 0; i < snake.positions.size(); i++) {
			drawMur(snake.positions.get(i).getX(), snake.positions.get(i)
					.getY(), 0, SNAKE_SIZE * 2);
		}

	}

	@Override
	public void renderGL() throws IOException {
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

		// transform for camera
		setCamera();

		initLightArrays();
		glShadeModel(GL_SMOOTH);
		glMaterial(GL_FRONT, GL_SPECULAR, matSpecular); // sets specular
														// material color
		glMaterialf(GL_FRONT, GL_SHININESS, 50.0f); // sets shininess

		glLight(GL_LIGHT0, GL_POSITION, lightPosition); // sets light position
		glLight(GL_LIGHT0, GL_SPECULAR, whiteLight); // sets specular light to
														// white
		glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight); // sets diffuse light to
													// white
		glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient); // global ambient
																// light

		glEnable(GL_LIGHTING); // enables lighting
		glEnable(GL_LIGHT0); // enables light0

		glEnable(GL_COLOR_MATERIAL); // enables opengl to use glColor3f to
										// define material color
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE); // tell opengl
															// glColor3f effects
															// the ambient and
															// diffuse
															// properties of
															// material
		// ----------- END: Variables & method calls added for Lighting Test
		// -----------//
		// ------- Added for Lighting Test----------//

		glPushMatrix();

		drawMap();
		textureSerpent.bind();
		snake.draw();
		// Draw Walls
		textureMur.bind();
		// glColor3f(1, 1, 1);

		for (int i = 0; i < object.size(); i++)
			object.get(i).draw();
		glPopMatrix();

	}

	private void creationTexte() throws IOException {

		glMatrixMode(GL_PROJECTION);
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
			tailReduce -= delta;
		} else {
			fontPower.drawString(20, 300, "Tail reduction", Color.red);
		}
		if (pointMulti > 0) {
			fontPower.drawString(20, 340, "Points X5", Color.green);
			pointMulti -= delta;
		} else {
			fontPower.drawString(20, 340, "Points X5", Color.red);
		}
		if (bulletTimeTimer > 0) {
			fontPower.drawString(20, 380, "(B)ullet-time (" + bulletTime + ")",
					Color.green);
			bulletTimeTimer -= delta;
		} else {
			fontPower.drawString(20, 380, "(B)ullet-time (" + bulletTime + ")",
					Color.red);
			snake.setSpeed(0.10f);
		}

	}

	private void afficheRaccourci() {

		font.drawString(20, 450, "Shortcuts : ");
		font.drawString(20, 470, "(R) : Restart");
		font.drawString(20, 490, "(A) : Full screen");
		font.drawString(20, 510, "(V) : Switch view");
		font.drawString(20, 530, "(Esc) : Quit");

	}

	private void afficheScore(Snake s) throws IOException {
		fontMenu.drawString(20, 50, "Snake 3D", Color.blue);
		fontPower.drawString(20, 150, "Score : \n" + s.getScore(), Color.red);
	}

	public static void setCamera() {

		if (SnakeGame.switchView) {
			float zoom = 40f;
			float reculCam = 20;
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
					SnakeGame.MAP_MILIEU.getY(), 150f,
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
		glColor3f(1f, 0.5f, 0.5f);
		glVertex3f(0 - (MAP_SIZE + WALL_SIZE), 0 - (MAP_SIZE + WALL_SIZE), 0f);
		glVertex3f(0 + MAP_SIZE + WALL_SIZE, 0 - (MAP_SIZE + WALL_SIZE), 0f);
		glVertex3f(0 + MAP_SIZE + WALL_SIZE, 0 + MAP_SIZE + WALL_SIZE, 0f);
		glVertex3f(0 - (MAP_SIZE + WALL_SIZE), 0 + MAP_SIZE + WALL_SIZE, 0f);

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
		
		//glNormal3f(0, 0, -1);
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
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y - a, size);
		glTexCoord2d(0, 1);
		glVertex3f(x + a, y + a, size);
		glTexCoord2d(0, 1);
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
		bulletTime = 5;
		mortSerpent = 500;
		snake = new Snake();
		walls = MazeReader.buildWallList("maze.txt");
		object = new ArrayList<Eatable>();
		for (int i = 0; i < SnakeGame.APPLENUMBER; i++) {
			// Randow other object
			object.add(new Apple());
		}
		// Initialize Snake start_position
		snake.intialize(walls, WALL_SIZE);
		SnakeGame.switchView = false;
		textureSerpent = TextureLoader.getTexture("JPG",
				ResourceLoader.getResourceAsStream("texture/serpent.jpg"));
		textureSol = TextureLoader.getTexture("JPG",
				ResourceLoader.getResourceAsStream("texture/herbe.jpg"));
		textureMur = TextureLoader.getTexture("JPG",
				ResourceLoader.getResourceAsStream("texture/metal.jpg"));
	}

	@Override
	protected void initGL() throws IOException {

		glClearDepth(1.0f); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Really Nice
															// Perspective
															// Calculations
		glEnable(GL_COLOR_MATERIAL);
		glEnable(GL_TEXTURE_2D);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SnakeGame.WIDTH, SnakeGame.HEIGHT, 0, 100, -100);
		glMatrixMode(GL_MODELVIEW);

	}

	private void initLightArrays() {
		matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();

		whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

		lModelAmbient = BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();
	}

	public int pollInput() throws LWJGLException {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if ((bulletTime > 0)
						&& Keyboard.getEventKey() == Keyboard.KEY_B) {
					snake.setSpeed(0.05f);
					bulletTime--;
					bulletTimeTimer = 6000;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Pressed");
					if (Display.isFullscreen())
						Display.setFullscreen(false);
					else
						Display.setFullscreen(true);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					System.out.println("Escape Key Pressed");
					return SnakeGame.MENU;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					System.out.println("V Key Pressed");
					SnakeGame.switchView = SnakeGame.switchView ? false : true;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_R) {
					System.out.println("R Key Pressed - Game restart");
					return SnakeGame.RESTART;
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT
						&& SnakeGame.switchView) {
					System.out.println("GAUCHE");
					snake.setMouvement(Game.RIGHT);
					if (snake.getDirection() == 0) {
						snake.setDirection(Game.DOWN);
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT
						&& SnakeGame.switchView) {
					System.out.println("DROITE");
					snake.setMouvement(Game.LEFT);
					if (snake.getDirection() == 0) {
						snake.setDirection(Game.DOWN);
					}
				}
			}
		}
		return SnakeGame.GAME;
	}
}
