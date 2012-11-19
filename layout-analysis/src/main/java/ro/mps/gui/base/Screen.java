package ro.mps.gui.base;

import javax.swing.*;
import java.awt.*;

/**
 * User: Alexandru Burghelea
 * Date: 11/17/12
 * Time: 12:45 PM
 */
public abstract class Screen extends JPanel {

    /* Some window and component size parameters */

    public static int WINDOW_WIDTH = 600 , WINDOW_HEIGHT = 600;

    public static final int TEXT_BOX_WIDTH = 50;

    /* Background color */
    public Color bg = Color.WHITE;

    public abstract String getWindowTitle();

    protected Screen() {
        super();
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
}
