package cz.muni.fi.pb138.project.Converter;

import java.io.*;
import java.time.LocalDateTime;

/**
 * Created by xtomasch on 6/3/16.
 */
public class PDFtest {
    public static void main(String[] args) throws Exception{
        LocalDateTime start = LocalDateTime.of(2014, 11, 11, 2, 23);
        LocalDateTime end = LocalDateTime.of(2018, 11, 11, 2, 23);
        new CreateSampleDocbook().generateDocBook(start,end);
        
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(
                new File("src/main/resources/cz/muni/fi/pb138/project/Examples/jobsdocbook.xml"));
        
        DocbookToPDFConverter test = new DocbookToPDFConverter();
        test.convert(XMLcontent,"GeneratedPDF/jobsdone.pdf");
    }
}