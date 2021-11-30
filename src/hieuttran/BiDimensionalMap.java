package hieuttran;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

/** A 2D map maintaining information about multiple landmarks in geographical area
 * @author Hieu Tran
 */
public final class BiDimensionalMap<T> {
	/** a sortedmap of a sortedmap stores the landmarks in the map
	 * the top level sortedmap holds the x value and the other one stores the y value of the landmark
	 */
	private final SortedMap<BigDecimal, SortedMap<BigDecimal, Collection<T>>> points = new TreeMap<>();

	/**
	 * Default BiDimensionalMap constructor
	 */
	BiDimensionalMap(){}

	/**
	 * Create a 2D map with every possible combination from the given x list and the y list
	 * @param xCoord x-coordinate list
	 * @param yCoord y-coordinate list
	 */
	BiDimensionalMap(Collection<BigDecimal> xCoord,  Collection<BigDecimal> yCoord){
		//consider every permutation of x in x-list and y in y-list and add the new map
		for (BigDecimal x : xCoord) {
			for (BigDecimal y : yCoord) {
				getUpdater().setX(x).setY(y).add();
			}
		}
	}

	/**
	 * Add the input landmark to every coordinate in the map
	 * @param value given landmark
	 */
	public final void addEverywhere(T value) {
		Collection<T> c = new HashSet<>();
		c.add(value);
		//add T to every existing coordinate in the map
		for (Coordinate coordinate : coordinateSet()) {
			getUpdater().setX(coordinate.x()).setY(coordinate.y()).setValues(c).add();
		}
	}

	/**
	 * gets the object stored at input coordinate (x, y) from this map
	 * @param x Given x-coordinate
	 * @param y Given y-coordinate
	 * @return A collection of landmarks in the location (x, y)
	 */
	public final Collection<T> get(BigDecimal x, BigDecimal y){
		//if there is no given x on the map, return null
		if (points.get(x) == null) {
			return null;
		}
		return points.get(x).get(y);
	}

	/**
	 * gets the object stored at input coordinate from this map
	 * @param coordinate Given coordinate
	 * @return A collection of landmarks in the given coordinate
	 */
	public final Collection<T> get(Coordinate coordinate){
		return get(coordinate.x(), coordinate.y());
	}

	/**
	 * invoke a new updater to update the map
	 * @return new Updater to update the map
	 */
	public   final   Updater getUpdater() {
		return new Updater();
	}

	/**
	 * Helper class to add landmark(s) to the map
	 */
	public final class Updater {
		private BigDecimal x = new BigDecimal(0); // default given x-coordinate
		private BigDecimal y = new BigDecimal(0); // default given y-coordinate
		private Supplier<Collection<T>> collectionFactory = HashSet<T>::new; //initial instance of collection stored at (x, y) location
		private Collection<T>  values = collectionFactory.get(); //values that will be added to the map

		/**set to the designated x-coordinate
		 * @param newX the designated
		 * @return the Updater itself
		 */
		public final Updater setX(BigDecimal newX) {
			x = newX;
			return this;
		}
		/**set to the designated x-coordinate
		 * @param newY the designated y
		 * @return the Updater itself
		 */
		public final Updater setY(BigDecimal newY) {
			y = newY;
			return this;
		}
		/**set to the designated collection factory
		 * @param c the designated collection factory
		 * @return the Updater itself
		 */
		public final Updater setCollectionFactory(Supplier<Collection<T>> c) {
			collectionFactory = c;
			return this;
		}
		/**set to the designated landmarks will be added to the map
		 * @param c the landmarks will be added to the map
		 * @return the Updater itself
		 */
		public final Updater setValues(Collection<T> c) {
			values = c;
			return this;
		}
		/**set to the input coordinate that will be added to
		 * @param coordinate the designated coordinate will be added to
		 * @return the Updater itself
		 */
		public  final  Updater setCoordinate(Coordinate coordinate) {
			x = coordinate.x();
			y = coordinate.y();
			return this;
		}

		/**
		 * Add the input landmark to the designated coordinate
		 * @param value Given landmark
		 * @return the Updater itself
		 */
		public final Updater addValue(T value) {
			values.add(value);
			return this;
		}

		/**
		 * replace the information at (x, y) with input landmarks
		 * @return A collection of previous landmarks at (x, y) or null if there is not any (x, y)
		 */
		public  final  Collection<T> set(){
			return points.computeIfAbsent(x, t -> new TreeMap<BigDecimal, Collection<T>>()).put(y, values);
		}

		/**
		 * add the given landmarks to given location (x, y)
		 * @return true if the location (x, y) has changed, otherwise false
		 */
		public final boolean add() {
			return points.computeIfAbsent(x, t -> new TreeMap<BigDecimal, Collection<T>>()).computeIfAbsent(y, t -> collectionFactory.get()).addAll(values);
		}	
	}

	/**
	 * get a set of all x-coordinates in the map
	 * @return a set of BigDecimal representing x-coordinates in the map
	 */
	public final Set<BigDecimal> xSet() {
		return points.keySet();
	}
	/**
	 * get a set of y-coordinate corresponds to the input x
	 * @param x valid x-coordinate containing the information needed
	 * @return a set of BigDecimal representing y-coordinates in the map
	 */
	public final Set<BigDecimal> ySet(BigDecimal x){
		if (!points.containsKey(x))
			return null;
		return points.get(x).keySet();
	}

	/**
	 * create a list of all coordinates in the map
	 * @return a list of all coordinates in the map
	 */
	public final List<Coordinate> coordinateSet(){
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		// add every coordinate of every combination between x in xSet and y in ySet
		for (BigDecimal x : xSet()) {
			for (BigDecimal y : ySet(x)) {
				coordinates.add(new Coordinate(x,y));
			}
		}
		return coordinates;
	}

	/**
	 * get a list of all collections in the map
	 * @return a list of all collections from every point in the map
	 */
	public final List<Collection<T>> collectionList(){
		List<Collection<T>> entries = new ArrayList<Collection<T>>();
		//add the collection in every point in the map to the list
		for (BigDecimal x : xSet()) {
			for (Collection<T> t : points.get(x).values()) {
				entries.add(t);
			}
		}
		return entries;
	}

	/**
	 * count landmarks in the map
	 * @return the number of landmarks in the map
	 */
	public final long collectionSize() {
		//count every element in the collection list
		return collectionSize(a -> true);
	}

	/**
	 * count landmarks that satisfied the input predicate
	 * @param filter Given condition
	 * @return the number of landmarks satisfied the given condition
	 */
	public final long collectionSize(Predicate<? super T> filter) {
		int count = 0;
		//for each collection in the map
		for (Collection<T> c : collectionList()) {
			//For each element in the collection
			for (T t : c) {
				//count 1 if the element satisfied the given condition
				if (filter.test(t))
					count++;
			}
		}
		return count;
	}

	/**
	 * Get the String representation of the 2D map
	 * @return String representation of the 2D map
	 */
	public String toString() {
		return "Map with " + collectionSize() + " entries and " + coordinateSet().size()+" coordinates";
	}

	/**
	 * Remove all the points in the map that is not in the input rectangle
	 * @param rectangle given rectangle
	 * @return a BiDimensionalMap containing only the points in the given rectangle
	 */
	public final BiDimensionalMap<T> slice(Rectangle rectangle) {
		BiDimensionalMap<T> ans = new BiDimensionalMap<>();
		//add all the points in the area limited by the left side and the right side of the given rectangle
		ans.points.putAll(points.subMap(rectangle.left(), rectangle.right()));
		//filter the points in the return value to points limited by the top and the bottom coordinates
		for (BigDecimal x : ans.points.keySet()) {
			ans.points.put(x, ans.points.get(x).subMap(rectangle.bottom(), rectangle.top()));
		}
		return ans;
	}
	
}
