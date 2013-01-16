package ro.mps.gui.screens.paragraph;

import ro.mps.data.concrete.Root;
import ro.mps.screen.concrete.BlockUsedInEditingScreen;
import ro.mps.gui.base.Screen;
import ro.mps.gui.screens.BottomPaneTemplate;
import ro.mps.screen.concrete.RootUsedInEditingScreen;
import ro.mps.gui.screens.Observer;
import ro.mps.gui.screens.Subject;
import ro.mps.screen.transformer.DataStructureTransformer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 18.11.2012
 * Time: 19:06
 */
public class ParagraphEditingScreen extends BottomPaneTemplate implements Observer, Subject {

    private static final String WINDOW_TITLE = "Edit Blocks";
    private List<ParagraphEntry> paragraphs;
    private JPanel containingPanel;
    private ParagraphPopupMenu popupMenu;
    private List<Observer> observers = new ArrayList<Observer>();

    /**
     * Builds the editing screen based on the root
     * @param root - root of the tree
     */
    public ParagraphEditingScreen(RootUsedInEditingScreen root) {
        super(WINDOW_TITLE, root);
        setPanel();
        addParagraphs(root.getTextFromParagraphs());
        super.addBottomPane();
    }

    public ParagraphEditingScreen(Root root) {
        this(DataStructureTransformer.transformRootToRootUsedInEditingScreen(root));
    }

    /**
     * Updates the screen based on the root
     * @param root - root of the tree
     */
    @Override
    public void update(RootUsedInEditingScreen root) {
        this.root = root;
        containingPanel.removeAll();
        addParagraphs(root.getTextFromParagraphs());
    }

    /**
     * Sets the panel
     */
    private void setPanel() {
        containingPanel = new JPanel();
        containingPanel.setSize(Screen.WINDOW_WIDTH - 50, Screen.WINDOW_HEIGHT - 150);
        containingPanel.setLayout(new BoxLayout(containingPanel, BoxLayout.Y_AXIS));
        containingPanel.setBorder(BorderFactory.createTitledBorder("Paragraphs"));
        super.addScrollPanel(containingPanel);
    }

    /**
     * Generates the paragraphs that can be edited
     */
    private void addParagraphs(List<String> paragraphsText) {
        paragraphs = new LinkedList<ParagraphEntry>();

        for (String paragraphText : paragraphsText) {
            ParagraphEntry paragraphEntry = new ParagraphEntry(paragraphText);
            paragraphs.add(paragraphEntry);
            setPopupMenuToJTextArea(paragraphEntry.getTextArea());
            containingPanel.add(paragraphEntry.getContainer());
        }
    }

    /**
     * Sets a popup menu to a text area
     *
     * @param textArea - text area
     */
    private void setPopupMenuToJTextArea(JTextArea textArea) {
        textArea.setComponentPopupMenu(getRightClickMenu());
        textArea.setEnabled(false);
    }

    /**
     * Returns the paragraph entry that are checked
     *
     * @return - returns a list of checked paragraphs
     */
    public List<ParagraphEntry> getCheckedParagraphs() {
        List<ParagraphEntry> checkedParagraphs = new LinkedList<ParagraphEntry>();

        for (ParagraphEntry paragraph : paragraphs) {
            JCheckBox checkBox = paragraph.getCheckBox();
            if (checkBox.isSelected()) {
                checkedParagraphs.add(paragraph);
                checkBox.setSelected(false);
            }
        }

        return checkedParagraphs;
    }

    /**
     * Removes one paragraph from container
     * @param paragraphToBeRemoved - paragraph that will be removed
     */
    private void removeParagraphFromContainer(ParagraphEntry paragraphToBeRemoved) {
        containingPanel.remove(paragraphToBeRemoved.getContainer());
        paragraphs.remove(paragraphToBeRemoved);
    }

    /**
     * Removes a block with all his children from data structure.
     * @param paragraphToBeRemoved - paragraphs that will be removed
     */
    private void removeBlockFromDataStructure(ParagraphEntry paragraphToBeRemoved) {
        int paragraphEntryIndex = getParagraphEntryIndex(paragraphToBeRemoved);
        containingPanel.remove(paragraphToBeRemoved.getContainer());
        root.removeChild(paragraphEntryIndex);
    }

    /**
     * Removes paragraph from data structure and UI screen.
     * @param paragraphEntries - paragraphs that will be removed.
     */
    public void removeParagraphs(List<ParagraphEntry> paragraphEntries) {
        for (ParagraphEntry paragraphEntry : paragraphEntries) {
            removeBlockFromDataStructure(paragraphEntry);
            removeParagraphFromContainer(paragraphEntry);
        }
    }

    /**
     * Removes paragraphs from the container
     * @param paragraphEntries - list of paragraphs that will be removed
     */
    public void removeParagraphsFromContainer(List<ParagraphEntry> paragraphEntries) {
        for (ParagraphEntry paragraphEntry : paragraphEntries) {
            removeParagraphFromContainer(paragraphEntry);
        }
    }

    /**
     * Sets text to a paragraph entry
     *
     * @param paragraph - paragraph entry
     * @param textToBeAdded - text for the paragraph
     */
    private void setTextToParagraphEntry(ParagraphEntry paragraph, String textToBeAdded) {
        JTextArea textArea = paragraph.getTextArea();
        textArea.setText(textToBeAdded);
    }

    /**
     * Merges paragraphs entry content
     *
     * @param paragraphs with merged content
     */
    public void mergeParagraphEntryContent(List<ParagraphEntry> paragraphs) {
        String textContent = "";

        if (paragraphs.size() == 0) {
            return;
        }

        for (ParagraphEntry paragraph : paragraphs) {
            JTextArea textArea = paragraph.getTextArea();
            textContent += textArea.getText();
        }

        ParagraphEntry paragraphWithMergedContent = paragraphs.get(0);
        setTextToParagraphEntry(paragraphWithMergedContent, textContent);
        paragraphs.remove(0);

        removeParagraphsFromContainer(paragraphs);
    }

    /**
     * Merges content of two paragraphs
     *
     * @param firstParagraph - first paragraph
     * @param secondParagraph - second paragraph
     * @return - returns true if paragraphs can be merged
     */
    public boolean mergeTwoParagraphEntryContent(ParagraphEntry firstParagraph, ParagraphEntry secondParagraph) {
        List<ParagraphEntry> paragraphEntries = new LinkedList<ParagraphEntry>();
        paragraphEntries.add(firstParagraph);
        paragraphEntries.add(secondParagraph);

        boolean paragraphsCanBeMerged = modifyDataStructureAfterMergeOperation(firstParagraph);
        if ( paragraphsCanBeMerged ) {
            mergeParagraphEntryContent(paragraphEntries);
        }

        return !paragraphsCanBeMerged;
    }

    /**
     * Modifies data structure after merge operation
     * @param paragraphEntry - paragraph entry
     * @return - returns true if merge can be done
     */
    private boolean modifyDataStructureAfterMergeOperation(ParagraphEntry paragraphEntry) {
        int paragraphEntryIndex = getParagraphEntryIndex(paragraphEntry);
        BlockUsedInEditingScreen firstBlock = root.getChild(paragraphEntryIndex);
        BlockUsedInEditingScreen secondBlock = root.getChild(paragraphEntryIndex + 1);

        return firstBlock.merge(secondBlock);
    }

    /**
     * Splits paragraphs entries content
     *
     * @param lineNumber - line number
     */
    public void splitParagraphEntriesContent(int lineNumber) {
        List<ParagraphEntry> checkedParagraphs = getCheckedParagraphs();

        for (ParagraphEntry checkedParagraph : checkedParagraphs) {
            splitParagraphEntryContent(checkedParagraph, lineNumber);
            modifyDataStructureAfterSplitOperation(checkedParagraph, lineNumber);
        }
    }

    /**
     * Updates the data structure after operation made in UI
     *
     * @param checkedParagraph - checked paragraphs
     * @param lineNumber - line number
     */
    private void modifyDataStructureAfterSplitOperation(ParagraphEntry checkedParagraph, int lineNumber) {
        int paragraphEntryIndex = getParagraphEntryIndex(checkedParagraph);
        BlockUsedInEditingScreen block = root.getChild(paragraphEntryIndex);
        block.split(lineNumber);
    }

    /**
     * Split paragraph entry content
     *
     * @param checkedParagraph - checked paragraphs
     * @param lineNumber - line number
     */
    public void splitParagraphEntryContent(ParagraphEntry checkedParagraph, int lineNumber) {
        JTextArea textArea = checkedParagraph.getTextArea();
        String paragraphText = textArea.getText();

        if (lineNumber < getNumberOfLines(paragraphText)) {
            textArea.setText(getFirstLines(paragraphText, lineNumber));
            ParagraphEntry newParagraph = new ParagraphEntry(getLastLines(paragraphText, lineNumber));
            int index = getParagraphEntryIndex(checkedParagraph) + 1;
            paragraphs.add(index, newParagraph);
            containingPanel.add(newParagraph.getContainer(), index);
            setPopupMenuToJTextArea(newParagraph.getTextArea());
        }

    }

    /**
     * Gets the paragraphEntry index from paragraphs entries list
     *
     * @param paragraphEntry - paragraph entry
     * @return - returns index of paragraph
     */
    private int getParagraphEntryIndex(ParagraphEntry paragraphEntry) {
        for (int i = 0; i < paragraphs.size(); i++) {
            if (paragraphs.get(i) == paragraphEntry) {
                return i;
            }
        }

        return -1;
    }

    public ParagraphEntry getParagraphAtIndex(int index) {
        return paragraphs.get(index);
    }

    public boolean isParagraphTheFirst(int index) {
        return index == 0;
    }

    public boolean isParagraphTheLast(int index) {
        return index == paragraphs.size() - 1;
    }

    /**
     * Returns the number of lines from a text
     *
     * @param text - text
     * @return - returns number of line from a text
     */
    public int getNumberOfLines(String text) {
        int numberOfAppearances = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                numberOfAppearances++;
            }
        }

        return numberOfAppearances;
    }

    /**
     * Returns lines from 0 to lineNumber
     *
     * @param paragraphText - paragraph text
     * @param lineNumber - line number
     * @return - returns line from 0 to lineNumber
     */
    private String getFirstLines(String paragraphText, int lineNumber) {
        int endPosition = getIndexForSplitting(paragraphText, lineNumber);
        return paragraphText.substring(0, endPosition);
    }

    /**
     * Returns lines from lineNumber to number of lines
     *
     * @param paragraphText - paragraph text
     * @param lineNumber - line number
     * @return - returns line from 0 to lineNumber
     */
    private String getLastLines(String paragraphText, int lineNumber) {
        int startPosition = getIndexForSplitting(paragraphText, lineNumber);
        return paragraphText.substring(startPosition);
    }

    /**
     * Returns the index where the split should start
     *
     * @param paragraphText - paragraph text
     * @param lineNumber - line number
     * @return - returns the position from where split should start
     */
    private int getIndexForSplitting(String paragraphText, int lineNumber) {
        final String LINE_SEPARATOR_TEXT = "line.separator";
        int position = 0;
        int lineSeparatorLength = System.getProperty(LINE_SEPARATOR_TEXT).length();

        for (int i = 0; i < lineNumber; i++) {
            position = paragraphText.indexOf(System.getProperty(LINE_SEPARATOR_TEXT), position) + lineSeparatorLength;
        }

        return position;
    }

    /**
     * Gets the menu for the right click
     */
    public ParagraphPopupMenu getPopupMenu() {
        return popupMenu != null ? popupMenu : new ParagraphPopupMenu(this);
    }

    public JPopupMenu getRightClickMenu() {
        return getPopupMenu().getRightClickMenu();
    }

    public JPanel getContainingPanel() {
        return containingPanel;
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
