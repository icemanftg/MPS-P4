package ro.mps.gui.screens;

import ro.mps.gui.base.Screen;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 18.11.2012
 * Time: 19:22
 */
public class BottomPaneTemplate extends Screen {
    public static final String PAGINA_NR = "Pagina nr:";
    public static final String GENERATE = "Generate";
    public final String windowTitle;

    private JScrollPane scrollPane;

    private JPanel pageNumber;
    private JLabel pageNumberLabel;
    private JPanel bottomPane;

    private JSpinner pageNumberSpinner;
    private JButton generateButton;

    public BottomPaneTemplate(String windowTitle) {
        super();
        this.windowTitle = windowTitle;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Builds the bottom panel
     */
    protected void addBottomPane() {
        bottomPane = new JPanel();
        addPageNumber();
        addGenerateButton();
        add(bottomPane, BorderLayout.LINE_END);
    }

    /**
     * Transforms a panel into a scrollable panel
     */
    protected void addScrollPanel(JPanel containingPanel) {
        int WIDTH_OFFSET = 50;
        int HEIGHT_OFFSET = 150;

        scrollPane = new JScrollPane(containingPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(Screen.WINDOW_WIDTH - WIDTH_OFFSET, Screen.WINDOW_HEIGHT - HEIGHT_OFFSET));
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        add(scrollPane);
    }

    /**
     * repaints the scroll pane
     */
    public void repaintScrollPane() {
        scrollPane.repaint();
        scrollPane.revalidate();
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
     * Adds the Generate button to the bottom panel
     */
    private void addGenerateButton() {
        generateButton = new JButton(GENERATE);
        generateButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        bottomPane.add(generateButton);
    }

    /**
     * Create a spinner model with range between 1 and 100
     * @return SpinnerModel representing the pageNumber;
     */
    private SpinnerModel pageNumberSpinner(){
        return pageNumberSpinner(100);
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

    @Override
    public String getWindowTitle() {
        return windowTitle;
    }
}
