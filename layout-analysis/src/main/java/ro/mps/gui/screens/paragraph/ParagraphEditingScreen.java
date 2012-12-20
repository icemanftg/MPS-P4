package ro.mps.gui.screens.paragraph;

import ro.mps.data.concrete.Block;
import ro.mps.gui.base.Screen;
import ro.mps.gui.screens.BottomPaneTemplate;
import ro.mps.data.concrete.Root;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 18.11.2012
 * Time: 19:06
 */
public class ParagraphEditingScreen extends BottomPaneTemplate {

    private static final String WINDOW_TITLE = "Edit Blocks";
    private List<ParagraphEntry> paragraphs;
    private Root root;
    private JPanel containingPanel;
    private ParagraphPopupMenu popupMenu;

    public ParagraphEditingScreen(List<String> paragraphsText) {
        super(WINDOW_TITLE);
        setPanel();
        addParagraphs(paragraphsText);
        super.addBottomPane();
    }

    public ParagraphEditingScreen(Root root) {
        this(root.getTextFromParagraphs());
        this.root = root;
    }

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
     * @param textArea with the popup menu set
     */
    private void setPopupMenuToJTextArea(JTextArea textArea) {
        textArea.setComponentPopupMenu(getRightClickMenu());
    }

    /**
     * Returns the paragraph entry that are checked
     *
     * @return
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
     * Removes paragraphs from the container
     *
     * @param paragraphsToBeRemoved
     */
    private void removeParagraphsFromContainer(List<ParagraphEntry> paragraphsToBeRemoved) {
        for (ParagraphEntry paragraphToBeRemoved : paragraphsToBeRemoved) {
            containingPanel.remove(paragraphToBeRemoved.getContainer());
            paragraphs.remove(paragraphToBeRemoved);
        }
    }

    private void removeBlocksFromDataStructure(List<ParagraphEntry> paragraphsToBeRemoved) {
        for (ParagraphEntry paragraphToBeRemoved : paragraphsToBeRemoved) {
            int paragraphEntryIndex = getParagraphEntryIndex(paragraphToBeRemoved);
            containingPanel.remove(paragraphToBeRemoved.getContainer());
            root.removeChild(paragraphEntryIndex);
        }
    }

    public void removeParagraphs(List<ParagraphEntry> paragraphEntries) {
        removeParagraphsFromContainer(paragraphEntries);
        removeBlocksFromDataStructure(paragraphEntries);
    }

    /**
     * Sets text to a paragraph entry
     *
     * @param paragraph
     * @param textToBeAdded
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
            textContent = addNewLineCharacter(textContent);
            textContent += textArea.getText();
        }

        ParagraphEntry paragraphWithMergedContent = paragraphs.get(0);
        setTextToParagraphEntry(paragraphWithMergedContent, textContent);
        paragraphs.remove(0);

        removeParagraphsFromContainer(paragraphs);
    }

    public void mergeTwoParagraphEntryContent(ParagraphEntry firstParagraph, ParagraphEntry secondParagraph) {
        List<ParagraphEntry> paragraphEntries = new LinkedList<ParagraphEntry>();
        paragraphEntries.add(firstParagraph);
        paragraphEntries.add(secondParagraph);
        mergeParagraphEntryContent(paragraphEntries);
    }

    private void modifyDataStructureAfterMergeOperation(ParagraphEntry paragraphEntry) {
        int paragraphEntryIndex = getParagraphEntryIndex(paragraphEntry);
        Block firstBlock = root.getChild(paragraphEntryIndex);
        Block secondBlock = root.getChild(paragraphEntryIndex + 1);

        firstBlock.merge(secondBlock);
    }

    /**
     * Splits paragraphs entries content
     *
     * @param lineNumber
     */
    public void splitParagraphEntriesContent(int lineNumber) {
        List<ParagraphEntry> checkedParagraphs = getCheckedParagraphs();

        for (ParagraphEntry checkedParagraph : checkedParagraphs) {
            splitParagraphEntryContent(checkedParagraph, lineNumber);
            modifyDataStructureAfterSplitOperation(checkedParagraph, lineNumber);
        }
    }

    private void modifyDataStructureAfterSplitOperation(ParagraphEntry checkedParagraph, int lineNumber) {
        int paragraphEntryIndex = getParagraphEntryIndex(checkedParagraph);
        Block block = root.getChild(paragraphEntryIndex);
        block.split(lineNumber);
    }

    /**
     * Split paragraph entry content
     *
     * @param checkedParagraph
     * @param lineNumber
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
     * @param paragraphEntry
     * @return
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
     * Returns the number of lines of a text
     *
     * @param text
     * @return
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
     * @param paragraphText
     * @param lineNumber
     * @return
     */
    private String getFirstLines(String paragraphText, int lineNumber) {
        int endPosition = getIndexForSplitting(paragraphText, lineNumber);
        return paragraphText.substring(0, endPosition - 1);
    }

    /**
     * Returns lines from lineNumber to number of lines
     *
     * @param paragraphText
     * @param lineNumber
     * @return
     */
    private String getLastLines(String paragraphText, int lineNumber) {
        int startPosition = getIndexForSplitting(paragraphText, lineNumber);
        return paragraphText.substring(startPosition);
    }

    /**
     * Returns the index where the split should start
     *
     * @param paragraphText
     * @param lineNumber
     * @return
     */
    private int getIndexForSplitting(String paragraphText, int lineNumber) {
        int position = 0;

        for (int i = 0; i < lineNumber; i++) {
            position = paragraphText.indexOf('\n', position) + 1;
        }

        return position;
    }

    /**
     * Appends newline character if text is not empty
     *
     * @param text
     * @return
     */
    private String addNewLineCharacter(String text) {
        final String LINE_SEPARATOR_TEXT = "line.separator";

        if (!text.isEmpty()) {
            text += System.getProperty(LINE_SEPARATOR_TEXT);
        }

        return text;
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
}
