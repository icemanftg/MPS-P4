/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.mps.move_resize;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import ro.mps.data.concrete.Root;

/**
 *
 * @author Alexandra
 */
public class ResizeController implements ActionListener {

    private JFrame frame;
    private JRadioButton buttonBlock, buttonLine;
    private JButton exportXml;
    private Root root;

    public ResizeController(Root root) {
        frame = new JFrame();
        frame.setSize(300, 200);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(1, 2));

        this.root = root;

        buttonBlock = new JRadioButton("Block", false);
        buttonBlock.addActionListener(this);

        buttonLine = new JRadioButton("Line", false);
        buttonLine.addActionListener(this);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(buttonLine);
        buttonGroup.add(buttonBlock);

        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.add(buttonBlock);
        buttonBox.add(buttonLine);

        frame.add(buttonBox);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        exportXml = new JButton("Export to XML");
        exportXml.addActionListener(this);

        panel.add(exportXml);
        frame.add(panel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonBlock)) {
            RootResizeProcessor.setBlock(true);
            root = RootResizeProcessor.process(root);
            return;
        }
        if (e.getSource().equals(buttonLine)) {
            RootResizeProcessor.setBlock(false);
            root = RootResizeProcessor.process(root);
            return;
        }
        if (e.getSource().equals(exportXml)) {
            return;
        }
    }
}