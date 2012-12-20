package ro.mps.smart.textbox;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import ro.mps.data.api.Compound;
import ro.mps.data.base.Node;
import ro.mps.data.concrete.Block;
import ro.mps.data.concrete.Line;
import ro.mps.smart.frame.SmartFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author alexandra
 */
public class SmartTextBox extends Box {

    private JTextArea textArea;
    private ComponentResizer resizer;
    private ComponentMover mover;
    private String content, visibleContent;
    private JLabel jl;
    private ArrayList<Line> lines;
    private SmartFrame frame;

    public SmartTextBox(int w, int h) {
        super(BoxLayout.PAGE_AXIS);

        setBounds(0, 0, w, h);

        Container cp = new Container();
        cp.setLayout(new FlowLayout(FlowLayout.RIGHT));

        jl = new JLabel("\n");
        cp.add(jl);
        add(cp);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(w, h - 20));
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                content = textArea.getText();
                updateLineContent(content, true);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                content = textArea.getText();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                content = textArea.getText();
            }
        });

        add(textArea);

        lines = new ArrayList<Line>();

        setPreferredSize(new Dimension(w, h));
        setBorder(BorderFactory.createLineBorder(Color.black));

        resizer = new ComponentResizer(this);
        mover = new ComponentMover(this, cp);
    }

    public void handleResize() throws Exception {
        textArea.setPreferredSize(new Dimension(getWidth(), getHeight() - jl.getHeight()));
        textArea.setMaximumSize(textArea.getPreferredSize());
        setBorder(BorderFactory.createLineBorder(Color.black));

        textArea.setMargin(new Insets(0, 0, 0, 0));

        checkIntersections();

        String message = "";

        if (lines.isEmpty()) {
            message = "No node";
        } else {
            message = "Containing node " + lines.get(0).getParent() + "\n";
            for (Line line : lines) {
                message += "\tLine: " + line + " :: " + line.getContent() + "\n";
            }
        }

        Logger.getLogger(getClass().getName()).log(Level.INFO, message);
    }

    public void handleMove() throws Exception {
        checkIntersections();
    }

    private void checkIntersections() throws Exception {
        Node me = (Node) lines.get(0).getParent();
        //am i inside?
        for (Node node : getFrame().getChildren()) {
            if (node.equals(me)) {
                continue;
            }

            if (node.inside(me.getLeftUpperCorner())
                    && node.inside(me.getLeftUpperCornerX() + me.getWidth(), me.getLeftUpperCornerY())
                    && node.inside(me.getLeftUpperCornerX() + me.getWidth(), me.getLeftUpperCornerY() + me.getHeight())
                    && node.inside(me.getLeftUpperCornerX(), me.getLeftUpperCornerY() + me.getHeight())) {
                // node.merge(me) sau ceva
                continue;
            }

            // se poate face merge daca m-am intins in dreapta si/sau in jos.

            if (me.inside(node.getLeftUpperCorner())) {
                // node.merge(me) sau ceva
                continue;
            }

            throw new Exception("Invalid resize!");
        }
    }

    private void updateLineContent(String content, boolean rendered) {
        String[] lineContents = content.split("\n");

        if (lineContents.length == 0) {
            lines.clear();
            return;
        }

        Block block;

        if (lines.isEmpty()) {
            block = new Block(
                    textArea.getX(),
                    textArea.getY(),
                    textArea.getWidth(),
                    textArea.getHeight());
//                    rendered ? lineWidth(lineContents[0]) : textArea.getWidth(),
//                    rendered ? lineHeight(lineContents[0]) : textArea.getHeight());
            getFrame().add(block);
        } else {
            block = (Block) lines.get(0).getParent();
        }

        lines.clear();

        Line line = new Line(
                textArea.getX(),
                textArea.getY(),
                rendered ? lineWidth(lineContents[0]) : textArea.getWidth(),
                rendered ? lineHeight(lineContents[0]) : textArea.getHeight());
        line.setContent(lineContents[0]);
        line.setParent(block);
        block.getChildren().add(line);
        lines.add(line);

        for (int i = 1; i < lineContents.length; ++i) {
            Line l = new Line(
                    lines.get(i - 1).getLeftUpperCornerX(),
                    lines.get(i - 1).getLeftUpperCornerY() + lines.get(i - 1).getHeight(),
                    rendered ? lineWidth(lineContents[i]) : textArea.getWidth(),
                    rendered ? lineHeight(lineContents[i]) : textArea.getHeight()
            );
            l.setParent(lines.get(i - 1).getParent());
            l.getParent().getChildren().add(l);
            l.setContent(lineContents[i]);
            lines.add(l);
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
        textArea.setText(text);
        content = text;
        updateLineContent(content, false);
    }

    public String[] getLineContents() {
        return new String[lines.size()];
    }

    public String[] getWords() {
        ArrayList<String> words = new ArrayList<String>();
        for (Line line : lines) {
            Collections.addAll(words, line.getContent().split(" ,.:;()[]/"));
        }
        return words.toArray(new String[words.size()]);
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    private void setLines(ArrayList<Line> lines) {
        this.lines = lines;
        content = "";
        for (Line line : lines) {
            content += line.getContent() + "\n";
        }
        content = content.trim();
    }

    private void add(Node node) {
        if (node instanceof Line) {
            Line line = (Line) node;
            lines.add(line);
            content += "\n" + line.getContent();
        }
    }

    public void add(Compound<Node> textAreaound) {
        for (Node node : textAreaound.getChildren()) {
            add(node);
        }
    }

    public SmartFrame getFrame() {
        return frame;
    }

    public void setFrame(SmartFrame frame) {
        this.frame = frame;
    }
}