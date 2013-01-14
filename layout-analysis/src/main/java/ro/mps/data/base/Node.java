package ro.mps.data.base;

import ro.mps.data.api.HasLabel;
import ro.mps.data.api.HasPosition;
import ro.mps.data.api.Moveable;
import ro.mps.data.api.Resizable;

import java.awt.*;

/**
 * Generic class for layout elements.
 *
 * @author radu
 */
public abstract class Node implements HasPosition, Resizable, Moveable, HasLabel {

    private int x, y;
    private int width, height;
    protected String label;

	/**
     * Default constructor - only used by ComposedBlock
     */
    public Node() {

    }

    /**
     * Constructor that sets position coordinates
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public Node(String label, int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getLeftUpperCornerX() {
        return x;
    }

    @Override
    public int getLeftUpperCornerY() {
        return y;
    }

    @Override
    public Point getLeftUpperCorner() {
        return new Point(x, y);
    }

    @Override
    public boolean inside(int x, int y) {
        return
                this.x <= x &&
                        this.x + width >= x &&
                        this.y <= y &&
                        this.y + height >= y;
    }

    @Override
    public boolean inside(Point p) {
        return inside(p.x, p.y);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }

    @Override
    public void resize(int deltaH, int deltaW) {
        width += deltaW;
        height += deltaH;
    }

    @Override
    public void setUpperLeftCorner(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setUpperLeftCorner(Point p) {
        setUpperLeftCorner(p.x, p.y);
    }

    @Override
    public boolean contains(HasPosition p) {
        return
                inside(p.getLeftUpperCorner()) &&
                        p.getLeftUpperCornerX() + p.getWidth() < x + width &&
                        p.getLeftUpperCornerY() + p.getHeight() < y + height;
    }

    @Override
    public boolean isContainedBy(HasPosition p) {
        return p.contains(this);
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String newLabel) {
        label = newLabel;
    }
    
    @Override
    public boolean clears(HasPosition p) {
    	//Checks under
    	return (getLeftUpperCornerY() + getHeight() <= p.getLeftUpperCornerY() ||
    	//Checks below
    			getLeftUpperCornerY() >= p.getLeftUpperCornerY() + p.getHeight() ||
    	//Checks right		
    			getLeftUpperCornerX() + getWidth() <= p.getLeftUpperCornerX() ||
    	//Checks left
    	    	getLeftUpperCornerX() >= p.getLeftUpperCornerX() + p.getWidth());
    }
}