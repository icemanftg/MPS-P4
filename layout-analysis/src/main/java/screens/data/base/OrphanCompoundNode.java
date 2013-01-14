package screens.data.base;

import screens.data.api.Compound;

import java.util.ArrayList;
import java.util.List;

public abstract class OrphanCompoundNode<T extends Node> extends Node implements Compound<T> {

    private List<T> children = new ArrayList<T>();

    public OrphanCompoundNode(String label, int x, int y, int height, int width) {
        super(label, x, y, height, width);
    }

    @Override
    public void addChildAtIndex(int index, T child) {
        children.add(index, child);
    }

    @Override
    public List<T> getChildren() {
        return children;
    }

    @Override
    public T getChild(int index) {
        return children.get(index);
    }

    @Override
    public void addChild(T child) {
        children.add(child);
    }

    @Override
    public void addChildren(List<T> childrenToBeAdded) {
        children.addAll(childrenToBeAdded);
    }

    public void removeChild(int index) {
        children.remove(index);
    }

    @Override
    public void removeChild(T child) {
        children.remove(child);
    }

    @Override
    public void removeChildren(List<T> children) {
        children.removeAll(children);
    }

    @Override
    public int getIndexOfChildFromChildrenList(T child) {
        for ( int i = 0; i < children.size(); i++ ) {
            if ( child == children.get(i) ) {
                return i;
            }
        }

        return -1;
    }
}
