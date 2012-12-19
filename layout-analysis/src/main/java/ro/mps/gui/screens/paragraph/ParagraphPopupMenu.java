package ro.mps.gui.screens.paragraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 24.11.2012
 * Time: 13:38
 */
public class ParagraphPopupMenu {
    private static final String MERGE_SELECTED = "Merge selected";
    private static final String SPLIT_AT_LINE = "Split at line";
    private static final String DELETE_SELECTED = "Delete selected";
    private static final String EDIT_PARAGRAPH = "Edit paragraph";
    private static final String SPLIT = "Split";

    private JPopupMenu rightClickMenu;
    private PositionSpinner positionSpinner;

    public ParagraphPopupMenu(ParagraphEditingScreen paragraphEditingScreen) {
        buildPopupMenu(paragraphEditingScreen);
    }

    public JPopupMenu getRightClickMenu() {
        return rightClickMenu;
    }

    /**
     * Builds the right click menu
     *
     * @return right click menu
     */
    private JPopupMenu buildPopupMenu(ParagraphEditingScreen paragraphEditingScreen) {
        rightClickMenu = new JPopupMenu(EDIT_PARAGRAPH);
        JMenuItem menuItem;

        menuItem = new JMenuItem(MERGE_SELECTED);
        menuItem.addActionListener(new RightClickActionListener(paragraphEditingScreen));
        rightClickMenu.add(menuItem);

        JMenu splitAt = new JMenu(SPLIT_AT_LINE);
        splitAt.add(getSplitContainer(paragraphEditingScreen));
        rightClickMenu.add(splitAt);

        rightClickMenu.addSeparator();
        menuItem = new JMenuItem(DELETE_SELECTED);
        menuItem.addActionListener(new RightClickActionListener(paragraphEditingScreen));
        rightClickMenu.add(menuItem);

        return rightClickMenu;
    }

    /**
     * Builds a panel that contains a spinner and a button
     * @return panel
     */
    private JPanel getSplitContainer(ParagraphEditingScreen paragraphEditingScreen) {
        JPanel splitContainer = new JPanel();
        JButton button = new JButton(SPLIT);
        positionSpinner = new PositionSpinner();
        splitContainer.setLayout(new FlowLayout());
        splitContainer.add(positionSpinner);
        button.addActionListener(new SplitActionListener(paragraphEditingScreen));
        splitContainer.add(button);

        return splitContainer;
    }

    class PositionSpinner extends JSpinner {
        PositionSpinner(int max) {
            super(new SpinnerNumberModel(1, 1, max, 1));
        }

        PositionSpinner() {
            this(100);
        }

        public SpinnerModel getSpinnerNumberModel() {
            return super.getModel();
        }
    }

    /**
     * Listener for the popup menu
     */
    class RightClickActionListener implements ActionListener {
        ParagraphEditingScreen paragraphEditingScreen;

        RightClickActionListener(ParagraphEditingScreen paragraphEditingScreen) {
            this.paragraphEditingScreen = paragraphEditingScreen;
        }

        private void repaintMyPanel() {
            paragraphEditingScreen.getContainingPanel().repaint();
            paragraphEditingScreen.repaintScrollPane();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(DELETE_SELECTED)) {
                paragraphEditingScreen.removeParagraphsFromContainer(paragraphEditingScreen.getCheckedParagraphs());
            }

            if (e.getActionCommand().equals(MERGE_SELECTED)) {
                paragraphEditingScreen.mergeParagraphEntryContent(paragraphEditingScreen.getCheckedParagraphs());
            }

            repaintMyPanel();
        }
    }

    /**
     * Listener for the split button
     */
    class SplitActionListener implements ActionListener {
        ParagraphEditingScreen paragraphEditingScreen;

        SplitActionListener(ParagraphEditingScreen paragraphEditingScreen) {
            this.paragraphEditingScreen = paragraphEditingScreen;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SpinnerModel spinnerNumberModel = positionSpinner.getSpinnerNumberModel();
            int lineNumber = Integer.parseInt(spinnerNumberModel.getValue().toString());
            paragraphEditingScreen.splitParagraphEntriesContent(lineNumber);

        }
    }
}
