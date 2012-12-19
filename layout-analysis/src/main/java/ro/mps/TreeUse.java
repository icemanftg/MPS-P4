package ro.mps;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TreeUse {

    public static void Test() {
        ro.mps.XmlToTree tmp = new ro.mps.XmlToTree("xml.xml");
        Document doc = tmp.getTree();
        NodeList root = doc.getChildNodes();


        //Navigate down the hierarchy to get to the CEO node
        Node comp = tmp.getNode("Company", root);
        Node exec = tmp.getNode("Executive", comp.getChildNodes());
        String execType = tmp.getNodeAttr("type", exec);

//Load the executive's data from the XML
        NodeList nodes = exec.getChildNodes();
        String lastName = tmp.getNodeValue("LastName", nodes);
        String firstName = tmp.getNodeValue("FirstName", nodes);
        String street = tmp.getNodeValue("street", nodes);
        String city = tmp.getNodeValue("city", nodes);
        String state = tmp.getNodeValue("state", nodes);
        String zip = tmp.getNodeValue("zip", nodes);

        System.out.println("Executive Information:");
        System.out.println("Type: " + execType);
        System.out.println(lastName + ", " + firstName);
        System.out.println(street);
        System.out.println(city + ", " + state + " " + zip);
    }
}

