package src;

import static org.lwjgl.opengl.GL11.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
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

	public static final int SNAKE_SIZE = 10;
	public static final int MAP_SIZE = 250;
	public static final int APPLE_SIZE = 7;
	public static final int WALL_SIZE = 20;

	Texture textureSerpent;
	Texture textureSol;
	Texture textureMur;

	public boolean perdu = false;

	public static int tailReduce = 0;
	public static int PointMulti = 0;
	public int appleEat = 0;

	static ArrayList<Eatable> object = new ArrayList<Eatable>();
	public static float rotation;
	static Snake snake;

	public static List<Position> walls = new ArrayList<Position>();

	public Game() throws IOException {
		initializeGame();

	}

	@Override
	public int update(int delta) {
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
					} else if (appleEat == Eatable.DOUBLE) {
						PointMulti = 2000;
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
			return SnakeGame.PERDU;
		}
		updateFPS();
		return SnakeGame.GAME;
	}

	@Override
	public void renderGL() throws IOException {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Viewport, text display (score,power,...)
		glViewport(0, 0, Display.getWidth() / 4, Display.getHeight());
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix
		// Set Up Ortho Mode To Fit 1/4 The Screen (Size Of A Viewport)
		GLU.gluOrtho2D(0, SnakeGame.WIDTH / 4, SnakeGame.HEIGHT, 0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glClear(GL_DEPTH_BUFFER_BIT);
		// Score Display
		afficheScore(snake);
		afficheRaccourci();
		affichePower();

		// ViewPort for the game.
		glViewport(Display.getWidth() / 4, Display.getHeight(),
				3 * Display.getWidth() / 4, Display.getHeight());
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		
		glLoadIdentity(); // Reset The Projection Matrix
		// Set Up Perspective Mode To Fit 1/4 The Screen (Size Of A Viewport)
		GLU.gluPerspective(45.0f, Display.getWidth() / Display.getHeight(), 0.1f,
				500.0f);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glClear(GL_DEPTH_BUFFER_BIT);

		glTranslatef(0.0f, 0.0f, -2.0f); // Move 2 Units Into The Screen
		glRotatef(-45.0f,1.0f,0.0f,0.0f); 

		glColor3f(1,0,0);
		draw3DQuad(100, 100, 0, 100);
		glBegin(GL_QUADS); // Begin Drawing A Single Quad
		glVertex3f(1.0f, 1.0f, 0.0f);
		glVertex3f(-1.0f, 1.0f, 0.0f);
		glVertex3f(-1.0f, -1.0f, 0.0f);
		glVertex3f(1.0f, -1.0f, 0.0f);
		glEnd(); 
		// Done Drawing The Textured Quad
		// glTranslatef(0.0f, 0.0f, 7.0f);
		/*
		 * glEnable(GL_LIGHTING); // Enable Lighting //
		 * glTranslatef(0.0f,0.0f,-2.0f); drawMap(); glDisable(GL_LIGHTING); //
		 * Disable Lighting // Dessin de la carte glPushMatrix(); //
		 * setCamera();
		 * 
		 * textureSerpent.bind(); snake.draw(); // Draw Walls textureMur.bind();
		 * // glColor3f(1, 1, 1); for (int i = 0; i < walls.size(); i++) {
		 * draw3DQuad(walls.get(i).getX(), walls.get(i).getY(), WALL_SIZE,
		 * WALL_SIZE * 2); } for (int i = 0; i < object.size(); i++)
		 * object.get(i).draw(); glPopMatrix();
		 */
	}

	private void affichePower() {
		fontPower.drawString(20, 250, "POWERS UP", Color.yellow);
		if (tailReduce > 0) {
			fontPower.drawString(20, 300, "Tail reduction", Color.green);
			tailReduce--;
		} else {
			fontPower.drawString(20, 300, "Tail reduction", Color.red);
		}
		if (PointMulti > 0) {
			fontPower.drawString(20, 340, "Points X5", Color.green);
			PointMulti--;
		} else {
			fontPower.drawString(20, 340, "Points X5", Color.red);
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
			float distance = 0.01f;
			switch (snake.getDirection()) {
			case LEFT:
				GLU.gluLookAt(snake.getX() - distance, snake.getY(), 0.5f, // where
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
			/* glTranslatef(0, -100, 0); */
			// GLU.gluLookAt(0, -1, 20, 1 ,1, 0, 0, 0, 1);
			/*
			 * glRotatef(-90,0,0,1); GLU.gluLookAt(1.5f, 1.5f, 5f,
			 * SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getY(), 0f, 1f,
			 * 1f, 1f);
			 */
			/*
			 * GLU.gluLookAt(SnakeGame.MAP_MILIEU.getX(),SnakeGame.MAP_MILIEU.getY
			 * (), 50f, // where is the eye
			 * SnakeGame.MAP_MILIEU.getX(),SnakeGame.MAP_MILIEU.getY(), 0f, //
			 * what point are we looking at 1f, 0f, 0f); // which way is up
			 */
		}

	}

	private void drawMap() {
		// Navigable map
		textureSol.bind();
		// glTranslated(SnakeGame.MAP_MILIEU.getX(),
		// SnakeGame.MAP_MILIEU.getY(),
		// 0);
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
		for (int i = -MAP_SIZE; i <= MAP_SIZE; i += WALL_SIZE) {
			// glColor3f(1, 0, 0);
			textureMur.bind();
			draw3DQuad(i, -(MAP_SIZE + WALL_SIZE / 2), -WALL_SIZE, WALL_SIZE);
			draw3DQuad(i, (MAP_SIZE + WALL_SIZE / 2), -WALL_SIZE, WALL_SIZE);
			draw3DQuad((MAP_SIZE + WALL_SIZE / 2), i, -WALL_SIZE, WALL_SIZE);
			draw3DQuad(-(MAP_SIZE + WALL_SIZE / 2), i, -WALL_SIZE, WALL_SIZE);
		}

	}

	public static void draw3DQuad(float x, float y, float z, float size) {
		float a = size / 2;

		glPushMatrix();
		//setCamera();
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

	private void initializeGame() throws IOException {
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
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
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


		glMatrixMode(GL_MODELVIEW);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		initLightArrays();

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

}

/*
 * glShadeModel(GL_SMOOTH);
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
// ------- Added for Lighting Test----------//*/
