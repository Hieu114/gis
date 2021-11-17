package hieuttran;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public final class RectilinearRegion {
	private final Set<Rectangle> rectangles = new HashSet<>();
	public Set<Rectangle> getRecs(){
		return rectangles;
	}
	private RectilinearRegion(Set<Rectangle> rectangles) {
		this.rectangles.addAll(rectangles);
	}
	
	private Set<BigDecimal> xCoord(){
		Set<BigDecimal> xCoord = new HashSet<>();
		for (Rectangle rec : getRecs()) {
			xCoord.add(rec.left());
			xCoord.add(rec.right());
		}
		return xCoord;
	}
	
	private Set<BigDecimal> yCoord(){
		Set<BigDecimal> yCoord = new HashSet<>();
		for (Rectangle rec : getRecs()) {
			yCoord.add(rec.top());
			yCoord.add(rec.bottom());
		}
		return yCoord;
	}
	
	public BiDimensionalMap<Rectangle> rectanglesMap(){
		BiDimensionalMap<Rectangle> grid = new BiDimensionalMap<Rectangle>(xCoord(), yCoord()); 
		for (Rectangle rec : getRecs()) {
			grid.slice(rec).addEverywhere(rec);
		}
		return grid;
	}
	
	private boolean isOverlap(Rectangle r1, Rectangle r2) {
		return !r1.equals(r2) && 
				r2.bottomLeft().compareTo(r1.bottomLeft()) >= 0 && 
				r2.bottomLeft().compareTo(r1.topRight()) < 0;
	}
	
	public boolean isOverlapping() {
		for (Rectangle r : getRecs()) {
			if (!getRecs().stream().noneMatch(rec -> isOverlap(r, rec)))
				return true;
		}
		return false;
	}
	
	public  static  final RectilinearRegion of(Set<Rectangle> rectangles) {
		RectilinearRegion r = new RectilinearRegion(rectangles);
		if (!(Objects.isNull(rectangles) && !r.isOverlapping()))
			return r;
		throw new IllegalArgumentException();
	}

	private boolean areConnected(Rectangle rec1, Rectangle rec2){
		return rec1.validate().left().compareTo(rec2.validate().right()) == 0 || //left side of the first rectangle touch the right side of the second rectangle
				rec1.right().compareTo(rec2.left()) == 0 || //right side of the first rectangle touch the left side of the second rectangle
				rec1.top().compareTo(rec2.bottom()) == 0 || //top side of the first rectangle touch the bottom side of the second rectangle
				rec1.bottom().compareTo(rec2.top()) == 0; //bottom side of the first rectangle touch the top side of the second rectangle
	}

	/* no parameter
	@return whether the region is connected
	* */
	boolean isConnected(){
		//initialize the set of visited rectangles with the starting element is the first rectangle.
		Set<Rectangle> visitedRectangles = new HashSet();
		//initialize the list of unvisited rectangles with the starting element is the first rectangle.
		Queue<Rectangle> unvisitedRectangles = new LinkedList<>();
		if (rectangles.isEmpty())
			return true;
		Rectangle initialRect = (Rectangle) rectangles.toArray()[0];
		visitedRectangles.add(initialRect);
		unvisitedRectangles.add(initialRect);
		//while (the unvisited list is not empty)
		while (unvisitedRectangles.size() != 0){
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
