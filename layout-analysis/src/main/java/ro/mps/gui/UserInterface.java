package ro.mps.gui;

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
        this.setSize(600, 600);
        this.setVisible(true);

        JTabbedPane jTabbedPane = new JTabbedPane();

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JLabel label1 = new JLabel();
        label1.setText("You are in area of Tab1");
        JLabel label2 = new JLabel();
        label2.setText("You are in area of Tab2");
        jp1.add(label1);
        jp2.add(label2);
        jTabbedPane.addTab("Tab1", jp1);
        jTabbedPane.addTab("Tab2", jp2);

        getContentPane().add(jTabbedPane);
    }

}