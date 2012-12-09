package ro.mps.data.api;

import java.awt.Point;

/**
 * Interface implemented by node classes that can change position
 * 
 * @author radu
 *
 */
public interface Moveable {

	/**
	 * Moves current node by deltaX pixels on the X axis, and deltaY pixels on the Y axis  
	 * @param deltaX
	 * @param deltaY
	 */
	public abstract void move(int deltaX, int deltaY);

	/**
	 * Sets the upper left corner position
	 * @param x
	 * @param y
	 */
	public abstract void setUpperLeftCorner(int x, int y);

	public abstract void setUpperLeftCorner(Point p);

}