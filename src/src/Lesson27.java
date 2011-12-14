package src;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import java.io.FileInputStream;
import org.lwjgl.util.glu.*;
import org.lwjgl.input.Keyboard;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author penguin
 */
public class Lesson27 {

  public static void main(String[] args) {
      try {
          Display.setDisplayMode(new DisplayMode(800,600));
          Display.setTitle("3D Pyramid");
          Display.create();
      } catch(Exception e) {
       
      }
          initGL();

          float rtri = 0.0f;
          Texture texture = null;
        try {
            texture = TextureLoader.getTexture("JPG", new FileInputStream("texture/caisse.jpg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      while(!Display.isCloseRequested()) {
          // Draw a Triangle :D

          GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

          GL11.glLoadIdentity();

     GL11.glTranslatef(0.0f,0.0f,-10.0f);

          GL11.glRotatef(rtri, 0.0f, 1.0f, 0.0f);

          texture.bind();

          GL11.glBegin(GL11.GL_TRIANGLES);

            GL11.glTexCoord2f(0.0f, 1.0f);
            GL11.glVertex3f(0.0f, 1.0f,0.0f);
            GL11.glTexCoord2f(-1.0f,-1.0f);
            GL11.glVertex3f(-1.0f, -1.0f,1.0f);
            GL11.glTexCoord2f(1.0f, -1.0f);
            GL11.glVertex3f(1.0f, -1.0f,1.0f);

            GL11.glTexCoord2f(0.0f, 1.0f);
            GL11.glVertex3f( 0.0f, 1.0f, 0.0f);
            GL11.glTexCoord2f(-1.0f,-1.0f);
            GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
            GL11.glTexCoord2f(1.0f, -1.0f);
            GL11.glVertex3f( 1.0f,-1.0f, -1.0f);

            GL11.glTexCoord2f(0.0f, 1.0f);
            GL11.glVertex3f( 0.0f, 1.0f, 0.0f);
            GL11.glTexCoord2f(-1.0f,-1.0f);
            GL11.glVertex3f(-1.0f,-1.0f, -1.0f);
            GL11.glTexCoord2f(1.0f, -1.0f);
            GL11.glVertex3f( 1.0f,-1.0f, -1.0f);

            GL11.glTexCoord2f(0.0f, 1.0f);
            GL11.glVertex3f(0.0f, 1.0f,0.0f);
            GL11.glTexCoord2f(-1.0f,-1.0f);
            GL11.glVertex3f(-1.0f, -1.0f,-1.0f);
            GL11.glTexCoord2f(1.0f, -1.0f);
            GL11.glVertex3f(-1.0f, -1.0f,1.0f);


          GL11.glEnd();

          GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
            GL11.glVertex3f( 1.0f,-1.0f, -1.0f);
            GL11.glVertex3f(-1.0f,-1.0f, -1.0f);
            GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
          GL11.glEnd();

          Display.update();
          rtri += 0.5f;
          // Exit-Key = ESC
          boolean exitPressed = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
          if(exitPressed) {
              System.out.println("Escape was pressed!");
              Display.destroy();

          }
      }

      Display.destroy();
  }

  private static void initGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45.0f,((float)800)/((float)600),0.1f,100.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        float lightAmbient[] = { 0.5f, 0.5f, 0.5f, 1.0f };  // Ambient Light Values
        float lightDiffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };      // Diffuse Light Values
        float lightPosition[] = { 0.0f, 0.0f, 2.0f, 1.0f }; // Light Position

        ByteBuffer temp = ByteBuffer.allocateDirect(16);
        temp.order(ByteOrder.nativeOrder());
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, (FloatBuffer)temp.asFloatBuffer().put(lightAmbient).flip());              // Setup The Ambient Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, (FloatBuffer)temp.asFloatBuffer().put(lightDiffuse).flip());              // Setup The Diffuse Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION,(FloatBuffer)temp.asFloatBuffer().put(lightPosition).flip());         // Position The Light
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT1);                          // Enable Light One
  }
}