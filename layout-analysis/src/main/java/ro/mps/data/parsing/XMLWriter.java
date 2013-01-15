package ro.mps.data.parsing;

import java.util.logging.*;
import ro.mps.data.concrete.*;
import ro.mps.data.base.*;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;

/** XML Writer for Layout Specification Format
 *
 * @author Silviu
 */

public class XMLWriter {

    /** DOM that will result after Root is converted */
    private static Document doc = null;

    /** Obtain the DOM
     *
     * @return
     */
    public Document getDocument() {
        return doc;
    }

    /** Takes a Root element and writes the resultant XML to the file
     *  indicated by filename
     *
     * @param root      The Root of our structure we are processing
     * @param filename  The name of the XML we will create
     * @return          true if successful, false if not
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public boolean writeFile(Root root, String filename) throws TransformerConfigurationException, TransformerException {

        /* First we build the DOM from the Root */
        this.convert(root);
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();

        /* Should not be null if convert was successful */
        if(doc == null)
            return false;

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);

        return true;
    }

    /** Takes a Root element and creates a DOM from our structure in the doc field
     *
     * @param root  The Root of our structure we are processing
     */
    public void convert(Root root) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            Element rootElement = doc.createElement(root.getLabel());
            doc.appendChild(rootElement);

            /* Put our Root attributes in the DOM root element */
            Attr attr = doc.createAttribute("image");
            attr.setValue(root.getImageName());
            rootElement.setAttributeNode(attr);

            attr = doc.createAttribute("direction");
            attr.setValue(root.getDirection());
            rootElement.setAttributeNode(attr);

            ArrayList<CompoundNode> blockList = (ArrayList)root.getChildren();
            if(blockList != null && blockList.size() > 0) {
                for(int i = 0 ; i < blockList.size();i++) {
                                /* Create an Element from each TextBlock or ImageBlock */
                    Element el;
                    if(blockList.get(i) instanceof ImageBlock)
                        el = getImageBlockElement(blockList.get(i));
                    else
                        el = getBlockElement(blockList.get(i));
                                /* Add Element to rootElement */
					if (! (blockList.get(i) instanceof PageNumberBlock))
							rootElement.appendChild(el);
                }

			}

			/* Create the Composed Block element and add it */
            Element cBlockElement = doc.createElement("ComposedBlock");
            ComposedBlock cBlockObject = new ComposedBlock();
            cBlockObject.addChild(root.getPageNumberBlock());
            attr = doc.createAttribute("type");
            attr.setValue(cBlockObject.getType());
            cBlockElement.setAttributeNode(attr);
            cBlockElement.appendChild(getBlockElement(cBlockObject.getBlock()));
            rootElement.appendChild(cBlockElement);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** Extracts information from a TextBlock and puts it in an Element we will
     *  attach to the DOM's root element
     *
     * @param block     the TextBlock we are processing
     * @return          the Element we will append to the DOM's root element
     */
    private Element getBlockElement(CompoundNode block) {

        Element blockElement = doc.createElement("TextBlock");

        /* Set element attributes */
        Attr attr = doc.createAttribute("left");
        attr.setValue(Integer.toString(block.getLeftUpperCornerX()));
        blockElement.setAttributeNode(attr);

        attr = doc.createAttribute("top");
        attr.setValue(Integer.toString(block.getLeftUpperCornerY()));
        blockElement.setAttributeNode(attr);

        attr = doc.createAttribute("bottom");
        attr.setValue(Integer.toString(block.getLeftUpperCornerY()+block.getHeight()));
        blockElement.setAttributeNode(attr);

        attr = doc.createAttribute("right");
        attr.setValue(Integer.toString(block.getLeftUpperCornerX()+block.getWidth()));
        blockElement.setAttributeNode(attr);

        /* Get all the TextLines and create elements from them */
        ArrayList<Line> textLineList = (ArrayList)block.getChildren();
        if(textLineList != null && textLineList.size() > 0) {
            for(int i = 0 ; i < textLineList.size();i++) {
                                /* Create an Element from each TextLine */
                Element lineElement = getLineElement(textLineList.get(i));
                                /* Add Element to blockElement */
                blockElement.appendChild(lineElement);
            }
        }
        return blockElement;
    }

    /** Extracts information from a TextLine and puts it in an Element we will
     *  attach to the parent element
     *
     * @param line  the TextLine of our structure we are processing
     * @return      the Element we will append to the parent block element
     */
    private Element getLineElement(Line line) {

        Element lineElement = doc.createElement("TextLine");

        /* Set element attributes */
        Attr attr = doc.createAttribute("left");
        attr.setValue(Integer.toString(line.getLeftUpperCornerX()));
        lineElement.setAttributeNode(attr);

        attr = doc.createAttribute("top");
        attr.setValue(Integer.toString(line.getLeftUpperCornerY()));
        lineElement.setAttributeNode(attr);

        attr = doc.createAttribute("bottom");
        attr.setValue(Integer.toString(line.getLeftUpperCornerY()+line.getHeight()));
        lineElement.setAttributeNode(attr);

        attr = doc.createAttribute("right");
        attr.setValue(Integer.toString(line.getLeftUpperCornerX()+line.getWidth()));
        lineElement.setAttributeNode(attr);

        /* Set TextLine Element content */
        Element stringElement = doc.createElement("String");
        stringElement.setTextContent(line.getContent());

        lineElement.appendChild(stringElement);

        return lineElement;

    }

    /** Extracts information from an ImageBlock and puts it in an Element we will
     *  attach to the DOM's root element
     *
     * @param block     the ImageBlock we are processing
     * @return          the Element we will append to the DOM's root element
     */
    private Element getImageBlockElement(CompoundNode block) {

        Element blockElement = doc.createElement("ImageBlock");

        /* Set element attributes */
        Attr attr = doc.createAttribute("left");
        attr.setValue(Integer.toString(block.getLeftUpperCornerX()));
        blockElement.setAttributeNode(attr);

        attr = doc.createAttribute("top");
        attr.setValue(Integer.toString(block.getLeftUpperCornerY()));
        blockElement.setAttributeNode(attr);

        attr = doc.createAttribute("bottom");
        attr.setValue(Integer.toString(block.getLeftUpperCornerY()+block.getHeight()));
        blockElement.setAttributeNode(attr);

        attr = doc.createAttribute("right");
        attr.setValue(Integer.toString(block.getLeftUpperCornerX()+block.getWidth()));
        blockElement.setAttributeNode(attr);

        return blockElement;
    }

}
