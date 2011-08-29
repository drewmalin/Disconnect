/*
 *  GameWorld.java
 *  Project Disconnect
 *
 *  Created by Drew Malin on 8/27/2011.
 *  Copyright 2011 Drew Malin. All rights reserved.
 *
 */
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
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
	
	private Camera camera;
	private Hero hero;
	
	private Timer timer;
	private long frameDelta;
	private long FRAME_LENGTH_MINIMUM = 10000000;
	
	// Method to begin setup
	public void start() throws InterruptedException {
		
		camera = new Camera();
		camera.setPosition(0f, 2f, 6f);
		camera.setTarget(0f, 0f, 0f);
		camera.setUp(0f, 1f, 0f);
		
		hero = new Hero();
		hero.setPositionArray(0f, 0f, 0f);
		
		init();
		timer = new Timer(); //The Timer constructor establishes an origin time.
		
		/* isCloseRequested comes in the form of the X on a window being pushed,
		   a CTRL-C keystroke, etc. Continue updating until this happens. update()
		   swaps the buffers.
		 */
		
		while (!Display.isCloseRequested()) {
			
			frameDelta = timer.getNanoDelta(); //This value will get passed to all the updates that are time dependent.
			if(frameDelta < FRAME_LENGTH_MINIMUM) { //If very little time has passed since the last update, yield the cpu
				Thread.sleep(10);
			}else{
				pollMouse();
				pollKeyboard();
				updateMap();
				drawEntities();
				Display.update();
			}
		
		}
		
		Display.destroy();
	}
	
	// Method to initialize the display
	public void init() {
	
		//TODO: lighting, shading, fog, particles
		
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
				camera.getPosition(0), camera.getPosition(1), camera.getPosition(2),	// eye position
				camera.getTarget(0), camera.getTarget(1), camera.getTarget(2), 			// target to look at (origin)
				camera.getUp(0), camera.getUp(1), camera.getUp(2));						// specify up axis
		
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
	
	// Method to draw the entities within the world
	public void drawEntities() {
		hero.draw();
	}
	
	// Method to update the map
	public void updateMap() {
		
		//TODO: The map should have its own update method, and should manage the
		//display of its many world entities
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
			
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3d(1.0, 0.0, 0.0);
		
		for (int x = -5; x <= 5; x++) {
			GL11.glVertex3d((double)x + hero.getPosition(0), 0d + hero.getPosition(1), -5d + hero.getPosition(2));
			GL11.glVertex3d((double)x + hero.getPosition(0), 0d + hero.getPosition(1), 5d + hero.getPosition(2));
		}
		
		for (int z = -5; z <= 5; z++) {
			GL11.glVertex3d(5d + hero.getPosition(0), 0d + hero.getPosition(1), (double)z + hero.getPosition(2));
			GL11.glVertex3d(-5d + hero.getPosition(0), 0d + hero.getPosition(1), (double)z + hero.getPosition(2));
		}
		GL11.glEnd();
		
	}
	
	// Method to poll the mouse
	public void pollMouse() {
		
		//TODO: Implement camera angle changes with mouse wheel scroll
		
		// Left click
		if (Mouse.isButtonDown(0)) { 
			
			float mouse[] = getMousePosition(Mouse.getX(), Mouse.getY());
			System.out.println(mouse[0] + ", " + mouse[1] + ", " + mouse[2]);
		}
		
	}
	
	// Method to poll the keyboard
	public void pollKeyboard() {
		
		//TODO: Implement jumping with the spacebar, the ability to keep 
		//a key depressed to move
		
		// While there are key events on the event buffer
		while (Keyboard.next()) {
			//Key depressed
			if (Keyboard.getEventKeyState()) {
				switch (Keyboard.getEventKey()) {
					case Keyboard.KEY_W:
						hero.changePositionZ(0.1f);
						break;
					case Keyboard.KEY_A:
						hero.changePositionX(0.1f);
						break;
					case Keyboard.KEY_S:
						hero.changePositionZ(-0.1f);
						break;
					case Keyboard.KEY_D:
						hero.changePositionX(-0.1f);
						break;
					default:
						break;
				}
			}
			else {
				//Key released
			}
		}
		
	}
	
	// Convert mouse coordinates to world coordinates
	static public float[] getMousePosition(int mouseX, int mouseY) {

		int winX = mouseX;
		int winY = mouseY;
		
		//--------------------- Memory setup ----------------------------//
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		
		FloatBuffer positionNear = BufferUtils.createFloatBuffer(3);
		FloatBuffer positionFar = BufferUtils.createFloatBuffer(3);
		//---------------------------------------------------------------//
		
		//--------------------- Save the view matrices ------------------//
		GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview );
		GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection );
		GL11.glGetInteger( GL11.GL_VIEWPORT, viewport );
		//---------------------------------------------------------------//
		
		/*
		 * The following logic:
		 * gluUnProject takes the mouse coordinates, the three view matrices, and an empty
		 * byte buffer to store the 3D world coordinate of the mouse click. By providing 0
		 * for the mouse click's z value, the position calculated is on the near z clipping
		 * plane. Likewise, providing a 1 for this value puts the 3D point on the far z
		 * clipping plane. These 3D coordinates are stored in positionNear and positionFar.
		 */
		GLU.gluUnProject((float)winX, (float)winY, 0, modelview, projection, viewport, positionNear);
		GLU.gluUnProject((float)winX, (float)winY, 1, modelview, projection, viewport, positionFar);
		
		/*
		 * The following logic:
		 * We now effectively have the beginning and end points of the ray that is produced
		 * by a mouse click. The direction vector is determined like so:
		 * 				r = <P2x, P2y, P2z> - <P1x, P1y, P1z>
		 * This vector is used in the parametric form for the equation of a line:
		 * 				x = P1x + mrx
		 * 				y = P1y + mry
		 * 				z = P1z + mrz
		 * We know that the "ground level" is the plane in which y = 0. Therefore if we fix
		 * this value, we can solve for m (m = (y - P1y) / ry). The newly acquired m value
		 * allows us to easily solve for the x and y positions on the y = 0 plane.
		 */

		float pos[] = new float[3];
		float r[] = new float[3];
		float m;
		float fixedYPlane = 0;
		
		r[0] = positionFar.get(0) - positionNear.get(0);
		r[1] = positionFar.get(1) - positionNear.get(1);
		r[2] = positionFar.get(2) - positionNear.get(2);

		m = (fixedYPlane - positionNear.get(1)) / r[1];
		
		pos[0] = positionNear.get(0) + (m * r[0]);
		pos[1] = fixedYPlane;
		pos[2] = positionNear.get(2) + (m * r[2]);

		return pos;
	}
}