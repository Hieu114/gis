package hieuttran;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A 2D map with rectangles
 */
public final class RectilinearRegion {
	private final Set<Rectangle> rectangles = new HashSet<>();

	/**
	 * retrieve the list of rectangles in the map
	 * @return the rectangles in the map
	 */
	public Set<Rectangle> getRecs(){
		return rectangles;
	}

	/**
	 * Build a region based on the input set of rectangles
	 * @param rectangles input set of rectangles
	 */
	private RectilinearRegion(Set<Rectangle> rectangles) {
		this.rectangles.addAll(rectangles);
	}

	/**
	 * retrieve all x-coordinates in the map
	 * @return set of BigDecimal of every x-coordinate in the map
	 */
	private Set<BigDecimal> xCoord(){
		Set<BigDecimal> xCoord = new HashSet<>();
		for (Rectangle rec : getRecs()) {
			xCoord.add(rec.left());
			xCoord.add(rec.right());
		}
		return xCoord;
	}
	/**
	 * retrieve all y-coordinates in the map
	 * @return set of BigDecimal of every y-coordinate in the map
	 */
	private Set<BigDecimal> yCoord(){
		Set<BigDecimal> yCoord = new HashSet<>();
		for (Rectangle rec : getRecs()) {
			yCoord.add(rec.top());
			yCoord.add(rec.bottom());
		}
		return yCoord;
	}

	/**
	 * Create a new BiDimensionalMap from the 2 lists of x-coordinates and y-coordinates
	 * @return a 2D map of rectangles with coordinates at xCoord * yCoord and no content
	 */
	public BiDimensionalMap<Rectangle> rectanglesMap(){
		BiDimensionalMap<Rectangle> grid = new BiDimensionalMap<Rectangle>(xCoord(), yCoord()); 
		for (Rectangle rec : getRecs()) {
			grid.slice(rec).addEverywhere(rec);
		}
		return grid;
	}

	/**
	 * Check if 2 rectangles overlap
	 * @param r1 given rectangle 1
	 * @param r2 given rectangle 2
	 * @return true if the 2 rectangles overlap, false otherwise
	 */
	private boolean isOverlap(Rectangle r1, Rectangle r2) {
		return !r1.equals(r2) && 
				r2.bottomLeft().compareTo(r1.bottomLeft()) >= 0 && 
				r2.bottomLeft().compareTo(r1.topRight()) < 0;
	}

	/**
	 * check if the region has any couple of rectangles overlaps
	 * @return true there is any couple of rectangles in the region overlaps, false otherwise
	 */
	public boolean isOverlapping() {
		for (Rectangle r : getRecs()) {
			if (!getRecs().stream().noneMatch(rec -> isOverlap(r, rec)))
				return true;
		}
		return false;
	}

	/**
	 * Creating new Rectilinear Region from input set of rectangles
	 * @param rectangles input rectangles
	 * @return a Rectilinear Region contains every input rectangle
	 * @throws IllegalArgumentException if the region overlaps
	 */
	public  static  final RectilinearRegion of(Set<Rectangle> rectangles) {
		RectilinearRegion r = new RectilinearRegion(rectangles);
		if (!(Objects.isNull(rectangles) && !r.isOverlapping()))
			return r;
		throw new IllegalArgumentException();
	}

	/**
	 * check if the 2 input rectangles share a vertical side
	 * @param rec1 input rectangle 1
	 * @param rec2 input rectangle 2
	 * @return true if the 2 input rectangles share a vertical side, false otherwise
	 */
	private boolean isShareSideVertical(Rectangle rec1, Rectangle rec2){
		if (rec1.validate().left().compareTo(rec2.validate().right()) == 0 ||
				rec1.right().compareTo(rec2.left()) == 0) {
			if (rec1.validate().top().compareTo(rec2.validate().top()) >= 0) {
				return rec1.bottom().compareTo(rec2.top()) < 0;
			}
			return rec2.bottom().compareTo(rec1.top()) < 0;
		}
		return false;
	}
	/**
	 * check if the 2 input rectangles share a horizontal side
	 * @param rec1 input rectangle 1
	 * @param rec2 input rectangle 2
	 * @return true if the 2 input rectangles share a horizontal side, false otherwise
	 */
	private boolean isShareSideHorizontal(Rectangle rec1, Rectangle rec2){
		if (rec1.top().compareTo(rec2.bottom()) == 0 ||
				rec1.bottom().compareTo(rec2.top()) == 0){
			if (rec1.validate().left().compareTo(rec2.validate().left()) >= 0) {
				return rec1.left().compareTo(rec2.right()) < 0;
			}
			return rec2.left().compareTo(rec1.right()) < 0;
		}
		return false;
	}

	/**
	 * Check if 2 rectangles are connected to each other
	 * @param rec1 input rectangle 1
	 * @param rec2 input rectangle 2
	 * @return true if the 2 input rectangles share a side, false otherwise
	 */
	private boolean areConnected(Rectangle rec1, Rectangle rec2) {
		return isShareSideVertical(rec1, rec2) || isShareSideHorizontal(rec1, rec2);
	}

	/**
	 * Check if the region is connected
	 * @return true if there is any connected rectangles in the region
	 */
	boolean isConnected(){
		//initialize the set of visited rectangles with the starting element is the first rectangle.
		Set<Rectangle> visitedRectangles = new HashSet<>();
		//initialize the list of unvisited rectangles with the starting element is the first rectangle.
		Queue<Rectangle> unvisitedRectangles = new LinkedList<>();
		if (rectangles.isEmpty())
			return true;
		Rectangle initialRect = (Rectangle) rectangles.toArray()[0];
		visitedRectangles.add(initialRect);
		unvisitedRectangles.add(initialRect);
		//while (the unvisited list is not empty)
		while (!unvisitedRectangles.isEmpty()){
			/* assign rtg to the first element in the unvisited list
			remove the first element in the list
			 */
			Rectangle rtg = unvisitedRectangles.poll();
			//for each rectangle in rectangles
			for (Rectangle rectangle : rectangles){
				//if the set of visited rectangles contains rectangle: do nothing, skip
				if (visitedRectangles.contains(rectangle.validate()))
					;
				//else if rtg and rectangle are connected:
				else if (areConnected(rtg.validate(), rectangle.validate())){
					//add rectangle to the visited set
					visitedRectangles.add(rectangle);
					//insert rectangle to the first place of the unvisited list
					unvisitedRectangles.add(rectangle);
				}
			}
		}
		//return (the size of the visited set equals the size of rectangles)
		return visitedRectangles.size() == rectangles.size();
	}
}
