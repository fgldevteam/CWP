/*
 * Copyright 2004 Forzani Group Ltd.
 * All Rights Reserved
 *
 */
package org.faceless.report;

import java.io.*;

import org.faceless.pdf2.PDF;
import org.faceless.report.ReportParser;

import org.xml.sax.SAXException;
/**
 * A class for manually converting an XML file into PDF - used during development
 * @author dschultz
 */
public class PDFGenerator {

    /**
     * Command line main method
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        OutputStream stream = new BufferedOutputStream( new FileOutputStream("/Users/darcy/Desktop/output.pdf"));
        try {
            PDFGenerator.createPDF("/Users/darcy/Desktop/input.xml", stream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
    }
    
    /**
     * Generate a PDF from the contents of the XML file
     * @param xmlfile the name of the XML file
     * @param out the output stream to write the PDF
     * @throws IOException
     * @throws SAXException
     */
    public static void createPDF(String xmlfile, OutputStream out) throws IOException, SAXException   {       
       ReportParser parser = ReportParser.getInstance();
       PDF pdf = parser.parse(xmlfile);
       pdf.render(out);
       out.close();
   }    
}
