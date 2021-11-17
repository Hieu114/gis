package hieuttran;

import java.math.BigDecimal;
import java.util.Objects;

public record Rectangle(Coordinate bottomLeft, Coordinate topRight) {
	public final Rectangle validate() {
		if (bottomLeft.validate().x().compareTo(topRight.validate().x()) < 0 &&
			bottomLeft.y().compareTo(topRight.y()) < 0)
			return this;
		throw new IllegalArgumentException();
	}
	public static final Rectangle validate(Rectangle rectangle) {
		Objects.requireNonNull(rectangle);
		return rectangle.validate();
	}
	
	public final BigDecimal left() {
		return bottomLeft().x();
	}
	public final BigDecimal right() {
		return topRight().x();
	}
	public final BigDecimal top() {
		return topRight().y();
	}
	public final BigDecimal bottom() {
		return bottomLeft().y();
	}
	

}
