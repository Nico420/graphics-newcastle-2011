package snakeMoteur;

import java.io.StreamTokenizer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class JeuConsole {

	
 public static void main(String[] args){
	 Carte c = new Carte(10, 10, 1);
	 System.out.println(c);
	 
	 if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX();
			int y = Mouse.getY();

			System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			System.out.println("SPACE KEY IS DOWN");
		}
 }
}
