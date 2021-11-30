package hieuttran;

import java.math.BigDecimal;
import java.util.*;

/**
 * Store the set of interest points in the 2D map
 */
public final class InterestPoints {
	private final BiDimensionalMap<InterestPoint> points = new BiDimensionalMap();

	/**
	 * create a new InterestPoints based on a map built by teh given builders
	 * @param builder
	 */
	private InterestPoints(Builder builder) {
		for (BigDecimal x : builder.points.xSet()) {
			for (BigDecimal y : builder.points.ySet(x)) {
				points.getUpdater().setX(x).setY(y).setValues(builder.points.get(x, y)).add();
			}
		}
	};

	/**
	 * Helper class to update the map in the InterestPoints
	 */
	public static class Builder{
		private final BiDimensionalMap<InterestPoint> points = new BiDimensionalMap();

		/**
		 * Add the valid interest point to the map
		 * @param interestPoint given interest point
		 * @return false if the map already contains the input interest point, otherwise true
		 * @throws NullPointerException if the input interest point is invalid
		 */
		public final boolean add(InterestPoint interestPoint) {
			Collection<InterestPoint> c = new ArrayList();
			c.add(interestPoint.validate());
			return points.getUpdater()	.setX(interestPoint.x())
										.setY(interestPoint.y())
										.setValues(c)
										.add();
		}

		/**
		 * build a new interest point based on the information in the Builder
		 * @return a new InterestPoints
		 */
		public final InterestPoints build() {
			return new InterestPoints(this);
		}
	}

	/**
	 * Retrieve the information in the input coordinate
	 * @param coordinate the given coordinate
	 * @return the collection of interest points
	 */
	public final Collection<InterestPoint> get(Coordinate coordinate){
		return points.get(coordinate.validate());
	}

	/**
	 * retrive the list of interest points in the map
	 * @return list of collections of interest points in the map
	 */
	public final List<Collection<InterestPoint>> interestPoints(){
		return points.collectionList();
	}

	/**
	 * get the String presentation if the interest points
	 * @return String presentation if the interest points
	 */
	public String toString() {
		return "Interest points with " + points.collectionSize() + " entries";
	}

	/**
	 * Count the number of interest points with input marker in the non-overlapping region
	 * @param region the rectilinear region surrounding the interest points
	 * @param marker input marker
	 * @return the number of interest points with input marker in the non-overlapping input region
	 */
	public final long count(RectilinearRegion region,  Marker marker) {
		long count = 0;
		for (Rectangle rec : region.getRecs()) {
			count += points.slice(rec).collectionSize(a -> a.hasMarker(marker));
		}
		return count;
	}
}
