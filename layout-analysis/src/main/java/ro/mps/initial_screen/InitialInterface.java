package ro.mps.initial_screen;

import ro.mps.gui.TabbedPannel;
import ro.mps.gui.base.Screen;

import javax.swing.*;


@SuppressWarnings("serial")
public class InitialInterface extends JFrame {


    public InitialInterface() {

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
        tabbedPannel.addPane(new FileLoadScreen());

        getContentPane().add(tabbedPannel);
    }


}