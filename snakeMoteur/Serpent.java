package snakeMoteur;

import java.awt.Color;

public class Serpent {

	private String name;
	private int length;
	private int speed;
	private Color color;
	
	public Serpent(){
		name="player 1";
		length = 3;
		speed=1;
		setColor(Color.GREEN);
	}

	public Serpent(String name, int length, int speed) {
		this.name = name;
		this.length = length;
		this.speed = speed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
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

	public void incLength(int inc){
		this.setLength(this.getLength()+inc);
	}
	
	public void incSpeed(int inc){
		this.setSpeed(this.getSpeed()+inc);
	}
	
	public void decLength(int dec){
		this.setLength(this.getLength()-dec);
	}
	
	public void decSpeed(int dec){
		this.setSpeed(this.getSpeed()-dec);
	}
	

	@Override
	public String toString() {
		return "Serpent [name=" + name + ", length=" + length + ", speed="
				+ speed + "]";
	}
}
