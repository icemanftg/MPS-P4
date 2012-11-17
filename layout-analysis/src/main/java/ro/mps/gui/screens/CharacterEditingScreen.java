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
public class CharacterEditingScreen extends Screen {

    private static final Integer LINES = 300;
    public static final String PAGINA_NR = "Pagina nr:";
    public static final String GENERATE = "Generate";
    protected List<JTextField> textLines;
    private JScrollPane scrollPane;

    private JPanel containingPanel;
    private JPanel pageNumber;
    private JLabel pageNumberLabel;
    private JPanel bottomPane;

    private JSpinner pageNumberSpinner;
    private JButton generateButton;

    @Override
    public String getWindowTitle() {
        return "Edit characters in lines";
    }

    public CharacterEditingScreen() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addLines();
        addScrollPanel();
        addBottomPane();
    }

    /**
     * Builds the bottom panel
     */
    private void addBottomPane() {
        bottomPane = new JPanel();
        addPageNumber();
        addGenerateButton();
        add(bottomPane);
    }

    /**
     * Adds the Generate button to the bottom panel
     */
    private void addGenerateButton() {
        generateButton = new JButton(GENERATE);
        generateButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        bottomPane.add(generateButton);
    }

    /**
     * Adds the page number label and spinner
     */
    private void addPageNumber() {
        pageNumber = new JPanel(new FlowLayout());
        pageNumberLabel = new JLabel(PAGINA_NR);
        pageNumberSpinner = new JSpinner(pageNumberSpinner());
        pageNumber.add(pageNumberLabel, BorderLayout.WEST);
        pageNumber.add(pageNumberSpinner,BorderLayout.EAST);
        pageNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomPane.add(pageNumber);
    }

    /**
     * Create a spinner model with range between 1 and <code>max</code>
     * @param max The maximum number of pages
     * @return SpinnerModel representing the pageNumber;
     */

    private SpinnerModel pageNumberSpinner(int max) {
        int initialValue = 1;
        int minimumValue = 1;
        int stepSize = 1;
        return  new SpinnerNumberModel(initialValue, minimumValue,max, stepSize);

    }

    /**
     * Create a spinner model with range between 1 and 100
     * @return SpinnerModel representing the pageNumber;
     */
    private SpinnerModel pageNumberSpinner(){
        return pageNumberSpinner(100);
    }

    /**
     * Generates the text lines that can be edited.
     */
    private void addLines() {
        textLines = new LinkedList<JTextField>();
        containingPanel = new JPanel();
        containingPanel.setSize(Screen.WINDOW_WIDTH,Screen.WINDOW_HEIGHT);
        containingPanel.setLayout(new BoxLayout(containingPanel,BoxLayout.Y_AXIS));

        for (int i = 0 ; i < LINES; i++){
            JTextField linie = new JTextField(String.valueOf(i),60);
            containingPanel.add(linie);
            textLines.add(linie);
        }

        containingPanel.setBorder(BorderFactory.createTitledBorder("Lines"));
    }

    /**
     * Transforms a panel into a scrollable panel
     */
    private void addScrollPanel() {
        int WIDTH_OFFSET = 50;
        int HEIGHT_OFFSET = 150;

        scrollPane = new JScrollPane(containingPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(Screen.WINDOW_WIDTH - WIDTH_OFFSET, Screen.WINDOW_HEIGHT - HEIGHT_OFFSET));
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        add(scrollPane);
    }
}
