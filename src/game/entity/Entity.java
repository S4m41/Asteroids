package game.entity;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;//create wrapper

public class Entity {
	Vector2D heading,position= new Vector2D(0, 0);
	double speed=1;
	//ID//java implicit
	long size=1;
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
}
