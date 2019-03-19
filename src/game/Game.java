package game;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;

import game.entity.Entity;
import game.world.World;
import main.Screen;

public class Game {
	private GameState currentState = GameState.unidentified;// bad statename
	
	private World currentWorld;
	private JFrame frame;
	private Screen screenPane;
	private int tFps = 60;
	private boolean timestepisfixed = false;
	private long fixedtimestep = 500;

	public Game() {
		setState(GameState.starting);
		init();
	}

	// do initial setup,, create own exception, TODO rename
	private void init() throws IllegalStateException {
		initWindow();
		frame.addKeyListener(new main.KeyListener(this));
		this.setCurrentWorld(new World());

		setState(GameState.running);
	}

	// temp block for window init move to own function
	private void initWindow() throws IllegalStateException {
		// do stuffs. init jframe here. perhaps own thread?
		if (getState() != GameState.starting)
			throw new IllegalStateException();

		// init the new frame and set size and closing behaviour
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 500));// temp do this in config file

		// create instance of child of jpane. use to paint to the frame
		screenPane = new Screen();
		screenPane.init(frame.getSize());

		// native doublebuffering. trustworthy?
		screenPane.setDoubleBuffered(true);
		// bind pane to frame
		frame.setContentPane(screenPane);

		frame.setVisible(true);
		frame.requestFocus();

	}

	// main loop of the program
	public void run() {
		if (getState() != GameState.running)
			throw new IllegalStateException();
		long last = System.nanoTime();

		
		long optimum = ((long) (1e9) / tFps);

		int fps = 0;
		double lastTick = 0;
		do {
			long now = System.nanoTime();// slow call?
			long timeTaken = now - last;
			last = now;
			double delta = timeTaken / ((double) optimum);

			// update the system
			update(delta);
			// repaint all
			screenPane.repaint();

			lastTick += timeTaken;
			fps++;

			if (lastTick >= 1e9) {// TODO replace with observer or similar
				System.out.println("(FPS: " + fps + ")");
				lastTick = 0;
				fps = 0;
			}
			long sleeptime = (long) ((last - System.nanoTime() + optimum) / 1e6);
			if(sleeptime<0) {
				sleeptime = 0;
			}
			if (timestepisfixed) {
				sleeptime = fixedtimestep;
			}
			try {
				Thread.sleep(sleeptime);// ?? replace with wait?
			} catch (InterruptedException e) {
			}

		} while (isRunning());

	}

	private void update(double delta) {
		if (getState() == GameState.running) {
			getCurrentWorld().setKeyInput(direction);
			getCurrentWorld().update(delta);			
		}
			
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

	private World getCurrentWorld() {
		return currentWorld;
	}

	private void setCurrentWorld(World braveNewWorld) {
		// add the new world to be drawn
		screenPane.addDrawable(braveNewWorld);// FIX wierd dataflow. the wold doesnt give a list of drawables
		if (currentWorld != null)
			// flag the old world to be discarded
			currentWorld.setActive(false);

		// replace wolds
		this.currentWorld = braveNewWorld;
		currentWorld.setActive(true);
	}

	private boolean[] direction = { false, false, false, false, false }; // not thread safe //todo create enum

	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_W:
			direction[0] = true;
			break;
		case KeyEvent.VK_A:
			direction[1] = true;

			break;
		case KeyEvent.VK_S:
			direction[2] = true;

			break;
		case KeyEvent.VK_D:
			direction[3] = true;

			break;
		case KeyEvent.VK_SPACE:
			direction[4] = true;

			break;
		default:
			break;
		}

	}

	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_W:
			direction[0] = false;
			break;
		case KeyEvent.VK_A:
			direction[1] = false;

			break;
		case KeyEvent.VK_S:
			direction[2] = false;

			break;
		case KeyEvent.VK_D:
			direction[3] = false;

			break;
		case KeyEvent.VK_SPACE:
			direction[4] = false;
			break;
		default:
			break;
		}
	}

}
