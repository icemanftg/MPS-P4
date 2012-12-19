package ro.mps.gui;


import junit.framework.TestCase;
import ro.mps.gui.screens.SelectionScreen;

import java.awt.*;

/**
 * User: Alexandru Burghelea
 * Date: 11/15/12
 * Time: 1:01 PM
 */
public class SelectionScreenTest extends TestCase {


    /**
     * Testing if gui starts.
     *
     * @throws Exception
     */
    public void testGuiStarts() throws Exception {
        EventQueue.invokeAndWait(new Runnable() {
            public void run() {
                SelectionScreen ss = new SelectionScreen();
            }
        });
//        while(true);
//        Assert.assertTrue("GUI not loaded", true);
    }
}
