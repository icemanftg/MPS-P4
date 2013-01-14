package screens.data.base;

import screens.data.api.HasLabel;
import screens.data.api.HasPosition;
import screens.data.api.Moveable;
import screens.data.api.Resizable;

import java.awt.*;

/**
 * Generic class for layout elements.
 *
 * @author radu
 */
public abstract class Node implements HasPosition, Resizable, Moveable, HasLabel {

    private int x, y;
    private int width, height;
    private String label;

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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
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

    public boolean strictlyInside(int x, int y) {
        return
                this.x < x &&
                this.x + width > x &&
                this.y < y &&
                this.y + height > y;
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
                        p.getLeftUpperCornerX() + p.getWidth() <= x + width &&
                        p.getLeftUpperCornerY() + p.getHeight() <= y + height;
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

    public boolean intersectsAnotherNode(Node node) {
        return
                node.strictlyInside(x, y) ||
                node.strictlyInside(x + width, y) ||
                node.strictlyInside(x, y + height) ||
                node.strictlyInside(x + width, y + height);
    }

    public boolean haveTheSameDimensions(Node node) {
        return
                x == node.getLeftUpperCornerX() &&
                y == node.getLeftUpperCornerY() &&
                width == node.getWidth() &&
                height == node.getHeight();
    }

    public void setDimensionsAfterMerge(Node node) {
        x = Math.min(x, node.getLeftUpperCornerX());
        y = Math.min(y, node.getLeftUpperCornerY());

        width = Math.max(width, node.getWidth());
        height = height + node.getHeight();

        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
}