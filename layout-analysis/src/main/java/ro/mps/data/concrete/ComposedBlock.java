package ro.mps.data.concrete;

import ro.mps.data.base.CompoundNode;

public class ComposedBlock extends CompoundNode {

    private Block textBlock;
    private static final String type = "page_number";

    public ComposedBlock() {
        
    }

    public ComposedBlock(Block block) {
        this.textBlock = block;
    }

    public void setBlock(Block block) {
        this.textBlock = block;
    }

    public Block getBlock() {
        return this.textBlock;
    }

    public String getType() {
        return type;
    }
    
    @Override
    public String getLabel() {
        return new String("ComposedBlock");
    }
}