import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.*;

public class GameWorld {
	
	private int width = 640;
	private int height = 480;
	
	private int zNear = 1000;
	private int zFar = -1000;
	
	// Method to begin setup
	public void start() {
		
		init();
		
		/* isCloseRequested comes in the form of the X on a window being pushed,
		   a CTRL-C keystroke, etc. Continue updating until this happens. update()
		   swaps the buffers.
		 */
		
		while (!Display.isCloseRequested()) {
			
			pollMouse();
			pollKeyboard();
			updateMap();
			drawHero();
			Display.update();
		
		}
	}
	
	// Method to initialize the display
	public void init() {
	
		try {
			
			Display.setDisplayMode( new DisplayMode( width, height ) );
			Display.create();
		
		} catch (LWJGLException e) {
			
			e.printStackTrace();
			System.exit(1);
		
		}
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//GL11.glOrtho(-(width/2), (width/2), -(height/2), (height/2), zNear, zFar);
		GLU.gluPerspective(60.0f, (float)width/(float)height, 0.1f, 60.0f);
		GL11.glTranslated(0.0, 0.0, -4.0);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	// Method to load the map
	public void loadMap() {
		
		//TODO: Read in map (from text file?)
		
	}
	
	// Method to load the world entities
	public void loadWorld() {
		
		//TODO: Read in the objects that make up the game world
		
	}
	
	// Method to draw the character
	public void drawHero() {
		
		GL11.glPushMatrix();
		GL11.glColor3d(0.0, 1.0, 0.0);
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex3d(0, 0, -5);
			GL11.glVertex3d(-5, 0, 0);
			GL11.glVertex3d(5, 0, 0);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	// Method to update the map
	public void updateMap() {
		updateHeroLocation();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		GLU.glutWireCube(2);
	}
	
	// Method to poll the mouse
	public void pollMouse() {
		
		// Right click
		if (Mouse.isButtonDown(0)) { 
			System.out.println(Mouse.getX() + " " + Mouse.getY());
		}
		
	}
	
	// Method to poll the keyboard
	public void pollKeyboard() {
		
		// While there are key events on the event buffer
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				switch (Keyboard.getEventKey()) {
					case Keyboard.KEY_W:
						System.out.println("W");
						break;
					case Keyboard.KEY_A:
						System.out.println("A");
						break;
					case Keyboard.KEY_S:
						System.out.println("S");
						break;
					case Keyboard.KEY_D:
						System.out.println("D");
						break;
					default:
						break;
				}
			}
		}
		
	}
	
	// Method to move the Hero
	public void updateHeroLocation() {
		
	}
}