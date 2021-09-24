package hieuttran;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class testInterestPoint {
	Coordinate c = new Coordinate(new BigDecimal(1), new BigDecimal(1));
	Coordinate d = new Coordinate(new BigDecimal(2), new BigDecimal(5));
	Coordinate e = new Coordinate(new BigDecimal(0.5), new BigDecimal(0.5));
	InterestPoint<Marker> i = new InterestPoint(c, Marker.GYM);
	@Test
	void testValidate() {
		assertEquals(i, i.validate());
	}

	@Test
	void testX() {
		assertEquals(new BigDecimal(1), c.x());
	}

	@Test
	void testY() {
		assertEquals(new BigDecimal(1), c.y());
	}

	@Test
	void testValidateInterestPoint() {
		assertEquals(i, i.validate());
	}

	@Test
	void testHasMarker() {
		assertEquals(true, i.hasMarker(Marker.GYM));
	}

//	@Test
//	void testToString() {
//		fail("Not yet implemented");
//	}

}
