package ro.mps.gui.screens.lines;

import ro.mps.data.concrete.Line;
import ro.mps.data.concrete.Root;
import ro.mps.gui.base.Screen;
import ro.mps.gui.screens.BottomPaneTemplate;
import ro.mps.gui.screens.Observer;
import ro.mps.gui.screens.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Alexandru Burghelea
 * Date: 11/17/12
 * Time: 5:52 PM
 */
public class CharacterEditingScreen extends BottomPaneTemplate implements Observer, Subject {
    private static final String WINDOW_TILE = "Edit characters in textFields";
    protected Root root;
    protected List<JTextField> textFields;
    protected List<Line> lines;
    protected JPanel containingPanel;
    private List<Observer> observers = new ArrayList<Observer>();

    public CharacterEditingScreen(List<String> textLines) {
        super(WINDOW_TILE);
        initPanel();
        addLines(textLines);
        super.addBottomPane();
    }

    public CharacterEditingScreen(Root root) {
        this(root.getTextFromLines());
        this.lines = root.getLines();
        this.root = root;
    }

    @Override
    public void update(Root root) {
        this.root = root;
        this.lines = root.getLines();
        containingPanel.removeAll();
        addLines(root.getTextFromLines());
    }

    public JPanel getContainingPanel() {
        return containingPanel;
    }

    public Root getRoot() {
        return root;
    }

    private void initPanel() {
        containingPanel = new JPanel();
        containingPanel.setSize(Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT);
        containingPanel.setLayout(new BoxLayout(containingPanel, BoxLayout.Y_AXIS));
        super.addScrollPanel(containingPanel);
    }

    /**
     * Generates the text textFields that can be edited.
     */
    protected void addLines(List<String> textLines) {
        textFields = new LinkedList<JTextField>();

        for (String textLine : textLines) {
            JTextField textField = new JTextField(textLine);
            containingPanel.add(textField);
            textFields.add(textField);
            textField.addFocusListener(new CharacterListener(this));
        }

        containingPanel.setBorder(BorderFactory.createTitledBorder("Lines"));
    }

    /**
     * Returns the index of a component from the panel
     *
     * @param searchedComponent - searched component
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
     * @return - returns the number of components
     */
    public int getNumberOfComponents() {
        return textFields.size();
    }

    class CharacterListener implements FocusListener {
        CharacterEditingScreen characterEditingScreen;

        CharacterListener(CharacterEditingScreen characterEditingScreen) {
            this.characterEditingScreen = characterEditingScreen;
        }

        private void showInfoAboutDataStructure() {
            System.out.println("------------------------------------------");
            System.out.println(characterEditingScreen.getRoot());
        }

        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField textField = (JTextField)e.getComponent();
            int componentIndex = characterEditingScreen.getComponentIndex(textField);

            lines.get(componentIndex).setContent(textField.getText());
            showInfoAboutDataStructure();
            characterEditingScreen.notifyObservers();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for ( Observer observer : observers ) {
            observer.update(root);
        }
    }
}
