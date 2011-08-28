/*
 *  Camera.java
 *  Project Disconnect
 *
 *  Created by Drew Malin on 8/27/2011.
 *  Copyright 2011 Drew Malin. All rights reserved.
 *
 */
public class Camera {
	
	private float position[];
	private float target[];
	private float angle[];
	private float up[];
	
	public Camera() {
		position = new float[3];
		target = new float[3];
		angle = new float[3];
		up = new float[3];
	}
	
	public void setPosition(float x, float y, float z) {
		position[0] = x;
		position[1] = y;
		position[2] = z;
	}
	
	public void setTarget(float x, float y, float z) {
		target[0] = x;
		target[1] = y;
		target[2] = z;
	}
	
	public void setAngle(float x, float y, float z) {
		angle[0] = x;
		angle[1] = y;
		angle[2] = z;
	}

	public void setUp(float x, float y, float z) {
		up[0] = x;
		up[1] = y;
		up[2] = z;
	}
	
	public float getPosition(int index) {
		assert ((index >= 0) && index <3);
		return position[index];
	}
	
	public float getTarget(int index) {
		assert ((index >= 0) && index <3);
		return target[index];
	}
	
	public float getAngle(int index) {
		assert ((index >= 0) && index <3);
		return angle[index];
	}
	
	public float getUp(int index) {
		assert ((index >= 0) && index <3);
		return up[index];
	}
}