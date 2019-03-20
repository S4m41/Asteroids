package main;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.Game;
import game.entity.Astroid;
import game.entity.Entity;
import game.entity.Ship;
import game.world.EntityMapVVE;
import game.world.EntityNode;
import game.world.World;

//main enty point of program
public class entry {
	public static void main(String args[]) {
		World w = new World();
		Ship e1 = new Ship(w);
		Astroid e2 = new Astroid(w);
		e1.setSpeed(0);
		e2.setSpeed(0);
		
		e1.setPosition(new Vector2D(0, 0));
		e2.setPosition(new Vector2D(21, 21));
		
		e1.setSize(10);
		e2.setSize(10);
		
		double radsq = (square(e1.getSize()+e2.getSize()));
		System.out.println("radsq:" + radsq);
		double distsq = square(e1.getPosition().distance(e2.getPosition()));
		System.out.println("distsq:" + distsq);
		if(radsq >= distsq) {
			System.out.println("Collision");
		}
		
		//gameLaunch();

	}
	private static double square(double d) {
		return d*d;
	}


	@SuppressWarnings("unused")
	private static void gameLaunch() {
		Game g = new Game();
		g.run();
	}

}
