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

    /**
     * Updates the screen based on the root
     * @param root - root of the tree
     */
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
     * @return - the menu
     */
    public JPopupMenu getRightClickMenu() {
        return getPopupMenu().getRightClickMenu();
    }

    /**
     * Merge a line with the previous one
     *
     * @param index - index of line
     */
    public boolean mergeWithPreviousLine(int index) {
        return mergeLines(index - 1, index);
    }

    /**
     * Merge a line with the next one
     *
     * @param index - index of line
     */
    public boolean mergeWithNextLine(int index) {
        return mergeLines(index, index + 1);
    }

    /**
     * Merges two lines identified by their indexes
     * @param indexOfFirstLine - index of the first line
     * @param indexOfSecondLine - index of the second line
     * @return true if the lines can be merged
     */
    private boolean mergeLines(int indexOfFirstLine, int indexOfSecondLine) {
        if (couldLineBeMerged(indexOfFirstLine, indexOfSecondLine)) {
            adjustScreenAfterMergeOperation(indexOfFirstLine, indexOfSecondLine);
            adjustDataStructureAfterMergeOperation(indexOfFirstLine, indexOfSecondLine);
            return true;
        }

        return false;
    }

    /**
     * Test if the lines can be merged
     * @param indexOfFirstLine - index of the first line
     * @param indexOfSecondLine - index of the second line
     * @return true if the lines can be merged
     */
    private boolean couldLineBeMerged(int indexOfFirstLine, int indexOfSecondLine) {
        Line firstLine = lines.get(indexOfFirstLine);
        Line secondLine = lines.get(indexOfSecondLine);

        return firstLine.haveSameParent(secondLine);
    }

    /**
     * Adjusts the screen after the merge operation
     * @param indexOfFirstLine - index of the first line
     * @param indexOfSecondLine - index of the second line
     */
    private void adjustScreenAfterMergeOperation(int indexOfFirstLine, int indexOfSecondLine) {
        JTextField firstTextField = textFields.get(indexOfFirstLine);
        JTextField secondTextField = textFields.get(indexOfSecondLine);
        firstTextField.setText(firstTextField.getText() + secondTextField.getText());
        textFields.remove(indexOfSecondLine);
        containingPanel.remove(indexOfSecondLine);
    }

    /**
     * Adjusts data structure after merge operation.
     * @param indexOfFirstLine - index of the first line
     * @param indexOfSecondLine - index of the second line
     */
    private void adjustDataStructureAfterMergeOperation(int indexOfFirstLine, int indexOfSecondLine) {
        Line firstLine = lines.get(indexOfFirstLine);
        Line secondLine = lines.get(indexOfSecondLine);

        firstLine.setContent(firstLine.getContent() + secondLine.getContent());
        deleteLineFromDataStructure(secondLine);
    }

    /**
     * Deletes a line from data structure
     * @param line - line that will be deleted
     */
    private void deleteLineFromDataStructure(Line line) {
        Compound<Line> parent = line.getParent();
        lines.remove(line);
        parent.removeChild(line);
    }

    /**
     * Splits a line at a given word number.
     * Line is selected through index.
     *
     * @param wordNumber - word number
     * @param index - index of line
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

    /**
     * Adjusts data structure after split operation.
     * @param wordNumber - this number will
     * @param index - index of the line
     * @param text - text of the line
     */
    private void adjustDataStructureAfterSplit(int wordNumber, int index, String text) {
        Line line = lines.get(index);
        Line newLine = lines.get(index + 1);
        line.setContent(getFirstPartOfSplit(text, wordNumber));
        newLine.setContent(getLastPartOfSplit(text, wordNumber));
    }

    /**
     * Tests if a line can be split.
     * @param index - index of the line that will be split
     * @return - true if line can be split
     */
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

    /**
     * Inserts a line in data structure after the currentLine
     * @param currentLine - current line
     * @param lineToBeInserted - line that will be inserted
     */
    private void insertLineInDataStructure(Line currentLine, Line lineToBeInserted) {
        Compound<Line> parent = currentLine.getParent();
        int indexOfChild = parent.getIndexOfChildFromChildrenList(currentLine);
        parent.addChildAtIndex(indexOfChild + 1, lineToBeInserted);
        lines = super.getRoot().getLines();
    }

    /**
     * Test if a line intersects other lines
     * @param line - line tested
     * @return true if line intersects othe lines
     */
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
     * @param text -  text of the line
     * @param positionIndex - position of the line
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
     * @param paragraphText - text of the paragraph
     * @param wordNumber - word number
     * @return - text containing the first part of the split
     */
    private String getFirstPartOfSplit(String paragraphText, int wordNumber) {
        int endPosition = getIndexForSplitting(paragraphText, wordNumber);
        return paragraphText.substring(0, endPosition - 1);
    }

    /**
     * Returns the second part of the text split by a wordNumber
     *
     * @param paragraphText - text of the paragraph
     * @param wordNumber - word number
     * @return - text containing the second part of the split
     */
    private String getLastPartOfSplit(String paragraphText, int wordNumber) {
        int startPosition = getIndexForSplitting(paragraphText, wordNumber);
        return paragraphText.substring(startPosition);
    }

    /**
     * Returns the index where splitting should start
     *
     * @param paragraphText - text of the paragraph
     * @param lineNumber - line number
     * @return - index where splitting should start
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
     * @return - returns number of words
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
