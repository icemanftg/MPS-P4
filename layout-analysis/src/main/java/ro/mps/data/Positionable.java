package ro.mps.data;

import java.awt.Point;

public interface Positionable {

	Point getLeftUpperCorner();
	
	/**
	 * Get the X coordinate of the upper left corner
	 * @return
	 */
	int getLeftUpperCornerX();
	
	/**
	 * Get the Y coordinate of the upper left corner
	 * @return
	 */
	int getRightUpperCornerY();
	
	/**
	 * Get the width of the element
	 * @return
	 */
	int getWidth();
	
	/**
	 * Get the height of the element
	 * @return
	 */
	int getHeight();
	
	/**
	 * Check if point inside the current element
	 * @param p
	 * @return
	 */
	boolean inside(Point p);
	boolean inside(int x,int y);
	
}
