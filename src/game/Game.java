package game;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;

import game.entity.Entity;
import game.world.World;

public class Game{
	
	private World currentWorld;
	// the list of all entities
	private ArrayList<Entity> entityList;
	private JFrame screen;
	
	public GameState currentState = GameState.unidentified;
	

	public Game() {

		setState(GameState.starting);
		this.currentWorld = new World();
		this.entityList = new ArrayList<Entity>();
	}

	// do initial setup,, create own exception
	public void start() throws IllegalStateException {
		// do stuffs. init jframe here. perhaps own thread?
		if (getState() != GameState.starting)
			throw new IllegalStateException();
		setState(GameState.running);
		this.run();
	}
	// main loop of the program
	private void run() {
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
			
			update(delta);
			draw();
			render();
			
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
			} catch (InterruptedException e) {
			}

		} while (isRunning());

	}

	private void update(double delta) {
		// update all entities. move to separate function?
		for (Entity e : entityList) {
			e.move();
			if (!e.isAlive()) {
				// TODO remove from list
			}

			if (e instanceof Entity) {//TODO visitor pattern? do nt le this start. leaks ahoy
				entityList.add(new Entity());
			}
		}

	}

	private void draw() {
		// TODO Auto-generated method stub draw to graphics object

	}
	public void paint( Graphics g){        g.drawString( "Hej, mr Universum!", 100,100);    }
	private void render() {
		// TODO Auto-generated method stub

	}

	private boolean isRunning() {
		return getState() == GameState.running;
	}

	private GameState getState() {
		return currentState;
	}

	private void setState(GameState state) {
		this.currentState = state;

	}
}
