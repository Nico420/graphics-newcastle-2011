package src;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;

import org.newdawn.slick.util.ResourceLoader;

@SuppressWarnings("deprecation")
public abstract class Etat {
	boolean lighting = false;
	TextureLoader tl = new TextureLoader();

	// ----------- Variables added for Lighting Test -----------//
	protected FloatBuffer matSpecular;
	protected FloatBuffer lightPosition;
	protected FloatBuffer whiteLight;
	protected FloatBuffer lModelAmbient;
	// ----------- END: Variables added for Lighting Test -----------//

	protected TrueTypeFont font;
	protected TrueTypeFont fontMenu;
	protected TrueTypeFont fontTitre;
	protected TrueTypeFont fontPower;

	private boolean antiAlias = true;
	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	public Etat() throws IOException {
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, antiAlias);
		initGL();
		// load font from file
		try {
			InputStream inputStream = ResourceLoader
					.getResourceAsStream("texture/FOO.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(36f); // set font size
			fontMenu = new TrueTypeFont(awtFont2, antiAlias);
			awtFont2 = awtFont2.deriveFont(60f); // set font size
			fontTitre = new TrueTypeFont(awtFont2, antiAlias);
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			fontPower = new TrueTypeFont(awtFont2, antiAlias);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int update(int delta) throws LWJGLException {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					return SnakeGame.MENU;
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_A) {
				System.out.println("A Key Pressed");
				if (Display.isFullscreen())
					Display.setFullscreen(false);
				else
					Display.setFullscreen(true);
			}
		}

		return -1;
	}

	public abstract void renderGL() throws IOException;

	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			fps = 0;
			lastFPS += 1000;
		}
		fps++;

	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	protected abstract void initGL() throws IOException;

	public abstract int pollInput() throws LWJGLException;
	

}
