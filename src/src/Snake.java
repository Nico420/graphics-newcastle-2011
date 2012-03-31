package src;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glPopMatrix;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import tools.Position;

/**
 * Snake class, use for drawing and interacting with the snake.
 * 
 * @author Nicolas
 * 
 */
public class Snake {

	private static final int DOWN_ROTATION = 0;
	private static final int UP_ROTATION = 180;
	private static final int RIGHT_ROTATION = 270;
	private static final int LEFT_ROTATION = 90;
	private static final int MAP_LIMIT = 10;
	public static float xTemp = 0;
	public static float yTemp = 0;

	private String name;
	private List<Position> positions;
	private float x;
	private float y;
	private Color c;
	private int lenght;
	private int mouvement;
	private int direction = Game.DOWN;
	private int score;

	private float speed;

	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param pSpeed
	 *            the speed to set
	 */
	public void setSpeed(float pSpeed) {
		this.speed = pSpeed;
	}

	/**
	 * @return the mouvement
	 */
	public int getMouvement() {
		return mouvement;
	}

	/**
	 * @param pMouvement
	 *            the mouvement to set
	 */
	public void setMouvement(int pMouvement) {
		this.mouvement = pMouvement;
	}

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
	 */
	public Snake(String pName, ArrayList<Position> pPositions, float pX,
			float pY, Color blue, int pLenght) {
		this.name = pName;
		this.positions = pPositions;
		this.x = pX;
		this.y = pY;
		this.c = blue;
		this.lenght = pLenght;
		this.speed = Game.SPEED;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param pName
	 *            the name to set
	 */
	public void setName(String pName) {
		this.name = pName;
	}

	/**
	 * @return the positions
	 */
	public List<Position> getPositions() {
		return positions;
	}

	/**
	 * @param pPositions
	 *            the positions to set
	 */
	public void setPositions(ArrayList<Position> pPositions) {
		this.positions = pPositions;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param pX
	 *            the x to set
	 */
	public void setX(float pX) {
		this.x = pX;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param pY
	 *            the y to set
	 */
	public void setY(float pY) {
		this.y = pY;
	}

	/**
	 * @return the c
	 */
	public Color getC() {
		return c;
	}

	/**
	 * @param pC
	 *            the c to set
	 */
	public void setC(Color pC) {
		this.c = pC;
	}

	/**
	 * @return the lenght
	 */
	public int getLenght() {
		return lenght;
	}

	/**
	 * @param pLenght
	 *            the lenght to set
	 */
	public void setLenght(int pLenght) {
		this.lenght = pLenght;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Snake [name=" + name + ", positions=" + positions + ", x=" + x
				+ ", y=" + y + ", c=" + c + ", lenght=" + lenght
				+ ", mouvement=" + mouvement + ", direction=" + direction
				+ ", score=" + score + ", speed=" + speed + "]";
	}

	public int addPosition(int appleEat) {
		if (Math.sqrt(Math.pow(xTemp - this.getX(), 2)
				+ Math.pow(yTemp - this.getY(), 2)) > (Game.SNAKE_SIZE * 2)) {
			drawBody(xTemp, yTemp, 0, Game.SNAKE_SIZE * 2);
			this.positions.add(new Position(xTemp, yTemp));
			switch (appleEat) {
			case Apple.GROW_UP:
				appleEat = 0;
				this.score += 100;
				if (Game.pointMulti > 0) {
					this.score += 500;
				}
				break;
			case Apple.REDUCE:
				this.positions = this.positions.subList(
						(int) Math.ceil(lenght / 2), lenght);
				this.setLenght(this.positions.size());
				appleEat = 0;
				this.score += 100;
				break;
			case Apple.MULTI:
				appleEat = 0;
				this.score += 1000;
				break;
			case 0:
				this.positions = this.positions.subList(1, lenght + 1);
				break;
			}
			xTemp = this.getX();
			yTemp = this.getY();
		}
		return appleEat;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int dir) {
		direction = dir;
	}

	public void update(int delta, boolean b) {
		if (!b) {
			// Classic view

			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				if (!(this.getMouvement() == Game.RIGHT))
					this.setMouvement(Game.LEFT);
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				if (!(this.getMouvement() == Game.LEFT))
					this.setMouvement(Game.RIGHT);
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				if (!(this.getMouvement() == Game.DOWN))
					this.setMouvement(Game.DOWN);
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				if (!(this.getMouvement() == Game.UP))
					this.setMouvement(Game.UP);
			if (this.getMouvement() == -1)
				this.setMouvement(this.getDirection());

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
			}
			this.setDirection(this.getMouvement());
		} else {
			// Tracking view
			switch (this.getDirection()) {
			case Game.LEFT:
				if (this.getMouvement() == Game.RIGHT)
					this.setDirection(Game.UP);
				if (this.getMouvement() == Game.LEFT)
					this.setDirection(Game.DOWN);
				break;
			case Game.RIGHT:
				if (this.getMouvement() == Game.RIGHT)
					this.setDirection(Game.DOWN);
				if (this.getMouvement() == Game.LEFT)
					this.setDirection(Game.UP);

				break;
			case Game.UP:
				if (this.getMouvement() == Game.RIGHT)
					this.setDirection(Game.RIGHT);
				if (this.getMouvement() == Game.LEFT)
					this.setDirection(Game.LEFT);
				break;
			case Game.DOWN:
				if (this.getMouvement() == Game.RIGHT)
					this.setDirection(Game.LEFT);
				if (this.getMouvement() == Game.LEFT)
					this.setDirection(Game.RIGHT);

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
			}
			this.setMouvement(-1);
		}

	}

	public boolean checkWallCollision(List<Position> walls) {
		Position actual = new Position(this.getX(), this.getY());

		if (actual.checkCollapse(walls, Game.WALL_SIZE, Game.SNAKE_SIZE))
			return true;

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
				Game.SNAKE_SIZE / 2))
			return true;
		return false;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
