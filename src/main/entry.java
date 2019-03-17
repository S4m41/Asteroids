package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;
import game.entity.Entity;

//main enty point of program
public class entry {
	public static void main(String args[]) {
		Entity e = new Entity();
	}
	private static void jframetest() {
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setSize(new Dimension(500, 500));
		
		Screen jp = new Screen();
		jp.init(test.getSize());
		
		ArrayList<Drawable> testing= new ArrayList<Drawable>();
		testing.add(new Entity());//entity doesnt work. 
		
		jp.updatedrawlist(testing);
		
		jp.setDoubleBuffered(true);
		test.setContentPane(jp);

		test.setVisible(true);
		test.requestFocus();
				
	}
}
