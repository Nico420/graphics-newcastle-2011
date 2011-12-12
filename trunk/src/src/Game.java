package src;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import tools.MazeReader;
import tools.Position;
import tools.Texture;

public class Game extends Etat {
	
	public static final int LEFT = 3;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 4;

	public static final int SNAKE_SIZE = 3;
	public static final int MAP_SIZE = 90;
	public static final int APPLE_SIZE = 2;
	public static final int WALL_SIZE = 5;
	

	Texture textureSerpent;
	Texture textureSol;
	Texture textureMur;
	
	public boolean perdu = false;
	
	public boolean appleEat = false;

	Apple apple;
	public static float rotation;
	static Snake snake;

	public static List<Position> walls = new ArrayList<Position>();
	

	
	public Game() throws IOException{
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
			if (snake.getX() - SNAKE_SIZE < apple.getX()
					&& snake.getX() + SNAKE_SIZE > apple.getX()
					&& snake.getY() - SNAKE_SIZE < apple.getY()
					&& snake.getY() + SNAKE_SIZE > apple.getY()) {
				snake.setLenght(snake.lenght + 1);
				appleEat = true;
				apple = new Apple();
			}
		}
		updateFPS();
		return SnakeGame.GAME;
	}

	@Override
	public void renderGL() throws IOException {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

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
	
	public static void setCamera() {
		if (SnakeGame.switchView) {
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
	
	private void initializeGame() throws IOException {
		snake = new Snake();
		walls = MazeReader.buildWallList("maze.txt");
		apple = new Apple();
		// Initialize Snake start_position
		snake.intialize(walls, WALL_SIZE);
		
		textureSerpent = tl.getTexture("texture/serpent.jpg");
		textureSol = tl.getTexture("texture/herbe.jpg");
		textureMur = tl.getTexture("texture/metal.jpg");
	}

}
