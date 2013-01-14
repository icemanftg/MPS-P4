package screens.data.concrete;

import screens.data.api.Compound;
import screens.data.api.HasContent;
import screens.data.api.HasParent;
import screens.data.base.Node;

public class LineUsedInEditingScreen extends Node implements HasContent, HasParent<LineUsedInEditingScreen> {

    private Compound<LineUsedInEditingScreen> parent;
    private String content;

    public LineUsedInEditingScreen(int x, int y, int height, int width) {
        super("line", x, y, height, width);
    }

    public LineUsedInEditingScreen(Compound<LineUsedInEditingScreen> p, int x, int y, int height, int width) {
        this(x, y, height, width);
        parent = p;
    }

    public LineUsedInEditingScreen(String content, Compound<LineUsedInEditingScreen> p, int x, int y, int height, int width) {
        this(p, x, y, height, width);
        this.content = content;
    }

    public boolean haveSameParent(LineUsedInEditingScreen line) {
        return this.getParent() == line.getParent();
    }

    @Override
    public Compound<LineUsedInEditingScreen> getParent() {
        return parent;
    }

    @Override
    public void setParent(Compound<LineUsedInEditingScreen> parent) {
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
