package ro.mps.smart.textbox;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import ro.mps.data.base.Node;
import ro.mps.data.concrete.Block;
import ro.mps.data.concrete.Line;
import ro.mps.smart.frame.SmartFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import ro.mps.data.api.HasParent;
import ro.mps.data.api.HasPosition;
import ro.mps.error.exceptions.DoenstFitException;

/**
 * @author alexandra
 */
public class SmartTextBox extends Box {

    private JTextArea textArea;
    private ComponentResizer resizer;
    private ComponentMover mover;
    private String content, visibleContent;
    private SmartFrame frame;
    private HasPosition parentNode;
    private Node node;
    private Dimension size;
    private Point position;

    public SmartTextBox(int w, int h) {
        super(BoxLayout.PAGE_AXIS);

        setBounds(0, 0, w, h);

        size = new Dimension(w, h);
        position = new Point(0, 0);

        Container cp = new Container();
        cp.setLayout(new FlowLayout(FlowLayout.RIGHT));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(w, h));

        add(textArea);


        setPreferredSize(new Dimension(w, h));
        setBorder(BorderFactory.createLineBorder(Color.black));

        resizer = new ComponentResizer(this);
        mover = new ComponentMover(this, textArea);
    }

    public HasPosition getParentNode() {
        return parentNode;
    }

    public void setParentNode(HasPosition parentNode) {
        this.parentNode = parentNode;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Block node) {
        this.node = node;
        System.out.println("Added block node");
        this.setBounds(node.getLeftUpperCornerX(), node.getLeftUpperCornerY(), node.getWidth(), node.getHeight());

        position = new Point(node.getLeftUpperCorner());
        size = new Dimension(node.getWidth(), node.getHeight());

//        for (Node child : node.getChildren()) {
//            if (child instanceof Line) {
//                Line line = (Line) child;
//
//                setText(getText() + "\n" + line.getContent());
//            }
//        }
    }

    public void setNode(Line node) {
//        pt linii
        System.out.println("Added line node");


        this.node = node;
        this.setBounds(node.getLeftUpperCornerX(), node.getLeftUpperCornerY(), node.getWidth(), node.getHeight());

        position = new Point(node.getLeftUpperCorner());
        size = new Dimension(node.getWidth(), node.getHeight());

        setText(((Line) node).getContent());
    }

    public void handleResize() {
        textArea.setPreferredSize(new Dimension(getWidth(), getHeight()));
        textArea.setMaximumSize(textArea.getPreferredSize());
        setBorder(BorderFactory.createLineBorder(Color.black));

        textArea.setMargin(new Insets(0, 0, 0, 0));

        try {
            checkIntersections();
        } catch (DoenstFitException ex) {
            Logger.getLogger(SmartTextBox.class.getName()).log(Level.SEVERE, null, ex);
            
            setBounds(position.x, position.y, size.width, size.height);
            
            WindowEvent wev = new WindowEvent(frame.getFrame(), WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);

        }

        node.setWidth(getWidth());
        node.setHeight(getHeight());
        
        size = new Dimension(getWidth(), getHeight());

    }

    public void handleMove() {
        try {
            checkIntersections();
        } catch (DoenstFitException ex) {
            Logger.getLogger(SmartTextBox.class.getName()).log(Level.SEVERE, null, ex);
            
            setBounds(position.x, position.y, size.width, size.height);
            
            WindowEvent wev = new WindowEvent(frame.getFrame(), WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
        }

        node.setUpperLeftCorner(getX(), getY());
        
        position = new Point(node.getLeftUpperCorner());
    }

    private void checkIntersections() throws DoenstFitException {

        if (parentNode != null && !node.isContainedBy(parentNode)) {
            throw new DoenstFitException();
        }

        for (Node other : getFrame().getChildren()) {
            if (node.equals(other)) {
                continue;
            }

            if (node.clears(other)) {
                continue;
            }

            if (((HasParent) node).getParent().equals(other)) {
                if (node.isContainedBy((HasPosition) other)) {
                    continue;
                } else {
                    throw new DoenstFitException();
                }
            }

            if (other instanceof Block) {
                for (Node child : ((Block) other).getChildren()) {
                    if (other.equals(child)) {
                        continue;
                    }
                    if (!node.clears(other)) {
                        throw new DoenstFitException();
                    }
                }
            }


        }

       
    }

    private void updateLineContent(String content, boolean rendered) {
        String[] lineContents = content.split("\n");

        if (lineContents.length == 0) {
//            lines.clear();
            return;
        }

        if (node instanceof Line) {
            Line line = (Line) node;
            line.setContent(lineContents[0]);
        } else {
            Block block = (Block) node;
            for (int i = 0; i < Math.min(lineContents.length, block.getChildren().size()); ++i) {
                Line line = (Line) block.getChildren().get(i);
                line.setContent(lineContents[i]);
            }

            if (block.getChildren().size() > lineContents.length) {
                for (int i = lineContents.length; i < block.getChildren().size(); ++i) {
                    block.getChildren().remove(lineContents.length);
                }
            }

            if (lineContents.length > block.getChildren().size()) {
                Line last = (Line) block.getChildren().get(block.getChildren().size() - 1);
                for (int i = block.getChildren().size(); i < lineContents.length; ++i) {
                    Line line = new Line(last.getLeftUpperCornerX(), last.getLeftUpperCornerY() + last.getHeight(), last.getWidth(), last.getHeight());
                    line.setParent(last.getParent());
                    line.setContent(lineContents[i]);
                    ((Block) last.getParent()).addChild(line);
                }
            }
        }

    }

    private int lineWidth(String line) {
        Graphics g = getGraphics();
        FontMetrics fm = g.getFontMetrics();
        int w = 0;

        for (char c : line.toCharArray()) {
            w += fm.charWidth(c);
        }

        return w;
    }

    private int lineHeight(String line) {
        return getGraphics().getFontMetrics().getHeight();
    }

    @Override
    public void validate() {
        Graphics g = getGraphics();
        final FontMetrics fontMetrics = g.getFontMetrics();

        String[] allLines = content.split("\n");
        int allLinesN = allLines.length;
        int nLines = 0;

        int h = 0;
        for (int i = 0; i < allLinesN; ++i) {
            h += fontMetrics.getHeight();
            if (h <= textArea.getPreferredSize().height) {
                ++nLines;
            } else {
                break;
            }
        }


        String[] lineContents = new String[nLines];
        visibleContent = "";

        for (int i = 0; i < lineContents.length; ++i) {
            String l = "";
            String lin = allLines[i];
            int w = 0;
            for (char c : lin.toCharArray()) {
                w += fontMetrics.charWidth(c);
                if (w <= textArea.getPreferredSize().width) {
                    l += c;
                } else {
                    break;
                }
            }
            lineContents[i] = l;
            visibleContent += l + "\n";
        }

        updateLineContent(visibleContent, true);
        textArea.setText(visibleContent.trim());

    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        System.out.println("[TEXT] " + text);

        textArea.setText(text);
        content = text;
        updateLineContent(content, false);
    }

    public SmartFrame getFrame() {
        return frame;
    }

    public void setFrame(SmartFrame frame) {
        this.frame = frame;
    }
}