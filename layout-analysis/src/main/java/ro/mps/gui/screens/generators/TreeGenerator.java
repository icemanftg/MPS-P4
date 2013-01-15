package ro.mps.gui.screens.generators;

import ro.mps.screen.concrete.BlockUsedInEditingScreen;
import ro.mps.screen.concrete.LineUsedInEditingScreen;
import ro.mps.screen.concrete.RootUsedInEditingScreen;

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

    public RootUsedInEditingScreen buildTree() {
        RootUsedInEditingScreen root = new RootUsedInEditingScreen(DEFAULT_HEIGHT, DEFAULT_WIDTH);

        for (int i = 0; i < numberOfBlocks; i++) {
            BlockUsedInEditingScreen block = buildBlock(i);
            root.addChild(block);
            block.setParent(root);

            for (int j = 0; j < NUMBER_OF_LINES_FOR_EACH_BLOCK; j++) {
                LineUsedInEditingScreen line = buildLine(j, block);
                block.addChild(line);
                line.setParent(block);
            }
        }

        return root;
    }

    private BlockUsedInEditingScreen buildBlock(int blockNumber) {
        final int BLOCK_HEIGHT = 50;
        final int BLOCK_WIDTH = 50;
        final int BASE_COORDINATE_FOR_Y = 0;
        final int COORDINATE_FOR_X = 0;
        int y = BASE_COORDINATE_FOR_Y + blockNumber * BLOCK_HEIGHT;

        return new BlockUsedInEditingScreen(COORDINATE_FOR_X, y, BLOCK_HEIGHT, BLOCK_WIDTH);
    }

    private LineUsedInEditingScreen buildLine(int lineNumber, BlockUsedInEditingScreen block) {
        final int LINE_HEIGHT = 10;
        final int LINE_WIDTH = 10;
        int y = block.getLeftUpperCornerY() + lineNumber * LINE_HEIGHT;
        int x = block.getLeftUpperCornerX();

        LineUsedInEditingScreen line = new LineUsedInEditingScreen(x, y, LINE_HEIGHT, LINE_WIDTH);
        line.setContent(PLACEHOLDER_TEXT[lineNumber]);

        return line;
    }
}
