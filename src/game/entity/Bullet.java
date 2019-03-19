package game.entity;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.world.World;

public class Bullet extends Entity {

	double decaydistance = square(50);// 500^2;
	double distanceTraveled = 0;
	public Bullet(World home) {
		super(home);
		this.speed *=2;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void move(double delta) {
		// TODO Auto-generated method stub
		Vector2D  distance = heading.scalarMultiply(speed*delta);
		distanceTraveled += square(distance.getX())+square(distance.getY());
		System.out.println(distanceTraveled);
		if(distanceTraveled>decaydistance) {
			this.alive = false;
		}
		super.move(delta);
		
	}
	private static double square(double d) {
		return d*d;
	}
	@Override
	protected void colidedWith(Entity cEntity) {
		// TODO Auto-generated method stub
		
	}
	

}