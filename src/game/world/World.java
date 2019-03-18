package game.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import game.entity.Astroid;
import game.entity.Entity;
import game.entity.Ship;
import main.Drawable;

public class World implements Drawable {
	private boolean visible = false;
	Dimension worldSize = new Dimension(50, 50);
	EntityMap entityMap;

	public World() {
		entityMap = new EntityMap(worldSize);
		entityMap.add(new Ship(this));
	}

	public void update(double delta) {
		// move all
		ArrayList<Entity> nowloop = entityMap.getContainedEntities();
		for (Entity e : nowloop) {
			if (e.isAlive()) {
				e.move(delta);
			}else {
				entityMap.remove(e);
			}
		}

		// tell all to resolve collisions
		// generate asteroids and ask ship if generate bullets

	}

	@Override
	public void draw(Graphics g) {
		for(Entity e : entityMap.getContainedEntities()) {
			
		}

	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public void setActive(boolean b) {
		visible = b;
	}

	public Entity doescollide(Vector2D position) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateposition(Entity entity) {
		entityMap.remove(entity);
		entityMap.add(entity);
	}

}
