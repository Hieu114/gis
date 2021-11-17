package hieuttran;

import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CoordinateTest {
	
	Coordinate c = new Coordinate(new BigDecimal(1), new BigDecimal(1));
	Coordinate d = new Coordinate(new BigDecimal(2), new BigDecimal(5));
	Coordinate e = new Coordinate(new BigDecimal(0.5), new BigDecimal(0.5));
	@Test
	public void testValidate() {
		assertEquals(c, c.validate());
	}

	@Test
	public void testValidateCoordinate() {
		assertEquals(c, c.validate(c));
	}

	@Test
	public void testCompareTo() {
		System.out.println("<");
		assertEquals(-1, c.compareTo(new Coordinate(new BigDecimal(4), new BigDecimal(-1))));
		System.out.print(">");
		assertEquals(1, c.compareTo(new Coordinate(new BigDecimal(1), new BigDecimal(0))));
		System.out.print("=");
		assertEquals(0, c.compareTo(new Coordinate(new BigDecimal(1), new BigDecimal(1))));
	}

}


