package ro.mps.data.base;

import ro.mps.data.api.Compound;
import ro.mps.data.api.HasParent;

public abstract class CompoundNode extends OrphanCompoundNode implements HasParent{

	private Compound parent;
	
	public CompoundNode(String label, int x, int y, int height, int width){
		super(label,x,y,height,width);
	}
	
	public CompoundNode(Compound parent, String label, int x, int y, int height, int width){
		super(label,x,y,height,width);
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
}
