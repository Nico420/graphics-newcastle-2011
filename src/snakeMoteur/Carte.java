package snakeMoteur;

import java.awt.Color;
import java.util.Arrays;

public class Carte {
	
	public static int FREE=0;
	public static int APPLES=1;
	public static int WALL=2;
	
	private int width;
	private int heigth;
	private Case[] cases;
	private Serpent[] serpents;
	
	public Carte(){
		width = 100;
		heigth = 100;
		
		Case[] cases = new Case[100];
		for(int i=0;i<cases.length;i++)
			cases[i]=new Case(FREE, Color.BLUE);
		setCases(cases);
		
		Serpent[] serpents = new Serpent[1];
		for(int i=0;i<serpents.length;i++)
			serpents[i]=new Serpent("Nicolas", 1,Color.BLUE, new Position(0, 0));
		
		setSerpents(serpents);
	}
	
	public Carte(int heigth, int width, int numPlayer){
		this.width=width;
		this.heigth=heigth;
		
		Case[] cases = new Case[heigth*width];
		for(int i=0;i<cases.length;i++)
			cases[i]=new Case(FREE, Color.BLUE);
		setCases(cases);
		
		Serpent[] serpents = new Serpent[numPlayer];
		for(int i=0;i<serpents.length;i++)
			serpents[i]=new Serpent("Nicolas", 1,Color.BLUE, new Position(0, 0));
		
		setSerpents(serpents);
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeigth() {
		return heigth;
	}
	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public Case[] getCases() {
		return cases;
	}

	public void setCases(Case[] cases) {
		this.cases = cases;
	}

	public Serpent[] getSerpents() {
		return serpents;
	}

	public void setSerpents(Serpent[] serpents) {
		this.serpents = serpents;
	}

	@Override
	public String toString() {
		return "Carte [width=" + width + ", heigth=" + heigth + ", cases="
				+ Arrays.toString(cases) + ", serpents="
				+ Arrays.toString(serpents) + "]";
	}
}
