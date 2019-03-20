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
		
		for (int i = 0; i < 500; i+=30) {
			Astroid e = new Astroid(this);
			double x = ThreadLocalRandom.current().nextDouble(0, 1);
			double y = ThreadLocalRandom.current().nextDouble(0, 1);
			e.setPosition(new Vector2D(i%500, 0));
			e.setHeading(new Vector2D(0, 1));
			try {

				entityMap.add(e);
			} catch (EntityMap.QuadtreeExeption e2) {
				e2.printStackTrace();
				System.exit(-1);
				// TODO: handle exception
			}
			
		}
		try {
		entityMap.add(player);
		} catch (EntityMap.QuadtreeExeption e2) {
			e2.printStackTrace();
			System.exit(-1);
			// TODO: handle exception
		}
		Vector2D worldmiddle = EntityMap.getWorldPosition(worldSize).scalarMultiply(0.5);
		player.setPosition(worldmiddle);
		player.setHeading(new Vector2D(0,0));
	}

	public void update(double delta){
		// move all
		ArrayList<Entity> nowloop = entityMap.getContainedEntities();
		//list of dead entities
		ArrayList<Entity> deadlist = new ArrayList<Entity>();
		int size = nowloop.size();
		for (int i =0;i<size;i++) {
			Entity e = nowloop.get(i);
			if (e.isAlive()) {
				delta = 1;//XXX
				e.move(delta);
				entityMap.update(e);
			}else {
				deadlist.add(e);
			}
		}
		if(player.isFiring()) {//temp
			Bullet b = new Bullet(this);
			b.setHeading(player.getLNZHeading());
			b.setPosition(player.getPosition());
			b.setPosition(b.calcNewPos(delta));
			try {
				entityMap.add(b);
				player.fire();	
			}catch (QuadtreeExeption err) {
				err.printStackTrace();
				System.exit(-1);
				// TODO: handle exception
			}
		}
		//remove all dead entities from loop
		for(Entity e : deadlist) {
			entityMap.remove(e);
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
		
		// TODO Auto-generated method stub
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

}
