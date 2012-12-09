package ro.mps.data;

import java.awt.Point;

/**
 * Generic class for layout elements.
 * 
 * @author radu
 *
 */
public abstract class Node implements Positionable, Resizable {

	private int x,y;
	private int width, height;
	
	/**
	 * Constructor that sets position coordinates 
	 * 
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 */
	public Node(int x, int y, int height, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
	public int getRightUpperCornerY() {
		return y;
	}
	
	@Override
	public Point getLeftUpperCorner() {
		return new Point(x,y);
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
	
}