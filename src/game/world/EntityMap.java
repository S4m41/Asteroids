package game.world;

import java.util.ArrayList;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import game.entity.Entity;

public class EntityMap {
	QuadTree storage;
	ArrayList<Entity> containedList = new ArrayList<Entity>();
	public EntityMap(Vector2D worldSize) {
		AABB boundary = new AABB(worldSize.scalarMultiply(0.5), worldSize.getX()/2,worldSize.getY()/2);
		storage = new QuadTree(boundary);
		// TODO Auto-generated constructor stub
	}

	public void add(Entity e) {
		storage.insert(e);
		containedList.add(e);
		// TODO Auto-generated method stub

	}

	public static Vector2D getWorldPosition(Vector2D gridpos) {
		// TODO Auto-generated method stub
		return gridpos;
	}
	public static Vector2D getGridPosition(Vector2D worldpos) {
		// TODO Auto-generated method stub
		return worldpos;
	}

	public ArrayList<Entity> getContainedEntities() {
		// TODO Auto-generated method stub
		return containedList;
	}
	//happens once per frame
	public void update(Entity entity) {
		AABB shake = new AABB(entity.getOldPosition(), 1, 1);
		
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @author Wikipedia https://en.wikipedia.org/wiki/Quadtree
	 */
	private class QuadTree {
		// Arbitrary constant to indicate how many elements can be stored in this quad
		// tree node
		private final int QT_NODE_CAPACITY = 4;

		// Axis-aligned bounding box stored as a center with half-dimensions
		// to represent the boundaries of this quad tree
		AABB boundary;
	
		// Points in this quad tree node
		private ArrayList<Entity> points = new ArrayList<Entity>(QT_NODE_CAPACITY);

		// Children
		private QuadTree northWest;
		private QuadTree northEast;
		private QuadTree southWest;
		private QuadTree southEast;

		// Methods
		QuadTree(AABB _boundary) {
			boundary = _boundary;
		}

		boolean insert(Entity p) {
			{
				// Ignore objects that do not belong in this quad tree
				if (!boundary.containsPoint(p.getPosition()))
					return false; // object cannot be added

				// If there is space in this quad tree and if doesn't have subdivisions, add the
				// object here
				if (points.size() < QT_NODE_CAPACITY && northWest == null) {
					points.add(p);
					return true;
				}

				// Otherwise, subdivide and then add the point to whichever node will accept it
				if (northWest == null)
					subdivide();
				// We have to add the points/data contained into this quad array to the new
				// quads if we want that only
				// the last node holds the data

				if (northWest.insert(p))
					return true;
				if (northEast.insert(p))
					return true;
				if (southWest.insert(p))
					return true;
				if (southEast.insert(p))
					return true;

				// Otherwise, the point cannot be inserted for some unknown reason (this should
				// never happen)
				return false;
			}
		}
		/**
		 * @author https://gist.github.com/chrislo27/81c56d24199a13821bd2
		 */
		void subdivide() {
				double halfDimensionx = (boundary.halfDimensionx)/2;
				double halfDimensiony = (boundary.halfDimensiony)/2;
				double x = boundary.center.getX();
				double y = boundary.center.getY();

				southEast = new QuadTree(new AABB (new Vector2D(x + halfDimensionx, y), halfDimensionx,halfDimensiony));//no negative Y?? @samai
				southWest = new QuadTree(new AABB (new Vector2D(x, y), halfDimensionx,halfDimensiony));
				northWest = new QuadTree(new AABB (new Vector2D(x, y + halfDimensiony), halfDimensionx,halfDimensiony));
				northEast = new QuadTree(new AABB (new Vector2D(x + halfDimensionx, y + halfDimensiony), halfDimensionx,halfDimensiony));
				
			//TODO
		} // create four children that fully divide this quad into four quads of equal
			// area

		ArrayList<Entity> queryRange(AABB range) {
			{
				// Prepare an array of results
				ArrayList<Entity> pointsInRange = new ArrayList<Entity>();

				// Automatically abort if the range does not intersect this quad
				if (!boundary.intersectsAABB(range))
					return pointsInRange; // empty list

				// Check objects at this quad level
				for (int p = 0; p < points.size(); p++) {
					if (range.containsPoint(points.get(p).getPosition()))
						pointsInRange.add(points.get(p));
				}

				// Terminate here, if there are no children
				if (northWest == null)
					return pointsInRange;

				// Otherwise, add the points from the children
				pointsInRange.addAll(northWest.queryRange(range));
				pointsInRange.addAll(northEast.queryRange(range));
				pointsInRange.addAll(southWest.queryRange(range));
				pointsInRange.addAll(southEast.queryRange(range));

				return pointsInRange;
			}
		}
	}
	private class AABB {
		private Vector2D center;
		private double halfDimensionx;
		double halfDimensiony;

		AABB(Vector2D center, double d, double e) {
			this.center = center;
			this.halfDimensionx = d;
			this.halfDimensiony =e;
		}

		boolean containsPoint(Vector2D vector2d) {
			if (center.getX() - (halfDimensionx) <= vector2d.getX() && vector2d.getX() < center.getX() + (halfDimensionx)) {
				// pass
			} else {
				return false;
			}
			if (center.getY() - (halfDimensiony) <= vector2d.getY() && vector2d.getY() < center.getY() + (halfDimensiony)) {

			} else {
				return false;
			}
			return true;

		}

		boolean intersectsAABB(AABB other) {
			if (Abs(this.center.getX() - other.center.getX()) > (this.halfDimensionx + other.halfDimensionx))
				return false;
			if (Abs(this.center.getY() - other.center.getY()) > (this.halfDimensiony + other.halfDimensiony))
				return false;
			return true;
		}

		private double Abs(double d) {
			if (d < 0) {
				d *= -1;
			}
			return d;
		}
	}


}
