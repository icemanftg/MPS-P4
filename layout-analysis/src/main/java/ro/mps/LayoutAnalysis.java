package ro.mps;

import ro.mps.configure.Config;
import ro.mps.gui.UserInterface;
import ro.mps.initial_screen.InitialInterface;

import java.awt.*;

/**
 * Hello world!
 */
public class LayoutAnalysis {
    Config configs = Config.ENVIRONMENT;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                InitialInterface layoutGUI = new InitialInterface();
            }
        });
    }
}
