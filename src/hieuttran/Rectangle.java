package hieuttran;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A rectangle represents an area within the map, which will often be queried for points within the rectangle.
 */
public record Rectangle(Coordinate bottomLeft, Coordinate topRight) {
	/**
	 * validate the rectangle
	 * @return true if either the bottom left is to the
	 * left  of  the  top  right  or  the  two  coordinates  lies  at  same
	 * horizontal value but bottom left is below the top right, false otherwise
	 * @throws IllegalArgumentException if the coordinates of the bottom left are bigger than top right's respectively
	 */
	public final Rectangle validate() {
		if (bottomLeft.validate().x().compareTo(topRight.validate().x()) < 0 &&
			bottomLeft.y().compareTo(topRight.y()) < 0)
			return this;
		throw new IllegalArgumentException();
	}

	/**
	 * validate the input rectangle
	 * @param rectangle a rectangle needs validating
	 * @return true if the rectangle's coordinates and itself are not null, false otherwise
	 */
	public static final Rectangle validate(Rectangle rectangle) {
		Objects.requireNonNull(rectangle);
		return rectangle.validate();
	}

	/**
	 * Get the left side's coordinate of the rectangle
	 * @return BigInteger representing left side's coordinate of the rectangle
	 */
	public final BigDecimal left() {
		return bottomLeft().x();
	}
	/**
	 * Get the right side's coordinate of the rectangle
	 * @return BigInteger representing right side's coordinate of the rectangle
	 */
	public final BigDecimal right() {
		return topRight().x();
	}
	/**
	 * Get the top side's coordinate of the rectangle
	 * @return BigInteger representing top side's coordinate of the rectangle
	 */
	public final BigDecimal top() {
		return topRight().y();
	}
	/**
	 * Get the bottom side's coordinate of the rectangle
	 * @return BigInteger representing bottom side's coordinate of the rectangle
	 */
	public final BigDecimal bottom() {
		return bottomLeft().y();
	}
	

}
