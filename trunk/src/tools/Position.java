package tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.Eatable;

/**
 * Position on the map
 * @author Nicolas
 *
 */
public class Position {
	public float x;
	public float y;
	public float z;

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

	public boolean equals(Object o) {
		if (o instanceof Position) {
			return (((Position) o).getX() == this.x)
					&& ((Position) o).getY() == this.y;
		}
		return false;
	}

	public boolean checkCollapse(List<Position> positions, int firstSize,
			int secondSize) {
		Iterator<Position> ite = positions.iterator();
		while (ite.hasNext()) {
				Position t = ite.next();
				boolean collisionX = Math.abs(t.getX() - this.getX()) < secondSize
						+ firstSize;
				boolean collisionY = Math.abs(t.getY() - this.getY()) < secondSize
						+ firstSize;
				if (collisionX && collisionY)
					return true;
		}
		return false;
	}

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
			if (collisionX && collisionY)
				return true;
		}
		return false;
	}
}
