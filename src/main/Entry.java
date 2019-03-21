package main;

import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.Game;
import game.entity.Astroid;
import game.entity.Bullet;
import game.entity.Entity;
import game.entity.Ship;
import game.world.EntityMap;
import game.world.EntityMap.QuadtreeExeption;
import game.world.World;

//main enty point of program
public class Entry {
	public static void main(String args[]) {
		//Tests.qtAdd();
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
