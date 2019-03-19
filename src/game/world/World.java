package game.world;


import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.entity.Astroid;
import game.entity.Bullet;
import game.entity.Entity;
import game.entity.Ship;
import main.Drawable;

public class World implements Drawable {
	private boolean visible = false;
	Vector2D worldSize = new Vector2D(500, 500);
	EntityMap entityMap; //too slow restructure
	//ArrayList<Entity> entityMap = new ArrayList<Entity>();
	Ship player = new Ship(this);
	
	public World() {
		entityMap = new EntityMap(worldSize);
		
		for (int i = 0; i < 50000; i++) {
			Astroid e = new Astroid(this);
			double x = ThreadLocalRandom.current().nextDouble(0, 1);
			double y = ThreadLocalRandom.current().nextDouble(0, 1);
			e.setHeading(new Vector2D(x, y));
			entityMap.add(e);
			
		}
		entityMap.add(player);
		Vector2D worldmiddle = EntityMap.getWorldPosition(worldSize).scalarMultiply(0.5);
		player.setPosition(worldmiddle);
		player.setHeading(new Vector2D(0,0));
	}

	public void update(double delta) {
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
			}else {
				deadlist.add(e);
			}
		}
		if(player.isFiring()) {//temp
			Bullet b = new Bullet(this);
			b.setHeading(player.getLNZHeading());
			b.setPosition(player.getPosition());
			b.setPosition(b.calcNewPos(delta));
			entityMap.add(b);
			player.fire();
		}
		//remove all dead entities from loop
		nowloop.removeAll(deadlist);
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
	public Entity doescollide(Vector2D position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void draw(Graphics g) {
		for(Entity e : entityMap.getContainedEntities()) {
			e.draw(g);
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
