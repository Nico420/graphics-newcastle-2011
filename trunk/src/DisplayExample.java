package src;
// DisplayExample
// Just creates a blank (black) window, ready for OpenGL to render to
//
// Exercises
// 
// 01 - Create display windows of different sizes, are all sizes valid? What other parameters are they about display modes?
// 02 - Find out what valid display modes you can use (at runtime)? Can you set the display mode to one of them?
// 03 - Could you write a simple algorithm to choose the "best" available display mode
// 04 - Create a window of the present desktop resolution. Is there anything wrong with it?
// 05 - Can you solve the problem above? 

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

@SuppressWarnings("unused")
public class DisplayExample {
	
	public static boolean exit=false;
	public static boolean displayChange=false;
	
	public void start() throws LWJGLException {
		try {
			DisplayMode dm = Display.getAvailableDisplayModes()[0];
			System.out.println(dm);
			Display.setDisplayMode(dm);
			Display.setFullscreen(false);
			Display.setResizable(true);
			Display.setTitle("TP 1 - Graphics");
			Display.create();

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// init OpenGL here
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 600, 0, 100, -100);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		float rtri = 3f;
		float ttri = 0f;
        

		while (!Display.isCloseRequested() && !exit) {												// Done Drawing The Quad
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer
		     
			GL11.glLoadIdentity();
			
			GL11.glTranslatef(400, 300, 0);
			// set the color of the quad (R,G,B,A)
		        GL11.glColor3f(1f,0f,0f);
		        
		        GL11.glPushMatrix();
		        GL11.glRotatef(rtri, 0, 0, 1);
			        GL11.glRotatef(ttri, 0, 1, 0);
			        GL11.glBegin(GL11.GL_TRIANGLES);
						GL11.glVertex3f(100,100,0);
						GL11.glVertex3f(200, 100, 0);
						GL11.glVertex3f(200, 200, 0);
					GL11.glEnd();
				GL11.glPopMatrix();
		
				/*GL11.glColor3f(0f,0f,1f);
				
				GL11.glPushMatrix();
		        GL11.glTranslated(0, ttri, 0);
		        GL11.glRotatef(rtri, 1, 0, 0);
		        GL11.glBegin(GL11.GL_TRIANGLES);
					GL11.glVertex3f(100,0,0);
					GL11.glVertex3f(200, 0, 0);
					GL11.glVertex3f(200, 100, 0);
				GL11.glEnd();
				GL11.glPopMatrix();*/
				pollInput();
				
			if(displayChange){
				if(Display.isFullscreen())
					Display.setFullscreen(false);
				else
					Display.setFullscreen(true);
					
			}
			Display.update(); // flushes OpenGL pipeline and swaps back and
								// front buffers. perhaps waits for v-sync.
			rtri+=0.1f;
			ttri+= 0.1f;
		}

		Display.destroy();
	}

	public static void main(String[] argv) throws LWJGLException {
		DisplayExample displayExample = new DisplayExample();
		displayExample.start();
	}
	
	
	public void pollInput() throws LWJGLException {

		if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX();
			int y = Mouse.getY();

			System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			System.out.println("SPACE KEY IS DOWN");
		}
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Pressed");
					displayChange=true;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					System.out.println("S Key Pressed");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("D Key Pressed");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
					System.out.println("Escape Key Pressed");
					exit=true;
				}
			} else {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Released");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					System.out.println("S Key Released");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("D Key Released");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
					System.out.println("Escape Key Released");
				}
			}
		}
	}
}