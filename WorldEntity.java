/*
 *  WorldEntity.java
 *  Project Disconnect
 *
 *  Created by Drew Malin on 8/27/2011.
 *  Copyright 2011 Drew Malin. All rights reserved.
 *
 */
public abstract class WorldEntity {
	public int id;
	public float position[];
	public String name;
	public int colorID[];
	
	public WorldEntity() {
		position = new float[3];
		colorID = new int[3];
	}
	
	public abstract void draw();

	public void setName(String name) {
		this.name = name;
	}
	
	public void setColorID(int R, int G, int B) {
		colorID[0] = R;
		colorID[1] = G;
		colorID[2] = B;
	}
	
	public void setPositionArray(float x, float y, float z) {
		position[0] = x;
		position[1] = y;
		position[2] = z;
	}
	
	public float getPosition(int index) {
		assert ((index >= 0) && index <3);
		return position[index];	
	}
	
	public void changePositionX(float delta) {
		position[0] += delta;
	}
	
	public void changePositionY(float delta) {
		position[1] += delta;
	}
	
	public void changePositionZ(float delta) {
		position[2] += delta;
	}
	
	public String getName() {
		return name;
	}
	
}