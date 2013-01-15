package ro.mps.data.concrete;

import ro.mps.data.api.Compound;
import ro.mps.data.base.CompoundNode;

public class Block extends CompoundNode {

    public Block(int x, int y, int height, int width) {
        super("Block", x, y, height, width);
    }

    public Block(Compound parent, int x, int y, int height, int width) {
        super(parent, "Block", x, y, height, width);
    }

}