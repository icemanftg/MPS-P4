package ro.mps.data.concrete;

import ro.mps.data.api.Compound;
import ro.mps.data.api.HasContent;
import ro.mps.data.api.HasParent;
import ro.mps.data.base.Node;

public class Line extends Node implements HasContent, HasParent {

    private Compound parent;
    private String content;

    public Line(int x, int y, int height, int width) {
        super("line", x, y, height, width);
    }

    public Line(Compound p, int x, int y, int height, int width) {
        this(x, y, height, width);
        parent = p;
    }

    public Line(String content, Compound p, int x, int y, int height, int width) {
        this(p, x, y, height, width);
        this.content = content;
    }

    @Override
    public Compound getParent() {
        return parent;
    }

    @Override
    public void setParent(Compound p) {
        parent = p;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
