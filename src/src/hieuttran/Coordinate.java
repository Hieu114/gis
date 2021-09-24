package hieuttran;
import java.math.BigDecimal;
import java.util.*;

public record Coordinate(BigDecimal x, BigDecimal y) implements Comparable<Coordinate> {
	public  static  final  Coordinate  ORIGIN = new Coordinate(new BigDecimal(0), new BigDecimal(0));
	public  final  Coordinate validate() {
		Objects.requireNonNull(x, "x is null");
		Objects.requireNonNull(y, "y is null");
		return this;
	}
	public static final Coordinate validate(Coordinate coordinate) {
		Objects.requireNonNull(coordinate, "The coordinate is null");
		return coordinate.validate();
	}
	public int compareTo(Coordinate c) {
		int result = this.x.compareTo(c.x());
		
		return result != 0 ? result : this.y.compareTo(c.y());
	}
	
	public String toSimpleString() {
		return "coordinate x: " + x + " y: " + y;
	}
}
