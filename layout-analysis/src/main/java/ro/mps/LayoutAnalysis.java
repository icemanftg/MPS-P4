package ro.mps;

import ro.mps.gui.UserInterface;

import java.awt.*;

/**
 * Hello world!
 */
public class LayoutAnalysis {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserInterface layoutGUI = new UserInterface();
            }
        });
    }
}
