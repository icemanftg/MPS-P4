package ro.mps.gui.screens.lines;

import javax.swing.*;
import java.util.List;

/**
 * User: Alexandru Burghelea
 * Date: 11/17/12
 * Time: 7:31 PM
 */
public class LinesEditingScreen extends CharacterEditingScreen {
    private static final String WINDOW_TITLE = "Edit lines in blocks";
    private LinesPopupMenu popupMenu;

    public LinesEditingScreen(List<String> textLines) {
        super(textLines);
        for (JTextField textLine : lines) {
            textLine.setEditable(false);
            textLine.setComponentPopupMenu(getRightClickMenu());
        }
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
    public void mergeWithPreviousLine(int index) {
        JTextField currentTextField = lines.get(index);
        JTextField previousTextField = lines.get(index - 1);
        String text = previousTextField.getText() + currentTextField.getText();
        currentTextField.setText(text);
        lines.remove(index - 1);
        containingPanel.remove(index - 1);
    }

    /**
     * Merge a line with the next one
     *
     * @param index of line
     */
    public void mergeWithNextLine(int index) {
        JTextField currentTextField = lines.get(index);
        JTextField nextTextField = lines.get(index + 1);
        String text = nextTextField.getText() + currentTextField.getText();
        currentTextField.setText(text);
        lines.remove(index + 1);
        containingPanel.remove(index + 1);
    }

    /**
     * Splits a line at a given word number.
     * Line is selected through index.
     *
     * @param wordNumber word number
     * @param index      of line
     */
    public void splitAtWord(int wordNumber, int index) {
        JTextField currentTextField = lines.get(index);
        String text = currentTextField.getText();

        if (wordNumber < getNumberOfWords(text)) {
            currentTextField.setText(getFirstPartOfSplit(text, wordNumber));
            addTextLine(getLastPartOfSplit(text, wordNumber), index + 1);
        }
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
        lines.add(positionIndex, textField);
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
