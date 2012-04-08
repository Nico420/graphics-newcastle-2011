package src;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import state.Etat;
import state.Menu;
import tools.Position;

/**
 * Main class of the application.
 * 
 * @author Nicolas
 * 
 */
public class SnakeGame {

	/**
	 * Build a snake Game
	 * 
	 * @param pAppleNumber
	 *            The number of apple
	 */
	public SnakeGame(int pAppleNumber) {
		super();
		this.appleNumber = pAppleNumber;
		//this.etat = new Menu(this);
	}

	/** Apple number */
	private int appleNumber;

	/** Window height */
	public static final int HEIGHT = 600;
	/** Window Width */
	public static final int WIDTH = 800;

	/** Middle of the map */
	public static final Position MAP_MILIEU = new Position(WIDTH - 400,
			HEIGHT - 300);

	/** Game state */
	private Etat etat;

	/** Exit the game */
	private static boolean exit = false;
	/** Switch view */
	private static boolean switchView = false;

	// float lightPosition1[] = { -MAP_SIZE, -MAP_SIZE, 1f, 1f };

	/** time at last frame */
	private long lastFrame;

	/**
	 * Get the state
	 * 
	 * @return The current state
	 */
	public Etat getEtat() {
		return etat;
	}

	/**
	 * Change the state
	 * 
	 * @param pEtat
	 *            The new state to set.
	 */
	public void setEtat(Etat pEtat) {
		this.etat = pEtat;
	}

	/**
	 * Start the game
	 */
	public void start() {

		try {
			DisplayMode dm = Display.getAvailableDisplayModes()[3];
			Display.setDisplayMode(dm);
			Display.setFullscreen(false);
			Display.setResizable(true);

			Display.setTitle("Snake 3D - 2012 - version 1.0");
			Display.create();

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		etat = new Menu(this);
		getDelta();

		while (!Display.isCloseRequested() && !exit) { // Done Drawing The Quad

			int delta = getDelta();
			// Update and draw the state.
			try {
				etat.update(delta);
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
			etat.renderGL();
			// Checking if keyboard interaction made the state change
			etat.pollInput();
			Display.update(); // flushes OpenGL pipeline and swaps back and
								// front buffers. perhaps waits for v-sync.
		}

		Display.destroy();
	}

	/**
	 * Get the Delta
	 * 
	 * @return The delta
	 */
	private int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get Time
	 * 
	 * @return Sys time
	 */
	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Launching the game
	 * 
	 * @param argv
	 *            Launching args
	 * @throws LWJGLException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] argv) {
		SnakeGame game = new SnakeGame(5);
		game.start();
	}

	/**
	 * Knowing the actual view
	 * 
	 * @return The view
	 */
	public static boolean isSwitchView() {
		return switchView;
	}

	/**
	 * Change view
	 * 
	 * @param pSwitchView
	 *            The new view to set
	 */
	public static void setSwitchView(boolean pSwitchView) {
		SnakeGame.switchView = pSwitchView;
	}

	/**
	 * Get the apple number
	 * 
	 * @return The apple number
	 */
	public int getAppleNumber() {
		return appleNumber;
	}

	/**
	 * Set a new apple number
	 * 
	 * @param pAppleNumber
	 *            The number of apple
	 */
	public void setAppleNumber(int pAppleNumber) {
		appleNumber = pAppleNumber;
	}
}
