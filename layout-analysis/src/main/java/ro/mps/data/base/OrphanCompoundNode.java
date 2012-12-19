package ro.mps.data.base;

import ro.mps.data.api.Compound;

import java.util.ArrayList;
import java.util.List;

public abstract class OrphanCompoundNode extends Node implements Compound {

    private List<Node> children = new ArrayList<Node>();

    public OrphanCompoundNode(String label, int x, int y, int height, int width) {
        super(label, x, y, height, width);
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

}
