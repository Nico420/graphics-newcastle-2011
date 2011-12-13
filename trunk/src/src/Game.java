package src;

import static org.lwjgl.opengl.GL11.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
					// Generate new item
					if (Math.random() > 0.9)
						object.set(i, new BlueApple());
					else if (Math.random() < 0.1)
						object.set(i, new Apple());
					else
						object.set(i, new Apple());
				}
			}
		} else {
			Fichier.ecrire("highScore.txt",
					snake.getName() + " - " + snake.getScore()+"\n");
			return SnakeGame.PERDU;
		}
		updateFPS();
		return SnakeGame.GAME;
	}

	@Override
	public void renderGL() throws IOException {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Score Display
		afficheScore(snake);
		afficheRaccourci();
		affichePower();
		glLoadIdentity();
		// Dessin de la carte
		glPushMatrix();
		setCamera();
		drawMap();
		textureSerpent.bind();
		snake.draw();
		// Draw Walls
		textureMur.bind();
		//glColor3f(1, 1, 1);
		for (int i = 0; i < walls.size(); i++) {
			draw3DQuad(walls.get(i).getX(), walls.get(i).getY(), WALL_SIZE,
					WALL_SIZE * 2);
		}
		for (int i = 0; i < object.size(); i++)
			object.get(i).draw();
		glPopMatrix();
	}

	private void affichePower() {
		// TODO Auto-generated method stub
		
	}

	private void afficheRaccourci() {

		font.drawString(50, 450, "Shortcuts : ");
		font.drawString(50, 470, "(R) : Restart");
		font.drawString(50, 490, "(A) : Full screen");
		font.drawString(50, 510, "(V) : Switch view");
		font.drawString(50, 530, "(Esc) : Quit");
		
	}

	private void afficheScore(Snake s) throws IOException {
		fontMenu.drawString(50, 50, "Snake 3D");
		font.drawString(50, 150, "Score : \n"+s.getScore(), Color.red);
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
			GLU.gluLookAt(SnakeGame.MAP_MILIEU.getX(),SnakeGame.MAP_MILIEU.getY(), 50f, // where is the eye
					SnakeGame.MAP_MILIEU.getX(),SnakeGame.MAP_MILIEU.getY(), 0f, // what point are we looking at
					1f, 1f, 1f); // which way is up
		}

	}

	private void drawMap() {
		// Navigable map
		textureSol.bind();
		glTranslated(SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getY(),0);
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
		glVertex3f(0 + MAP_SIZE,0 + MAP_SIZE, 0);
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
		//glTranslated(SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getX(),0);
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
		object= new ArrayList<Eatable>();
		for (int i = 0; i < SnakeGame.APPLENUMBER; i++) {
			// Randow other object
			object.add(new Apple());
		}
		// Initialize Snake start_position
		snake.intialize(walls, WALL_SIZE);
		SnakeGame.switchView = false;
		textureSerpent = TextureLoader.getTexture("JPG",ResourceLoader.getResourceAsStream("texture/serpent.jpg"));
		textureSol = TextureLoader.getTexture("JPG",ResourceLoader.getResourceAsStream("texture/herbe.jpg"));
		textureMur = TextureLoader.getTexture("JPG",ResourceLoader.getResourceAsStream("texture/metal.jpg"));
	}

}
