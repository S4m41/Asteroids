package game.world;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import game.entity.Entity;

@Deprecated
public class EntityMapVVE extends Vector<Vector<EntityNode>> {// too slow. use sort and sweep or some kind of tree// use quadtree
	/*
	 * https://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374
	 */
	private static final long serialVersionUID = 648795001271499834L;
	private ArrayList<Entity> itarateList = new ArrayList<Entity>();
	private static Dimension tileSize = new Dimension(10, 10);

	public EntityMapVVE(Dimension d) {

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
	
	public void add(Entity e) {//TODO add index guards
		Dimension d = getGridPosition(e.getPosition());
		int x= d.width;
		int y = d.height;
		this.get(x%50).get(y%50).add(e);//XXX remove mod 50 and add some other guard
		itarateList.add(e);
	}

	public void remove(Entity e) {//TODO add index guards
		Dimension d = getGridPosition(e.getPosition());
		int x= d.width;
		int y = d.height;
		this.get(x).get(y).remove(e);
		itarateList.remove(e);
	}

	public ArrayList<Entity> getContainedEntities(){
		return itarateList;
		
	}
	public static Dimension getGridPosition(Vector2D vpos) {
		int x = ((int) vpos.getX())/tileSize.width;
		int y = ((int) vpos.getY())/tileSize.height;
		return new Dimension(x,y);
	}
	public static Vector2D getWorldPosition(Dimension d) {
		int x = ((int) d.getWidth())*tileSize.width;
		int y = ((int) d.getHeight())*tileSize.height;
		return new Vector2D(x,y);
	}
	public void update(Entity entity) {
		//remove(entity);//implement quadtrees 
		//add(entity);
		// TODO Auto-generated method stub
		
	}

}
