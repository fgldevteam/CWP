/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Feb 15, 2005
 */
package com.fgl.cwp.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import com.fgl.cwp.common.Constants;

/**
 * @author bting
 */
public abstract class FileAttachment {
    
    private static Log log = LogFactory.getLog(FileAttachment.class);

    private FormFile attachment;
    
    /**
     * Save attachment to directory.
     * @param directory - location of where to save the attachment
     * @param fileName - the file name
     * @return boolean
     * @throws IOException
     */
    private boolean saveToDisk(String directory, String fileName) throws IOException {
        
        boolean success = false;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        
        File file = new File(directory, fileName);
        
        try {            
            // Save the new file to disk
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(attachment.getFileData());
            bos.flush();
            success = true;
        } catch (IOException ioe) {
            log.error("Failed to write file to disk", ioe);
            throw ioe;
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                    bos = null;
                }
            } catch (Exception e) {
                // do nothing
            }
            
            try {
                if (fos != null) {
                    fos.close();
                    fos = null;
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return success;
    }

    /**
     * Delete file attachment from directory.
     * @param directory - the location of where to delete the file
     * @param fileName - the name of the file
     * @return boolean
     */
    public boolean deleteFromDisk(String directory, String fileName) {
        if (StringUtils.isNotEmpty(fileName)) {
            File file = new File(directory, fileName);
            if (file.exists()) {
                if (file.delete()) {
                    log.debug("deleted file from disk: " + fileName);
                    return true;
                }
            }
        }
        log.error("failed to delete file from disk: " + fileName);
        return false;
    }
    
    
    
    /**
     * Validate and save attachment.
     * @param directory
     * @param newFileName
     * @param doDelete
     * @param oldFileName
     * @return
     * @throws Exception
     */
    public List saveAttachment(String directory, String newFileName, boolean doDelete, String oldFileName) throws Exception {
        
        List<String> errors = new ArrayList<String>();


        if (StringUtils.isEmpty(newFileName)) {  
            errors.add("attachment.error.file.required");
        }
        
        if (errors.isEmpty()) {
            try {
                // Check that we received a file with data
                if (attachment.getFileSize() <= 0) {
                    errors.add("attachment.error.file.empty");
                } else if (attachment.getFileSize() > Constants.MAX_FILE_SIZE ) {
                    errors.add("attachment.error.fileSizeTooBig");
                }
                
                if (errors.isEmpty()) {

                    if (doDelete) {
                        deleteFromDisk(directory, oldFileName);
                    }
                    
                    // save new file to disk
                    saveToDisk(directory, newFileName);
                }
                
            } catch (Exception e) {
                log.error("failed to save file to disk", e);
                throw e;
            }
        }
        
        return errors;
        
    }
    
    /**
     * @return Returns the attachment.
     */
    public FormFile getAttachment() {
        return attachment;
    }
    /**
     * @param attachment The attachment to set.
     */
    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }
}
