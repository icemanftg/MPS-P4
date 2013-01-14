package screens.data.base;

import screens.data.api.Compound;
import screens.data.api.HasParent;

/**
 * A compoundNode has as children a compoundNode of type C
 * His father has as children a compoundNode of type T
 * @param <T> the compound's class
 * @param <C> the children's class
 */
public abstract class CompoundNode<T extends Node, C extends Node> extends OrphanCompoundNode<C> implements HasParent<T> {

    private Compound<T> parent;

    public CompoundNode(String label, int x, int y, int height, int width) {
        super(label, x, y, height, width);
    }

    public CompoundNode(Compound parent, String label, int x, int y, int height, int width) {
        super(label, x, y, height, width);
        this.parent = parent;
    }

    @Override
    public Compound<T> getParent() {
        return parent;
    }

    @Override
    public void setParent(Compound<T> p) {
        parent = p;
    }
}
