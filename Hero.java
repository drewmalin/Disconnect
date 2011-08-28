/*
 *  Hero.java
 *  Project Disconnect
 *
 *  Created by Drew Malin on 8/27/2011.
 *  Copyright 2011 Drew Malin. All rights reserved.
 *
 */
import org.lwjgl.opengl.GL11;

public class Hero extends WorldEntity {
	
	public Hero() {
		super();
		//TODO Link attributes, skills, inventory, animations, textures
	}

	@Override
	public void draw() {
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
}