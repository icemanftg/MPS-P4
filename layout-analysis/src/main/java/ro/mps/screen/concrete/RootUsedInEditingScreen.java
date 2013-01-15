package ro.mps.screen.concrete;

import ro.mps.data.concrete.ComposedBlock;
import ro.mps.data.concrete.ImageBlock;
import ro.mps.screen.base.OrphanCompoundNode;
import java.util.LinkedList;
import java.util.List;

public class RootUsedInEditingScreen extends OrphanCompoundNode<BlockUsedInEditingScreen> {
    private ComposedBlock composedBlock;
    private List<ImageBlock> imageBlockList;
    private boolean hasComposedBlock = false;

    public RootUsedInEditingScreen(int width, int height) {
        super("Document", 0, 0, height, width);
    }

    public ComposedBlock getComposedBlock() {
        return composedBlock;
    }

    public void setComposedBlock(ComposedBlock composedBlock) {
        this.composedBlock = composedBlock;
        hasComposedBlock = true;
    }

    public boolean isHasComposedBlock() {
        return hasComposedBlock;
    }

    public List<ImageBlock> getImageBlockList() {
        return imageBlockList;
    }

    public void setImageBlockList(List<ImageBlock> imageBlockList) {
        this.imageBlockList = imageBlockList;
    }

    /**
     * Returns text from paragraphs
     * @return - returns a list of text from paragraph
     */
    public List<String> getTextFromParagraphs() {
        List<String> textFromParagraphs = new LinkedList<String>();

        for ( BlockUsedInEditingScreen block : getChildren() ) {
            textFromParagraphs.add(block.getTextFromParagraph());
        }

        return textFromParagraphs;
    }

    /**
     * Returns text from lines
     * @return - returns a list of text from paragraph
     */
    public List<String> getTextFromLines() {
        List<String> textFromLines = new LinkedList<String>();

        for ( BlockUsedInEditingScreen block : getChildren() ) {
            for ( LineUsedInEditingScreen line : block.getChildren() ) {
                textFromLines.add(line.getContent());
            }
        }

        return textFromLines;
    }

    /**
     * Returns the line contained in the tree
     * @return - returns a list of lines contained in the tree
     */
    public List<LineUsedInEditingScreen> getLines() {
        List<LineUsedInEditingScreen> lines = new LinkedList<LineUsedInEditingScreen>();

        for ( BlockUsedInEditingScreen block : getChildren() ) {
            lines.addAll(block.getChildren());
        }

        return lines;
    }

    @Override
    public String toString() {
        final String TEMPLATE = "**ROOT**\n" +
                "\tx = %d\n" +
                "\ty = %d\n" +
                "\tWidth = %d\n" +
                "\tHeight = %d\n";

        String result = String.format(TEMPLATE, getLeftUpperCornerX(), getLeftUpperCornerY(), getHeight(), getWidth());

        for ( BlockUsedInEditingScreen block : getChildren() ) {
            result += block.toString();
        }

        return result;
    }
}
