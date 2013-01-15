package ro.mps.data.api;

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
    
    /**
     * 
     * Checks if the other element doesn't overlap the current one
     * 
     * @param p
     * @return
     */
    boolean clears(HasPosition p);
    
    void setWidth(int width);
    void setHeight(int height);
    
    void setLeftUpperCornerX(int x);
    void setLeftUpperCornerY(int y);
    
    public void incHeight();
    
    public void incWidth();
    
    public void decHeight();
    
    public void decWidth();
    
    public void down();

    public void up();

	public void left();

	public void right();

}
