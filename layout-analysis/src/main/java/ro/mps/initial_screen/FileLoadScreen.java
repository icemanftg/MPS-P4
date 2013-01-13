package ro.mps.initial_screen;

import ro.mps.crop.CropScreen;
import ro.mps.gui.base.Screen;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileLoadScreen extends Screen {
    /**
     * Constant title strings
     */
    public static final String SELECTION_SCREEN = "Input selection screen";
    public static final String SELECT_WHAT_TO_EDIT = "Type";
    public static final String SELECT_INPUT_FILE = "Select your input file (XML/IMG)";

    /**
     * File selection text field
     */
    private JTextField filename = new JTextField(TEXT_BOX_WIDTH);

    /**
     * Buttons
     */
    private JButton browse = new JButton("Browse"), proceed = new JButton("Proceed");

    /**
     * Radio buttons
     */
    private JRadioButton xml = new JRadioButton("XML"),
            image = new JRadioButton("Image");

    /**
     * A reference to the file we choose - for further use
     */
    private File inputFile;

    public FileLoadScreen() {
        super();
        
        /* Add button listeners */
        browse.addActionListener(new OpenButtonListener());
        proceed.addActionListener(new AnalyzeButtonListener());


        /* Text field settings */
        filename.setText("Path to input...");
        filename.setEditable(false);
        filename.setBackground(bg);

        setLayout(new FlowLayout(FlowLayout.CENTER));

        /* Add first section - filename text field and "Browse" button */
        add(buildBrowsePanel());
        /* Add second section - checkboxes */
        add(buildCheckboxPanel());
        /* Add third section - "Analyze" button */
        add(buildAnalyzePanel());

        this.setVisible(true);
    }

    /**
     * Builds the panel that contains the Analyze button
     *
     * @return JPanel
     */
    private JPanel buildAnalyzePanel() {
        JPanel analyzePanel = new JPanel();
        analyzePanel.setLayout(new BoxLayout(analyzePanel, BoxLayout.X_AXIS));
        analyzePanel.add(proceed);
        return analyzePanel;
    }

    /**
     * Builds the panel that contains checkboxes
     *
     * @ return JPanel
     */
    private JPanel buildCheckboxPanel() {
    	ButtonGroup group = new ButtonGroup();
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setBackground(bg);
        group.add(xml);
        group.add(image);
        image.setBackground(bg);
        xml.setBackground(bg);
        checkboxPanel.add(xml);
        checkboxPanel.add(image);
        checkboxPanel.setBorder(BorderFactory.createTitledBorder(SELECT_WHAT_TO_EDIT));
        
        return checkboxPanel;
    }

    /**
     * Builds the panel that contains the Browse button and text field
     *
     * @return JPanel
     */
    private JPanel buildBrowsePanel() {
        JPanel browserPanel = new JPanel();
        browserPanel.setBackground(bg);
        browserPanel.add(filename);
        browserPanel.add(browse);
        browserPanel.setBorder(BorderFactory.createTitledBorder(null, SELECT_INPUT_FILE,
                TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
        return browserPanel;
    }

    @Override
    public String getWindowTitle() {
        return SELECTION_SCREEN;
    }

    /* "Browse" function uses JFileChooser */
    class OpenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            int rVal = c.showOpenDialog(FileLoadScreen.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().getName());
                inputFile = c.getSelectedFile();
            }
        }
    }

    class AnalyzeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (image.isSelected()){
            	CropScreen.startOCRSCreen(inputFile.getAbsolutePath());
            } else {
            	// start XML parsing 
            }

        }
    }

    /**
     * Retains a reference to the file chosen to work with
     *
     * @return File
     */
    public File getInputFile() {
        return this.inputFile;
    }

}