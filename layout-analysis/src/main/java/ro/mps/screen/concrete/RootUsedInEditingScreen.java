package ro.mps.screen.concrete;

import ro.mps.screen.base.OrphanCompoundNode;
import java.util.LinkedList;
import java.util.List;

public class RootUsedInEditingScreen extends OrphanCompoundNode<Block> {

    public RootUsedInEditingScreen(int width, int height) {
        super("document", 0, 0, height, width);
    }

    /**
     * Returns text from paragraphs
     * @return - returns a list of text from paragraph
     */
    public List<String> getTextFromParagraphs() {
        List<String> textFromParagraphs = new LinkedList<String>();

        for ( Block block : getChildren() ) {
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

        for ( Block block : getChildren() ) {
            for ( Line line : block.getChildren() ) {
                textFromLines.add(line.getContent());
            }
        }

        return textFromLines;
    }

    /**
     * Returns the line contained in the tree
     * @return - returns a list of lines contained in the tree
     */
    public List<Line> getLines() {
        List<Line> lines = new LinkedList<Line>();

        for ( Block block : getChildren() ) {
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

        for ( Block block : getChildren() ) {
            result += block.toString();
        }

        return result;
    }
}
