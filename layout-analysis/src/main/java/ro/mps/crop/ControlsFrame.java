package ro.mps.crop;

import ro.mps.data.parsing.XMLWriter;

import javax.swing.*;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 1/15/13
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */

class ControlsFrame extends JFrame {

    private static int LANG_MENU_HEIGHT = 100;

    public static String[] LANGS = new String[] {"eng", "deu"};
    public static String[] BLOCK_TYPES = new String[] {"Image", "Paragraph", "Page Number"};

    private CropScreen attatched;
    JTabbedPane panel = new JTabbedPane();

    JPanel langPanel = new JPanel();
    JPanel blockPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String blockType;

    ButtonGroup langGroup = new ButtonGroup();
    ButtonGroup blockTypeGroup = new ButtonGroup();

    public ControlsFrame(String title, final CropScreen attatched) {
        super(title);
        this.attatched = attatched;

        ActionListener langChanged = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ControlsFrame.this.attatched.setLanguage(((JRadioButton)arg0.getSource()).getText());
            }
        };

        ActionListener blockChanged = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                blockType = ((JRadioButton)arg0.getSource()).getText();
                if (blockType.equals("Image"))
                    attatched.renderingColor = Color.RED;
                else if (blockType.equals("Paragraph"))
                    attatched.renderingColor = Color.GREEN;
                else
                    attatched.renderingColor = Color.ORANGE;

            }
        };

        ActionListener reset = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                attatched.reset();
            }
        };

        ActionListener next = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
            }
        };

        ActionListener export = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                XMLWriter writer = new XMLWriter();
                try {
                    writer.writeFile(attatched.getRoot(), "exported.xml");
                } catch (TransformerConfigurationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (TransformerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        getContentPane().add(top);

        /**
         * Adding the lang buttons
         */
        for (String lang : LANGS) {
            JRadioButton btn = new JRadioButton(lang);
            langGroup.add(btn);
            langPanel.add(btn);
            if (lang.equals("eng"))
                btn.setSelected(true);
            btn.addActionListener(langChanged);
        }

        /**
         * Add the block type buttons
         */
        for (String lang : BLOCK_TYPES) {
            JRadioButton btn = new JRadioButton(lang);
            blockTypeGroup.add(btn);
            blockPanel.add(btn);
            if (lang.equals("Paragraph"))
                btn.setSelected(true);
            btn.addActionListener(blockChanged);
        }

        langPanel.setBorder(BorderFactory.createTitledBorder("Choose text language"));
        panel.addTab("Language Settings", langPanel);

        blockPanel.setBorder(BorderFactory.createTitledBorder("Choose block type"));
        panel.addTab("Block Type", blockPanel);

        JButton btn = new JButton("Ok");
        buttonsPanel.add(btn);
        btn.addActionListener(next);

        btn = new JButton("Reset");
        buttonsPanel.add(btn);
        btn.addActionListener(reset);

        btn = new JButton("Export to XML");
        buttonsPanel.add(btn);
        btn.addActionListener(export);

        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        panel.addTab("Actions", buttonsPanel);
        top.add(panel, BorderLayout.CENTER);
        add(panel);
    }

}