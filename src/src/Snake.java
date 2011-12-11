package src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

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
	int direction;

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

		drawSnakeHead(x, y, 0, SnakeGame.SNAKE_SIZE * 2);
		// Dessin du serpent
		glColor3f(1, 1, 1);
		for (int i = 0; i < positions.size(); i++) {
			draw3DQuad(positions.get(i).getX(), positions.get(i).getY(), 0,
					SnakeGame.SNAKE_SIZE * 2);
		}

	}

	public void intialize(List<Position> walls, int wallSize) {

		while ((new Position(x, y).checkCollapse(walls, SnakeGame.WALL_SIZE))) {
			x = (float) (-(SnakeGame.MAP_SIZE - SnakeGame.SNAKE_SIZE) + (SnakeGame.MAP_SIZE * 2 - SnakeGame.SNAKE_SIZE)
					* Math.random());
			y = (float) (-(SnakeGame.MAP_SIZE - SnakeGame.SNAKE_SIZE) + (SnakeGame.MAP_SIZE * 2 - SnakeGame.SNAKE_SIZE)
					* Math.random());
		}

	}

	private void drawSnakeHead(float x, float y, float z, int size) {

		glPushMatrix(); // Reset The View
		glLoadIdentity();
		SnakeGame.setCamera();
		glTranslatef(x, y, 0);
		float rotation = 0;
		switch (this.getDirection()) {
		case SnakeGame.LEFT:
			rotation = 90;
			break;
		case SnakeGame.RIGHT:
			rotation = 270;
			break;
		case SnakeGame.UP:
			rotation = 180;
			break;
		case SnakeGame.DOWN:
			rotation = 0;
			break;
		}

		glRotatef(rotation, 0, 0, 1.0f);
		glBegin(GL_QUADS);
		// bottom RIGHT
		glVertex3f(0, SnakeGame.SNAKE_SIZE, 0);
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -1);
		glVertex3f(SnakeGame.SNAKE_SIZE, 0, -1);
		glVertex3f(SnakeGame.SNAKE_SIZE, 0, 0);

		glVertex3f(SnakeGame.SNAKE_SIZE, 0, -1);
		glVertex3f(SnakeGame.SNAKE_SIZE, 0, 0);
		glVertex3f(SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, 0);
		glVertex3f(SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, -1);

		glVertex3f(SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, -1);
		glVertex3f(SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, 0);
		glVertex3f(0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE, 0);
		glVertex3f(0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				-1);

		// bottom Left
		glVertex3f(0, SnakeGame.SNAKE_SIZE, 0);
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -1);
		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, -1);
		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, 0);

		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, -1);
		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, 0);
		glVertex3f(-SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, 0);
		glVertex3f(-SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, -1);

		glVertex3f(-SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, -1);
		glVertex3f(-SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, 0);
		glVertex3f(-0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				0);
		glVertex3f(-0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				-1);

		// top RIGHT
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE);
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -2);
		glVertex3f(SnakeGame.SNAKE_SIZE, 0, -2);
		glVertex3f(SnakeGame.SNAKE_SIZE, 0, -SnakeGame.SNAKE_SIZE);

		glVertex3f(SnakeGame.SNAKE_SIZE, 0, -1);
		glVertex3f(SnakeGame.SNAKE_SIZE, 0, -SnakeGame.SNAKE_SIZE);
		glVertex3f(SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE,
				-SnakeGame.SNAKE_SIZE);
		glVertex3f(SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, -1);

		glVertex3f(SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, -1);
		glVertex3f(SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE,
				-SnakeGame.SNAKE_SIZE);
		glVertex3f(0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				-SnakeGame.SNAKE_SIZE);
		glVertex3f(0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				-1);

		// top Left
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE);
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -2);
		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, -2);
		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, -SnakeGame.SNAKE_SIZE);

		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, -1);
		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, -SnakeGame.SNAKE_SIZE);
		glVertex3f(-SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE,
				-SnakeGame.SNAKE_SIZE);
		glVertex3f(-SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, -1);

		glVertex3f(-SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, -1);
		glVertex3f(-SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE,
				-SnakeGame.SNAKE_SIZE);
		glVertex3f(-0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				-SnakeGame.SNAKE_SIZE);
		glVertex3f(-0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				-1);

		glEnd();

		glBegin(GL_POLYGON);
		glColor3f(1, 0, 0);
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -1);
		glVertex3f(SnakeGame.SNAKE_SIZE, 0, -1);
		glVertex3f(SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, -1);
		glVertex3f(0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				-1);
		glVertex3f(-0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				-1);
		glVertex3f(-SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE, -1);
		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, -1);
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -1);

		glEnd();
		glBegin(GL_POLYGON);
		glColor3f(0, 1, 0);
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE);
		glVertex3f(SnakeGame.SNAKE_SIZE, 0, -SnakeGame.SNAKE_SIZE);
		glVertex3f(SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE,
				-SnakeGame.SNAKE_SIZE);
		glVertex3f(0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				-SnakeGame.SNAKE_SIZE);
		glVertex3f(-0.5f * SnakeGame.SNAKE_SIZE, -1.5f * SnakeGame.SNAKE_SIZE,
				-SnakeGame.SNAKE_SIZE);
		glVertex3f(-SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE,
				-SnakeGame.SNAKE_SIZE);
		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, -SnakeGame.SNAKE_SIZE);
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE);

		glEnd();
		glBegin(GL_POLYGON);
		glColor3f(1, 0, 0);
		glVertex3f(0, SnakeGame.SNAKE_SIZE, -SnakeGame.SNAKE_SIZE);
		glVertex3f(-SnakeGame.SNAKE_SIZE, 0, -1);
		glVertex3f(SnakeGame.SNAKE_SIZE, 0, -1);

		glEnd();
		glPopMatrix();
	}

	public static void draw3DQuad(float x, float y, float z, float size) {
		float a = size / 2;

		glPushMatrix();
		glLoadIdentity();
		SnakeGame.setCamera();
		glBegin(GL_QUADS);
		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, z - a);
		glTexCoord2d(1, 1);
		glVertex3f(x + a, y - a, z - a);
		glTexCoord2d(1, 0);
		glVertex3f(x + a, y + a, z - a);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y + a, z - a);

		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, z + a);
		glTexCoord2d(1, 1);
		glVertex3f(x + a, y - a, z + a);
		glTexCoord2d(1, 0);
		glVertex3f(x + a, y + a, z + a);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y + a, z + a);

		glTexCoord2d(0, 1);
		glVertex3f(x - a, y - a, z + a);
		glTexCoord2d(1, 1);
		glVertex3f(x + a, y - a, z + a);
		glTexCoord2d(1, 0);
		glVertex3f(x + a, y - a, z - a);
		glTexCoord2d(0, 0);
		glVertex3f(x - a, y - a, z - a);

		glTexCoord2d(0, 1);
		glVertex3f(x + a, y + a, z + a);
		glTexCoord2d(1, 1);
		glVertex3f(x - a, y + a, z + a);
		glTexCoord2d(1, 0);
		glVertex3f(x - a, y + a, z - a);
		glTexCoord2d(0, 0);
		glVertex3f(x + a, y + a, z - a);
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

	public boolean addPosition(boolean appleEat) {
		if (Math.sqrt(Math.pow(xTemp - this.getX(), 2)
				+ Math.pow(yTemp - this.getY(), 2)) > (SnakeGame.SNAKE_SIZE * 2)) {
			draw3DQuad(xTemp, yTemp, 0, SnakeGame.SNAKE_SIZE * 2);
			this.positions.add(new Position(xTemp, yTemp));
			if (!appleEat) {
				this.positions = this.positions.subList(1, lenght + 1);
			} else {
				appleEat = false;
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
				if (!(this.getMouvement() == SnakeGame.RIGHT))
					this.setMouvement(SnakeGame.LEFT);
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				if (!(this.getMouvement() == SnakeGame.LEFT))
					this.setMouvement(SnakeGame.RIGHT);
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				if (!(this.getMouvement() == SnakeGame.DOWN))
					this.setMouvement(SnakeGame.UP);
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				if (!(this.getMouvement() == SnakeGame.UP))
					this.setMouvement(SnakeGame.DOWN);

			switch (this.getMouvement()) {
			case SnakeGame.LEFT:
				this.setX(this.getX() - 0.05f * delta);
				break;
			case SnakeGame.RIGHT:
				this.setX(this.getX() + 0.05f * delta);
				break;
			case SnakeGame.UP:
				this.setY(this.getY() - 0.05f * delta);
				break;
			case SnakeGame.DOWN:
				this.setY(this.getY() + 0.05f * delta);
				break;
			}
			this.setDirection(this.getMouvement());
		} else {	
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						System.out.println("GAUCHE");
						this.setMouvement(SnakeGame.LEFT);
						if (this.getDirection() == 0)
							this.setDirection(SnakeGame.RIGHT);
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						System.out.println("DROITE");
						this.setMouvement(SnakeGame.RIGHT);
						if (this.getDirection() == 0)
							this.setDirection(SnakeGame.LEFT);
					}
				}
			}
		
			switch (this.getDirection()) {
			case SnakeGame.LEFT:
				if (this.getMouvement() == SnakeGame.RIGHT)
					this.setDirection(SnakeGame.DOWN);
				if (this.getMouvement() == SnakeGame.LEFT)
					this.setDirection(SnakeGame.UP);
				break;
			case SnakeGame.RIGHT:
				if (this.getMouvement() == SnakeGame.LEFT)
					this.setDirection(SnakeGame.DOWN);
				if (this.getMouvement() == SnakeGame.RIGHT)
					this.setDirection(SnakeGame.UP);
				break;
			case SnakeGame.UP:
				if (this.getMouvement() == SnakeGame.RIGHT)
					this.setDirection(SnakeGame.RIGHT);
				if (this.getMouvement() == SnakeGame.LEFT)
					this.setDirection(SnakeGame.LEFT);
				break;
			case SnakeGame.DOWN:
				if (this.getMouvement() == SnakeGame.LEFT)
					this.setDirection(SnakeGame.LEFT);
				if (this.getMouvement() == SnakeGame.RIGHT)
					this.setDirection(SnakeGame.RIGHT);
				break;
			}

			switch (this.getDirection()) {
			case SnakeGame.LEFT:
				this.setX(this.getX() - 0.05f * delta);
				break;
			case SnakeGame.RIGHT:
				this.setX(this.getX() + 0.05f * delta);
				break;
			case SnakeGame.UP:
				this.setY(this.getY() - 0.05f * delta);
				break;
			case SnakeGame.DOWN:
				this.setY(this.getY() + 0.05f * delta);
				break;
			}
			this.setMouvement(0);
			System.out.println(direction+" "+mouvement);
		}

	}

	public boolean checkWallCollision(List<Position> walls) {
		Position actual = new Position(this.getX(), this.getY());

		if (actual.checkCollapse(walls, SnakeGame.WALL_SIZE))
			return true;

		if (this.getX() < -SnakeGame.MAP_SIZE + SnakeGame.SNAKE_SIZE) {
			this.setY(-SnakeGame.MAP_SIZE + SnakeGame.SNAKE_SIZE);
			return true;
		}
		if (this.getX() > SnakeGame.MAP_SIZE - SnakeGame.SNAKE_SIZE) {
			this.setY(SnakeGame.MAP_SIZE - SnakeGame.SNAKE_SIZE);
			return true;
		}
		if (this.getY() < -SnakeGame.MAP_SIZE + SnakeGame.SNAKE_SIZE) {
			this.setY(-SnakeGame.MAP_SIZE + SnakeGame.SNAKE_SIZE);
			return true;
		}
		if (this.getY() > SnakeGame.MAP_SIZE - SnakeGame.SNAKE_SIZE) {
			this.setY(SnakeGame.MAP_SIZE - SnakeGame.SNAKE_SIZE);
			return true;
		}

		if (actual.checkCollapse(this.positions, SnakeGame.SNAKE_SIZE / 2))
			return true;
		return false;
	}

}
