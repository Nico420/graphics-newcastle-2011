package src;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.io.IOException;

public class Countdown extends Etat {

	public float countDown = 3;
	
	@Override
	public int update(int delta) {
		countDown-=0.001f;
		updateFPS();
		if(countDown>1)
			return SnakeGame.COUNTDOWN;
		else{
			return SnakeGame.GAME;
		}
		
	}

	@Override
	public void renderGL() throws IOException {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		int number = (int) Math.ceil(countDown);
		switch(number){
		case 1:
			glColor3f(0, 0, 1);
			break;
		case 2:
			glColor3f(0, 1, 0);
			break;
		case 3:
			glColor3f(1, 0, 0);
			break;
			
		}
		
		glBegin(GL_QUADS);
		// Start New game
		glVertex3f(-40, -20, 0);
		glVertex3f(-40, 0, 0);
		glVertex3f(40, 0, 0);
		glVertex3f(40, -20, 0);
		glEnd();

	}

}
