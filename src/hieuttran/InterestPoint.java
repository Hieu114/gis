package hieuttran;
import java.math.BigDecimal;
import java.util.*;

/**
 * Landmark whose information is recorded in GIS with marker M and located in the input coordinate
 * @param <M> input marker
 * @param coordinate input coordinate
 */
public record InterestPoint<M>(Coordinate coordinate, M marker) {
	/**
	 * Validate the interest point
	 * @return true if the interest point's coordinates and itself are not null, false otherwise
	 */
	public final InterestPoint<M> validate() {
		Objects.requireNonNull(marker);
		coordinate.validate();
		return this;
	}

	/**
	 * get the x-coordinate of the coordinate
	 * @return BigInteger representing the x-coordinate of the interest point
	 * @throws NullPointerException if the input coordinate is not valid
	 */
	public BigDecimal x() {
		return coordinate.validate().x();
	}
	/**
	 * get the y-coordinate of the coordinate
	 * @return BigInteger representing the y-coordinate of the interest point
	 * @throws NullPointerException if the input coordinate is not valid
	 */
	public BigDecimal y() {
		return coordinate.validate().y();
	}

	/**
	 * validate the interest point
	 * @param interestPoint InterestPoint need validating
	 * @return the interest point itself if valid
	 * @throws NullPointerException if the input interest point is not valid
	 */
	public static final InterestPoint validate(InterestPoint interestPoint) {
		Objects.requireNonNull(interestPoint);
		return interestPoint.validate();
	}

	/**
	 * check if the interest point has the input marker
	 * @param marker given marker
	 * @return true if the interest point has the input marker, otherwise false
	 */
	public boolean hasMarker(M marker) {
		Objects.requireNonNull(marker);
		return marker().equals(marker);
	}

	/**
	 * print the information of the interest point under String
	 * @return String representation of the interest point
	 */
	public String toString() {
		return "interest point x: "+coordinate.x() + " y: " + coordinate.y() +" marker: " + marker;
	}
	
	
}
