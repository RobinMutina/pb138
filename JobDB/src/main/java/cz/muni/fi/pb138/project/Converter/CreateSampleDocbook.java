/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.project.Converter;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Impl.JobDoneManagerImpl;
import cz.muni.fi.pb138.project.Impl.JobTypeManagerImpl;
import cz.muni.fi.pb138.project.Impl.UserManagerImpl;
import cz.muni.fi.pb138.project.Interfaces.JobDoneManager;
import cz.muni.fi.pb138.project.Interfaces.JobTypeManager;
import cz.muni.fi.pb138.project.Interfaces.UserManager;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.sql.DataSource;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Node;

/**
 *
 * @author Vladislav Malynyc
 */
public class CreateSampleDocbook {

    private static Document document;
    private String path;
    
    public CreateSampleDocbook(String OS){
        if (OS.contains("Windows")){
            path = "src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml";
        }
        else{
            path = "JobDB/src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml";
        }
    }
    /**
     *
     * @param start
     * @param end
     * @throws FileNotFoundException
     * @throws FOPException
     * @throws TransformerException
     */

    public void generateDocBook() {
        try {
            document = DocumentHelper.createDocument();
            Element root = document.addElement("article");
            Element info = root.addElement("articleinfo");
            info.addElement("title").addText("Jobs Done at specified time");
            for(int j = 0;j<5;j++){
                    Element sectionElement = root.addElement("section");
                    sectionElement.addElement("title").addText("Vlad Malynych");
                    
                    Element tableElement = sectionElement.addElement("table").addAttribute("frame","all");
                    tableElement.addElement("title").addText("from:"+"12.12.45"+"to"+"12.45.15");
                    
                    Element table = tableElement.addElement("tgroup").addAttribute("cols", "4").addAttribute("align", "left");
                    table.addElement("colspec").addAttribute("colnum", "1").addAttribute("colname", "c1").addAttribute("colwidth", "1*");
                    table.addElement("colspec").addAttribute("colnum", "2").addAttribute("colname", "c2").addAttribute("colwidth", "1*");
                    table.addElement("colspec").addAttribute("colnum", "3").addAttribute("colname", "c3").addAttribute("colwidth", "1*");
                    table.addElement("colspec").addAttribute("colnum", "4").addAttribute("colname", "c4").addAttribute("colwidth", "1*");
                    
                    Element head = table.addElement("thead");
                    Element headrow = head.addElement("row");
                    headrow.addElement("entry").addAttribute("align", "center").addText("Job Type");
                    headrow.addElement("entry").addAttribute("align", "center").addText("Start");
                    headrow.addElement("entry").addAttribute("align", "center").addText("End");
                    headrow.addElement("entry").addAttribute("align", "center").addText("PricePerHour");
                    
                    Element foot = table.addElement("tfoot");
                    Element footrow = foot.addElement("row");
                    footrow.addElement("entry").addAttribute("namest", "c3").addAttribute("nameend", "c4").addAttribute("align", "center")
                            .addText("Total Salary:"+"1545");
                    
                    Element body = table.addElement("tbody");
                    for(int i=0;i<7;i++){
                        Element bodyrow = body.addElement("row");
                            bodyrow.addElement("entry").addAttribute("align", "center").addText("Work");
                            bodyrow.addElement("entry").addAttribute("align", "center").addText("12:00");
                            bodyrow.addElement("entry").addAttribute("align", "center").addText("13:00");
                            bodyrow.addElement("entry").addAttribute("align", "center").addText("141");
                    }
            }
                for(int j = 0;j<5;j++){
                    Element sectionElement = root.addElement("section");
                    sectionElement.addElement("title").addText("Vasya Voedylo");
                    
                    Element tableElement = sectionElement.addElement("table").addAttribute("frame","all");
                    tableElement.addElement("title").addText("from:"+"2.06.85"+"to"+"19.45.95");
                    
                    Element table = tableElement.addElement("tgroup").addAttribute("cols", "4").addAttribute("align", "left");
                    table.addElement("colspec").addAttribute("colnum", "1").addAttribute("colname", "c1").addAttribute("colwidth", "1*");
                    table.addElement("colspec").addAttribute("colnum", "2").addAttribute("colname", "c2").addAttribute("colwidth", "1*");
                    table.addElement("colspec").addAttribute("colnum", "3").addAttribute("colname", "c3").addAttribute("colwidth", "1*");
                    table.addElement("colspec").addAttribute("colnum", "4").addAttribute("colname", "c4").addAttribute("colwidth", "1*");
                    
                    Element head = table.addElement("thead");
                    Element headrow = head.addElement("row");
                    headrow.addElement("entry").addAttribute("align", "center").addText("Job Type");
                    headrow.addElement("entry").addAttribute("align", "center").addText("Start");
                    headrow.addElement("entry").addAttribute("align", "center").addText("End");
                    headrow.addElement("entry").addAttribute("align", "center").addText("PricePerHour");
                    
                    Element foot = table.addElement("tfoot");
                    Element footrow = foot.addElement("row");
                    footrow.addElement("entry").addAttribute("namest", "c3").addAttribute("nameend", "c4").addAttribute("align", "center")
                            .addText("Total Salary:"+"565");
                    
                    Element body = table.addElement("tbody");
                    for(int i=0;i<7;i++){
                        Element bodyrow = body.addElement("row");
                            bodyrow.addElement("entry").addAttribute("align", "center").addText("Developer");
                            bodyrow.addElement("entry").addAttribute("align", "center").addText("15:44");
                            bodyrow.addElement("entry").addAttribute("align", "center").addText("20:69");
                            bodyrow.addElement("entry").addAttribute("align", "center").addText("69");
                    }
            }

            writeToXML(document);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeToXML(Document document) throws IOException {
        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter(path));
        writer.write(document);
        writer.close();

        // Pretty print the document to System.out
        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter(System.out, format);
        writer.write(document);
    }
}