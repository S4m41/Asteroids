package game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;//create wrapper

import game.world.World;
import main.Drawable;

public abstract class Entity implements Drawable {
	public static final int MAXSIZE = 50;
	protected Vector2D position = new Vector2D(1, 1);
	protected Vector2D heading = new Vector2D(1, 1);
	protected double speed = 2;
	//// type //enum? int?
	protected boolean alive = true;
	protected Color mycol = Color.blue;// temp replace w sprite
	private static int _IDCOUNT = 0;
	private final int ID = setID(); //temp java implicit. check later assuming unique
	protected int size = 10;

	protected Vector2D oldposition = getPosition();
	// sprite
	
	private World myworld;

	public Entity(World home) {// temp public make protected and add public argless constructor
		setMyworld(home);
		int red, blue, green;
		do {
			red = ThreadLocalRandom.current().nextInt(0, 255);
			green = ThreadLocalRandom.current().nextInt(0, 255);
			blue = ThreadLocalRandom.current().nextInt(0, 255);
		} while (red + blue + green < 100);
		mycol = new Color(red, green, blue);
	}
	protected static double square(double d) {
		return d*d;
	}
	
	public void move(double delta) {
		oldposition = position;
		position = position.add(heading.scalarMultiply(speed*delta));
		
		
		ArrayList<Entity> cEntityList = getMyworld().doescollide(position);// do i collide with anything at this position.needs size
		cEntityList.remove(this);
		//ArrayList<Entity> notIt = new ArrayList<Entity>();
		for(Entity collisionPartner: cEntityList){
			if(collisionPartner!=this)
				if(!(this.size/2+collisionPartner.size/2< position.distance(collisionPartner.position))) {//XXX test expression
					if(this.colidedWith(collisionPartner))
						break;
			}
		}
		getMyworld().updateposition(this);
		
		// System.out.println(ID);
	}

	public Vector2D calcNewPos(double delta) {
		return position.add(heading.scalarMultiply(speed));
	}

	public abstract boolean colidedWith(Entity cEntity);
	@Override
	public void draw(Graphics g) {
		g.setColor(mycol);
		g.fillRect((int) position.getX()-size/2, (int) position.getY()-size/2, size, size);
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
	public static double wrapAround(double coordinate, double max) {
		coordinate %= max;
		return (coordinate < 0) ? coordinate + max : coordinate;
	}

	public boolean isAlive() {// inline this TODO
		return alive;
	}

	@Override
	public boolean isVisible() {
		return true;//isAlive();
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

	public Vector2D getPosition() {
		return position;
	}
	public Vector2D getOldPosition() {
		return oldposition;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public void setPosition(double x, double y) {
		setPosition(new Vector2D(x, y));
	
	}
	@Override
	public boolean equals(Object obj) {
		//probably unnecessary;
		if(obj instanceof Entity) {
			Entity e = (Entity) obj;
			return getID()==e.getID();
		}
		return super.equals(obj);
	}

	public int getSize() {
		return size;
	}

	public boolean setSize(int size) {
		if(size<MAXSIZE)
			this.size = size;
		else
			return false;
		return true;
	}
	public int getID() {
		return ID;
	}
	private static int setID() {
		return _IDCOUNT++;
	}
	public World getMyworld() {
		return myworld;
	}
	public void setMyworld(World myworld) {
		this.myworld = myworld;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String val =super.toString(); 
		return val.substring(val.lastIndexOf('.'),val.indexOf('@'))  + " ID:"+getID()+ position.toString() +"size:" + size;
	}

}
