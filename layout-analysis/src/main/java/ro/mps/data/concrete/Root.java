package ro.mps.data.concrete;

import ro.mps.data.base.OrphanCompoundNode;
import java.util.LinkedList;
import java.util.List;

public class Root extends OrphanCompoundNode<Block> {

    public Root(int width, int height) {
        super("document", 0, 0, height, width);
    }

    public List<String> getTextFromParagraphs() {
        List<String> textFromParagraphs = new LinkedList<String>();

        for ( Block block : getChildren() ) {
            textFromParagraphs.add(block.getTextFromParagraph());
        }

        return textFromParagraphs;
    }
}
