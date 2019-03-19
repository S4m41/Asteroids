import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

class QuadTree {
	// Arbitrary constant to indicate how many elements can be stored in this quad
	// tree node
	private final int QT_NODE_CAPACITY = 4;

	// Axis-aligned bounding box stored as a center with half-dimensions
	// to represent the boundaries of this quad tree
	AABB boundary;

	// temp
	@SuppressWarnings("serial")
	private class XY extends Vector2D {
		public XY(double x, double y) {
			super(x, y);
		}
	};

	private class AABB {
		private XY center;
		private float halfDimension;

		AABB(XY center, float halfDimension) {
			this.center = center;
			this.halfDimension = halfDimension;
		}

		boolean containsPoint(XY point) {
			if (center.getX() - (halfDimension) <= point.getX() && point.getX() < center.getX() + (halfDimension)) {
				// pass
			} else {
				return false;
			}
			if (center.getY() - (halfDimension) <= point.getY() && point.getY() < center.getY() + (halfDimension)) {

			} else {
				return false;
			}
			return true;

		}

		boolean intersectsAABB(AABB other) {
			if (Abs(this.center.getX() - other.center.getX()) > (this.halfDimension + other.halfDimension))
				return false;
			if (Abs(this.center.getY() - other.center.getY()) > (this.halfDimension + other.halfDimension))
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

	// Points in this quad tree node
	private ArrayList<XY> points = new ArrayList<QuadTree.XY>(QT_NODE_CAPACITY);

	// Children
	private QuadTree northWest;
	private QuadTree northEast;
	private QuadTree southWest;
	private QuadTree southEast;

	// Methods
	QuadTree(AABB _boundary) {
		boundary = _boundary;
	}

	boolean insert(XY p) {
		{
			// Ignore objects that do not belong in this quad tree
			if (!boundary.containsPoint(p))
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
		float halfDimension = (boundary.halfDimension) / 2;
		double x = boundary.center.getX();
		double y = boundary.center.getY();

		southEast = new QuadTree(new AABB(new XY(x + halfDimension, y), halfDimension));// no negative Y?? @samai
		southWest = new QuadTree(new AABB(new XY(x, y), halfDimension));
		northWest = new QuadTree(new AABB(new XY(x, y + halfDimension), halfDimension));
		northEast = new QuadTree(new AABB(new XY(x + halfDimension, y + halfDimension), halfDimension));

		// TODO
	} // create four children that fully divide this quad into four quads of equal
		// area

	ArrayList<XY> queryRange(AABB range) {
		{
			// Prepare an array of results
			ArrayList<XY> pointsInRange = new ArrayList<XY>();

			// Automatically abort if the range does not intersect this quad
			if (!boundary.intersectsAABB(range))
				return pointsInRange; // empty list

			// Check objects at this quad level
			for (int p = 0; p < points.size(); p++) {
				if (range.containsPoint(points.get(p)))
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