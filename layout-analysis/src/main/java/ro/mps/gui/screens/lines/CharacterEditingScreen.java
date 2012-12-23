package ro.mps.gui.screens.lines;

import ro.mps.data.concrete.Root;
import ro.mps.gui.base.Screen;
import ro.mps.gui.screens.BottomPaneTemplate;

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
    private static final String WINDOW_TILE = "Edit characters in lines";
    private Root root;
    protected List<JTextField> lines;
    protected JPanel containingPanel;


    public CharacterEditingScreen(List<String> textLines) {
        super(WINDOW_TILE);
        addLines(textLines);
        super.addBottomPane();
    }

    public CharacterEditingScreen(Root root) {
        this(root.getTextFromLines());
        this.root = root;
    }

    public JPanel getContainingPanel() {
        return containingPanel;
    }

    /**
     * Generates the text lines that can be edited.
     */
    private void addLines(List<String> textLines) {
        lines = new LinkedList<JTextField>();
        containingPanel = new JPanel();
        containingPanel.setSize(Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT);
        containingPanel.setLayout(new BoxLayout(containingPanel, BoxLayout.Y_AXIS));

        for (String textLine : textLines) {
            JTextField textField = new JTextField(textLine);
            containingPanel.add(textField);
            lines.add(textField);
        }

        containingPanel.setBorder(BorderFactory.createTitledBorder("Lines"));
        super.addScrollPanel(containingPanel);
    }

    /**
     * Returns the index of a component from the panel
     *
     * @param searchedComponent
     * @return index
     */
    public int getComponentIndex(Component searchedComponent) {
        Component[] components = containingPanel.getComponents();
        int counter = 0;

        for (Component component : components) {
            if (searchedComponent == component) {
                return counter;
            }
            counter++;
        }

        return -1;
    }

    /**
     * Returns the number of components
     *
     * @return
     */
    public int getNumberOfComponents() {
        return lines.size();
    }
}
