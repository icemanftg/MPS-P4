package ro.mps.data.parsing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import ro.mps.data.concrete.*;
import ro.mps.data.api.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;

/** XML Parser for Layout Specification Format
 *
 * @author Silviu
 */
public class XMLParser {

    /** The root of the DOM we will create */
    private static Root root = null;

    /** Obtain the root
     *
     * @return      the root element (must check for null)
     */
    public Root getRoot() {
        return root;
    }


    /**  Parsing function
     *
     * @param filename      the name of the xml file
     */
    public void parse(String filename) {

        root = new Root(1000, 1000);
        try {
            File XmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(XmlFile);
            doc.getDocumentElement().normalize();

            /* Put Document attributes in our root */
            root.setImageName(doc.getDocumentElement().getAttribute("image"));
            root.setDirection(doc.getDocumentElement().getAttribute("direction"));

            /* Get the page number ComposedBlock */
            ComposedBlock cBlock = getPageNumberComposedBlock(doc);
            root.setPageNumber(cBlock);

            /* Get list of all TextBlocks */
            NodeList nList = doc.getElementsByTagName("TextBlock");
            if(nList != null && nList.getLength() > 0) {
			for(int i = 0 ; i < nList.getLength();i++) {
                                /* Get only the TextBlock elements that
                                   are immediate children of Root
                                 */
				Element el = (Element)nList.item(i);
                                if(el.getParentNode().getNodeName().equals("Document")) {
                                    /* Put the information in a Block */
                                    Block b = getBlock(el, root);
                                    /* Add that Block as a child of root */
                                    root.addChild(b);
                                }
			}
		}

        } catch (SAXException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /** Creates a Block from a TextBlock element of the xml
     *
     * @param el        the Element we will analyze
     * @param root      the parent of the newly created Block
     * @return          the Block object
     */
    private Block getBlock(Element el, Compound root) {

        /* Get all the block attributes */
        int left = Integer.parseInt(el.getAttribute("left"));
	int top = Integer.parseInt(el.getAttribute("top"));
	int right = Integer.parseInt(el.getAttribute("right"));
        int bottom = Integer.parseInt(el.getAttribute("bottom"));

	/* Create a new Block with the attributes read from the xml nodes
           and root as parent
         */
	Block b = new Block(root, left, top, bottom, right);

        /* Iterate through all the lines of the block */
        NodeList nList = el.getElementsByTagName("TextLine");
        if(nList != null && nList.getLength() > 0) {
			for(int i = 0 ; i < nList.getLength();i++) {
                                /* Get the TextLine element */
				Element elem = (Element)nList.item(i);
                                /* Get the Line object */
				Line line = getLine(elem, b);
                                /* Add it to the Block's children */
				b.addChild(line);
			}
		}
        return b;
    }

    /** Creates a Line object from a TextLine element of the xml
     *
     * @param elem      the Element we will analyze
     * @param b         the parent Block we will attach the line to
     * @return          the Line object
     */
    private Line getLine(Element elem, Block b) {

        /* Get all the line attributes */
        int left = Integer.parseInt(elem.getAttribute("left"));
	int top = Integer.parseInt(elem.getAttribute("top"));
	int right = Integer.parseInt(elem.getAttribute("right"));
        int bottom = Integer.parseInt(elem.getAttribute("bottom"));

	/* Create a new Line with the attributes read from the xml , parent and content */
        String content = getTextValue(elem, "String");
        Line line = new Line(content, b, left, top, bottom, right);
        return line;
    }

    /** Gets the value of an Element in string format
     *
     * @param ele       the parent Element
     * @param tagName   the name of the child Element we want
     * @return          the string value
     */
    private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}


    /** Calls getTextValue and returns a int value
     *
     * @ return     the int value of the parsed string
     */
    private int getIntValue(Element ele, String tagName) {
	return Integer.parseInt(getTextValue(ele,tagName));
    }


    /**
     * Obtain the page number in a ComposedBlock object of our structure
     * @param doc   the Document we are analyzing
     * @return      the ComposedBlock object with the necessary data
     */
    private ComposedBlock getPageNumberComposedBlock(Document doc) {
        /* Get the ComposedBlock element */
        NodeList nodeList = doc.getElementsByTagName("ComposedBlock");
        Element pageNumber = (Element)nodeList.item(0);

        /* Get the TextBlock element from the ComposedBlock */
        nodeList = pageNumber.getElementsByTagName(("TextBlock"));
        Element textBlock = (Element)nodeList.item(0);

        /* Create a ComposedBlock object */
        ComposedBlock cBlock = new ComposedBlock();
        /* Get the Block object from the TextBlock */
        Block block = getBlock(textBlock, cBlock);

        cBlock.setBlock(block);
        return cBlock;
    }
}
