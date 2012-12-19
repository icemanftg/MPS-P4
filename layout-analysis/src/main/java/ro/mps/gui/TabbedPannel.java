package ro.mps.gui;

import ro.mps.gui.base.Screen;

import javax.swing.*;

/**
 * User: Alexandru Burghelea
 * Date: 11/17/12
 * Time: 12:44 PM
 */
public class TabbedPannel extends JTabbedPane {

    public TabbedPannel() {
        super();
        setVisible(true);
        setSize(500, 500);
    }

    public void addPane(Screen panel) {
        this.addTab(panel.getWindowTitle(), panel);
    }

}
