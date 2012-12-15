package ro.mps.configure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Alexandru Burghelea
 * Date: 12/15/12
 * Time: 10:20 AM
 */
public class ConfigTest {

    Config environment;

    @Before
    public void setUp() throws Exception {
        environment = Config.CONFIGS;
    }


    @Test
    public void testProperties() throws Exception {
        Assert.assertTrue("Ar trebui sa existe minim 3 proprietati in fisierul de config", environment.size() >= 3);
    }

    @Test
    public void testGetOutputFolder() throws Exception {
        String folder = environment.getOutputFolder();
        System.out.println(folder);
        Assert.assertNotNull(folder);
    }

    @Test
    public void testGetExecsFolder() throws Exception {
        String folder = environment.getExecsFolder();
        System.out.println(folder);
        Assert.assertNotNull(folder);
    }

    @Test
    public void testGetSchemaFolder() throws Exception {
        String folder = environment.getSchemaFolder();
        System.out.println(folder);
        Assert.assertNotNull(folder);
    }
}
