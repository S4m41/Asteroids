package main;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.entity.Astroid;
import game.entity.Entity;
import game.entity.Ship;
import game.world.EntityMap;
import game.world.World;

public class tests {

	@SuppressWarnings("unused")
	private static void entityMapTest() {
		Vector2D worldSize = new Vector2D(50, 50);
		EntityMap entityMap = new EntityMap(worldSize);
		System.out.println(entityMap.getContainedEntities().isEmpty());

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

		ArrayList<Drawable> testing = new ArrayList<Drawable>();
		World w = new World();
		Entity e = new Astroid(w);
		testing.add(e);// entity doesnt work. fixed //testing.add(e);
		e.move(1);
		jp.updatedrawlist(testing);
		jp.repaint();
		testing.remove(e);
		jp.setDoubleBuffered(true);
		test.setContentPane(jp);

		test.setVisible(true);
		test.requestFocus();
	}

	private static void updateTest() {
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setSize(new Dimension(500, 500));

		Screen jp = new Screen();
		jp.init(test.getSize());

		ArrayList<Drawable> testing = new ArrayList<Drawable>();
		World w = new World();
		Entity e = new Astroid(w);
		testing.add(e);// entity doesnt work. fixed //testing.add(e);
		jp.updatedrawlist(testing);

		jp.setDoubleBuffered(true);
		test.setContentPane(jp);

		test.setVisible(true);
		test.requestFocus();

		long last = System.nanoTime();

		int tFps = 60;
		long optimum = ((long) (1e9) / tFps);

		int fps = 0;
		double lastTick = 0;
		do {
			long now = System.nanoTime();// slowcall?
			long timeTaken = now - last;
			last = now;
			double delta = timeTaken / ((double) optimum);// XXX wrong

			test.repaint();
			e.move(1);
			lastTick += timeTaken;
			fps++;

			if (lastTick >= 1e9) {// TODO replace with observer or similar
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
		// create frame and set behaviour
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 500));

		// create pane and init
		Screen pane = new Screen();
		pane.init(frame.getSize());

		// list for drawing
		ArrayList<Drawable> testinglist = new ArrayList<Drawable>();

		// bind drawable list
		pane.updatedrawlist(testinglist);

		// doublebuffer
		pane.setDoubleBuffered(true); // bind pane to frame
		frame.setContentPane(pane);

		// create and bind entities
		World w = new World();
		Entity e = new Astroid(w);
		testinglist.add(e);// entity doesnt work. fixed
		Entity e1 = new Astroid(w);
		testinglist.add(e1);
		e1.setHeading(new Vector2D(0, 1));

		frame.setVisible(true);
		frame.requestFocus();

		// timer of last looptime. set to now to avoid spazzing on launch
		long last = System.nanoTime();

		// target fps
		int tFps = 60; // optimum fps
		long optimum = ((long) (1e9) / tFps); // current fps
		int fps = 0; // time since last fps tick
		double lastTick = 0;
		do {

			long now = System.nanoTime();// slow call? //time since last loop
			long timeTaken = now - last;
			last = now;
			double delta = timeTaken / ((double) optimum);// XXX wrong

			e.move(1);
			e1.move(1);
			frame.repaint(); // update fpsticker
			lastTick += timeTaken;
			fps++;

			if (lastTick >= 1e9) {// TODO replace with observer or similar
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

	private static void wraptest() {
		System.out.println(wrapAround(42, 50));
		System.out.println(wrapAround(-42, 50));
		System.out.println(wrapAround(50, 50));
		System.out.println(wrapAround(-50, 50));
		System.out.println(wrapAround(142, 50));
		System.out.println(wrapAround((float) -142.0, 50));
	}

	private static float wrapAround(float coordinate, float max) {
		coordinate %= max;
		return (coordinate < 0) ? coordinate + max : coordinate;
	}

	@SuppressWarnings("unused")
	private static void vectortest() {
		Vector2D v0 = new Vector2D(0, 0);
		Vector2D v1 = new Vector2D(1, 0);
		Vector2D v2 = new Vector2D(-1, 0);

		System.out.println(Vector2D.angle(v1, v2));
	}

	@SuppressWarnings("unused")
	private static void entitymoveTest() {
		World w = new World();
		Entity e = new Astroid(w);// temp add entity()
		e.setPosition(new Vector2D(1, 0));
		e.setHeading(new Vector2D(0, 1));
		e.setSpeed(142.0);
		e.move(1);
		e.wrap(new Vector2D(50, 50));
		System.out.println(e.getPosition());
	}

	private static void simpleCollisionTest() {
		World w = new World();
		Ship e1 = new Ship(w);
		Astroid e2 = new Astroid(w);
		e1.setSpeed(0);
		e2.setSpeed(0);

		e1.setPosition(new Vector2D(0, 0));
		e2.setPosition(new Vector2D(21, 21));

		e1.setSize(10);
		e2.setSize(10);

		double radsq = (square(e1.getSize() + e2.getSize()));
		System.out.println("radsq:" + radsq);
		double distsq = square(e1.getPosition().distance(e2.getPosition()));
		System.out.println("distsq:" + distsq);
		if (radsq >= distsq) {
			System.out.println("Collision");
		}
	}

	private static double square(double d) {
		return d * d;
	}
}
