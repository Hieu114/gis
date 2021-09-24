package hieuttran;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class RectilinearRegion {
	private final Set<Rectangle> rectangles = new HashSet<>();
	public Set<Rectangle> getRecs(){
		return rectangles;
	}
	private RectilinearRegion(Set<Rectangle> rectangles) {
		this.rectangles.addAll(rectangles);
	}
	public RectilinearRegion() {}
	
	public BiDimensionalMap<Rectangle> rectanglesMap(){
		Set<BigDecimal> xCoord = new HashSet<>();
		Set<BigDecimal> yCoord = new HashSet<>();
		for (Rectangle rec : getRecs()) {
			xCoord.add(rec.left());
			xCoord.add(rec.right());
			yCoord.add(rec.top());
			yCoord.add(rec.bottom());
		}
		BiDimensionalMap<Rectangle> grid = new BiDimensionalMap<Rectangle>(xCoord, yCoord); 
		for (Rectangle rec : getRecs()) {
			grid.slice(rec).addEverywhere(rec);
		}
		return grid;
	}
	
	public boolean isOverlapping() {
		for (Rectangle r : getRecs()) {
			if (!getRecs().stream().noneMatch(rec -> (!r.equals(rec) && (rec.bottomLeft().compareTo(r.bottomLeft()) >= 0 && rec.bottomLeft().compareTo(r.topRight()) < 0))))
				return true;
		}
		return false;
	}
	
	public  static  final RectilinearRegion of(Set<Rectangle>  rectangles) {
		if (!(Objects.isNull(rectangles) || new RectilinearRegion(rectangles).isOverlapping()))
			return new RectilinearRegion(rectangles);
		throw new IllegalArgumentException();
	}
	
//	public static void main(String[] args) {
//		BigDecimal two = new BigDecimal(2);
//		BigDecimal three = new BigDecimal(3);
//		BigDecimal four = new BigDecimal(4);
//		BigDecimal five = new BigDecimal(5);
//		Set<Rectangle> met = new HashSet<>();
//		met.add(new Rectangle (new Coordinate(two, three), new Coordinate(five, four)));
//		met.add(new Rectangle (new Coordinate(two, three), new Coordinate(three, five)));
//		RectilinearRegion r = new RectilinearRegion(met);
//		System.out.print(r.rectanglesMap());
//	}
}
