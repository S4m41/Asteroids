package game.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Vector;

import game.entity.Astroid;
import game.entity.Entity;
import game.entity.EntityNode;
import game.entity.Ship;
import main.Drawable;

public class World implements Drawable {
	private boolean visible = false;
	Dimension worldSize = new Dimension(50, 50);
	EntityMap entityMap;

	public World() {
		entityMap = new EntityMap(worldSize);
		entityMap.add(new Ship());
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
		g.setColor(Color.blue);
		g.fillOval(10, 10, 10, 10);

	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public void setActive(boolean b) {
		visible = b;
	}

}
