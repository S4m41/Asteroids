package main;

import game.Game;

//main enty point of program
public class Entry {
	public static void main(String args[]) {
		
		gameLaunch();

	}

	@SuppressWarnings("unused")
	private static void gameLaunch() {
		Game g = new Game();
		g.run();
	}
	@SuppressWarnings("unused")
	private static void testall() {
		Tests.stringTraceTest();
		Tests.advancedCollisionTest();
		Tests.drawlistTest();
		Tests.EIDTest();
		Tests.entityMapTest();
		Tests.entitymoveTest();
		Tests.jframetest();
		Tests.qtMovementTest();
		Tests.simpleCollisionTest();
		Tests.stringTraceTest();
		Tests.updateAdvancedTest();
		Tests.updateTest();
		Tests.vectortest();
		Tests.wraptest();
	}
}
