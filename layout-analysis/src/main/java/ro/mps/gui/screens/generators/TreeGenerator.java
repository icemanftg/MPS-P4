package ro.mps.gui.screens.generators;

import ro.mps.screen.concrete.Block;
import ro.mps.screen.concrete.Line;
import ro.mps.screen.concrete.Root;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 22.12.2012
 * Time: 13:20
 */
public class TreeGenerator {
    private final int numberOfBlocks;
    private final int NUMBER_OF_LINES_FOR_EACH_BLOCK = 5;
    private final int DEFAULT_HEIGHT = 1000;
    private final int DEFAULT_WIDTH = 1000;
    private static final String[] PLACEHOLDER_TEXT = {
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed",
            " diam nonummy nibh euismod tincidunt ut laoreet dolore magna",
            "aliquam erat volutpat. Ut wisi enim ad minim veniam, quis",
            " nostrud exerci tation ullamcorper suscipit lobortis nisl ut",
            " aliquip ex ea commodo consequat. Duis autem vel eum iriure"
    };

    public TreeGenerator(int numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
    }

    public Root buildTree() {
        Root root = new Root(DEFAULT_HEIGHT, DEFAULT_WIDTH);

        for (int i = 0; i < numberOfBlocks; i++) {
            Block block = buildBlock(i);
            root.addChild(block);
            block.setParent(root);

            for (int j = 0; j < NUMBER_OF_LINES_FOR_EACH_BLOCK; j++) {
                Line line = buildLine(j, block);
                block.addChild(line);
                line.setParent(block);
            }
        }

        return root;
    }

    private Block buildBlock(int blockNumber) {
        final int BLOCK_HEIGHT = 50;
        final int BLOCK_WIDTH = 50;
        final int BASE_COORDINATE_FOR_Y = 0;
        final int COORDINATE_FOR_X = 0;
        int y = BASE_COORDINATE_FOR_Y + blockNumber * BLOCK_HEIGHT;

        return new Block(COORDINATE_FOR_X, y, BLOCK_HEIGHT, BLOCK_WIDTH);
    }

    private Line buildLine(int lineNumber, Block block) {
        final int LINE_HEIGHT = 10;
        final int LINE_WIDTH = 10;
        int y = block.getLeftUpperCornerY() + lineNumber * LINE_HEIGHT;
        int x = block.getLeftUpperCornerX();

        Line line = new Line(x, y, LINE_HEIGHT, LINE_WIDTH);
        line.setContent(PLACEHOLDER_TEXT[lineNumber]);

        return line;
    }
}
