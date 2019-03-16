package main;

import game.Game;

//main enty point of program
public class main {

	public static void main(String[] args) {
		Game current = new Game();
		try {
			current.start();
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
	}

}
