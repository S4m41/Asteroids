package main;

import game.Game;

//main enty point of program
public class Entry {
	public static void main(String args[]) {
		//Tests.stringTraceTest();
		Tests.advancedCollisionTest();
		
		//gameLaunch();

	}

	@SuppressWarnings("unused")
	private static void gameLaunch() {
		Game g = new Game();
		g.run();
	}

}
