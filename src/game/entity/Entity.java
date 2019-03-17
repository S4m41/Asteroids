package game.entity;
import java.awt.Graphics;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;//create wrapper

import main.Drawable;

public class Entity implements Drawable {
	Vector2D heading,position= new Vector2D(0, 0);
	double speed=1;
	//ID//java implicit
	int size=10;
	////type //enum? int?
	boolean alive = false;
	//sprite
	public void move() {
		position.add(heading.scalarMultiply(speed));
	}
	public boolean isAlive() {
		return alive; 
	}
	//+collidedWith(entity)
	//-ded()
	@Override
	public void draw(Graphics g) {
		g.fillRect((int) position.getX(), (int)position.getY(), (int)position.getX()+size, (int)position.getX()+size);
		
	}
}
