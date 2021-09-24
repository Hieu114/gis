package hieuttran;

import java.math.BigDecimal;
import java.util.*;

public final class InterestPoints {
	private final BiDimensionalMap<InterestPoint> points = new BiDimensionalMap();
	private InterestPoints(Builder builder) {
		for (BigDecimal x : builder.points.xSet()) {
			for (BigDecimal y : builder.points.ySet(x)) {
				points.getUpdater().setX(x).setY(y).setValues(builder.points.get(x, y)).add();
			}
		}
	};
	public static class Builder{
		private final BiDimensionalMap<InterestPoint> points = new BiDimensionalMap();
		public final boolean add(InterestPoint interestPoint) {
			Collection<InterestPoint> c = new ArrayList();
			c.add(interestPoint.validate());
			return points.getUpdater()	.setX(interestPoint.x())
										.setY(interestPoint.y())
										.setValues(c)
										.add();
		}
		public final InterestPoints build() {
			return new InterestPoints(this);
		}
	}
		
	public final Collection<InterestPoint> get(Coordinate coordinate){
		return points.get(coordinate.validate());
	}
	
	public final List<Collection<InterestPoint>> interestPoints(){
		return points.collectionList();
	}
		
	public String toString() {
		return "Interest points with " + points.collectionSize() + " entries";
	}
	
	public final long count(RectilinearRegion region,  Marker marker) {
		long count = 0;
		for (Rectangle rec : region.getRecs()) {
			count += points.slice(rec).collectionSize(a -> a.hasMarker(marker));
		}
		return count;
	}
	
//	public static void main(String[] args) {
//		Builder builder = new Builder();
//		BigDecimal two = new BigDecimal(2);
//		BigDecimal three = new BigDecimal(3);
//		BigDecimal four = new BigDecimal(4);
//		BigDecimal five = new BigDecimal(5);
//		System.out.print(builder.add(new InterestPoint<Marker>(new Coordinate(two,three), Marker.GYM))+"\n");
//		System.out.print(builder.add(new InterestPoint<Marker>(new Coordinate(three,four), Marker.CLASSROOM))+"\n");
//		InterestPoints map = new InterestPoints(builder);
//		System.out.print(map.interestPoints() + "\n");
//		System.out.print(map.get(new Coordinate(two,two)) + "\n");
//		Set<Rectangle> met = new HashSet<>();
//		met.add(new Rectangle (new Coordinate(two, three), new Coordinate(three, four)));
//		met.add(new Rectangle (new Coordinate(three, four), new Coordinate(four, five)));
//		RectilinearRegion r = new RectilinearRegion();
//		r.of(met);
//		System.out.print(r.getRecs() + "\n");
//		System.out.print(map.count(r, Marker.GYM));
//	}
//}
