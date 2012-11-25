package ro.mps.gui.screens.paragraph;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 24.11.2012
 * Time: 13:18
 */
public class ParagraphEntry {
    private JTextArea textArea;
    private JCheckBox checkBox;
    private JPanel container;

    public ParagraphEntry(String text) {
        container = makeInnerContainer(text);
    }

    /**
     * Generate a container that has a textarea and a checkbox
     * @param text
     * @return
     */
    private JPanel makeInnerContainer(String text) {
        textArea = makeJTextArea(text);
        textArea.setColumns(40);
        textArea.setRows(5);
        JPanel innerContainer = new JPanel();
        innerContainer.setLayout(new FlowLayout());
        checkBox = new JCheckBox();

        innerContainer.add(checkBox);
        innerContainer.add(textArea);

        return innerContainer;
    }

    /**
     * Builds a text area
     *
     * @param text
     * @return text area
     */
    private JTextArea makeJTextArea(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        return textArea;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    public JPanel getContainer() {
        return container;
    }
}
