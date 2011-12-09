package src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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
	/**
	 * @return the mouvement
	 */
	public int getMouvement() {
		return mouvement;
	}

	/**
	 * @param mouvement the mouvement to set
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
	
	public Snake(){
		this.name="Snake";
		this.positions = new ArrayList<Position>();
		this.x = 0;
		this.y=0;
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
	 * @param name the name to set
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
	 * @param positions the positions to set
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
	 * @param x the x to set
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
	 * @param y the y to set
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
	 * @param c the c to set
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
	 * @param lenght the lenght to set
	 */
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	
	public void draw(){
		drawSnakeHead(x, y, 0, SnakeGame.SNAKE_SIZE * 2);
		// Dessin du serpent
		// draw3DQuad(xTemp, yTemp, 0, SNAKE_SIZE * 2);
		for (int i = 0; i < positions.size(); i++) {
			draw3DQuad(positions.get(i).getX(), positions.get(i).getY(), 0,
					SnakeGame.SNAKE_SIZE * 2);
		}
		
	}

	public void intialize(List<Position> walls, int wallSize) {
		
		while((new Position(x,y).checkCollapse(walls, SnakeGame.WALL_SIZE))){
			System.out.println(x+ " "+y);
			x = (float) (-SnakeGame.MAP_SIZE + SnakeGame.MAP_SIZE*2*Math.random());
			y = (float) (-SnakeGame.MAP_SIZE + SnakeGame.MAP_SIZE*2*Math.random());
		}
		
	}
	
	private void drawSnakeHead(float x, float y, float z, int size) {
		float a = size / 2;
		
		glPushMatrix();                                    //Reset The View
		glBegin(GL_QUADS);
		glVertex3f(x - a, y - a, z - a);
		glVertex3f(x + a, y - a, z - a);
		glVertex3f(x + a, y + a, z - a);
		glVertex3f(x - a, y + a, z - a);
		glVertex3f(x - a, y - a, z + a);
		glVertex3f(x + a, y - a, z + a);
		glVertex3f(x + a, y + a, z + a);
		glVertex3f(x - a, y + a, z + a);
		glVertex3f(x - a, y - a, z + a);
		glVertex3f(x + a, y - a, z + a);
		glVertex3f(x + a, y - a, z - a);
		glVertex3f(x - a, y - a, z - a);
		glVertex3f(x + a, y + a, z + a);
		glVertex3f(x - a, y + a, z + a);
		glVertex3f(x - a, y + a, z - a);
		glVertex3f(x + a, y + a, z - a);
		glEnd();
		glPopMatrix();
	}
	
	public static void draw3DQuad(float x, float y, float z, float size) {
		float a = size / 2;

		glPushMatrix();
		glBegin(GL_QUADS);
		glVertex3f(x - a, y - a, z - a);
		glVertex3f(x + a, y - a, z - a);
		glVertex3f(x + a, y + a, z - a);
		glVertex3f(x - a, y + a, z - a);
		glVertex3f(x - a, y - a, z + a);
		glVertex3f(x + a, y - a, z + a);
		glVertex3f(x + a, y + a, z + a);
		glVertex3f(x - a, y + a, z + a);
		glVertex3f(x - a, y - a, z + a);
		glVertex3f(x + a, y - a, z + a);
		glVertex3f(x + a, y - a, z - a);
		glVertex3f(x - a, y - a, z - a);
		glVertex3f(x + a, y + a, z + a);
		glVertex3f(x - a, y + a, z + a);
		glVertex3f(x - a, y + a, z - a);
		glVertex3f(x + a, y + a, z - a);
		glEnd();
		glPopMatrix();

	}

	/* (non-Javadoc)
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
	
}
