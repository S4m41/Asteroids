package game.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.entity.Astroid;
import game.entity.Entity;
import game.entity.Ship;
import jdk.nashorn.api.tree.ForInLoopTree;
import main.Drawable;

public class World implements Drawable {
	private boolean visible = false;
	Dimension worldSize = new Dimension(50, 50);
	EntityMap entityMap; //too slow restructure
	//ArrayList<Entity> entityMap = new ArrayList<Entity>();

	public World() {
		entityMap = new EntityMap(worldSize);
		
		for (int i = 0; i < 50; i++) {
			Astroid e = new Astroid(this);
			double x = ThreadLocalRandom.current().nextDouble(0, 1);
			double y = ThreadLocalRandom.current().nextDouble(0, 1);
			//e.setHeading(new Vector2D(x, y));
			entityMap.add(e);
			
		}
		
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

}
