package ro.mps.gui;

import ro.mps.gui.base.Screen;
import ro.mps.gui.screens.CharacterEditingScreen;
import ro.mps.gui.screens.LinesEditingScreen;
import ro.mps.gui.screens.ParagraphEditingScreen;
import ro.mps.gui.screens.SelectionScreen;

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

//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        }
        addTabbedPannel();
    }

    /**
     * Builds the initial tabbed pannel.
     */
    private void addTabbedPannel() {
        TabbedPannel tabbedPannel = new TabbedPannel();
        tabbedPannel.addPane(new ParagraphEditingScreen());
        tabbedPannel.addPane(new LinesEditingScreen());
        tabbedPannel.addPane(new SelectionScreen());
        tabbedPannel.addPane(new CharacterEditingScreen());

        getContentPane().add(tabbedPannel);
    }



}