package hieuttran;
import java.math.BigDecimal;
import java.util.*;
public record InterestPoint<M>(Coordinate coordinate, M marker) {
	public final InterestPoint<M> validate() {
		Objects.requireNonNull(marker);
		coordinate.validate();
		return this;
	}
	
	public BigDecimal x() {
		return coordinate.validate().x();
	}
	
	public BigDecimal y() {
		return coordinate.validate().y();
	}
	
	public static final InterestPoint validate(InterestPoint interestPoint) {
		Objects.requireNonNull(interestPoint);
		return interestPoint.validate();
	}
	
	public boolean hasMarker(M marker) {
		Objects.requireNonNull(marker);
		return marker().equals(marker);
	}
	
	public String toString() {
		return "interest point x: "+coordinate.x() + " y: " + coordinate.y() +" marker: " + marker;
	}
	
	
}
