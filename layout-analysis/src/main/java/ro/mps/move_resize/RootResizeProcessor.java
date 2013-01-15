/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.mps.move_resize;

import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ro.mps.data.base.CompoundNode;
import ro.mps.data.base.Node;
import ro.mps.data.concrete.Block;
import ro.mps.data.concrete.ComposedBlock;
import ro.mps.data.concrete.Line;
import ro.mps.data.concrete.Root;
import ro.mps.error.gui.ErrorThrower;
import ro.mps.smart.frame.SmartFrame;
import ro.mps.smart.textbox.SmartTextBox;

/**
 *
 * @author Alexandra
 */
public class RootResizeProcessor {

    private static boolean block = true;

    public static boolean isBlock() {
        return block;
    }

    public static void setBlock(boolean block) {
        RootResizeProcessor.block = block;
    }

    public static Root process(Root root) {
        final Object lock = new Object();
        List<Node> blocks = root.getChildren();

        final SmartFrame frame = new SmartFrame("Layout analysis - move and resize");
        frame.getFrame().setSize(root.getWidth(), root.getHeight());

        for (Node blockNode : blocks) {
            if (blockNode instanceof Block && isBlock()) {

                System.out.println("Adding block " + blockNode);

                Block block = (Block) blockNode;
                String content = "";

                for (Node lineNode : block.getChildren()) {
                    Line line = (Line) lineNode;
                    content += line.getContent() + "\n";
                }
                content = content.trim();

                SmartTextBox smartTextBox = new SmartTextBox(block.getWidth(), block.getHeight());
                smartTextBox.setParentNode(root);

                smartTextBox.setNode(block);
                smartTextBox.setText(content);

                frame.add(smartTextBox);
                frame.add(block);
            } else if (blockNode instanceof Block && !isBlock()) {
                CompoundNode block = (CompoundNode) blockNode;

                for (Node lineNode : block.getChildren()) {
                    if (lineNode instanceof Line) {
                        Line line = (Line) lineNode;
                        SmartTextBox smartTextBox = new SmartTextBox(line.getWidth(), line.getHeight());
                        smartTextBox.setNode(line);
                        smartTextBox.setText(line.getContent());
                        smartTextBox.setParentNode(block);

                        frame.add(smartTextBox);
                    }
                }

                frame.add(block);
            }

        }

        frame.getFrame().setVisible(true);

        Thread windowThread = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (frame.getFrame().isVisible()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(RootResizeProcessor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        };

        windowThread.start();

        frame.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                synchronized (lock) {
                    frame.getFrame().setVisible(false);
                    lock.notify();
                }
            }
        });

        try {
            windowThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(RootResizeProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }


        return root;
    }
}