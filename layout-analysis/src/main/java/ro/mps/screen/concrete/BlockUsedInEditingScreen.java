package ro.mps.screen.concrete;

import ro.mps.data.concrete.Block;
import ro.mps.screen.api.Compound;
import ro.mps.screen.base.CompoundNode;

import java.util.List;

public class BlockUsedInEditingScreen extends CompoundNode<BlockUsedInEditingScreen, LineUsedInEditingScreen> {

    public BlockUsedInEditingScreen(int x, int y, int height, int width) {
        super("block", x, y, height, width);
    }

    public BlockUsedInEditingScreen(Compound parent, int x, int y, int height, int width) {
        super(parent, "block", x, y, height, width);
    }

    public BlockUsedInEditingScreen(Block block) {
        super( "block",
                block.getLeftUpperCornerX(),
                block.getLeftUpperCornerY(),
                block.getHeight(),
                block.getWidth());
    }

    /**
     * Returns text of a paragraph
     * @return - returns text from all this children
     */
    public String getTextFromParagraph() {
        String content = "";
        final String LINE_SEPARATOR_TEXT = "line.separator";

        for ( LineUsedInEditingScreen child : getChildren() ) {
            content += child.getContent() + System.getProperty(LINE_SEPARATOR_TEXT);
        }

        return content;
    }

    /**
     * Tests if merge with another block can be made and adds all the children from that node.
     * @param block - block to be merged
     * @return - returns true if blocks can be merged
     */
    public boolean merge(BlockUsedInEditingScreen block) {
        boolean canBeMerged = canBeMerged(block);
        if ( canBeMerged ) {
            addChildrenFromAnotherBlock(block);
            removeBlock(block);
        }

        return canBeMerged;
    }

    /**
     * Splits a node from a position specified by line.
     * @param index - index of the line where the split will be made
     */
    public void split(int index) {
        addNewBlockToRoot(index);
        setNewHeightForBlockAfterSplit(index);
        removeElementsContainedInNewBlock(index);
    }

    /**
     * Adds a block that contains the same children as block identified by index to the root.
     * @param index - position where the new block will be added
     */
    private void addNewBlockToRoot(int index) {
        Compound<BlockUsedInEditingScreen> root = getParent();
        int indexOfChildFromChildrenList = root.getIndexOfChildFromChildrenList(this) + 1;
        root.addChildAtIndex(indexOfChildFromChildrenList, makeNewBlock(index));
    }

    /**
     * Returns a new block with the same dimensions as the block pointed by index
     * @param index - index
     * @return - returns a block with the same dimensions as the block pointed by index
     */
    private BlockUsedInEditingScreen makeNewBlock(int index) {
        List<LineUsedInEditingScreen> children = getChildren();
        LineUsedInEditingScreen lineToSplit = children.get(index);

        int heightForNewBlock = calculateHeight(index);

        BlockUsedInEditingScreen blockResultedFromSplit = new BlockUsedInEditingScreen(getParent(), lineToSplit.getLeftUpperCornerX(),
                lineToSplit.getLeftUpperCornerY(), heightForNewBlock, getWidth());
        addChildrenToNewBlock(blockResultedFromSplit, children.subList(index, children.size()));

        return blockResultedFromSplit;
    }

    /**
     * Removes line that are not supposed to be in the current block because they are in the new block
     * @param index - index that tells start position for deleting the blocks
     */
    private void removeElementsContainedInNewBlock(int index) {
        List<LineUsedInEditingScreen> children = getChildren();
        this.removeChildren(children.subList(index, children.size()));
    }

    /**
     * Sets proper dimension to the block
     * @param index - index of the block
     */
    private void setNewHeightForBlockAfterSplit(int index) {
        LineUsedInEditingScreen child = getChildren().get(index);
        int newHeight = child.getLeftUpperCornerY() - getLeftUpperCornerY();
        setHeight(newHeight);
    }

    /**
     * Adds children to block
     * @param newBlock - block
     * @param children - list of lines
     */
    private void addChildrenToNewBlock(BlockUsedInEditingScreen newBlock, List<LineUsedInEditingScreen> children) {
        newBlock.addChildren(children);
    }

    /**
     * Calculates proper height
     * @param index - index
     * @return - returns height
     */
    private int calculateHeight(int index) {
        List<LineUsedInEditingScreen> children = getChildren();
        LineUsedInEditingScreen line = children.get(index);

        return getHeight() - line.getLeftUpperCornerY() + getLeftUpperCornerY();
    }

    /**
     * Tests if blocks can be merged
     * @param block - block
     * @return - returns true if blocks can be merged
     */
    private boolean canBeMerged(BlockUsedInEditingScreen block) {
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

    /**
     * Adds children from another block
     * @param block - block whose children will be added to the current block
     */
    private void addChildrenFromAnotherBlock(BlockUsedInEditingScreen block) {
        List<LineUsedInEditingScreen> children = block.getChildren();
        this.addChildren(children);
    }

    /**
     * Removes a block
     * @param block - block to be removed
     */
    private void removeBlock(BlockUsedInEditingScreen block) {
        Compound<BlockUsedInEditingScreen> root = this.getParent();
        root.removeChild(block);
    }

    private void setOldValues(int x, int y, int height, int with) {
        this.setX(x);
        this.setY(y);
        this.setHeight(height);
        this.setWidth(with);
    }

    /**
     * Test if block intersects other block
     * @param block - tested block
     * @return - returns true if block intersects other block
     */
    private boolean intersectsOtherBlocks(BlockUsedInEditingScreen block) {
        Compound<BlockUsedInEditingScreen> root = this.getParent();
        List<BlockUsedInEditingScreen> blocks = root.getChildren();

        for ( BlockUsedInEditingScreen otherBlock : blocks ) {
            if ( otherBlock != this && otherBlock != block
                    && this.intersectsAnotherNode(otherBlock) && this.haveTheSameDimensions(otherBlock) ) {
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

        for ( LineUsedInEditingScreen line : getChildren() ) {
            result += line.toString();
        }

        return result;
    }
}