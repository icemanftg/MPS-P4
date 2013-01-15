package ro.mps.data.concrete;

import ro.mps.data.api.Compound;
import ro.mps.data.base.CompoundNode;

public class ImageBlock extends CompoundNode {

    public ImageBlock(int x, int y, int height, int width) {
        super("ImageBlock", x, y, height, width);
    }

    public ImageBlock(Compound parent, int x, int y, int height, int width) {
        super(parent, "ImageBlock", x, y, height, width);
    }
}
