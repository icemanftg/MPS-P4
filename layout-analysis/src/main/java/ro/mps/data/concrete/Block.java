package ro.mps.data.concrete;

import ro.mps.data.api.Compound;
import ro.mps.data.base.CompoundNode;

import java.util.List;

public class Block extends CompoundNode<Block, Line> {

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

    public boolean merge(Block block) {
        boolean canBeMerged = canBeMerged(block);
        if ( canBeMerged ) {
            addChildrenFromAnotherBlock(block);
            removeBlock(block);
        }

        return canBeMerged;
    }

    public void split(int index) {
        addNewBlockToRoot(index);
        setNewHeightForBlockAfterSplit(index);
        removeElementsContainedInNewBlock(index);
    }

    private void addNewBlockToRoot(int index) {
        Compound<Block> root = getParent();
        int indexOfChildFromChildrenList = root.getIndexOfChildFromChildrenList(this) + 1;
        root.addChildAtIndex(indexOfChildFromChildrenList, makeNewBlock(index));
    }

    private Block makeNewBlock(int index) {
        List<Line> children = getChildren();
        Line lineToSplit = children.get(index);

        int heightForNewBlock = calculateHeight(index);

        Block blockResultedFromSplit = new Block(getParent(), lineToSplit.getLeftUpperCornerX(),
                lineToSplit.getLeftUpperCornerY(), heightForNewBlock, getWidth());
        addChildrenToNewBlock(blockResultedFromSplit, children.subList(index, children.size()));

        return blockResultedFromSplit;
    }

    private void removeElementsContainedInNewBlock(int index) {
        List<Line> children = getChildren();
        this.removeChildren(children.subList(index, children.size()));
    }

    private void setNewHeightForBlockAfterSplit(int index) {
        Line child = getChildren().get(index);
        int newHeight = child.getLeftUpperCornerY() - getLeftUpperCornerY();
        setHeight(newHeight);
    }

    private void addChildrenToNewBlock(Block newBlock, List<Line> children) {
        newBlock.addChildren(children);
    }

    private int calculateHeight(int index) {
        List<Line> children = getChildren();
        Line line = children.get(index);

        return getHeight() - line.getLeftUpperCornerY() + getLeftUpperCornerY();
    }

    private boolean canBeMerged(Block block) {
        int backupX = block.getLeftUpperCornerX();
        int backupY = block.getLeftUpperCornerY();
        int backupHeight = block.getHeight();
        int backupWith = block.getWidth();

        this.setDimensionsAfterMerge(block);

        if ( intersectsOtherBlocks(block) ) {
            setOldValues(backupX, backupY, backupHeight, backupWith);
            return false;
        }

        return true;
    }

    private void addChildrenFromAnotherBlock(Block block) {
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

        for ( Block otherBlock : blocks ) {
            if ( otherBlock != this && otherBlock != block && this.inside(otherBlock.getLeftUpperCorner()) ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        final String TEMPLATE = "\t**BLOCK**\n" +
                "\t\tx = %d\n" +
                "\t\ty = %d\n" +
                "\t\tWidth = %d\n" +
                "\t\tHeight = %d\n";

        String result = String.format(TEMPLATE, getLeftUpperCornerX(), getLeftUpperCornerY(), getHeight(), getWidth());

        for ( Line line : getChildren() ) {
            result += line.toString();
        }

        return result;
    }
}