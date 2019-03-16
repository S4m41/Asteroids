package game.entity;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Entity {
	Vector2D heading,position= new Vector2D(0, 0);
	double speed;
	//ID//java implicit
	long size;
	////type //enum? int?
	boolean alive = false;
	//sprite

	public void move() {
		
	}
	public boolean isAlive() {
		return alive; 
	}
	//+collidedWith(entity)
	//-ded()
}
