package cz.muni.fi.pb138.project.Examples;

import cz.muni.fi.pb138.project.Utilities.DocbookToPDFConverter;

import java.io.*;

/**
 * Created by xtomasch on 6/3/16.
 */
public class PDFtest {
    public static void main(String[] args) throws Exception{
        String XMLcontent = org.apache.commons.io.FileUtils.readFileToString(
                new File(PDFtest.class.getResource("docbooktest.xml").getPath()));
        new DocbookToPDFConverter().convert(XMLcontent,"/home/xtomasch/muahaha.pdf");
    }
}
