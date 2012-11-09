package ro.mps.layout;

import javax.swing.*;


public class UserInterface extends JFrame{


    public UserInterface(){

        this.init();
    }

    /**
     * Initializeaza fereastra principala
     */
    private void init(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(600, 600);

        this.setVisible(true);
    }

}