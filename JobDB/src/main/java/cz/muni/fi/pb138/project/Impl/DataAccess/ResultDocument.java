package cz.muni.fi.pb138.project.Impl.DataAccess;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by martin on 31.5.2016.
 */
public class ResultDocument {
    public static Document getDocument(String input) throws IOException, SAXException, ParserConfigurationException {
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
