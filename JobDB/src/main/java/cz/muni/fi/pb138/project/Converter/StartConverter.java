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
    private static String pathXML;
    private static String pathPDF;
    
    private static void setPath(String OS){
        if (OS.contains("Windows")){
            pathXML = "src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml";
            pathPDF = "GeneratedPDF/jobsdone.pdf";
        }
        else{
            pathXML = "JobDB/src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml";
            pathPDF = "JobDB/GeneratedPDF/jobsdone.pdf";
        }
    }
    /**
     * Just for testing not for use
     * @param args args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception{
        LocalDateTime start = LocalDateTime.of(2011, 11, 25, 06, 02);
        LocalDateTime end = LocalDateTime.of(2018, 11, 25, 06, 02);
       
        String OS = System.getProperty("os.name");
        setPath(OS);
        
        new CreateSampleDocbook(OS).generateDocBook(start,end);
        
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(new File(pathXML));
        
        DocbookToPDFConverter test = new DocbookToPDFConverter(OS);
        test.convert(XMLcontent,pathPDF);
        
//        new CreateSampleDocbook(OS).generateDocBookForUser(0L,start,end);
//        
//        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(new File(pathXML));
//        
//        DocbookToPDFConverter conv = new DocbookToPDFConverter(OS);
//        conv.convert(XMLcontent,pathPDF);
    }
    
    /**
     * Generates docbook xml file for all users from data in specified time, and converts it to PDF.
     * Generated pdf file is in source folder GeneratedPDF/jobsdone.pdf
     * @param start start time
     * @param end end time
     */
    public void convertAll(LocalDateTime start, LocalDateTime end) throws Exception{
        String OS = System.getProperty("os.name");
        setPath(OS);
        
        new CreateSampleDocbook(OS).generateDocBook(start,end);
        
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(new File(pathXML));
        
        DocbookToPDFConverter conv = new DocbookToPDFConverter(OS);
        conv.convert(XMLcontent,pathPDF);
    }
    
    /**
     * Generates docbook xml file for one user from data in specified time, and converts it to PDF.
     * @param id user id
     * @param start start time
     * @param end end time
     * @throws Exception 
     */
    public void convertOneUser(Long id, LocalDateTime start, LocalDateTime end) throws Exception{
        String OS = System.getProperty("os.name");
        setPath(OS);
        
        new CreateSampleDocbook(OS).generateDocBookForUser(id,start,end);
        
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(new File(pathXML));
        
        DocbookToPDFConverter conv = new DocbookToPDFConverter(OS);
        conv.convert(XMLcontent,pathPDF);
    }
}