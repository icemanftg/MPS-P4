package ro.mps.data.concrete;

import ro.mps.data.api.Compound;
import ro.mps.data.base.CompoundNode;

public class ImageBlock extends Block {

    public ImageBlock(int x, int y, int height, int width) {
        super(x, y, height, width);
        label = "ImageBlock";
    }

    public ImageBlock(Compound parent, int x, int y, int height, int width) {
        super(parent, x, y, height, width);
        label = "ImageBlock";
    }
}
