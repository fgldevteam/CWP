/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Mar 16, 2005
 */
package com.fgl.cwp.persistence.services;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.faceless.pdf2.PDF;
import org.faceless.report.ReportParser;

import com.fgl.cwp.exception.GeneratePdfException;

/**
 * @author bting
 */
public class PDFService {
    
    private static Log log = LogFactory.getLog(PDFService.class);
    
    private static PDFService instance = null;
    
    private PDFService() {
        // empty
    }
    
    /**
     * Return the instance
     * @return
     */
    public static PDFService getInstance() {
        if (instance == null) {
            instance = new PDFService();
        }
        return instance;
    }
    
    
    /**
     * Generate the pdf from the given url and save to file system
     * @param xmlTemplateURL
     * @param dir - the directory to save the pdf to
     * @param fileName
     * @return
     * @throws GeneratePdfException
     */
    public File generatePdf(String xmlTemplateURL, String dir, String fileName) throws GeneratePdfException {
        
        File pdfFile = null;        
        FileOutputStream fos = null;

        final String DATE_FORMAT = "MM-dd-yyyy.HHmmssSSS";
        
        try {
            ReportParser parser = ReportParser.getInstance();

            // write to file system
            PDF pdf = parser.parse(xmlTemplateURL);
            
            String timeStamp = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
            
            String filePath = dir + fileName + "." + timeStamp + ".pdf";
            pdfFile = new File(filePath);
            
            // make sure the directory exists
            File directory = new File(pdfFile.getParent());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            fos = new FileOutputStream(pdfFile);
            pdf.render(fos);           
            
            // Update the report status to processed
            log.info("Pdf file: " + filePath + " generated successfully");
            
        } catch (Throwable t) {
            log.error("Failed to generate pdf for " + fileName, t);
            throw new GeneratePdfException(t);
        } finally {
            // Close all resources
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    // do nothing
                }
            }
        }
        return pdfFile;
    }
    
}
