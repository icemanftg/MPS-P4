package ro.mps.gui;

import ro.mps.gui.base.Screen;
import ro.mps.gui.screens.*;
import ro.mps.gui.screens.lines.CharacterEditingScreen;
import ro.mps.gui.screens.lines.LinesEditingScreen;
import ro.mps.gui.screens.lines.LinesTextGenerator;
import ro.mps.gui.screens.paragraph.ParagraphEditingScreen;
import ro.mps.gui.screens.paragraph.ParagraphsTextGenerator;

import javax.swing.*;


@SuppressWarnings("serial")
public class UserInterface extends JFrame{


    public UserInterface(){

        this.init();
    }

    /**
     * Initializeaza fereastra principala
     */
    private void init(){
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
        tabbedPannel.addPane(new ParagraphEditingScreen(ParagraphsTextGenerator.getParagraphsText(30)));
        tabbedPannel.addPane(new LinesEditingScreen(LinesTextGenerator.getLinesText(100)));
        tabbedPannel.addPane(new SelectionScreen());
        tabbedPannel.addPane(new CharacterEditingScreen(LinesTextGenerator.getLinesText(100)));

        getContentPane().add(tabbedPannel);
    }



}