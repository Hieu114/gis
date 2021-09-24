package hieuttran;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class testBush {
	
	Coordinate c = new Coordinate(new BigDecimal(1), new BigDecimal(1));
	Coordinate d = new Coordinate(new BigDecimal(2), new BigDecimal(5));
	Coordinate e = new Coordinate(new BigDecimal(0.5), new BigDecimal(0.5));
	@Test
	void testValidate() {
		assertEquals(c, c.validate());
	}

	@Test
	void testValidateCoordinate() {
		assertEquals(c, c.validate(c));
	}

	@Test
	void testCompareTo() {
		System.out.println("<");
		assertEquals(-1, c.compareTo(new Coordinate(new BigDecimal(4), new BigDecimal(-1))));
		System.out.print(">");
		assertEquals(1, c.compareTo(new Coordinate(new BigDecimal(1), new BigDecimal(0))));
		System.out.print("=");
		assertEquals(0, c.compareTo(new Coordinate(new BigDecimal(1), new BigDecimal(1))));
	}

//	@Test
//	void testToSimpleString() {
//		assertEquals()
//	}

}


