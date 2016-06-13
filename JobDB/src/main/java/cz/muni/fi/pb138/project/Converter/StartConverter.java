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
    public static void main(String[] args) throws Exception{
        String OS = System.getProperty("os.name");
        setPath(OS);
        new CreateSampleDocbook(OS).generateDocBook();
        
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(new File(pathXML));
        
        DocbookToPDFConverter test = new DocbookToPDFConverter(OS);
        test.convert(XMLcontent,pathPDF);
    }
    /**
     * Generates docbook xml file from data in specified time, and converts it to PDF.
     * Generated pdf file is in source folder GeneratedPDF/jobsdone.pdf
     * @param start start time
     * @param end end time
     */
    public void convert() throws Exception{
        String OS = System.getProperty("os.name");
        setPath(OS);
        new CreateSampleDocbook(OS).generateDocBook();
        
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(new File(pathXML));
        
        DocbookToPDFConverter test = new DocbookToPDFConverter(OS);
        test.convert(XMLcontent,pathPDF);
import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 * @author Vladislav Malynych
 */
public class StartConverter {
    /**
     * Testing method to check generating of docbook and PDF files
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception{
        LocalDateTime start = LocalDateTime.of(2014, 11, 11, 2, 23);
        LocalDateTime end = LocalDateTime.of(2018, 11, 11, 2, 23);
        new CreateSampleDocbook().generateDocBook(start,end);
        
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(
                new File("src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml"));
        
        DocbookToPDFConverter test = new DocbookToPDFConverter();
        test.convert(XMLcontent,"GeneratedPDF/jobsdone.pdf");
    }
    /**
     * Generates docbook xml file from data in specified time, and converts it to PDF.
     * Generated pdf file is in source folder GeneratedPDF/jobsdone.pdf
     * @param start start time
     * @param end end time
     * @throws java.lang.Exception
     */
    public void convert(LocalDateTime start,LocalDateTime end) throws Exception{
        new CreateSampleDocbook().generateDocBook(start,end);
        
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(
                new File("src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml"));
        
        DocbookToPDFConverter test = new DocbookToPDFConverter();
        test.convert(XMLcontent,"GeneratedPDF/jobsdone.pdf");
    }
}
