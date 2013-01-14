package ro.mps.data.base;

import ro.mps.data.api.Compound;
import ro.mps.data.api.HasParent;
import ro.mps.data.api.HasPosition;

public abstract class CompoundNode extends OrphanCompoundNode implements HasParent {

    private Compound parent;

	/**
     * Default constructor - only used by ComposedBlock
     */
    public CompoundNode() {

    }

    public CompoundNode(String label, int x, int y, int height, int width) {
        super(label, x, y, height, width);
    }

    public CompoundNode(Compound parent, String label, int x, int y, int height, int width) {
        super(label, x, y, height, width);
        this.parent = parent;
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
    		if (! child.clears(p))
    			return false;
    	}
    	
    	/**
    	 * No overlapping with the children or the block itself
    	 */
    	return true;
    }
}
