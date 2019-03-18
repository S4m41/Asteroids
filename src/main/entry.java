package main;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.Game;
import game.entity.Entity;
import game.entity.EntityNode;
import game.world.EntityMap;

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
	@SuppressWarnings("unused")
	private static void entityMapTest() {
		Dimension worldSize = new Dimension(50,50);
		EntityMap entityMap = new EntityMap(worldSize);
		System.out.println(entityMap.get(0).get(0).isEmpty());
		
		
	}
	@SuppressWarnings("unused")
	private static void jframetest() {
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setSize(new Dimension(500, 500));
		
		Screen jp = new Screen();
		jp.init(test.getSize());
		
		jp.setDoubleBuffered(true);
		test.setContentPane(jp);

		test.setVisible(true);
		test.requestFocus();
				
	}
	@SuppressWarnings("unused")
	private static void drawlistTest() {
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setSize(new Dimension(500, 500));
		
		Screen jp = new Screen();
		jp.init(test.getSize());
		
		ArrayList<Drawable> testing= new ArrayList<Drawable>();
		Entity e = new Entity();
		testing.add(e);//entity doesnt work. fixed
		//testing.add(e);
		e.move(1);
		jp.updatedrawlist(testing);
		jp.repaint();
		testing.remove(e);
		jp.setDoubleBuffered(true);
		test.setContentPane(jp);

		test.setVisible(true);
		test.requestFocus();
	}
	@SuppressWarnings("unused")
	private static void updateTest() {
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setSize(new Dimension(500, 500));
		
		Screen jp = new Screen();
		jp.init(test.getSize());
		
		ArrayList<Drawable> testing= new ArrayList<Drawable>();
		Entity e = new Entity();
		testing.add(e);//entity doesnt work. fixed
		//testing.add(e);
		jp.updatedrawlist(testing);
		
		jp.setDoubleBuffered(true);
		test.setContentPane(jp);

		test.setVisible(true);
		test.requestFocus();
		
		long last = System.nanoTime();
		
		int tFps = 60;
		long optimum =  ((long)(1e9) / tFps);
		
		int fps = 0;
		double lastTick = 0;
		do {
			long now = System.nanoTime();// slow call?
			long timeTaken = now - last;
			last = now;
			double delta = timeTaken / ((double) optimum);//XXX wrong
			
			
			test.repaint();
			e.move(1);
			lastTick += timeTaken;
			fps++;

			if (lastTick >= 1e9) {//TODO replace with observer or similar
				System.out.println("(FPS: " + fps + ")");
				lastTick = 0;
				fps = 0;
				
			}
			long sleeptime = (long) ((last - System.nanoTime() + optimum) / 1e6);
			try {
				Thread.sleep(sleeptime);
			} catch (InterruptedException ierr) {
			}

		} while (test.isVisible());
	}
	@SuppressWarnings("unused")
	private static void updateAdvancedTest() {
		//create frame and set behaviour
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 500));
		
		//create pane and init
		Screen pane = new Screen();
		pane.init(frame.getSize());
		
		//list for drawing
		ArrayList<Drawable> testinglist= new ArrayList<Drawable>();
		
		//bind drawable list
		pane.updatedrawlist(testinglist);
		
		//doublebuffer
		pane.setDoubleBuffered(true);
		//bind pane to frame
		frame.setContentPane(pane);
		
		//create and bind entities
		Entity e = new Entity();
		testinglist.add(e);//entity doesnt work. fixed
		Entity e1 = new Entity();
		testinglist.add(e1);
		e1.setHeading(new Vector2D(0,1));
		
		
		frame.setVisible(true);
		frame.requestFocus();
		
		//timer of last looptime. set to now to avoid spazzing on launch
		long last = System.nanoTime();
		
		//target fps
		int tFps = 60;
		//optimum fps
		long optimum =  ((long)(1e9) / tFps);
		//current fps
		int fps = 0;
		//time since last fps tick
		double lastTick = 0;
		do {
			
			long now = System.nanoTime();// slow call?
			//time since last loop
			long timeTaken = now - last;
			last = now;
			double delta = timeTaken / ((double) optimum);//XXX wrong
			
			
			
			e.move(1);
			e1.move(1);
			frame.repaint();
			//update fpsticker
			lastTick += timeTaken;
			fps++;

			if (lastTick >= 1e9) {//TODO replace with observer or similar
				System.out.println("(FPS: " + fps + ")");
				lastTick = 0;
				fps = 0;
				
			}
			long sleeptime = (long) ((last - System.nanoTime() + optimum) / 1e6);
			try {
				Thread.sleep(sleeptime);
			} catch (InterruptedException ierr) {
			}

		} while (frame.isVisible());
	}
}
