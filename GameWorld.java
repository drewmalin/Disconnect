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
	
	private int width = 1024;
	private int height = 768;
	private float diagonal = .70710678f;
	
	private float zNear = 0.1f;
	private float zFar = 1000f;
	private float frustAngle = 60f;
	
	private Camera camera;
	private Hero hero;
	private Map map;
	float mouse[];
	
	private Timer timer;
	private long frameDelta;
	private long FRAME_LENGTH_MINIMUM = 10000000;
	
	// Method to begin setup
	public void start() throws InterruptedException {
		
		camera = new Camera();
		camera.setPosition(0f, 7f, 7f);
		camera.setTarget(0f, 0f, 0f);
		camera.setUp(0f, 1f, 0f);
		
		mouse = new float[3];
		
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
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		GLU.gluPerspective(frustAngle, (float)width/(float)height, zNear, zFar);
		GLU.gluLookAt(
				camera.getPosition(0),	camera.getPosition(1), 	camera.getPosition(2),
				camera.getTarget(0), 	camera.getTarget(1), 	camera.getTarget(2),
				camera.getUp(0), 		camera.getUp(1), 		camera.getUp(2));
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	
	public void loadMap() {	
		map = new Map("GridWorld.txt");	
	}
	
	
	public void loadWorld() {
		//TODO: Read in the objects that make up the game world
	}	
	

	public void drawEntities() {
		hero.draw();
		
		GL11.glColor3f(0f, 0f, 1f);
		GL11.glBegin(GL11.GL_QUADS);	//Back
			GL11.glVertex3d(  1,  1, 0d );
			GL11.glVertex3d( -1,  1, 0d );
			GL11.glVertex3d( -1, -1, 0d );
			GL11.glVertex3d(  1, -1, 0d );
		GL11.glEnd();
	}
	
	
	public void updateMap() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glPushMatrix();
			GL11.glLoadIdentity();
			GL11.glTranslatef(hero.getPosition(0), hero.getPosition(1), hero.getPosition(2));
			map.draw();
		GL11.glPopMatrix();
	}
	
	
	public void pollMouse() {
		//TODO: Implement camera angle changes with mouse wheel scroll
		
		//Updated regardless of mouse click
		mouse = getMousePosition(Mouse.getX(), Mouse.getY());
		hero.setTargetVect(mouse[0], mouse[1], mouse[2]);
		
		if (Mouse.isButtonDown(0)) { 		// Left click
			mouse = getMousePosition(Mouse.getX(), Mouse.getY());
			System.out.println(mouse[0] + ", " + mouse[1] + ", " + mouse[2]);
		//	hero.setTargetVect(mouse[0], mouse[1], mouse[2]);
		}
	}
	

	public void pollKeyboard() {
		
		//TODO: Implement jumping with the spacebar
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_A)) {
			hero.changePositionZ(hero.movementRate * diagonal);
			hero.changePositionX(hero.movementRate * diagonal);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
			hero.changePositionZ(hero.movementRate * diagonal);
			hero.changePositionX(-hero.movementRate * diagonal);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_A)) {
			hero.changePositionZ(-hero.movementRate * diagonal);
			hero.changePositionX(hero.movementRate * diagonal);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
			hero.changePositionZ(-hero.movementRate * diagonal);
			hero.changePositionX(-hero.movementRate * diagonal);
		}
		else {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				hero.changePositionZ(hero.movementRate);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				hero.changePositionX(hero.movementRate);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				hero.changePositionZ(-hero.movementRate);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				hero.changePositionX(-hero.movementRate);
			}
		}
	}
	

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