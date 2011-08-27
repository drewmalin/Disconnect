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
	
	private float zNear = 0.1f;
	private float zFar = 60f;
	private float frustAngle = 60f;
	
	private float cameraPosX = 0f;
	private float cameraPosY = 2f;
	private float cameraPosZ = 6f;
	private float cameraTargetX = 0f;
	private float cameraTargetY = 0f;
	private float cameraTargetZ = 0f;
	
	private double playerPosX = 0;
	private double playerPosY = 0;
	private double playerPosZ = 0;
	
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
		
		Display.destroy();
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
		GLU.gluPerspective(frustAngle, (float)width/(float)height, zNear, zFar);
		GLU.gluLookAt(
				cameraPosX, cameraPosY, cameraPosZ,				// eye position
				cameraTargetX, cameraTargetY, cameraTargetZ, 	// target to look at (origin)
				0f, 1f, 0f);									// specify up axis
		
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
		GL11.glColor3d(0d, 1d, 0d);
		
		GL11.glBegin(GL11.GL_QUADS);	//Back
			GL11.glVertex3d(0.25d, 0.25d, 1d);
			GL11.glVertex3d(-0.25d, 0.25d, 1d);
			GL11.glVertex3d(-0.25d, -0.25d, 1d);
			GL11.glVertex3d(0.25d, -0.25d, 1d);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Top
			GL11.glVertex3d(0.25d, 0.25d, 1d);
			GL11.glVertex3d(0d, 0d, 0d);
			GL11.glVertex3d(-0.25d, 0.25d, 1d);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Left
			GL11.glVertex3d(-0.25d, 0.25d, 1d);
			GL11.glVertex3d(0d, 0d, 0d);
			GL11.glVertex3d(-0.25d, -0.25d, 1d);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Bottom
			GL11.glVertex3d(-0.25d, -0.25d, 1d);
			GL11.glVertex3d(0d, 0d, 0d);
			GL11.glVertex3d(0.25d, -0.25d, 1d);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Right
			GL11.glVertex3d(0.25d, -0.25d, 1d);
			GL11.glVertex3d(0d, 0d, 0d);
			GL11.glVertex3d(0.25d, 0.25d, 1d);
		GL11.glEnd();
		GL11.glPopMatrix();
		
	}
	
	// Method to update the map
	public void updateMap() {
		updateHeroLocation();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
			
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3d(1.0, 0.0, 0.0);
		
		for (int x = -5; x <= 5; x++) {
			GL11.glVertex3d((double)x + playerPosX, 0d + playerPosY, -5d + playerPosZ);
			GL11.glVertex3d((double)x + playerPosX, 0d + playerPosY, 5d + playerPosZ);
		}
		
		for (int z = -5; z <= 5; z++) {
			GL11.glVertex3d(5d + playerPosX, 0d + playerPosY, (double)z + playerPosZ);
			GL11.glVertex3d(-5d + playerPosX, 0d + playerPosY, (double)z + playerPosZ);
		}
		GL11.glEnd();
		
	}
	
	// Method to poll the mouse
	public void pollMouse() {
		
		// Left click
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
						playerPosZ += 0.1;
						break;
					case Keyboard.KEY_A:
						System.out.println("A");
						playerPosX += 0.1;
						break;
					case Keyboard.KEY_S:
						System.out.println("S");
						playerPosZ -= 0.1;
						break;
					case Keyboard.KEY_D:
						System.out.println("D");
						playerPosX -= 0.1;
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