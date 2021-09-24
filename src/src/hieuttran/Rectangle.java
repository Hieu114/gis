package hieuttran;

import java.math.BigDecimal;
import java.util.Objects;

public record Rectangle(Coordinate bottomLeft, Coordinate topRight) {
	public final Rectangle validate() {
		if (bottomLeft.validate().compareTo(topRight.validate()) >= 0)
			throw new IllegalArgumentException();
		return this;
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
	
//	public static void main (String[] args){
//		BigDecimal two = new BigDecimal(2);
//		BigDecimal three = new BigDecimal(3);
//		BigDecimal four = new BigDecimal(4);
//		BigDecimal five = new BigDecimal(5);
//		Rectangle rec = new Rectangle(new Coordinate(three, three), new Coordinate(four, five));
//		System.out.print(rec.validate().top());
//	}
}
