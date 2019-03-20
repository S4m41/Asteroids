package game.entity;

import java.awt.Color;
import java.awt.Graphics;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.world.World;

public class Ship extends Entity {
	boolean fireFlag = false;
	Vector2D lastNZHeading = new Vector2D(1, 0);
	double lastFire = 0;
	final int coolDown = 10;
	private static boolean deathmsg = false;

	public Ship(World home) {
		super(home);
		this.mycol = Color.RED; // goes fastah
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(double delta) {
		// TODO Auto-generated method stub
		super.move(delta);
		lastFire += 1 * delta;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(mycol);//TODO sprites
		g.fillOval((int) position.getX(), (int) position.getY(), size, size);
	}

	@Override
	public boolean colidedWith(Entity cEntity) {
		if(cEntity instanceof Astroid && deathmsg ) {
			System.out.println("§§§§§§§§");
			System.out.println("ya DED");
			System.out.println("§§§§§§§§");
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	public void shouldfire() {
		if (lastFire > coolDown) {
			fireFlag = true;
			lastFire=0;
		}
			
	}

	public void fire() {// make this actually fire
		fireFlag = false;
	}

	public boolean isFiring() {
		return fireFlag;
	}

	@Override
	public void setHeading(Vector2D heading) {
		if (!heading.getZero().equals(heading)) {
			lastNZHeading = heading;
		}
		super.setHeading(heading);
	}
	/***
	 * 
	 * @return the last non-zero heading this ship had
	 */
	public Vector2D getLNZHeading() {
		// TODO Auto-generated method stub
		return lastNZHeading;
	}

}
