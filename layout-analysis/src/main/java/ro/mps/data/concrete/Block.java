package ro.mps.data.concrete;

import ro.mps.data.api.Compound;
import ro.mps.data.base.CompoundNode;

import java.util.List;

public class Block extends CompoundNode<Line> {

    public Block(int x, int y, int height, int width) {
        super("block", x, y, height, width);
    }

    public Block(Compound parent, int x, int y, int height, int width) {
        super(parent, "block", x, y, height, width);
    }

    public String getTextFromParagraph() {
        String content = "";
        final String LINE_SEPARATOR_TEXT = "line.separator";

        for ( Line child : getChildren() ) {
            content += child.getContent() + System.getProperty(LINE_SEPARATOR_TEXT);
        }

        return content;
    }

    public boolean canBeMerged(Block block) {
        int backupX = block.getLeftUpperCornerX();
        int backupY = block.getLeftUpperCornerY();
        int backupHeight = block.getHeight();
        int backupWith = block.getWidth();

        this.setDimensionsAfterMerge(block);

        if ( intersectsOtherBlocks(block) ) {
            setOldValues(backupX, backupY, backupHeight, backupWith);
            return true;
        }

        return false;
    }

    public void merge(Block block) {
        if ( canBeMerged(block) ) {
            addChildren(block);
            removeBlock(block);
        }
    }

    private void addChildren(Block block) {
        List<Line> children = block.getChildren();
        this.addChildren(children);
    }

    private void removeBlock(Block block) {
        Compound<Block> root = this.getParent();
        root.removeChild(block);
    }

    private void setOldValues(int x, int y, int height, int with) {
        this.setX(x);
        this.setY(y);
        this.setHeight(height);
        this.setWidth(with);
    }

    private boolean intersectsOtherBlocks(Block block) {
        Compound<Block> root = this.getParent();
        List<Block> blocks = root.getChildren();

        blocks.remove(this);
        blocks.remove(block);

        for ( Block otherBlock : blocks ) {
            if ( this.inside(otherBlock.getLeftUpperCorner()) ) {
                return true;
            }
        }

        return false;
    }
}