package game.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;//create wrapper

import game.world.World;
import main.Drawable;

public abstract class Entity implements Drawable {

	protected Vector2D position = new Vector2D(1, 1);
	protected Vector2D heading = new Vector2D(1, 1);
	protected double speed = 2;
	//// type //enum? int?
	protected boolean alive = true;
	protected Color mycol = Color.blue;// temp replace w sprite

	private final int ID = hashCode(); // java implicit. check later temp
	protected int size = 10;

	protected Vector2D oldposition = getPosition();

	World myworld;

	public Entity(World home) {// temp public make protected and add public argless constructor
		myworld = home;
		int red, blue, green;
		do {
			red = ThreadLocalRandom.current().nextInt(0, 255);
			green = ThreadLocalRandom.current().nextInt(0, 255);
			blue = ThreadLocalRandom.current().nextInt(0, 255);
		} while (red + blue + green < 100);
		mycol = new Color(red, green, blue);
	}

	// sprite
	public void move(double delta) {
		oldposition = position;
		position = position.add(heading.scalarMultiply(speed*delta));
		Entity cEntity = myworld.doescollide(position);// do i collide with anything at this position.needs size
		if (cEntity != null) {
			this.colidedWith(cEntity);
		} else {
			myworld.updateposition(this);
		}
		// System.out.println(ID);
	}

	public Vector2D calcNewPos(double delta) {
		return position.add(heading.scalarMultiply(speed));
	}

	protected abstract void colidedWith(Entity cEntity);
	// -ded()
	@Override
	public void draw(Graphics g) {
		g.setColor(mycol);
		g.fillRect((int) position.getX(), (int) position.getY(), size, size);
	}

	public boolean isAlive() {// inline this TODO
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
		if (heading.getZero().equals(heading))
			this.heading = heading;
		else
			this.heading = heading.normalize();
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

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public void setPosition(double x, double y) {
		setPosition(new Vector2D(x, y));

	}

	public void wrap(Vector2D worldPosition) {
		// TODO Auto-generated method stub
		// if(Vector2D.angle(arg0, arg1)))//doesnt support negative angles
		Vector2D newwrappos = new Vector2D(wrapAround(position.getX(), worldPosition.getX()),
				wrapAround(position.getY(), worldPosition.getY()));
		this.position = newwrappos;

	}

	/*
	 * https://codereview.stackexchange.com/questions/58063/screen-wraparound
	 */
	private static double wrapAround(double coordinate, double max) {
		coordinate %= max;
		return (coordinate < 0) ? coordinate + max : coordinate;
	}

}
