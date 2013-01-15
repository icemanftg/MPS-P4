/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.mps.smart.frame;

import ro.mps.screen.base.Node;
import ro.mps.smart.textbox.SmartTextBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alexandra
 */
public class SmartFrame {

    private ArrayList<Node> children;
    private JFrame frame;

    public SmartFrame() {
        frame = new JFrame();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        children = new ArrayList<Node>();
    }

    public SmartFrame(String title) {
        frame = new JFrame(title);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        children = new ArrayList<Node>();
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void add(Node node) {
        children.add(node);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Added node");
    }

    public Component add(Component comp) {
        frame.add(comp);

        if (comp instanceof SmartTextBox) {
            ((SmartTextBox) comp).setFrame(this);
        }
        return comp;
    }

    public JFrame getFrame() {
        return frame;
    }
}
