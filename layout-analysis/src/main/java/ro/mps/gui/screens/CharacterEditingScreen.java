package ro.mps.gui.screens;

import ro.mps.gui.base.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Alexandru Burghelea
 * Date: 11/17/12
 * Time: 5:52 PM
 */
public class CharacterEditingScreen extends BottomPaneTemplate {

    private static final Integer LINES = 300;
    private static final String WINDOW_TILE = "Edit characters in lines";
    protected List<JTextField> textLines;

    public CharacterEditingScreen() {
        super(WINDOW_TILE);
        addLines();
        super.addBottomPane();
    }

    /**
     * Generates the text lines that can be edited.
     */
    private void addLines() {
        textLines = new LinkedList<JTextField>();
        JPanel containingPanel = new JPanel();
        containingPanel.setSize(Screen.WINDOW_WIDTH,Screen.WINDOW_HEIGHT);
        containingPanel.setLayout(new BoxLayout(containingPanel,BoxLayout.Y_AXIS));

        for (int i = 0 ; i < LINES; i++){
            JTextField linie = new JTextField(String.valueOf(i),60);
            containingPanel.add(linie);
            textLines.add(linie);
        }

        containingPanel.setBorder(BorderFactory.createTitledBorder("Lines"));
        super.addScrollPanel(containingPanel);
    }
}
