package ro.mps.data;

import java.awt.Point;

public interface Resizable {
	
	/**
	 * Moves current node by deltaX pixels on the X axis, and deltaY pixels on the Y axis  
	 * @param deltaX
	 * @param deltaY
	 */
	void move(int deltaX, int deltaY);
	
	/**
	 * Sets the upper left corner position
	 * @param x
	 * @param y
	 */
	void setUpperLeftCorner(int x, int y);
	void setUpperLeftCorner(Point p);
	
	/**
	 * Resizes the current node by added the provided params to its previous isze
	 * @param deltaH
	 * @param deltaW
	 */
	void resize(int deltaH, int deltaW);
	

}
