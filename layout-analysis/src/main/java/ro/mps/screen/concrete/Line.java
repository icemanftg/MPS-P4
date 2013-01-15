package ro.mps.screen.concrete;

import ro.mps.screen.api.Compound;
import ro.mps.screen.api.HasContent;
import ro.mps.screen.api.HasParent;
import ro.mps.screen.base.Node;

public class Line extends Node implements HasContent, HasParent<Line> {

    private Compound<Line> parent;
    private String content;

    public Line(int x, int y, int height, int width) {
        super("line", x, y, height, width);
    }

    public Line(Compound<Line> p, int x, int y, int height, int width) {
        this(x, y, height, width);
        parent = p;
    }

    public Line(String content, Compound<Line> p, int x, int y, int height, int width) {
        this(p, x, y, height, width);
        this.content = content;
    }

    public boolean haveSameParent(Line line) {
        return this.getParent() == line.getParent();
    }

    @Override
    public Compound<Line> getParent() {
        return parent;
    }

    @Override
    public void setParent(Compound<Line> parent) {
        this.parent = parent;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final String TEMPLATE = "\t\t**LINE**\n" +
                "\t\t\tx = %d\n" +
                "\t\t\ty = %d\n" +
                "\t\t\tWidth = %d\n" +
                "\t\t\tHeight = %d\n" +
                "\t\t\tContent = %s\n";

        return String.format(TEMPLATE, getLeftUpperCornerX(), getLeftUpperCornerY(), getHeight(), getWidth(), content);
    }
}
