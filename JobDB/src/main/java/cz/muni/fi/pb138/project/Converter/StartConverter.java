/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.project.Converter;

import java.io.File;
import java.time.LocalDateTime;


/**
 *
 * @author Vladislav Malynych
 */
public class StartConverter {
    private String pathXML;
    private String pathPDF;
    private Boolean netb;

    public StartConverter(){
        pathXML = "src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml";
        pathPDF = "GeneratedPDF/jobsdone.pdf";
        netb = true;
    }

    private void changePath(){
        pathXML = "JobDB/src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml";
        pathPDF = "JobDB/GeneratedPDF/jobsdone.pdf";
        netb = false;
    }
    /**
     * Just for testing not for use
     * @param args args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception{
        
        String pathXML1 = "src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml";
        String pathPDF1 = "GeneratedPDF/jobsdone.pdf";
        Boolean netb1 = true;
        
        LocalDateTime start = LocalDateTime.of(2011, 11, 25, 06, 02);
        LocalDateTime end = LocalDateTime.of(2018, 11, 25, 06, 02);

        if ((new CreateSampleDocbook().generateDocBook(start, end)) == false){
            pathXML1 = "JobDB/src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml";
            pathPDF1 = "JobDB/GeneratedPDF/jobsdone.pdf";
            netb1 = false;
        }
        
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(new File(pathXML1));

        DocbookToPDFConverter test = new DocbookToPDFConverter(netb1);
        test.convert(XMLcontent,pathPDF1);

    }
    
    /**
     * Generates docbook xml file for all users from data in specified time, and converts it to PDF.
     * Generated pdf file is in source folder GeneratedPDF/jobsdone.pdf
     * @param start start time
     * @param end end time
     */
    public void convertAll(LocalDateTime start, LocalDateTime end) throws Exception{
        if ((new CreateSampleDocbook().generateDocBook(start, end)) == false){
            changePath();
        }
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(new File(pathXML));

        DocbookToPDFConverter test = new DocbookToPDFConverter(netb);
        test.convert(XMLcontent,pathPDF);
    }
    
    /**
     * Generates docbook xml file for one user from data in specified time, and converts it to PDF.
     * @param id user id
     * @param start start time
     * @param end end time
     * @throws Exception 
     */
    public void convertOneUser(Long id, LocalDateTime start, LocalDateTime end) throws Exception{
        if ((new CreateSampleDocbook().generateDocBookForUser(id,start, end)) == false){
            changePath();
        }
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(new File(pathXML));

        DocbookToPDFConverter test = new DocbookToPDFConverter(netb);
        test.convert(XMLcontent,pathPDF);
    }
}