package game.world;

import java.awt.Graphics;

import main.Drawable;

public class World implements Drawable {
	private boolean visible=false;

	public void update(double delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	public void setActive(boolean b) {
		visible=b;		
	}

}
