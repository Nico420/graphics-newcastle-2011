package snakeMoteur;

import java.awt.Color;
import java.util.ArrayList;

public class Serpent {

	private String name;
	private int speed;
	private Color color;

	private ArrayList<Position> positions;

	public Serpent() {
		name = "player 1";
		speed = 1;
		setColor(Color.GREEN);
		setPositions(new ArrayList<Position>());
	}

	public Serpent(String name, int speed, Color c, Position depart) {
		this.name = name;
		this.speed = speed;
		this.color = c;
		setPositions(new ArrayList<Position>());
		positions.add(depart);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return positions.size();
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<Position> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}

	public void mouvement(int x, int y, boolean growing) {
		Position last = positions.get(0);

		positions.add(0, new Position(last.getX() + x, last.getY() + y));
		if (!growing) {
			positions.remove(positions.size() - 1);
		}
	}

	@Override
	public String toString() {
		return "Serpent [name=" + name + ", speed=" + speed + ", color="
				+ color + ", positions=" + positions + "]";
	}
}
