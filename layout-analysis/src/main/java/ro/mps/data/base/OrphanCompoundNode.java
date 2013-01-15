package ro.mps.data.base;

import ro.mps.data.api.Compound;
import ro.mps.data.api.HasPosition;

import java.util.ArrayList;
import java.util.List;

public abstract class OrphanCompoundNode extends Node implements Compound {

    private List<Node> children = new ArrayList<Node>();

	/**
     * Default constructor - only used by ComposedBlock
     */
    public OrphanCompoundNode() {
    }

    public OrphanCompoundNode(String label, int x, int y, int height, int width) {
        super(label, x, y, height, width);
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public boolean fits(HasPosition p) {
    	/*
    	 * Check if fits inside the container (i.e parent)
    	 */
    	if (! (
    			(getLeftUpperCornerX() <= p.getLeftUpperCornerX()) &&
    			(getLeftUpperCornerX() + getWidth() >= p.getLeftUpperCornerX() + p.getWidth()) &&
    			(getLeftUpperCornerY() <= p.getLeftUpperCornerY()) &&
    			(getLeftUpperCornerY() + getHeight() >= p.getLeftUpperCornerY() + p.getHeight())))
    			return false;
    	/**
    	 * Checks overlapping with children
    	 */
    	for (HasPosition child : getChildren()) {
    		if (child == p){
    			continue;
    		}
    		if (! child.clears(p))
    			return false;
    	}
    	
    	/**
    	 * No overlapping with the children or the block itself
    	 */
    	return true;
    }

}
