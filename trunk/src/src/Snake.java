package src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import tools.Position;

public class Snake {

	public static float xTemp = 0;
	public static float yTemp = 0;

	String name;
	List<Position> positions;
	float x;
	float y;
	Color c;
	int lenght;
	int mouvement;
	int direction = Game.DOWN;
	private int score;
	
	private float speed = 0.05f;
	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @return the mouvement
	 */
	public int getMouvement() {
		return mouvement;
	}

	/**
	 * @param mouvement
	 *            the mouvement to set
	 */
	public void setMouvement(int mouvement) {
		this.mouvement = mouvement;
	}

	/**
	 * @param name
	 * @param positions
	 * @param x
	 * @param y
	 * @param c
	 * @param lenght
	 */
	public Snake(String name, ArrayList<Position> positions, float x, float y,
			Color c, int lenght) {
		super();
		this.name = name;
		this.positions = positions;
		this.x = x;
		this.y = y;
		this.c = c;
		this.lenght = lenght;

	}

	public Snake() {
		this.name = "Snake";
		this.positions = new ArrayList<Position>();
		this.x = 0;
		this.y = 0;
		this.c = Color.BLUE;
		this.lenght = 0;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the positions
	 */
	public List<Position> getPositions() {
		return positions;
	}

	/**
	 * @param positions
	 *            the positions to set
	 */
	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the c
	 */
	public Color getC() {
		return c;
	}

	/**
	 * @param c
	 *            the c to set
	 */
	public void setC(Color c) {
		this.c = c;
	}

	/**
	 * @return the lenght
	 */
	public int getLenght() {
		return lenght;
	}

	/**
	 * @param lenght
	 *            the lenght to set
	 */
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public void draw() {
		
		drawSnakeHead(x, y, 0, Game.SNAKE_SIZE * 2);
		// Dessin du serpent
		glColor3f(1, 1, 1);
		for (int i = 0; i < positions.size(); i++) {
			drawBody(positions.get(i).getX(), positions.get(i).getY(), 0,
					Game.SNAKE_SIZE * 2);
		}

	}

	public void intialize(List<Position> walls, int wallSize) {

		while ((new Position(x, y).checkCollapse(walls, Game.WALL_SIZE, Game.SNAKE_SIZE))) {
			x = (float) (-((Game.MAP_SIZE-10) - Game.SNAKE_SIZE) + ((Game.MAP_SIZE-10) * 2 - Game.SNAKE_SIZE)
					* Math.random());
			y = (float) (-((Game.MAP_SIZE-10) - Game.SNAKE_SIZE) + ((Game.MAP_SIZE-10) * 2 - Game.SNAKE_SIZE)
					* Math.random());
		}

	}

	private void drawSnakeHead(float x, float y, float z, int size) {

		glPushMatrix(); // Reset The View
		//glLoadIdentity();
		//Game.setCamera();
		glTranslated(SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getY(),0);
		glTranslatef(x, y, 0);
		float rotation = 0;
		switch (this.getDirection()) {
		case Game.LEFT:
			rotation = 90;
			break;
		case Game.RIGHT:
			rotation = 270;
			break;
		case Game.UP:
			rotation = 180;
			break;
		case Game.DOWN:
			rotation = 0;
			break;
		}

		glRotatef(rotation, 0, 0, 1.0f);
		glBegin(GL_QUADS);
		// bottom RIGHT
		glVertex3f(0, Game.SNAKE_SIZE, 0);
		glVertex3f(0, Game.SNAKE_SIZE, 1);
		glVertex3f(Game.SNAKE_SIZE, 0, 1);
		glVertex3f(Game.SNAKE_SIZE, 0, 0);

		glVertex3f(Game.SNAKE_SIZE, 0, 1);
		glVertex3f(Game.SNAKE_SIZE, 0, 0);
		glVertex3f(Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 0);
		glVertex3f(Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 1);

		glVertex3f(Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 1);
		glVertex3f(Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 0);
		glVertex3f(0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE, 0);
		glVertex3f(0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				1);

		// bottom Left
		glVertex3f(0, Game.SNAKE_SIZE, 0);
		glVertex3f(0, Game.SNAKE_SIZE, 1);
		glVertex3f(-Game.SNAKE_SIZE, 0, 1);
		glVertex3f(-Game.SNAKE_SIZE, 0, 0);

		glVertex3f(-Game.SNAKE_SIZE, 0, 1);
		glVertex3f(-Game.SNAKE_SIZE, 0, 0);
		glVertex3f(-Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 0);
		glVertex3f(-Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 1);

		glVertex3f(-Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 1);
		glVertex3f(-Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 0);
		glVertex3f(-0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				0);
		glVertex3f(-0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				1);

		// top RIGHT
		glVertex3f(0, Game.SNAKE_SIZE, Game.SNAKE_SIZE);
		glVertex3f(0, Game.SNAKE_SIZE, -2);
		glVertex3f(Game.SNAKE_SIZE, 0, -2);
		glVertex3f(Game.SNAKE_SIZE, 0, Game.SNAKE_SIZE);

		glVertex3f(Game.SNAKE_SIZE, 0, 1);
		glVertex3f(Game.SNAKE_SIZE, 0, Game.SNAKE_SIZE);
		glVertex3f(Game.SNAKE_SIZE, -Game.SNAKE_SIZE,
				Game.SNAKE_SIZE);
		glVertex3f(Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 1);

		glVertex3f(Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 1);
		glVertex3f(Game.SNAKE_SIZE, -Game.SNAKE_SIZE,
				Game.SNAKE_SIZE);
		glVertex3f(0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				Game.SNAKE_SIZE);
		glVertex3f(0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				1);

		// top Left
		glVertex3f(0, Game.SNAKE_SIZE, Game.SNAKE_SIZE);
		glVertex3f(0, Game.SNAKE_SIZE, -2);
		glVertex3f(-Game.SNAKE_SIZE, 0, -2);
		glVertex3f(-Game.SNAKE_SIZE, 0, Game.SNAKE_SIZE);

		glVertex3f(-Game.SNAKE_SIZE, 0, 1);
		glVertex3f(-Game.SNAKE_SIZE, 0, Game.SNAKE_SIZE);
		glVertex3f(-Game.SNAKE_SIZE, -Game.SNAKE_SIZE,
				Game.SNAKE_SIZE);
		glVertex3f(-Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 1);

		glVertex3f(-Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 1);
		glVertex3f(-Game.SNAKE_SIZE, -Game.SNAKE_SIZE,
				Game.SNAKE_SIZE);
		glVertex3f(-0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				Game.SNAKE_SIZE);
		glVertex3f(-0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				1);

		glEnd();

		glBegin(GL_POLYGON);
		glColor3f(1, 0, 0);
		glVertex3f(0, Game.SNAKE_SIZE, 1);
		glVertex3f(Game.SNAKE_SIZE, 0, 1);
		glVertex3f(Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 1);
		glVertex3f(0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				1);
		glVertex3f(-0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				1);
		glVertex3f(-Game.SNAKE_SIZE, -Game.SNAKE_SIZE, 1);
		glVertex3f(-Game.SNAKE_SIZE, 0, 1);
		glVertex3f(0, Game.SNAKE_SIZE, 1);

		glEnd();
		glBegin(GL_POLYGON);
		glColor3f(0, 1, 0);
		glVertex3f(0, Game.SNAKE_SIZE, Game.SNAKE_SIZE);
		glVertex3f(Game.SNAKE_SIZE, 0, Game.SNAKE_SIZE);
		glVertex3f(Game.SNAKE_SIZE, -Game.SNAKE_SIZE,
				Game.SNAKE_SIZE);
		glVertex3f(0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				Game.SNAKE_SIZE);
		glVertex3f(-0.5f * Game.SNAKE_SIZE, -1.5f * Game.SNAKE_SIZE,
				Game.SNAKE_SIZE);
		glVertex3f(-Game.SNAKE_SIZE, -Game.SNAKE_SIZE,
				Game.SNAKE_SIZE);
		glVertex3f(-Game.SNAKE_SIZE, 0, Game.SNAKE_SIZE);
		glVertex3f(0, Game.SNAKE_SIZE, Game.SNAKE_SIZE);

		glEnd();
		glBegin(GL_POLYGON);
		glColor3f(1, 0, 0);
		glVertex3f(0, Game.SNAKE_SIZE, Game.SNAKE_SIZE);
		glVertex3f(-Game.SNAKE_SIZE, 0, 1);
		glVertex3f(Game.SNAKE_SIZE, 0, 1);

		glEnd();
		glPopMatrix();
	}

	public static void drawBody(float x, float y, float z, float size) {
		float a = size / 2;
		
		glPushMatrix();
		//glLoadIdentity();
		//Game.setCamera();
		glTranslated(SnakeGame.MAP_MILIEU.getX(), SnakeGame.MAP_MILIEU.getY(),0);
		
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0.5);
		glVertex3f(x - a, y - a, z - a);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x + a, y - a, z - a);
		glTexCoord2d(0.5, 0);
		glVertex3f(x + a, y + a, z - a);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y + a, z - a);

		glTexCoord2d(0, 0.5);
		glVertex3f(x - a, y - a, z + a);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x + a, y - a, z + a);
		glTexCoord2d(0.5, 0);
		glVertex3f(x + a, y + a, z + a);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y + a, z + a);

		glTexCoord2d(0, 0.5);
		glVertex3f(x - a, y - a, z + a);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x + a, y - a, z + a);
		glTexCoord2d(0.5, 0);
		glVertex3f(x + a, y - a, z - a);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y - a, z - a);

		glTexCoord2d(0, 0.5);
		glVertex3f(x + a, y + a, z + a);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x - a, y + a, z + a);
		glTexCoord2d(0.5, 0);
		glVertex3f(x - a, y + a, z - a);
		glTexCoord2d(0, 0);
		glVertex3f(x + a, y + a, z - a);
		
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x + a, y - a, z + a);
		glTexCoord2d(0.5, 0);
		glVertex3f(x + a, y - a, z - a);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x - a, y + a, z + a);
		glTexCoord2d(0.5, 0);
		glVertex3f(x - a, y + a, z - a);
		
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x + a, y - a, z + a);
		glTexCoord2d(0.5, 0);
		glVertex3f(x + a, y - a, z - a);
		glTexCoord2d(0.5, 0.5);
		glVertex3f(x - a, y + a, z + a);
		glTexCoord2d(0.5, 0);
		glVertex3f(x - a, y + a, z - a);
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
				+ ", y=" + y + ", c=" + c + ", lenght=" + lenght + "]";
	}

	public int addPosition(int appleEat) {
		if (Math.sqrt(Math.pow(xTemp - this.getX(), 2)
				+ Math.pow(yTemp - this.getY(), 2)) > (Game.SNAKE_SIZE * 2)) {
			drawBody(xTemp, yTemp, 0, Game.SNAKE_SIZE * 2);
			this.positions.add(new Position(xTemp, yTemp));
			switch(appleEat){
			case Apple.GROW_UP:
				appleEat = 0;
				this.score+=100;
				if(Game.pointMulti>0){
					this.score+=500;
				}
				break;
			case Apple.REDUCE:
				this.positions = this.positions.subList((int) Math.ceil(lenght/2), lenght);
				this.setLenght(this.positions.size());
				appleEat = 0;
				this.score+=100;
				break;
			case Apple.DOUBLE:
				appleEat = 0;
				this.score+=1000;
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
			this.setMouvement(0);
		}

	}

	public boolean checkWallCollision(List<Position> walls) {
		Position actual = new Position(this.getX(), this.getY());

		if (actual.checkCollapse(walls, Game.WALL_SIZE, Game.SNAKE_SIZE))
			return true;

		if (this.getX() < -Game.MAP_SIZE + Game.SNAKE_SIZE) {
			this.setY(-Game.MAP_SIZE + Game.SNAKE_SIZE);
			return true;
		}
		if (this.getX() > Game.MAP_SIZE - Game.SNAKE_SIZE) {
			this.setY(Game.MAP_SIZE - Game.SNAKE_SIZE);
			return true;
		}
		if (this.getY() < -Game.MAP_SIZE + Game.SNAKE_SIZE) {
			this.setY(-Game.MAP_SIZE + Game.SNAKE_SIZE);
			return true;
		}
		if (this.getY() > Game.MAP_SIZE - Game.SNAKE_SIZE) {
			this.setY(Game.MAP_SIZE - Game.SNAKE_SIZE);
			return true;
		}

		if (actual.checkCollapse(this.positions, Game.SNAKE_SIZE / 2, Game.SNAKE_SIZE/2))
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
