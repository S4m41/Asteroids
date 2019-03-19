package main;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.Game;
import game.entity.Entity;
import game.world.EntityMapVVE;
import game.world.EntityNode;
import game.world.World;

//main enty point of program
public class entry {
	public static void main(String args[]) {

		gameLaunch();

	}


	@SuppressWarnings("unused")
	private static void gameLaunch() {
		Game g = new Game();
		g.run();
	}

}
