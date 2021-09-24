package hieuttran;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

class testBiMap {
	
	@Test
	void testSet() {
		BigDecimal two = new BigDecimal(2);
		BigDecimal three = new BigDecimal(3);
		BigDecimal four = new BigDecimal(4);
		BigDecimal five = new BigDecimal(5);
		BiDimensionalMap map = new BiDimensionalMap<InterestPoint>();
		BiDimensionalMap.Updater updater = map.getUpdater();
		updater.setX(two).setY(two);
		Coordinate c = new Coordinate(two, two);
		List<InterestPoint<Marker>> list1 = new ArrayList<InterestPoint<Marker>>();
		list1.add(new InterestPoint(c, Marker.GYM));
		list1.add(new InterestPoint(c, Marker.RESIDENCE_HALL));
		updater.setValues(list1);
		updater.set();
//		System.out.print(updater.set()+"\n");
		assertEquals(list1, map.get(two, two),"get() method");
		
//		System.out.print(updater.set()+"\n");
//		
//		System.out.print(map.get(new BigDecimal(1), new BigDecimal(1))+"\n");
//		System.out.print(map.xSet()+"\n");
//		System.out.print(map.ySet(new BigDecimal(1))+"\n");
		assertEquals(list1, updater.set(), "set() method, return the previous value");
		Set<BigDecimal> l = new TreeSet();
		l.add(two);
		assertEquals(l, map.xSet(), "xSet() method");
		assertEquals(l, map.ySet(two), "ySet() method");
		
		Coordinate f = new Coordinate(three, five);
		List<InterestPoint<Marker>> list2 = new ArrayList<InterestPoint<Marker>>();
		list2.add(new InterestPoint(f, Marker.CLASSROOM));
		list2.add(new InterestPoint(f, Marker.RESIDENCE_HALL));
		updater.setValues(list2);
		updater.setX(three).setY(five);
		updater.setValues(list2);
		updater.set();
		
		updater.setX(three).setY(four);
		Coordinate e = new Coordinate(three, four);
		List<InterestPoint<Marker>> list3 = new ArrayList<InterestPoint<Marker>>();
		list3.add(new InterestPoint(e, Marker.CLASSROOM));
		updater.setValues(list3);
		updater.set();
		
		
		List<Coordinate> listCoordinate = new ArrayList<Coordinate>();
		listCoordinate.add(c);
		listCoordinate.add(e);
		listCoordinate.add(f);
		assertEquals(listCoordinate, map.coordinateSet(), "coordinateSet() method");
		
		List<Collection<InterestPoint<Marker>>> listCollection = new ArrayList();
		listCollection.add(list1);
		listCollection.add(list3);
		listCollection.add(list2);	
		assertEquals(listCollection, map.collectionList(), "collectionList() method");
		
		long i = 2L;
		Predicate<InterestPoint<Marker>> pr = t -> (t.marker() == Marker.CLASSROOM);
		assertEquals(i, map.collectionSize(pr), "filter method");
		
		Rectangle rec = new Rectangle(c, f);
		BiDimensionalMap ans = new BiDimensionalMap<InterestPoint>();
		List<Coordinate> listSlice = new ArrayList();
		listSlice.add(c);
		assertEquals(listSlice, map.slice(rec).coordinateSet(), "slice()");
	}
	

}
