package cz.muni.fi.pb138.project.Converter;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Path;

/**
 * Created by xtomasch on 6/2/16.
 */
public class DocbookToPDFConverter {

    private static FopFactory fopFactory;

    public DocbookToPDFConverter(){
        if (fopFactory == null){
            try {
                File cfgFile = new File("src/main/resources/cz/muni/fi/pb138/project/Utilities/fopCfg.xml");
                fopFactory = FopFactory.newInstance(cfgFile);
            } catch(Exception e) {
                //FIXME: throw more apropriate exceptions in whole class
                throw new RuntimeException(e);
            }
        }
    }

    public void convert(String XMLcontent, String resultPdfPath){

        String FOcontent = new XSLTProcessor().applyXSL(
                XMLcontent,"src/main/resources/cz/muni/fi/pb138/project/Utilities/docbook-xsl/fo/docbook.xsl");

        OutputStream out = null;

        try {

            // Step 2: Set up output stream.
            // Note: Using BufferedOutputStream for performance reasons (helpful with FileOutputStreams).
            out = new BufferedOutputStream(new FileOutputStream(new File(resultPdfPath)));

            // Step 3: Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

            // Step 4: Setup JAXP using identity transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(); // identity transformer

            // Step 5: Setup input and output for XSLT transformation
            // Setup input stream
            Source src = new StreamSource(new StringReader(FOcontent));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Step 6: Start XSLT transformation and FOP processing
            transformer.transform(src, res);

        } catch(Exception e){
            throw new RuntimeException(e);
        }finally {
            //Clean-up
            try {
                out.close();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
