package ro.mps;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XmlToTree {

    private String _fName;

    public Node getNode(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                return node;
            }
        }
        return null;
    }

    public String getNodeValue(Node node) {
        NodeList childNodes = node.getChildNodes();
        for (int x = 0; x < childNodes.getLength(); x++) {
            Node data = childNodes.item(x);
            if (data.getNodeType() == Node.TEXT_NODE)
                return data.getNodeValue();
        }
        return "";
    }

    public String getNodeValue(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                NodeList childNodes = node.getChildNodes();
                for (int y = 0; y < childNodes.getLength(); y++) {
                    Node data = childNodes.item(y);
                    if (data.getNodeType() == Node.TEXT_NODE)
                        return data.getNodeValue();
                }
            }
        }
        return "";
    }

    public String getNodeAttr(String attrName, Node node) {
        NamedNodeMap attrs = node.getAttributes();
        for (int y = 0; y < attrs.getLength(); y++) {
            Node attr = attrs.item(y);
            if (attr.getNodeName().equalsIgnoreCase(attrName)) {
                return attr.getNodeValue();
            }
        }
        return "";
    }

    public String getNodeAttr(String tagName, String attrName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                NodeList childNodes = node.getChildNodes();
                for (int y = 0; y < childNodes.getLength(); y++) {
                    Node data = childNodes.item(y);
                    if (data.getNodeType() == Node.ATTRIBUTE_NODE) {
                        if (data.getNodeName().equalsIgnoreCase(attrName))
                            return data.getNodeValue();
                    }
                }
            }
        }

        return "";
    }

    public XmlToTree(String fName) {
        _fName = fName;
    }

    public Document getTree() {
        try {
            DOMParser parser = new DOMParser();
            parser.parse(_fName);
            return parser.getDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}


//EXEMPLU FOLOSIRE:::
//  XMLtoTree tmp = new XMLtoTree("calea catre fisier XML");
//  Document doc = tmp.getTree();
//        NodeList root = doc.getChildNodes();
//
//
//// Navigate down the hierarchy to get to the CEO node
//Node comp = getNode("Company", root);
//Node exec = getNode("Executive", comp.getChildNodes() );
//String execType = getNodeAttr("type", exec);
//
//// Load the executive's data from the XML
//NodeList nodes = exec.getChildNodes();
//String lastName = getNodeValue("LastName", nodes);
//String firstName = getNodeValue("FirstName", nodes);
//String street = getNodeValue("street", nodes);
//String city = getNodeValue("city", nodes);
//String state = getNodeValue("state", nodes);
//String zip = getNodeValue("zip", nodes);
//
//System.out.println("Executive Information:");
//System.out.println("Type: " + execType);
//System.out.println(lastName + ", " + firstName);
//System.out.println(street);
//System.out.println(city + ", " + state + " " + zip);
