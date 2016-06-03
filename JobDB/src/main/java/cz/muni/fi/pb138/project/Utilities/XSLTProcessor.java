package cz.muni.fi.pb138.project.Utilities;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by xtomasch on 6/3/16.
 */
public class XSLTProcessor {
    /**
     * http://stackoverflow.com/questions/851987/how-to-transform-a-string-object-containing-xml-to-an-element-on-an-existing/852010#852010
     * @param XMLcontent
     * @param XSLpath
     * @return
     */
    public String applyXSL(String XMLcontent, String XSLpath){
        try {
            StringReader reader = new StringReader(XMLcontent);
            StringWriter writer = new StringWriter();
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(
                    new javax.xml.transform.stream.StreamSource(XSLpath));

            transformer.transform(
                    new javax.xml.transform.stream.StreamSource(reader),
                    new javax.xml.transform.stream.StreamResult(writer));

            String result = writer.toString();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
