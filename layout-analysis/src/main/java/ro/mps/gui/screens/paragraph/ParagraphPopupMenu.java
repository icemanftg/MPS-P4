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
    private static final String MERGE_WITH_PREVIOUS = "Merge with previous paragraph";
    private static final String MERGE_WITH_NEXT = "Merge with next paragraph";
    private static final String SPLIT_AT_LINE = "Split at line";
    private static final String DELETE_SELECTED = "Delete selected";
    private static final String EDIT_PARAGRAPH = "Edit paragraph";
    private static final String SPLIT = "Split";
    private static final String ERROR_MESSAGE = "Paragraphs can't be merged!";

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
     * @return - returns right click menu
     */
    private JPopupMenu buildPopupMenu(ParagraphEditingScreen paragraphEditingScreen) {
        rightClickMenu = new JPopupMenu(EDIT_PARAGRAPH);
        JMenuItem menuItem;

        menuItem = new JMenuItem(MERGE_WITH_PREVIOUS);
        menuItem.addActionListener(new RightClickActionListener(paragraphEditingScreen));
        rightClickMenu.add(menuItem);

        menuItem = new JMenuItem(MERGE_WITH_NEXT);
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
     *
     * @return - returns the panel
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

        /**
         * Returns the component that triggered the event
         *
         * @param event - action event
         * @return - returns componet that triggerd the event
         */
        private Component getInvoker(ActionEvent event) {
            JMenuItem source = (JMenuItem) event.getSource();
            JPopupMenu jPopupMenu = (JPopupMenu) source.getParent();

            return jPopupMenu.getInvoker().getParent();
        }

        private boolean mergeParagraphs(int indexOfFirstParagraph, int indexOfSecondParagraph) {
            ParagraphEntry firstParagraphEntry = paragraphEditingScreen.getParagraphAtIndex(indexOfFirstParagraph);
            ParagraphEntry secondParagraphEntry = paragraphEditingScreen.getParagraphAtIndex(indexOfSecondParagraph);
            return paragraphEditingScreen.mergeTwoParagraphEntryContent(firstParagraphEntry, secondParagraphEntry);
        }

        private void showInfoAboutDataStructure() {
            System.out.println("------------------------------------------");
            System.out.println(paragraphEditingScreen.getRootUsedInEditingScreen());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Component jPopupMenu = getInvoker(e);
            JPanel containingPanel = paragraphEditingScreen.getContainingPanel();
            int componentIndex = getComponentIndex(containingPanel.getComponents(), jPopupMenu);
            boolean displayMessage = false;

            if (e.getActionCommand().equals(DELETE_SELECTED)) {
                paragraphEditingScreen.removeParagraphs(paragraphEditingScreen.getCheckedParagraphs());
            }

            if (e.getActionCommand().equals(MERGE_WITH_PREVIOUS) &&
                    !paragraphEditingScreen.isParagraphTheFirst(componentIndex)) {
                displayMessage = mergeParagraphs(componentIndex - 1, componentIndex);
            }

            if (e.getActionCommand().equals(MERGE_WITH_NEXT) &&
                    !paragraphEditingScreen.isParagraphTheLast(componentIndex)) {
                displayMessage = mergeParagraphs(componentIndex, componentIndex + 1);
            }

            if ( displayMessage ) {
                JOptionPane.showMessageDialog(paragraphEditingScreen, ERROR_MESSAGE);
            }

            showInfoAboutDataStructure();
            repaintMyPanel();
            paragraphEditingScreen.notifyObservers();
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

        private void showInfoAboutDataStructure() {
            System.out.println("------------------------------------------");
            System.out.println(paragraphEditingScreen.getRootUsedInEditingScreen());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SpinnerModel spinnerNumberModel = positionSpinner.getSpinnerNumberModel();
            int lineNumber = Integer.parseInt(spinnerNumberModel.getValue().toString());
            paragraphEditingScreen.splitParagraphEntriesContent(lineNumber);
            showInfoAboutDataStructure();
            paragraphEditingScreen.notifyObservers();
        }
    }

    /**
     * Returns the index of a component from the panel
     * @param components - components
     * @param searchedComponent - searched component
     * @return - returns index of the searched component
     */
    public int getComponentIndex(Component[] components, Component searchedComponent) {
        int counter = 0;

        for (Component component : components) {
            if (searchedComponent == component) {
                return counter;
            }
            counter++;
        }

        return -1;
    }
}
