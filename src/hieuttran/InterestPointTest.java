package hieuttran;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


public class InterestPointTest {
	Coordinate c = new Coordinate(new BigDecimal(1), new BigDecimal(1));
	Coordinate d = new Coordinate(new BigDecimal(2), new BigDecimal(5));
	Coordinate e = new Coordinate(new BigDecimal(0.5), new BigDecimal(0.5));
	InterestPoint<Marker> i = new InterestPoint(c, Marker.GYM);
	@Test
	public void testValidate() {
		assertEquals(i, i.validate());
	}

	@Test
	public void testX() {
		assertEquals(new BigDecimal(1), c.x());
	}

	@Test
	public void testY() {
		assertEquals(new BigDecimal(1), c.y());
	}

	@Test
	public void testValidateInterestPoint() {
		assertEquals(i, i.validate());
	}

	@Test
	public void testHasMarker() {
		assertEquals(true, i.hasMarker(Marker.GYM));
	}

}
