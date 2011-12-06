package src;

import java.util.Iterator;
import java.util.List;

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

	public static boolean checkCollapse(Position e, Position t) {
		boolean res = false;

		return res;

	}

	public boolean checkCollapse(List<Position> positions) {
		Iterator<Position> ite = positions.iterator();
		while (ite.hasNext()) {
			Position t = ite.next();
			if (this.getX() - Test.SNAKE_SIZE < this.getX()
					&& this.getX() + Test.SNAKE_SIZE > this.getX()
					&& this.getY() - Test.SNAKE_SIZE < this.getY()
					&& this.getY() + Test.SNAKE_SIZE > this.getY()){
				System.out.println(t+" "+this);
				return true;
				
			}
				
		}
		return false;
	}
}
