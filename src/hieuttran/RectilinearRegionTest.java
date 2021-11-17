package hieuttran;

import org.junit.Test;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RectilinearRegionTest {
    @Test
    public void testAreConnected(){
        RectilinearRegion region =
        RectilinearRegion.of(Set.of(new Rectangle(new Coordinate(new BigDecimal(2), new BigDecimal(2)),
                                                  new Coordinate(new BigDecimal(3), new BigDecimal(4))),
                                    new Rectangle(new Coordinate(new BigDecimal(3), new BigDecimal(2)),
                                                  new Coordinate(new BigDecimal(5), new BigDecimal(3))),
                new Rectangle(new Coordinate(new BigDecimal(2), new BigDecimal(4)),
                        new Coordinate(new BigDecimal(3), new BigDecimal(5))),
                new Rectangle(new Coordinate(new BigDecimal(4), new BigDecimal(1)),
                        new Coordinate(new BigDecimal(6), new BigDecimal(2)))));
        assertEquals(true, region.isConnected());
        RectilinearRegion region2 = RectilinearRegion.of(new HashSet<>());
        assertEquals(true, region2.isConnected());
        RectilinearRegion region3 =
        RectilinearRegion.of(Set.of(new Rectangle(new Coordinate(new BigDecimal(1), new BigDecimal(2)),
                                                  new Coordinate(new BigDecimal(2), new BigDecimal(4))),
                                    new Rectangle(new Coordinate(new BigDecimal(2), new BigDecimal(2)),
                                                  new Coordinate(new BigDecimal(3), new BigDecimal(3))),
                                    new Rectangle(new Coordinate(new BigDecimal(8), new BigDecimal(8)),
                                                  new Coordinate(new BigDecimal(9), new BigDecimal(9)))));
        assertEquals(false, region3.isConnected());
        RectilinearRegion region4 =
                RectilinearRegion.of(Set.of(new Rectangle(new Coordinate(new BigDecimal(3), new BigDecimal(3)),
                                new Coordinate(new BigDecimal(4), new BigDecimal(4))),
                        new Rectangle(new Coordinate(new BigDecimal(2), new BigDecimal(3)),
                                new Coordinate(new BigDecimal(3), new BigDecimal(5)))));
        assertEquals(true, region4.isConnected());
        RectilinearRegion region5 =
                RectilinearRegion.of(Set.of(new Rectangle(new Coordinate(new BigDecimal(2), new BigDecimal(3)),
                                new Coordinate(new BigDecimal(4), new BigDecimal(4))),
                        new Rectangle(new Coordinate(new BigDecimal(2), new BigDecimal(2)),
                                new Coordinate(new BigDecimal(5), new BigDecimal(3)))));
        assertEquals(true, region5.isConnected());
    }

    //Stress test
    @Test
    public void stressTest(){
        Set<Rectangle> connectRectangles = new HashSet<>();
        Set<Rectangle> disconnectRectangles = new HashSet<>();
        //should be 1000000 but my computer cannot run
        for (int i = 0; i < 100; i = i + 2){
            connectRectangles.add(new Rectangle(new Coordinate(new BigDecimal(i), new BigDecimal(0)),
                                         new Coordinate(new BigDecimal(i + 2), new BigDecimal(5))));
            disconnectRectangles.add(new Rectangle(new Coordinate(new BigDecimal(i), new BigDecimal(2)),
                    new Coordinate(new BigDecimal(i + 1), new BigDecimal(5))));
        }
        assertTrue(RectilinearRegion.of(connectRectangles).isConnected());
        assertFalse(RectilinearRegion.of(disconnectRectangles).isConnected());
    }
}
