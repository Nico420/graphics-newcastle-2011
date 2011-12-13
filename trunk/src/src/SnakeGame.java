package src;

import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import tools.Position;

public class SnakeGame {

	Etat etat;
	int etatActual = MENU;
	int etatTemp = MENU;
	// Constant for States
	public static final int MENU = 0;
	public static final int GAME = 4;
	public static final int HIGHSCORE = 2;
	public static final int QUIT = 3;
	public static final int COUNTDOWN = 1;
	public static final int PERDU = 5;

	public static final int APPLENUMBER = 5;
	
	public static final int HEIGHT=600;
	public static final int WIDTH=800;
	

	
	public static final Position MAP_MILIEU = new Position(WIDTH-300, HEIGHT-300); 
	// float lightPosition1[] = { -MAP_SIZE, -MAP_SIZE, 1f, 1f };

	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	public static boolean exit = false;
	public static boolean switchView = false;

	public int chooseBestDisplay() throws LWJGLException {
		int res = 3;
		return res;
	}

	public void start() throws LWJGLException, InterruptedException,
			IOException {

		try {
			int bestDisplay = chooseBestDisplay();
			DisplayMode dm = Display.getAvailableDisplayModes()[bestDisplay];
			System.out.println(dm);
			Display.setDisplayMode(dm);
			Display.setFullscreen(false);
			Display.setResizable(true);
			Display.setTitle("Graphics");
			Display.create();

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		etat = new Menu();
		getDelta();
		lastFPS = getTime();
		while (!Display.isCloseRequested() && !exit) { // Done Drawing The Quad
			if (etatTemp == QUIT) {
				exit = true;
			} else if (etatTemp != etatActual) {
				switch (etatTemp) {
				case MENU:
					etat = new Menu();
					break;
				case GAME:
					etat = new Game();
					break;
				case COUNTDOWN:
					etat = new Countdown();
					break;
				case PERDU:
					etat = new Menu();
					break;
				case HIGHSCORE:
					etat = new HighScore();
				}
				etatActual = etatTemp;
			}

			int delta = getDelta();
			etatTemp = etat.update(delta);
			etat.renderGL();
			pollInput();
			Display.update(); // flushes OpenGL pipeline and swaps back and
								// front buffers. perhaps waits for v-sync.
		}

		Display.destroy();
	}

	public static void main(String[] argv) throws LWJGLException,
			InterruptedException, IOException {
		SnakeGame game = new SnakeGame();
		game.start();
	}

	public void pollInput() throws LWJGLException, IOException {

		if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX();
			int y = Mouse.getY();
			System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Pressed");
					if (Display.isFullscreen())
						Display.setFullscreen(false);
					else
						Display.setFullscreen(true);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					System.out.println("Escape Key Pressed");
					exit = true;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					System.out.println("V Key Pressed");
					switchView = switchView ? false : true;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_R) {
					System.out.println("R Key Pressed - Game restart");
					etat = new Game();
				}
			}
		}
	}

	private int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
}