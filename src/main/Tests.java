package main;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.entity.Astroid;
import game.entity.Bullet;
import game.entity.Entity;
import game.entity.Ship;
import game.world.EntityMap;
import game.world.EntityMap.QuadtreeExeption;
import game.world.World;

public class Tests {

	@SuppressWarnings("unused")
	public static void entityMapTest() {
		Vector2D worldSize = new Vector2D(50, 50);
		EntityMap entityMap = new EntityMap(worldSize);
		System.out.println(entityMap.getContainedEntities().isEmpty());

	}

	@SuppressWarnings("unused")
	public static void jframetest() {
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
	public static void drawlistTest() {
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

	public static void updateTest() {
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
			@SuppressWarnings("unused")
			double delta = timeTaken / ((double) optimum);// XXX wrong

			test.repaint();
			e.move(1);
			lastTick += timeTaken;
			fps++;

			if (lastTick >= 1e9) {// TODO replace with observer or similar
				System.out.println("(FPS: " + fps + ")");
				lastTick = 0;
				fps = 0;
				test.setVisible(false);

			}
			long sleeptime = (long) ((last - System.nanoTime() + optimum) / 1e6);
			try {
				Thread.sleep(sleeptime);
			} catch (InterruptedException ierr) {
			}
		} while (test.isVisible());
	}

	@SuppressWarnings("unused")
	public static void updateAdvancedTest() {// change to use current game
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
				frame.setVisible(false);
			}
			long sleeptime = (long) ((last - System.nanoTime() + optimum) / 1e6);
			try {
				Thread.sleep(sleeptime);
			} catch (InterruptedException ierr) {
			}

		} while (frame.isVisible());
	}

	public static void wraptest() {
		System.out.println(Entity.wrapAround(42, 50));
		System.out.println(Entity.wrapAround(-42, 50));
		System.out.println(Entity.wrapAround(50, 50));
		System.out.println(Entity.wrapAround(-50, 50));
		System.out.println(Entity.wrapAround(142, 50));
		System.out.println(Entity.wrapAround((float) -142.0, 50));
	}

	public static float wrapAround(float coordinate, float max) {
		coordinate %= max;
		return (coordinate < 0) ? coordinate + max : coordinate;
	}

	@SuppressWarnings("unused")
	public static void vectortest() {
		Vector2D v0 = new Vector2D(0, 0);
		Vector2D v1 = new Vector2D(1, 0);
		Vector2D v2 = new Vector2D(-1, 0);

		System.out.println(Vector2D.angle(v1, v2));
	}

	@SuppressWarnings("unused")
	public static void entitymoveTest() {
		World w = new World();
		Entity e = new Astroid(w);// temp add entity()
		e.setPosition(new Vector2D(1, 0));
		e.setHeading(new Vector2D(0, 1));
		e.setSpeed(142.0);
		e.move(1);
		e.wrap(new Vector2D(50, 50));
		System.out.println(e.getPosition());
	}

	@SuppressWarnings("unused")
	public static void simpleCollisionTest() {
		World w = new World();
		Ship e1 = new Ship(w);
		Astroid e2 = new Astroid(w);
		e1.setSpeed(0);
		e2.setSpeed(0);

		e1.setPosition(new Vector2D(0, 0));
		e2.setPosition(new Vector2D(21, 21));

		e1.setSize(10);
		e2.setSize(10);

		System.out.println(e1.toString() + "\t" + e2.toString());
		double radsq = ((e1.getSize() + e2.getSize()));
		System.out.println("radadd:" + radsq + "\t\t\t\t radsq:" + square(radsq));
		radsq = square(radsq);
		double distsq = (e1.getPosition().distance(e2.getPosition()));
		System.out.println("distadd:" + distsq + "\t\t  radsq:" + square(distsq));
		distsq = square(distsq);
		if (radsq >= distsq) {
			System.out.println("Collision");
		}
	}

	public static void advancedCollisionTest() {
		World w = new World();
		Ship e1 = new Ship(w);
		Astroid e2 = new Astroid(w);
		e1.setSpeed(0);
		e2.setSpeed(0);

		e1.setPosition(new Vector2D(0, 0));
		e2.setPosition(new Vector2D(21, 21));

		e1.setSize(10);
		e2.setSize(10);

		w.add(e1);
		w.add(e2);
		System.out.println(w.toString());

		ArrayList<Entity> poss = w.doescollide(e1.getPosition());
		for (Entity e3 : poss) {

		}
		System.out.println(poss.toString());
		e1.colidedWith(e2);
		// TODO XXX
	}

	@SuppressWarnings("unused")
	public static void qtMovementTest() {
		World w = new World();
		Ship e1 = new Ship(w);
		e1.setSpeed(50);

		e1.setPosition(new Vector2D(0, 0));
		e1.setHeading(new Vector2D(10, 0.1));
		e1.setSize(5);
		w.init();
		w.add(e1);
		System.out.println(w.toString());
		System.out.println(e1.toString());
		System.out.println("-----------------------------------------");

		String listw = "";
		String listn = "";
		PrintWriter outw = null;
		PrintWriter outn = null;
		try {
			outw = new PrintWriter("listw.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		try {
			outn = new PrintWriter("listn.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		// int wierd = 0; // 6250018/50000000
		for (int i = 0; i < 5000 * 5; i++) {
			e1.move(1);
			Vector2D pos = e1.getPosition();
			int x = (int) pos.getX();
			int y = (int) pos.getY();

			ArrayList<Entity> cand = w.getPos(e1);

			for (Entity e : cand) {
				if (e == e1 && e instanceof Ship) {
					Vector2D pos1 = e.getPosition();
					int x1 = (int) pos1.getX();
					int y1 = (int) pos1.getY();

					listn += x1 + "," + y1 + "\n";
				}
			}

			String l = "";
			if (i % 5000 == 0) {
				if (i % 500000 == 0) {
					l = "\n§";
				} else {
					l = ".";
				}
			}
			System.out.print(l);
			// System.out.println(s);
			// System.out.println(e1.toString());
			// System.out.println("-----------------------------------------");
		}
		System.out.println("§!");
		outw.print(listw);
		outn.print(listn);
		outw.close();
		outn.close();
		// System.out.println("\n"+listw);
		// System.out.println(wierd);

	}

	public static void stringTraceTest() {
		// double delta = 1;
		World w = new World();
		Ship e1 = new Ship(w);
		Astroid e2 = new Astroid(w);
		e1.setSpeed(0);
		e2.setSpeed(0);

		e1.setPosition(new Vector2D(0, 0));
		e2.setPosition(new Vector2D(21, 21));

		e1.setSize(10);
		e2.setSize(10);

		w.add(e1);
		w.add(e2);
		w.init();
		String s = w.toString();
		System.out.println("" + s);

	}

	public static void EIDTest() {
		World w = new World();
		for (int i = 0; i < 10; i++) {
			System.out.println(new Astroid(w).getID() + "\n");
		}
	}

	private static double square(double d) {
		return d * d;
	}

	@SuppressWarnings("unused")
	public static void qtDeletionTest() {
		EntityMap em = new EntityMap();
		World w = new World();

		Ship sheep = new Ship(w);
		Bullet bang = new Bullet(w);
		Astroid rocks = new Astroid(w);

		populate_entitymap(em, w);
		try {
			em.add(sheep);
			em.add(rocks);
			em.add(bang);

		} catch (QuadtreeExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		populate_entitymap(em, w);

		//System.out.println(sheep + em.printStringTrace(sheep));
		System.out.println(em.remove(sheep));
		System.out.println(sheep +""+ em.queryEntity(sheep));
		System.out.println(em.remove(rocks));
		System.out.println(rocks +""+ em.queryEntity(rocks));
		System.out.println(em.remove(bang));
		System.out.println(bang +""+ em.queryEntity(bang));
		System.out.println("done");

	}

	@SuppressWarnings("unused")
	static void populate_entitymap(EntityMap em, World w) {
		for (int i = 0; i < (int) 5e4; i++) {
			Entity e;
			if (i % 3 == 2 && 1 < 0.5) {
				e = new Bullet(w);
			} else if (i % 3 == 1 && 1 < 0.2) {
				e = new Ship(w);
			} else {
				e = new Astroid(w);
			}
			e.setPosition(new Vector2D(i % 500, (i / 2) % 500));
			try {
				em.add(e);
			} catch (QuadtreeExeption e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
