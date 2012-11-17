package ro.mps.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SelectionScreen extends JFrame implements Screen {
    /* Strings */
    String windowTitle = "Unnamed screentin";

    static final String checkboxesTitle = "Select what to edit";

    static final String selectTitle = "Select your input file (XML/IMG)";


    static final int TEXT_BOX_WIDTH = 50;

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

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(windowTitle);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        /* Add button listeners */
        browse.addActionListener(new OpenL());
        analyze.addActionListener(new AnalyzeL());

        /* Text field settings */
        filename.setText("Path to input...");
        filename.setEditable(false);
        filename.setBackground(bg);

        /* Add first section - filename text field and "Browse" button */
        Container cp = getContentPane();
        JPanel p = new JPanel();
        p.setBackground(bg);
        p.add(filename);
        p.add(browse);
        p.setBorder(BorderFactory.createTitledBorder(null, selectTitle,
                TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
        cp.add(p, BorderLayout.NORTH);

        /* Add second section - checkboxes */
        p = new JPanel();
        p.setBackground(bg);
        JPanel flow = new JPanel(new GridLayout(3, 1));
        flow.setBackground(bg);
        flow.add(characters);
        flow.add(lines);
        flow.add(blocks);
        characters.setBackground(bg);
        lines.setBackground(bg);
        blocks.setBackground(bg);
        flow.setBorder(BorderFactory.createTitledBorder(checkboxesTitle));
        p.add(flow);
        cp.add(p, BorderLayout.CENTER);

        /* Add third section - "Analyze" button */
        p = new JPanel();
        p.setBackground(bg);
        flow = new JPanel(new FlowLayout());
        analyze.setPreferredSize(new Dimension(100, 30));
        flow.add(analyze);
        p.add(flow);
        cp.add(p, BorderLayout.SOUTH);
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