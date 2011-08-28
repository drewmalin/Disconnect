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
	
	private double playerPosX = 0;
	private double playerPosY = 0;
	private double playerPosZ = 0;
	
	// Method to begin setup
	public void start() {
		
		camera = new Camera();
		camera.setPosition(0f, 2f, 6f);
		camera.setTarget(0f, 0f, 0f);
		camera.setUp(0f, 1f, 0f);
		
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
			System.out.println(Mouse.getX() + ", " + Mouse.getY());
			
			float mouse[] = getMousePosition(Mouse.getX(), Mouse.getY());
			System.out.println(mouse[0] + ", " + mouse[1] + ", " + mouse[2]);
		}
		
	}
	
	// Method to poll the keyboard
	public void pollKeyboard() {
		
		// While there are key events on the event buffer
		while (Keyboard.next()) {
			//Key depressed
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
			else {
				//Key released
			}
		}
		
	}
	
	// Method to move the Hero
	public void updateHeroLocation() {
		
	}
	
	// Convert mouse coordinates to world coordinates
	static public float[] getMousePosition(int mouseX, int mouseY) {

		int winX = mouseX;
		int winY = mouseY;
		
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		
		FloatBuffer positionNear = BufferUtils.createFloatBuffer(3);
		FloatBuffer positionFar = BufferUtils.createFloatBuffer(3);
		
		GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview );
		GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection );
		GL11.glGetInteger( GL11.GL_VIEWPORT, viewport );
		
		GLU.gluUnProject((float)winX, (float)winY, 0, modelview, projection, viewport, positionNear);
		GLU.gluUnProject((float)winX, (float)winY, 1, modelview, projection, viewport, positionFar);
		
		//Fix Y plane at 0
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