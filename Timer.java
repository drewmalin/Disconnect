/*
 *  Timer.java
 *  Project Disconnect
 *
 *  Created by Ben Roye on 8/29/2011.
 *  Copyright 2011 Ben Roye. All rights reserved.
 *
 */
public class Timer
{
	private long lastCheckTime;
	
	public Timer(){
		lastCheckTime = System.nanoTime();
	}
	
	public long getNanoDelta(){
		long temp = System.nanoTime();
		long delta = temp - lastCheckTime;
		lastCheckTime = temp;
		return delta;
	}
}
