package ro.mps.screen.transformer;

import ro.mps.data.base.Node;
import ro.mps.data.concrete.*;
import ro.mps.screen.concrete.BlockUsedInEditingScreen;
import ro.mps.screen.concrete.LineUsedInEditingScreen;
import ro.mps.screen.concrete.RootUsedInEditingScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 1/15/13
 * Time: 4:06 PM
 */
public class DataStructureTransformer {
    public static RootUsedInEditingScreen transformRootToRootUsedInEditingScreen(Root root) {
        RootUsedInEditingScreen rootUsedInEditingScreen = new RootUsedInEditingScreen(root.getWidth(),
                root.getHeight());
        addBlocksToRootUsedInEditingScreen(rootUsedInEditingScreen, root);
        rootUsedInEditingScreen.setImageBlockList(getImageBlockFromRoot(root));
        rootUsedInEditingScreen.setPageNumberBlock(root.getPageNumberBlock());

        return rootUsedInEditingScreen;
    }

    public static Root transformRootUsedInEditingScreenToRoot(RootUsedInEditingScreen rootUsedInEditingScreen) {
        Root root = new Root(rootUsedInEditingScreen.getWidth(), rootUsedInEditingScreen.getHeight());
        addImageBlocks(root, rootUsedInEditingScreen);
        addPageNumberBlock(root, rootUsedInEditingScreen);
        addBlocks(root, rootUsedInEditingScreen);

        return root;
    }

    private static void addImageBlocks(Root root, RootUsedInEditingScreen rootUsedInEditingScreen) {
        for ( ImageBlock imageBlock : rootUsedInEditingScreen.getImageBlockList() ) {
            root.addChild(imageBlock);
            // TODO
        }
    }

    private static void addPageNumberBlock(Root root, RootUsedInEditingScreen rootUsedInEditingScreen) {
        if ( rootUsedInEditingScreen.hasPageNumberBlock() )
        root.addChild(rootUsedInEditingScreen.getPageNumberBlock());
    }

    private static void addBlocks(Root root, RootUsedInEditingScreen rootUsedInEditingScreen) {
        for ( BlockUsedInEditingScreen blockUsedInEditingScreen : rootUsedInEditingScreen.getChildren() ) {
            Block block = new Block(blockUsedInEditingScreen.getLeftUpperCornerX(),
                    blockUsedInEditingScreen.getLeftUpperCornerY(),
                    blockUsedInEditingScreen.getHeight(),
                    blockUsedInEditingScreen.getWidth());
            root.addChild(block);
            block.setParent(root);
            addLinesToABlock(block, blockUsedInEditingScreen);
            
        }
    }

    private static void addLinesToABlock(Block block, BlockUsedInEditingScreen blockUsedInEditingScreen) {
        for ( LineUsedInEditingScreen lineUsedInEditingScreen : blockUsedInEditingScreen.getChildren() ) {
            Line line = new Line(lineUsedInEditingScreen.getLeftUpperCornerX(),
                    lineUsedInEditingScreen.getLeftUpperCornerY(),
                    lineUsedInEditingScreen.getHeight(),
                    lineUsedInEditingScreen.getWidth());
            line.setParent(block);
            line.setContent(lineUsedInEditingScreen.getContent());
            block.addChild(line);
        }
    }

    private static void addBlocksToRootUsedInEditingScreen(RootUsedInEditingScreen rootUsedInEditingScreen, Root root) {
        List<BlockUsedInEditingScreen> blocksUsedInEditingScreen = new ArrayList<BlockUsedInEditingScreen>();

        for ( Node node : root.getChildren()) {
            if ( node instanceof Block && !(node instanceof PageNumberBlock) && !(node instanceof ImageBlock) ) {
                BlockUsedInEditingScreen blockUsedInEditingScreen = new BlockUsedInEditingScreen((Block) node);
                blockUsedInEditingScreen.setParent(rootUsedInEditingScreen);
                addLinesToABlockUsedInEditingScreen(blockUsedInEditingScreen, (Block) node);
                blocksUsedInEditingScreen.add(blockUsedInEditingScreen);
            }
        }

        rootUsedInEditingScreen.addChildren(blocksUsedInEditingScreen);
    }

    private static void addLinesToABlockUsedInEditingScreen(BlockUsedInEditingScreen blockUsedInEditingScreen, Block block) {
        List<LineUsedInEditingScreen> linesUsedInEditingScreen = new ArrayList<LineUsedInEditingScreen>();

        for ( Node node : block.getChildren()) {
            if ( node instanceof Line ) {
                LineUsedInEditingScreen lineUsedInEditingScreen = transformLineToLineUsedInEditingScreen((Line) node);
                lineUsedInEditingScreen.setParent(blockUsedInEditingScreen);
                linesUsedInEditingScreen.add(lineUsedInEditingScreen);
            }
        }

        blockUsedInEditingScreen.addChildren(linesUsedInEditingScreen);
    }

    private static LineUsedInEditingScreen transformLineToLineUsedInEditingScreen(Line line) {
        LineUsedInEditingScreen lineUsedInEditingScreen = new LineUsedInEditingScreen(line);
        lineUsedInEditingScreen.setContent(line.getContent());

        return lineUsedInEditingScreen;
    }

    private static List<ImageBlock> getImageBlockFromRoot(Root root) {
        List<ImageBlock> imageBlocks = new ArrayList<ImageBlock>();

        for ( Node node : root.getChildren() ) {
            if ( node instanceof ImageBlock ) {
                imageBlocks.add((ImageBlock) node);
            }
        }

        return imageBlocks;
    }
}
