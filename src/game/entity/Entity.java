package game.entity;

import java.awt.Color;
import java.awt.Graphics;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;//create wrapper

import main.Drawable;

public class Entity implements Drawable {
	protected Vector2D position = new Vector2D(1, 1);
	protected Vector2D heading = new Vector2D(1, 1);
	protected double speed = 1;
	private int ID = hashCode(); // java implicit. check later temp
	private int size = 10;
	//// type //enum? int?
	protected boolean alive = true;
	
	protected Color mycol = Color.blue;//temp replace w sprite

	// sprite
	public void move() {
		position = position.add(heading.scalarMultiply(speed));
	}

	
	// +collidedWith(entity)
	// -ded()
	@Override
	public void draw(Graphics g) {
		g.setColor(mycol);
		g.fillRect((int)position.getX(), (int) position.getY(), size, size);
	}
	public boolean isAlive() {//inline this TODO
		return alive;
	}
	@Override
	public boolean isVisible() {
		return isAlive();
	}


	public Vector2D getHeading() {
		return heading;
	}


	public void setHeading(Vector2D heading) {
		this.heading = heading;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public int getID() {
		return ID;
	}
}
