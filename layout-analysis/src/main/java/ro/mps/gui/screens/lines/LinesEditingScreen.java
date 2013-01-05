package ro.mps.gui.screens.lines;

import ro.mps.data.api.Compound;
import ro.mps.data.concrete.Line;
import ro.mps.data.concrete.Root;
import ro.mps.gui.screens.Observer;
import ro.mps.gui.screens.Subject;

import javax.swing.*;

/**
 * User: Alexandru Burghelea
 * Date: 11/17/12
 * Time: 7:31 PM
 */
public class LinesEditingScreen extends CharacterEditingScreen implements Observer, Subject {
    private static final String WINDOW_TITLE = "Edit lines in blocks";
    private LinesPopupMenu popupMenu;

    public LinesEditingScreen(Root root) {
        super(root);
        makeTextNonEditable();
    }

    private void makeTextNonEditable() {
        for (JTextField textLine : textFields) {
            textLine.setEditable(false);
            textLine.setComponentPopupMenu(getRightClickMenu());
        }
    }

    @Override
    public void update(Root root) {
        this.root = root;
        this.lines = root.getLines();
        containingPanel.removeAll();
        this.addLines(root.getTextFromLines());
        makeTextNonEditable();
    }

    /**
     * Gets the menu for the right click
     */
    public LinesPopupMenu getPopupMenu() {
        return popupMenu != null ? popupMenu : new LinesPopupMenu(this);
    }

    /**
     * Returns right click menu
     *
     * @return
     */
    public JPopupMenu getRightClickMenu() {
        return getPopupMenu().getRightClickMenu();
    }

    /**
     * Merge a line with the previous one
     *
     * @param index of line
     */
    public boolean mergeWithPreviousLine(int index) {
        return mergeLines(index - 1, index);
    }

    /**
     * Merge a line with the next one
     *
     * @param index of line
     */
    public boolean mergeWithNextLine(int index) {
        return mergeLines(index, index + 1);
    }

    private boolean mergeLines(int indexOfFirstLine, int indexOfSecondLine) {
        if (couldLineBeMerged(indexOfFirstLine, indexOfSecondLine)) {
            adjustScreenAfterMergeOperation(indexOfFirstLine, indexOfSecondLine);
            adjustDataStructureAfterMergeOperation(indexOfFirstLine, indexOfSecondLine);
            return true;
        }

        return false;
    }

    private boolean couldLineBeMerged(int indexOfFirstLine, int indexOfSecondLine) {
        Line firstLine = lines.get(indexOfFirstLine);
        Line secondLine = lines.get(indexOfSecondLine);

        return firstLine.haveSameParent(secondLine);
    }

    private void adjustScreenAfterMergeOperation(int indexOfFirstLine, int indexOfSecondLine) {
        JTextField firstTextField = textFields.get(indexOfFirstLine);
        JTextField secondTextField = textFields.get(indexOfSecondLine);
        firstTextField.setText(firstTextField.getText() + secondTextField.getText());
        textFields.remove(indexOfSecondLine);
        containingPanel.remove(indexOfSecondLine);
    }

    private void adjustDataStructureAfterMergeOperation(int indexOfFirstLine, int indexOfSecondLine) {
        Line firstLine = lines.get(indexOfFirstLine);
        Line secondLine = lines.get(indexOfSecondLine);

        firstLine.setContent(firstLine.getContent() + secondLine.getContent());
        deleteLineFromDataStructure(secondLine);
    }

    private void deleteLineFromDataStructure(Line line) {
        Compound<Line> parent = line.getParent();
        lines.remove(line);
        parent.removeChild(line);
    }

    /**
     * Splits a line at a given word number.
     * Line is selected through index.
     *
     * @param wordNumber word number
     * @param index      of line
     */
    public boolean splitAtWord(int wordNumber, int index) {
        JTextField currentTextField = textFields.get(index);
        String text = currentTextField.getText();

        if (canBeSplit(index) && wordNumber < getNumberOfWords(text)) {
            adjustDataStructureAfterSplit(wordNumber, index, text);
            currentTextField.setText(getFirstPartOfSplit(text, wordNumber));
            addTextLine(getLastPartOfSplit(text, wordNumber), index + 1);
            return false;
        }

        return true;
    }

    private void adjustDataStructureAfterSplit(int wordNumber, int index, String text) {
        Line line = lines.get(index);
        Line newLine = lines.get(index + 1);
        line.setContent(getFirstPartOfSplit(text, wordNumber));
        newLine.setContent(getLastPartOfSplit(text, wordNumber));
    }

    private boolean canBeSplit(int index) {
        Line currentLine = lines.get(index);
        Line newLine = new Line(currentLine.getParent(),
                currentLine.getLeftUpperCornerX(),
                currentLine.getLeftUpperCornerY() + currentLine.getHeight(),
                currentLine.getHeight(),
                currentLine.getWidth());

        if ( !intersectsOtherLines(newLine) ) {
            insertLineInDataStructure(currentLine, newLine);
            return true;
        }

        return false;
    }

    private void insertLineInDataStructure(Line currentLine, Line lineToBeInserted) {
        Compound<Line> parent = currentLine.getParent();
        int indexOfChild = parent.getIndexOfChildFromChildrenList(currentLine);
        parent.addChildAtIndex(indexOfChild + 1, lineToBeInserted);
        lines = super.getRoot().getLines();
    }

    private boolean intersectsOtherLines(Line line) {
        for ( Line otherLine : lines ) {
            if ( otherLine != line && ( line.intersectsAnotherNode(otherLine)
                    || line.haveTheSameDimensions(otherLine) ) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a text line at a given position
     *
     * @param text
     * @param positionIndex
     */
    private void addTextLine(String text, int positionIndex) {
        JTextField textField = new JTextField(text);
        containingPanel.add(textField, positionIndex);
        textFields.add(positionIndex, textField);
        textField.setEditable(false);
        textField.setComponentPopupMenu(getRightClickMenu());
    }

    /**
     * Returns the first part of the text split by a wordNumber
     *
     * @param paragraphText
     * @param wordNumber
     * @return
     */
    private String getFirstPartOfSplit(String paragraphText, int wordNumber) {
        int endPosition = getIndexForSplitting(paragraphText, wordNumber);
        return paragraphText.substring(0, endPosition - 1);
    }

    /**
     * Returns the second part of the text split by a wordNumber
     *
     * @param paragraphText
     * @param wordNumber
     * @return
     */
    private String getLastPartOfSplit(String paragraphText, int wordNumber) {
        int startPosition = getIndexForSplitting(paragraphText, wordNumber);
        return paragraphText.substring(startPosition);
    }

    /**
     * Returns the index where splitting should start
     *
     * @param paragraphText
     * @param lineNumber
     * @return
     */
    private int getIndexForSplitting(String paragraphText, int lineNumber) {
        int position = 0;

        for (int i = 0; i < lineNumber; i++) {
            position = paragraphText.indexOf(' ', position) + 1;
        }

        return position;
    }

    /**
     * Counts the number of words in a text
     *
     * @param text text
     * @return number of words
     */
    public int getNumberOfWords(String text) {
        int numberOfAppearances = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                numberOfAppearances++;
            }
        }

        return numberOfAppearances;
    }

    @Override
    public String getWindowTitle() {
        return WINDOW_TITLE;
    }
}
