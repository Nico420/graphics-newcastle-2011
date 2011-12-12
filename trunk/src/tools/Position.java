package tools;

import java.util.Iterator;
import java.util.List;

import src.Game;

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

	public boolean checkCollapse(List<Position> positions, int size) {
		Iterator<Position> ite = positions.iterator();
		while (ite.hasNext()) {
			Position t = ite.next();
			boolean collisionX = Math.abs(t.getX()-this.getX())<Game.SNAKE_SIZE + size;
			boolean collisionY = Math.abs(t.getY()-this.getY())<Game.SNAKE_SIZE + size;
			if (collisionX && collisionY)
				return true;				
		}
		return false;
	}
}
