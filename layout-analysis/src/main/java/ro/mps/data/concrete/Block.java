package ro.mps.data.concrete;

import ro.mps.data.api.Compound;
import ro.mps.data.base.CompoundNode;

public class Block extends CompoundNode {

    public Block(int x, int y, int height, int width) {
        super("BlockUsedInEditingScreen", x, y, height, width);
    }

    public Block(Compound parent, int x, int y, int height, int width) {
        super(parent, "BlockUsedInEditingScreen", x, y, height, width);
    }
    
    public boolean isPageNumber(){
    	return false;
    	
    }

}