package com.fgl.cwp.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.mail.internet.ContentDisposition;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fgl.cwp.exception.InvalidDirectoryException;


/**
 * Send a file to the client's browser.
 * @author bting
 */
public class FileDownloadServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   
    protected static Log log = LogFactory.getLog(FileDownloadServlet.class);
    
    private static final int BUFFER_SIZE = 2048;

    private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("language_ascii");
    private static String NOTICE_DIRECTORY = RESOURCE_BUNDLE.getString("notices.filepath");
    private static String DOCUMENT_DIRECTORY = RESOURCE_BUNDLE.getString("documents.filepath");
    private static String FORM_DIRECTORY = RESOURCE_BUNDLE.getString("forms.filepath"); 

    
    /**
     * Compare the requested directory with the list of valid ones.
     * @param directory
     * @throws InvalidDirectoryException
     */
    protected void checkDirectory(String directory) throws InvalidDirectoryException {
        
        File noticeDir = new File(NOTICE_DIRECTORY);
        File documentDir = new File(DOCUMENT_DIRECTORY);
        File formDir = new File(FORM_DIRECTORY);
        
        File dir = new File(directory);
        
        boolean found = false;

        if (dir.isDirectory()) {
            if (noticeDir.equals(dir)
                    || documentDir.equals(dir)
                    || formDir.equals(dir)) {
                found = true;
            }
        }

        if (!found) {
            throw new InvalidDirectoryException("Unauthorized download from directory: " + directory); 
        }

    }
    /**
     * Send a file to the client
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("FileDownloadServlet: start");
        
        String directory = request.getParameter("dir");
        String filename = request.getParameter("file");
        String contentType = request.getParameter("contentType");
        String inline = request.getParameter("inline");


        checkDirectory(directory);
        
        // Check if file exists
        File file = new File(directory, filename);
        if (!file.isFile() || !file.exists()) {
            throw new FileNotFoundException();
        }
        
        
        log.debug("filename: " + filename);

        if (filename != null) {
            response.setContentType(contentType);

            if (inline.equalsIgnoreCase("false")) {

                ContentDisposition cd = new ContentDisposition();
                cd.setDisposition("attachment");
                cd.setParameter("filename", filename);

                response.setHeader("Content-Disposition", cd.toString());
            }
            
            // Read in the file and stream to browser
            InputStream is = null;
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            ServletOutputStream out = null;
            try {
                is = new FileInputStream(file);
                bis = new BufferedInputStream(is);

                // Set the response content length
                response.setContentLength(bis.available());
                
                out = response.getOutputStream();
                bos = new BufferedOutputStream(out);
                
                byte[] buff = new byte[BUFFER_SIZE];
                int bytesRead;

                // Simple read/write loop.
                while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                
            } catch (IOException ioe) {
                log.error(ioe.getMessage());
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (Throwable t) {
                        // do nothing
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (Throwable t) {
                        // do nothing
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (Throwable t) {
                        // do nothing
                    }
                }
                if (out != null) {
                    out.flush();
                    try {
                        out.close();
                    } catch (Throwable t) {
                        // do nothing
                    }
                }

            }
        }
    }
}
