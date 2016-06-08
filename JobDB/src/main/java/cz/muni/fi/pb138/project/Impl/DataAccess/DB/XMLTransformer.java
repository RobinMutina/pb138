package cz.muni.fi.pb138.project.Impl.DataAccess.DB;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Xml transformation between string and node
 * @author Martin Sevcik
 */
public class XMLTransformer {

    /**
     * Converts xml file to string
     * @param node node to convert
     * @return String format of node
     * @throws TransformerException if error occurs
     */
    public static String nodeToString(Node node) throws TransformerException {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            throw new TransformerException("Transforming to XML format failed", te);
        }
        return sw.toString();
    }

    /**
     * Creates document from String
     * @param input input string
     * @return Document
     * @throws IOException thrown if error occurs
     * @throws SAXException thrown if error occurs
     * @throws ParserConfigurationException thrown if error occurs
     * @throws IllegalArgumentException if input is null
     */
    public static Document stringToDocument(String input) throws IOException, SAXException, ParserConfigurationException {
        if (input == null){
            throw new IllegalArgumentException("input is null");
        }

        if (input.isEmpty()){
            return null;
        }

        DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(input));
        Document doc = dBuilder.parse(is);
        doc.getDocumentElement().normalize();

        return doc;
    }
}
