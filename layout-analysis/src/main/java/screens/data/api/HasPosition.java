package screens.data.api;

import java.awt.*;

/**
 * Interface implemented by classes that occupy space and a position in the document
 *
 * @author radu
 */
public interface HasPosition {

    /**
     * Get the origin (upper left corner) of the node
     *
     * @return
     */
    Point getLeftUpperCorner();

    /**
     * Get the X coordinate of the upper left corner
     *
     * @return
     */
    int getLeftUpperCornerX();

    /**
     * Get the Y coordinate of the upper left corner
     *
     * @return
     */
    int getLeftUpperCornerY();

    /**
     * Get the width of the element
     *
     * @return
     */
    int getWidth();

    /**
     * Get the height of the element
     *
     * @return
     */
    int getHeight();

    /**
     * Check if point inside the current element
     *
     * @param p
     * @return
     */
    boolean inside(Point p);

    boolean inside(int x, int y);

    /**
     * Checks if this positionable contains the param positionable
     *
     * @param p
     * @return
     */
    boolean contains(HasPosition p);

    /**
     * Checks if current positionable is within the boundries of the param positionable
     *
     * @param p
     * @return
     */
    boolean isContainedBy(HasPosition p);

}
