package hieuttran;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
public final class BiDimensionalMap<T> {
	private final SortedMap<BigDecimal, SortedMap<BigDecimal, Collection<T>>> points = new TreeMap();
	
	BiDimensionalMap(){}
	
	BiDimensionalMap(Collection<BigDecimal> xCoord,  Collection<BigDecimal> yCoord){
		for (BigDecimal x : xCoord) {
			for (BigDecimal y : yCoord) {
				getUpdater().setX(x).setY(y).add();
			}
		}
	}
	
	public final void addEverywhere(T value) {
		Collection<T> c = new HashSet<>();
		c.add(value);
		for (Coordinate coordinate : coordinateSet()) {
			getUpdater().setX(coordinate.x()).setY(coordinate.y()).setValues(c).add();
		}
	}
	
	public final Collection<T> get(BigDecimal x, BigDecimal y){
		if (points.get(x) == null) {
			return null;
		}
		return points.get(x).get(y);
	}
	
	public final Collection<T> get(Coordinate coordinate){
		return get(coordinate.x(), coordinate.y());
	}
	
	public   final   Updater getUpdater() {
		return new Updater();
	}
	
	public final class Updater {
		private BigDecimal x = new BigDecimal(0);
		private BigDecimal y = new BigDecimal(0);
		private Supplier<Collection<T>> collectionFactory = HashSet<T>::new;
		private Collection<T>  values = collectionFactory.get();
		
		public final Updater setX(BigDecimal b) {
			x = b;
			return this;
		}
		public final Updater setY(BigDecimal b) {
			y = b;
			return this;
		}
		public final Updater setCollectionFactory(Supplier<Collection<T>> c) {
			collectionFactory = c;
			return this;
		}
		public final Updater setValues(Collection<T> c) {
			values = c;
			return this;
		}
		
		public  final  Updater setCoordinate(Coordinate coordinate) {
			x = coordinate.x();
			y = coordinate.y();
			return this;
		}
		public final Updater addValue(T value) {
			values.add(value);
			return this;
		}
		
		public  final  Collection<T> set(){
			points.computeIfAbsent(x, t -> new TreeMap<BigDecimal, Collection<T>>());
			return points.get(x).put(y, values);
		}
		
		public final boolean add() {
			if (!points.containsKey(x)) {
				points.put(x, new TreeMap<BigDecimal, Collection<T>>());
				
			}
			if (!points.get(x).containsKey(y)) {
				points.get(x).put(y, collectionFactory.get());
			}	
			return points.get(x).get(y).addAll(values);
		}	
	}
	
	public final Set<BigDecimal> xSet() {
		return points.keySet();
	}
	
	public final Set<BigDecimal> ySet(BigDecimal x){
		if (!points.containsKey(x))
			return null;
		return points.get(x).keySet();
	}
	
	public final List<Coordinate> coordinateSet(){
		List coordinates = new ArrayList<Coordinate>();
		for (BigDecimal x : xSet()) {
			for (BigDecimal y : ySet(x)) {
				coordinates.add(new Coordinate(x,y));
			}
		}
		return coordinates;
	}
	public final List<Collection<T>> collectionList(){
		List entries = new ArrayList<Collection<T>>();
		for (BigDecimal x : xSet()) {
			for (Collection<T> t : points.get(x).values()) {
				entries.add(t);
			}
		}
		return entries;
	}
	public final long collectionSize() {
		return collectionSize(a -> true);
	}
	public final long collectionSize(Predicate<? super T> filter) {
		int count = 0;
		for (Collection<T> c : collectionList()) {
			for (T t : c) {
				if (filter.test(t))
					count++;
			}
		}
		return count;
	}
	
	public String toString() {
		return "Map with " + collectionSize() + " entries and " + coordinateSet().size()+" coordinates";
	}
	
	public final BiDimensionalMap<T> slice(Rectangle rectangle) {
		BiDimensionalMap<T> ans = new BiDimensionalMap<>();
		ans.points.putAll(points.subMap(rectangle.left(), rectangle.right()));
		for (BigDecimal x : ans.points.keySet()) {
			ans.points.put(x, ans.points.get(x).subMap(rectangle.bottom(), rectangle.top()));
		}
		return ans;
	}
	
}
