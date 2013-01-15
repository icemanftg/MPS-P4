/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.mps.smart.textbox;

import ro.mps.smart.frame.SmartFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import ro.mps.data.base.Node;
import ro.mps.data.concrete.Block;
import ro.mps.data.concrete.ComposedBlock;
import ro.mps.data.concrete.Line;
import ro.mps.data.concrete.Root;
import ro.mps.move_resize.ResizeController;
import ro.mps.move_resize.RootResizeProcessor;

/**
 * @author Alexandra
 */
public class SmartTextBoxDemo {
    public static void main(String[] args) {
        Root root = new Root(600, 600);
        
        Block block = new Block(root, 15, 15, 200, 200);
        Line l1 = new Line(block, 15, 15, 10, 200);
        l1.setContent("first line");
        l1.setParent(block);
        block.addChild(l1);
        Line l2 = new Line(block, 15, 30, 10, 200);
        l2.setContent("second line");
        l2.setParent(block);
        block.addChild(l2);
        
        Block block2 = new Block(root, 315, 315, 200, 200);
        Line l3 = new Line(block2, 15, 15, 10, 200);
        l3.setContent("third line");
        l3.setParent(block2);
        block2.addChild(l3);
        Line l4 = new Line(block2, 15, 30, 10, 200);
        l4.setContent("fourth line");
        l4.setParent(block2);
        block2.addChild(l4);
        
        root.addChild(block);
        root.addChild(block2);
        
        root = RootResizeProcessor.process(root);
        
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SmartTextBoxDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RootResizeProcessor.process(root);
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(SmartTextBoxDemo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        Root root2 = new Root(400, 400);
//        
//        Block block3 = new Block(root2, 30, 20, 300, 300);
//        
//        Line l5 = new Line("some line", block3, 30, 30, 30, 100),
//                l6 = new Line("some other line", block3, 50, 50, 20, 100);
//        block3.addChild(l6);
//        block3.addChild(l5);
//        root2.addChild(block3);
//        
//        RootResizeProcessor.setBlock(false);
//        
//        root2 = RootResizeProcessor.process(root2);
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(SmartTextBoxDemo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        RootResizeProcessor.process(root2);
    }
}