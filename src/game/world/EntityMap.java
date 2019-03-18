package game.world;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import game.entity.Entity;

public class EntityMap extends Vector<Vector<EntityNode>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 648795001271499834L;
	private ArrayList<Entity> itarateList = new ArrayList<Entity>();
	private Dimension tileSize = new Dimension(10, 10);

	public EntityMap() {
		this(new Dimension(50, 50));
	}

	public EntityMap(Dimension d) {

		for (int x = 0; x < d.width; x++) {
			// the main container is for x axis the secondary is for y axis
			Vector<EntityNode> vY = new Vector<EntityNode>();
			for (int y = 0; y < d.height; y++) {
				vY.add(new EntityNode());
			}

			this.add(x, vY);

		}
	}
	/***
	 * get the node at grid coordinate. use get gridposition to calculate grid coordinates
	 * @param xGrid 
	 * @param yGrid
	 * @return
	 */
	public EntityNode get(int xGrid, int yGrid) {
		return this.get(xGrid).get(yGrid);

	}

	public void add(Entity e) {
		Dimension d = getGridPosition(e);
		int x= d.width;
		int y = d.height;
		this.get(x).get(y).add(e);
		itarateList.add(e);
	}

	public void remove(Entity e) {
		Dimension d = getGridPosition(e);
		int x= d.width;
		int y = d.height;
		this.get(x).get(y).remove(e);
		itarateList.remove(e);
	}

	public ArrayList<Entity> getContainedEntities(){
		return itarateList;
		
	}
	public Dimension getGridPosition(Entity e) {
		int x = (int) (e.getPosition().getX()/tileSize.width);
		int y = (int) (e.getPosition().getY()/tileSize.height);
		return new Dimension(x,y);
	}

}
