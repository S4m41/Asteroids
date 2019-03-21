package game.world;


import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.entity.Astroid;
import game.entity.Bullet;
import game.entity.Entity;
import game.entity.Ship;
import game.world.EntityMap.QuadtreeExeption;
import main.Drawable;

public class World implements Drawable {
	private boolean visible = false;
	Vector2D worldSize = new Vector2D(500, 500);
	EntityMap entityMap; //too slow restructure
	//ArrayList<Entity> entityMap = new ArrayList<Entity>();
	Ship player = new Ship(this);
	
	@SuppressWarnings("unused")
	public World(){
		entityMap = new EntityMap(worldSize);
		
	}
	@SuppressWarnings("unused")
	public void init() {
		add(player);
		Vector2D worldmiddle = EntityMap.getWorldPosition(worldSize).scalarMultiply(0.5);
		player.setPosition(worldmiddle);
		player.setHeading(new Vector2D(0,0));
		
		for (int i = 0; i < 10; i+=1) {
			
			Astroid e = new Astroid(this);
			double x = ThreadLocalRandom.current().nextDouble(0, 1);
			double y = ThreadLocalRandom.current().nextDouble(0, 1);
			e.setPosition(new Vector2D((i+50)%500,100+ i%100));
			e.setHeading(new Vector2D(0, 1));
			e.setSpeed(1);
			e.setSize(20);//ThreadLocalRandom.current().nextInt(9, 50));
			add(e);
		}
	}

	public void update(double delta){
		// move all
		ArrayList<Entity> nowloop = entityMap.getContainedEntities();
		//list of dead entities
		ArrayList<Entity> deadlist = new ArrayList<Entity>();
		//list of new astroids
		ArrayList<Astroid> bornList = new ArrayList<Astroid>();
		int size = nowloop.size();
		for (int i =0;i<size;i++) {
			Entity e = nowloop.get(i);
			if (e.isAlive()) {
				
				e.move(delta);
				//entityMap.update(e);
				
				if(e instanceof Astroid) {//move logic somewhere lese temp
					Astroid a = (Astroid)e;
					if(a.hasSplit()) {
						Astroid[] children = new Astroid[2];
						a.getChildren(children, 2);
						bornList.add(children[0]); 
						bornList.add(children[1]);
					}
				}
					
				
			}else {
				System.out.println(e);
				deadlist.add(e);
			}
		}
		if(player.isFiring()) {//temp
			Bullet b = new Bullet(this);
			b.setHeading(player.getLNZHeading());
			b.setPosition(player.getPosition());
			b.setPosition(b.calcNewPos(delta));
			
			add(b);
			player.fire();	
			
		}
		for(Entity a: bornList) {
			add(a);
		}
		//remove all dead entities from loop
		for(Entity e : deadlist) {
			entityMap.remove(e);
			System.out.println(e);
		}
		deadlist.clear();
		// tell all to resolve collisions
		// generate asteroids and ask ship if generate bullets

	}



	/***
	 * temp Why do i have wrap logic here.
	 * @param entity the entity to be updated in the world
	 */
	
	public void updateposition(Entity entity) {

		entity.wrap(EntityMap.getWorldPosition(worldSize)); //delays wrap by one frame
		entityMap.update(entity);
	}
	public ArrayList<Entity> doescollide(Vector2D position) {
		ArrayList<Entity> possibleCollisions = entityMap.queryRange(position, Entity.MAXSIZE*2);
		return possibleCollisions;
	}
	@Override
	public void draw(Graphics g) {
		try {
			for(Entity e : entityMap.getContainedEntities()) {//XXX
				e.draw(g);
			}
		}catch(java.util.ConcurrentModificationException e) {
			System.out.println("---------------------------------");
			System.out.println("ConcurrentModificationException!!");
			System.out.println("---------------------------------");
		}
		

	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public void setActive(boolean b) {
		visible = b;
	}

	public void setKeyInput(boolean[] direction) {
		/*
		for(int i = 0; i<4; i++) {
			if(direction[i] && !direction[(i+2)%4]) {
				
			}
		}*/
		int xheading = 0, yheading =0;
		if(direction [0] && !direction[2]) {
			yheading = -1;
		}else if(direction [2] && !direction[0]) {
			yheading = 1;
		}
		if (direction [1] && !direction[3]) {
			xheading = -1;
		}else if (direction [3] && !direction[1]) {
			xheading = 1;
		}
		player.setHeading(new Vector2D(xheading, yheading));
		if(direction[4]) {//inefficient
			player.shouldfire();
		}
		
	}

	public void add(Entity e1) {
		try {
			entityMap.add(e1);
		} catch (QuadtreeExeption e) {
			e.printStackTrace();
			System.exit(-1);//brick
		}
		
	}
	public String stringTrace(Entity e1) {
		// TODO Auto-generated method stub
		return entityMap.printStringTrace(e1);
	}
	
	@Override
	public String toString() {
		String ret = super.toString();
		if(player.isAlive())
			ret += "\n Player:alive";
		else
			ret += "\n Player:ded";
		ret += "\n" + entityMap.toString();
		return ret;
	}
	/**
	 * testing function do not use
	 * @param e1
	 * @return 
	 */
	public ArrayList<Entity> getPos(Ship e1) {
		return entityMap.queryRange(e1.getPosition(), 2);
		
	}
	
}
