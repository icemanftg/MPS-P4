package ro.mps.gui;


import junit.framework.TestCase;
import org.junit.Assert;
import ro.mps.properties.Properties;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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
        while(true);
//        Assert.assertTrue("GUI not loaded", true);
    }
}
