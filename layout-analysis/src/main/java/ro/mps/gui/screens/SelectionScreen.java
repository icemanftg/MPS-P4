package ro.mps.gui.screens;

import ro.mps.gui.base.Screen;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SelectionScreen extends Screen {
    /* Strings */
    String windowTitle = "Selection Screen";

    static final String checkboxesTitle = "Select what to edit";

    static final String selectTitle = "Select your input file (XML/IMG)";

    /* File selection text field */
    private JTextField filename = new JTextField(TEXT_BOX_WIDTH);

    /* Buttons */
    private JButton browse = new JButton("Browse"), analyze = new JButton("Analyze");

    /* Checkboxes */
    private JCheckBox characters = new JCheckBox("Characters"),
            lines = new JCheckBox("Lines of characters"),
            blocks = new JCheckBox("Blocks of lines");

    /* A reference to the file we choose - for further use */
    private File inputFile;

    /* Constructor */
    public SelectionScreen() {
        super();

        /* Add button listeners */
        browse.addActionListener(new OpenL());
        analyze.addActionListener(new AnalyzeL());


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

    private JPanel buildAnalyzePanel() {
        JPanel analizePanel = new JPanel();
        analizePanel.setLayout(new BoxLayout(analizePanel,BoxLayout.X_AXIS));
        analizePanel.add(analyze);
        return analizePanel;
    }

    private JPanel buildCheckboxPanel() {
        JPanel checkboxPanel = new JPanel(new GridLayout(3, 1));
        checkboxPanel.setBackground(bg);
        checkboxPanel.add(characters);
        checkboxPanel.add(lines);
        checkboxPanel.add(blocks);
        characters.setBackground(bg);
        lines.setBackground(bg);
        blocks.setBackground(bg);
        checkboxPanel.setBorder(BorderFactory.createTitledBorder(checkboxesTitle));
        return checkboxPanel;
    }

    private JPanel buildBrowsePanel() {
        JPanel browserPannel = new JPanel();
        browserPannel.setBackground(bg);
        browserPannel.add(filename);
        browserPannel.add(browse);
        browserPannel.setBorder(BorderFactory.createTitledBorder(null, selectTitle,
                TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
        return browserPannel;
    }

    @Override
    public String getWindowTitle() {
        return windowTitle;
    }

    /* "Browse" function uses JFileChooser */
    class OpenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            int rVal = c.showOpenDialog(SelectionScreen.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().getName());
                inputFile = c.getSelectedFile();
            }
        }
    }

    class AnalyzeL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /* The magic happens here */

        }
    }

    public File getInputFile() {
        return this.inputFile;
    }

}