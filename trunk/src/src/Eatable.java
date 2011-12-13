package src;

import java.awt.Color;
import tools.Position;

public class Eatable {

	public static final int GROW_UP = 1;
	public static final int REDUCE = 2;
	public static final int SLOW=3;
	
	public float x;
	public float y;
	
	public Color color;
	public int action;
	/**
	 * @param x
	 * @param y
	 * @param color
	 * @param action
	 */
	public Eatable(float x, float y, Color color, int action) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.action = action;
		while((new Position(this.x,this.y)).checkCollapse(Game.walls, Game.WALL_SIZE, Game.APPLE_SIZE)){
			this.x = (float) (-(Game.MAP_SIZE-Game.APPLE_SIZE) + (Game.MAP_SIZE * 2 - Game.APPLE_SIZE)
					* Math.random());
			this.y = (float) (-(Game.MAP_SIZE-Game.APPLE_SIZE) + (Game.MAP_SIZE * 2 - Game.APPLE_SIZE)
					* Math.random());	
			}
		while((new Position(this.x,this.y)).checkCollapse(Game.object, Game.SNAKE_SIZE, Game.APPLE_SIZE)){
			this.x = (float) (-(Game.MAP_SIZE-Game.APPLE_SIZE) + (Game.MAP_SIZE * 2 - Game.APPLE_SIZE)
					* Math.random());
			this.y = (float) (-(Game.MAP_SIZE-Game.APPLE_SIZE) + (Game.MAP_SIZE * 2 - Game.APPLE_SIZE)
					* Math.random());	
			}
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
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * @return the action
	 */
	public int getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(int action) {
		this.action = action;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Eatable [x=" + x + ", y=" + y + ", color=" + color
				+ ", action=" + action + "]";
	}
	public void draw() {
		// TODO Auto-generated method stub
		
	}
	
}
