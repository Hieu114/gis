package hieuttran;
import java.math.BigDecimal;
import java.util.*;

/**
 * Represent a pair (x, y) of horizontal and vertical values denoting the location of a landmark
 */
public record Coordinate(BigDecimal x, BigDecimal y) implements Comparable<Coordinate> {
	/**
	 * The origin of the 2D map, located at (0, 0)
	 */
	public  static  final  Coordinate  ORIGIN = new Coordinate(new BigDecimal(0), new BigDecimal(0));

	/**
	 * check if the coordinate is valid
	 * @return the coordinate itself if valid
	 * @throws NullPointerException if the coordinate is not valid
	 */
	public  final  Coordinate validate() {
		Objects.requireNonNull(x, "x is null");
		Objects.requireNonNull(y, "y is null");
		return this;
	}

	/**
	 * check if the input coordinate is valid
	 * @param coordinate Given coordinate
	 * @return the coordinate itself if valid
	 */
	public static final Coordinate validate(Coordinate coordinate) {
		Objects.requireNonNull(coordinate, "The coordinate is null");
		return coordinate.validate();
	}

	/**
	 * compare to another coordinate
	 * @param c coordinate need comparing to
	 * @return an integer showing if the coordinate is bigger than the compared coordinate
	 * positive value if it is bigger
	 * 0 if 2 values are equal
	 * negative value if it is smaller
	 */
	public int compareTo(Coordinate c) {
		int result = this.x.compareTo(c.x());
		
		return result != 0 ? result : this.y.compareTo(c.y());
	}

	/**
	 * get String representation of the coordinate
	 * @return String representation of the coordinate
	 */
	public String toSimpleString() {
		return "coordinate x: " + x + " y: " + y;
	}
}
