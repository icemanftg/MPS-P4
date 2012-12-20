package ro.mps.data.base;

import ro.mps.data.api.Compound;

import java.util.ArrayList;
import java.util.List;

public abstract class OrphanCompoundNode<T extends Node> extends Node implements Compound {

    private List<T> children = new ArrayList<T>();

    public OrphanCompoundNode(String label, int x, int y, int height, int width) {
        super(label, x, y, height, width);
    }

    @Override
    public List<T> getChildren() {
        return children;
    }

    public void addChild(T child) {
        children.add(child);
    }

    public void addChildren(List<T> childrenToBeAdded) {
        children.addAll(childrenToBeAdded);
    }

    public void removeChild(int index) {
        children.remove(index);
    }

    @Override
    public void removeChild(Node child) {
        children.remove(child);
    }

    @Override
    public void removeChildren(List children) {
        children.removeAll(children);
    }
}
