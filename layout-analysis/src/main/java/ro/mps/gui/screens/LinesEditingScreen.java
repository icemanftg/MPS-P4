package ro.mps.gui.screens;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Alexandru Burghelea
 * Date: 11/17/12
 * Time: 7:31 PM
 */
public class LinesEditingScreen extends CharacterEditingScreen {

    private static final String WINDOW_TITLE = "Edit lines in blocks";
    private static final String MERGE_WITH_NEXT_LINE = "Merge with next line";
    private static final String MERGE_WITH_PREVIOUS_LINE = "Merge with previous line";
    private static final String SPLIT_AT_POSITION = "Split at position";
    private static final String EDIT_LINES = "Edit lines";

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

    private JPopupMenu popupMenu;

    public LinesEditingScreen() {
        super();
        for (JTextField textLine : textLines) {
            textLine.setEditable(false);
            textLine.setComponentPopupMenu(getRightClickMenu());
        }
    }

    /**
     * Builds the right click menu
     *
     * @return right click menu
     */
    private JPopupMenu buildPopupMenu() {
        popupMenu = new JPopupMenu(EDIT_LINES);
        JMenuItem menuItem;

        JMenu splitAt = new JMenu(SPLIT_AT_POSITION);
        splitAt.add(new PositionSpinner());
        popupMenu.add(splitAt);
        popupMenu.addSeparator();


        menuItem = new JMenuItem(MERGE_WITH_PREVIOUS_LINE);
        menuItem.addActionListener(new RightClickActionListener());
        popupMenu.add(menuItem);
        menuItem = new JMenuItem(MERGE_WITH_NEXT_LINE);
        menuItem.addActionListener(new RightClickActionListener());
        popupMenu.add(menuItem);
        return popupMenu;
    }


    /**
     * Gets the menu for the right click
     */
    public JPopupMenu getRightClickMenu() {
        return popupMenu != null ? popupMenu : buildPopupMenu();
    }

    @Override
    public String getWindowTitle() {
        return WINDOW_TITLE;
    }
}
