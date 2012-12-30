package src;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import state.Game;
import tools.Position;

/**
 * Snake class, use for drawing and interacting with the snake.
 * 
 * @author Nicolas
 * 
 */
public class Snake {

	/** Down rotation */
	private static final int DOWN_ROTATION = 0;
	/** Left rotation */
	private static final int LEFT_ROTATION = 90;
	/** Map limit */
	private static final int MAP_LIMIT = 10;
	/** Right rotation */
	private static final int RIGHT_ROTATION = 270;
	/** Up rotation */
	private static final int UP_ROTATION = 180;

	/** Snake temp variable */
	private static float xTemp = 0;
	/** Snake temp variable */
	private static float yTemp = 0;

	/**
	 * Draw the snake body
	 * 
	 * @param x
	 *            Snake x
	 * @param y
	 *            Snake y
	 * @param z
	 *            Snake y
	 * @param size
	 *            Snake size
	 */
	public static void drawBody(float x, float y, float z, float size) {
		float a = size / 2;

		glPushMatrix();
		glTranslated(SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getY(),
				0);
		glBegin(GL_QUADS);

		// glNormal3f(0, 0, -1);
		glTexCoord2d(0, 0.5);
		glVertex3f(x - a, y - a, 0);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x + a, y - a, 0);
		glTexCoord2d(0.5, 0);
		glVertex3f(x + a, y + a, 0);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y + a, 0);

		glTexCoord2d(0, 0.5);
		glVertex3f(x - a, y - a, size);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x + a, y - a, size);
		glTexCoord2d(0.5, 0);
		glVertex3f(x + a, y + a, size);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y + a, size);

		glTexCoord2d(0, 0.5);
		glVertex3f(x - a, y - a, size);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x + a, y - a, size);
		glTexCoord2d(0, 0.5);
		glVertex3f(x + a, y - a, 0);
		glTexCoord2d(0.5, 0);
		glVertex3f(x - a, y - a, 0);

		glTexCoord2d(0, 0.5);
		glVertex3f(x + a, y + a, size);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x - a, y + a, size);
		glTexCoord2d(0.5, 0);
		glVertex3f(x - a, y + a, 0);
		glTexCoord2d(0, 0);
		glVertex3f(x + a, y + a, 0);

		glTexCoord2d(0, 0.5);
		glVertex3f(x - a, y - a, 0);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x - a, y + a, 0);
		glTexCoord2d(0.5, 0);
		glVertex3f(x - a, y + a, size);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y - a, size);

		glTexCoord2d(0.5, 0);
		glVertex3f(x + a, y + a, 0);
		glTexCoord2d(0, 0);
		glVertex3f(x + a, y - a, 0);
		glTexCoord2d(0, 0.5);
		glVertex3f(x + a, y - a, size);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x + a, y + a, size);

		glEnd();

		glPopMatrix();

	}

	/** Snake Color */
	private Color c;

	/** Snake direction */
	private int direction = Game.DOWN;
	/** Snake lenght */
	private int lenght;

	/** Snake mouvement */
	private int mouvement;
	/** Snake name */
	private String name;
	/** Snake positions */
	private List<Position> positions;
	/** Snake Fichier */
	private int score;
	/** Snake Speed */
	private float speed;
	/** Snake actuel head position */
	private float x;

	/** Snake actuel head position */
	private float y;

	/** Apple eat */
	private int appleEat;
	
	/** Key configuration */
	private int KUp;
	private int KDown;
	private int KLeft;
	private int KRight;
	/**
	 * Construct the snake.
	 * 
	 * @param pName
	 *            The snake name
	 * @param pPositions
	 *            Snake positions list
	 * @param pX
	 *            Snake starting position x
	 * @param pY
	 *            Snake starting position y
	 * @param blue
	 *            Snake color
	 * @param pLenght
	 *            Snake length
	 * @param pSpeed
	 *            Snake speed
	 */
	public Snake(String pName, ArrayList<Position> pPositions, float pX,
			float pY, Color blue, int pLenght, float pSpeed, int snakeNumber) {
		this.name = pName;
		this.positions = pPositions;
		this.x = pX;
		this.y = pY;
		this.c = blue;
		this.lenght = pLenght;
		this.speed = pSpeed;
		this.setAppleEat(0);
		this.setKeys(snakeNumber+1);
	}

	private void setKeys(int snakeNumber) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("keys.config"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setKUp(prop.getProperty(snakeNumber+"Up").toString());
		this.setKDown(prop.getProperty(snakeNumber+"Down").toString());
		this.setKLeft(prop.getProperty(snakeNumber+"Left").toString());
		this.setKRight(prop.getProperty(snakeNumber+"Right").toString());
	}

	/**
	 * Add a position to the snake
	 * 
	 * @param pAppleEat
	 *            Have an apple been eaten ? which snake
	 * @return apple eat new state.
	 */
	public int addPosition(int pAppleEat) {
		if (Math.sqrt(Math.pow(xTemp - this.getX(), 2)
				+ Math.pow(yTemp - this.getY(), 2)) > (Game.SNAKE_SIZE * 2)) {
			drawBody(xTemp, yTemp, 0, Game.SNAKE_SIZE * 2);
			this.positions.add(new Position(xTemp, yTemp));
			switch (pAppleEat) {
			case Apple.GROW_UP:
				pAppleEat = 0;
				this.score += 100;
				if (Game.getPointMulti() > 0) {
					this.score += 500;
				}
				break;
			case Apple.REDUCE:
				this.positions = this.positions.subList(
						(int) Math.ceil(lenght / 2), lenght);
				this.setLenght(this.positions.size());
				pAppleEat = 0;
				this.score += 100;
				break;
			case Apple.MULTI:
				pAppleEat = 0;
				this.score += 1000;
				break;
			case 0:
				this.positions = this.positions.subList(1, lenght + 1);
				break;
			default:
				break;
			}
			xTemp = this.getX();
			yTemp = this.getY();
		}
		return pAppleEat;
	}

	/**
	 * Check wall collision
	 * 
	 * @param walls
	 *            Walls positions
	 * @return True if collision
	 */
	public boolean checkWallCollision(List<Position> walls) {
		Position actual = new Position(this.getX(), this.getY());

		if (actual.checkCollapse(walls, Game.WALL_SIZE, Game.SNAKE_SIZE)) {
			return true;
		}

		if (this.getX() < -Game.MAP_SIZE + Game.SNAKE_SIZE) {
			// this.setY(-Game.MAP_SIZE + Game.SNAKE_SIZE);
			return true;
		}
		if (this.getX() > Game.MAP_SIZE - Game.SNAKE_SIZE) {
			// this.setY(Game.MAP_SIZE - Game.SNAKE_SIZE);
			return true;
		}
		if (this.getY() < -Game.MAP_SIZE + Game.SNAKE_SIZE) {
			// this.setY(-Game.MAP_SIZE + Game.SNAKE_SIZE);
			return true;
		}
		if (this.getY() > Game.MAP_SIZE - Game.SNAKE_SIZE) {
			// this.setY(Game.MAP_SIZE - Game.SNAKE_SIZE);
			return true;
		}

		if (actual.checkCollapse(this.positions, Game.SNAKE_SIZE / 2,
				Game.SNAKE_SIZE / 2)) {
			return true;
		}
		return false;
	}

	/**
	 * Draw the snake.
	 */
	public void draw() {

		drawSnakeHead(x, y, 0, (float) Game.SNAKE_SIZE + 1);
		// Dessin du serpent
		glColor3f(1, 1, 1);
		for (int i = 0; i < positions.size(); i++) {
			drawBody(positions.get(i).getX(), positions.get(i).getY(), 0,
					(float) Game.SNAKE_SIZE * 2);
		}

	}

	/**
	 * Draw the snake head.
	 * 
	 * @param pX
	 *            X position
	 * @param pY
	 *            Y position
	 * @param pZ
	 *            Z position
	 * @param pSize
	 *            Snake head size
	 */
	private void drawSnakeHead(float pX, float pY, float pZ, float pSize) {

		glPushMatrix(); // Reset The View
		glTranslated(SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getY(),
				0);
		glTranslatef(pX, pY, 0);
		float rotation = 0;
		switch (this.getDirection()) {
		case Game.LEFT:
			rotation = LEFT_ROTATION;
			break;
		case Game.RIGHT:
			rotation = RIGHT_ROTATION;
			break;
		case Game.UP:
			rotation = UP_ROTATION;
			break;
		case Game.DOWN:
			rotation = DOWN_ROTATION;
			break;
		default:
			break;
		}

		glRotatef(rotation, 0, 0, 1.0f);
		glColor3f(((float) c.getRed()) / 255, ((float) c.getGreen()) / 255,
				((float) c.getBlue()) / 255);
		glBegin(GL_QUADS);
		// bottom RIGHT
		glVertex3f(0, pSize, 0);
		glVertex3f(0, pSize, 1);
		glVertex3f(pSize, 0, 1);
		glVertex3f(pSize, 0, 0);

		glVertex3f(pSize, 0, 1);
		glVertex3f(pSize, 0, 0);
		glVertex3f(pSize, -pSize, 0);
		glVertex3f(pSize, -pSize, 1);

		glVertex3f(pSize, -pSize, 1);
		glVertex3f(pSize, -pSize, 0);
		glVertex3f(0.5f * pSize, -1.5f * pSize, 0);
		glVertex3f(0.5f * pSize, -1.5f * pSize, 1);

		// bottom Left
		glVertex3f(0, pSize, 0);
		glVertex3f(0, pSize, 1);
		glVertex3f(-pSize, 0, 1);
		glVertex3f(-pSize, 0, 0);

		glVertex3f(-pSize, 0, 1);
		glVertex3f(-pSize, 0, 0);
		glVertex3f(-pSize, -pSize, 0);
		glVertex3f(-pSize, -pSize, 1);

		glVertex3f(-pSize, -pSize, 1);
		glVertex3f(-pSize, -pSize, 0);
		glVertex3f(-0.5f * pSize, -1.5f * pSize, 0);
		glVertex3f(-0.5f * pSize, -1.5f * pSize, 1);

		// top RIGHT
		glVertex3f(0, pSize, pSize);
		glVertex3f(0, pSize, -2);
		glVertex3f(pSize, 0, -2);
		glVertex3f(pSize, 0, pSize);

		glVertex3f(pSize, 0, 1);
		glVertex3f(pSize, 0, pSize);
		glVertex3f(pSize, -pSize, pSize);
		glVertex3f(pSize, -pSize, 1);

		glVertex3f(pSize, -pSize, 1);
		glVertex3f(pSize, -pSize, pSize);
		glVertex3f(0.5f * pSize, -1.5f * pSize, pSize);
		glVertex3f(0.5f * pSize, -1.5f * pSize, 1);

		// top Left
		glVertex3f(0, pSize, pSize);
		glVertex3f(0, pSize, -2);
		glVertex3f(-pSize, 0, -2);
		glVertex3f(-pSize, 0, pSize);

		glVertex3f(-pSize, 0, 1);
		glVertex3f(-pSize, 0, pSize);
		glVertex3f(-pSize, -pSize, pSize);
		glVertex3f(-pSize, -pSize, 1);

		glVertex3f(-pSize, -pSize, 1);
		glVertex3f(-pSize, -pSize, pSize);
		glVertex3f(-0.5f * pSize, -1.5f * pSize, pSize);
		glVertex3f(-0.5f * pSize, -1.5f * pSize, 1);

		glEnd();

		glBegin(GL_POLYGON);
		glVertex3f(0, pSize, 1);
		glVertex3f(pSize, 0, 1);
		glVertex3f(pSize, -pSize, 1);
		glVertex3f(0.5f * pSize, -1.5f * pSize, 1);
		glVertex3f(-0.5f * pSize, -1.5f * pSize, 1);
		glVertex3f(-pSize, -pSize, 1);
		glVertex3f(-pSize, 0, 1);
		glVertex3f(0, pSize, 1);

		glEnd();
		glBegin(GL_POLYGON);
		glVertex3f(0, pSize, pSize);
		glVertex3f(pSize, 0, pSize);
		glVertex3f(pSize, -pSize, pSize);
		glVertex3f(0.5f * pSize, -1.5f * pSize, pSize);
		glVertex3f(-0.5f * pSize, -1.5f * pSize, pSize);
		glVertex3f(-pSize, -pSize, pSize);
		glVertex3f(-pSize, 0, pSize);
		glVertex3f(0, pSize, pSize);

		glEnd();
		glBegin(GL_POLYGON);
		glVertex3f(0, pSize, pSize);
		glVertex3f(-pSize, 0, 1);
		glVertex3f(pSize, 0, 1);
		glEnd();

		// Rajouter deux yeux !
		/*
		 * glColor3f(0.5f, 0.5f, 0.5f); float eyes = size / 2;
		 * glBegin(GL_QUADS); glColor3f(1f, 1f, 1f); glVertex3f(0, eyes, size);
		 * glVertex3f(eyes, eyes, size); glVertex3f(eyes, 0, size);
		 * glVertex3f(0, 0, size); glVertex3f(0, eyes, size); glVertex3f(-eyes,
		 * eyes, size); glVertex3f(-eyes, 0, size); glVertex3f(0, 0, size);
		 * 
		 * eyes /= 2; glColor3f(0f, 0f, 1f); glVertex3f(eyes, eyes * 2, size);
		 * glVertex3f(eyes * 2, eyes * 2, size); glVertex3f(eyes * 2, eyes,
		 * size); glVertex3f(eyes, eyes, size); glVertex3f(eyes, eyes * 2,
		 * size); glVertex3f(-eyes * 2, eyes * 2, size); glVertex3f(-eyes * 2,
		 * eyes, size); glVertex3f(eyes, eyes, size); glEnd();
		 */

		glPopMatrix();
	}

	/**
	 * @return the c
	 */
	public Color getC() {
		return c;
	}

	/**
	 * Get direction
	 * 
	 * @return The snake Direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @return the lenght
	 */
	public int getLenght() {
		return lenght;
	}

	/**
	 * @return the mouvement
	 */
	public int getMouvement() {
		return mouvement;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the positions
	 */
	public List<Position> getPositions() {
		return positions;
	}

	/**
	 * Get the score
	 * 
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * Initialize the snake.
	 * 
	 * @param walls
	 *            Walls position
	 * @param wallSize
	 *            Wall size
	 */
	public void initialize(List<Position> walls, int wallSize) {

		while ((new Position(x, y).checkCollapse(walls, Game.WALL_SIZE,
				Game.SNAKE_SIZE))) {
			x = (float) (-(Game.MAP_SIZE - MAP_LIMIT - Game.SNAKE_SIZE) + ((Game.MAP_SIZE
					- MAP_LIMIT - Game.SNAKE_SIZE) * 2)
					* Math.random());
			y = (float) (-(Game.MAP_SIZE - MAP_LIMIT - Game.SNAKE_SIZE) + ((Game.MAP_SIZE
					- MAP_LIMIT - Game.SNAKE_SIZE) * 2)
					* Math.random());
		}

	}

	/**
	 * @param pC
	 *            the c to set
	 */
	public void setC(Color pC) {
		this.c = pC;
	}

	/**
	 * Set a new Direction
	 * 
	 * @param dir
	 *            new direction to set.
	 */
	public void setDirection(int dir) {
		direction = dir;
	}

	/**
	 * @param pLenght
	 *            the lenght to set
	 */
	public void setLenght(int pLenght) {
		this.lenght = pLenght;
	}

	/**
	 * @param pMouvement
	 *            the mouvement to set
	 */
	public void setMouvement(int pMouvement) {
		this.mouvement = pMouvement;
	}

	/**
	 * @param pName
	 *            the name to set
	 */
	public void setName(String pName) {
		this.name = pName;
	}

	/**
	 * @param pPositions
	 *            the positions to set
	 */
	public void setPositions(ArrayList<Position> pPositions) {
		this.positions = pPositions;
	}

	/**
	 * Set a new score
	 * 
	 * @param pScore
	 *            The new score to set.
	 */
	public void setScore(int pScore) {
		this.score = pScore;
	}

	/**
	 * @param pSpeed
	 *            the speed to set
	 */
	public void setSpeed(float pSpeed) {
		this.speed = pSpeed;
	}

	/**
	 * @param pX
	 *            the x to set
	 */
	public void setX(float pX) {
		this.x = pX;
	}

	/**
	 * @param pY
	 *            the y to set
	 */
	public void setY(float pY) {
		this.y = pY;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Snake [c=" + c + ", direction=" + direction + ", lenght="
				+ lenght + ", mouvement=" + mouvement + ", name=" + name
				+ ", positions=" + positions + ", score=" + score + ", speed="
				+ speed + ", x=" + x + ", y=" + y + ", appleEat=" + appleEat
				+ ", KUp=" + KUp + ", KDown=" + KDown + ", KLeft=" + KLeft
				+ ", KRight=" + KRight + "]";
	}

	/**
	 * Snake update
	 * 
	 * @param delta
	 *            Delta update
	 * @param view
	 *            View
	 */
	public void update(int delta, boolean view) {
		if (!view) {
			// Classic view

			if (Keyboard.isKeyDown(this.getKLeft())) {
				if (!(this.getMouvement() == Game.RIGHT)) {
					this.setMouvement(Game.LEFT);
				}
			}
			if (Keyboard.isKeyDown(this.getKRight())) {
				if (!(this.getMouvement() == Game.LEFT)) {
					this.setMouvement(Game.RIGHT);
				}
			}
			if (Keyboard.isKeyDown(this.getKUp())) {
				if (!(this.getMouvement() == Game.DOWN)) {
					this.setMouvement(Game.DOWN);
				}
			}
			if (Keyboard.isKeyDown(this.getKDown())) {
				if (!(this.getMouvement() == Game.UP)) {
					this.setMouvement(Game.UP);
				}
			}
			if (this.getMouvement() == -1) {
				this.setMouvement(this.getDirection());
			}

			switch (this.getMouvement()) {
			case Game.LEFT:
				this.setX(this.getX() - this.speed * delta);
				break;
			case Game.RIGHT:
				this.setX(this.getX() + this.speed * delta);
				break;
			case Game.UP:
				this.setY(this.getY() - this.speed * delta);
				break;
			case Game.DOWN:
				this.setY(this.getY() + this.speed * delta);
				break;
			default:
				break;
			}
			this.setDirection(this.getMouvement());
		} else {
			// Tracking view
			switch (this.getDirection()) {
			case Game.LEFT:
				if (this.getMouvement() == Game.RIGHT) {
					this.setDirection(Game.UP);
				}
				if (this.getMouvement() == Game.LEFT) {
					this.setDirection(Game.DOWN);
				}
				break;
			case Game.RIGHT:
				if (this.getMouvement() == Game.RIGHT) {
					this.setDirection(Game.DOWN);
				}
				if (this.getMouvement() == Game.LEFT) {
					this.setDirection(Game.UP);
				}

				break;
			case Game.UP:
				if (this.getMouvement() == Game.RIGHT) {
					this.setDirection(Game.RIGHT);
				}
				if (this.getMouvement() == Game.LEFT) {
					this.setDirection(Game.LEFT);
				}
				break;
			case Game.DOWN:
				if (this.getMouvement() == Game.RIGHT) {
					this.setDirection(Game.LEFT);
				}
				if (this.getMouvement() == Game.LEFT) {
					this.setDirection(Game.RIGHT);
				}

				break;
			default:
				break;
			}

			switch (this.getDirection()) {
			case Game.LEFT:
				this.setX(this.getX() - this.speed * delta);
				break;
			case Game.RIGHT:
				this.setX(this.getX() + this.speed * delta);
				break;
			case Game.UP:
				this.setY(this.getY() - this.speed * delta);
				break;
			case Game.DOWN:
				this.setY(this.getY() + this.speed * delta);
				break;
			default:
				break;
			}
			this.setMouvement(-1);
		}

	}

	/**
	 * Get apple eat
	 * 
	 * @return Apple eat
	 */
	public int getAppleEat() {
		return appleEat;
	}

	/**
	 * Set a new apple eat
	 * 
	 * @param pAppleEat
	 *            New apple eat to set
	 */
	public void setAppleEat(int pAppleEat) {
		this.appleEat = pAppleEat;
	}

	public int getKUp() {
		return KUp;
	}

	public void setKUp(String string) {
		KUp = Keyboard.getKeyIndex(string.toUpperCase());
	}

	public int getKDown() {
		return KDown;
	}

	public void setKDown(String string) {
		KDown = Keyboard.getKeyIndex(string.toUpperCase());
	}

	public int getKLeft() {
		return KLeft;
	}

	public void setKLeft(String string) {
		KLeft = Keyboard.getKeyIndex(string.toUpperCase());
	}

	public int getKRight() {
		return KRight;
	}

	public void setKRight(String string) {
		KRight = Keyboard.getKeyIndex(string.toUpperCase());
	}

}
