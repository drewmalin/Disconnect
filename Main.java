/*
 *  Main.java
 *  Project Disconnect
 *
 *  Created by Drew Malin on 8/27/2011.
 *  Copyright 2011 Drew Malin. All rights reserved.
 *
 */
public class Main {
	public static void main (String[] args) throws InterruptedException {
		//TODO: splash screen/main menu need to look into lwjgl capabilities for this
		GameWorld gw = new GameWorld();
		
		gw.loadMap();
		gw.loadWorld();
		gw.start();
		
	}
}
