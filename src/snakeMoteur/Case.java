package snakeMoteur;

import java.awt.Color;

public class Case {

	int type;
	Color color;
	
	public Case(int type, Color color){
		this.type=type;
		this.color = color;
	}

	@Override
	public String toString() {
		return "Case [type=" + type + ", color=" + color + "]";
	}
}
