package src;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import tools.Fichier;


@SuppressWarnings("deprecation")
public class HighScore extends Etat {

	
	LinkedList<Integer> score;
	
	public HighScore(){
		super();
		score = Fichier.getScore("highScore.txt");
	}
	@Override
	public int update(int delta) {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					return SnakeGame.MENU;
				}
			}
		}
		updateFPS();
		return SnakeGame.HIGHSCORE;
	}

	@Override
	public void renderGL() throws IOException {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		Color.white.bind();

		fontTitre.drawString(200, 10, "SNAKE 3D",
				Color.red);
		fontTitre.drawString(100, 110, "HIGH SCORE TABLE",
				Color.green);

		for(int i=0;i<score.size();i++){
			fontMenu.drawString(250, 230+35*i, (i+1)+" - "+score.get(i),
					Color.yellow);
		}

	}

}
