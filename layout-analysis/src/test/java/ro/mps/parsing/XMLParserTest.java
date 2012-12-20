package ro.mps.parsing;

import java.awt.EventQueue;
import junit.framework.TestCase;
import ro.mps.data.parsing.XMLParser;
import ro.mps.data.concrete.*;


public class XMLParserTest extends TestCase {


    /**
     * Parsing a simple xml
     *
     * @throws Exception
     */
    public void testGuiStarts() throws Exception {
        EventQueue.invokeAndWait(new Runnable() {
            public void run() {
                XMLParser parser = new XMLParser();
                parser.parse("simple_test.xml");
                for(int i = 0; i < parser.getRoot().getChildren().size(); i++) {
                    Block b = (Block)parser.getRoot().getChildren().get(i);
                    System.out.println(b.getLabel());
                    for(int j = 0; j < b.getChildren().size(); j++) {
                        Line l = (Line)b.getChildren().get(j);
                        System.out.println(l.getContent());
                    }
                }
            }
        });
    }
}
