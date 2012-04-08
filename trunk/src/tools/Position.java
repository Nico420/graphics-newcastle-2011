package tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.Eatable;

/**
 * Position on the map.
 * 
 * @author Nicolas
 * 
 */
public class Position {

	/** X position. */
	private float x;
	/** Y position. */
	private float y;
	/** Z position. */
	private float z;

	/**
	 * Constructor.
	 * 
	 * @param pX
	 *            X position
	 * @param pY
	 *            Y position
	 */
	public Position(float pX, float pY) {
		this.x = pX;
		this.y = pY;
		this.z = 0;
	}

	/**
	 * Check collapsing.
	 * 
	 * @param object
	 *            Object list
	 * @param firstSize
	 *            Size of the first element
	 * @param secondSize
	 *            Size of the second element
	 * @return True if collapsing
	 */
	public boolean checkCollapse(ArrayList<Eatable> object, int firstSize,
			int secondSize) {
		Iterator<Eatable> ite = object.iterator();
		while (ite.hasNext()) {
			Eatable item = ite.next();
			Position t = new Position(item.getX(), item.getY());
			boolean collisionX = Math.abs(t.getX() - this.getX()) < secondSize
					+ firstSize;
			boolean collisionY = Math.abs(t.getY() - this.getY()) < secondSize
					+ firstSize;
			if (collisionX && collisionY) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check collapsing.
	 * 
	 * @param positions
	 *            Positions list
	 * @param firstSize
	 *            Size of the first element
	 * @param secondSize
	 *            Size of the second element
	 * @return True if collapsing
	 */
	public boolean checkCollapse(List<Position> positions, int firstSize,
			int secondSize) {
		Iterator<Position> ite = positions.iterator();
		while (ite.hasNext()) {
			Position t = ite.next();
			boolean collisionX = Math.abs(t.getX() - this.getX()) < secondSize
					+ firstSize;
			boolean collisionY = Math.abs(t.getY() - this.getY()) < secondSize
					+ firstSize;
			if (collisionX && collisionY) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Test equality.
	 * 
	 * @param o
	 *            Object to compare
	 * @return True if equal
	 */
	public boolean equals(Object o) {
		if (o instanceof Position) {
			return (((Position) o).getX() == this.x)
					&& ((Position) o).getY() == this.y;
		}
		return false;
	}

	/**
	 * Get X.
	 * 
	 * @return Position X
	 */
	public float getX() {
		return x;
	}

	/**
	 * Get Y.
	 * 
	 * @return Position Y
	 */
	public float getY() {
		return y;
	}

	/**
	 * Get Z.
	 * 
	 * @return Position Z
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Set a new X.
	 * 
	 * @param pX
	 *            the new X
	 */
	public void setX(float pX) {
		this.x = pX;
	}

	/**
	 * Set a new Y.
	 * 
	 * @param pY
	 *            the new Y
	 */
	public void setY(float pY) {
		this.y = pY;
	}

	/**
	 * Set a new Z.
	 * 
	 * @param pZ
	 *            the new Z
	 */
	public void setZ(float pZ) {
		this.z = pZ;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
}
