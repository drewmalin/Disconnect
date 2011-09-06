/*
 *  Character.java
 *  Project Disconnect
 *
 *  Created by Drew Malin on 8/31/2011.
 *  Copyright 2011 Drew Malin. All rights reserved.
 *
 */
import java.util.ArrayList;
import java.lang.Math;

public abstract class Character extends WorldEntity {
	
	ArrayList<Attribute> attributes;
	
	public float currentDirectionVect[];
	public float targetDirectionVect[];
	public float movementRate;
	
	public Character() {
		attributes = new ArrayList<Attribute>();
		
		movementRate = 0.025f;
		currentDirectionVect = new float[3];
		targetDirectionVect = new float[3];
		
		currentDirectionVect[0] = 0;
		currentDirectionVect[1] = 0;
		currentDirectionVect[2] = -1;	
		
		targetDirectionVect[0] = 0;
		targetDirectionVect[1] = 0;
		targetDirectionVect[2] = -1;
	}
	
	public void setTargetVect(float x, float y, float z) {
	
		float l = (float) Math.pow((x*x) + (y*y) + (z*z), .5);
		targetDirectionVect[0] = x / l;
		targetDirectionVect[1] = y / l;
		targetDirectionVect[2] = z / l;	
	}
	
	public void unitDirectionVect(float x, float y, float z) {
		float l = (float) Math.pow((x*x) + (y*y) + (z*z), .5);
		currentDirectionVect[0] = x / l;
		currentDirectionVect[1] = y / l;
		currentDirectionVect[2] = z / l;	
	}
	
	public void calculateRotation() {
		
		float remainingRads, remainingAngle;
		
		// If we are already looking in the direction of the click, we're done,
		// the remaining angle to rotate through is 0.
		if (currentDirectionVect[0] == targetDirectionVect[0] &&
		    currentDirectionVect[2] == targetDirectionVect[2]) {
			remainingAngle = 0;
		}
		else {
			//Calculate the remaining angle to rotate through (in radians) then
			//translate into degrees.
			remainingRads = calculateAngleDiscrepency();
			remainingAngle = remainingRads * (180 / (float) Math.PI);

			//Adjust angle, use the cross product to determine the correct direction
			//of rotation. If the cross product results with a positive y value, then
			//the fastest way to rotate is counterclockwise. If the y value is negative, 
			//the fastest direction of rotation is clockwise.
			if (!cross(currentDirectionVect, targetDirectionVect)) {
				remainingAngle *= -1;
			}
		}
		
		//Update the character's necessary angle of rotation for drawing later
		changeRotationY(remainingAngle);
		
		//Set the current direction vector to the target vector.
		currentDirectionVect[0] = targetDirectionVect[0];
		currentDirectionVect[2] = targetDirectionVect[2];

	}
	
	/*
	 * Method to calculate the angle created by the clicked map location,
	 * the hero's position, and a point on the current view (direction)
	 * vector. Following the law of cosines, this angle, g, is:
	 * 	g = arccos((a^2 + b^2 - c^2) / 2ab)
	 * where a is the distance from the hero to the clicked point, b is the
	 * distance from the hero to the point on the direction vector,
	 * and c is the distance between a and b (pythagorean theorem). 
	 * 
	 */
	
	//TODO USE DOT PRODUCT!!!! 
	// a dot b = a1*b1 + a2*b2 + a3*b3
	// a dot b = ||a|| * ||b|| cos theta
	// theta = arccos((a1*b1 + a2*b2 + a3*b3)/(||a|| * ||b||))
	private float calculateAngleDiscrepency() {
		
		// Calculate dot product currentDirection dot targetDirection
		float aDotB = (currentDirectionVect[0] * targetDirectionVect[0]) +
					  (currentDirectionVect[2] * targetDirectionVect[2]);
		
		// Solve for theta (since these vectors are unit vectors, we don't 
		// need to solve for, then divide by, their lengths)
		
		float theta = (float)Math.acos(aDotB);
		
		// Was getting dot product value greater than 1 in some specific circumstances
		// (value was something like 1.0000000001) which is outside of the domain for 
		// arc cosine. If this ever occurs, force theta to be 0 (as if aDotB were 1)
		if (aDotB > 1)
			theta = 0;
		return theta;
	}
	
	//Cross product. Thank you Wikipedia.
	private boolean cross(float[] a, float[] b) {

		float yVal = a[2]*b[0] - a[0]*b[2];
		
		return yVal >= 0 ? true : false;
	}
}