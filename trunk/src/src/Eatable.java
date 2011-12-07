package src;

import java.awt.Color;

public class Eatable {

	public static final int GROW_UP = 0;
	public static final int REDUCE = 0;
	
	public float x;
	public float y;
	
	public Color color;
	public float action;
	/**
	 * @param x
	 * @param y
	 * @param color
	 * @param action
	 */
	public Eatable(float x, float y, Color color, float action) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.action = action;
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
	public float getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(float action) {
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
	
	
}
