package ro.mps.gui.screens;

import ro.mps.gui.base.Screen;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 18.11.2012
 * Time: 19:06
 */
public class ParagraphEditingScreen extends BottomPaneTemplate {

    private static final String MERGE_WITH_PREVIOUS_LINE = "Merge selected";
    private static final String SPLIT_AT_LINE = "Split at line";
    private static final String DELETE_SELECTED = "Delete selected";
    private static final String WINDOW_TITLE = "Edit Blocks";
    private static final String EDIT_PARAGRAPH = "Edit paragraph";
    private static final int NUMBER_OF_PARAGRAPHS = 200;

    private JPopupMenu popupMenu;
    private List<JTextArea> paragraphs;
    private List<JCheckBox> checkBoxes;

    public ParagraphEditingScreen() {
        super(WINDOW_TITLE);
        addParagraphs();

//        super.addBottomPane();
    }

    /**
     * Generates the paragraphs that can be edited
     */
    private void addParagraphs() {
        paragraphs = new LinkedList<JTextArea>();
        checkBoxes = new LinkedList<JCheckBox>();

        JPanel containingPanel = new JPanel();
        containingPanel.setSize(Screen.WINDOW_WIDTH - 50, Screen.WINDOW_HEIGHT -150);
        containingPanel.setLayout(new BoxLayout(containingPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < NUMBER_OF_PARAGRAPHS; i++) {
            JTextArea textArea = makeJTextArea(String.valueOf(i));
            JCheckBox checkBox = new JCheckBox();

            containingPanel.add(textArea);
            containingPanel.add(checkBox);

            checkBoxes.add(checkBox);
            paragraphs.add(textArea);
        }

        containingPanel.setBorder(BorderFactory.createTitledBorder("Paragraphs"));
        super.addScrollPanel(containingPanel);
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
        textArea.setComponentPopupMenu(getRightClickMenu());

        return textArea;
    }

    /**
     * Builds the right click menu
     *
     * @return right click menu
     */
    private JPopupMenu buildPopupMenu() {
        popupMenu = new JPopupMenu(EDIT_PARAGRAPH);
        JMenuItem menuItem;

        menuItem = new JMenuItem(MERGE_WITH_PREVIOUS_LINE);
        menuItem.addActionListener(new RightClickActionListener());
        popupMenu.add(menuItem);

        JMenu splitAt = new JMenu(SPLIT_AT_LINE);
        splitAt.add(new PositionSpinner());
        popupMenu.add(splitAt);

        popupMenu.addSeparator();
        menuItem = new JMenuItem(DELETE_SELECTED);
        menuItem.addActionListener(new RightClickActionListener());
        popupMenu.add(menuItem);

        return popupMenu;
    }

    class RightClickActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: implement right click logic
            System.out.println(e.getActionCommand());
        }
    }

    class PositionSpinner extends JSpinner {
        PositionSpinner(int max) {
            super(new SpinnerNumberModel(1, 1, max, 1));
            this.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    //TODO: implement page changing logic
                    System.out.println(((JSpinner) e.getSource()).getValue());
                }
            }
            );
        }

        PositionSpinner() {
            this(100);
        }
    }

    /**
     * Gets the menu for the right click
     */
    public JPopupMenu getRightClickMenu() {
        return popupMenu != null ? popupMenu : buildPopupMenu();
    }
}
