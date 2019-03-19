package game;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

import game.entity.Entity;
import game.world.World;
import main.Screen;

public class Game{
	
	private World currentWorld;
	private JFrame frame;
	private Screen screenPane;
	
	public GameState currentState = GameState.unidentified;//bad statename
	private boolean timestepisfixed = false;
	private long fixedtimestep = 500;
	

	public Game() {
		setState(GameState.starting);
		init();
	}


	// do initial setup,, create own exception, TODO rename
	private void init() throws IllegalStateException {
		initWindow();
		
		this.setCurrentWorld(new World());
		
		
		setState(GameState.running);
	}
	//temp block for window init move to own function 
	private void initWindow() throws IllegalStateException {
		// do stuffs. init jframe here. perhaps own thread?
		if (getState() != GameState.starting)
			throw new IllegalStateException();
		
		//init the new frame and  set size and closing behaviour
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 500));//temp do this in config file
		
		//create instance of child of jpane. use to paint to the frame
		screenPane = new Screen();
		screenPane.init(frame.getSize());
		
		//native doublebuffering. trustworthy?
		screenPane.setDoubleBuffered(true);
		//bind pane to frame
		frame.setContentPane(screenPane);

		frame.setVisible(true);
		frame.requestFocus();
		
	}
	// main loop of the program
	public void run() {
		if (getState() != GameState.running)
			throw new IllegalStateException();
		long last = System.nanoTime();
		
		int tFps = 60;
		long optimum =  ((long)(1e9) / tFps);
		
		int fps = 0;
		double lastTick = 0;
		do {
			long now = System.nanoTime();// slow call?
			long timeTaken = now - last;
			last = now;
			double delta = timeTaken / ((double) optimum);
			
			//update the system
			update(delta);
			//repaint all
			screenPane.repaint();
			
			lastTick += timeTaken;
			fps++;

			if (lastTick >= 1e9) {//TODO replace with observer or similar
				System.out.println("(FPS: " + fps + ")");
				lastTick = 0;
				fps = 0;
			}
			long sleeptime = (long) ((last - System.nanoTime() + optimum) / 1e6);
			if(timestepisfixed) {
				sleeptime = fixedtimestep;
			}
			try {
				Thread.sleep(sleeptime);//?? replace with wait?
			} catch (InterruptedException e) {
			}

		} while (isRunning());

	}

	private void update(double delta) {
		if(getState() ==GameState.running)
			getCurrentWorld().update(delta);
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
		//add the new world to be drawn
		screenPane.addDrawable(braveNewWorld);//FIX wierd dataflow. the wold doesnt give a list of drawables
		if(currentWorld!= null)
			//flag the old world to be discarded
			currentWorld.setActive(false);
		
		//replace wolds
		this.currentWorld = braveNewWorld;
		currentWorld.setActive(true);
	}

}
