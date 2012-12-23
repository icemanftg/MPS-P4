package ro.mps.gui;

import ro.mps.gui.base.Screen;
import ro.mps.gui.screens.SelectionScreen;
import ro.mps.gui.screens.lines.CharacterEditingScreen;
import ro.mps.gui.screens.lines.LinesEditingScreen;
import ro.mps.gui.screens.lines.LinesTextGenerator;
import ro.mps.gui.screens.paragraph.ParagraphEditingScreen;
import ro.mps.gui.screens.paragraph.ParagraphsTextGenerator;
import ro.mps.gui.screens.paragraph.TreeGenerator;

import javax.swing.*;


@SuppressWarnings("serial")
public class UserInterface extends JFrame {


    public UserInterface() {

        this.init();
    }

    /**
     * Initializeaza fereastra principala
     */
    private void init() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT);
        this.setVisible(true);

        addTabbedPannel();
    }

    /**
     * Builds the initial tabbed pannel.
     */
    private void addTabbedPannel() {
        TabbedPannel tabbedPannel = new TabbedPannel();
        TreeGenerator treeGenerator = new TreeGenerator(5);
        tabbedPannel.addPane(new ParagraphEditingScreen(treeGenerator.buildTree()));
        tabbedPannel.addPane(new LinesEditingScreen(treeGenerator.buildTree()));
        tabbedPannel.addPane(new SelectionScreen());
        tabbedPannel.addPane(new CharacterEditingScreen(treeGenerator.buildTree()));

        getContentPane().add(tabbedPannel);
    }


}