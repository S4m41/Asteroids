package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.Game;

public class KeyListener extends KeyAdapter {
	
	Game g;
	
	public KeyListener(Game gg) {
		g= gg;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyPressed(e);
		g.keyPressed(e);//temp
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyReleased(e);
		g.keyReleased(e);//temp
	}

}
