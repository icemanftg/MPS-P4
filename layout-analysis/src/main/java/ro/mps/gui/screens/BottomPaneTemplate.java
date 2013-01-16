package ro.mps.gui.screens;

import ro.mps.data.concrete.ComposedBlock;
import ro.mps.data.concrete.PageNumberBlock;
import ro.mps.data.concrete.Root;
import ro.mps.data.parsing.XMLWriter;
import ro.mps.gui.base.Screen;
import ro.mps.screen.concrete.RootUsedInEditingScreen;
import ro.mps.screen.transformer.DataStructureTransformer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 18.11.2012
 * Time: 19:22
 */
public class BottomPaneTemplate extends Screen {
    public static final String PAGE_NUMBER = "Pagina nr:";
    public static final String EXPORT_TO_XML = "Export to XML";
    public static final String PAGE_NUMBER_ERROR = "Page number coudn't be set";
    public final String windowTitle;
    protected RootUsedInEditingScreen root;
    private JScrollPane scrollPane;

    private JPanel pageNumber;
    private JLabel pageNumberLabel;
    private JPanel bottomPane;

    private JSpinner pageNumberSpinner;
    private JButton generateButton;

    public BottomPaneTemplate(String windowTitle, RootUsedInEditingScreen root) {
        super();
        this.windowTitle = windowTitle;
        this.root = root;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Builds the bottom panel
     */
    protected void addBottomPane() {
        bottomPane = new JPanel();
        addPageNumber();
        addExportToXmlButton();
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

    public RootUsedInEditingScreen getRootUsedInEditingScreen() {
        return root;
    }

    public Root getRoot() {
        return DataStructureTransformer.transformRootUsedInEditingScreenToRoot(root);
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
        pageNumberLabel = new JLabel(PAGE_NUMBER);
        pageNumberSpinner = new JSpinner(pageNumberSpinner());
        pageNumberSpinner.addChangeListener(new ChangeListenerForSpinner());
        pageNumber.add(pageNumberLabel, BorderLayout.WEST);
        pageNumber.add(pageNumberSpinner, BorderLayout.EAST);
        pageNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomPane.add(pageNumber);
    }

    class ChangeListenerForSpinner implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            if ( root.hasPageNumberBlock() ) {
                JSpinner source = (JSpinner)changeEvent.getSource();
                String pageNumber = source.getValue().toString();

                PageNumberBlock pageNumberBlock = root.getPageNumberBlock();

                pageNumberBlock.setPageNumber(pageNumber);
            }
            else {
                JOptionPane.showMessageDialog(pageNumber, PAGE_NUMBER_ERROR);
            }

        }
    }

    class ExportToXmlListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            XMLWriter writer = new XMLWriter();
            try {
                writer.writeFile(getRoot(), "exported.xml");
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    private void addExportToXmlButton() {
        generateButton = new JButton(EXPORT_TO_XML);
        generateButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        generateButton.addActionListener(new ExportToXmlListener());
        bottomPane.add(generateButton);
    }

    /**
     * Create a spinner model with range between 1 and 100
     *
     * @return SpinnerModel representing the pageNumber;
     */
    private SpinnerModel pageNumberSpinner() {
        return pageNumberSpinner(100);
    }

    /**
     * Create a spinner model with range between 1 and <code>max</code>
     *
     * @param max The maximum number of pages
     * @return SpinnerModel representing the pageNumber;
     */
    private SpinnerModel pageNumberSpinner(int max) {
        int initialValue = 1;
        int minimumValue = 1;
        int stepSize = 1;
        return new SpinnerNumberModel(initialValue, minimumValue, max, stepSize);

    }

    @Override
    public String getWindowTitle() {
        return windowTitle;
    }
}
